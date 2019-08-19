package com.example.janche.user.service.impl;

import com.example.janche.common.core.AbstractService;
import com.example.janche.common.exception.CustomException;
import com.example.janche.common.model.Constant;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.common.restResult.ResultCode;
import com.example.janche.user.dao.MenuRightMapper;
import com.example.janche.user.dao.UserAndRoleMapper;
import com.example.janche.user.dao.UserMapper;
import com.example.janche.user.domain.User;
import com.example.janche.user.domain.UserAndRole;
import com.example.janche.user.dto.MenuDTO;
import com.example.janche.user.dto.user.*;
import com.example.janche.user.service.UserService;
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
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserAndRoleMapper userAndRoleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<User> list(PageParam pageParam, String query) {
        Example example = new Example(User.class);
        example.or().andLike("username", "%"+query+"%");
        example.or().andLike("actualName", "%"+query+"%");
        example.or().andLike("sex", "%"+query+"%");

        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(), pageParam.getOrderBy());
        return userMapper.selectByExample(example);
    }

    /**
     * 下拉框用户列表
     *
     * @return
     */
    @Override
    public List<User> listAll() {
        Example example = new Example(User.class);
        example.and().andEqualTo("status", Constant.STATUS_ENABLE);
        return userMapper.selectByExample(example);
    }

    @Override
    public List<User> findAll(PageParam pageParam, UserConditionDTO dto) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(), pageParam.getOrderBy());
        Example example = new Example(User.class);
        if (StringUtils.isNotEmpty(dto.getUsername())){
            example.and().andLike("username", "%"+dto.getUsername()+"%");
        }
        if (StringUtils.isNotEmpty(dto.getActualName())){
            example.and().andLike("actualName", "%"+dto.getActualName()+"%");
        }
        if (null != dto.getStatus()){
            example.and().andEqualTo("status", dto.getStatus());
        }
        return userMapper.selectByExample(example);
    }

    /**
     * 根据用户名获取权限信息
     * @param username
     * @return
     */
    @Override
    public UserDTO getRolesByUsername(String username) {
        return userMapper.getRolesByUsername(username);
    }

    /**
     * 添加用户
     * @param inputDTO
     */
    @Override
    public void addUser(UserInputDTO inputDTO) {
        User user = new User();
        BeanUtils.copyProperties(inputDTO, user);
        user.setPassword(passwordEncoder.encode(inputDTO.getPassword()));
        // 默认启用
        user.setStatus(Constant.STATUS_ENABLE);
        user.setCreateTime(new Date());
        user.setModifyTime(new Date());
        userMapper.insert(user);
        // 增加用户角色关联表
        String roleIds = inputDTO.getRoleIds();
        if (StringUtils.isNotEmpty(roleIds)){
            insertUserAndRoles(roleIds, user);
        }
    }

    /**
     * 删除用户
     * @param id
     */
    @Override
    public void deleteUser(Long id) {
        // 删除用户和角色的关联
        Example example = new Example(UserAndRole.class);
        example.and().andEqualTo("userId", id);
        userAndRoleMapper.deleteByExample(example);
        // 删除用户
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除用户
     * @param ids
     */
    @Override
    public void deleteUser(String ids) {
        List<String> Ids = Arrays.stream(ids.split(",")).collect(Collectors.toList());
        // 删除用户和角色的关联
        Example example = new Example(UserAndRole.class);
        example.and().andIn("userId", Ids);
        userAndRoleMapper.deleteByExample(example);
        // 删除用户
        userMapper.deleteByIds(ids);
    }

    /**
     * 批量冻结用户
     * @param ids
     */
    @Override
    public void frozeUser(String ids, Integer status) {
        List<String> Ids = Arrays.stream(ids.split(",")).collect(Collectors.toList());
        Example example = new Example(User.class);
        example.and().andIn("id", Ids);
        List<User> users = userMapper.selectByExample(example);
        for (User user :users) {
            user.setStatus(status);
            user.setModifyTime(new Date());
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    /**
     * 获取用户明细
     * @param id
     * @return
     */
    @Override
    public UserOutpDTO findOne(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (null == user){
            throw new CustomException(ResultCode.USER_NOT_EXIST);
        }
        Example example = new Example(UserAndRole.class);
        example.and().andEqualTo("userId", id);
        List<UserAndRole> userAndRoles = userAndRoleMapper.selectByExample(example);

        Set<Long> roleSet = new HashSet<>();
        userAndRoles.stream().forEach(e ->
            roleSet.add(e.getRoleId())
        );
        UserOutpDTO outpDTO = new UserOutpDTO();
        BeanUtils.copyProperties(user, outpDTO);
        outpDTO.setRoleIds(StringUtils.join(roleSet, ","));
        return outpDTO;
    }

    /**
     * 修改用户信息
     * @param inputDTO
     */
    @Override
    public void updateUser(UserInputDTO inputDTO) {
        User user = new User();
        BeanUtils.copyProperties(inputDTO, user);
        user.setModifyTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);

        // 删除用户角色关系
        Example example = new Example(UserAndRole.class);
        example.and().andEqualTo("userId", user.getId());
        userAndRoleMapper.deleteByExample(example);
        // 增加用户角色关联表
        String roleIds = inputDTO.getRoleIds();
        if (StringUtils.isNotEmpty(roleIds)){
            insertUserAndRoles(roleIds, user);
        }
    }

    /**
     * 批量插入用户角色关联记录
     * @param roleIds
     * @param user
     */
    private void insertUserAndRoles(String roleIds, User user) {
        List<UserAndRole> list = new ArrayList<>();
        Arrays.stream(roleIds.split(",")).forEach(e -> {
            UserAndRole build = UserAndRole.builder()
                    .userId(user.getId())
                    .roleId(Long.parseLong(e))
                    .build();
            list.add(build);
        });
        userAndRoleMapper.insertList(list);
    }


    /**
     * 修改用户密码
     * @param dto
     */
    @Override
    public void updateUserPwd(UserPwdDTO dto) {
        User user = userMapper.selectByPrimaryKey(dto.getId());
        boolean matches = passwordEncoder.matches(dto.getPassword(), user.getPassword());
        if (!matches){
            throw new CustomException(ResultCode.OLD_PASSWORD_ERROR);
        }
        String encodePwd = passwordEncoder.encode(dto.getNewPassword());
        user.setPassword(encodePwd);
        user.setModifyTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 重置用户密码
     * @param userId
     */
    @Override
    public void resetUserPwd(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (null != user){
            String encodePwd = passwordEncoder.encode(Constant.RESET_PASSWORD);
            user.setPassword(encodePwd);
            user.setModifyTime(new Date());
            userMapper.updateByPrimaryKeySelective(user);
        }else {
            throw new CustomException(ResultCode.RESET_PASSWORD_ERROR);
        }
    }

    /**
     * 判断用户名密码是否正确
     * @param username
     * @param password
     * @return
     */
    private Boolean checkUser(String username, String password){
        User user = this.findBy("username", username);
        return passwordEncoder.matches(password, user.getPassword());
    }

    /**
     * 获取用户权限
     * @return
     */
    @Override
    public List<MenuDTO> getUserMenus(Long userId) {
        // return menuRightMapper.getMenusByUserId(userId);
        return null;
    }
}
