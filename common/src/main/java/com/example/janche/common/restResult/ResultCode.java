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

    UNLOGIN(-999, "用户未登录或账号已在其它地方登陆，请重新登录"),

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
     * 区域相关
     */
    AREA_ADD_FAIL(5001, "区域新增失败"),

    AREA_UPDATE_FAIL(5002, "区域修改失败"),

    AREA_DELETE_FAIL(5003, "区域修改失败"),

    AREA_CODE_REPLACE(5004, "区域编码重复"),

    USER_NOT_AREA(5005, "用户没有区域"),

    AREAID_NOT_EXIST(5006, "区域ID不存在"),

    AREA_EXIST_LINKED_USER(5007, "此区域存在关联用户,请先删除关联的用户"),

    AREA_EXIST_LINKED_DEVICE(5008, "此区域存在关联设备,请先删除关联的设备"),

    AREA_SELECT_FAIL(5009, "请选择正确的区域"),

    /**
     * 资源类别相关
     */
    CATEGORY_EXIST_LINKED_USER(5201, "此资源类别下存在关联用户,请先删除关联的用户"),

    CATEGORY_EXIST_LINKED_DEVICE(5202, "此资源类别下存在关联设备,请先删除关联的设备"),

    /**
     * 设备相关
     */
    DEVICE_ADD_FAIL(5101, "设备新增失败"),

    DEVICE_UPDATE_FAIL(5102, "设备修改失败"),

    DEVICE_DELETE_FAIL(5103, "设备删除失败"),

    DEVICEID_NOT_EXIST(5104, "设备ID不存在"),

    DEVICE_CODE_REPEAT(5105, "设备编码重复，导入失败"),

    DEVICE_IP_REPEAT(5106, "设备IP重复，导入失败"),

    DEVICE_REQUIRED_NULL(5107, "必填字段部分为空，导入失败"),

    DEVICE_TOTAL_OVER_MAX(5108, "系统设备数量已到最大值"),

    DEVICE_IP_ILLEGAL(5109, "设备IP不合法，导入失败"),

//===========  系统管理
    /**
     * 用户管理
     */
    USER_ADD_FAIL(7001, "新增用户失败"),

    USER_UPDATE_FAIL(7002, "修改用户失败"),

    USER_DELETE_FAIL(7003, "删除用户失败"),

    USER_FINDNOWUSER_FAIL(7004, "获取登录用户失败"),

    USER_FINDUSER_FAIL(7005, "获取用户失败"),

    USER_FINDLIST_FAIL(7006, "获取用户列表失败"),

    USER_OUT_FAIL(7007, "导出用户列表失败"),

    USER_IMPORT_FAIL(7008, "导入用户列表失败"),



    /**
     * 角色管理
     */
    ROLE_ADD_FAIL(7101, "新增角色失败"),

    ROLE_UPDATE_FAIL(7102, "修改角色失败"),

    ROLE_DELETE_FAIL(7103, "删除角色失败"),

    ROLE_LIST_FAIL(7104, "获取角色列表失败"),

    ROLE_FINDROLE_FAIL(7105, "获取角色失败"),

    ROLE_USERLIST_FAIL(7106, "获取角色对应用户列表失败"),

    ROLE_USERROLES_FAIL(7107, "获取用户能操作角色集合失败"),

    /**
     * 办公办案自定义
     */
    OFFICE_ADD_FAIL(7201, "新增办公办案系统失败"),

    OFFICE_UPDATE_FAIL(7202, "修改办公办案系统失败"),

    OFFICE_DELETE_FAIL(7203, "删除办公办案系统失败"),

    OFFICE_LIST_FAIL(7204, "获取办公办案系统列表失败"),

    OFFICE_UPLOAD_FAIL(7205, "上传图片失败"),

    OFFICE_GETLIMAGE_FAIL(7206, "获取图片失败"),

    OFFICE_LISTBAY_FAIL(7207, "分类获取办公办案系统失败"),

    /**
     * 业务组管理
     */
    GROUP_ADD_FAIL(7301, "新增业务组失败"),

    GROUP_UPDATE_FAIL(7302, "修改业务组失败"),

    GROUP_DELETE_FAIL(7303, "删除业务组失败"),

    GROUP_LIST_FAIL(7304, "获取业务组列表失败"),



    /**
     * 系统配置
     */
    SYSCONFIG_GET_FAIL(7401, "获取系统配置失败"),

    SYSCONFIG_UPDATE_FAIL(7402, "修改系统配置失败");




// ==========运维管理

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
