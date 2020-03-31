package com.xuecheng.api.config.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;

public interface CmsPageControllerApi {
    //页面分页查询接口
    @ApiOperation("分页查询页面列表")
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    @ApiOperation("新增页面")
    public CmsPageResult add(CmsPage cmsPage);

    @ApiOperation("修改页面")
    public CmsPageResult edit(String id,CmsPage cmsPage);

    @ApiOperation("通过id页面")
    public CmsPage findById(String id);

    @ApiOperation("删除页面")
    public ResponseResult delete(String id);

    @ApiOperation("页面发布")
    public ResponseResult post(String pageId);

    @ApiOperation("保存页面")
    public CmsPageResult save(CmsPage cmsPage);


    @ApiOperation("保存页面")
    public CmsPostPageResult postPageQuick(CmsPage cmsPage);



}
