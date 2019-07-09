package com.example.janche.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Class description
 *  日期格式化
 * @version        1.0, 18/09/22
 * @author         pitt
 */
public class DateUtils extends DateFormatUtils {

    /**
     * 自定义日期格式
     * 使用方法: DateFormatUtils.format(new Date(), DateUtils.DATE_AND_HOURS_PATTERN))
     * 年月日-时分秒
     */
    public static final String DATE_AND_HOURS_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年月日
     * */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 还缺少时间的加减运算,后面添加
     * */
}


