package com.example.janche.oauth.service.impl;

import com.example.janche.common.core.AbstractService;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.oauth.dao.OauthRefreshTokenMapper;
import com.example.janche.oauth.domain.OauthRefreshToken;
import com.example.janche.oauth.service.OauthRefreshTokenService;
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
public class OauthRefreshTokenServiceImpl extends AbstractService<OauthRefreshToken> implements OauthRefreshTokenService {
    @Resource
    private OauthRefreshTokenMapper oauthRefreshTokenMapper;

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    @Override
    public List<OauthRefreshToken> list(PageParam pageParam, String query) {
        Example example = new Example(OauthRefreshToken.class);
        //TODO 设置查询字段
        //example.or().andLike("name", "%"+query+"%");
        //example.or().andLike("code", "%"+query+"%");

        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(), pageParam.getOrderBy());
        return oauthRefreshTokenMapper.selectByExample(example);
    }
}
