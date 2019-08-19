package com.example.janche.common.restResult;

/**
 * @author lirong
 * @ClassName: ResultCode
 * @Description: 返回的状态码
 * @date 2018-12-03 9:26
 */

public enum ResultCode {

    /**
     * HTTP通信使用
     */
    SUCCESS(200, "SUCCESS"),

    ERROR(500, "服务器内部错误"),

    NOT_FOUND(404, "接口不存在"),

    FAIL(400, "接口异常,请联系管理员"),

    UNAUTHORIZED(401, "未认证（签名错误）"),


    /**
     * security
     */
    LOGIN_ERROR(-990, "登录失败"),

    OVER_MAX_LOGIN_NUM(-991, "当前在线人数过多，请稍后登录"),

    REFRESH_TOKEN_EXPIRED(-997, "用户 刷新令牌过期"),

    LIMITED_AUTHORITY(-1000, "权限不够"),

    UNLOGIN(-999, "用户未登录"),

    TOKEN_ILLEGAL(-996, "用户令牌不合法"),

    TOKEN_EXPIRED(-998, "用户 普通令牌过期"),

    IP_NOT_ALLOW(-980, "登录的IP不被允许"),

    /**
     * oauth2
     */
    MISSING_CLIENT_INFO(-1009, "客户端注册信息缺失"),

    CLIENT_ID_ALREADY_EXIST(-1010, "client_id已存在"),

    /**
     * 导入导出
     */
    DATA_IS_NULL(4901, "请选择数据导出"),

    IMPORT_FAIL(4902, "未知错误，导入失败"),



    /**
     * 用户管理
     */

    USER_FINDNOWUSER_FAIL(2004, "获取登录用户失败"),

    USER_NOT_EXIST(2005, "用户不存在"),

    OLD_PASSWORD_ERROR(2002, "原密码错误"),

    PASSWORD_ERROR(2003, "密码错误"),

    RESET_PASSWORD_ERROR(2004, "重置密码失败"),


    /**
     * 角色管理
     */
    ROLE_ADD_FAIL(7101, "新增角色失败"),

    ROLE_UPDATE_FAIL(7102, "修改角色失败"),

    ROLE_DELETE_FAIL(7103, "删除角色失败"),


    ;

    /**
     * 统计数据
     */

    public int code;

    public String message;

    ResultCode (int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
    // /**
    //  * 登录失败
    //  */
    // int LOGIN_ERROR = -990;
    // /**
    //  * 用户令牌不合法
    //  */
    // int TOKEN_ILLEGAL = -996;
    // /**
    //  * 用户 刷新令牌过期
    //  */
    // int REFRESH_TOKEN_EXPIRED = -997;
    // /**
    //  * 用户 普通令牌过期
    //  */
    // int TOKEN_EXPIRED = -998;


    // /**
    //  * 非自定义异常
    //  */
    // int NOT_CUSTOM_EXCEPTION = -2000;
    // /**
    //  * 非自定义运行时异常
    //  */
    // int NOT_CUSTOM_RUNTIME_EXCEPTION = -2001;
    //
    // /**
    //  * 区域管理----查找单元列表失败
    //  */
    // int FAIL_AREA_LIST_FIND = -1301;
    // /**
    //  * 区域管理----新增区域失败
    //  */
    // int FAIL_AREA_ADD = -1302;
    // /**
    //  * 区域管理----删除区域失败
    //  */
    // int FAIL_AREA_DELETE = -1303;
    // /**
    //  * 区域管理----更新区域失败
    //  */
    // int FAIL_AREA_UPATE = -1304;
    // /**
    //  * 区域管理----根据Id查询区域
    //  */
    // int FAIL_AREA_FIND_BY_ID = -1305;
    //
    // /**
    //  * 房间管理----查找房间列表失败
    //  */
    // int FAIL_ROOM_LIST_FIND = -1306;
    // /**
    //  * 房间管理----新增房间失败
    //  */
    // int FAIL_ROOM_ADD = -1307;
    // /**
    //  * 房间管理----删除房间失败
    //  */
    // int FAIL_ROOM_DELETE = -1308;
    // /**
    //  * 房间管理----更新房间失败
    //  */
    // int FAIL_ROOM_UPATE = -1309;
    // /**
    //  * 房间管理----根据Id查询房间
    //  */
    // int FAIL_ROOM_FIND_BY_ID = -1310;
    //
    //
    // /**
    //  * 设备管理----查找设备列表失败
    //  */
    // int FAIL_DEVICE_LIST_FIND = -1311;
    // /**
    //  * 设备管理----新增设备失败
    //  */
    // int FAIL_DEVICE_ADD = -1312;
    // /**
    //  * 设备管理----删除设备失败
    //  */
    // int FAIL_DEVICE_DELETE = -1313;
    // /**
    //  * 设备管理----更新设备失败
    //  */
    // int FAIL_DEVICE_UPATE = -1314;
    // /**
    //  * 设备管理----根据Id查询设备
    //  */
    // int FAIL_DEVICE_FIND_BY_ID = -1315;
    //
    // /**
    //  * 获取ID_TEXT类型数据
    //  */
    // int FAIL_ROOM_FIND_ID_NAME = -1316;
    // /**
    //  * 根据区域id_name形式的Json
    //  */
    // int FAIL_AREA_FIND_ID_NAME = -1317;
    // /**
    //  * 设备详情----根据设备ID获取设备详细参数
    //  */
    // int FAIL_DEVICEPARA_FIND_BY_DEVID = -1318;
    // /**
    //  * 设备详情----更新设备详情
    //  */
    // int FAIL__UPDATE_DEVICEPARA = -1319;
    // /**
    //  * 设备详情----应用到更多的设备
    //  */
    // int FAIL_APPLY_MORE_DEVICE = -1320;
    // /**
    //  * 设备详情----应用到更多房间
    //  */
    // int FAIL_APPLY_MORE_ROOM = -1321;
    // /**
    //  * 设备详情----应用到此房间
    //  */
    // int FAIL_APPLY_CURRENT_ROOM = -1322;
    // /**
    //  * 将设备应用到房间
    //  */
    // int FAIL_APPLY_DEV_TO_ROOM = -1323;
    // /**
    //  * 查询所有勾选房间的设备
    //  */
    // int FAIL_SELECT_ROOM_DEVICE = -1324;
    // /**
    //  * 根据查询条件获取设备个数
    //  */
    // int FAIL_DEVICE_COUNT_CONDITION = -1325;
    // /**
    //  * 获取选中的区域的所有的房间
    //  */
    // int FAIL_SELECT_AREA_ROOM = -1326;
    // /**
    //  * 根据查询条件获取房间个数
    //  */
    // int FAIL_ROOM_COUNT_CONDITION = -1327;


}
