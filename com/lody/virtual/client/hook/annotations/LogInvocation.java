package com.lody.virtual.client.hook.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LogInvocation {
  Condition value() default Condition.ALWAYS;
  
  public enum Condition {
    ALWAYS,
    NEVER {
      public int getLogLevel(boolean param2Boolean1, boolean param2Boolean2) {
        return -1;
      }
    },
    NOT_HOOKED,
    ON_ERROR;
    
    static {
      null  = new null("NOT_HOOKED", 3);
      NOT_HOOKED = ;
      $VALUES = new Condition[] { NEVER, ALWAYS, ON_ERROR,  };
    }
    
    public abstract int getLogLevel(boolean param1Boolean1, boolean param1Boolean2);
  }
  
  enum null {
    public int getLogLevel(boolean param1Boolean1, boolean param1Boolean2) {
      return -1;
    }
  }
  
  enum null {
    public int getLogLevel(boolean param1Boolean1, boolean param1Boolean2) {
      byte b;
      if (param1Boolean2) {
        b = 5;
      } else {
        b = 4;
      } 
      return b;
    }
  }
  
  enum null {
    public int getLogLevel(boolean param1Boolean1, boolean param1Boolean2) {
      byte b;
      if (param1Boolean2) {
        b = 5;
      } else {
        b = -1;
      } 
      return b;
    }
  }
  
  enum null {
    public int getLogLevel(boolean param1Boolean1, boolean param1Boolean2) {
      byte b;
      if (param1Boolean1) {
        b = -1;
      } else if (param1Boolean2) {
        b = 5;
      } else {
        b = 4;
      } 
      return b;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\annotations\LogInvocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */