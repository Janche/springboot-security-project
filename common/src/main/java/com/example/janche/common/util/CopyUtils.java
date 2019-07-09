package com.example.janche.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * @ClassName CopyUtils
 * @Description 复制所需属性到dto模板中（前提是目标类与来源类的属性是同名且包含关系）
 * @Author Wwei
 */
public class CopyUtils {
    public static void copyProperties(Object source,Object dest) throws Exception {
        try {
            // 获取目标类的属性
            BeanInfo destBean = Introspector.getBeanInfo(dest.getClass(), Object.class);
            PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();

            // 获取来源类的属性
            BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(), Object.class);
            PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

            for (int i = 0;i<sourceProperty.length;i++){
                for (int j=0;j<destProperty.length;j++){
                    //属性的类和名称都一样才可以进行复制
                    if(sourceProperty[i].getName().equals(destProperty[j].getName())&&
                            sourceProperty[i].getPropertyType().equals(destProperty[j].getPropertyType())){
                            // 调用source的get方法，dest的set方法
                            destProperty[j].getWriteMethod().invoke(dest,sourceProperty[i].getReadMethod().invoke(source));
                            break;
                    }
                }
            }


        }catch (Exception e){
            throw new Exception("属性复制失败"+e.getMessage());
        }

    }

}
