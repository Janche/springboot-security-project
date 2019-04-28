package com.cebon.cdjcy.oauth.service;

import com.cebon.cdjcy.common.core.Service;
import com.cebon.cdjcy.common.restResult.PageParam;
import com.cebon.cdjcy.oauth.domain.OauthClientDetails;
import com.cebon.cdjcy.oauth.dto.OauthClientDetailsDTO;

import java.util.List;

/**
* @author lirong
* @Description: // TODO 为类添加注释
* @date 2019-03-21 10:38:28
*/
public interface OauthClientDetailsService extends Service<OauthClientDetails> {

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    List<OauthClientDetails> list(PageParam pageParam, String query);

    /**
     * 获取所有的客户端的信息
     * @return
     */
    List<OauthClientDetailsDTO> getAllClientDetails();

    /**
     * 新增oauthclient数据
     * @param dto
     * @return
     */
    void addClientDetails(OauthClientDetailsDTO dto);

    /**
     * 删除此客户端的所有信息
     * @param oldClientId
     */
    void deleteClient(String oldClientId);
}
