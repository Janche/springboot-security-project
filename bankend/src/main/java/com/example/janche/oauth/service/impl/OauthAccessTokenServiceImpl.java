package com.example.janche.oauth.service.impl;

import com.example.janche.common.core.AbstractService;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.oauth.dao.OauthAccessTokenMapper;
import com.example.janche.oauth.domain.OauthAccessToken;
import com.example.janche.oauth.service.OauthAccessTokenService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
* @author lirong
* @Description: // TODO 为类添加注释
* @date 2019-03-21 10:38:28
*/
@Slf4j
@Service
@Transactional
public class OauthAccessTokenServiceImpl extends AbstractService<OauthAccessToken> implements OauthAccessTokenService {
    @Resource
    private OauthAccessTokenMapper oauthAccessTokenMapper;

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    @Override
    public List<OauthAccessToken> list(PageParam pageParam, String query) {
        Example example = new Example(OauthAccessToken.class);
        //TODO 设置查询字段
        //example.or().andLike("name", "%"+query+"%");
        //example.or().andLike("code", "%"+query+"%");

        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(), pageParam.getOrderBy());
        return oauthAccessTokenMapper.selectByExample(example);
    }
}
