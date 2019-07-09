package com.example.janche.common.utils.date;

/**
 * <p>星期枚举类</p>
 * @author qxb
 * @version 1.0
 * @since 2018.01.24
 *
 */
public enum WeekEnum {
    MONDAY("星期一", "Monday", "Mon.", 1),
    TUESDAY("星期二", "Tuesday", "Tues.", 2),
    WEDNESDAY("星期三", "Wednesday", "Wed.", 3),
    THURSDAY("星期四", "Thursday", "Thur.", 4),
    FRIDAY("星期五", "Friday", "Fri.", 5),
    SATURDAY("星期六", "Saturday", "Sat.", 6),
    SUNDAY("星期日", "Sunday", "Sun.", 7);

    String name_cn;
    String name_en;
    String name_enShort;
    int number;

    WeekEnum(String name_cn, String name_en, String name_enShort, int number) {
        this.name_cn = name_cn;
        this.name_en = name_en;
        this.name_enShort = name_enShort;
        this.number = number;
    }

    /**
     *
     * <li>getChineseName 得到中文日期</li>
     * @return    星期一 星期二...
     * @throws
     */
    public String getChineseName() {
        return name_cn;
    }

    /**
     *
     * <li>方法名：getName 得到英文日期（Monday..）</li>
     * @return    Monday Tuesday...
     * @throws
     */
    public String getName() {
        return name_en;
    }

    /**
     *
     * <li>方法名：getShortName 得到日期缩写</li>
     * @return    Mon. Tue. Fri...
     * @throws
     */
    public String getShortName() {
        return name_enShort;
    }

    /**
     *
     * <li>方法名：getNumber 得到星期几</li>
     * @return    数字1、2、3..
     * @throws
     */
    public int getNumber() {
        return number;
    }
}
