package com.example.janche.user.dao;

import com.example.janche.common.core.Mapper;
import com.example.janche.user.domain.MenuRight;
import com.example.janche.user.dto.MenuDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuRightMapper extends Mapper<MenuRight> {
    List<MenuDTO> getAllMenus();

    List<MenuRight> getUserMenus(@Param("userId") Long userId);
}