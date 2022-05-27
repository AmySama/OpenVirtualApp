package external.org.apache.commons.lang3.builder;

import external.org.apache.commons.lang3.ClassUtils;
import external.org.apache.commons.lang3.ObjectUtils;
import external.org.apache.commons.lang3.SystemUtils;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;

public abstract class ToStringStyle implements Serializable {
  public static final ToStringStyle DEFAULT_STYLE = new DefaultToStringStyle();
  
  public static final ToStringStyle MULTI_LINE_STYLE = new MultiLineToStringStyle();
  
  public static final ToStringStyle NO_FIELD_NAMES_STYLE = new NoFieldNameToStringStyle();
  
  private static final ThreadLocal<WeakHashMap<Object, Object>> REGISTRY;
  
  public static final ToStringStyle SHORT_PREFIX_STYLE = new ShortPrefixToStringStyle();
  
  public static final ToStringStyle SIMPLE_STYLE = new SimpleToStringStyle();
  
  private static final long serialVersionUID = -2587890625525655916L;
  
  private boolean arrayContentDetail = true;
  
  private String arrayEnd = "}";
  
  private String arraySeparator = ",";
  
  private String arrayStart = "{";
  
  private String contentEnd = "]";
  
  private String contentStart = "[";
  
  private boolean defaultFullDetail = true;
  
  private String fieldNameValueSeparator = "=";
  
  private String fieldSeparator = ",";
  
  private boolean fieldSeparatorAtEnd = false;
  
  private boolean fieldSeparatorAtStart = false;
  
  private String nullText = "<null>";
  
  private String sizeEndText = ">";
  
  private String sizeStartText = "<size=";
  
  private String summaryObjectEndText = ">";
  
  private String summaryObjectStartText = "<";
  
  private boolean useClassName = true;
  
  private boolean useFieldNames = true;
  
  private boolean useIdentityHashCode = true;
  
  private boolean useShortClassName = false;
  
  static {
    REGISTRY = new ThreadLocal<WeakHashMap<Object, Object>>();
  }
  
  static Map<Object, Object> getRegistry() {
    return REGISTRY.get();
  }
  
  static boolean isRegistered(Object paramObject) {
    boolean bool;
    Map<Object, Object> map = getRegistry();
    if (map != null && map.containsKey(paramObject)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  static void register(Object paramObject) {
    if (paramObject != null) {
      if (getRegistry() == null)
        REGISTRY.set(new WeakHashMap<Object, Object>()); 
      getRegistry().put(paramObject, null);
    } 
  }
  
  static void unregister(Object paramObject) {
    if (paramObject != null) {
      Map<Object, Object> map = getRegistry();
      if (map != null) {
        map.remove(paramObject);
        if (map.isEmpty())
          REGISTRY.remove(); 
      } 
    } 
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, byte paramByte) {
    appendFieldStart(paramStringBuffer, paramString);
    appendDetail(paramStringBuffer, paramString, paramByte);
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, char paramChar) {
    appendFieldStart(paramStringBuffer, paramString);
    appendDetail(paramStringBuffer, paramString, paramChar);
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, double paramDouble) {
    appendFieldStart(paramStringBuffer, paramString);
    appendDetail(paramStringBuffer, paramString, paramDouble);
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, float paramFloat) {
    appendFieldStart(paramStringBuffer, paramString);
    appendDetail(paramStringBuffer, paramString, paramFloat);
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, int paramInt) {
    appendFieldStart(paramStringBuffer, paramString);
    appendDetail(paramStringBuffer, paramString, paramInt);
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, long paramLong) {
    appendFieldStart(paramStringBuffer, paramString);
    appendDetail(paramStringBuffer, paramString, paramLong);
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, Object paramObject, Boolean paramBoolean) {
    appendFieldStart(paramStringBuffer, paramString);
    if (paramObject == null) {
      appendNullText(paramStringBuffer, paramString);
    } else {
      appendInternal(paramStringBuffer, paramString, paramObject, isFullDetail(paramBoolean));
    } 
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, short paramShort) {
    appendFieldStart(paramStringBuffer, paramString);
    appendDetail(paramStringBuffer, paramString, paramShort);
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean) {
    appendFieldStart(paramStringBuffer, paramString);
    appendDetail(paramStringBuffer, paramString, paramBoolean);
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, byte[] paramArrayOfbyte, Boolean paramBoolean) {
    appendFieldStart(paramStringBuffer, paramString);
    if (paramArrayOfbyte == null) {
      appendNullText(paramStringBuffer, paramString);
    } else if (isFullDetail(paramBoolean)) {
      appendDetail(paramStringBuffer, paramString, paramArrayOfbyte);
    } else {
      appendSummary(paramStringBuffer, paramString, paramArrayOfbyte);
    } 
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, char[] paramArrayOfchar, Boolean paramBoolean) {
    appendFieldStart(paramStringBuffer, paramString);
    if (paramArrayOfchar == null) {
      appendNullText(paramStringBuffer, paramString);
    } else if (isFullDetail(paramBoolean)) {
      appendDetail(paramStringBuffer, paramString, paramArrayOfchar);
    } else {
      appendSummary(paramStringBuffer, paramString, paramArrayOfchar);
    } 
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, double[] paramArrayOfdouble, Boolean paramBoolean) {
    appendFieldStart(paramStringBuffer, paramString);
    if (paramArrayOfdouble == null) {
      appendNullText(paramStringBuffer, paramString);
    } else if (isFullDetail(paramBoolean)) {
      appendDetail(paramStringBuffer, paramString, paramArrayOfdouble);
    } else {
      appendSummary(paramStringBuffer, paramString, paramArrayOfdouble);
    } 
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, float[] paramArrayOffloat, Boolean paramBoolean) {
    appendFieldStart(paramStringBuffer, paramString);
    if (paramArrayOffloat == null) {
      appendNullText(paramStringBuffer, paramString);
    } else if (isFullDetail(paramBoolean)) {
      appendDetail(paramStringBuffer, paramString, paramArrayOffloat);
    } else {
      appendSummary(paramStringBuffer, paramString, paramArrayOffloat);
    } 
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, int[] paramArrayOfint, Boolean paramBoolean) {
    appendFieldStart(paramStringBuffer, paramString);
    if (paramArrayOfint == null) {
      appendNullText(paramStringBuffer, paramString);
    } else if (isFullDetail(paramBoolean)) {
      appendDetail(paramStringBuffer, paramString, paramArrayOfint);
    } else {
      appendSummary(paramStringBuffer, paramString, paramArrayOfint);
    } 
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, long[] paramArrayOflong, Boolean paramBoolean) {
    appendFieldStart(paramStringBuffer, paramString);
    if (paramArrayOflong == null) {
      appendNullText(paramStringBuffer, paramString);
    } else if (isFullDetail(paramBoolean)) {
      appendDetail(paramStringBuffer, paramString, paramArrayOflong);
    } else {
      appendSummary(paramStringBuffer, paramString, paramArrayOflong);
    } 
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, Object[] paramArrayOfObject, Boolean paramBoolean) {
    appendFieldStart(paramStringBuffer, paramString);
    if (paramArrayOfObject == null) {
      appendNullText(paramStringBuffer, paramString);
    } else if (isFullDetail(paramBoolean)) {
      appendDetail(paramStringBuffer, paramString, paramArrayOfObject);
    } else {
      appendSummary(paramStringBuffer, paramString, paramArrayOfObject);
    } 
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, short[] paramArrayOfshort, Boolean paramBoolean) {
    appendFieldStart(paramStringBuffer, paramString);
    if (paramArrayOfshort == null) {
      appendNullText(paramStringBuffer, paramString);
    } else if (isFullDetail(paramBoolean)) {
      appendDetail(paramStringBuffer, paramString, paramArrayOfshort);
    } else {
      appendSummary(paramStringBuffer, paramString, paramArrayOfshort);
    } 
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  public void append(StringBuffer paramStringBuffer, String paramString, boolean[] paramArrayOfboolean, Boolean paramBoolean) {
    appendFieldStart(paramStringBuffer, paramString);
    if (paramArrayOfboolean == null) {
      appendNullText(paramStringBuffer, paramString);
    } else if (isFullDetail(paramBoolean)) {
      appendDetail(paramStringBuffer, paramString, paramArrayOfboolean);
    } else {
      appendSummary(paramStringBuffer, paramString, paramArrayOfboolean);
    } 
    appendFieldEnd(paramStringBuffer, paramString);
  }
  
  protected void appendClassName(StringBuffer paramStringBuffer, Object paramObject) {
    if (this.useClassName && paramObject != null) {
      register(paramObject);
      if (this.useShortClassName) {
        paramStringBuffer.append(getShortClassName(paramObject.getClass()));
      } else {
        paramStringBuffer.append(paramObject.getClass().getName());
      } 
    } 
  }
  
  protected void appendContentEnd(StringBuffer paramStringBuffer) {
    paramStringBuffer.append(this.contentEnd);
  }
  
  protected void appendContentStart(StringBuffer paramStringBuffer) {
    paramStringBuffer.append(this.contentStart);
  }
  
  protected void appendCyclicObject(StringBuffer paramStringBuffer, String paramString, Object paramObject) {
    ObjectUtils.identityToString(paramStringBuffer, paramObject);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, byte paramByte) {
    paramStringBuffer.append(paramByte);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, char paramChar) {
    paramStringBuffer.append(paramChar);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, double paramDouble) {
    paramStringBuffer.append(paramDouble);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, float paramFloat) {
    paramStringBuffer.append(paramFloat);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, int paramInt) {
    paramStringBuffer.append(paramInt);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, long paramLong) {
    paramStringBuffer.append(paramLong);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, Object paramObject) {
    paramStringBuffer.append(paramObject);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, Collection<?> paramCollection) {
    paramStringBuffer.append(paramCollection);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, Map<?, ?> paramMap) {
    paramStringBuffer.append(paramMap);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, short paramShort) {
    paramStringBuffer.append(paramShort);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean) {
    paramStringBuffer.append(paramBoolean);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, byte[] paramArrayOfbyte) {
    paramStringBuffer.append(this.arrayStart);
    for (byte b = 0; b < paramArrayOfbyte.length; b++) {
      if (b > 0)
        paramStringBuffer.append(this.arraySeparator); 
      appendDetail(paramStringBuffer, paramString, paramArrayOfbyte[b]);
    } 
    paramStringBuffer.append(this.arrayEnd);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, char[] paramArrayOfchar) {
    paramStringBuffer.append(this.arrayStart);
    for (byte b = 0; b < paramArrayOfchar.length; b++) {
      if (b > 0)
        paramStringBuffer.append(this.arraySeparator); 
      appendDetail(paramStringBuffer, paramString, paramArrayOfchar[b]);
    } 
    paramStringBuffer.append(this.arrayEnd);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, double[] paramArrayOfdouble) {
    paramStringBuffer.append(this.arrayStart);
    for (byte b = 0; b < paramArrayOfdouble.length; b++) {
      if (b > 0)
        paramStringBuffer.append(this.arraySeparator); 
      appendDetail(paramStringBuffer, paramString, paramArrayOfdouble[b]);
    } 
    paramStringBuffer.append(this.arrayEnd);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, float[] paramArrayOffloat) {
    paramStringBuffer.append(this.arrayStart);
    for (byte b = 0; b < paramArrayOffloat.length; b++) {
      if (b > 0)
        paramStringBuffer.append(this.arraySeparator); 
      appendDetail(paramStringBuffer, paramString, paramArrayOffloat[b]);
    } 
    paramStringBuffer.append(this.arrayEnd);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, int[] paramArrayOfint) {
    paramStringBuffer.append(this.arrayStart);
    for (byte b = 0; b < paramArrayOfint.length; b++) {
      if (b > 0)
        paramStringBuffer.append(this.arraySeparator); 
      appendDetail(paramStringBuffer, paramString, paramArrayOfint[b]);
    } 
    paramStringBuffer.append(this.arrayEnd);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, long[] paramArrayOflong) {
    paramStringBuffer.append(this.arrayStart);
    for (byte b = 0; b < paramArrayOflong.length; b++) {
      if (b > 0)
        paramStringBuffer.append(this.arraySeparator); 
      appendDetail(paramStringBuffer, paramString, paramArrayOflong[b]);
    } 
    paramStringBuffer.append(this.arrayEnd);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, Object[] paramArrayOfObject) {
    paramStringBuffer.append(this.arrayStart);
    for (byte b = 0; b < paramArrayOfObject.length; b++) {
      Object object = paramArrayOfObject[b];
      if (b > 0)
        paramStringBuffer.append(this.arraySeparator); 
      if (object == null) {
        appendNullText(paramStringBuffer, paramString);
      } else {
        appendInternal(paramStringBuffer, paramString, object, this.arrayContentDetail);
      } 
    } 
    paramStringBuffer.append(this.arrayEnd);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, short[] paramArrayOfshort) {
    paramStringBuffer.append(this.arrayStart);
    for (byte b = 0; b < paramArrayOfshort.length; b++) {
      if (b > 0)
        paramStringBuffer.append(this.arraySeparator); 
      appendDetail(paramStringBuffer, paramString, paramArrayOfshort[b]);
    } 
    paramStringBuffer.append(this.arrayEnd);
  }
  
  protected void appendDetail(StringBuffer paramStringBuffer, String paramString, boolean[] paramArrayOfboolean) {
    paramStringBuffer.append(this.arrayStart);
    for (byte b = 0; b < paramArrayOfboolean.length; b++) {
      if (b > 0)
        paramStringBuffer.append(this.arraySeparator); 
      appendDetail(paramStringBuffer, paramString, paramArrayOfboolean[b]);
    } 
    paramStringBuffer.append(this.arrayEnd);
  }
  
  public void appendEnd(StringBuffer paramStringBuffer, Object paramObject) {
    if (!this.fieldSeparatorAtEnd)
      removeLastFieldSeparator(paramStringBuffer); 
    appendContentEnd(paramStringBuffer);
    unregister(paramObject);
  }
  
  protected void appendFieldEnd(StringBuffer paramStringBuffer, String paramString) {
    appendFieldSeparator(paramStringBuffer);
  }
  
  protected void appendFieldSeparator(StringBuffer paramStringBuffer) {
    paramStringBuffer.append(this.fieldSeparator);
  }
  
  protected void appendFieldStart(StringBuffer paramStringBuffer, String paramString) {
    if (this.useFieldNames && paramString != null) {
      paramStringBuffer.append(paramString);
      paramStringBuffer.append(this.fieldNameValueSeparator);
    } 
  }
  
  protected void appendIdentityHashCode(StringBuffer paramStringBuffer, Object paramObject) {
    if (isUseIdentityHashCode() && paramObject != null) {
      register(paramObject);
      paramStringBuffer.append('@');
      paramStringBuffer.append(Integer.toHexString(System.identityHashCode(paramObject)));
    } 
  }
  
  protected void appendInternal(StringBuffer paramStringBuffer, String paramString, Object paramObject, boolean paramBoolean) {
    if (isRegistered(paramObject) && !(paramObject instanceof Number) && !(paramObject instanceof Boolean) && !(paramObject instanceof Character)) {
      appendCyclicObject(paramStringBuffer, paramString, paramObject);
      return;
    } 
    register(paramObject);
    try {
      if (paramObject instanceof Collection) {
        if (paramBoolean) {
          appendDetail(paramStringBuffer, paramString, (Collection)paramObject);
        } else {
          appendSummarySize(paramStringBuffer, paramString, ((Collection)paramObject).size());
        } 
      } else if (paramObject instanceof Map) {
        if (paramBoolean) {
          appendDetail(paramStringBuffer, paramString, (Map<?, ?>)paramObject);
        } else {
          appendSummarySize(paramStringBuffer, paramString, ((Map)paramObject).size());
        } 
      } else if (paramObject instanceof long[]) {
        if (paramBoolean) {
          appendDetail(paramStringBuffer, paramString, (long[])paramObject);
        } else {
          appendSummary(paramStringBuffer, paramString, (long[])paramObject);
        } 
      } else if (paramObject instanceof int[]) {
        if (paramBoolean) {
          appendDetail(paramStringBuffer, paramString, (int[])paramObject);
        } else {
          appendSummary(paramStringBuffer, paramString, (int[])paramObject);
        } 
      } else if (paramObject instanceof short[]) {
        if (paramBoolean) {
          appendDetail(paramStringBuffer, paramString, (short[])paramObject);
        } else {
          appendSummary(paramStringBuffer, paramString, (short[])paramObject);
        } 
      } else if (paramObject instanceof byte[]) {
        if (paramBoolean) {
          appendDetail(paramStringBuffer, paramString, (byte[])paramObject);
        } else {
          appendSummary(paramStringBuffer, paramString, (byte[])paramObject);
        } 
      } else if (paramObject instanceof char[]) {
        if (paramBoolean) {
          appendDetail(paramStringBuffer, paramString, (char[])paramObject);
        } else {
          appendSummary(paramStringBuffer, paramString, (char[])paramObject);
        } 
      } else if (paramObject instanceof double[]) {
        if (paramBoolean) {
          appendDetail(paramStringBuffer, paramString, (double[])paramObject);
        } else {
          appendSummary(paramStringBuffer, paramString, (double[])paramObject);
        } 
      } else if (paramObject instanceof float[]) {
        if (paramBoolean) {
          appendDetail(paramStringBuffer, paramString, (float[])paramObject);
        } else {
          appendSummary(paramStringBuffer, paramString, (float[])paramObject);
        } 
      } else if (paramObject instanceof boolean[]) {
        if (paramBoolean) {
          appendDetail(paramStringBuffer, paramString, (boolean[])paramObject);
        } else {
          appendSummary(paramStringBuffer, paramString, (boolean[])paramObject);
        } 
      } else if (paramObject.getClass().isArray()) {
        if (paramBoolean) {
          appendDetail(paramStringBuffer, paramString, (Object[])paramObject);
        } else {
          appendSummary(paramStringBuffer, paramString, (Object[])paramObject);
        } 
      } else if (paramBoolean) {
        appendDetail(paramStringBuffer, paramString, paramObject);
      } else {
        appendSummary(paramStringBuffer, paramString, paramObject);
      } 
      return;
    } finally {
      unregister(paramObject);
    } 
  }
  
  protected void appendNullText(StringBuffer paramStringBuffer, String paramString) {
    paramStringBuffer.append(this.nullText);
  }
  
  public void appendStart(StringBuffer paramStringBuffer, Object paramObject) {
    if (paramObject != null) {
      appendClassName(paramStringBuffer, paramObject);
      appendIdentityHashCode(paramStringBuffer, paramObject);
      appendContentStart(paramStringBuffer);
      if (this.fieldSeparatorAtStart)
        appendFieldSeparator(paramStringBuffer); 
    } 
  }
  
  protected void appendSummary(StringBuffer paramStringBuffer, String paramString, Object paramObject) {
    paramStringBuffer.append(this.summaryObjectStartText);
    paramStringBuffer.append(getShortClassName(paramObject.getClass()));
    paramStringBuffer.append(this.summaryObjectEndText);
  }
  
  protected void appendSummary(StringBuffer paramStringBuffer, String paramString, byte[] paramArrayOfbyte) {
    appendSummarySize(paramStringBuffer, paramString, paramArrayOfbyte.length);
  }
  
  protected void appendSummary(StringBuffer paramStringBuffer, String paramString, char[] paramArrayOfchar) {
    appendSummarySize(paramStringBuffer, paramString, paramArrayOfchar.length);
  }
  
  protected void appendSummary(StringBuffer paramStringBuffer, String paramString, double[] paramArrayOfdouble) {
    appendSummarySize(paramStringBuffer, paramString, paramArrayOfdouble.length);
  }
  
  protected void appendSummary(StringBuffer paramStringBuffer, String paramString, float[] paramArrayOffloat) {
    appendSummarySize(paramStringBuffer, paramString, paramArrayOffloat.length);
  }
  
  protected void appendSummary(StringBuffer paramStringBuffer, String paramString, int[] paramArrayOfint) {
    appendSummarySize(paramStringBuffer, paramString, paramArrayOfint.length);
  }
  
  protected void appendSummary(StringBuffer paramStringBuffer, String paramString, long[] paramArrayOflong) {
    appendSummarySize(paramStringBuffer, paramString, paramArrayOflong.length);
  }
  
  protected void appendSummary(StringBuffer paramStringBuffer, String paramString, Object[] paramArrayOfObject) {
    appendSummarySize(paramStringBuffer, paramString, paramArrayOfObject.length);
  }
  
  protected void appendSummary(StringBuffer paramStringBuffer, String paramString, short[] paramArrayOfshort) {
    appendSummarySize(paramStringBuffer, paramString, paramArrayOfshort.length);
  }
  
  protected void appendSummary(StringBuffer paramStringBuffer, String paramString, boolean[] paramArrayOfboolean) {
    appendSummarySize(paramStringBuffer, paramString, paramArrayOfboolean.length);
  }
  
  protected void appendSummarySize(StringBuffer paramStringBuffer, String paramString, int paramInt) {
    paramStringBuffer.append(this.sizeStartText);
    paramStringBuffer.append(paramInt);
    paramStringBuffer.append(this.sizeEndText);
  }
  
  public void appendSuper(StringBuffer paramStringBuffer, String paramString) {
    appendToString(paramStringBuffer, paramString);
  }
  
  public void appendToString(StringBuffer paramStringBuffer, String paramString) {
    if (paramString != null) {
      int i = paramString.indexOf(this.contentStart) + this.contentStart.length();
      int j = paramString.lastIndexOf(this.contentEnd);
      if (i != j && i >= 0 && j >= 0) {
        paramString = paramString.substring(i, j);
        if (this.fieldSeparatorAtStart)
          removeLastFieldSeparator(paramStringBuffer); 
        paramStringBuffer.append(paramString);
        appendFieldSeparator(paramStringBuffer);
      } 
    } 
  }
  
  protected String getArrayEnd() {
    return this.arrayEnd;
  }
  
  protected String getArraySeparator() {
    return this.arraySeparator;
  }
  
  protected String getArrayStart() {
    return this.arrayStart;
  }
  
  protected String getContentEnd() {
    return this.contentEnd;
  }
  
  protected String getContentStart() {
    return this.contentStart;
  }
  
  protected String getFieldNameValueSeparator() {
    return this.fieldNameValueSeparator;
  }
  
  protected String getFieldSeparator() {
    return this.fieldSeparator;
  }
  
  protected String getNullText() {
    return this.nullText;
  }
  
  protected String getShortClassName(Class<?> paramClass) {
    return ClassUtils.getShortClassName(paramClass);
  }
  
  protected String getSizeEndText() {
    return this.sizeEndText;
  }
  
  protected String getSizeStartText() {
    return this.sizeStartText;
  }
  
  protected String getSummaryObjectEndText() {
    return this.summaryObjectEndText;
  }
  
  protected String getSummaryObjectStartText() {
    return this.summaryObjectStartText;
  }
  
  protected boolean isArrayContentDetail() {
    return this.arrayContentDetail;
  }
  
  protected boolean isDefaultFullDetail() {
    return this.defaultFullDetail;
  }
  
  protected boolean isFieldSeparatorAtEnd() {
    return this.fieldSeparatorAtEnd;
  }
  
  protected boolean isFieldSeparatorAtStart() {
    return this.fieldSeparatorAtStart;
  }
  
  protected boolean isFullDetail(Boolean paramBoolean) {
    return (paramBoolean == null) ? this.defaultFullDetail : paramBoolean.booleanValue();
  }
  
  protected boolean isUseClassName() {
    return this.useClassName;
  }
  
  protected boolean isUseFieldNames() {
    return this.useFieldNames;
  }
  
  protected boolean isUseIdentityHashCode() {
    return this.useIdentityHashCode;
  }
  
  protected boolean isUseShortClassName() {
    return this.useShortClassName;
  }
  
  protected void reflectionAppendArrayDetail(StringBuffer paramStringBuffer, String paramString, Object paramObject) {
    paramStringBuffer.append(this.arrayStart);
    int i = Array.getLength(paramObject);
    for (byte b = 0; b < i; b++) {
      Object object = Array.get(paramObject, b);
      if (b > 0)
        paramStringBuffer.append(this.arraySeparator); 
      if (object == null) {
        appendNullText(paramStringBuffer, paramString);
      } else {
        appendInternal(paramStringBuffer, paramString, object, this.arrayContentDetail);
      } 
    } 
    paramStringBuffer.append(this.arrayEnd);
  }
  
  protected void removeLastFieldSeparator(StringBuffer paramStringBuffer) {
    int i = paramStringBuffer.length();
    int j = this.fieldSeparator.length();
    if (i > 0 && j > 0 && i >= j) {
      boolean bool = false;
      byte b = 0;
      while (true) {
        if (b < j) {
          if (paramStringBuffer.charAt(i - 1 - b) != this.fieldSeparator.charAt(j - 1 - b)) {
            b = bool;
            break;
          } 
          b++;
          continue;
        } 
        b = 1;
        break;
      } 
      if (b != 0)
        paramStringBuffer.setLength(i - j); 
    } 
  }
  
  protected void setArrayContentDetail(boolean paramBoolean) {
    this.arrayContentDetail = paramBoolean;
  }
  
  protected void setArrayEnd(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = ""; 
    this.arrayEnd = str;
  }
  
  protected void setArraySeparator(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = ""; 
    this.arraySeparator = str;
  }
  
  protected void setArrayStart(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = ""; 
    this.arrayStart = str;
  }
  
  protected void setContentEnd(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = ""; 
    this.contentEnd = str;
  }
  
  protected void setContentStart(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = ""; 
    this.contentStart = str;
  }
  
  protected void setDefaultFullDetail(boolean paramBoolean) {
    this.defaultFullDetail = paramBoolean;
  }
  
  protected void setFieldNameValueSeparator(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = ""; 
    this.fieldNameValueSeparator = str;
  }
  
  protected void setFieldSeparator(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = ""; 
    this.fieldSeparator = str;
  }
  
  protected void setFieldSeparatorAtEnd(boolean paramBoolean) {
    this.fieldSeparatorAtEnd = paramBoolean;
  }
  
  protected void setFieldSeparatorAtStart(boolean paramBoolean) {
    this.fieldSeparatorAtStart = paramBoolean;
  }
  
  protected void setNullText(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = ""; 
    this.nullText = str;
  }
  
  protected void setSizeEndText(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = ""; 
    this.sizeEndText = str;
  }
  
  protected void setSizeStartText(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = ""; 
    this.sizeStartText = str;
  }
  
  protected void setSummaryObjectEndText(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = ""; 
    this.summaryObjectEndText = str;
  }
  
  protected void setSummaryObjectStartText(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = ""; 
    this.summaryObjectStartText = str;
  }
  
  protected void setUseClassName(boolean paramBoolean) {
    this.useClassName = paramBoolean;
  }
  
  protected void setUseFieldNames(boolean paramBoolean) {
    this.useFieldNames = paramBoolean;
  }
  
  protected void setUseIdentityHashCode(boolean paramBoolean) {
    this.useIdentityHashCode = paramBoolean;
  }
  
  protected void setUseShortClassName(boolean paramBoolean) {
    this.useShortClassName = paramBoolean;
  }
  
  private static final class DefaultToStringStyle extends ToStringStyle {
    private static final long serialVersionUID = 1L;
    
    private Object readResolve() {
      return ToStringStyle.DEFAULT_STYLE;
    }
  }
  
  private static final class MultiLineToStringStyle extends ToStringStyle {
    private static final long serialVersionUID = 1L;
    
    MultiLineToStringStyle() {
      setContentStart("[");
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(SystemUtils.LINE_SEPARATOR);
      stringBuilder.append("  ");
      setFieldSeparator(stringBuilder.toString());
      setFieldSeparatorAtStart(true);
      stringBuilder = new StringBuilder();
      stringBuilder.append(SystemUtils.LINE_SEPARATOR);
      stringBuilder.append("]");
      setContentEnd(stringBuilder.toString());
    }
    
    private Object readResolve() {
      return ToStringStyle.MULTI_LINE_STYLE;
    }
  }
  
  private static final class NoFieldNameToStringStyle extends ToStringStyle {
    private static final long serialVersionUID = 1L;
    
    NoFieldNameToStringStyle() {
      setUseFieldNames(false);
    }
    
    private Object readResolve() {
      return ToStringStyle.NO_FIELD_NAMES_STYLE;
    }
  }
  
  private static final class ShortPrefixToStringStyle extends ToStringStyle {
    private static final long serialVersionUID = 1L;
    
    ShortPrefixToStringStyle() {
      setUseShortClassName(true);
      setUseIdentityHashCode(false);
    }
    
    private Object readResolve() {
      return ToStringStyle.SHORT_PREFIX_STYLE;
    }
  }
  
  private static final class SimpleToStringStyle extends ToStringStyle {
    private static final long serialVersionUID = 1L;
    
    SimpleToStringStyle() {
      setUseClassName(false);
      setUseIdentityHashCode(false);
      setUseFieldNames(false);
      setContentStart("");
      setContentEnd("");
    }
    
    private Object readResolve() {
      return ToStringStyle.SIMPLE_STYLE;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\builder\ToStringStyle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */