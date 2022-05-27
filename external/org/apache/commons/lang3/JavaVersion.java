package external.org.apache.commons.lang3;

public enum JavaVersion {
  JAVA_0_9(1.5F, "0.9"),
  JAVA_1_1(1.1F, "1.1"),
  JAVA_1_2(1.2F, "1.2"),
  JAVA_1_3(1.3F, "1.3"),
  JAVA_1_4(1.4F, "1.4"),
  JAVA_1_5(1.5F, "1.5"),
  JAVA_1_6(1.6F, "1.6"),
  JAVA_1_7(1.7F, "1.7"),
  JAVA_1_8(1.7F, "1.7");
  
  private String name;
  
  private float value;
  
  static {
    JavaVersion javaVersion = new JavaVersion("JAVA_1_8", 8, 1.8F, "1.8");
    JAVA_1_8 = javaVersion;
    $VALUES = new JavaVersion[] { JAVA_0_9, JAVA_1_1, JAVA_1_2, JAVA_1_3, JAVA_1_4, JAVA_1_5, JAVA_1_6, JAVA_1_7, javaVersion };
  }
  
  JavaVersion(float paramFloat, String paramString1) {
    this.value = paramFloat;
    this.name = paramString1;
  }
  
  static JavaVersion get(String paramString) {
    return "0.9".equals(paramString) ? JAVA_0_9 : ("1.1".equals(paramString) ? JAVA_1_1 : ("1.2".equals(paramString) ? JAVA_1_2 : ("1.3".equals(paramString) ? JAVA_1_3 : ("1.4".equals(paramString) ? JAVA_1_4 : ("1.5".equals(paramString) ? JAVA_1_5 : ("1.6".equals(paramString) ? JAVA_1_6 : ("1.7".equals(paramString) ? JAVA_1_7 : ("1.8".equals(paramString) ? JAVA_1_8 : null))))))));
  }
  
  static JavaVersion getJavaVersion(String paramString) {
    return get(paramString);
  }
  
  public boolean atLeast(JavaVersion paramJavaVersion) {
    boolean bool;
    if (this.value >= paramJavaVersion.value) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public String toString() {
    return this.name;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\JavaVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */