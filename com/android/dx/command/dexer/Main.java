package com.android.dx.command.dexer;

import com.android.dex.Dex;
import com.android.dex.DexException;
import com.android.dex.util.FileUtils;
import com.android.dx.cf.code.SimException;
import com.android.dx.cf.direct.AttributeFactory;
import com.android.dx.cf.direct.ClassPathOpener;
import com.android.dx.cf.direct.DirectClassFile;
import com.android.dx.cf.direct.StdAttributeFactory;
import com.android.dx.cf.iface.ParseException;
import com.android.dx.command.UsageException;
import com.android.dx.dex.DexOptions;
import com.android.dx.dex.cf.CfOptions;
import com.android.dx.dex.cf.CfTranslator;
import com.android.dx.dex.file.ClassDefItem;
import com.android.dx.dex.file.DexFile;
import com.android.dx.dex.file.EncodedMethod;
import com.android.dx.merge.CollisionPolicy;
import com.android.dx.merge.DexMerger;
import com.android.dx.rop.annotation.Annotation;
import com.android.dx.rop.annotation.Annotations;
import com.android.dx.rop.annotation.AnnotationsList;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Prototype;
import com.android.dx.rop.type.Type;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class Main {
  private static final Attributes.Name CREATED_BY = new Attributes.Name("Created-By");
  
  private static final String DEX_EXTENSION = ".dex";
  
  private static final String DEX_PREFIX = "classes";
  
  private static final String IN_RE_CORE_CLASSES = "Ill-advised or mistaken usage of a core class (java.* or javax.*)\nwhen not building a core library.\n\nThis is often due to inadvertently including a core library file\nin your application's project, when using an IDE (such as\nEclipse). If you are sure you're not intentionally defining a\ncore class, then this is the most likely explanation of what's\ngoing on.\n\nHowever, you might actually be trying to define a class in a core\nnamespace, the source of which you may have taken, for example,\nfrom a non-Android virtual machine project. This will most\nassuredly not work. At a minimum, it jeopardizes the\ncompatibility of your app with future versions of the platform.\nIt is also often of questionable legality.\n\nIf you really intend to build a core library -- which is only\nappropriate as part of creating a full virtual machine\ndistribution, as opposed to compiling an application -- then use\nthe \"--core-library\" option to suppress this error message.\n\nIf you go ahead and use \"--core-library\" but are in fact\nbuilding an application, then be forewarned that your application\nwill still fail to build or run, at some point. Please be\nprepared for angry customers who find, for example, that your\napplication ceases to function once they upgrade their operating\nsystem. You will be to blame for this problem.\n\nIf you are legitimately using some code that happens to be in a\ncore package, then the easiest safe alternative you have is to\nrepackage that code. That is, move the classes in question into\nyour own package namespace. This means that they will never be in\nconflict with core system classes. JarJar is a tool that may help\nyou in this endeavor. If you find that you cannot do this, then\nthat is an indication that the path you are on will ultimately\nlead to pain, suffering, grief, and lamentation.\n";
  
  private static final String[] JAVAX_CORE = new String[] { 
      "accessibility", "crypto", "imageio", "management", "naming", "net", "print", "rmi", "security", "sip", 
      "sound", "sql", "swing", "transaction", "xml" };
  
  private static final String MANIFEST_NAME = "META-INF/MANIFEST.MF";
  
  private static final int MAX_FIELD_ADDED_DURING_DEX_CREATION = 9;
  
  private static final int MAX_METHOD_ADDED_DURING_DEX_CREATION = 2;
  
  private List<Future<Boolean>> addToDexFutures = new ArrayList<Future<Boolean>>();
  
  private volatile boolean anyFilesProcessed;
  
  private Arguments args;
  
  private ExecutorService classDefItemConsumer;
  
  private ExecutorService classTranslatorPool;
  
  private Set<String> classesInMainDex = null;
  
  private final DxContext context;
  
  private ExecutorService dexOutPool;
  
  private List<byte[]> dexOutputArrays = (List)new ArrayList<byte>();
  
  private List<Future<byte[]>> dexOutputFutures = new ArrayList<Future<byte[]>>();
  
  private Object dexRotationLock = new Object();
  
  private AtomicInteger errors = new AtomicInteger(0);
  
  private OutputStreamWriter humanOutWriter = null;
  
  private final List<byte[]> libraryDexBuffers = (List)new ArrayList<byte>();
  
  private int maxFieldIdsInProcess = 0;
  
  private int maxMethodIdsInProcess = 0;
  
  private long minimumFileAge = 0L;
  
  private DexFile outputDex;
  
  private TreeMap<String, byte[]> outputResources;
  
  public Main(DxContext paramDxContext) {
    this.context = paramDxContext;
  }
  
  private boolean addClassToDex(ClassDefItem paramClassDefItem) {
    synchronized (this.outputDex) {
      this.outputDex.add(paramClassDefItem);
      return true;
    } 
  }
  
  private void checkClassName(String paramString) {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 'java/'
    //   4: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   7: istore_2
    //   8: iconst_0
    //   9: istore_3
    //   10: iload_2
    //   11: ifeq -> 20
    //   14: iconst_1
    //   15: istore #4
    //   17: goto -> 79
    //   20: iload_3
    //   21: istore #4
    //   23: aload_1
    //   24: ldc_w 'javax/'
    //   27: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   30: ifeq -> 79
    //   33: aload_1
    //   34: bipush #47
    //   36: bipush #6
    //   38: invokevirtual indexOf : (II)I
    //   41: istore #4
    //   43: iload #4
    //   45: iconst_m1
    //   46: if_icmpne -> 52
    //   49: goto -> 14
    //   52: aload_1
    //   53: bipush #6
    //   55: iload #4
    //   57: invokevirtual substring : (II)Ljava/lang/String;
    //   60: astore #5
    //   62: iload_3
    //   63: istore #4
    //   65: getstatic com/android/dx/command/dexer/Main.JAVAX_CORE : [Ljava/lang/String;
    //   68: aload #5
    //   70: invokestatic binarySearch : ([Ljava/lang/Object;Ljava/lang/Object;)I
    //   73: iflt -> 79
    //   76: goto -> 14
    //   79: iload #4
    //   81: ifne -> 85
    //   84: return
    //   85: aload_0
    //   86: getfield context : Lcom/android/dx/command/dexer/DxContext;
    //   89: getfield err : Ljava/io/PrintStream;
    //   92: astore #5
    //   94: new java/lang/StringBuilder
    //   97: dup
    //   98: invokespecial <init> : ()V
    //   101: astore #6
    //   103: aload #6
    //   105: ldc_w '\\ntrouble processing "'
    //   108: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: pop
    //   112: aload #6
    //   114: aload_1
    //   115: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: pop
    //   119: aload #6
    //   121: ldc_w '":\\n\\n'
    //   124: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   127: pop
    //   128: aload #6
    //   130: ldc 'Ill-advised or mistaken usage of a core class (java.* or javax.*)\\nwhen not building a core library.\\n\\nThis is often due to inadvertently including a core library file\\nin your application's project, when using an IDE (such as\\nEclipse). If you are sure you're not intentionally defining a\\ncore class, then this is the most likely explanation of what's\\ngoing on.\\n\\nHowever, you might actually be trying to define a class in a core\\nnamespace, the source of which you may have taken, for example,\\nfrom a non-Android virtual machine project. This will most\\nassuredly not work. At a minimum, it jeopardizes the\\ncompatibility of your app with future versions of the platform.\\nIt is also often of questionable legality.\\n\\nIf you really intend to build a core library -- which is only\\nappropriate as part of creating a full virtual machine\\ndistribution, as opposed to compiling an application -- then use\\nthe "--core-library" option to suppress this error message.\\n\\nIf you go ahead and use "--core-library" but are in fact\\nbuilding an application, then be forewarned that your application\\nwill still fail to build or run, at some point. Please be\\nprepared for angry customers who find, for example, that your\\napplication ceases to function once they upgrade their operating\\nsystem. You will be to blame for this problem.\\n\\nIf you are legitimately using some code that happens to be in a\\ncore package, then the easiest safe alternative you have is to\\nrepackage that code. That is, move the classes in question into\\nyour own package namespace. This means that they will never be in\\nconflict with core system classes. JarJar is a tool that may help\\nyou in this endeavor. If you find that you cannot do this, then\\nthat is an indication that the path you are on will ultimately\\nlead to pain, suffering, grief, and lamentation.\\n'
    //   132: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: pop
    //   136: aload #5
    //   138: aload #6
    //   140: invokevirtual toString : ()Ljava/lang/String;
    //   143: invokevirtual println : (Ljava/lang/String;)V
    //   146: aload_0
    //   147: getfield errors : Ljava/util/concurrent/atomic/AtomicInteger;
    //   150: invokevirtual incrementAndGet : ()I
    //   153: pop
    //   154: new com/android/dx/command/dexer/Main$StopProcessing
    //   157: dup
    //   158: aconst_null
    //   159: invokespecial <init> : (Lcom/android/dx/command/dexer/Main$1;)V
    //   162: athrow
  }
  
  public static void clearInternTables() {
    Prototype.clearInternTable();
    RegisterSpec.clearInternTable();
    CstType.clearInternTable();
    Type.clearInternTable();
  }
  
  private void closeOutput(OutputStream paramOutputStream) throws IOException {
    if (paramOutputStream == null)
      return; 
    paramOutputStream.flush();
    if (paramOutputStream != this.context.out)
      paramOutputStream.close(); 
  }
  
  private void createDexFile() {
    this.outputDex = new DexFile(this.args.dexOptions);
    if (this.args.dumpWidth != 0)
      this.outputDex.setDumpWidth(this.args.dumpWidth); 
  }
  
  private boolean createJar(String paramString) {
    try {
      null = makeManifest();
      OutputStream outputStream = openOutput(paramString);
      JarOutputStream jarOutputStream = new JarOutputStream();
      this(outputStream, null);
      try {
        for (Map.Entry<String, byte> entry : this.outputResources.entrySet()) {
          String str = (String)entry.getKey();
          byte[] arrayOfByte = (byte[])entry.getValue();
          JarEntry jarEntry = new JarEntry();
          this(str);
          int i = arrayOfByte.length;
          if (this.args.verbose) {
            PrintStream printStream = this.context.out;
            StringBuilder stringBuilder = new StringBuilder();
            this();
            stringBuilder.append("writing ");
            stringBuilder.append(str);
            stringBuilder.append("; size ");
            stringBuilder.append(i);
            stringBuilder.append("...");
            printStream.println(stringBuilder.toString());
          } 
          jarEntry.setSize(i);
          jarOutputStream.putNextEntry(jarEntry);
          jarOutputStream.write(arrayOfByte);
          jarOutputStream.closeEntry();
        } 
        return true;
      } finally {
        jarOutputStream.finish();
        jarOutputStream.flush();
        closeOutput(outputStream);
      } 
    } catch (Exception exception) {
      if (this.args.debug) {
        this.context.err.println("\ntrouble writing output:");
        exception.printStackTrace(this.context.err);
      } else {
        PrintStream printStream = this.context.err;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\ntrouble writing output: ");
        stringBuilder.append(exception.getMessage());
        printStream.println(stringBuilder.toString());
      } 
      return false;
    } 
  }
  
  private void dumpMethod(DexFile paramDexFile, String paramString, OutputStreamWriter paramOutputStreamWriter) {
    PrintStream printStream1;
    StringBuilder stringBuilder;
    PrintStream printStream2;
    boolean bool = paramString.endsWith("*");
    int i = paramString.lastIndexOf('.');
    if (i <= 0 || i == paramString.length() - 1) {
      printStream1 = this.context.err;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("bogus fully-qualified method name: ");
      stringBuilder1.append(paramString);
      printStream1.println(stringBuilder1.toString());
      return;
    } 
    String str2 = paramString.substring(0, i).replace('.', '/');
    String str3 = paramString.substring(i + 1);
    ClassDefItem classDefItem = printStream1.getClassOrNull(str2);
    if (classDefItem == null) {
      printStream1 = this.context.err;
      stringBuilder = new StringBuilder();
      stringBuilder.append("no such class: ");
      stringBuilder.append(str2);
      printStream1.println(stringBuilder.toString());
      return;
    } 
    String str1 = str3;
    if (bool)
      str1 = str3.substring(0, str3.length() - 1); 
    ArrayList arrayList = classDefItem.getMethods();
    TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
    for (EncodedMethod encodedMethod : arrayList) {
      String str = encodedMethod.getName().getString();
      if ((bool && str.startsWith(str1)) || (!bool && str.equals(str1)))
        treeMap.put(encodedMethod.getRef().getNat(), encodedMethod); 
    } 
    if (treeMap.size() == 0) {
      printStream2 = this.context.err;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("no such method: ");
      stringBuilder1.append((String)stringBuilder);
      printStream2.println(stringBuilder1.toString());
      return;
    } 
    PrintWriter printWriter = new PrintWriter((Writer)printStream2);
    for (EncodedMethod encodedMethod : treeMap.values()) {
      encodedMethod.debugPrint(printWriter, this.args.verboseDump);
      CstString cstString = classDefItem.getSourceFile();
      if (cstString != null) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("  source file: ");
        stringBuilder1.append(cstString.toQuoted());
        printWriter.println(stringBuilder1.toString());
      } 
      Annotations annotations = classDefItem.getMethodAnnotations(encodedMethod.getRef());
      AnnotationsList annotationsList = classDefItem.getParameterAnnotations(encodedMethod.getRef());
      if (annotations != null) {
        printWriter.println("  method annotations:");
        for (Annotation annotation : annotations.getAnnotations()) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("    ");
          stringBuilder1.append(annotation);
          printWriter.println(stringBuilder1.toString());
        } 
      } 
      if (annotationsList != null) {
        printWriter.println("  parameter annotations:");
        int j = annotationsList.size();
        for (i = 0; i < j; i++) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("    parameter ");
          stringBuilder1.append(i);
          printWriter.println(stringBuilder1.toString());
          for (Annotation annotation : annotationsList.get(i).getAnnotations()) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("      ");
            stringBuilder2.append(annotation);
            printWriter.println(stringBuilder2.toString());
          } 
        } 
      } 
    } 
    printWriter.flush();
  }
  
  private static String fixPath(String paramString) {
    String str = paramString;
    if (File.separatorChar == '\\')
      str = paramString.replace('\\', '/'); 
    int i = str.lastIndexOf("/./");
    if (i != -1)
      return str.substring(i + 3); 
    paramString = str;
    if (str.startsWith("./"))
      paramString = str.substring(2); 
    return paramString;
  }
  
  private static String getDexFileName(int paramInt) {
    if (paramInt == 0)
      return "classes.dex"; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("classes");
    stringBuilder.append(paramInt + 1);
    stringBuilder.append(".dex");
    return stringBuilder.toString();
  }
  
  public static void main(String[] paramArrayOfString) throws IOException {
    DxContext dxContext = new DxContext();
    Arguments arguments = new Arguments(dxContext);
    arguments.parse(paramArrayOfString);
    int i = (new Main(dxContext)).runDx(arguments);
    if (i != 0)
      System.exit(i); 
  }
  
  private Manifest makeManifest() throws IOException {
    Manifest manifest;
    Attributes attributes;
    byte[] arrayOfByte = this.outputResources.get("META-INF/MANIFEST.MF");
    if (arrayOfByte == null) {
      manifest = new Manifest();
      attributes = manifest.getMainAttributes();
      attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
    } else {
      manifest = new Manifest(new ByteArrayInputStream((byte[])manifest));
      attributes = manifest.getMainAttributes();
      this.outputResources.remove("META-INF/MANIFEST.MF");
    } 
    String str = attributes.getValue(CREATED_BY);
    if (str == null) {
      str = "";
    } else {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append(" + ");
      str = stringBuilder1.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append("dx 1.16");
    str = stringBuilder.toString();
    attributes.put(CREATED_BY, str);
    attributes.putValue("Dex-Location", "classes.dex");
    return manifest;
  }
  
  private byte[] mergeIncremental(byte[] paramArrayOfbyte, File paramFile) throws IOException {
    File file;
    Dex dex;
    if (paramArrayOfbyte != null) {
      dex = new Dex(paramArrayOfbyte);
    } else {
      paramArrayOfbyte = null;
    } 
    if (paramFile.exists()) {
      Dex dex1 = new Dex(paramFile);
    } else {
      paramFile = null;
    } 
    if (paramArrayOfbyte == null && paramFile == null)
      return null; 
    if (paramArrayOfbyte == null) {
      file = paramFile;
    } else if (paramFile != null) {
      CollisionPolicy collisionPolicy = CollisionPolicy.KEEP_FIRST;
      DxContext dxContext = this.context;
      dex = (new DexMerger(new Dex[] { (Dex)file, (Dex)paramFile }, collisionPolicy, dxContext)).merge();
    } 
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    dex.writeTo(byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
  }
  
  private byte[] mergeLibraryDexBuffers(byte[] paramArrayOfbyte) throws IOException {
    ArrayList<Dex> arrayList = new ArrayList();
    if (paramArrayOfbyte != null)
      arrayList.add(new Dex(paramArrayOfbyte)); 
    Iterator<byte> iterator = this.libraryDexBuffers.iterator();
    while (iterator.hasNext())
      arrayList.add(new Dex((byte[])iterator.next())); 
    return arrayList.isEmpty() ? null : (new DexMerger(arrayList.<Dex>toArray(new Dex[arrayList.size()]), CollisionPolicy.FAIL, this.context)).merge().getBytes();
  }
  
  private OutputStream openOutput(String paramString) throws IOException {
    return (OutputStream)((paramString.equals("-") || paramString.startsWith("-.")) ? this.context.out : new FileOutputStream(paramString));
  }
  
  private DirectClassFile parseClass(String paramString, byte[] paramArrayOfbyte) {
    DirectClassFile directClassFile = new DirectClassFile(paramArrayOfbyte, paramString, this.args.cfOptions.strictNameCheck);
    directClassFile.setAttributeFactory((AttributeFactory)StdAttributeFactory.THE_ONE);
    directClassFile.getMagic();
    return directClassFile;
  }
  
  private boolean processAllFiles() {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial createDexFile : ()V
    //   4: aload_0
    //   5: getfield args : Lcom/android/dx/command/dexer/Main$Arguments;
    //   8: getfield jarOutput : Z
    //   11: ifeq -> 25
    //   14: aload_0
    //   15: new java/util/TreeMap
    //   18: dup
    //   19: invokespecial <init> : ()V
    //   22: putfield outputResources : Ljava/util/TreeMap;
    //   25: aload_0
    //   26: iconst_0
    //   27: putfield anyFilesProcessed : Z
    //   30: aload_0
    //   31: getfield args : Lcom/android/dx/command/dexer/Main$Arguments;
    //   34: getfield fileNames : [Ljava/lang/String;
    //   37: astore_1
    //   38: aload_1
    //   39: invokestatic sort : ([Ljava/lang/Object;)V
    //   42: aload_0
    //   43: new java/util/concurrent/ThreadPoolExecutor
    //   46: dup
    //   47: aload_0
    //   48: getfield args : Lcom/android/dx/command/dexer/Main$Arguments;
    //   51: getfield numThreads : I
    //   54: aload_0
    //   55: getfield args : Lcom/android/dx/command/dexer/Main$Arguments;
    //   58: getfield numThreads : I
    //   61: lconst_0
    //   62: getstatic java/util/concurrent/TimeUnit.SECONDS : Ljava/util/concurrent/TimeUnit;
    //   65: new java/util/concurrent/ArrayBlockingQueue
    //   68: dup
    //   69: aload_0
    //   70: getfield args : Lcom/android/dx/command/dexer/Main$Arguments;
    //   73: getfield numThreads : I
    //   76: iconst_2
    //   77: imul
    //   78: iconst_1
    //   79: invokespecial <init> : (IZ)V
    //   82: new java/util/concurrent/ThreadPoolExecutor$CallerRunsPolicy
    //   85: dup
    //   86: invokespecial <init> : ()V
    //   89: invokespecial <init> : (IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;Ljava/util/concurrent/RejectedExecutionHandler;)V
    //   92: putfield classTranslatorPool : Ljava/util/concurrent/ExecutorService;
    //   95: aload_0
    //   96: invokestatic newSingleThreadExecutor : ()Ljava/util/concurrent/ExecutorService;
    //   99: putfield classDefItemConsumer : Ljava/util/concurrent/ExecutorService;
    //   102: aload_0
    //   103: getfield args : Lcom/android/dx/command/dexer/Main$Arguments;
    //   106: getfield mainDexListFile : Ljava/lang/String;
    //   109: ifnull -> 300
    //   112: aload_0
    //   113: getfield args : Lcom/android/dx/command/dexer/Main$Arguments;
    //   116: getfield strictNameCheck : Z
    //   119: ifeq -> 135
    //   122: new com/android/dx/command/dexer/Main$MainDexListFilter
    //   125: astore_2
    //   126: aload_2
    //   127: aload_0
    //   128: aconst_null
    //   129: invokespecial <init> : (Lcom/android/dx/command/dexer/Main;Lcom/android/dx/command/dexer/Main$1;)V
    //   132: goto -> 144
    //   135: new com/android/dx/command/dexer/Main$BestEffortMainDexListFilter
    //   138: dup
    //   139: aload_0
    //   140: invokespecial <init> : (Lcom/android/dx/command/dexer/Main;)V
    //   143: astore_2
    //   144: iconst_0
    //   145: istore_3
    //   146: iload_3
    //   147: aload_1
    //   148: arraylength
    //   149: if_icmpge -> 166
    //   152: aload_0
    //   153: aload_1
    //   154: iload_3
    //   155: aaload
    //   156: aload_2
    //   157: invokespecial processOne : (Ljava/lang/String;Lcom/android/dx/cf/direct/ClassPathOpener$FileNameFilter;)V
    //   160: iinc #3, 1
    //   163: goto -> 146
    //   166: aload_0
    //   167: getfield dexOutputFutures : Ljava/util/List;
    //   170: invokeinterface size : ()I
    //   175: ifgt -> 287
    //   178: aload_0
    //   179: getfield args : Lcom/android/dx/command/dexer/Main$Arguments;
    //   182: getfield minimalMainDex : Z
    //   185: ifeq -> 240
    //   188: aload_0
    //   189: getfield dexRotationLock : Ljava/lang/Object;
    //   192: astore #4
    //   194: aload #4
    //   196: monitorenter
    //   197: aload_0
    //   198: getfield maxMethodIdsInProcess : I
    //   201: ifgt -> 224
    //   204: aload_0
    //   205: getfield maxFieldIdsInProcess : I
    //   208: ifle -> 214
    //   211: goto -> 224
    //   214: aload #4
    //   216: monitorexit
    //   217: aload_0
    //   218: invokespecial rotateDexFile : ()V
    //   221: goto -> 240
    //   224: aload_0
    //   225: getfield dexRotationLock : Ljava/lang/Object;
    //   228: invokevirtual wait : ()V
    //   231: goto -> 197
    //   234: astore_2
    //   235: aload #4
    //   237: monitorexit
    //   238: aload_2
    //   239: athrow
    //   240: new com/android/dx/command/dexer/Main$RemoveModuleInfoFilter
    //   243: astore #4
    //   245: new com/android/dx/command/dexer/Main$NotFilter
    //   248: astore #5
    //   250: aload #5
    //   252: aload_2
    //   253: aconst_null
    //   254: invokespecial <init> : (Lcom/android/dx/cf/direct/ClassPathOpener$FileNameFilter;Lcom/android/dx/command/dexer/Main$1;)V
    //   257: aload #4
    //   259: aload #5
    //   261: invokespecial <init> : (Lcom/android/dx/cf/direct/ClassPathOpener$FileNameFilter;)V
    //   264: iconst_0
    //   265: istore_3
    //   266: iload_3
    //   267: aload_1
    //   268: arraylength
    //   269: if_icmpge -> 333
    //   272: aload_0
    //   273: aload_1
    //   274: iload_3
    //   275: aaload
    //   276: aload #4
    //   278: invokespecial processOne : (Ljava/lang/String;Lcom/android/dx/cf/direct/ClassPathOpener$FileNameFilter;)V
    //   281: iinc #3, 1
    //   284: goto -> 266
    //   287: new com/android/dex/DexException
    //   290: astore_2
    //   291: aload_2
    //   292: ldc_w 'Too many classes in --main-dex-list, main dex capacity exceeded'
    //   295: invokespecial <init> : (Ljava/lang/String;)V
    //   298: aload_2
    //   299: athrow
    //   300: new com/android/dx/command/dexer/Main$RemoveModuleInfoFilter
    //   303: astore_2
    //   304: aload_2
    //   305: getstatic com/android/dx/cf/direct/ClassPathOpener.acceptAll : Lcom/android/dx/cf/direct/ClassPathOpener$FileNameFilter;
    //   308: invokespecial <init> : (Lcom/android/dx/cf/direct/ClassPathOpener$FileNameFilter;)V
    //   311: iconst_0
    //   312: istore_3
    //   313: iload_3
    //   314: aload_1
    //   315: arraylength
    //   316: if_icmpge -> 333
    //   319: aload_0
    //   320: aload_1
    //   321: iload_3
    //   322: aaload
    //   323: aload_2
    //   324: invokespecial processOne : (Ljava/lang/String;Lcom/android/dx/cf/direct/ClassPathOpener$FileNameFilter;)V
    //   327: iinc #3, 1
    //   330: goto -> 313
    //   333: aload_0
    //   334: getfield classTranslatorPool : Ljava/util/concurrent/ExecutorService;
    //   337: invokeinterface shutdown : ()V
    //   342: aload_0
    //   343: getfield classTranslatorPool : Ljava/util/concurrent/ExecutorService;
    //   346: ldc2_w 600
    //   349: getstatic java/util/concurrent/TimeUnit.SECONDS : Ljava/util/concurrent/TimeUnit;
    //   352: invokeinterface awaitTermination : (JLjava/util/concurrent/TimeUnit;)Z
    //   357: pop
    //   358: aload_0
    //   359: getfield classDefItemConsumer : Ljava/util/concurrent/ExecutorService;
    //   362: invokeinterface shutdown : ()V
    //   367: aload_0
    //   368: getfield classDefItemConsumer : Ljava/util/concurrent/ExecutorService;
    //   371: ldc2_w 600
    //   374: getstatic java/util/concurrent/TimeUnit.SECONDS : Ljava/util/concurrent/TimeUnit;
    //   377: invokeinterface awaitTermination : (JLjava/util/concurrent/TimeUnit;)Z
    //   382: pop
    //   383: aload_0
    //   384: getfield addToDexFutures : Ljava/util/List;
    //   387: invokeinterface iterator : ()Ljava/util/Iterator;
    //   392: astore_2
    //   393: aload_2
    //   394: invokeinterface hasNext : ()Z
    //   399: ifeq -> 537
    //   402: aload_2
    //   403: invokeinterface next : ()Ljava/lang/Object;
    //   408: checkcast java/util/concurrent/Future
    //   411: astore_1
    //   412: aload_1
    //   413: invokeinterface get : ()Ljava/lang/Object;
    //   418: pop
    //   419: goto -> 393
    //   422: astore #4
    //   424: aload_0
    //   425: getfield errors : Ljava/util/concurrent/atomic/AtomicInteger;
    //   428: invokevirtual incrementAndGet : ()I
    //   431: bipush #10
    //   433: if_icmpge -> 524
    //   436: aload_0
    //   437: getfield args : Lcom/android/dx/command/dexer/Main$Arguments;
    //   440: getfield debug : Z
    //   443: ifeq -> 477
    //   446: aload_0
    //   447: getfield context : Lcom/android/dx/command/dexer/DxContext;
    //   450: getfield err : Ljava/io/PrintStream;
    //   453: ldc_w 'Uncaught translation error:'
    //   456: invokevirtual println : (Ljava/lang/String;)V
    //   459: aload #4
    //   461: invokevirtual getCause : ()Ljava/lang/Throwable;
    //   464: aload_0
    //   465: getfield context : Lcom/android/dx/command/dexer/DxContext;
    //   468: getfield err : Ljava/io/PrintStream;
    //   471: invokevirtual printStackTrace : (Ljava/io/PrintStream;)V
    //   474: goto -> 393
    //   477: aload_0
    //   478: getfield context : Lcom/android/dx/command/dexer/DxContext;
    //   481: getfield err : Ljava/io/PrintStream;
    //   484: astore #5
    //   486: new java/lang/StringBuilder
    //   489: astore_1
    //   490: aload_1
    //   491: invokespecial <init> : ()V
    //   494: aload_1
    //   495: ldc_w 'Uncaught translation error: '
    //   498: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   501: pop
    //   502: aload_1
    //   503: aload #4
    //   505: invokevirtual getCause : ()Ljava/lang/Throwable;
    //   508: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   511: pop
    //   512: aload #5
    //   514: aload_1
    //   515: invokevirtual toString : ()Ljava/lang/String;
    //   518: invokevirtual println : (Ljava/lang/String;)V
    //   521: goto -> 393
    //   524: new java/lang/InterruptedException
    //   527: astore_2
    //   528: aload_2
    //   529: ldc_w 'Too many errors'
    //   532: invokespecial <init> : (Ljava/lang/String;)V
    //   535: aload_2
    //   536: athrow
    //   537: aload_0
    //   538: getfield errors : Ljava/util/concurrent/atomic/AtomicInteger;
    //   541: invokevirtual get : ()I
    //   544: istore_3
    //   545: iload_3
    //   546: ifeq -> 625
    //   549: aload_0
    //   550: getfield context : Lcom/android/dx/command/dexer/DxContext;
    //   553: getfield err : Ljava/io/PrintStream;
    //   556: astore_1
    //   557: new java/lang/StringBuilder
    //   560: dup
    //   561: invokespecial <init> : ()V
    //   564: astore #4
    //   566: aload #4
    //   568: iload_3
    //   569: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   572: pop
    //   573: aload #4
    //   575: ldc_w ' error'
    //   578: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   581: pop
    //   582: iload_3
    //   583: iconst_1
    //   584: if_icmpne -> 594
    //   587: ldc_w ''
    //   590: astore_2
    //   591: goto -> 598
    //   594: ldc_w 's'
    //   597: astore_2
    //   598: aload #4
    //   600: aload_2
    //   601: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   604: pop
    //   605: aload #4
    //   607: ldc_w '; aborting'
    //   610: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   613: pop
    //   614: aload_1
    //   615: aload #4
    //   617: invokevirtual toString : ()Ljava/lang/String;
    //   620: invokevirtual println : (Ljava/lang/String;)V
    //   623: iconst_0
    //   624: ireturn
    //   625: aload_0
    //   626: getfield args : Lcom/android/dx/command/dexer/Main$Arguments;
    //   629: getfield incremental : Z
    //   632: ifeq -> 644
    //   635: aload_0
    //   636: getfield anyFilesProcessed : Z
    //   639: ifne -> 644
    //   642: iconst_1
    //   643: ireturn
    //   644: aload_0
    //   645: getfield anyFilesProcessed : Z
    //   648: ifne -> 676
    //   651: aload_0
    //   652: getfield args : Lcom/android/dx/command/dexer/Main$Arguments;
    //   655: getfield emptyOk : Z
    //   658: ifne -> 676
    //   661: aload_0
    //   662: getfield context : Lcom/android/dx/command/dexer/DxContext;
    //   665: getfield err : Ljava/io/PrintStream;
    //   668: ldc_w 'no classfiles specified'
    //   671: invokevirtual println : (Ljava/lang/String;)V
    //   674: iconst_0
    //   675: ireturn
    //   676: aload_0
    //   677: getfield args : Lcom/android/dx/command/dexer/Main$Arguments;
    //   680: getfield optimize : Z
    //   683: ifeq -> 713
    //   686: aload_0
    //   687: getfield args : Lcom/android/dx/command/dexer/Main$Arguments;
    //   690: getfield statistics : Z
    //   693: ifeq -> 713
    //   696: aload_0
    //   697: getfield context : Lcom/android/dx/command/dexer/DxContext;
    //   700: getfield codeStatistics : Lcom/android/dx/dex/cf/CodeStatistics;
    //   703: aload_0
    //   704: getfield context : Lcom/android/dx/command/dexer/DxContext;
    //   707: getfield out : Ljava/io/PrintStream;
    //   710: invokevirtual dumpStatistics : (Ljava/io/PrintStream;)V
    //   713: iconst_1
    //   714: ireturn
    //   715: astore_2
    //   716: aload_0
    //   717: getfield classTranslatorPool : Ljava/util/concurrent/ExecutorService;
    //   720: invokeinterface shutdownNow : ()Ljava/util/List;
    //   725: pop
    //   726: aload_0
    //   727: getfield classDefItemConsumer : Ljava/util/concurrent/ExecutorService;
    //   730: invokeinterface shutdownNow : ()Ljava/util/List;
    //   735: pop
    //   736: aload_2
    //   737: aload_0
    //   738: getfield context : Lcom/android/dx/command/dexer/DxContext;
    //   741: getfield out : Ljava/io/PrintStream;
    //   744: invokevirtual printStackTrace : (Ljava/io/PrintStream;)V
    //   747: new java/lang/RuntimeException
    //   750: dup
    //   751: ldc_w 'Unexpected exception in translator thread.'
    //   754: aload_2
    //   755: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   758: athrow
    //   759: astore_2
    //   760: aload_0
    //   761: getfield classTranslatorPool : Ljava/util/concurrent/ExecutorService;
    //   764: invokeinterface shutdownNow : ()Ljava/util/List;
    //   769: pop
    //   770: aload_0
    //   771: getfield classDefItemConsumer : Ljava/util/concurrent/ExecutorService;
    //   774: invokeinterface shutdownNow : ()Ljava/util/List;
    //   779: pop
    //   780: new java/lang/RuntimeException
    //   783: dup
    //   784: ldc_w 'Translation has been interrupted'
    //   787: aload_2
    //   788: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   791: athrow
    //   792: astore_2
    //   793: goto -> 333
    //   796: astore #5
    //   798: goto -> 197
    // Exception table:
    //   from	to	target	type
    //   102	132	792	com/android/dx/command/dexer/Main$StopProcessing
    //   135	144	792	com/android/dx/command/dexer/Main$StopProcessing
    //   146	160	792	com/android/dx/command/dexer/Main$StopProcessing
    //   166	197	792	com/android/dx/command/dexer/Main$StopProcessing
    //   197	211	234	finally
    //   214	217	234	finally
    //   217	221	792	com/android/dx/command/dexer/Main$StopProcessing
    //   224	231	796	java/lang/InterruptedException
    //   224	231	234	finally
    //   235	238	234	finally
    //   238	240	792	com/android/dx/command/dexer/Main$StopProcessing
    //   240	264	792	com/android/dx/command/dexer/Main$StopProcessing
    //   266	281	792	com/android/dx/command/dexer/Main$StopProcessing
    //   287	300	792	com/android/dx/command/dexer/Main$StopProcessing
    //   300	311	792	com/android/dx/command/dexer/Main$StopProcessing
    //   313	327	792	com/android/dx/command/dexer/Main$StopProcessing
    //   333	393	759	java/lang/InterruptedException
    //   333	393	715	java/lang/Exception
    //   393	412	759	java/lang/InterruptedException
    //   393	412	715	java/lang/Exception
    //   412	419	422	java/util/concurrent/ExecutionException
    //   412	419	759	java/lang/InterruptedException
    //   412	419	715	java/lang/Exception
    //   424	474	759	java/lang/InterruptedException
    //   424	474	715	java/lang/Exception
    //   477	521	759	java/lang/InterruptedException
    //   477	521	715	java/lang/Exception
    //   524	537	759	java/lang/InterruptedException
    //   524	537	715	java/lang/Exception
  }
  
  private boolean processClass(String paramString, byte[] paramArrayOfbyte) {
    if (!this.args.coreLibrary)
      checkClassName(paramString); 
    try {
      DirectClassFileConsumer directClassFileConsumer = new DirectClassFileConsumer();
      this(this, paramString, paramArrayOfbyte, null);
      ClassParserTask classParserTask = new ClassParserTask();
      this(this, paramString, paramArrayOfbyte);
      directClassFileConsumer.call(classParserTask.call());
      return true;
    } catch (ParseException parseException) {
      throw parseException;
    } catch (Exception exception) {
      throw new RuntimeException("Exception parsing classes", exception);
    } 
  }
  
  private boolean processFileBytes(String paramString, long paramLong, byte[] paramArrayOfbyte) {
    StringBuilder stringBuilder;
    boolean bool;
    boolean bool1 = paramString.endsWith(".class");
    boolean bool2 = paramString.equals("classes.dex");
    if (this.outputResources != null) {
      bool = true;
    } else {
      bool = false;
    } 
    if (!bool1 && !bool2 && !bool) {
      if (this.args.verbose) {
        PrintStream printStream = this.context.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("ignored resource ");
        stringBuilder.append(paramString);
        printStream.println(stringBuilder.toString());
      } 
      return false;
    } 
    if (this.args.verbose) {
      PrintStream printStream = this.context.out;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("processing ");
      stringBuilder1.append(paramString);
      stringBuilder1.append("...");
      printStream.println(stringBuilder1.toString());
    } 
    paramString = fixPath(paramString);
    if (bool1) {
      if (bool && this.args.keepClassesInJar)
        synchronized (this.outputResources) {
          this.outputResources.put(paramString, stringBuilder);
        }  
      if (paramLong < this.minimumFileAge)
        return true; 
      processClass(paramString, (byte[])stringBuilder);
      return false;
    } 
    if (bool2)
      synchronized (this.libraryDexBuffers) {
        this.libraryDexBuffers.add(stringBuilder);
        return true;
      }  
    synchronized (this.outputResources) {
      this.outputResources.put(paramString, stringBuilder);
      return true;
    } 
  }
  
  private void processOne(String paramString, ClassPathOpener.FileNameFilter paramFileNameFilter) {
    if ((new ClassPathOpener(paramString, true, paramFileNameFilter, new FileBytesConsumer())).process())
      updateStatus(true); 
  }
  
  private static void readPathsFromFile(String paramString, Collection<String> paramCollection) throws IOException {
    Collection collection = null;
    try {
      FileReader fileReader = new FileReader();
      this(paramString);
      BufferedReader bufferedReader = new BufferedReader();
      this(fileReader);
    } finally {
      paramString = null;
    } 
    if (paramCollection != null)
      paramCollection.close(); 
    throw paramString;
  }
  
  private void rotateDexFile() {
    DexFile dexFile = this.outputDex;
    if (dexFile != null) {
      ExecutorService executorService = this.dexOutPool;
      if (executorService != null) {
        this.dexOutputFutures.add((Future)executorService.submit(new DexWriter(dexFile)));
      } else {
        this.dexOutputArrays.add(writeDex(dexFile));
      } 
    } 
    createDexFile();
  }
  
  public static int run(Arguments paramArguments) throws IOException {
    return (new Main(new DxContext())).runDx(paramArguments);
  }
  
  private int runMonoDex() throws IOException {
    File file;
    if (this.args.incremental) {
      if (this.args.outName == null) {
        this.context.err.println("error: no incremental output name specified");
        return -1;
      } 
      arrayOfByte1 = (byte[])new File(this.args.outName);
      file = (File)arrayOfByte1;
      if (arrayOfByte1.exists()) {
        this.minimumFileAge = arrayOfByte1.lastModified();
        file = (File)arrayOfByte1;
      } 
    } else {
      file = null;
    } 
    if (!processAllFiles())
      return 1; 
    if (this.args.incremental && !this.anyFilesProcessed)
      return 0; 
    if (!this.outputDex.isEmpty() || this.args.humanOutName != null) {
      byte[] arrayOfByte = writeDex(this.outputDex);
      arrayOfByte1 = arrayOfByte;
      if (arrayOfByte == null)
        return 2; 
    } else {
      arrayOfByte1 = null;
    } 
    byte[] arrayOfByte2 = arrayOfByte1;
    if (this.args.incremental)
      arrayOfByte2 = mergeIncremental(arrayOfByte1, file); 
    byte[] arrayOfByte1 = mergeLibraryDexBuffers(arrayOfByte2);
    if (this.args.jarOutput) {
      this.outputDex = null;
      if (arrayOfByte1 != null)
        this.outputResources.put("classes.dex", arrayOfByte1); 
      if (!createJar(this.args.outName))
        return 3; 
    } else if (arrayOfByte1 != null && this.args.outName != null) {
      OutputStream outputStream = openOutput(this.args.outName);
      outputStream.write(arrayOfByte1);
      closeOutput(outputStream);
    } 
    return 0;
  }
  
  private int runMultiDex() throws IOException {
    if (this.args.mainDexListFile != null) {
      this.classesInMainDex = new HashSet<String>();
      readPathsFromFile(this.args.mainDexListFile, this.classesInMainDex);
    } 
    this.dexOutPool = Executors.newFixedThreadPool(this.args.numThreads);
    if (!processAllFiles())
      return 1; 
    if (this.libraryDexBuffers.isEmpty()) {
      DexFile dexFile = this.outputDex;
      if (dexFile != null) {
        this.dexOutputFutures.add((Future)this.dexOutPool.submit(new DexWriter(dexFile)));
        this.outputDex = null;
      } 
      try {
        this.dexOutPool.shutdown();
        if (this.dexOutPool.awaitTermination(600L, TimeUnit.SECONDS)) {
          for (Future<byte[]> future : this.dexOutputFutures)
            this.dexOutputArrays.add(future.get()); 
          if (this.args.jarOutput) {
            for (byte b = 0; b < this.dexOutputArrays.size(); b++)
              this.outputResources.put(getDexFileName(b), this.dexOutputArrays.get(b)); 
            if (!createJar(this.args.outName))
              return 3; 
          } else if (this.args.outName != null) {
            File file = new File(this.args.outName);
            byte b = 0;
            while (b < this.dexOutputArrays.size()) {
              FileOutputStream fileOutputStream = new FileOutputStream(new File(file, getDexFileName(b)));
              try {
                fileOutputStream.write(this.dexOutputArrays.get(b));
                closeOutput(fileOutputStream);
              } finally {
                closeOutput(fileOutputStream);
              } 
            } 
          } 
          return 0;
        } 
        RuntimeException runtimeException = new RuntimeException();
        this("Timed out waiting for dex writer threads.");
        throw runtimeException;
      } catch (InterruptedException interruptedException) {
        this.dexOutPool.shutdownNow();
        throw new RuntimeException("A dex writer thread has been interrupted.");
      } catch (Exception exception) {
        this.dexOutPool.shutdownNow();
        throw new RuntimeException("Unexpected exception in dex writer thread");
      } 
    } 
    throw new DexException("Library dex files are not supported in multi-dex mode");
  }
  
  private ClassDefItem translateClass(byte[] paramArrayOfbyte, DirectClassFile paramDirectClassFile) {
    try {
      return CfTranslator.translate(this.context, paramDirectClassFile, paramArrayOfbyte, this.args.cfOptions, this.args.dexOptions, this.outputDex);
    } catch (ParseException parseException) {
      this.context.err.println("\ntrouble processing:");
      if (this.args.debug) {
        parseException.printStackTrace(this.context.err);
      } else {
        parseException.printContext(this.context.err);
      } 
      this.errors.incrementAndGet();
      return null;
    } 
  }
  
  private void updateStatus(boolean paramBoolean) {
    this.anyFilesProcessed = paramBoolean | this.anyFilesProcessed;
  }
  
  private byte[] writeDex(DexFile paramDexFile) {
    try {
      byte[] arrayOfByte;
      if (this.args.methodToDump != null) {
        paramDexFile.toDex(null, false);
        dumpMethod(paramDexFile, this.args.methodToDump, this.humanOutWriter);
        arrayOfByte = null;
      } else {
        arrayOfByte = paramDexFile.toDex(this.humanOutWriter, this.args.verboseDump);
      } 
      if (this.args.statistics)
        this.context.out.println(paramDexFile.getStatistics().toHuman()); 
    } finally {
      if (this.humanOutWriter != null)
        this.humanOutWriter.flush(); 
    } 
    if (this.args.debug) {
      this.context.err.println("\ntrouble writing output:");
      exception.printStackTrace(this.context.err);
    } else {
      PrintStream printStream = this.context.err;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("\ntrouble writing output: ");
      stringBuilder.append(exception.getMessage());
      printStream.println(stringBuilder.toString());
    } 
    return null;
  }
  
  public int runDx(Arguments paramArguments) throws IOException {
    this.errors.set(0);
    this.libraryDexBuffers.clear();
    this.args = paramArguments;
    paramArguments.makeOptionsObjects();
    if (this.args.humanOutName != null) {
      OutputStream outputStream = openOutput(this.args.humanOutName);
      this.humanOutWriter = new OutputStreamWriter(outputStream);
    } else {
      paramArguments = null;
    } 
    try {
      if (this.args.multiDex)
        return runMultiDex(); 
      return runMonoDex();
    } finally {
      closeOutput((OutputStream)paramArguments);
    } 
  }
  
  public static class Arguments {
    private static final String INCREMENTAL_OPTION = "--incremental";
    
    private static final String INPUT_LIST_OPTION = "--input-list";
    
    private static final String MAIN_DEX_LIST_OPTION = "--main-dex-list";
    
    private static final String MINIMAL_MAIN_DEX_OPTION = "--minimal-main-dex";
    
    private static final String MULTI_DEX_OPTION = "--multi-dex";
    
    private static final String NUM_THREADS_OPTION = "--num-threads";
    
    public boolean allowAllInterfaceMethodInvokes = false;
    
    public CfOptions cfOptions;
    
    public final DxContext context;
    
    public boolean coreLibrary = false;
    
    public boolean debug = false;
    
    public DexOptions dexOptions;
    
    public String dontOptimizeListFile = null;
    
    public int dumpWidth = 0;
    
    public boolean emptyOk = false;
    
    public String[] fileNames;
    
    public boolean forceJumbo = false;
    
    public String humanOutName = null;
    
    public boolean incremental = false;
    
    private List<String> inputList = null;
    
    public boolean jarOutput = false;
    
    public boolean keepClassesInJar = false;
    
    public boolean localInfo = true;
    
    public String mainDexListFile = null;
    
    public int maxNumberOfIdxPerDex = 65536;
    
    public String methodToDump = null;
    
    public int minSdkVersion = 13;
    
    public boolean minimalMainDex = false;
    
    public boolean multiDex = false;
    
    public int numThreads = 1;
    
    public boolean optimize = true;
    
    public String optimizeListFile = null;
    
    public String outName = null;
    
    private boolean outputIsDirectDex = false;
    
    private boolean outputIsDirectory = false;
    
    public int positionInfo = 2;
    
    public boolean statistics;
    
    public boolean strictNameCheck = true;
    
    public boolean verbose = false;
    
    public boolean verboseDump = false;
    
    public boolean warnings = true;
    
    public Arguments() {
      this(new DxContext());
    }
    
    public Arguments(DxContext param1DxContext) {
      this.context = param1DxContext;
    }
    
    private void parse(String[] param1ArrayOfString) {
      ArgumentsParser argumentsParser = new ArgumentsParser(param1ArrayOfString);
      parseFlags(argumentsParser);
      this.fileNames = argumentsParser.getRemaining();
      List<String> list = this.inputList;
      if (list != null && !list.isEmpty()) {
        this.inputList.addAll(Arrays.asList(this.fileNames));
        list = this.inputList;
        this.fileNames = list.<String>toArray(new String[list.size()]);
      } 
      if (this.fileNames.length == 0) {
        if (!this.emptyOk) {
          this.context.err.println("no input files specified");
          throw new UsageException();
        } 
      } else if (this.emptyOk) {
        this.context.out.println("ignoring input files");
      } 
      if (this.humanOutName == null && this.methodToDump != null)
        this.humanOutName = "-"; 
      if (this.mainDexListFile == null || this.multiDex) {
        if (!this.minimalMainDex || (this.mainDexListFile != null && this.multiDex)) {
          if (!this.multiDex || !this.incremental) {
            if (!this.multiDex || !this.outputIsDirectDex) {
              if (this.outputIsDirectory && !this.multiDex)
                this.outName = (new File(this.outName, "classes.dex")).getPath(); 
              makeOptionsObjects();
              return;
            } 
            PrintStream printStream = this.context.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported output \"");
            stringBuilder.append(this.outName);
            stringBuilder.append("\". ");
            stringBuilder.append("--multi-dex");
            stringBuilder.append(" supports only archive or directory output");
            printStream.println(stringBuilder.toString());
            throw new UsageException();
          } 
          this.context.err.println("--incremental is not supported with --multi-dex");
          throw new UsageException();
        } 
        this.context.err.println("--minimal-main-dex is only supported in combination with --multi-dex and --main-dex-list");
        throw new UsageException();
      } 
      this.context.err.println("--main-dex-list is only supported in combination with --multi-dex");
      throw new UsageException();
    }
    
    private void parseFlags(ArgumentsParser param1ArgumentsParser) {
      while (param1ArgumentsParser.getNext()) {
        PrintStream printStream2;
        StringBuilder stringBuilder2;
        PrintStream printStream1;
        StringBuilder stringBuilder1;
        if (param1ArgumentsParser.isArg("--debug")) {
          this.debug = true;
          continue;
        } 
        if (param1ArgumentsParser.isArg("--no-warning")) {
          this.warnings = false;
          continue;
        } 
        if (param1ArgumentsParser.isArg("--verbose")) {
          this.verbose = true;
          continue;
        } 
        if (param1ArgumentsParser.isArg("--verbose-dump")) {
          this.verboseDump = true;
          continue;
        } 
        if (param1ArgumentsParser.isArg("--no-files")) {
          this.emptyOk = true;
          continue;
        } 
        if (param1ArgumentsParser.isArg("--no-optimize")) {
          this.optimize = false;
          continue;
        } 
        if (param1ArgumentsParser.isArg("--no-strict")) {
          this.strictNameCheck = false;
          continue;
        } 
        if (param1ArgumentsParser.isArg("--core-library")) {
          this.coreLibrary = true;
          continue;
        } 
        if (param1ArgumentsParser.isArg("--statistics")) {
          this.statistics = true;
          continue;
        } 
        if (param1ArgumentsParser.isArg("--optimize-list=")) {
          if (this.dontOptimizeListFile == null) {
            this.optimize = true;
            this.optimizeListFile = param1ArgumentsParser.getLastValue();
            continue;
          } 
          this.context.err.println("--optimize-list and --no-optimize-list are incompatible.");
          throw new UsageException();
        } 
        if (param1ArgumentsParser.isArg("--no-optimize-list=")) {
          if (this.dontOptimizeListFile == null) {
            this.optimize = true;
            this.dontOptimizeListFile = param1ArgumentsParser.getLastValue();
            continue;
          } 
          this.context.err.println("--optimize-list and --no-optimize-list are incompatible.");
          throw new UsageException();
        } 
        if (param1ArgumentsParser.isArg("--keep-classes")) {
          this.keepClassesInJar = true;
          continue;
        } 
        if (param1ArgumentsParser.isArg("--output=")) {
          this.outName = param1ArgumentsParser.getLastValue();
          if ((new File(this.outName)).isDirectory()) {
            this.jarOutput = false;
            this.outputIsDirectory = true;
            continue;
          } 
          if (FileUtils.hasArchiveSuffix(this.outName)) {
            this.jarOutput = true;
            continue;
          } 
          if (this.outName.endsWith(".dex") || this.outName.equals("-")) {
            this.jarOutput = false;
            this.outputIsDirectDex = true;
            continue;
          } 
          printStream2 = this.context.err;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("unknown output extension: ");
          stringBuilder.append(this.outName);
          printStream2.println(stringBuilder.toString());
          throw new UsageException();
        } 
        if (printStream2.isArg("--dump-to=")) {
          this.humanOutName = printStream2.getLastValue();
          continue;
        } 
        if (printStream2.isArg("--dump-width=")) {
          this.dumpWidth = Integer.parseInt(printStream2.getLastValue());
          continue;
        } 
        if (printStream2.isArg("--dump-method=")) {
          this.methodToDump = printStream2.getLastValue();
          this.jarOutput = false;
          continue;
        } 
        if (printStream2.isArg("--positions=")) {
          String str = printStream2.getLastValue().intern();
          if (str == "none") {
            this.positionInfo = 1;
            continue;
          } 
          if (str == "important") {
            this.positionInfo = 3;
            continue;
          } 
          if (str == "lines") {
            this.positionInfo = 2;
            continue;
          } 
          PrintStream printStream = this.context.err;
          stringBuilder2 = new StringBuilder();
          stringBuilder2.append("unknown positions option: ");
          stringBuilder2.append(str);
          printStream.println(stringBuilder2.toString());
          throw new UsageException();
        } 
        if (stringBuilder2.isArg("--no-locals")) {
          this.localInfo = false;
          continue;
        } 
        if (stringBuilder2.isArg("--num-threads=")) {
          this.numThreads = Integer.parseInt(stringBuilder2.getLastValue());
          continue;
        } 
        if (stringBuilder2.isArg("--incremental")) {
          this.incremental = true;
          continue;
        } 
        if (stringBuilder2.isArg("--force-jumbo")) {
          this.forceJumbo = true;
          continue;
        } 
        if (stringBuilder2.isArg("--multi-dex")) {
          this.multiDex = true;
          continue;
        } 
        if (stringBuilder2.isArg("--main-dex-list=")) {
          this.mainDexListFile = stringBuilder2.getLastValue();
          continue;
        } 
        if (stringBuilder2.isArg("--minimal-main-dex")) {
          this.minimalMainDex = true;
          continue;
        } 
        if (stringBuilder2.isArg("--set-max-idx-number=")) {
          this.maxNumberOfIdxPerDex = Integer.parseInt(stringBuilder2.getLastValue());
          continue;
        } 
        if (stringBuilder2.isArg("--input-list=")) {
          File file = new File(stringBuilder2.getLastValue());
          try {
            ArrayList<String> arrayList = new ArrayList();
            this();
            this.inputList = arrayList;
            Main.readPathsFromFile(file.getAbsolutePath(), this.inputList);
          } catch (IOException iOException) {
            printStream1 = this.context.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to read input list file: ");
            stringBuilder.append(file.getName());
            printStream1.println(stringBuilder.toString());
            throw new UsageException();
          } 
          continue;
        } 
        if (printStream1.isArg("--min-sdk-version=")) {
          byte b;
          String str = printStream1.getLastValue();
          try {
            b = Integer.parseInt(str);
          } catch (NumberFormatException numberFormatException) {
            b = -1;
          } 
          if (b >= 1) {
            this.minSdkVersion = b;
            continue;
          } 
          PrintStream printStream = System.err;
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append("improper min-sdk-version option: ");
          stringBuilder1.append(str);
          printStream.println(stringBuilder1.toString());
          throw new UsageException();
        } 
        if (stringBuilder1.isArg("--allow-all-interface-method-invokes")) {
          this.allowAllInterfaceMethodInvokes = true;
          continue;
        } 
        PrintStream printStream3 = this.context.err;
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("unknown option: ");
        stringBuilder3.append(stringBuilder1.getCurrent());
        printStream3.println(stringBuilder3.toString());
        throw new UsageException();
      } 
    }
    
    public void makeOptionsObjects() {
      CfOptions cfOptions = new CfOptions();
      this.cfOptions = cfOptions;
      cfOptions.positionInfo = this.positionInfo;
      this.cfOptions.localInfo = this.localInfo;
      this.cfOptions.strictNameCheck = this.strictNameCheck;
      this.cfOptions.optimize = this.optimize;
      this.cfOptions.optimizeListFile = this.optimizeListFile;
      this.cfOptions.dontOptimizeListFile = this.dontOptimizeListFile;
      this.cfOptions.statistics = this.statistics;
      if (this.warnings) {
        this.cfOptions.warn = this.context.err;
      } else {
        this.cfOptions.warn = this.context.noop;
      } 
      DexOptions dexOptions = new DexOptions(this.context.err);
      this.dexOptions = dexOptions;
      dexOptions.minSdkVersion = this.minSdkVersion;
      this.dexOptions.forceJumbo = this.forceJumbo;
      this.dexOptions.allowAllInterfaceMethodInvokes = this.allowAllInterfaceMethodInvokes;
    }
    
    public void parseFlags(String[] param1ArrayOfString) {
      parseFlags(new ArgumentsParser(param1ArrayOfString));
    }
    
    private static class ArgumentsParser {
      private final String[] arguments;
      
      private String current;
      
      private int index;
      
      private String lastValue;
      
      public ArgumentsParser(String[] param2ArrayOfString) {
        this.arguments = param2ArrayOfString;
        this.index = 0;
      }
      
      private boolean getNextValue() {
        int i = this.index;
        String[] arrayOfString = this.arguments;
        if (i >= arrayOfString.length)
          return false; 
        this.current = arrayOfString[i];
        this.index = i + 1;
        return true;
      }
      
      public String getCurrent() {
        return this.current;
      }
      
      public String getLastValue() {
        return this.lastValue;
      }
      
      public boolean getNext() {
        int i = this.index;
        String[] arrayOfString = this.arguments;
        if (i >= arrayOfString.length)
          return false; 
        String str = arrayOfString[i];
        this.current = str;
        if (str.equals("--") || !this.current.startsWith("--"))
          return false; 
        this.index++;
        return true;
      }
      
      public String[] getRemaining() {
        String[] arrayOfString1 = this.arguments;
        int i = arrayOfString1.length;
        int j = this.index;
        i -= j;
        String[] arrayOfString2 = new String[i];
        if (i > 0)
          System.arraycopy(arrayOfString1, j, arrayOfString2, 0, i); 
        return arrayOfString2;
      }
      
      public boolean isArg(String param2String) {
        PrintStream printStream;
        int i = param2String.length();
        if (i > 0) {
          int j = i - 1;
          if (param2String.charAt(j) == '=') {
            if (this.current.startsWith(param2String)) {
              this.lastValue = this.current.substring(i);
              return true;
            } 
            String str = param2String.substring(0, j);
            if (this.current.equals(str)) {
              if (getNextValue()) {
                this.lastValue = this.current;
                return true;
              } 
              printStream = System.err;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Missing value after parameter ");
              stringBuilder.append(str);
              printStream.println(stringBuilder.toString());
              throw new UsageException();
            } 
            return false;
          } 
        } 
        return this.current.equals(printStream);
      }
    }
  }
  
  private static class ArgumentsParser {
    private final String[] arguments;
    
    private String current;
    
    private int index;
    
    private String lastValue;
    
    public ArgumentsParser(String[] param1ArrayOfString) {
      this.arguments = param1ArrayOfString;
      this.index = 0;
    }
    
    private boolean getNextValue() {
      int i = this.index;
      String[] arrayOfString = this.arguments;
      if (i >= arrayOfString.length)
        return false; 
      this.current = arrayOfString[i];
      this.index = i + 1;
      return true;
    }
    
    public String getCurrent() {
      return this.current;
    }
    
    public String getLastValue() {
      return this.lastValue;
    }
    
    public boolean getNext() {
      int i = this.index;
      String[] arrayOfString = this.arguments;
      if (i >= arrayOfString.length)
        return false; 
      String str = arrayOfString[i];
      this.current = str;
      if (str.equals("--") || !this.current.startsWith("--"))
        return false; 
      this.index++;
      return true;
    }
    
    public String[] getRemaining() {
      String[] arrayOfString1 = this.arguments;
      int i = arrayOfString1.length;
      int j = this.index;
      i -= j;
      String[] arrayOfString2 = new String[i];
      if (i > 0)
        System.arraycopy(arrayOfString1, j, arrayOfString2, 0, i); 
      return arrayOfString2;
    }
    
    public boolean isArg(String param1String) {
      PrintStream printStream;
      int i = param1String.length();
      if (i > 0) {
        int j = i - 1;
        if (param1String.charAt(j) == '=') {
          if (this.current.startsWith(param1String)) {
            this.lastValue = this.current.substring(i);
            return true;
          } 
          String str = param1String.substring(0, j);
          if (this.current.equals(str)) {
            if (getNextValue()) {
              this.lastValue = this.current;
              return true;
            } 
            printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Missing value after parameter ");
            stringBuilder.append(str);
            printStream.println(stringBuilder.toString());
            throw new UsageException();
          } 
          return false;
        } 
      } 
      return this.current.equals(printStream);
    }
  }
  
  private class BestEffortMainDexListFilter implements ClassPathOpener.FileNameFilter {
    Map<String, List<String>> map = new HashMap<String, List<String>>();
    
    public BestEffortMainDexListFilter() {
      Iterator<String> iterator = Main.this.classesInMainDex.iterator();
      while (iterator.hasNext()) {
        String str1 = Main.fixPath(iterator.next());
        String str2 = getSimpleName(str1);
        List<String> list2 = this.map.get(str2);
        List<String> list1 = list2;
        if (list2 == null) {
          list1 = new ArrayList(1);
          this.map.put(str2, list1);
        } 
        list1.add(str1);
      } 
    }
    
    private String getSimpleName(String param1String) {
      int i = param1String.lastIndexOf('/');
      String str = param1String;
      if (i >= 0)
        str = param1String.substring(i + 1); 
      return str;
    }
    
    public boolean accept(String param1String) {
      if (param1String.endsWith(".class")) {
        param1String = Main.fixPath(param1String);
        String str = getSimpleName(param1String);
        List list = this.map.get(str);
        if (list != null) {
          Iterator<String> iterator = list.iterator();
          while (iterator.hasNext()) {
            if (param1String.endsWith(iterator.next()))
              return true; 
          } 
        } 
        return false;
      } 
      return true;
    }
  }
  
  private class ClassDefItemConsumer implements Callable<Boolean> {
    Future<ClassDefItem> futureClazz;
    
    int maxFieldIdsInClass;
    
    int maxMethodIdsInClass;
    
    String name;
    
    private ClassDefItemConsumer(String param1String, Future<ClassDefItem> param1Future, int param1Int1, int param1Int2) {
      this.name = param1String;
      this.futureClazz = param1Future;
      this.maxMethodIdsInClass = param1Int1;
      this.maxFieldIdsInClass = param1Int2;
    }
    
    public Boolean call() throws Exception {
      try {
        ClassDefItem classDefItem = this.futureClazz.get();
        if (classDefItem != null) {
          Main.this.addClassToDex(classDefItem);
          Main.this.updateStatus(true);
        } 
        if (Main.this.args.multiDex)
          synchronized (Main.this.dexRotationLock) {
            Main.access$1902(Main.this, Main.this.maxMethodIdsInProcess - this.maxMethodIdsInClass);
            Main.access$2002(Main.this, Main.this.maxFieldIdsInProcess - this.maxFieldIdsInClass);
            Main.this.dexRotationLock.notifyAll();
          }  
        return Boolean.valueOf(true);
      } catch (ExecutionException null) {
        Exception exception;
        Throwable throwable = exception.getCause();
        if (throwable instanceof Exception)
          exception = (Exception)throwable; 
        throw exception;
      } finally {
        Exception exception;
      } 
      if (Main.this.args.multiDex)
        synchronized (Main.this.dexRotationLock) {
          Main.access$1902(Main.this, Main.this.maxMethodIdsInProcess - this.maxMethodIdsInClass);
          Main.access$2002(Main.this, Main.this.maxFieldIdsInProcess - this.maxFieldIdsInClass);
          Main.this.dexRotationLock.notifyAll();
        }  
      throw SYNTHETIC_LOCAL_VARIABLE_2;
    }
  }
  
  private class ClassParserTask implements Callable<DirectClassFile> {
    byte[] bytes;
    
    String name;
    
    private ClassParserTask(String param1String, byte[] param1ArrayOfbyte) {
      this.name = param1String;
      this.bytes = param1ArrayOfbyte;
    }
    
    public DirectClassFile call() throws Exception {
      return Main.this.parseClass(this.name, this.bytes);
    }
  }
  
  private class ClassTranslatorTask implements Callable<ClassDefItem> {
    byte[] bytes;
    
    DirectClassFile classFile;
    
    String name;
    
    private ClassTranslatorTask(String param1String, byte[] param1ArrayOfbyte, DirectClassFile param1DirectClassFile) {
      this.name = param1String;
      this.bytes = param1ArrayOfbyte;
      this.classFile = param1DirectClassFile;
    }
    
    public ClassDefItem call() {
      return Main.this.translateClass(this.bytes, this.classFile);
    }
  }
  
  private class DexWriter implements Callable<byte[]> {
    private final DexFile dexFile;
    
    private DexWriter(DexFile param1DexFile) {
      this.dexFile = param1DexFile;
    }
    
    public byte[] call() throws IOException {
      return Main.this.writeDex(this.dexFile);
    }
  }
  
  private class DirectClassFileConsumer implements Callable<Boolean> {
    byte[] bytes;
    
    Future<DirectClassFile> dcff;
    
    String name;
    
    private DirectClassFileConsumer(String param1String, byte[] param1ArrayOfbyte, Future<DirectClassFile> param1Future) {
      this.name = param1String;
      this.bytes = param1ArrayOfbyte;
      this.dcff = param1Future;
    }
    
    private Boolean call(DirectClassFile param1DirectClassFile) {
      // Byte code:
      //   0: aload_0
      //   1: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   4: invokestatic access$1400 : (Lcom/android/dx/command/dexer/Main;)Lcom/android/dx/command/dexer/Main$Arguments;
      //   7: getfield multiDex : Z
      //   10: ifeq -> 355
      //   13: aload_1
      //   14: invokevirtual getConstantPool : ()Lcom/android/dx/rop/cst/ConstantPool;
      //   17: invokeinterface size : ()I
      //   22: istore_2
      //   23: aload_1
      //   24: invokevirtual getMethods : ()Lcom/android/dx/cf/iface/MethodList;
      //   27: invokeinterface size : ()I
      //   32: iload_2
      //   33: iadd
      //   34: iconst_2
      //   35: iadd
      //   36: istore_3
      //   37: iload_2
      //   38: aload_1
      //   39: invokevirtual getFields : ()Lcom/android/dx/cf/iface/FieldList;
      //   42: invokeinterface size : ()I
      //   47: iadd
      //   48: bipush #9
      //   50: iadd
      //   51: istore #4
      //   53: aload_0
      //   54: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   57: invokestatic access$1700 : (Lcom/android/dx/command/dexer/Main;)Ljava/lang/Object;
      //   60: astore #5
      //   62: aload #5
      //   64: monitorenter
      //   65: aload_0
      //   66: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   69: invokestatic access$1800 : (Lcom/android/dx/command/dexer/Main;)Lcom/android/dx/dex/file/DexFile;
      //   72: astore #6
      //   74: aload #6
      //   76: monitorenter
      //   77: aload_0
      //   78: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   81: invokestatic access$1800 : (Lcom/android/dx/command/dexer/Main;)Lcom/android/dx/dex/file/DexFile;
      //   84: invokevirtual getMethodIds : ()Lcom/android/dx/dex/file/MethodIdsSection;
      //   87: invokevirtual items : ()Ljava/util/Collection;
      //   90: invokeinterface size : ()I
      //   95: istore #7
      //   97: aload_0
      //   98: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   101: invokestatic access$1800 : (Lcom/android/dx/command/dexer/Main;)Lcom/android/dx/dex/file/DexFile;
      //   104: invokevirtual getFieldIds : ()Lcom/android/dx/dex/file/FieldIdsSection;
      //   107: invokevirtual items : ()Ljava/util/Collection;
      //   110: invokeinterface size : ()I
      //   115: istore_2
      //   116: aload #6
      //   118: monitorexit
      //   119: iload #7
      //   121: iload_3
      //   122: iadd
      //   123: aload_0
      //   124: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   127: invokestatic access$1900 : (Lcom/android/dx/command/dexer/Main;)I
      //   130: iadd
      //   131: aload_0
      //   132: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   135: invokestatic access$1400 : (Lcom/android/dx/command/dexer/Main;)Lcom/android/dx/command/dexer/Main$Arguments;
      //   138: getfield maxNumberOfIdxPerDex : I
      //   141: if_icmpgt -> 169
      //   144: iload_2
      //   145: iload #4
      //   147: iadd
      //   148: aload_0
      //   149: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   152: invokestatic access$2000 : (Lcom/android/dx/command/dexer/Main;)I
      //   155: iadd
      //   156: aload_0
      //   157: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   160: invokestatic access$1400 : (Lcom/android/dx/command/dexer/Main;)Lcom/android/dx/command/dexer/Main$Arguments;
      //   163: getfield maxNumberOfIdxPerDex : I
      //   166: if_icmple -> 223
      //   169: aload_0
      //   170: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   173: invokestatic access$1900 : (Lcom/android/dx/command/dexer/Main;)I
      //   176: ifgt -> 270
      //   179: aload_0
      //   180: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   183: invokestatic access$2000 : (Lcom/android/dx/command/dexer/Main;)I
      //   186: ifle -> 192
      //   189: goto -> 270
      //   192: aload_0
      //   193: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   196: invokestatic access$1800 : (Lcom/android/dx/command/dexer/Main;)Lcom/android/dx/dex/file/DexFile;
      //   199: invokevirtual getClassDefs : ()Lcom/android/dx/dex/file/ClassDefsSection;
      //   202: invokevirtual items : ()Ljava/util/Collection;
      //   205: invokeinterface size : ()I
      //   210: ifle -> 223
      //   213: aload_0
      //   214: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   217: invokestatic access$2100 : (Lcom/android/dx/command/dexer/Main;)V
      //   220: goto -> 280
      //   223: aload_0
      //   224: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   227: aload_0
      //   228: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   231: invokestatic access$1900 : (Lcom/android/dx/command/dexer/Main;)I
      //   234: iload_3
      //   235: iadd
      //   236: invokestatic access$1902 : (Lcom/android/dx/command/dexer/Main;I)I
      //   239: pop
      //   240: aload_0
      //   241: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   244: aload_0
      //   245: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   248: invokestatic access$2000 : (Lcom/android/dx/command/dexer/Main;)I
      //   251: iload #4
      //   253: iadd
      //   254: invokestatic access$2002 : (Lcom/android/dx/command/dexer/Main;I)I
      //   257: pop
      //   258: aload #5
      //   260: monitorexit
      //   261: iload #4
      //   263: istore_2
      //   264: iload_3
      //   265: istore #7
      //   267: goto -> 360
      //   270: aload_0
      //   271: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   274: invokestatic access$1700 : (Lcom/android/dx/command/dexer/Main;)Ljava/lang/Object;
      //   277: invokevirtual wait : ()V
      //   280: aload_0
      //   281: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   284: invokestatic access$1800 : (Lcom/android/dx/command/dexer/Main;)Lcom/android/dx/dex/file/DexFile;
      //   287: astore #6
      //   289: aload #6
      //   291: monitorenter
      //   292: aload_0
      //   293: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   296: invokestatic access$1800 : (Lcom/android/dx/command/dexer/Main;)Lcom/android/dx/dex/file/DexFile;
      //   299: invokevirtual getMethodIds : ()Lcom/android/dx/dex/file/MethodIdsSection;
      //   302: invokevirtual items : ()Ljava/util/Collection;
      //   305: invokeinterface size : ()I
      //   310: istore #7
      //   312: aload_0
      //   313: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   316: invokestatic access$1800 : (Lcom/android/dx/command/dexer/Main;)Lcom/android/dx/dex/file/DexFile;
      //   319: invokevirtual getFieldIds : ()Lcom/android/dx/dex/file/FieldIdsSection;
      //   322: invokevirtual items : ()Ljava/util/Collection;
      //   325: invokeinterface size : ()I
      //   330: istore_2
      //   331: aload #6
      //   333: monitorexit
      //   334: goto -> 119
      //   337: astore_1
      //   338: aload #6
      //   340: monitorexit
      //   341: aload_1
      //   342: athrow
      //   343: astore_1
      //   344: aload #6
      //   346: monitorexit
      //   347: aload_1
      //   348: athrow
      //   349: astore_1
      //   350: aload #5
      //   352: monitorexit
      //   353: aload_1
      //   354: athrow
      //   355: iconst_0
      //   356: istore #7
      //   358: iconst_0
      //   359: istore_2
      //   360: aload_0
      //   361: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   364: invokestatic access$2300 : (Lcom/android/dx/command/dexer/Main;)Ljava/util/concurrent/ExecutorService;
      //   367: new com/android/dx/command/dexer/Main$ClassTranslatorTask
      //   370: dup
      //   371: aload_0
      //   372: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   375: aload_0
      //   376: getfield name : Ljava/lang/String;
      //   379: aload_0
      //   380: getfield bytes : [B
      //   383: aload_1
      //   384: aconst_null
      //   385: invokespecial <init> : (Lcom/android/dx/command/dexer/Main;Ljava/lang/String;[BLcom/android/dx/cf/direct/DirectClassFile;Lcom/android/dx/command/dexer/Main$1;)V
      //   388: invokeinterface submit : (Ljava/util/concurrent/Callable;)Ljava/util/concurrent/Future;
      //   393: astore_1
      //   394: aload_0
      //   395: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   398: invokestatic access$2500 : (Lcom/android/dx/command/dexer/Main;)Ljava/util/concurrent/ExecutorService;
      //   401: new com/android/dx/command/dexer/Main$ClassDefItemConsumer
      //   404: dup
      //   405: aload_0
      //   406: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   409: aload_0
      //   410: getfield name : Ljava/lang/String;
      //   413: aload_1
      //   414: iload #7
      //   416: iload_2
      //   417: aconst_null
      //   418: invokespecial <init> : (Lcom/android/dx/command/dexer/Main;Ljava/lang/String;Ljava/util/concurrent/Future;IILcom/android/dx/command/dexer/Main$1;)V
      //   421: invokeinterface submit : (Ljava/util/concurrent/Callable;)Ljava/util/concurrent/Future;
      //   426: astore_1
      //   427: aload_0
      //   428: getfield this$0 : Lcom/android/dx/command/dexer/Main;
      //   431: invokestatic access$2600 : (Lcom/android/dx/command/dexer/Main;)Ljava/util/List;
      //   434: aload_1
      //   435: invokeinterface add : (Ljava/lang/Object;)Z
      //   440: pop
      //   441: iconst_1
      //   442: invokestatic valueOf : (Z)Ljava/lang/Boolean;
      //   445: areturn
      //   446: astore #6
      //   448: goto -> 280
      // Exception table:
      //   from	to	target	type
      //   65	77	349	finally
      //   77	119	343	finally
      //   119	169	349	finally
      //   169	189	349	finally
      //   192	220	349	finally
      //   223	261	349	finally
      //   270	280	446	java/lang/InterruptedException
      //   270	280	349	finally
      //   280	292	349	finally
      //   292	334	337	finally
      //   338	341	337	finally
      //   341	343	349	finally
      //   344	347	343	finally
      //   347	349	349	finally
      //   350	353	349	finally
    }
    
    public Boolean call() throws Exception {
      return call(this.dcff.get());
    }
  }
  
  private class FileBytesConsumer implements ClassPathOpener.Consumer {
    private FileBytesConsumer() {}
    
    public void onException(Exception param1Exception) {
      ParseException parseException;
      if (!(param1Exception instanceof Main.StopProcessing)) {
        if (param1Exception instanceof SimException) {
          Main.this.context.err.println("\nEXCEPTION FROM SIMULATION:");
          PrintStream printStream = Main.this.context.err;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(param1Exception.getMessage());
          stringBuilder.append("\n");
          printStream.println(stringBuilder.toString());
          Main.this.context.err.println(((SimException)param1Exception).getContext());
        } else if (param1Exception instanceof ParseException) {
          Main.this.context.err.println("\nPARSE ERROR:");
          parseException = (ParseException)param1Exception;
          if (Main.this.args.debug) {
            parseException.printStackTrace(Main.this.context.err);
          } else {
            parseException.printContext(Main.this.context.err);
          } 
        } else {
          Main.this.context.err.println("\nUNEXPECTED TOP-LEVEL EXCEPTION:");
          parseException.printStackTrace(Main.this.context.err);
        } 
        Main.this.errors.incrementAndGet();
        return;
      } 
      throw (Main.StopProcessing)parseException;
    }
    
    public void onProcessArchiveStart(File param1File) {
      if (Main.this.args.verbose) {
        PrintStream printStream = Main.this.context.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("processing archive ");
        stringBuilder.append(param1File);
        stringBuilder.append("...");
        printStream.println(stringBuilder.toString());
      } 
    }
    
    public boolean processFileBytes(String param1String, long param1Long, byte[] param1ArrayOfbyte) {
      return Main.this.processFileBytes(param1String, param1Long, param1ArrayOfbyte);
    }
  }
  
  private class MainDexListFilter implements ClassPathOpener.FileNameFilter {
    private MainDexListFilter() {}
    
    public boolean accept(String param1String) {
      if (param1String.endsWith(".class")) {
        param1String = Main.fixPath(param1String);
        return Main.this.classesInMainDex.contains(param1String);
      } 
      return true;
    }
  }
  
  private static class NotFilter implements ClassPathOpener.FileNameFilter {
    private final ClassPathOpener.FileNameFilter filter;
    
    private NotFilter(ClassPathOpener.FileNameFilter param1FileNameFilter) {
      this.filter = param1FileNameFilter;
    }
    
    public boolean accept(String param1String) {
      return this.filter.accept(param1String) ^ true;
    }
  }
  
  private static class RemoveModuleInfoFilter implements ClassPathOpener.FileNameFilter {
    protected final ClassPathOpener.FileNameFilter delegate;
    
    public RemoveModuleInfoFilter(ClassPathOpener.FileNameFilter param1FileNameFilter) {
      this.delegate = param1FileNameFilter;
    }
    
    public boolean accept(String param1String) {
      boolean bool;
      if (this.delegate.accept(param1String) && !"module-info.class".equals(param1String)) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
  }
  
  private static class StopProcessing extends RuntimeException {
    private StopProcessing() {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\command\dexer\Main.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */