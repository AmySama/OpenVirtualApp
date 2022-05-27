package com.android.dx.command.grep;

import com.android.dex.Dex;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

public final class Main {
  public static void main(String[] paramArrayOfString) throws IOException {
    boolean bool = false;
    String str2 = paramArrayOfString[0];
    String str1 = paramArrayOfString[1];
    if ((new Grep(new Dex(new File(str2)), Pattern.compile(str1), new PrintWriter(System.out))).grep() <= 0)
      bool = true; 
    System.exit(bool);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\command\grep\Main.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */