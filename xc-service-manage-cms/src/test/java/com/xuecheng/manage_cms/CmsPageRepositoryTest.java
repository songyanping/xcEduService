package com.xuecheng.manage_cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.*;

//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class CmsPageRepositoryTest {

//    @Autowired
//    CmsPageRepository cmsPageRepository;
//
//    @Autowired
//    RestTemplate restTemplate;


//    //测试分页
//    @Test
//    public void  testFindPage(){
//        int page = 0;   //从零开始
//        int size = 10; //每页记录数
//        Pageable pageable = PageRequest.of(page,size);
//        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
//        System.out.println("123");
//        System.out.println(all);
//    }
//    //测试插入
//    @Test
//    public void testInsert(){
//        CmsPage cmsPage = new CmsPage();
//        cmsPage.setSiteId("site01");
//        cmsPage.setTemplateId("templateId01");
//        cmsPage.setPageName("测试页面01");
//        cmsPage.setPageCreateTime(new Date());
//        List<CmsPageParam> cmsPageParams = new ArrayList<>();
//        CmsPageParam cmsPageParam = new CmsPageParam();
//        cmsPageParam.setPageParamName("pageparam1");
//        cmsPageParam.setPageParamValue("paramValue1");
//        cmsPageParams.add(cmsPageParam);
//        cmsPage.setPageParams(cmsPageParams);
//        cmsPageRepository.save(cmsPage);
//    }
//
//    //测试删除
//    @Test
//    public void testDelete(){
//        cmsPageRepository.deleteById("5def7030551e892064399b4a");
//
//    }
//
//    //测试更新
//    @Test
//    public void testUpdate(){
//        Optional<CmsPage> optional = cmsPageRepository.findById("5def8af92a92d93738167d0f");
//        if(optional.isPresent()){
//            CmsPage cmsPage = optional.get();
//            cmsPage.setPageName("测试页面2");
//            cmsPageRepository.save(cmsPage);
//        }
//
//    }

//    //测试http获取接口
//    @Test
//    public void testRestTemplate(){
//        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f",Map.class);
//        System.out.println(forEntity);
//    }

//}
