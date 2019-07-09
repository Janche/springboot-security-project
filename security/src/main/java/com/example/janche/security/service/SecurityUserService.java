package com.example.janche.security.service;


import com.example.janche.security.authentication.SecurityUser;
import com.example.janche.user.dao.MenuRightMapper;
import com.example.janche.user.dao.RoleMapper;
import com.example.janche.user.dto.LoginUserDTO;
import com.example.janche.user.dto.UserDTO;
import com.example.janche.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("securityUserService")
public class SecurityUserService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuRightMapper menuRightMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDTO userDTO = userService.getRolesByUsername(username);
        // 默认用户ID为1的为管理员
        if (null != userDTO){
            if(1L == userDTO.getId()) {
                // this.getAdminPermission(userDTO);
            }
            SecurityUser securityUser = new SecurityUser(LoginUserDTO.user2LoginUserDTO(userDTO));
            return securityUser;
        } else {
            throw new UsernameNotFoundException(username + " 用户不存在!");
        }
    }


    /**
     * 为管理员赋所有权限
     * @param userDTO
     * @return
     */
    // private UserDTO getAdminPermission(UserDTO userDTO) {
    //     List<Role> roles = roleMapper.selectAll();
    //     List<MenuRight> menuRights = menuRightMapper.selectAll();
    //     List<ServiceGroup> serviceGroups = serviceGroupMapper.selectAll();
    //     userDTO.setRoleList(roles);
    //     userDTO.setMenuRightList(menuRights);
    //     userDTO.setGroupList(serviceGroups);
    //     return userDTO;
    // }
}
