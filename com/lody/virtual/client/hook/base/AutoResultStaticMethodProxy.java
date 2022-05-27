package com.lody.virtual.client.hook.base;

import com.lody.virtual.helper.utils.Reflect;
import java.lang.reflect.Method;

public class AutoResultStaticMethodProxy extends StaticMethodProxy {
  public AutoResultStaticMethodProxy(String paramString) {
    super(paramString);
  }
  
  public Object call(Object paramObject, Method paramMethod, Object... paramVarArgs) throws Throwable {
    return getDefaultValue(paramObject, paramMethod, paramVarArgs);
  }
  
  public Object getDefaultValue(Object paramObject, Method paramMethod, Object... paramVarArgs) {
    Class<Boolean> clazz = Reflect.wrapper(paramMethod.getReturnType());
    paramObject = Integer.valueOf(0);
    if (clazz == null)
      return paramObject; 
    if (clazz.isPrimitive()) {
      if (Boolean.class == clazz)
        return Boolean.valueOf(false); 
      if (Integer.class == clazz)
        return paramObject; 
      if (Long.class == clazz)
        return Long.valueOf(0L); 
      if (Short.class == clazz)
        return Short.valueOf((short)0); 
      if (Byte.class == clazz)
        return Byte.valueOf((byte)0); 
      if (Double.class == clazz)
        return Double.valueOf(0.0D); 
      if (Float.class == clazz)
        return Float.valueOf(0.0F); 
      if (Character.class == clazz)
        return Character.valueOf(false); 
    } 
    return null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\base\AutoResultStaticMethodProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */