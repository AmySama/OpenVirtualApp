package com.android.dx.command.findusages;

import com.android.dex.Dex;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public final class Main {
  public static void main(String[] paramArrayOfString) throws IOException {
    String str2 = paramArrayOfString[0];
    String str3 = paramArrayOfString[1];
    String str1 = paramArrayOfString[2];
    Dex dex = new Dex(new File(str2));
    PrintWriter printWriter = new PrintWriter(System.out);
    (new FindUsages(dex, str3, str1, printWriter)).findUsages();
    printWriter.flush();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\command\findusages\Main.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */