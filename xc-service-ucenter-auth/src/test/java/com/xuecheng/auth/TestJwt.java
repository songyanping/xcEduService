package com.xuecheng.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateCrtKey;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestJwt {
    @Test
    public void testCreateJwt(){
        //证书文件
        String key_location = "xc.keystore";
        //密钥库密码
        String keystore_password = "xuechengkeystore";
        //访问证书路径
        ClassPathResource resource = new ClassPathResource(key_location);
        //密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource,keystore_password.toCharArray());
        //密钥的密码，此密码和别名要匹配
        String keypassword = "xuecheng";
        //密钥别名
        String alias = "xckey";
        //密钥对
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias,keypassword.toCharArray());
        //私钥
        RSAPrivateCrtKey aPrivate = (RSAPrivateCrtKey) keyPair.getPrivate();
        //定义payload信息
        Map<String,Object> tokenMap = new HashMap<>();
        tokenMap.put("name","itcast");
        String bodyString  = JSON.toJSONString(tokenMap);
        //生成jwt令牌
        Jwt jwt = JwtHelper.encode(bodyString,new RsaSigner(aPrivate));
        //取出令牌
        String token = jwt.getEncoded();
        System.out.println("token="+token);

    }

    //校验jwt令牌
    @Test
    public void testVerify(){
        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnASXh9oSvLRLxk901HANYM6KcYMzX8vFPnH/To2R+SrUVw1O9rEX6m1+rIaMzrEKPm12qPjVq3HMXDbRdUaJEXsB7NgGrAhepYAdJnYMizdltLdGsbfyjITUCOvzZ/QgM1M4INPMD+Ce859xse06jnOkCUzinZmasxrmgNV3Db1GtpyHIiGVUY0lSO1Frr9m5dpemylaT0BV3UwTQWVW9ljm6yR3dBncOdDENumT5tGbaDVyClV0FEB1XdSKd7VjiDCDbUAUbDTG1fm3K9sx7kO1uMGElbXLgMfboJ963HEJcU01km7BmFntqI5liyKheX+HBUCD4zbYNPw236U+7QIDAQAB-----END PUBLIC KEY-----";
        //jwt令牌
        String jwtString = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOiIxIiwidXNlcnBpYyI6bnVsbCwidXNlcl9uYW1lIjoiaXRjYXN0Iiwic2NvcGUiOlsiYXBwIl0sIm5hbWUiOiJ0ZXN0MDIiLCJ1dHlwZSI6IjEwMTAwMiIsImlkIjoiNDkiLCJleHAiOjE1ODQ3NjIyNTEsImF1dGhvcml0aWVzIjpbImNvdXJzZV9maW5kX3BpYyIsImNvdXJzZV9nZXRfYmFzZWluZm8iXSwianRpIjoiMzk5MjM1NDktODRiOC00ZGYyLWI3MjgtZjcyZjI2YjIyZDFjIiwiY2xpZW50X2lkIjoiWGNXZWJBcHAifQ.fFcPrnB0E4G5TBxBbf99eaUZmHRh67z2bjC14Umw5nnafiSmmslOj7rCEfZFEE-CgpN0SjJgmNI_B4YOEsxz3dM272YaMdBWAHDiPTayKQ_4RQfum96CMSZObGC09jVJ4wbTqAk3aJHQ3DA_cW3vxswkC1gV1raeM09V0Naofpo4F5mWYAKpm872s5wPie12cb4I3H4JtgjN_VW-O_nEtDHslW1nT637cpLNIVb5uHMtZ7IDNoZSaGqOScYk5BR0OYugNw3krI_7FDdVKn__1vMrkcwsgucWP3lttGOZ6a7LKgHORaQHmYJrJ9kWDnzLXeWIM-NrhMYiy_ZunyLR9g";
        //校验jwt令牌
        Jwt jwt =JwtHelper.decodeAndVerify(jwtString,new RsaVerifier(publickey));
        //拿到jwt令牌中的自定义的内容
        String claims = jwt.getClaims();
        System.out.println(claims);

    }


}
