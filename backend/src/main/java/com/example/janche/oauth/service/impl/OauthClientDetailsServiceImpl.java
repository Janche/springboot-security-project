package com.example.janche.oauth.service.impl;

import com.example.janche.common.core.AbstractService;
import com.example.janche.common.exception.CustomException;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.common.restResult.ResultCode;
import com.example.janche.oauth.dao.OauthAccessTokenMapper;
import com.example.janche.oauth.dao.OauthClientDetailsMapper;
import com.example.janche.oauth.dao.OauthRefreshTokenMapper;
import com.example.janche.oauth.domain.OauthAccessToken;
import com.example.janche.oauth.domain.OauthClientDetails;
import com.example.janche.oauth.domain.OauthRefreshToken;
import com.example.janche.oauth.dto.OauthClientDetailsDTO;
import com.example.janche.oauth.service.OauthClientDetailsService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author lirong
* @Description: // TODO 为类添加注释
* @date 2019-03-21 10:38:28
*/
@Slf4j
@Service
@Transactional
public class OauthClientDetailsServiceImpl extends AbstractService<OauthClientDetails> implements OauthClientDetailsService {
    @Resource
    private OauthClientDetailsMapper oauthClientDetailsMapper;
    @Resource
    private OauthAccessTokenMapper oauthAccessTokenMapper;
    @Resource
    private OauthRefreshTokenMapper oauthRefreshTokenMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    @Override
    public List<OauthClientDetails> list(PageParam pageParam, String query) {
        Example example = new Example(OauthClientDetails.class);
        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(), pageParam.getOrderBy());
        return oauthClientDetailsMapper.selectByExample(example);
    }

    /**
     * 获取所有的客户端的信息
     *
     * @return
     */
    @Override
    public List<OauthClientDetailsDTO> getAllClientDetails() {
        List<OauthClientDetails> oauthClientDetails = oauthClientDetailsMapper.selectAll();
        return OauthClientDetailsDTO.details2DTOs(oauthClientDetails);
    }

    /**
     * 新增oauthclient数据
     *
     * @param dto
     * @return
     */
    @Override
    public void addClientDetails(OauthClientDetailsDTO dto) {
        String clientId = dto.getClientId();
        String clientSecret = dto.getClientSecret();
        String grantTypes = dto.getAuthorizedGrantTypes();

        if (StringUtils.isEmpty(clientId) || StringUtils.isEmpty(clientSecret) || StringUtils.isEmpty(grantTypes)){
            throw new CustomException(ResultCode.MISSING_CLIENT_INFO);
        }
        OauthClientDetails clientDetails = this.findBy("clientId", clientId);
        if (clientDetails != null) {
            throw new CustomException(ResultCode.CLIENT_ID_ALREADY_EXIST);
        }

        OauthClientDetails details = new OauthClientDetails();
        BeanUtils.copyProperties(dto, details);
        // 对密码进行加密

        String encode = passwordEncoder.encode(clientSecret);
        details.setClientSecret(encode);

        oauthClientDetailsMapper.insert(details);
    }

    /**
     * 删除此客户端的所有信息
     *
     * @param oldClientId
     */
    @Override
    public void deleteClient(String oldClientId) {
        oauthClientDetailsMapper.deleteByPrimaryKey(oldClientId);
        Example example = new Example(OauthAccessToken.class);
        example.createCriteria().andEqualTo("clientId", oldClientId);
        List<OauthAccessToken> oauthAccessTokens = oauthAccessTokenMapper.selectByExample(example);
        List<String> list = new ArrayList<>();
        oauthAccessTokens.stream().forEach(e -> list.add(e.getRefreshToken()));
        oauthAccessTokenMapper.deleteByExample(example);

        // 删除 refreshToken
        if (list.size() > 0) {
            example = new Example(OauthRefreshToken.class);
            example.createCriteria().andIn("tokenId", list);
            oauthRefreshTokenMapper.deleteByExample(example);
        }
        // todo oauth 的授权码code没有删除
    }
}
