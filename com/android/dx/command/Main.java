package com.android.dx.command;

public class Main {
  private static final String USAGE_MESSAGE = "usage:\n  dx --dex [--debug] [--verbose] [--positions=<style>] [--no-locals]\n  [--no-optimize] [--statistics] [--[no-]optimize-list=<file>] [--no-strict]\n  [--keep-classes] [--output=<file>] [--dump-to=<file>] [--dump-width=<n>]\n  [--dump-method=<name>[*]] [--verbose-dump] [--no-files] [--core-library]\n  [--num-threads=<n>] [--incremental] [--force-jumbo] [--no-warning]\n  [--multi-dex [--main-dex-list=<file> [--minimal-main-dex]]\n  [--input-list=<file>] [--min-sdk-version=<n>]\n  [--allow-all-interface-method-invokes]\n  [<file>.class | <file>.{zip,jar,apk} | <directory>] ...\n    Convert a set of classfiles into a dex file, optionally embedded in a\n    jar/zip. Output name must end with one of: .dex .jar .zip .apk or be a\n    directory.\n    Positions options: none, important, lines.\n    --multi-dex: allows to generate several dex files if needed. This option is\n    exclusive with --incremental, causes --num-threads to be ignored and only\n    supports folder or archive output.\n    --main-dex-list=<file>: <file> is a list of class file names, classes\n    defined by those class files are put in classes.dex.\n    --minimal-main-dex: only classes selected by --main-dex-list are to be put\n    in the main dex.\n    --input-list: <file> is a list of inputs.\n    Each line in <file> must end with one of: .class .jar .zip .apk or be a\n    directory.\n    --min-sdk-version=<n>: Enable dex file features that require at least sdk\n    version <n>.\n  dx --annotool --annotation=<class> [--element=<element types>]\n  [--print=<print types>]\n  dx --dump [--debug] [--strict] [--bytes] [--optimize]\n  [--basic-blocks | --rop-blocks | --ssa-blocks | --dot] [--ssa-step=<step>]\n  [--width=<n>] [<file>.class | <file>.txt] ...\n    Dump classfiles, or transformations thereof, in a human-oriented format.\n  dx --find-usages <file.dex> <declaring type> <member>\n    Find references and declarations to a field or method.\n    <declaring type> is a class name in internal form, like Ljava/lang/Object;\n    <member> is a field or method name, like hashCode.\n  dx -J<option> ... <arguments, in one of the above forms>\n    Pass VM-specific options to the virtual machine that runs dx.\n  dx --version\n    Print the version of this tool (1.16).\n  dx --help\n    Print this message.";
  
  public static void main(String[] paramArrayOfString) {
    // Byte code:
    //   0: iconst_0
    //   1: istore_1
    //   2: iconst_0
    //   3: istore_2
    //   4: iconst_0
    //   5: istore_3
    //   6: iload_2
    //   7: istore #4
    //   9: iload_3
    //   10: aload_0
    //   11: arraylength
    //   12: if_icmpge -> 268
    //   15: aload_0
    //   16: iload_3
    //   17: aaload
    //   18: astore #5
    //   20: iload_1
    //   21: istore #4
    //   23: aload #5
    //   25: ldc '--'
    //   27: invokevirtual equals : (Ljava/lang/Object;)Z
    //   30: ifne -> 273
    //   33: aload #5
    //   35: ldc '--'
    //   37: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   40: istore #6
    //   42: iload #6
    //   44: ifne -> 53
    //   47: iload_1
    //   48: istore #4
    //   50: goto -> 273
    //   53: aload #5
    //   55: ldc '--dex'
    //   57: invokevirtual equals : (Ljava/lang/Object;)Z
    //   60: ifeq -> 74
    //   63: aload_0
    //   64: iload_3
    //   65: invokestatic without : ([Ljava/lang/String;I)[Ljava/lang/String;
    //   68: invokestatic main : ([Ljava/lang/String;)V
    //   71: goto -> 150
    //   74: aload #5
    //   76: ldc '--dump'
    //   78: invokevirtual equals : (Ljava/lang/Object;)Z
    //   81: ifeq -> 95
    //   84: aload_0
    //   85: iload_3
    //   86: invokestatic without : ([Ljava/lang/String;I)[Ljava/lang/String;
    //   89: invokestatic main : ([Ljava/lang/String;)V
    //   92: goto -> 150
    //   95: aload #5
    //   97: ldc '--annotool'
    //   99: invokevirtual equals : (Ljava/lang/Object;)Z
    //   102: ifeq -> 116
    //   105: aload_0
    //   106: iload_3
    //   107: invokestatic without : ([Ljava/lang/String;I)[Ljava/lang/String;
    //   110: invokestatic main : ([Ljava/lang/String;)V
    //   113: goto -> 150
    //   116: aload #5
    //   118: ldc '--find-usages'
    //   120: invokevirtual equals : (Ljava/lang/Object;)Z
    //   123: ifeq -> 137
    //   126: aload_0
    //   127: iload_3
    //   128: invokestatic without : ([Ljava/lang/String;I)[Ljava/lang/String;
    //   131: invokestatic main : ([Ljava/lang/String;)V
    //   134: goto -> 150
    //   137: aload #5
    //   139: ldc '--version'
    //   141: invokevirtual equals : (Ljava/lang/Object;)Z
    //   144: ifeq -> 158
    //   147: invokestatic version : ()V
    //   150: iconst_0
    //   151: istore_3
    //   152: iconst_1
    //   153: istore #4
    //   155: goto -> 275
    //   158: aload #5
    //   160: ldc '--help'
    //   162: invokevirtual equals : (Ljava/lang/Object;)Z
    //   165: istore #6
    //   167: iload #6
    //   169: ifeq -> 177
    //   172: iconst_1
    //   173: istore_3
    //   174: goto -> 152
    //   177: iinc #3, 1
    //   180: goto -> 6
    //   183: astore_0
    //   184: iconst_1
    //   185: istore_3
    //   186: goto -> 205
    //   189: astore_0
    //   190: iconst_1
    //   191: istore_3
    //   192: goto -> 249
    //   195: astore_0
    //   196: iconst_1
    //   197: istore #4
    //   199: goto -> 273
    //   202: astore_0
    //   203: iconst_0
    //   204: istore_3
    //   205: getstatic java/lang/System.err : Ljava/io/PrintStream;
    //   208: ldc '\\nUNEXPECTED TOP-LEVEL ERROR:'
    //   210: invokevirtual println : (Ljava/lang/String;)V
    //   213: aload_0
    //   214: invokevirtual printStackTrace : ()V
    //   217: aload_0
    //   218: instanceof java/lang/NoClassDefFoundError
    //   221: ifne -> 231
    //   224: aload_0
    //   225: instanceof java/lang/NoSuchMethodError
    //   228: ifeq -> 239
    //   231: getstatic java/lang/System.err : Ljava/io/PrintStream;
    //   234: ldc 'Note: You may be using an incompatible virtual machine or class library.\\n(This program is known to be incompatible with recent releases of GCJ.)'
    //   236: invokevirtual println : (Ljava/lang/String;)V
    //   239: iconst_3
    //   240: invokestatic exit : (I)V
    //   243: goto -> 265
    //   246: astore_0
    //   247: iconst_0
    //   248: istore_3
    //   249: getstatic java/lang/System.err : Ljava/io/PrintStream;
    //   252: ldc '\\nUNEXPECTED TOP-LEVEL EXCEPTION:'
    //   254: invokevirtual println : (Ljava/lang/String;)V
    //   257: aload_0
    //   258: invokevirtual printStackTrace : ()V
    //   261: iconst_2
    //   262: invokestatic exit : (I)V
    //   265: iload_3
    //   266: istore #4
    //   268: iconst_0
    //   269: istore_3
    //   270: goto -> 275
    //   273: iconst_1
    //   274: istore_3
    //   275: iload #4
    //   277: ifne -> 290
    //   280: getstatic java/lang/System.err : Ljava/io/PrintStream;
    //   283: ldc 'error: no command specified'
    //   285: invokevirtual println : (Ljava/lang/String;)V
    //   288: iconst_1
    //   289: istore_3
    //   290: iload_3
    //   291: ifeq -> 301
    //   294: invokestatic usage : ()V
    //   297: iconst_1
    //   298: invokestatic exit : (I)V
    //   301: return
    //   302: astore_0
    //   303: iload_1
    //   304: istore #4
    //   306: goto -> 273
    // Exception table:
    //   from	to	target	type
    //   9	15	302	com/android/dx/command/UsageException
    //   9	15	246	java/lang/RuntimeException
    //   9	15	202	finally
    //   23	42	302	com/android/dx/command/UsageException
    //   23	42	246	java/lang/RuntimeException
    //   23	42	202	finally
    //   53	71	195	com/android/dx/command/UsageException
    //   53	71	189	java/lang/RuntimeException
    //   53	71	183	finally
    //   74	92	195	com/android/dx/command/UsageException
    //   74	92	189	java/lang/RuntimeException
    //   74	92	183	finally
    //   95	113	195	com/android/dx/command/UsageException
    //   95	113	189	java/lang/RuntimeException
    //   95	113	183	finally
    //   116	134	195	com/android/dx/command/UsageException
    //   116	134	189	java/lang/RuntimeException
    //   116	134	183	finally
    //   137	150	195	com/android/dx/command/UsageException
    //   137	150	189	java/lang/RuntimeException
    //   137	150	183	finally
    //   158	167	195	com/android/dx/command/UsageException
    //   158	167	189	java/lang/RuntimeException
    //   158	167	183	finally
  }
  
  private static void usage() {
    System.err.println("usage:\n  dx --dex [--debug] [--verbose] [--positions=<style>] [--no-locals]\n  [--no-optimize] [--statistics] [--[no-]optimize-list=<file>] [--no-strict]\n  [--keep-classes] [--output=<file>] [--dump-to=<file>] [--dump-width=<n>]\n  [--dump-method=<name>[*]] [--verbose-dump] [--no-files] [--core-library]\n  [--num-threads=<n>] [--incremental] [--force-jumbo] [--no-warning]\n  [--multi-dex [--main-dex-list=<file> [--minimal-main-dex]]\n  [--input-list=<file>] [--min-sdk-version=<n>]\n  [--allow-all-interface-method-invokes]\n  [<file>.class | <file>.{zip,jar,apk} | <directory>] ...\n    Convert a set of classfiles into a dex file, optionally embedded in a\n    jar/zip. Output name must end with one of: .dex .jar .zip .apk or be a\n    directory.\n    Positions options: none, important, lines.\n    --multi-dex: allows to generate several dex files if needed. This option is\n    exclusive with --incremental, causes --num-threads to be ignored and only\n    supports folder or archive output.\n    --main-dex-list=<file>: <file> is a list of class file names, classes\n    defined by those class files are put in classes.dex.\n    --minimal-main-dex: only classes selected by --main-dex-list are to be put\n    in the main dex.\n    --input-list: <file> is a list of inputs.\n    Each line in <file> must end with one of: .class .jar .zip .apk or be a\n    directory.\n    --min-sdk-version=<n>: Enable dex file features that require at least sdk\n    version <n>.\n  dx --annotool --annotation=<class> [--element=<element types>]\n  [--print=<print types>]\n  dx --dump [--debug] [--strict] [--bytes] [--optimize]\n  [--basic-blocks | --rop-blocks | --ssa-blocks | --dot] [--ssa-step=<step>]\n  [--width=<n>] [<file>.class | <file>.txt] ...\n    Dump classfiles, or transformations thereof, in a human-oriented format.\n  dx --find-usages <file.dex> <declaring type> <member>\n    Find references and declarations to a field or method.\n    <declaring type> is a class name in internal form, like Ljava/lang/Object;\n    <member> is a field or method name, like hashCode.\n  dx -J<option> ... <arguments, in one of the above forms>\n    Pass VM-specific options to the virtual machine that runs dx.\n  dx --version\n    Print the version of this tool (1.16).\n  dx --help\n    Print this message.");
  }
  
  private static void version() {
    System.err.println("dx version 1.16");
    System.exit(0);
  }
  
  private static String[] without(String[] paramArrayOfString, int paramInt) {
    int i = paramArrayOfString.length - 1;
    String[] arrayOfString = new String[i];
    System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, paramInt);
    System.arraycopy(paramArrayOfString, paramInt + 1, arrayOfString, paramInt, i - paramInt);
    return arrayOfString;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\command\Main.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */