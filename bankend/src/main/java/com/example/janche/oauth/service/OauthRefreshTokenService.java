package com.example.janche.oauth.service;

import com.example.janche.common.core.Service;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.oauth.domain.OauthRefreshToken;

import java.util.List;

/**
* @author lirong
* @Description: // TODO 为类添加注释
* @date 2019-03-21 10:38:28
*/
public interface OauthRefreshTokenService extends Service<OauthRefreshToken> {

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    List<OauthRefreshToken> list(PageParam pageParam, String query);

}
