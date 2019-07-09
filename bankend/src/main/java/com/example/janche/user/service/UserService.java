package com.example.janche.user.service;

import com.example.janche.common.core.Service;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.user.domain.User;
import com.example.janche.user.dto.UserDTO;
import com.example.janche.user.dto.UserInputDTO;
import com.example.janche.user.dto.UserOutpDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
*
* @author CodeGenerator
* @date 2018-11-01 09:59:47
*/
public interface UserService extends Service<User> {

    /**
     * 根据分页、排序信息和检索条件查询 数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    List<User> list(PageParam pageParam, String query);

    /**
     * 查询所有用户信息
     *
     * @return
     */
    public List<User> queryList();

    /**
     * 根据登录用户名查找用户信息
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据用户名获取用户的相关权限信息
     * @param username
     * @return
     */
    UserDTO getRolesByUsername(String username);

    /**
     * 根据用户名和密码查找用户
     * username 用户名
     * password 密码
     * @return Integer在数据库数量
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
     * @param user
     */
    public void addUser(User user);

    /**
     * 修改用户信息
     * @param user
     */
    public void updateUser(User user);

    /**
     * 修改用户密码
     * @param user
     */
    public void updateUserPwd(User user);

    /**
     *  删除用户
     *  @param id 用户id
     */
    public void deleteUserById(Long id);

    /**
     * 根据区域获取所有用户信息集合。（分页，返回用户加角色）
     * @param inputDTO  查询条件
     * @param pageParam 分页对象
     * @return  List<UserDTO> userDTO信息集合
     */
    public List<UserOutpDTO> findListByArea(UserInputDTO inputDTO, PageParam pageParam);

    /**
     * 查询用户详细信息（角色，权限集合，区域，业务组）
     * id 用户id
     * @return UserDTO用户详细信息
     */
    public UserDTO findUserMessage(Long id);


    /**
     * 导出用户列表
     * @param pageParam
     * @param inputDTO 查询条件
     */
    ResponseEntity<byte[]> exportDeviceList(PageParam pageParam, UserInputDTO inputDTO);

}
