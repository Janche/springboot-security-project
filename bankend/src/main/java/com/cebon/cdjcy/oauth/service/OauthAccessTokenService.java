package com.cebon.cdjcy.oauth.service;

import com.cebon.cdjcy.common.core.Service;
import com.cebon.cdjcy.common.restResult.PageParam;
import com.cebon.cdjcy.oauth.domain.OauthAccessToken;

import java.util.List;

/**
* @author lirong
* @Description: // TODO 为类添加注释
* @date 2019-03-21 10:38:28
*/
public interface OauthAccessTokenService extends Service<OauthAccessToken> {

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    List<OauthAccessToken> list(PageParam pageParam, String query);

}
