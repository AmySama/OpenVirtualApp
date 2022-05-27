package android.support.multidex;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.zip.ZipFile;

public final class MultiDex {
  private static final String CODE_CACHE_NAME = "code_cache";
  
  private static final String CODE_CACHE_SECONDARY_FOLDER_NAME = "secondary-dexes";
  
  private static final boolean IS_VM_MULTIDEX_CAPABLE;
  
  private static final int MAX_SUPPORTED_SDK_VERSION = 20;
  
  private static final int MIN_SDK_VERSION = 4;
  
  private static final String NO_KEY_PREFIX = "";
  
  private static final String OLD_SECONDARY_FOLDER_NAME = "secondary-dexes";
  
  static final String TAG = "MultiDex";
  
  private static final int VM_WITH_MULTIDEX_VERSION_MAJOR = 2;
  
  private static final int VM_WITH_MULTIDEX_VERSION_MINOR = 1;
  
  private static final Set<File> installedApk = new HashSet<File>();
  
  static {
    IS_VM_MULTIDEX_CAPABLE = isVMMultidexCapable(System.getProperty("java.vm.version"));
  }
  
  private static void clearOldDexDir(Context paramContext) throws Exception {
    File file = new File(paramContext.getFilesDir(), "secondary-dexes");
    if (file.isDirectory()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Clearing old secondary dex dir (");
      stringBuilder.append(file.getPath());
      stringBuilder.append(").");
      Log.i("MultiDex", stringBuilder.toString());
      File[] arrayOfFile = file.listFiles();
      if (arrayOfFile == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to list secondary dex dir content (");
        stringBuilder.append(file.getPath());
        stringBuilder.append(").");
        Log.w("MultiDex", stringBuilder.toString());
        return;
      } 
      int i = arrayOfFile.length;
      for (byte b = 0; b < i; b++) {
        File file1 = arrayOfFile[b];
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Trying to delete old file ");
        stringBuilder1.append(file1.getPath());
        stringBuilder1.append(" of size ");
        stringBuilder1.append(file1.length());
        Log.i("MultiDex", stringBuilder1.toString());
        if (!file1.delete()) {
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Failed to delete old file ");
          stringBuilder1.append(file1.getPath());
          Log.w("MultiDex", stringBuilder1.toString());
        } else {
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Deleted old file ");
          stringBuilder1.append(file1.getPath());
          Log.i("MultiDex", stringBuilder1.toString());
        } 
      } 
      if (!file.delete()) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to delete secondary dex dir ");
        stringBuilder.append(file.getPath());
        Log.w("MultiDex", stringBuilder.toString());
      } else {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Deleted old secondary dex dir ");
        stringBuilder.append(file.getPath());
        Log.i("MultiDex", stringBuilder.toString());
      } 
    } 
  }
  
  private static void doInstallation(Context paramContext, File paramFile1, File paramFile2, String paramString1, String paramString2, boolean paramBoolean) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException {
    synchronized (installedApk) {
      if (installedApk.contains(paramFile1))
        return; 
      installedApk.add(paramFile1);
      if (Build.VERSION.SDK_INT > 20) {
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("MultiDex is not guaranteed to work in SDK version ");
        stringBuilder.append(Build.VERSION.SDK_INT);
        stringBuilder.append(": SDK version higher than ");
        stringBuilder.append(20);
        stringBuilder.append(" should be backed by ");
        stringBuilder.append("runtime with built-in multidex capabilty but it's not the ");
        stringBuilder.append("case here: java.vm.version=\"");
        stringBuilder.append(System.getProperty("java.vm.version"));
        stringBuilder.append("\"");
        Log.w("MultiDex", stringBuilder.toString());
      } 
      try {
        File file;
        ClassLoader classLoader = paramContext.getClassLoader();
        if (classLoader == null) {
          Log.e("MultiDex", "Context class loader is null. Must be running in test mode. Skip patching.");
          return;
        } 
        try {
          clearOldDexDir(paramContext);
        } finally {
          Exception exception = null;
        } 
        MultiDexExtractor multiDexExtractor = new MultiDexExtractor();
        this(paramFile1, file);
        paramFile1 = null;
        try {
          List<? extends File> list = multiDexExtractor.load(paramContext, paramString2, false);
          try {
            installSecondaryDexes(classLoader, file, list);
          } catch (IOException iOException1) {
            if (paramBoolean) {
              Log.w("MultiDex", "Failed to install extracted secondary dex files, retrying with forced extraction", iOException1);
              installSecondaryDexes(classLoader, file, multiDexExtractor.load(paramContext, paramString2, true));
            } else {
              throw iOException1;
            } 
          } 
          try {
            multiDexExtractor.close();
            File file1 = paramFile1;
          } catch (IOException iOException) {}
          if (iOException == null)
            return; 
          throw iOException;
        } finally {
          try {
            multiDexExtractor.close();
          } catch (IOException iOException) {}
        } 
      } catch (RuntimeException runtimeException) {
        Log.w("MultiDex", "Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching.", runtimeException);
        return;
      } 
    } 
  }
  
  private static void expandFieldArray(Object paramObject, String paramString, Object[] paramArrayOfObject) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    Field field = findField(paramObject, paramString);
    Object[] arrayOfObject2 = (Object[])field.get(paramObject);
    Object[] arrayOfObject1 = (Object[])Array.newInstance(arrayOfObject2.getClass().getComponentType(), arrayOfObject2.length + paramArrayOfObject.length);
    System.arraycopy(arrayOfObject2, 0, arrayOfObject1, 0, arrayOfObject2.length);
    System.arraycopy(paramArrayOfObject, 0, arrayOfObject1, arrayOfObject2.length, paramArrayOfObject.length);
    field.set(paramObject, arrayOfObject1);
  }
  
  private static Field findField(Object paramObject, String paramString) throws NoSuchFieldException {
    Class<?> clazz = paramObject.getClass();
    while (clazz != null) {
      try {
        Field field = clazz.getDeclaredField(paramString);
        if (!field.isAccessible())
          field.setAccessible(true); 
        return field;
      } catch (NoSuchFieldException noSuchFieldException) {
        clazz = clazz.getSuperclass();
      } 
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Field ");
    stringBuilder.append(paramString);
    stringBuilder.append(" not found in ");
    stringBuilder.append(paramObject.getClass());
    throw new NoSuchFieldException(stringBuilder.toString());
  }
  
  private static Method findMethod(Object paramObject, String paramString, Class<?>... paramVarArgs) throws NoSuchMethodException {
    Class<?> clazz = paramObject.getClass();
    while (clazz != null) {
      try {
        Method method = clazz.getDeclaredMethod(paramString, paramVarArgs);
        if (!method.isAccessible())
          method.setAccessible(true); 
        return method;
      } catch (NoSuchMethodException noSuchMethodException) {
        clazz = clazz.getSuperclass();
      } 
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Method ");
    stringBuilder.append(paramString);
    stringBuilder.append(" with parameters ");
    stringBuilder.append(Arrays.asList(paramVarArgs));
    stringBuilder.append(" not found in ");
    stringBuilder.append(paramObject.getClass());
    throw new NoSuchMethodException(stringBuilder.toString());
  }
  
  private static ApplicationInfo getApplicationInfo(Context paramContext) {
    try {
      return paramContext.getApplicationInfo();
    } catch (RuntimeException runtimeException) {
      Log.w("MultiDex", "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", runtimeException);
      return null;
    } 
  }
  
  private static File getDexDir(Context paramContext, File paramFile, String paramString) throws IOException {
    paramFile = new File(paramFile, "code_cache");
    try {
      mkdirChecked(paramFile);
      file = paramFile;
    } catch (IOException iOException) {
      file = new File(file.getFilesDir(), "code_cache");
      mkdirChecked(file);
    } 
    File file = new File(file, paramString);
    mkdirChecked(file);
    return file;
  }
  
  public static void install(Context paramContext) {
    Log.i("MultiDex", "Installing application");
    if (IS_VM_MULTIDEX_CAPABLE) {
      Log.i("MultiDex", "VM has multidex support, MultiDex support library is disabled.");
      return;
    } 
    if (Build.VERSION.SDK_INT >= 4)
      try {
        ApplicationInfo applicationInfo = getApplicationInfo(paramContext);
        if (applicationInfo == null) {
          Log.i("MultiDex", "No ApplicationInfo available, i.e. running on a test Context: MultiDex support library is disabled.");
          return;
        } 
        File file1 = new File();
        this(applicationInfo.sourceDir);
        File file2 = new File();
        this(applicationInfo.dataDir);
        doInstallation(paramContext, file1, file2, "secondary-dexes", "", true);
        Log.i("MultiDex", "install done");
        return;
      } catch (Exception exception) {
        Log.e("MultiDex", "MultiDex installation failure", exception);
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("MultiDex installation failed (");
        stringBuilder1.append(exception.getMessage());
        stringBuilder1.append(").");
        throw new RuntimeException(stringBuilder1.toString());
      }  
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("MultiDex installation failed. SDK ");
    stringBuilder.append(Build.VERSION.SDK_INT);
    stringBuilder.append(" is unsupported. Min SDK version is ");
    stringBuilder.append(4);
    stringBuilder.append(".");
    throw new RuntimeException(stringBuilder.toString());
  }
  
  public static void installInstrumentation(Context paramContext1, Context paramContext2) {
    Log.i("MultiDex", "Installing instrumentation");
    if (IS_VM_MULTIDEX_CAPABLE) {
      Log.i("MultiDex", "VM has multidex support, MultiDex support library is disabled.");
      return;
    } 
    if (Build.VERSION.SDK_INT >= 4)
      try {
        ApplicationInfo applicationInfo1 = getApplicationInfo(paramContext1);
        if (applicationInfo1 == null) {
          Log.i("MultiDex", "No ApplicationInfo available for instrumentation, i.e. running on a test Context: MultiDex support library is disabled.");
          return;
        } 
        ApplicationInfo applicationInfo2 = getApplicationInfo(paramContext2);
        if (applicationInfo2 == null) {
          Log.i("MultiDex", "No ApplicationInfo available, i.e. running on a test Context: MultiDex support library is disabled.");
          return;
        } 
        StringBuilder stringBuilder2 = new StringBuilder();
        this();
        stringBuilder2.append(paramContext1.getPackageName());
        stringBuilder2.append(".");
        String str = stringBuilder2.toString();
        File file1 = new File();
        this(applicationInfo2.dataDir);
        File file3 = new File();
        this(applicationInfo1.sourceDir);
        StringBuilder stringBuilder1 = new StringBuilder();
        this();
        stringBuilder1.append(str);
        stringBuilder1.append("secondary-dexes");
        doInstallation(paramContext2, file3, file1, stringBuilder1.toString(), str, false);
        File file2 = new File();
        this(applicationInfo2.sourceDir);
        doInstallation(paramContext2, file2, file1, "secondary-dexes", "", false);
        Log.i("MultiDex", "Installation done");
        return;
      } catch (Exception exception) {
        Log.e("MultiDex", "MultiDex installation failure", exception);
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("MultiDex installation failed (");
        stringBuilder1.append(exception.getMessage());
        stringBuilder1.append(").");
        throw new RuntimeException(stringBuilder1.toString());
      }  
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("MultiDex installation failed. SDK ");
    stringBuilder.append(Build.VERSION.SDK_INT);
    stringBuilder.append(" is unsupported. Min SDK version is ");
    stringBuilder.append(4);
    stringBuilder.append(".");
    throw new RuntimeException(stringBuilder.toString());
  }
  
  private static void installSecondaryDexes(ClassLoader paramClassLoader, File paramFile, List<? extends File> paramList) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException, SecurityException, ClassNotFoundException, InstantiationException {
    if (!paramList.isEmpty())
      if (Build.VERSION.SDK_INT >= 19) {
        V19.install(paramClassLoader, paramList, paramFile);
      } else if (Build.VERSION.SDK_INT >= 14) {
        V14.install(paramClassLoader, paramList);
      } else {
        V4.install(paramClassLoader, paramList);
      }  
  }
  
  static boolean isVMMultidexCapable(String paramString) {
    // Byte code:
    //   0: iconst_0
    //   1: istore_1
    //   2: iload_1
    //   3: istore_2
    //   4: aload_0
    //   5: ifnull -> 78
    //   8: ldc_w '(\d+)\.(\d+)(\.\d+)?'
    //   11: invokestatic compile : (Ljava/lang/String;)Ljava/util/regex/Pattern;
    //   14: aload_0
    //   15: invokevirtual matcher : (Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   18: astore_3
    //   19: iload_1
    //   20: istore_2
    //   21: aload_3
    //   22: invokevirtual matches : ()Z
    //   25: ifeq -> 78
    //   28: aload_3
    //   29: iconst_1
    //   30: invokevirtual group : (I)Ljava/lang/String;
    //   33: invokestatic parseInt : (Ljava/lang/String;)I
    //   36: istore #4
    //   38: aload_3
    //   39: iconst_2
    //   40: invokevirtual group : (I)Ljava/lang/String;
    //   43: invokestatic parseInt : (Ljava/lang/String;)I
    //   46: istore #5
    //   48: iload #4
    //   50: iconst_2
    //   51: if_icmpgt -> 70
    //   54: iload_1
    //   55: istore_2
    //   56: iload #4
    //   58: iconst_2
    //   59: if_icmpne -> 78
    //   62: iload_1
    //   63: istore_2
    //   64: iload #5
    //   66: iconst_1
    //   67: if_icmplt -> 78
    //   70: iconst_1
    //   71: istore_2
    //   72: goto -> 78
    //   75: astore_3
    //   76: iload_1
    //   77: istore_2
    //   78: new java/lang/StringBuilder
    //   81: dup
    //   82: invokespecial <init> : ()V
    //   85: astore_3
    //   86: aload_3
    //   87: ldc_w 'VM with version '
    //   90: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: pop
    //   94: aload_3
    //   95: aload_0
    //   96: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: pop
    //   100: iload_2
    //   101: ifeq -> 111
    //   104: ldc_w ' has multidex support'
    //   107: astore_0
    //   108: goto -> 115
    //   111: ldc_w ' does not have multidex support'
    //   114: astore_0
    //   115: aload_3
    //   116: aload_0
    //   117: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   120: pop
    //   121: ldc 'MultiDex'
    //   123: aload_3
    //   124: invokevirtual toString : ()Ljava/lang/String;
    //   127: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)I
    //   130: pop
    //   131: iload_2
    //   132: ireturn
    // Exception table:
    //   from	to	target	type
    //   28	48	75	java/lang/NumberFormatException
  }
  
  private static void mkdirChecked(File paramFile) throws IOException {
    paramFile.mkdir();
    if (!paramFile.isDirectory()) {
      File file = paramFile.getParentFile();
      if (file == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to create dir ");
        stringBuilder.append(paramFile.getPath());
        stringBuilder.append(". Parent file is null.");
        Log.e("MultiDex", stringBuilder.toString());
      } else {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Failed to create dir ");
        stringBuilder1.append(paramFile.getPath());
        stringBuilder1.append(". parent file is a dir ");
        stringBuilder1.append(stringBuilder.isDirectory());
        stringBuilder1.append(", a file ");
        stringBuilder1.append(stringBuilder.isFile());
        stringBuilder1.append(", exists ");
        stringBuilder1.append(stringBuilder.exists());
        stringBuilder1.append(", readable ");
        stringBuilder1.append(stringBuilder.canRead());
        stringBuilder1.append(", writable ");
        stringBuilder1.append(stringBuilder.canWrite());
        Log.e("MultiDex", stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Failed to create directory ");
      stringBuilder.append(paramFile.getPath());
      throw new IOException(stringBuilder.toString());
    } 
  }
  
  private static final class V14 {
    private static final int EXTRACTED_SUFFIX_LENGTH = 4;
    
    private final ElementConstructor elementConstructor;
    
    private V14() throws ClassNotFoundException, SecurityException, NoSuchMethodException {
      JBMR2ElementConstructor jBMR2ElementConstructor;
      Class<?> clazz = Class.forName("dalvik.system.DexPathList$Element");
      try {
        ICSElementConstructor iCSElementConstructor = new ICSElementConstructor();
        this(clazz);
      } catch (NoSuchMethodException noSuchMethodException) {
        try {
          JBMR11ElementConstructor jBMR11ElementConstructor = new JBMR11ElementConstructor();
          this(clazz);
        } catch (NoSuchMethodException noSuchMethodException1) {
          jBMR2ElementConstructor = new JBMR2ElementConstructor(clazz);
        } 
      } 
      this.elementConstructor = jBMR2ElementConstructor;
    }
    
    static void install(ClassLoader param1ClassLoader, List<? extends File> param1List) throws IOException, SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
      Object object = MultiDex.findField(param1ClassLoader, "pathList").get(param1ClassLoader);
      Object[] arrayOfObject = (new V14()).makeDexElements(param1List);
      try {
        MultiDex.expandFieldArray(object, "dexElements", arrayOfObject);
      } catch (NoSuchFieldException noSuchFieldException) {
        Log.w("MultiDex", "Failed find field 'dexElements' attempting 'pathElements'", noSuchFieldException);
        MultiDex.expandFieldArray(object, "pathElements", arrayOfObject);
      } 
    }
    
    private Object[] makeDexElements(List<? extends File> param1List) throws IOException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
      int i = param1List.size();
      Object[] arrayOfObject = new Object[i];
      for (byte b = 0; b < i; b++) {
        File file = param1List.get(b);
        arrayOfObject[b] = this.elementConstructor.newInstance(file, DexFile.loadDex(file.getPath(), optimizedPathFor(file), 0));
      } 
      return arrayOfObject;
    }
    
    private static String optimizedPathFor(File param1File) {
      File file = param1File.getParentFile();
      String str = param1File.getName();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str.substring(0, str.length() - EXTRACTED_SUFFIX_LENGTH));
      stringBuilder.append(".dex");
      return (new File(file, stringBuilder.toString())).getPath();
    }
    
    private static interface ElementConstructor {
      Object newInstance(File param2File, DexFile param2DexFile) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException;
    }
    
    private static class ICSElementConstructor implements ElementConstructor {
      private final Constructor<?> elementConstructor;
      
      ICSElementConstructor(Class<?> param2Class) throws SecurityException, NoSuchMethodException {
        Constructor<?> constructor = param2Class.getConstructor(new Class[] { File.class, ZipFile.class, DexFile.class });
        this.elementConstructor = constructor;
        constructor.setAccessible(true);
      }
      
      public Object newInstance(File param2File, DexFile param2DexFile) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
        return this.elementConstructor.newInstance(new Object[] { param2File, new ZipFile(param2File), param2DexFile });
      }
    }
    
    private static class JBMR11ElementConstructor implements ElementConstructor {
      private final Constructor<?> elementConstructor;
      
      JBMR11ElementConstructor(Class<?> param2Class) throws SecurityException, NoSuchMethodException {
        Constructor<?> constructor = param2Class.getConstructor(new Class[] { File.class, File.class, DexFile.class });
        this.elementConstructor = constructor;
        constructor.setAccessible(true);
      }
      
      public Object newInstance(File param2File, DexFile param2DexFile) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return this.elementConstructor.newInstance(new Object[] { param2File, param2File, param2DexFile });
      }
    }
    
    private static class JBMR2ElementConstructor implements ElementConstructor {
      private final Constructor<?> elementConstructor;
      
      JBMR2ElementConstructor(Class<?> param2Class) throws SecurityException, NoSuchMethodException {
        Constructor<?> constructor = param2Class.getConstructor(new Class[] { File.class, boolean.class, File.class, DexFile.class });
        this.elementConstructor = constructor;
        constructor.setAccessible(true);
      }
      
      public Object newInstance(File param2File, DexFile param2DexFile) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return this.elementConstructor.newInstance(new Object[] { param2File, Boolean.FALSE, param2File, param2DexFile });
      }
    }
  }
  
  private static interface ElementConstructor {
    Object newInstance(File param1File, DexFile param1DexFile) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException;
  }
  
  private static class ICSElementConstructor implements V14.ElementConstructor {
    private final Constructor<?> elementConstructor;
    
    ICSElementConstructor(Class<?> param1Class) throws SecurityException, NoSuchMethodException {
      Constructor<?> constructor = param1Class.getConstructor(new Class[] { File.class, ZipFile.class, DexFile.class });
      this.elementConstructor = constructor;
      constructor.setAccessible(true);
    }
    
    public Object newInstance(File param1File, DexFile param1DexFile) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
      return this.elementConstructor.newInstance(new Object[] { param1File, new ZipFile(param1File), param1DexFile });
    }
  }
  
  private static class JBMR11ElementConstructor implements V14.ElementConstructor {
    private final Constructor<?> elementConstructor;
    
    JBMR11ElementConstructor(Class<?> param1Class) throws SecurityException, NoSuchMethodException {
      Constructor<?> constructor = param1Class.getConstructor(new Class[] { File.class, File.class, DexFile.class });
      this.elementConstructor = constructor;
      constructor.setAccessible(true);
    }
    
    public Object newInstance(File param1File, DexFile param1DexFile) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
      return this.elementConstructor.newInstance(new Object[] { param1File, param1File, param1DexFile });
    }
  }
  
  private static class JBMR2ElementConstructor implements V14.ElementConstructor {
    private final Constructor<?> elementConstructor;
    
    JBMR2ElementConstructor(Class<?> param1Class) throws SecurityException, NoSuchMethodException {
      Constructor<?> constructor = param1Class.getConstructor(new Class[] { File.class, boolean.class, File.class, DexFile.class });
      this.elementConstructor = constructor;
      constructor.setAccessible(true);
    }
    
    public Object newInstance(File param1File, DexFile param1DexFile) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
      return this.elementConstructor.newInstance(new Object[] { param1File, Boolean.FALSE, param1File, param1DexFile });
    }
  }
  
  private static final class V19 {
    static void install(ClassLoader param1ClassLoader, List<? extends File> param1List, File param1File) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException {
      Object object = MultiDex.findField(param1ClassLoader, "pathList").get(param1ClassLoader);
      ArrayList<IOException> arrayList = new ArrayList();
      MultiDex.expandFieldArray(object, "dexElements", makeDexElements(object, new ArrayList<File>(param1List), param1File, arrayList));
      if (arrayList.size() > 0) {
        IOException[] arrayOfIOException1;
        Iterator<IOException> iterator = arrayList.iterator();
        while (iterator.hasNext())
          Log.w("MultiDex", "Exception in makeDexElement", iterator.next()); 
        Field field = MultiDex.findField(object, "dexElementsSuppressedExceptions");
        IOException[] arrayOfIOException2 = (IOException[])field.get(object);
        if (arrayOfIOException2 == null) {
          arrayOfIOException1 = arrayList.<IOException>toArray(new IOException[arrayList.size()]);
        } else {
          arrayOfIOException1 = new IOException[arrayList.size() + arrayOfIOException2.length];
          arrayList.toArray(arrayOfIOException1);
          System.arraycopy(arrayOfIOException2, 0, arrayOfIOException1, arrayList.size(), arrayOfIOException2.length);
        } 
        field.set(object, arrayOfIOException1);
        IOException iOException = new IOException("I/O exception during makeDexElement");
        iOException.initCause(arrayList.get(0));
        throw iOException;
      } 
    }
    
    private static Object[] makeDexElements(Object param1Object, ArrayList<File> param1ArrayList, File param1File, ArrayList<IOException> param1ArrayList1) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
      return (Object[])MultiDex.findMethod(param1Object, "makeDexElements", new Class[] { ArrayList.class, File.class, ArrayList.class }).invoke(param1Object, new Object[] { param1ArrayList, param1File, param1ArrayList1 });
    }
  }
  
  private static final class V4 {
    static void install(ClassLoader param1ClassLoader, List<? extends File> param1List) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, IOException {
      int i = param1List.size();
      Field field = MultiDex.findField(param1ClassLoader, "path");
      StringBuilder stringBuilder = new StringBuilder((String)field.get(param1ClassLoader));
      String[] arrayOfString = new String[i];
      File[] arrayOfFile = new File[i];
      ZipFile[] arrayOfZipFile = new ZipFile[i];
      DexFile[] arrayOfDexFile = new DexFile[i];
      ListIterator<? extends File> listIterator = param1List.listIterator();
      while (listIterator.hasNext()) {
        File file = listIterator.next();
        String str = file.getAbsolutePath();
        stringBuilder.append(':');
        stringBuilder.append(str);
        i = listIterator.previousIndex();
        arrayOfString[i] = str;
        arrayOfFile[i] = file;
        arrayOfZipFile[i] = new ZipFile(file);
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str);
        stringBuilder1.append(".dex");
        arrayOfDexFile[i] = DexFile.loadDex(str, stringBuilder1.toString(), 0);
      } 
      field.set(param1ClassLoader, stringBuilder.toString());
      MultiDex.expandFieldArray(param1ClassLoader, "mPaths", (Object[])arrayOfString);
      MultiDex.expandFieldArray(param1ClassLoader, "mFiles", (Object[])arrayOfFile);
      MultiDex.expandFieldArray(param1ClassLoader, "mZips", (Object[])arrayOfZipFile);
      MultiDex.expandFieldArray(param1ClassLoader, "mDexs", (Object[])arrayOfDexFile);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\multidex\MultiDex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */