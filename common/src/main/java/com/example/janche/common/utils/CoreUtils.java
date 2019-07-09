package com.example.janche.common.utils;

import com.example.janche.common.exception.CustomException;
import com.example.janche.common.utils.date.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author lirong
 * @version v1.0
 * @since 2018/4/3 15:30
 */
@Slf4j
public class CoreUtils {

    public static final char UNDERLINE = '_';

    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        log.debug("UUID {}", s);
        return s;
    }

    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }

    public static String getNowTime() {
        return (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(Calendar.getInstance().getTime());
    }

    /**
     * 驼峰字符串转下划线
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 排序类型适配器 方法
     *
     * @param sortOrder
     * @return "" || "ASC" || "DESC"
     */
    public static String sortOrderAdapter(String sortOrder) {
        if (sortOrder == null || "".equals(sortOrder.trim())) {
            return "";
        }
        sortOrder = sortOrder.trim();
        sortOrder = sortOrder.toUpperCase();
        switch (sortOrder) {
            case "ASCEND":
                return "ASC";
            case "ASC":
                return "ASC";
            case "DESCEND":
                return "DESC";
            case "DESC":
                return "DESC";
            default:
                return "";
        }
    }

    /**
     * @desc sortFiled:age, gmtEtl, gmtCreate; sortOrder:asc, desc (排序方式可比排序个数少，默认为asc，用逗号分隔)
     * @param sortField
     * @param sortOrder
     * @param isMultiple 是否多个字段同时排序
     * @return
     */
    public static String getOrderBy(String sortField, String sortOrder, Boolean isMultiple) {
        if (!isMultiple){
            return CoreUtils.getOrderBy(sortField, sortOrder);
        }

        String[] fileds = sortField.split(",");
        String[] orders = sortOrder.split(",");
        StringBuilder sb = new StringBuilder();
        if(fileds.length >= orders.length){
            for (int i = 0; i < fileds.length; i++) {
                String field = CoreUtils.camelToUnderline(fileds[i]);
                if (field != ""){
                    String order;
                    if (i > orders.length-1){
                        order = CoreUtils.sortOrderAdapter("asc");
                    }else {
                        order = CoreUtils.sortOrderAdapter(orders[i]);
                    }
                    sb.append(field + " " + order);
                    sb.append(", ");
                }
            }
        }else{
            throw new CustomException(107, "请输入合理的排序字段个数");
        }
        return sb.substring(0, sb.length() - 2);
    }


    public static String getOrderBy(String sortField, String sortOrder) {
        String field = CoreUtils.camelToUnderline(sortField);
        if (field != "") {
            String order = sortOrderAdapter(sortOrder);
            return (field + " " + order);
        }
        return "";
    }


    /**
     * 根据出生日期返回年龄
     * @param birthDay
     * @return
     */
    public static String getAge(Date birthDay){
        int days = DateUtils.getIntervalDays(new Date(),birthDay);
        String age = "";
        if(days < 730 && days > 60){
            age = days/30+"月";
        }else if(days < 61){
            age = days+"天";
        }else{
            age = days/365+"岁";
        }
        return age;
    }

    /**
     * 将数字转为对应的操作类型
     * @param operation
     * @return
     */
    public static String getOperation(Integer operation){
        switch (operation){
            case 1: return "新增";
            case 2: return "删除";
            case 3: return "修改";
            case 4: return "查看";
            case 5: return "登录";
            case 6: return "登出";
            case 7: return "导入";
            case 8: return "导出";
            default: return "未说明接口";
        }
    }

    /**
     * 将对应的操作类型转为数字
     * @param operation
     * @return
     */
    public static Integer getOperation(String operation){
        switch (operation){
            case "新增": return 1;
            case "删除": return 2;
            case "修改": return 3;
            case "查看": return 4;
            case "登录": return 5;
            case "登出": return 6;
            case "导入": return 7;
            case "导出": return 8;
            case "下载": return 9;
            default: return 0;
        }
    }


    /**
     * 将数字转为对应的模块
     * @param type
     * @return
     */
    public static String getModule(Integer type){
        switch (type){
            case 1: return "首页";
            case 2: return "办公系统";
            case 3: return "办案系统";
            case 4: return "视频录像";
            case 5: return "基础管理";
            case 6: return "报警管理";
            case 7: return "系统管理";
            case 8: return "运维管理";
            default: return "未指定模块";
        }
    }

}
