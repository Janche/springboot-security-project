package com.example.janche.user.service.impl;

import com.example.janche.common.config.ApplicationConfig;
import com.example.janche.common.core.AbstractService;
import com.example.janche.common.util.PoiUtils;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.user.dao.UserMapper;
import com.example.janche.user.domain.User;
import com.example.janche.user.dto.UserDTO;
import com.example.janche.user.dto.UserInputDTO;
import com.example.janche.user.dto.UserOutpDTO;
import com.example.janche.user.service.UserService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private ApplicationConfig applicationConfig;

    @Override
    public List<User> queryList() {

        return null;
    }

    //根据用户名查询是存在
    @Override
    public Integer findCountByName(String username) {
        return userMapper.findCountByName(username);
    }

    @Override
    public List<User> list(PageParam pageParam, String query) {
        Example example = new Example(User.class);
//        example.or().andLike("code", "%"+query+"%");
//        example.or().andLike("deviceId", "%"+query+"%");

        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(), pageParam.getOrderBy());
        return userMapper.selectByExample(example);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUserName(username);
    }


    @Override
    public UserDTO getRolesByUsername(String username) {
        return userMapper.getRolesByUsername(username);
    }

    //根据用户名和密码查找用户
    @Override
    public User findByNameAndPwd(String username, String password) {
        return userMapper.findByNameAndPwd(username,password);
    }



    //添加用户
    @Override
    public void addUser(User user) {
        user.setCreateTime(new Date());
        user.setModifyTime(new Date());
        userMapper.addUser(user);
    }


 //    根据用户编号查询是存在
    @Override
    public Integer findCountByUserNum(String userNum) {
        return userMapper.findCountByUserNum(userNum);
    }



    //修改用户信息
    @Override
    public void updateUser(User user) {
        user.setModifyTime(new Date());
        userMapper.updateUser(user);
    }

    //修改用户密码
    @Override
    public void updateUserPwd(User user) {
        user.setModifyTime(new Date());
        userMapper.updateUserPwd(user);
    }

    //删除用户
    @Override
    public void deleteUserById(Long id) {
        userMapper.deleteUserById(id);
    }


   //查询用户详细信息（用户，角色，权限集合，区域，业务组）
    @Override
    public UserDTO findUserMessage(Long id) {
        return userMapper.findUserMessage(id);
    }

    //根据区域获取所有用户信息集合。（分页，返回用户加角色）
    @Override
    public List<UserOutpDTO> findListByArea(UserInputDTO inputDTO, PageParam pageParam) {
       PageHelper.startPage(pageParam.getPage(), pageParam.getSize());
       return userMapper.findListByArea(inputDTO);
    }

    // 导出用户列表
    @Override
    public ResponseEntity<byte[]> exportDeviceList(PageParam pageParam,UserInputDTO inputDTO) {
        pageParam.setSize(0);  //查询全部
        List<UserOutpDTO> data = this.findListByArea(inputDTO,pageParam);

        Map<Integer, String> levelMap = applicationConfig.getUserlevel();
        Long userId=1L;
        for (UserOutpDTO dto : data) {
//            System.out.println(dto.getLevelId());
            dto.setId(userId);  //id顺序排列
            userId+=1L;
            dto.setLevelId(levelMap.get(dto.getLevelId()));
            dto.setState((dto.getState().equals(1))?"允许":"禁止");
        }

        String[] headers = {"序号","用户编号","姓名","账号","职位","电话","角色","状态"};


        PoiUtils poiUtils = new PoiUtils("用户列表", "导出模板.xls");
        poiUtils.setHeaders(headers, "用户列表");

        // 为excel表生成数据
        poiUtils.fillDataAndStyle(data, 2);

        // 将内容返回响应
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            poiUtils.getWorkbook().write(bos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 文件名
        String filename = "用户列表";
        try {
            filename = new String(filename.getBytes("gbk"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            log.warn("不支持编码格式");
            e.printStackTrace();

        }
        // 设置http响应头
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Disposition", "attachment;filename=" + filename + ".xls");

        return new ResponseEntity<byte[]>(bos.toByteArray(), header, HttpStatus.CREATED);
    }

}
