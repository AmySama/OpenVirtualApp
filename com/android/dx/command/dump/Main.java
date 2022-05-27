package com.android.dx.command.dump;

import com.android.dex.util.FileUtils;
import com.android.dx.cf.iface.ParseException;
import com.android.dx.util.HexParser;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Main {
  private final Args parsedArgs = new Args();
  
  public static void main(String[] paramArrayOfString) {
    (new Main()).run(paramArrayOfString);
  }
  
  private void processOne(String paramString, byte[] paramArrayOfbyte) {
    if (this.parsedArgs.dotDump) {
      DotDumper.dump(paramArrayOfbyte, paramString, this.parsedArgs);
    } else if (this.parsedArgs.basicBlocks) {
      BlockDumper.dump(paramArrayOfbyte, System.out, paramString, false, this.parsedArgs);
    } else if (this.parsedArgs.ropBlocks) {
      BlockDumper.dump(paramArrayOfbyte, System.out, paramString, true, this.parsedArgs);
    } else if (this.parsedArgs.ssaBlocks) {
      this.parsedArgs.optimize = false;
      SsaDumper.dump(paramArrayOfbyte, System.out, paramString, this.parsedArgs);
    } else {
      ClassDumper.dump(paramArrayOfbyte, System.out, paramString, this.parsedArgs);
    } 
  }
  
  private void run(String[] paramArrayOfString) {
    PrintStream printStream;
    byte b;
    for (b = 0; b < paramArrayOfString.length; b++) {
      String str = paramArrayOfString[b];
      if (str.equals("--") || !str.startsWith("--"))
        break; 
      if (str.equals("--bytes")) {
        this.parsedArgs.rawBytes = true;
      } else if (str.equals("--basic-blocks")) {
        this.parsedArgs.basicBlocks = true;
      } else if (str.equals("--rop-blocks")) {
        this.parsedArgs.ropBlocks = true;
      } else if (str.equals("--optimize")) {
        this.parsedArgs.optimize = true;
      } else if (str.equals("--ssa-blocks")) {
        this.parsedArgs.ssaBlocks = true;
      } else if (str.startsWith("--ssa-step=")) {
        this.parsedArgs.ssaStep = str.substring(str.indexOf('=') + 1);
      } else if (str.equals("--debug")) {
        this.parsedArgs.debug = true;
      } else if (str.equals("--dot")) {
        this.parsedArgs.dotDump = true;
      } else if (str.equals("--strict")) {
        this.parsedArgs.strictParse = true;
      } else if (str.startsWith("--width=")) {
        str = str.substring(str.indexOf('=') + 1);
        this.parsedArgs.width = Integer.parseInt(str);
      } else if (str.startsWith("--method=")) {
        str = str.substring(str.indexOf('=') + 1);
        this.parsedArgs.method = str;
      } else {
        printStream = System.err;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unknown option: ");
        stringBuilder.append(str);
        printStream.println(stringBuilder.toString());
        throw new RuntimeException("usage");
      } 
    } 
    if (b != printStream.length) {
      while (b < printStream.length) {
        PrintStream printStream1 = printStream[b];
        try {
          PrintStream printStream2 = System.out;
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("reading ");
          stringBuilder.append((String)printStream1);
          stringBuilder.append("...");
          printStream2.println(stringBuilder.toString());
          byte[] arrayOfByte2 = FileUtils.readFile((String)printStream1);
          boolean bool = printStream1.endsWith(".class");
          byte[] arrayOfByte1 = arrayOfByte2;
          if (!bool)
            try {
              String str = new String();
              this(arrayOfByte2, "utf-8");
              byte[] arrayOfByte = HexParser.parse(str);
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
              RuntimeException runtimeException = new RuntimeException();
              this("shouldn't happen", unsupportedEncodingException);
              throw runtimeException;
            }  
          processOne((String)printStream1, (byte[])unsupportedEncodingException);
        } catch (ParseException parseException) {
          System.err.println("\ntrouble parsing:");
          if (this.parsedArgs.debug) {
            parseException.printStackTrace();
          } else {
            parseException.printContext(System.err);
          } 
        } 
        b++;
      } 
      return;
    } 
    System.err.println("no input files specified");
    throw new RuntimeException("usage");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\command\dump\Main.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */