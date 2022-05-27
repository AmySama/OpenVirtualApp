package com.tencent.mm.opensdk.channel.a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;
import java.security.MessageDigest;

public class a {
  public static int a(Bundle paramBundle, String paramString, int paramInt) {
    if (paramBundle == null)
      return paramInt; 
    try {
      int i = paramBundle.getInt(paramString, paramInt);
      paramInt = i;
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("getIntExtra exception:");
      stringBuilder.append(exception.getMessage());
      Log.e("MicroMsg.IntentUtil", stringBuilder.toString());
    } 
    return paramInt;
  }
  
  public static Object a(int paramInt, String paramString) {
    switch (paramInt) {
      case 6:
        try {
          return Double.valueOf(paramString);
        } catch (Exception exception) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("resolveObj exception:");
          stringBuilder.append(exception.getMessage());
          Log.e("MicroMsg.SDK.PluginProvider.Resolver", stringBuilder.toString());
        } 
        return null;
      case 5:
        return Float.valueOf((String)exception);
      case 4:
        return Boolean.valueOf((String)exception);
      case 3:
        return exception;
      case 2:
        return Long.valueOf((String)exception);
      case 1:
        return Integer.valueOf((String)exception);
    } 
    Log.e("MicroMsg.SDK.PluginProvider.Resolver", "unknown type");
    return null;
  }
  
  public static String a(Bundle paramBundle, String paramString) {
    Exception exception2 = null;
    if (paramBundle == null)
      return null; 
    try {
      String str = paramBundle.getString(paramString);
    } catch (Exception exception1) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("getStringExtra exception:");
      stringBuilder.append(exception1.getMessage());
      Log.e("MicroMsg.IntentUtil", stringBuilder.toString());
      exception1 = exception2;
    } 
    return (String)exception1;
  }
  
  public static boolean a(Context paramContext, a parama) {
    String str1;
    String str2;
    if (paramContext == null || parama == null) {
      str1 = "send fail, invalid argument";
      Log.e("MicroMsg.SDK.MMessage", str1);
      return false;
    } 
    if (b.b(parama.b)) {
      str1 = "send fail, action is null";
      Log.e("MicroMsg.SDK.MMessage", str1);
      return false;
    } 
    StringBuilder stringBuilder2 = null;
    if (!b.b(parama.a)) {
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(parama.a);
      stringBuilder2.append(".permission.MM_MESSAGE");
      str2 = stringBuilder2.toString();
    } 
    Intent intent = new Intent(parama.b);
    Bundle bundle = parama.e;
    if (bundle != null)
      intent.putExtras(bundle); 
    String str3 = str1.getPackageName();
    intent.putExtra("_mmessage_sdkVersion", 638058496);
    intent.putExtra("_mmessage_appPackage", str3);
    intent.putExtra("_mmessage_content", parama.c);
    intent.putExtra("_mmessage_support_content_type", parama.d);
    intent.putExtra("_mmessage_checksum", a(parama.c, 638058496, str3));
    str1.sendBroadcast(intent, str2);
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("send mm message, intent=");
    stringBuilder1.append(intent);
    stringBuilder1.append(", perm=");
    stringBuilder1.append(str2);
    Log.d("MicroMsg.SDK.MMessage", stringBuilder1.toString());
    return true;
  }
  
  public static byte[] a(String paramString, int paramInt) {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: ifnull -> 883
    //   6: aload_0
    //   7: invokevirtual length : ()I
    //   10: ifne -> 16
    //   13: goto -> 883
    //   16: new java/net/URL
    //   19: astore_3
    //   20: aload_3
    //   21: aload_0
    //   22: invokespecial <init> : (Ljava/lang/String;)V
    //   25: aload_3
    //   26: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   29: checkcast java/net/HttpURLConnection
    //   32: astore_0
    //   33: aload_0
    //   34: ifnonnull -> 74
    //   37: ldc 'MicroMsg.SDK.NetUtil'
    //   39: ldc 'open connection failed.'
    //   41: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   44: aload_0
    //   45: ifnull -> 52
    //   48: aload_0
    //   49: invokevirtual disconnect : ()V
    //   52: aconst_null
    //   53: areturn
    //   54: astore #4
    //   56: goto -> 115
    //   59: astore #5
    //   61: goto -> 126
    //   64: astore #5
    //   66: goto -> 139
    //   69: astore #5
    //   71: goto -> 152
    //   74: aload_0
    //   75: ldc 'GET'
    //   77: invokevirtual setRequestMethod : (Ljava/lang/String;)V
    //   80: aload_0
    //   81: iload_1
    //   82: invokevirtual setConnectTimeout : (I)V
    //   85: aload_0
    //   86: iload_1
    //   87: invokevirtual setReadTimeout : (I)V
    //   90: aload_0
    //   91: invokevirtual getResponseCode : ()I
    //   94: istore_1
    //   95: iload_1
    //   96: sipush #300
    //   99: if_icmplt -> 165
    //   102: ldc 'MicroMsg.SDK.NetUtil'
    //   104: ldc 'httpURLConnectionGet 300'
    //   106: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   109: aload_0
    //   110: invokevirtual disconnect : ()V
    //   113: aconst_null
    //   114: areturn
    //   115: aconst_null
    //   116: astore_3
    //   117: aload_0
    //   118: astore #6
    //   120: aload #4
    //   122: astore_0
    //   123: goto -> 846
    //   126: aconst_null
    //   127: astore #4
    //   129: aload #4
    //   131: astore #6
    //   133: aload_0
    //   134: astore #7
    //   136: goto -> 420
    //   139: aconst_null
    //   140: astore #4
    //   142: aload #4
    //   144: astore #6
    //   146: aload_0
    //   147: astore #7
    //   149: goto -> 558
    //   152: aconst_null
    //   153: astore #6
    //   155: aload #6
    //   157: astore #4
    //   159: aload_0
    //   160: astore #7
    //   162: goto -> 696
    //   165: aload_0
    //   166: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   169: astore_3
    //   170: new java/io/ByteArrayOutputStream
    //   173: astore_2
    //   174: aload_2
    //   175: invokespecial <init> : ()V
    //   178: sipush #1024
    //   181: newarray byte
    //   183: astore #6
    //   185: aload_3
    //   186: aload #6
    //   188: invokevirtual read : ([B)I
    //   191: istore_1
    //   192: iload_1
    //   193: iconst_m1
    //   194: if_icmpeq -> 208
    //   197: aload_2
    //   198: aload #6
    //   200: iconst_0
    //   201: iload_1
    //   202: invokevirtual write : ([BII)V
    //   205: goto -> 185
    //   208: aload_2
    //   209: invokevirtual toByteArray : ()[B
    //   212: astore #6
    //   214: ldc 'MicroMsg.SDK.NetUtil'
    //   216: ldc 'httpGet end'
    //   218: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)V
    //   221: aload_0
    //   222: invokevirtual disconnect : ()V
    //   225: aload_3
    //   226: invokevirtual close : ()V
    //   229: aload_2
    //   230: invokevirtual close : ()V
    //   233: aload #6
    //   235: areturn
    //   236: astore #6
    //   238: aload_2
    //   239: astore #4
    //   241: aload #6
    //   243: astore_2
    //   244: aload_3
    //   245: astore #6
    //   247: aload_0
    //   248: astore_3
    //   249: goto -> 831
    //   252: astore #5
    //   254: aload_3
    //   255: astore #4
    //   257: aload_2
    //   258: astore #6
    //   260: aload_0
    //   261: astore #7
    //   263: goto -> 420
    //   266: astore #5
    //   268: aload_3
    //   269: astore #4
    //   271: aload_2
    //   272: astore #6
    //   274: aload_0
    //   275: astore #7
    //   277: goto -> 558
    //   280: astore #5
    //   282: aload_3
    //   283: astore #6
    //   285: aload_2
    //   286: astore #4
    //   288: aload_0
    //   289: astore #7
    //   291: goto -> 696
    //   294: astore_2
    //   295: aload_0
    //   296: astore #6
    //   298: aload_2
    //   299: astore_0
    //   300: goto -> 399
    //   303: astore_2
    //   304: aload_3
    //   305: astore #4
    //   307: aload_2
    //   308: astore_3
    //   309: aload_0
    //   310: astore #7
    //   312: aload_3
    //   313: astore_0
    //   314: goto -> 414
    //   317: astore_2
    //   318: aload_3
    //   319: astore #4
    //   321: aload_2
    //   322: astore_3
    //   323: aload_0
    //   324: astore #7
    //   326: aload_3
    //   327: astore_0
    //   328: goto -> 552
    //   331: astore_2
    //   332: aload_3
    //   333: astore #6
    //   335: aload_2
    //   336: astore_3
    //   337: aload_0
    //   338: astore #7
    //   340: aload_3
    //   341: astore_0
    //   342: goto -> 690
    //   345: astore_2
    //   346: aconst_null
    //   347: astore_3
    //   348: aload_0
    //   349: astore #6
    //   351: aload_2
    //   352: astore_0
    //   353: goto -> 399
    //   356: astore_3
    //   357: aconst_null
    //   358: astore #4
    //   360: aload_0
    //   361: astore #7
    //   363: aload_3
    //   364: astore_0
    //   365: goto -> 414
    //   368: astore_3
    //   369: aconst_null
    //   370: astore #4
    //   372: aload_0
    //   373: astore #7
    //   375: aload_3
    //   376: astore_0
    //   377: goto -> 552
    //   380: astore_3
    //   381: aconst_null
    //   382: astore #6
    //   384: aload_0
    //   385: astore #7
    //   387: aload_3
    //   388: astore_0
    //   389: goto -> 690
    //   392: astore_0
    //   393: aconst_null
    //   394: astore #6
    //   396: aload #6
    //   398: astore_3
    //   399: aload_3
    //   400: astore_2
    //   401: aconst_null
    //   402: astore_3
    //   403: goto -> 846
    //   406: astore_0
    //   407: aconst_null
    //   408: astore #7
    //   410: aload #7
    //   412: astore #4
    //   414: aconst_null
    //   415: astore #6
    //   417: aload_0
    //   418: astore #5
    //   420: aload #4
    //   422: astore_0
    //   423: aload #6
    //   425: astore_2
    //   426: aload #7
    //   428: astore_3
    //   429: new java/lang/StringBuilder
    //   432: astore #8
    //   434: aload #4
    //   436: astore_0
    //   437: aload #6
    //   439: astore_2
    //   440: aload #7
    //   442: astore_3
    //   443: aload #8
    //   445: invokespecial <init> : ()V
    //   448: aload #4
    //   450: astore_0
    //   451: aload #6
    //   453: astore_2
    //   454: aload #7
    //   456: astore_3
    //   457: aload #8
    //   459: ldc 'httpGet ex:'
    //   461: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   464: pop
    //   465: aload #4
    //   467: astore_0
    //   468: aload #6
    //   470: astore_2
    //   471: aload #7
    //   473: astore_3
    //   474: aload #8
    //   476: aload #5
    //   478: invokevirtual getMessage : ()Ljava/lang/String;
    //   481: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   484: pop
    //   485: aload #4
    //   487: astore_0
    //   488: aload #6
    //   490: astore_2
    //   491: aload #7
    //   493: astore_3
    //   494: ldc 'MicroMsg.SDK.NetUtil'
    //   496: aload #8
    //   498: invokevirtual toString : ()Ljava/lang/String;
    //   501: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   504: aload #7
    //   506: ifnull -> 518
    //   509: aload #7
    //   511: invokevirtual disconnect : ()V
    //   514: goto -> 518
    //   517: astore_0
    //   518: aload #4
    //   520: ifnull -> 532
    //   523: aload #4
    //   525: invokevirtual close : ()V
    //   528: goto -> 532
    //   531: astore_0
    //   532: aload #6
    //   534: ifnull -> 542
    //   537: aload #6
    //   539: invokevirtual close : ()V
    //   542: aconst_null
    //   543: areturn
    //   544: astore_0
    //   545: aconst_null
    //   546: astore #7
    //   548: aload #7
    //   550: astore #4
    //   552: aconst_null
    //   553: astore #6
    //   555: aload_0
    //   556: astore #5
    //   558: aload #4
    //   560: astore_0
    //   561: aload #6
    //   563: astore_2
    //   564: aload #7
    //   566: astore_3
    //   567: new java/lang/StringBuilder
    //   570: astore #8
    //   572: aload #4
    //   574: astore_0
    //   575: aload #6
    //   577: astore_2
    //   578: aload #7
    //   580: astore_3
    //   581: aload #8
    //   583: invokespecial <init> : ()V
    //   586: aload #4
    //   588: astore_0
    //   589: aload #6
    //   591: astore_2
    //   592: aload #7
    //   594: astore_3
    //   595: aload #8
    //   597: ldc 'httpGet ex:'
    //   599: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   602: pop
    //   603: aload #4
    //   605: astore_0
    //   606: aload #6
    //   608: astore_2
    //   609: aload #7
    //   611: astore_3
    //   612: aload #8
    //   614: aload #5
    //   616: invokevirtual getMessage : ()Ljava/lang/String;
    //   619: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   622: pop
    //   623: aload #4
    //   625: astore_0
    //   626: aload #6
    //   628: astore_2
    //   629: aload #7
    //   631: astore_3
    //   632: ldc 'MicroMsg.SDK.NetUtil'
    //   634: aload #8
    //   636: invokevirtual toString : ()Ljava/lang/String;
    //   639: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   642: aload #7
    //   644: ifnull -> 656
    //   647: aload #7
    //   649: invokevirtual disconnect : ()V
    //   652: goto -> 656
    //   655: astore_0
    //   656: aload #4
    //   658: ifnull -> 670
    //   661: aload #4
    //   663: invokevirtual close : ()V
    //   666: goto -> 670
    //   669: astore_0
    //   670: aload #6
    //   672: ifnull -> 680
    //   675: aload #6
    //   677: invokevirtual close : ()V
    //   680: aconst_null
    //   681: areturn
    //   682: astore_0
    //   683: aconst_null
    //   684: astore #7
    //   686: aload #7
    //   688: astore #6
    //   690: aconst_null
    //   691: astore #4
    //   693: aload_0
    //   694: astore #5
    //   696: aload #6
    //   698: astore_0
    //   699: aload #4
    //   701: astore_2
    //   702: aload #7
    //   704: astore_3
    //   705: new java/lang/StringBuilder
    //   708: astore #8
    //   710: aload #6
    //   712: astore_0
    //   713: aload #4
    //   715: astore_2
    //   716: aload #7
    //   718: astore_3
    //   719: aload #8
    //   721: invokespecial <init> : ()V
    //   724: aload #6
    //   726: astore_0
    //   727: aload #4
    //   729: astore_2
    //   730: aload #7
    //   732: astore_3
    //   733: aload #8
    //   735: ldc 'httpGet ex:'
    //   737: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   740: pop
    //   741: aload #6
    //   743: astore_0
    //   744: aload #4
    //   746: astore_2
    //   747: aload #7
    //   749: astore_3
    //   750: aload #8
    //   752: aload #5
    //   754: invokevirtual getMessage : ()Ljava/lang/String;
    //   757: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   760: pop
    //   761: aload #6
    //   763: astore_0
    //   764: aload #4
    //   766: astore_2
    //   767: aload #7
    //   769: astore_3
    //   770: ldc 'MicroMsg.SDK.NetUtil'
    //   772: aload #8
    //   774: invokevirtual toString : ()Ljava/lang/String;
    //   777: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   780: aload #7
    //   782: ifnull -> 794
    //   785: aload #7
    //   787: invokevirtual disconnect : ()V
    //   790: goto -> 794
    //   793: astore_0
    //   794: aload #6
    //   796: ifnull -> 808
    //   799: aload #6
    //   801: invokevirtual close : ()V
    //   804: goto -> 808
    //   807: astore_0
    //   808: aload #4
    //   810: ifnull -> 818
    //   813: aload #4
    //   815: invokevirtual close : ()V
    //   818: aconst_null
    //   819: areturn
    //   820: astore #6
    //   822: aload_2
    //   823: astore #4
    //   825: aload #6
    //   827: astore_2
    //   828: aload_0
    //   829: astore #6
    //   831: aload #6
    //   833: astore #7
    //   835: aload_2
    //   836: astore_0
    //   837: aload_3
    //   838: astore #6
    //   840: aload #7
    //   842: astore_2
    //   843: aload #4
    //   845: astore_3
    //   846: aload #6
    //   848: ifnull -> 861
    //   851: aload #6
    //   853: invokevirtual disconnect : ()V
    //   856: goto -> 861
    //   859: astore #6
    //   861: aload_2
    //   862: ifnull -> 873
    //   865: aload_2
    //   866: invokevirtual close : ()V
    //   869: goto -> 873
    //   872: astore_2
    //   873: aload_3
    //   874: ifnull -> 881
    //   877: aload_3
    //   878: invokevirtual close : ()V
    //   881: aload_0
    //   882: athrow
    //   883: ldc 'MicroMsg.SDK.NetUtil'
    //   885: ldc 'httpGet, url is null'
    //   887: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   890: aconst_null
    //   891: areturn
    //   892: astore_0
    //   893: goto -> 52
    //   896: astore_0
    //   897: goto -> 113
    //   900: astore_0
    //   901: goto -> 225
    //   904: astore_0
    //   905: goto -> 229
    //   908: astore_0
    //   909: goto -> 233
    //   912: astore_0
    //   913: goto -> 542
    //   916: astore_0
    //   917: goto -> 680
    //   920: astore_0
    //   921: goto -> 818
    //   924: astore_3
    //   925: goto -> 881
    // Exception table:
    //   from	to	target	type
    //   16	33	682	java/net/MalformedURLException
    //   16	33	544	java/io/IOException
    //   16	33	406	java/lang/Exception
    //   16	33	392	finally
    //   37	44	69	java/net/MalformedURLException
    //   37	44	64	java/io/IOException
    //   37	44	59	java/lang/Exception
    //   37	44	54	finally
    //   48	52	892	finally
    //   74	95	380	java/net/MalformedURLException
    //   74	95	368	java/io/IOException
    //   74	95	356	java/lang/Exception
    //   74	95	345	finally
    //   102	109	69	java/net/MalformedURLException
    //   102	109	64	java/io/IOException
    //   102	109	59	java/lang/Exception
    //   102	109	54	finally
    //   109	113	896	finally
    //   165	170	380	java/net/MalformedURLException
    //   165	170	368	java/io/IOException
    //   165	170	356	java/lang/Exception
    //   165	170	345	finally
    //   170	178	331	java/net/MalformedURLException
    //   170	178	317	java/io/IOException
    //   170	178	303	java/lang/Exception
    //   170	178	294	finally
    //   178	185	280	java/net/MalformedURLException
    //   178	185	266	java/io/IOException
    //   178	185	252	java/lang/Exception
    //   178	185	236	finally
    //   185	192	280	java/net/MalformedURLException
    //   185	192	266	java/io/IOException
    //   185	192	252	java/lang/Exception
    //   185	192	236	finally
    //   197	205	280	java/net/MalformedURLException
    //   197	205	266	java/io/IOException
    //   197	205	252	java/lang/Exception
    //   197	205	236	finally
    //   208	221	280	java/net/MalformedURLException
    //   208	221	266	java/io/IOException
    //   208	221	252	java/lang/Exception
    //   208	221	236	finally
    //   221	225	900	finally
    //   225	229	904	finally
    //   229	233	908	finally
    //   429	434	820	finally
    //   443	448	820	finally
    //   457	465	820	finally
    //   474	485	820	finally
    //   494	504	820	finally
    //   509	514	517	finally
    //   523	528	531	finally
    //   537	542	912	finally
    //   567	572	820	finally
    //   581	586	820	finally
    //   595	603	820	finally
    //   612	623	820	finally
    //   632	642	820	finally
    //   647	652	655	finally
    //   661	666	669	finally
    //   675	680	916	finally
    //   705	710	820	finally
    //   719	724	820	finally
    //   733	741	820	finally
    //   750	761	820	finally
    //   770	780	820	finally
    //   785	790	793	finally
    //   799	804	807	finally
    //   813	818	920	finally
    //   851	856	859	finally
    //   865	869	872	finally
    //   877	881	924	finally
  }
  
  public static byte[] a(String paramString1, int paramInt, String paramString2) {
    StringBuffer stringBuffer = new StringBuffer();
    if (paramString1 != null)
      stringBuffer.append(paramString1); 
    stringBuffer.append(paramInt);
    stringBuffer.append(paramString2);
    stringBuffer.append("mMcShCsTr");
    byte[] arrayOfByte = stringBuffer.toString().substring(1, 9).getBytes();
    char[] arrayOfChar = new char[16];
    arrayOfChar[0] = '0';
    arrayOfChar[1] = '1';
    arrayOfChar[2] = '2';
    arrayOfChar[3] = '3';
    arrayOfChar[4] = '4';
    arrayOfChar[5] = '5';
    arrayOfChar[6] = '6';
    arrayOfChar[7] = '7';
    arrayOfChar[8] = '8';
    arrayOfChar[9] = '9';
    arrayOfChar[10] = 'a';
    arrayOfChar[11] = 'b';
    arrayOfChar[12] = 'c';
    arrayOfChar[13] = 'd';
    arrayOfChar[14] = 'e';
    arrayOfChar[15] = 'f';
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.update(arrayOfByte);
      arrayOfByte = messageDigest.digest();
      int i = arrayOfByte.length;
      char[] arrayOfChar1 = new char[i * 2];
      byte b = 0;
      paramInt = 0;
      while (b < i) {
        byte b1 = arrayOfByte[b];
        int j = paramInt + 1;
        arrayOfChar1[paramInt] = (char)arrayOfChar[b1 >>> 4 & 0xF];
        paramInt = j + 1;
        arrayOfChar1[j] = (char)arrayOfChar[b1 & 0xF];
        b++;
      } 
      String str = new String();
      this(arrayOfChar1);
    } catch (Exception exception) {
      exception = null;
    } 
    return exception.getBytes();
  }
  
  public static class a {
    public String a;
    
    public String b;
    
    public String c;
    
    public long d;
    
    public Bundle e;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\channel\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */