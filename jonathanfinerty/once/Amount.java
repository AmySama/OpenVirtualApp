package jonathanfinerty.once;

public class Amount {
  public static CountChecker exactly(final int numberOfTimes) {
    return new CountChecker() {
        public boolean check(int param1Int) {
          boolean bool;
          if (numberOfTimes == param1Int) {
            bool = true;
          } else {
            bool = false;
          } 
          return bool;
        }
      };
  }
  
  public static CountChecker lessThan(final int numberOfTimes) {
    return new CountChecker() {
        public boolean check(int param1Int) {
          boolean bool;
          if (param1Int < numberOfTimes) {
            bool = true;
          } else {
            bool = false;
          } 
          return bool;
        }
      };
  }
  
  public static CountChecker moreThan(final int numberOfTimes) {
    return new CountChecker() {
        public boolean check(int param1Int) {
          boolean bool;
          if (param1Int > numberOfTimes) {
            bool = true;
          } else {
            bool = false;
          } 
          return bool;
        }
      };
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\jonathanfinerty\once\Amount.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */