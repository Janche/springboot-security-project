package com.cebon.cdjcy.common.model;

/**
 * 常量
 */
public interface  Constant {
    int INDEX_ONE = 1;
    int INDEX_ZERO = 0;
    int INDEX_NEGATIVE_ONE = -1;
    int INDEX_TWO = 2;
    int INDEX_THREE = 3;

    /**
     * 时间格式化
     */
    String DATE_FORMATTER_TIME = "YYYY-MM-dd HH:mm:ss";
    String DATE_FORMATTER_DATE = "YYYY-MM-dd";

      /**
       * 一个房间拥有的设备最大总数
       */
      int ROOM_DEVICE_MAX_NUM=5;

      //=========================设备状态=============//
    /**
     * 设备未配置
     */
    int DEV_NO_CONFIG= 1;
    /**
     * 设备已配置待生效
     */
    int DEV_CONFIG_NO_EFFECTIVE= 2;

    /**
     * 设备在线
     */
    int DEV_ONLINE = 3;

    //==============================设备类型
    /**
     * IPC
     */
    int DEV_IPC = 1;
    /**
     * NVR
     */
    int DEV_NVR = 2;

    //=============================案件状态
    /**
     * 完结案件
     */
    int END_CASE=3;

    /**
     * 超级管理员
     */
    String ROLE_ADMIN="ROLE_ADMIN";
    /**
     * 系统管理员
     */
    String ROLE_SYSTEM_MANAGER="ROLE_SYSTEM_MANAGER";
    /**
     * 表名
     */
    String LAWCASE = "lawcase";
    String OFFENDER = "offender";
    String ROOM = "room";
    String AREA = "area";



    /**
     *=======================设备相关==================
     */

    /**
     * 设备状态：正常、离线、故障
     */
    int DEV_NORMAL = 1;
    int DEV_OFFLINE = 0;
    int DEV_ERROR = 2;

    /**
     * 设备厂家：海康，大华
     */
    int DEV_FACTORY_HAIKANG = 1;
    int DEV_FACTORY_DAHUA = 2;

    /**
     * 设备类型：IPC，NVR
     */
    int DEV_TYPE_IPC = 1;
    int DEV_TYPE_NVR = 2;

    /**
     * 设备分辨率：1080P，720P
     */
    String DEV_RESOLUTION_1080 = "1080P";
    String DEV_RESOLUTION_720 = "720P";

    /**
     * 设备编码
     */
    String DEV_CODE_H264 = "H264";
    String DEV_CODE_H265 = "H265";

    /**
     * 设备帧率:24，25
     */
    int DEV_FRAMERATE24 = 24;
    int DEV_FRAMERATE25 = 25;

    /**
     * 设备码流：固定码流、可变码流
     */
    String DEV_VIDEO_STREAM_UNCHANGE = "固定码流";
    String DEV_VIDEO_STREAM_CHANGE = "可变码流";

    /**
     * 设备码率：1024KB，2048KB
     */
    String DEV_VIDEO_BITRATE_1024 = "1024KB";
    String DEV_VIDEO_BITRATE_2048 = "2048KB";

    /**
     * 公共状态： 启用、未启用
     */
    int STATUS_ENABLE = 1;
    int STATUS_DISABLE = 0;

}
