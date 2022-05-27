package external.org.apache.commons.lang3.builder;

import external.org.apache.commons.lang3.ObjectUtils;

public class ToStringBuilder implements Builder<String> {
  private static volatile ToStringStyle defaultStyle = ToStringStyle.DEFAULT_STYLE;
  
  private final StringBuffer buffer;
  
  private final Object object;
  
  private final ToStringStyle style;
  
  public ToStringBuilder(Object paramObject) {
    this(paramObject, null, null);
  }
  
  public ToStringBuilder(Object paramObject, ToStringStyle paramToStringStyle) {
    this(paramObject, paramToStringStyle, null);
  }
  
  public ToStringBuilder(Object paramObject, ToStringStyle paramToStringStyle, StringBuffer paramStringBuffer) {
    ToStringStyle toStringStyle = paramToStringStyle;
    if (paramToStringStyle == null)
      toStringStyle = getDefaultStyle(); 
    StringBuffer stringBuffer = paramStringBuffer;
    if (paramStringBuffer == null)
      stringBuffer = new StringBuffer(512); 
    this.buffer = stringBuffer;
    this.style = toStringStyle;
    this.object = paramObject;
    toStringStyle.appendStart(stringBuffer, paramObject);
  }
  
  public static ToStringStyle getDefaultStyle() {
    return defaultStyle;
  }
  
  public static String reflectionToString(Object paramObject) {
    return ReflectionToStringBuilder.toString(paramObject);
  }
  
  public static String reflectionToString(Object paramObject, ToStringStyle paramToStringStyle) {
    return ReflectionToStringBuilder.toString(paramObject, paramToStringStyle);
  }
  
  public static String reflectionToString(Object paramObject, ToStringStyle paramToStringStyle, boolean paramBoolean) {
    return ReflectionToStringBuilder.toString(paramObject, paramToStringStyle, paramBoolean, false, null);
  }
  
  public static <T> String reflectionToString(T paramT, ToStringStyle paramToStringStyle, boolean paramBoolean, Class<? super T> paramClass) {
    return ReflectionToStringBuilder.toString(paramT, paramToStringStyle, paramBoolean, false, paramClass);
  }
  
  public static void setDefaultStyle(ToStringStyle paramToStringStyle) {
    if (paramToStringStyle != null) {
      defaultStyle = paramToStringStyle;
      return;
    } 
    throw new IllegalArgumentException("The style must not be null");
  }
  
  public ToStringBuilder append(byte paramByte) {
    this.style.append(this.buffer, (String)null, paramByte);
    return this;
  }
  
  public ToStringBuilder append(char paramChar) {
    this.style.append(this.buffer, (String)null, paramChar);
    return this;
  }
  
  public ToStringBuilder append(double paramDouble) {
    this.style.append(this.buffer, (String)null, paramDouble);
    return this;
  }
  
  public ToStringBuilder append(float paramFloat) {
    this.style.append(this.buffer, (String)null, paramFloat);
    return this;
  }
  
  public ToStringBuilder append(int paramInt) {
    this.style.append(this.buffer, (String)null, paramInt);
    return this;
  }
  
  public ToStringBuilder append(long paramLong) {
    this.style.append(this.buffer, (String)null, paramLong);
    return this;
  }
  
  public ToStringBuilder append(Object paramObject) {
    this.style.append(this.buffer, (String)null, paramObject, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(String paramString, byte paramByte) {
    this.style.append(this.buffer, paramString, paramByte);
    return this;
  }
  
  public ToStringBuilder append(String paramString, char paramChar) {
    this.style.append(this.buffer, paramString, paramChar);
    return this;
  }
  
  public ToStringBuilder append(String paramString, double paramDouble) {
    this.style.append(this.buffer, paramString, paramDouble);
    return this;
  }
  
  public ToStringBuilder append(String paramString, float paramFloat) {
    this.style.append(this.buffer, paramString, paramFloat);
    return this;
  }
  
  public ToStringBuilder append(String paramString, int paramInt) {
    this.style.append(this.buffer, paramString, paramInt);
    return this;
  }
  
  public ToStringBuilder append(String paramString, long paramLong) {
    this.style.append(this.buffer, paramString, paramLong);
    return this;
  }
  
  public ToStringBuilder append(String paramString, Object paramObject) {
    this.style.append(this.buffer, paramString, paramObject, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(String paramString, Object paramObject, boolean paramBoolean) {
    this.style.append(this.buffer, paramString, paramObject, Boolean.valueOf(paramBoolean));
    return this;
  }
  
  public ToStringBuilder append(String paramString, short paramShort) {
    this.style.append(this.buffer, paramString, paramShort);
    return this;
  }
  
  public ToStringBuilder append(String paramString, boolean paramBoolean) {
    this.style.append(this.buffer, paramString, paramBoolean);
    return this;
  }
  
  public ToStringBuilder append(String paramString, byte[] paramArrayOfbyte) {
    this.style.append(this.buffer, paramString, paramArrayOfbyte, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(String paramString, byte[] paramArrayOfbyte, boolean paramBoolean) {
    this.style.append(this.buffer, paramString, paramArrayOfbyte, Boolean.valueOf(paramBoolean));
    return this;
  }
  
  public ToStringBuilder append(String paramString, char[] paramArrayOfchar) {
    this.style.append(this.buffer, paramString, paramArrayOfchar, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(String paramString, char[] paramArrayOfchar, boolean paramBoolean) {
    this.style.append(this.buffer, paramString, paramArrayOfchar, Boolean.valueOf(paramBoolean));
    return this;
  }
  
  public ToStringBuilder append(String paramString, double[] paramArrayOfdouble) {
    this.style.append(this.buffer, paramString, paramArrayOfdouble, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(String paramString, double[] paramArrayOfdouble, boolean paramBoolean) {
    this.style.append(this.buffer, paramString, paramArrayOfdouble, Boolean.valueOf(paramBoolean));
    return this;
  }
  
  public ToStringBuilder append(String paramString, float[] paramArrayOffloat) {
    this.style.append(this.buffer, paramString, paramArrayOffloat, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(String paramString, float[] paramArrayOffloat, boolean paramBoolean) {
    this.style.append(this.buffer, paramString, paramArrayOffloat, Boolean.valueOf(paramBoolean));
    return this;
  }
  
  public ToStringBuilder append(String paramString, int[] paramArrayOfint) {
    this.style.append(this.buffer, paramString, paramArrayOfint, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(String paramString, int[] paramArrayOfint, boolean paramBoolean) {
    this.style.append(this.buffer, paramString, paramArrayOfint, Boolean.valueOf(paramBoolean));
    return this;
  }
  
  public ToStringBuilder append(String paramString, long[] paramArrayOflong) {
    this.style.append(this.buffer, paramString, paramArrayOflong, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(String paramString, long[] paramArrayOflong, boolean paramBoolean) {
    this.style.append(this.buffer, paramString, paramArrayOflong, Boolean.valueOf(paramBoolean));
    return this;
  }
  
  public ToStringBuilder append(String paramString, Object[] paramArrayOfObject) {
    this.style.append(this.buffer, paramString, paramArrayOfObject, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(String paramString, Object[] paramArrayOfObject, boolean paramBoolean) {
    this.style.append(this.buffer, paramString, paramArrayOfObject, Boolean.valueOf(paramBoolean));
    return this;
  }
  
  public ToStringBuilder append(String paramString, short[] paramArrayOfshort) {
    this.style.append(this.buffer, paramString, paramArrayOfshort, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(String paramString, short[] paramArrayOfshort, boolean paramBoolean) {
    this.style.append(this.buffer, paramString, paramArrayOfshort, Boolean.valueOf(paramBoolean));
    return this;
  }
  
  public ToStringBuilder append(String paramString, boolean[] paramArrayOfboolean) {
    this.style.append(this.buffer, paramString, paramArrayOfboolean, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(String paramString, boolean[] paramArrayOfboolean, boolean paramBoolean) {
    this.style.append(this.buffer, paramString, paramArrayOfboolean, Boolean.valueOf(paramBoolean));
    return this;
  }
  
  public ToStringBuilder append(short paramShort) {
    this.style.append(this.buffer, (String)null, paramShort);
    return this;
  }
  
  public ToStringBuilder append(boolean paramBoolean) {
    this.style.append(this.buffer, (String)null, paramBoolean);
    return this;
  }
  
  public ToStringBuilder append(byte[] paramArrayOfbyte) {
    this.style.append(this.buffer, (String)null, paramArrayOfbyte, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(char[] paramArrayOfchar) {
    this.style.append(this.buffer, (String)null, paramArrayOfchar, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(double[] paramArrayOfdouble) {
    this.style.append(this.buffer, (String)null, paramArrayOfdouble, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(float[] paramArrayOffloat) {
    this.style.append(this.buffer, (String)null, paramArrayOffloat, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(int[] paramArrayOfint) {
    this.style.append(this.buffer, (String)null, paramArrayOfint, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(long[] paramArrayOflong) {
    this.style.append(this.buffer, (String)null, paramArrayOflong, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(Object[] paramArrayOfObject) {
    this.style.append(this.buffer, (String)null, paramArrayOfObject, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(short[] paramArrayOfshort) {
    this.style.append(this.buffer, (String)null, paramArrayOfshort, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder append(boolean[] paramArrayOfboolean) {
    this.style.append(this.buffer, (String)null, paramArrayOfboolean, (Boolean)null);
    return this;
  }
  
  public ToStringBuilder appendAsObjectToString(Object paramObject) {
    ObjectUtils.identityToString(getStringBuffer(), paramObject);
    return this;
  }
  
  public ToStringBuilder appendSuper(String paramString) {
    if (paramString != null)
      this.style.appendSuper(this.buffer, paramString); 
    return this;
  }
  
  public ToStringBuilder appendToString(String paramString) {
    if (paramString != null)
      this.style.appendToString(this.buffer, paramString); 
    return this;
  }
  
  public String build() {
    return toString();
  }
  
  public Object getObject() {
    return this.object;
  }
  
  public StringBuffer getStringBuffer() {
    return this.buffer;
  }
  
  public ToStringStyle getStyle() {
    return this.style;
  }
  
  public String toString() {
    if (getObject() == null) {
      getStringBuffer().append(getStyle().getNullText());
    } else {
      this.style.appendEnd(getStringBuffer(), getObject());
    } 
    return getStringBuffer().toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\builder\ToStringBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */