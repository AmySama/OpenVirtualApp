package com.tencent.mapsdk.rastercore.tile;

import java.net.URL;
import java.util.Locale;

public final class c {
  private static String a;
  
  static {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Locale.getDefault().getLanguage());
    stringBuilder.append("-");
    stringBuilder.append(Locale.getDefault().getCountry());
    a = stringBuilder.toString();
  }
  
  public static byte[] a(URL paramURL) {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aconst_null
    //   3: astore_2
    //   4: aconst_null
    //   5: astore_3
    //   6: aconst_null
    //   7: astore #4
    //   9: aload_0
    //   10: ifnonnull -> 15
    //   13: aconst_null
    //   14: areturn
    //   15: invokestatic getDefaultHost : ()Ljava/lang/String;
    //   18: ifnull -> 59
    //   21: new java/net/Proxy
    //   24: astore #5
    //   26: getstatic java/net/Proxy$Type.HTTP : Ljava/net/Proxy$Type;
    //   29: astore #6
    //   31: new java/net/InetSocketAddress
    //   34: astore #7
    //   36: aload #7
    //   38: invokestatic getDefaultHost : ()Ljava/lang/String;
    //   41: invokestatic getDefaultPort : ()I
    //   44: invokespecial <init> : (Ljava/lang/String;I)V
    //   47: aload #5
    //   49: aload #6
    //   51: aload #7
    //   53: invokespecial <init> : (Ljava/net/Proxy$Type;Ljava/net/SocketAddress;)V
    //   56: goto -> 62
    //   59: aconst_null
    //   60: astore #5
    //   62: aload #5
    //   64: ifnull -> 82
    //   67: aload_0
    //   68: aload #5
    //   70: invokevirtual openConnection : (Ljava/net/Proxy;)Ljava/net/URLConnection;
    //   73: astore_0
    //   74: aload_0
    //   75: checkcast java/net/HttpURLConnection
    //   78: astore_0
    //   79: goto -> 90
    //   82: aload_0
    //   83: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   86: astore_0
    //   87: goto -> 74
    //   90: aload_0
    //   91: sipush #5000
    //   94: invokevirtual setConnectTimeout : (I)V
    //   97: aload_0
    //   98: sipush #15000
    //   101: invokevirtual setReadTimeout : (I)V
    //   104: aload_0
    //   105: ldc 'Accept-Encoding'
    //   107: ldc 'gzip, deflate'
    //   109: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   112: aload_0
    //   113: ldc 'User-Agent'
    //   115: ldc 'QmapSdk/1.2.8 Android'
    //   117: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   120: aload_0
    //   121: ldc 'Accept-Language'
    //   123: getstatic com/tencent/mapsdk/rastercore/tile/c.a : Ljava/lang/String;
    //   126: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   129: aload_0
    //   130: invokevirtual getResponseCode : ()I
    //   133: sipush #200
    //   136: if_icmpne -> 541
    //   139: aload_0
    //   140: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   143: astore #5
    //   145: aload #5
    //   147: astore #6
    //   149: aload #5
    //   151: astore #8
    //   153: aload_0
    //   154: invokevirtual getContentType : ()Ljava/lang/String;
    //   157: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   160: invokevirtual trim : ()Ljava/lang/String;
    //   163: ldc 'text'
    //   165: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   168: ifeq -> 364
    //   171: aload #5
    //   173: astore #6
    //   175: aload #5
    //   177: astore #8
    //   179: new java/io/InputStreamReader
    //   182: astore #9
    //   184: aload #5
    //   186: astore #6
    //   188: aload #5
    //   190: astore #8
    //   192: aload #9
    //   194: aload #5
    //   196: invokespecial <init> : (Ljava/io/InputStream;)V
    //   199: new java/io/BufferedReader
    //   202: astore #10
    //   204: aload #10
    //   206: aload #9
    //   208: invokespecial <init> : (Ljava/io/Reader;)V
    //   211: aload #10
    //   213: invokevirtual readLine : ()Ljava/lang/String;
    //   216: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   219: invokevirtual trim : ()Ljava/lang/String;
    //   222: astore #11
    //   224: aload #4
    //   226: astore #8
    //   228: aload #5
    //   230: astore_1
    //   231: aload #9
    //   233: astore #6
    //   235: aload #10
    //   237: astore #7
    //   239: aload #11
    //   241: ifnull -> 553
    //   244: aload #4
    //   246: astore #8
    //   248: aload #5
    //   250: astore_1
    //   251: aload #9
    //   253: astore #6
    //   255: aload #10
    //   257: astore #7
    //   259: aload #11
    //   261: ldc '0'
    //   263: invokevirtual equals : (Ljava/lang/Object;)Z
    //   266: ifeq -> 553
    //   269: iconst_1
    //   270: newarray byte
    //   272: dup
    //   273: iconst_0
    //   274: iconst_m1
    //   275: bastore
    //   276: astore #8
    //   278: aload #5
    //   280: astore_1
    //   281: aload #9
    //   283: astore #6
    //   285: aload #10
    //   287: astore #7
    //   289: goto -> 553
    //   292: astore #7
    //   294: aload #5
    //   296: astore #6
    //   298: aload #9
    //   300: astore #8
    //   302: aload #10
    //   304: astore #5
    //   306: goto -> 614
    //   309: astore #7
    //   311: aload #5
    //   313: astore #7
    //   315: aload #9
    //   317: astore #6
    //   319: aload #10
    //   321: astore #5
    //   323: goto -> 653
    //   326: astore #7
    //   328: aconst_null
    //   329: astore_1
    //   330: aload #5
    //   332: astore #6
    //   334: aload #9
    //   336: astore #8
    //   338: aload_1
    //   339: astore #5
    //   341: goto -> 614
    //   344: astore #7
    //   346: aconst_null
    //   347: astore #8
    //   349: aload #5
    //   351: astore #7
    //   353: aload #9
    //   355: astore #6
    //   357: aload #8
    //   359: astore #5
    //   361: goto -> 653
    //   364: aload #5
    //   366: astore #7
    //   368: aload #5
    //   370: astore #6
    //   372: aload #5
    //   374: astore #8
    //   376: ldc 'gzip'
    //   378: aload_0
    //   379: ldc 'Content-Encoding'
    //   381: invokevirtual getHeaderField : (Ljava/lang/String;)Ljava/lang/String;
    //   384: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   387: ifeq -> 418
    //   390: aload #5
    //   392: astore #6
    //   394: aload #5
    //   396: astore #8
    //   398: new java/util/zip/GZIPInputStream
    //   401: astore #7
    //   403: aload #5
    //   405: astore #6
    //   407: aload #5
    //   409: astore #8
    //   411: aload #7
    //   413: aload #5
    //   415: invokespecial <init> : (Ljava/io/InputStream;)V
    //   418: aload #7
    //   420: astore #6
    //   422: aload #7
    //   424: astore #8
    //   426: aload #7
    //   428: invokestatic a : (Ljava/io/InputStream;)[B
    //   431: astore_1
    //   432: aload #7
    //   434: astore #6
    //   436: aload_1
    //   437: iconst_0
    //   438: aload_1
    //   439: arraylength
    //   440: invokestatic decodeByteArray : ([BII)Landroid/graphics/Bitmap;
    //   443: pop
    //   444: aconst_null
    //   445: astore #5
    //   447: aload_1
    //   448: astore #8
    //   450: aload #5
    //   452: astore #6
    //   454: aload #7
    //   456: astore_1
    //   457: aload #5
    //   459: astore #7
    //   461: goto -> 553
    //   464: astore_1
    //   465: aload #7
    //   467: astore #6
    //   469: aload #7
    //   471: astore #8
    //   473: new java/lang/StringBuilder
    //   476: astore #5
    //   478: aload #7
    //   480: astore #6
    //   482: aload #7
    //   484: astore #8
    //   486: aload #5
    //   488: ldc 'decoder bitmap error:'
    //   490: invokespecial <init> : (Ljava/lang/String;)V
    //   493: aload #7
    //   495: astore #6
    //   497: aload #7
    //   499: astore #8
    //   501: aload #5
    //   503: aload_1
    //   504: invokevirtual getMessage : ()Ljava/lang/String;
    //   507: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   510: pop
    //   511: aload #7
    //   513: astore_1
    //   514: goto -> 543
    //   517: astore #7
    //   519: aconst_null
    //   520: astore #5
    //   522: aload #5
    //   524: astore #8
    //   526: goto -> 614
    //   529: astore #5
    //   531: aconst_null
    //   532: astore #6
    //   534: aload #8
    //   536: astore #7
    //   538: goto -> 649
    //   541: aconst_null
    //   542: astore_1
    //   543: aconst_null
    //   544: astore #6
    //   546: aconst_null
    //   547: astore #7
    //   549: aload #4
    //   551: astore #8
    //   553: aload #8
    //   555: astore_3
    //   556: aload_1
    //   557: astore #4
    //   559: aload #6
    //   561: astore #10
    //   563: aload #7
    //   565: astore #9
    //   567: aload_0
    //   568: ifnull -> 693
    //   571: aload #7
    //   573: astore #5
    //   575: goto -> 675
    //   578: astore #7
    //   580: aconst_null
    //   581: astore #8
    //   583: aload #8
    //   585: astore #5
    //   587: aload #5
    //   589: astore #6
    //   591: goto -> 614
    //   594: astore #5
    //   596: goto -> 643
    //   599: astore #7
    //   601: aconst_null
    //   602: astore #6
    //   604: aload #6
    //   606: astore #8
    //   608: aload #8
    //   610: astore #5
    //   612: aload_1
    //   613: astore_0
    //   614: aload_0
    //   615: ifnull -> 622
    //   618: aload_0
    //   619: invokevirtual disconnect : ()V
    //   622: aload #5
    //   624: invokestatic a : (Ljava/io/Closeable;)V
    //   627: aload #8
    //   629: invokestatic a : (Ljava/io/Closeable;)V
    //   632: aload #6
    //   634: invokestatic a : (Ljava/io/Closeable;)V
    //   637: aload #7
    //   639: athrow
    //   640: astore_0
    //   641: aconst_null
    //   642: astore_0
    //   643: aconst_null
    //   644: astore #7
    //   646: aconst_null
    //   647: astore #6
    //   649: aload #6
    //   651: astore #5
    //   653: aload #7
    //   655: astore #4
    //   657: aload #6
    //   659: astore #10
    //   661: aload #5
    //   663: astore #9
    //   665: aload_0
    //   666: ifnull -> 693
    //   669: aload #7
    //   671: astore_1
    //   672: aload_2
    //   673: astore #8
    //   675: aload_0
    //   676: invokevirtual disconnect : ()V
    //   679: aload #5
    //   681: astore #9
    //   683: aload #6
    //   685: astore #10
    //   687: aload_1
    //   688: astore #4
    //   690: aload #8
    //   692: astore_3
    //   693: aload #9
    //   695: invokestatic a : (Ljava/io/Closeable;)V
    //   698: aload #10
    //   700: invokestatic a : (Ljava/io/Closeable;)V
    //   703: aload #4
    //   705: invokestatic a : (Ljava/io/Closeable;)V
    //   708: aload_3
    //   709: areturn
    // Exception table:
    //   from	to	target	type
    //   15	56	640	java/lang/Exception
    //   15	56	599	finally
    //   67	74	640	java/lang/Exception
    //   67	74	599	finally
    //   74	79	640	java/lang/Exception
    //   74	79	599	finally
    //   82	87	640	java/lang/Exception
    //   82	87	599	finally
    //   90	145	594	java/lang/Exception
    //   90	145	578	finally
    //   153	171	529	java/lang/Exception
    //   153	171	517	finally
    //   179	184	529	java/lang/Exception
    //   179	184	517	finally
    //   192	199	529	java/lang/Exception
    //   192	199	517	finally
    //   199	211	344	java/lang/Exception
    //   199	211	326	finally
    //   211	224	309	java/lang/Exception
    //   211	224	292	finally
    //   259	269	309	java/lang/Exception
    //   259	269	292	finally
    //   376	390	529	java/lang/Exception
    //   376	390	517	finally
    //   398	403	529	java/lang/Exception
    //   398	403	517	finally
    //   411	418	529	java/lang/Exception
    //   411	418	517	finally
    //   426	432	529	java/lang/Exception
    //   426	432	517	finally
    //   436	444	464	java/lang/Exception
    //   436	444	517	finally
    //   473	478	529	java/lang/Exception
    //   473	478	517	finally
    //   486	493	529	java/lang/Exception
    //   486	493	517	finally
    //   501	511	529	java/lang/Exception
    //   501	511	517	finally
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\tile\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */