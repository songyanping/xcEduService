package com.xuecheng.api.config.course;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(value ="课程管理接口",description = "课程管理接口，提供课程增删改查")
public interface CourseControllerApi {

    @ApiOperation("查询课程计划列表接口")
    public TeachplanNode findTeachplanList(String courseId);

    @ApiOperation("添加课程计划列表接口")
    public ResponseResult addTeachplan(Teachplan teachplan);

    @ApiOperation("删除课程计划列表接口")
    public ResponseResult deleteTeachplan(String courseId);

    @ApiOperation("添加课程图片")
    public ResponseResult addCoursePic(String courseId,String pic);

    @ApiOperation("删除课程图片")
    public ResponseResult deleteCoursePic(String courseId);

    @ApiOperation("获取课程图片")
    public CoursePic findCoursePic(String courseId);

    @ApiOperation("课程视图查询")
    public CourseView courseview(String id);

    @ApiOperation("课程预览")
    public CoursePublishResult preview(String id);

    @ApiOperation("分页课程查询")
    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);

    @ApiOperation("课程发布")
    public CoursePublishResult publish(@PathVariable String id);
}
