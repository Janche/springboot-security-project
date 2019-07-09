package com.example.janche.user.service.impl;

import com.example.janche.common.core.AbstractService;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.user.dao.MenuRightMapper;
import com.example.janche.user.dao.RoleMapper;
import com.example.janche.user.domain.MenuRight;
import com.example.janche.user.domain.Role;
import com.example.janche.user.domain.User;
import com.example.janche.user.dto.MenuRightDTO;
import com.example.janche.user.dto.menuRightWeb.FristMenuDTO;
import com.example.janche.user.dto.menuRightWeb.MeunRightWebDTO;
import com.example.janche.user.dto.menuRightWeb.SecondMenuDTO;
import com.example.janche.user.service.MenuRightService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author CodeGenerator
 * @date 2018-11-01 09:34:51
 */
@Slf4j
@Service
@Transactional
public class MenuRightServiceImpl extends AbstractService<MenuRight> implements MenuRightService {
    @Resource
    private MenuRightMapper menuRightMapper;

    @Resource
    private RoleMapper roleMapper;



    @Override
    public List<MenuRight> findByRoles(Set<Role> roles) {
        return null;
    }

    @Override
    public List<MenuRight> findByUser(User user) {
        return null;
    }

    @Override
    public List<MenuRight> queryList(MenuRightDTO menuRightDTO) {
        return null;
    }

    @Override
    public List<MenuRight> queryListByUrl(String url) {
        Example example = new Example(MenuRight.class);
        example.and().andLike("url", url+"%");
        return  menuRightMapper.selectByExample(example);
    }

    @Override
    public List<MenuRightDTO> queryList(MenuRightDTO menuRightDTO, List<Long> roleIds, PageParam pageParam) {
        return null;
    }

    /**
     * 搜索所有权限菜单节点、
     */
    @Override
    public ArrayList<MenuRight> findAllMenuRight() {
        return menuRightMapper.findAllMenuRight();
    }

    /**
     * 根据URL获取所有的权限
     */
    @Override
    public List<MenuRight> findAllMenuRightByUrl(String url){
        return menuRightMapper.findAllMenuRightByUrl(url);
    }

    @Override
    public List<MenuRight> list(PageParam pageParam, String query) {
        Example example = new Example(MenuRight.class);
//        example.or().andLike("code", "%"+query+"%");
//        example.or().andLike("deviceId", "%"+query+"%");

        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(), pageParam.getOrderBy());
        return menuRightMapper.selectByExample(example);
    }



    //根据id查询权限菜单节点
    @Override
    public MenuRight findMenuRightById(long id) {
        return menuRightMapper.findMenuRightById(id);
    }

   //添加一个权限节点
    @Override
    public void addMenuRight(MenuRight menuRight) {
        menuRightMapper.addMenuRight(menuRight);
    }



    //删除单个权限菜单节点
    @Override
    public void deleteMenuRight(long id) {
        menuRightMapper.deleteMenuRight(id);
    }
   //修改单个权限菜单节点
    @Override
    public void updateMenuRight(MenuRight menuRight) {
        menuRightMapper.updateMenuRight(menuRight);
    }

    //通过角色查询权限
    @Override
    public MeunRightWebDTO findListByRoleId(long id) {

        List<MenuRight> list=menuRightMapper.findListByRoleId(id);
        return this.getMenuTree(list);

    }

    //获取当前用户权限菜单节点书
    @Override
    public MeunRightWebDTO FindMenuRightWebDtO(long id) {
        List<MenuRight> list=menuRightMapper.findListByUserId(id);
        return this.getMenuTree(list);

    }
    //获取所有权限菜单节点树
    public MeunRightWebDTO FindAllMenuRightWebDtO() {
        List<MenuRight> list=menuRightMapper.findAllMenuRight();
        return this.getMenuTree(list);
    }

    //生成权限树方法
    public MeunRightWebDTO getMenuTree(List<MenuRight> list){
        //封装一层
        MeunRightWebDTO meunRightWebDTO=new MeunRightWebDTO();
        List<FristMenuDTO> firstList=new ArrayList<>();
        List<SecondMenuDTO> secondList=new ArrayList<>();
        List<MenuRight>    thirdList=new ArrayList<>();
        for ( MenuRight menu:list) {
            if(menu.getGrades()==1){
                firstList.add(FristMenuDTO.menuRight2MenuRightDTO(menu));
            }
            if(menu.getGrades()==2){
                secondList.add(SecondMenuDTO.menuRight2MenuRightDTO(menu));
            }
            if(menu.getGrades()==3){
                thirdList.add(menu);
            }
        }
        meunRightWebDTO.setFristMenus(firstList);

        //封装二层
        List<FristMenuDTO> firstMenuList=meunRightWebDTO.getFristMenus();
        for (FristMenuDTO first: firstMenuList) {
            List<SecondMenuDTO> secondMenuDTOS=new ArrayList<>();
            for (SecondMenuDTO   second :  secondList) {
                if(first.getId()==second.getParentId()){
                    secondMenuDTOS.add(second);
                }
            }
            Integer type=1;
            //查看权限id集合
            List<Long> lookids1=new ArrayList<>();
            //操作权限id集合
            List<Long> handleIds1=new ArrayList<>();
            //直接链接三级
            for (MenuRight   third :  thirdList) {
                if(third.getParentId()==first.getId()){
                  if(third.getMethod().equals("get")){
                        lookids1.add(third.getId());
                    }else{
                        type=2;
                        handleIds1.add(third.getId());
                    }
                }
            }
            first.setType(type);
            first.setFixedType(type);
            first.setLookids(lookids1);
            first.setHandleIds(handleIds1);
            first.setSecondMenus(secondMenuDTOS);
        }
        meunRightWebDTO.setFristMenus(firstMenuList);

        //封装三层
        firstMenuList=meunRightWebDTO.getFristMenus();
        for (FristMenuDTO first: firstMenuList) {
            List<SecondMenuDTO> secondMenuDTOS=first.getSecondMenus();
            if(secondMenuDTOS!=null&&secondMenuDTOS.size()!=0){
                for (SecondMenuDTO   second :  secondMenuDTOS) {
                    //查看权限id集合
                    List<Long> lookids=new ArrayList<>();
                    //操作权限id集合
                    List<Long> handleIds=new ArrayList<>();
                    Integer type2=1;
                    for (MenuRight   third :  thirdList) {
                        if(second.getId()==third.getParentId()){
                            if(third.getMethod().equals("get")){
                                lookids.add(third.getId());
                            }else{
                                type2=2;
                                handleIds.add(third.getId());
                            }
                        }

                    }
                    second.setType(type2);
                    second.setFixedType(type2);
                    second.setLookids(lookids);
                    second.setHandleIds(handleIds);
                }
            }

        }

        meunRightWebDTO.setFristMenus(firstMenuList);

        return meunRightWebDTO;
    }


}
