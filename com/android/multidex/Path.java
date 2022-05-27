package com.android.multidex;

import com.android.dx.cf.direct.DirectClassFile;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

class Path {
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream(40960);
  
  private final String definition;
  
  List<ClassPathElement> elements = new ArrayList<ClassPathElement>();
  
  private final byte[] readBuffer = new byte[20480];
  
  Path(String paramString) throws IOException {
    this.definition = paramString;
    String[] arrayOfString = paramString.split(Pattern.quote(File.pathSeparator));
    int i = arrayOfString.length;
    byte b = 0;
    while (b < i) {
      String str = arrayOfString[b];
      try {
        File file = new File();
        this(str);
        addElement(getClassPathElement(file));
        b++;
      } catch (IOException iOException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Wrong classpath: ");
        stringBuilder.append(iOException.getMessage());
        throw new IOException(stringBuilder.toString(), iOException);
      } 
    } 
  }
  
  private void addElement(ClassPathElement paramClassPathElement) {
    this.elements.add(paramClassPathElement);
  }
  
  static ClassPathElement getClassPathElement(File paramFile) throws ZipException, IOException {
    if (paramFile.isDirectory())
      return new FolderPathElement(paramFile); 
    if (paramFile.isFile())
      return new ArchivePathElement(new ZipFile(paramFile)); 
    if (paramFile.exists()) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("\"");
      stringBuilder1.append(paramFile.getPath());
      stringBuilder1.append("\" is not a directory neither a zip file");
      throw new IOException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("File \"");
    stringBuilder.append(paramFile.getPath());
    stringBuilder.append("\" not found");
    throw new FileNotFoundException(stringBuilder.toString());
  }
  
  private static byte[] readStream(InputStream paramInputStream, ByteArrayOutputStream paramByteArrayOutputStream, byte[] paramArrayOfbyte) throws IOException {
    try {
      while (true) {
        int i = paramInputStream.read(paramArrayOfbyte);
        if (i < 0)
          return paramByteArrayOutputStream.toByteArray(); 
        paramByteArrayOutputStream.write(paramArrayOfbyte, 0, i);
      } 
    } finally {
      paramInputStream.close();
    } 
  }
  
  DirectClassFile getClass(String paramString) throws FileNotFoundException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aconst_null
    //   3: astore_2
    //   4: aload_0
    //   5: getfield elements : Ljava/util/List;
    //   8: invokeinterface iterator : ()Ljava/util/Iterator;
    //   13: astore_3
    //   14: aload_2
    //   15: astore #4
    //   17: aload_3
    //   18: invokeinterface hasNext : ()Z
    //   23: ifeq -> 131
    //   26: aload_3
    //   27: invokeinterface next : ()Ljava/lang/Object;
    //   32: checkcast com/android/multidex/ClassPathElement
    //   35: astore #4
    //   37: aload #4
    //   39: aload_1
    //   40: invokeinterface open : (Ljava/lang/String;)Ljava/io/InputStream;
    //   45: astore #5
    //   47: aload #5
    //   49: aload_0
    //   50: getfield baos : Ljava/io/ByteArrayOutputStream;
    //   53: aload_0
    //   54: getfield readBuffer : [B
    //   57: invokestatic readStream : (Ljava/io/InputStream;Ljava/io/ByteArrayOutputStream;[B)[B
    //   60: astore #6
    //   62: aload_0
    //   63: getfield baos : Ljava/io/ByteArrayOutputStream;
    //   66: invokevirtual reset : ()V
    //   69: new com/android/dx/cf/direct/DirectClassFile
    //   72: astore #4
    //   74: aload #4
    //   76: aload #6
    //   78: aload_1
    //   79: iconst_0
    //   80: invokespecial <init> : ([BLjava/lang/String;Z)V
    //   83: aload #4
    //   85: getstatic com/android/dx/cf/direct/StdAttributeFactory.THE_ONE : Lcom/android/dx/cf/direct/StdAttributeFactory;
    //   88: invokevirtual setAttributeFactory : (Lcom/android/dx/cf/direct/AttributeFactory;)V
    //   91: aload #4
    //   93: astore_2
    //   94: aload #5
    //   96: invokevirtual close : ()V
    //   99: goto -> 131
    //   102: astore #6
    //   104: goto -> 112
    //   107: astore #6
    //   109: aload_2
    //   110: astore #4
    //   112: aload #4
    //   114: astore_2
    //   115: aload #5
    //   117: invokevirtual close : ()V
    //   120: aload #4
    //   122: astore_2
    //   123: aload #6
    //   125: athrow
    //   126: astore #4
    //   128: goto -> 14
    //   131: aload #4
    //   133: ifnull -> 141
    //   136: aload_0
    //   137: monitorexit
    //   138: aload #4
    //   140: areturn
    //   141: new java/io/FileNotFoundException
    //   144: astore #4
    //   146: new java/lang/StringBuilder
    //   149: astore_2
    //   150: aload_2
    //   151: invokespecial <init> : ()V
    //   154: aload_2
    //   155: ldc 'File "'
    //   157: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: pop
    //   161: aload_2
    //   162: aload_1
    //   163: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   166: pop
    //   167: aload_2
    //   168: ldc '" not found'
    //   170: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   173: pop
    //   174: aload #4
    //   176: aload_2
    //   177: invokevirtual toString : ()Ljava/lang/String;
    //   180: invokespecial <init> : (Ljava/lang/String;)V
    //   183: aload #4
    //   185: athrow
    //   186: astore_1
    //   187: aload_0
    //   188: monitorexit
    //   189: aload_1
    //   190: athrow
    //   191: astore #4
    //   193: goto -> 14
    // Exception table:
    //   from	to	target	type
    //   4	14	186	finally
    //   17	37	186	finally
    //   37	47	191	java/io/IOException
    //   37	47	186	finally
    //   47	83	107	finally
    //   83	91	102	finally
    //   94	99	126	java/io/IOException
    //   94	99	186	finally
    //   115	120	126	java/io/IOException
    //   115	120	186	finally
    //   123	126	126	java/io/IOException
    //   123	126	186	finally
    //   141	186	186	finally
  }
  
  Iterable<ClassPathElement> getElements() {
    return this.elements;
  }
  
  public String toString() {
    return this.definition;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\multidex\Path.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */