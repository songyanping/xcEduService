package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

    //扩展查询方法
    //根据页面名称查询
    public CmsPage findByPageName(String pageName);

    //根据页面名称和类型查询
    public CmsPage findByPageNameAndPageType(String pageName,String pageType);

    //根据 站点和页面类型查询记录数
    public int countBySiteIdAndPageType(String siteId, String pageType);

    //根据站点和页面类型分页查询
    public CmsPage findBySiteIdAndPageType(String siteId, String pageType);

    //根据页面名称、站点id、页面访问路径查询
    public CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName,String siteId,String pageWebPath );




}
