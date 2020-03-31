package com.xuecheng.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestGridFS {

    //存储模板文件到mongodb数据
    @Autowired
    GridFSBucket gridFSBucket;
    @Autowired
    GridFsTemplate gridFsTemplate;

//    @Test
//    public  void testStore() throws FileNotFoundException{
//        File file = new File("D:\\course.ftl");
//        FileInputStream fileInputStream = new FileInputStream(file);
//        ObjectId objectId = gridFsTemplate.store(fileInputStream,"course.ftl");
//        System.out.println(objectId); //5e4404a1e444173178b66b9a
//    }

    @Test
    public  void testStore() throws FileNotFoundException{
        File file = new File("D:\\idea-xconline\\xcEduService\\xc-service-manage-cms\\src\\main\\resources\\index_banner.ftl");
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectId objectId = gridFsTemplate.store(fileInputStream,"index_banner.ftl");
        System.out.println(objectId); //5e772f1f77fccb494838542c
    }
}
