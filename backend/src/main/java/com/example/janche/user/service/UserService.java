package com.example.janche.user.service;

import com.example.janche.common.core.Service;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.user.domain.User;
import com.example.janche.user.dto.MenuDTO;
import com.example.janche.user.dto.user.*;

import java.util.List;

/**
*
* @author lirong
* @date 2018-11-01 09:59:47
*/
public interface UserService extends Service<User> {

    /**
     * 添加用户
     * @param inputDTO
     */
    void addUser(UserInputDTO inputDTO);

    /**
     * 修改用户信息
     * @param inputDTO
     */
    void updateUser(UserInputDTO inputDTO);

    /**
     *  删除用户
     *  @param id 用户id
     */
    void deleteUser(Long id);

    /**
     * 根据分页、排序信息和检索条件查询 数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    List<User> list(PageParam pageParam, String query);

    /**
     * 下拉框用户列表
     * @return
     */
    List<User> listAll();

    /**
     * 根据用户名获取用户的相关权限信息
     * @param username
     * @return
     */
    UserDTO getRolesByUsername(String username);

    /**
     * 分页条件查询所有用户
     * @param pageParam
     * @param dto
     * @return
     */
    List<User> findAll(PageParam pageParam, UserConditionDTO dto);

    /**
     * 修改用户密码
     * @param dto
     */
    void updateUserPwd(UserPwdDTO dto);

    /**
     * 重置用户密码
     * @param userId
     */
    void resetUserPwd(Long userId);

    /**
     * 批量删除用户
     * @param ids
     */
    void deleteUser(String ids);

    /**
     * 批量冻结用户
     * @param ids
     * @param status
     */
    void frozeUser(String ids, Integer status);

    /**
     * 获取用户明细
     * @param id
     * @return
     */
    UserOutpDTO findOne(Long id);

    /**
     * 获取用户所有的权限
     * @return
     */
    List<MenuDTO> getUserMenus(Long userId);
}
