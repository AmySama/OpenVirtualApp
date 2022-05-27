package com.lody.virtual.helper.utils.marks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface FakeDeviceMark {
  String value() default "";
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\marks\FakeDeviceMark.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */