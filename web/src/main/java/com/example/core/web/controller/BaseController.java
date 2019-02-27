package com.example.core.web.controller;

import com.example.core.common.config.ApplicationConfig;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public class BaseController {
    @Autowired
    @Getter
    public ApplicationConfig applicationConfig;


    public Pageable getDefaultPage() {
        if (StringUtils.isEmpty(this.applicationConfig.getSortcolumn()) || this.applicationConfig.getSorttype() == null) {
            return new PageRequest(0, this.applicationConfig.getPagesize(), Sort.unsorted());
        }
        Sort sort = new Sort(this.applicationConfig.getSorttype().getSortType(), this.applicationConfig.getSortcolumn());
        return new PageRequest(0, this.applicationConfig.getPagesize(), sort);
    }

}
