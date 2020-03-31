package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

public interface CmsPageService {
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    CmsPageResult add(CmsPage cmsPage);

    CmsPageResult update(String id, CmsPage cmsPage);


    ResponseResult delete(String id);

    CmsPage getById(String id);

    String getPageHtml(String pageId);

    ResponseResult postPage(String pageId);

    CmsPageResult save(CmsPage cmsPage);

    CmsPostPageResult postPageQuick(CmsPage cmsPage);
}
