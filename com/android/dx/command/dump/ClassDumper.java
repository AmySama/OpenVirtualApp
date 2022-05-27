package com.android.dx.command.dump;

import com.android.dx.cf.direct.AttributeFactory;
import com.android.dx.cf.direct.DirectClassFile;
import com.android.dx.cf.direct.StdAttributeFactory;
import com.android.dx.util.ByteArray;
import java.io.PrintStream;

public final class ClassDumper extends BaseDumper {
  private ClassDumper(byte[] paramArrayOfbyte, PrintStream paramPrintStream, String paramString, Args paramArgs) {
    super(paramArrayOfbyte, paramPrintStream, paramString, paramArgs);
  }
  
  public static void dump(byte[] paramArrayOfbyte, PrintStream paramPrintStream, String paramString, Args paramArgs) {
    (new ClassDumper(paramArrayOfbyte, paramPrintStream, paramString, paramArgs)).dump();
  }
  
  public void dump() {
    byte[] arrayOfByte = getBytes();
    ByteArray byteArray = new ByteArray(arrayOfByte);
    DirectClassFile directClassFile = new DirectClassFile(byteArray, getFilePath(), getStrictParse());
    directClassFile.setAttributeFactory((AttributeFactory)StdAttributeFactory.THE_ONE);
    directClassFile.setObserver(this);
    directClassFile.getMagic();
    int i = getReadBytes();
    if (i != arrayOfByte.length)
      parsed(byteArray, i, arrayOfByte.length - i, "<extra data at end of file>"); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\command\dump\ClassDumper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */