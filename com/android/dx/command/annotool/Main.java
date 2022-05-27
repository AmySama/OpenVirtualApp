package com.android.dx.command.annotool;

import java.lang.annotation.ElementType;
import java.util.EnumSet;
import java.util.Locale;

public class Main {
  public static void main(String[] paramArrayOfString) {
    Arguments arguments = new Arguments();
    try {
      arguments.parse(paramArrayOfString);
      (new AnnotationLister(arguments)).process();
      return;
    } catch (InvalidArgumentException invalidArgumentException) {
      System.err.println(invalidArgumentException.getMessage());
      throw new RuntimeException("usage");
    } 
  }
  
  static class Arguments {
    String aclass;
    
    EnumSet<ElementType> eTypes = EnumSet.noneOf(ElementType.class);
    
    String[] files;
    
    EnumSet<Main.PrintType> printTypes = EnumSet.noneOf(Main.PrintType.class);
    
    void parse(String[] param1ArrayOfString) throws Main.InvalidArgumentException {
      for (byte b = 0;; b++) {
        if (b < param1ArrayOfString.length) {
          str = param1ArrayOfString[b];
          if (str.startsWith("--annotation=")) {
            str = str.substring(str.indexOf('=') + 1);
            if (this.aclass == null) {
              this.aclass = str.replace('.', '/');
            } else {
              throw new Main.InvalidArgumentException("--annotation can only be specified once.");
            } 
          } else if (str.startsWith("--element=")) {
            str = str.substring(str.indexOf('=') + 1);
            try {
              for (String str : str.split(","))
                this.eTypes.add(ElementType.valueOf(str.toUpperCase(Locale.ROOT))); 
            } catch (IllegalArgumentException illegalArgumentException) {
              throw new Main.InvalidArgumentException("invalid --element");
            } 
          } else {
            if (str.startsWith("--print=")) {
              str = str.substring(str.indexOf('=') + 1);
              try {
                for (String str : str.split(","))
                  this.printTypes.add(Main.PrintType.valueOf(str.toUpperCase(Locale.ROOT))); 
                b++;
              } catch (IllegalArgumentException illegalArgumentException) {
                throw new Main.InvalidArgumentException("invalid --print");
              } 
              continue;
            } 
            String[] arrayOfString = new String[illegalArgumentException.length - b];
            this.files = arrayOfString;
            System.arraycopy(illegalArgumentException, b, arrayOfString, 0, arrayOfString.length);
            break;
          } 
        } else {
          break;
        } 
      } 
      if (this.aclass != null) {
        if (this.printTypes.isEmpty())
          this.printTypes.add(Main.PrintType.CLASS); 
        if (this.eTypes.isEmpty())
          this.eTypes.add(ElementType.TYPE); 
        EnumSet<ElementType> enumSet = this.eTypes.clone();
        enumSet.remove(ElementType.TYPE);
        enumSet.remove(ElementType.PACKAGE);
        if (enumSet.isEmpty())
          return; 
        throw new Main.InvalidArgumentException("only --element parameters 'type' and 'package' supported");
      } 
      throw new Main.InvalidArgumentException("--annotation must be specified");
    }
  }
  
  private static class InvalidArgumentException extends Exception {
    InvalidArgumentException() {}
    
    InvalidArgumentException(String param1String) {
      super(param1String);
    }
  }
  
  enum PrintType {
    CLASS, INNERCLASS, METHOD, PACKAGE;
    
    static {
      PrintType printType = new PrintType("PACKAGE", 3);
      PACKAGE = printType;
      $VALUES = new PrintType[] { CLASS, INNERCLASS, METHOD, printType };
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\command\annotool\Main.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */