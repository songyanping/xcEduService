package com.xuecheng.auth.service;

import com.xuecheng.auth.client.UserClient;
import com.xuecheng.framework.domain.ucenter.XcMenu;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.framework.exception.ExceptionCatch;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER= LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if(authentication==null){
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if(clientDetails!=null){
                //密码
                String clientSecret = clientDetails.getClientSecret();
                return new User(username,clientSecret,AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }


        //远程调用用户服务，获取信息
        XcUserExt userext = userClient.getUserext(username);
        if (userext == null){
            //返回空，表示用户不存在,spring security会抛出异常
            return null;
        }


//        XcUserExt userext = new XcUserExt();
//        userext.setUsername("itcast");
          //密码加盐
//        userext.setPassword(new BCryptPasswordEncoder().encode("123"));

        //取出正确密码（hash值）
        String password = userext.getPassword();
        //这里暂时使用静态密码
        //String password ="123";
        //用户权限，这里暂时使用静态数据，最终会从数据库读取
        //从数据库获取权限
        List<String> user_permission = new ArrayList<>();
        List<XcMenu> permissions = userext.getPermissions();
        permissions.forEach(item-> user_permission.add(item.getCode()));
//        user_permission.add("course_get_baseinfo");
//        user_permission.add("course_find_pic");

        String user_permission_string  = StringUtils.join(user_permission.toArray(), ",");
        LOGGER.info(" |--------------------------| ");
        LOGGER.info(" |-----------用户权限--------|");
        LOGGER.info("用户：{},拥有的权限：{}",username,user_permission_string);
        LOGGER.info(" |---------------------------| ");
        UserJwt userDetails = new UserJwt(username,password,AuthorityUtils.commaSeparatedStringToAuthorityList(user_permission_string));
        userDetails.setId(userext.getId());
        userDetails.setUtype(userext.getUtype());//用户类型
        userDetails.setCompanyId(userext.getCompanyId());//所属企业
        userDetails.setName(userext.getName());//用户名称
        userDetails.setUserpic(userext.getUserpic());//用户头像
        return userDetails;


    }
}
