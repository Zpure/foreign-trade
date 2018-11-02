package com.zcpure.foreign.trade.utils.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解用于Excel导出数据对象
 *
 * @author jiangyf
 * @date 2017年9月18日 下午6:32:20
 */
@Retention(RetentionPolicy.RUNTIME) /* 运行时有效 */
@Target(ElementType.FIELD) /* 范围 */
public @interface ExcelColumn {

    /**
     * Excel中的列名
     *
     * @return
     */
    String name();

    /**
     * 列名对应的A,B,C,D...,不指定按照默认顺序排序
     *
     * @return
     */
    String column() default "";

    /**
     * 提示信息
     *
     * @return
     */
    String prompt() default "";

    /**
     * 设置只能选择不能输入的列内容
     *
     * @return
     */
    String[] combo() default {};

    /**
     * 是否导出数据
     *
     * @return
     */
    boolean isExport() default true;

}
