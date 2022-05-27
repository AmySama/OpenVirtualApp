package com.tencent.mapsdk.rastercore.tile.b;

import java.net.URL;

public final class e extends a {
  private String[] a = new String[] { "https://m0.map.gtimg.com/hwap", "https://m1.map.gtimg.com/hwap", "https://m2.map.gtimg.com/hwap", "https://m3.map.gtimg.com/hwap" };
  
  public e(int paramInt) {
    super(paramInt);
  }
  
  public final URL getTileUrl(int paramInt1, int paramInt2, int paramInt3, Object... paramVarArgs) {
    // Byte code:
    //   0: getstatic com/tencent/mapsdk/rastercore/b.a : I
    //   3: istore #5
    //   5: iconst_0
    //   6: istore #6
    //   8: aconst_null
    //   9: astore #7
    //   11: iload #5
    //   13: istore #8
    //   15: aload #4
    //   17: ifnull -> 105
    //   20: iload #5
    //   22: istore #8
    //   24: iload #5
    //   26: istore #9
    //   28: aload #4
    //   30: arraylength
    //   31: ifle -> 105
    //   34: iload #5
    //   36: istore #9
    //   38: aload #4
    //   40: iconst_0
    //   41: aaload
    //   42: invokevirtual toString : ()Ljava/lang/String;
    //   45: invokestatic parseInt : (Ljava/lang/String;)I
    //   48: istore #5
    //   50: iload #5
    //   52: istore #8
    //   54: iload #5
    //   56: istore #9
    //   58: aload #4
    //   60: arraylength
    //   61: iconst_3
    //   62: if_icmpne -> 105
    //   65: iload #5
    //   67: istore #9
    //   69: aload #4
    //   71: iconst_1
    //   72: aaload
    //   73: invokevirtual toString : ()Ljava/lang/String;
    //   76: astore #10
    //   78: aload #4
    //   80: iconst_2
    //   81: aaload
    //   82: invokevirtual toString : ()Ljava/lang/String;
    //   85: invokestatic parseBoolean : (Ljava/lang/String;)Z
    //   88: istore #11
    //   90: iload #5
    //   92: istore #8
    //   94: iload #11
    //   96: istore #6
    //   98: aload #10
    //   100: astore #4
    //   102: goto -> 108
    //   105: aconst_null
    //   106: astore #4
    //   108: new java/lang/StringBuffer
    //   111: astore #10
    //   113: iload_1
    //   114: iload_2
    //   115: iadd
    //   116: aload_0
    //   117: getfield a : [Ljava/lang/String;
    //   120: arraylength
    //   121: invokestatic a : (II)I
    //   124: istore #9
    //   126: ldc2_w 2.0
    //   129: iload_3
    //   130: i2d
    //   131: invokestatic pow : (DD)D
    //   134: iload_2
    //   135: i2d
    //   136: dsub
    //   137: dconst_1
    //   138: dsub
    //   139: d2i
    //   140: istore_2
    //   141: new java/lang/StringBuilder
    //   144: astore #12
    //   146: aload #12
    //   148: sipush #128
    //   151: invokespecial <init> : (I)V
    //   154: aload #12
    //   156: aload_0
    //   157: getfield a : [Ljava/lang/String;
    //   160: iload #9
    //   162: aaload
    //   163: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   166: pop
    //   167: aload #12
    //   169: ldc '?'
    //   171: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   174: pop
    //   175: aload #12
    //   177: ldc 'z='
    //   179: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   182: pop
    //   183: aload #12
    //   185: iload_3
    //   186: iconst_1
    //   187: isub
    //   188: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   191: pop
    //   192: aload #12
    //   194: ldc '&x='
    //   196: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   199: pop
    //   200: aload #12
    //   202: iload_1
    //   203: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   206: pop
    //   207: aload #12
    //   209: ldc '&y='
    //   211: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   214: pop
    //   215: aload #12
    //   217: iload_2
    //   218: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   221: pop
    //   222: aload #12
    //   224: ldc '&styleid='
    //   226: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   229: pop
    //   230: aload #12
    //   232: getstatic com/tencent/mapsdk/rastercore/b.e : I
    //   235: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   238: pop
    //   239: aload #12
    //   241: ldc '&version='
    //   243: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   246: pop
    //   247: aload #12
    //   249: iload #8
    //   251: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   254: pop
    //   255: aload #10
    //   257: aload #12
    //   259: invokevirtual toString : ()Ljava/lang/String;
    //   262: invokespecial <init> : (Ljava/lang/String;)V
    //   265: iload #6
    //   267: ifeq -> 303
    //   270: aload #10
    //   272: ldc '&v='
    //   274: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   277: pop
    //   278: aload #10
    //   280: invokestatic v : ()I
    //   283: invokevirtual append : (I)Ljava/lang/StringBuffer;
    //   286: pop
    //   287: aload #10
    //   289: ldc '&md5='
    //   291: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   294: pop
    //   295: aload #10
    //   297: aload #4
    //   299: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   302: pop
    //   303: new java/net/URL
    //   306: astore #4
    //   308: aload #4
    //   310: aload #10
    //   312: invokevirtual toString : ()Ljava/lang/String;
    //   315: invokespecial <init> : (Ljava/lang/String;)V
    //   318: goto -> 341
    //   321: astore #4
    //   323: new java/lang/StringBuilder
    //   326: dup
    //   327: ldc 'Error new URL with str:'
    //   329: invokespecial <init> : (Ljava/lang/String;)V
    //   332: aconst_null
    //   333: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   336: pop
    //   337: aload #7
    //   339: astore #4
    //   341: aload #4
    //   343: areturn
    //   344: astore #4
    //   346: iload #9
    //   348: istore #8
    //   350: goto -> 105
    //   353: astore #4
    //   355: iload #5
    //   357: istore #8
    //   359: aload #10
    //   361: astore #4
    //   363: goto -> 108
    // Exception table:
    //   from	to	target	type
    //   28	34	344	java/lang/Exception
    //   38	50	344	java/lang/Exception
    //   58	65	344	java/lang/Exception
    //   69	78	344	java/lang/Exception
    //   78	90	353	java/lang/Exception
    //   108	265	321	java/net/MalformedURLException
    //   270	303	321	java/net/MalformedURLException
    //   303	318	321	java/net/MalformedURLException
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\tile\b\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */