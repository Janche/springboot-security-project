package com.example.janche.user.dao;

import com.example.janche.common.core.Mapper;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.user.domain.User;
import com.example.janche.user.dto.UserDTO;
import com.example.janche.user.dto.UserInputDTO;
import com.example.janche.user.dto.UserOutpDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *   用户的dao接口
 */
public interface UserMapper extends Mapper<User> {

    /**
     * 根据分页、排序信息和检索条件查询 数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    List<User> list(PageParam pageParam, String query);

    /**
     * 根据登录用户名查找用户信息
     * @param username
     * @return User 该名字用户信息
     */
    public User findByUserName(String username);

    UserDTO getRolesByUsername(@Param("username") String username);

    /**
     * 根据用户名和密码查找用户
     * username 用户名
     * password 密码
     * @return User
     */
    public User findByNameAndPwd(String username,String password);

    /**
     * 根据用户名查询是存在
     * @param username 用户名
     * @return Integer 该名字用户数量
     */
    public Integer findCountByName(String username);

    /**
     * 根据用户编号查询是存在
     * @param userNum 用户编号
     * @return Integer 该编号数量
     */
    public Integer findCountByUserNum(String userNum);

    /**
     * 添加用户
     * @param user 用户对象
     */
    public void addUser(User user);

    /**
     * 修改用户
     * @param user 用户对象
     */
    public void updateUser(User user);

    /**
     * 修改用户密码
     * @param user 用户对象
     */
    public void updateUserPwd(User user);

    /**
     *  通过id删除用户
     *  @param id 用户id
     */
    public void deleteUserById(Long id);

    /**
     * 根据区域获取所有用户信息集合。（分页，返回用户加角色）
     * @param inputDTO 查询条件
     * @return  List<UserDTO> userDTO信息集合
     */
    public List<UserOutpDTO> findListByArea(UserInputDTO inputDTO);

    /**
     * 查询用户详细信息（角色，权限集合，区域，业务组）
     * id 用户id
     * @return UserDTO用户详细信息
     */
    public UserDTO findUserMessage(Long id);

}