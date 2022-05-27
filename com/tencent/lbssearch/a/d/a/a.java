package com.tencent.lbssearch.a.d.a;

import com.tencent.lbssearch.a.d.e;
import com.tencent.lbssearch.a.d.h;
import com.tencent.lbssearch.a.d.j;
import com.tencent.lbssearch.a.d.n;
import com.tencent.lbssearch.a.d.o;
import com.tencent.lbssearch.a.d.q;
import com.tencent.lbssearch.a.d.r;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class a implements e {
  protected static final boolean a = r.b;
  
  private static int d = 3000;
  
  private static int e = 4096;
  
  protected final d b;
  
  protected final b c;
  
  public a(d paramd) {
    this(paramd, new b(e));
  }
  
  public a(d paramd, b paramb) {
    this.b = paramd;
    this.c = paramb;
  }
  
  protected static Map<String, String> a(Set<Map.Entry<String, List<String>>> paramSet) {
    TreeMap<String, Object> treeMap = new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
    for (Map.Entry<String, List<String>> entry : paramSet) {
      if (entry.getKey() != null)
        treeMap.put((String)entry.getKey(), ((List)entry.getValue()).get(0)); 
    } 
    return (Map)treeMap;
  }
  
  private void a(long paramLong, j<?> paramj, byte[] paramArrayOfbyte, int paramInt) {
    if (a || paramLong > d) {
      String str;
      if (paramArrayOfbyte != null) {
        Integer integer = Integer.valueOf(paramArrayOfbyte.length);
      } else {
        str = "null";
      } 
      r.a("HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]", new Object[] { paramj, Long.valueOf(paramLong), str, Integer.valueOf(paramInt), Integer.valueOf(paramj.q().b()) });
    } 
  }
  
  private static void a(String paramString, j<?> paramj, q paramq) throws q {
    n n = paramj.q();
    int i = paramj.p();
    try {
      n.a(paramq);
      paramj.a(String.format("%s-retry [timeout=%s]", new Object[] { paramString, Integer.valueOf(i) }));
      return;
    } catch (q q1) {
      paramj.a(String.format("%s-timeout-giveup [timeout=%s]", new Object[] { paramString, Integer.valueOf(i) }));
      throw q1;
    } 
  }
  
  private byte[] a(HttpURLConnection paramHttpURLConnection) throws IOException, o {
    f f = new f(this.c, paramHttpURLConnection.getContentLength());
    byte[] arrayOfByte1 = null;
    byte[] arrayOfByte2 = arrayOfByte1;
    try {
      InputStream inputStream = paramHttpURLConnection.getInputStream();
      if (inputStream != null) {
        arrayOfByte2 = arrayOfByte1;
        byte[] arrayOfByte = this.c.a(1024);
        while (true) {
          arrayOfByte2 = arrayOfByte;
          int i = inputStream.read(arrayOfByte);
          if (i != -1) {
            arrayOfByte2 = arrayOfByte;
            f.write(arrayOfByte, 0, i);
            continue;
          } 
          arrayOfByte2 = arrayOfByte;
          inputStream.close();
          arrayOfByte2 = arrayOfByte;
          arrayOfByte1 = f.toByteArray();
          return arrayOfByte1;
        } 
      } 
      arrayOfByte2 = arrayOfByte1;
      o o = new o();
      arrayOfByte2 = arrayOfByte1;
      this();
      arrayOfByte2 = arrayOfByte1;
      throw o;
    } finally {
      this.c.a(arrayOfByte2);
      f.close();
    } 
  }
  
  public h a(j<?> paramj) throws q {
    // Byte code:
    //   0: invokestatic elapsedRealtime : ()J
    //   3: lstore_2
    //   4: iconst_0
    //   5: istore #4
    //   7: aconst_null
    //   8: astore #5
    //   10: aconst_null
    //   11: astore #6
    //   13: iload #4
    //   15: iconst_3
    //   16: if_icmpge -> 591
    //   19: invokestatic emptyMap : ()Ljava/util/Map;
    //   22: astore #7
    //   24: new java/util/HashMap
    //   27: astore #8
    //   29: aload #8
    //   31: invokespecial <init> : ()V
    //   34: aload_0
    //   35: getfield b : Lcom/tencent/lbssearch/a/d/a/d;
    //   38: aload_1
    //   39: aload #8
    //   41: invokeinterface a : (Lcom/tencent/lbssearch/a/d/j;Ljava/util/Map;)Ljava/net/HttpURLConnection;
    //   46: astore #8
    //   48: aload #8
    //   50: invokevirtual getResponseCode : ()I
    //   53: istore #9
    //   55: aload #8
    //   57: invokevirtual getHeaderFields : ()Ljava/util/Map;
    //   60: invokeinterface entrySet : ()Ljava/util/Set;
    //   65: invokestatic a : (Ljava/util/Set;)Ljava/util/Map;
    //   68: astore #10
    //   70: iload #9
    //   72: sipush #304
    //   75: if_icmpne -> 110
    //   78: new com/tencent/lbssearch/a/d/h
    //   81: dup
    //   82: sipush #304
    //   85: aconst_null
    //   86: aload #10
    //   88: iconst_1
    //   89: invokestatic elapsedRealtime : ()J
    //   92: lload_2
    //   93: lsub
    //   94: invokespecial <init> : (I[BLjava/util/Map;ZJ)V
    //   97: areturn
    //   98: astore #7
    //   100: aconst_null
    //   101: astore #6
    //   103: aload #10
    //   105: astore #11
    //   107: goto -> 247
    //   110: aload #6
    //   112: astore #7
    //   114: aload #8
    //   116: ifnull -> 127
    //   119: aload_0
    //   120: aload #8
    //   122: invokespecial a : (Ljava/net/HttpURLConnection;)[B
    //   125: astore #7
    //   127: aload_0
    //   128: invokestatic elapsedRealtime : ()J
    //   131: lload_2
    //   132: lsub
    //   133: aload_1
    //   134: aload #7
    //   136: aload #8
    //   138: invokevirtual getResponseCode : ()I
    //   141: invokespecial a : (JLcom/tencent/lbssearch/a/d/j;[BI)V
    //   144: iload #9
    //   146: sipush #200
    //   149: if_icmplt -> 180
    //   152: iload #9
    //   154: sipush #299
    //   157: if_icmpgt -> 180
    //   160: new com/tencent/lbssearch/a/d/h
    //   163: dup
    //   164: iload #9
    //   166: aload #7
    //   168: aload #10
    //   170: iconst_0
    //   171: invokestatic elapsedRealtime : ()J
    //   174: lload_2
    //   175: lsub
    //   176: invokespecial <init> : (I[BLjava/util/Map;ZJ)V
    //   179: areturn
    //   180: new java/io/IOException
    //   183: astore #6
    //   185: aload #6
    //   187: invokespecial <init> : ()V
    //   190: aload #6
    //   192: athrow
    //   193: astore #6
    //   195: aload #7
    //   197: astore #11
    //   199: aload #6
    //   201: astore #7
    //   203: aload #11
    //   205: astore #6
    //   207: goto -> 103
    //   210: astore #6
    //   212: aconst_null
    //   213: astore #10
    //   215: aload #7
    //   217: astore #11
    //   219: aload #6
    //   221: astore #7
    //   223: aload #10
    //   225: astore #6
    //   227: goto -> 247
    //   230: astore #10
    //   232: aconst_null
    //   233: astore #6
    //   235: aload #7
    //   237: astore #11
    //   239: aload #5
    //   241: astore #8
    //   243: aload #10
    //   245: astore #7
    //   247: aload #8
    //   249: ifnull -> 486
    //   252: aload #8
    //   254: invokevirtual getResponseCode : ()I
    //   257: istore #9
    //   259: ldc 'Unexpected response code %d for %s'
    //   261: iconst_2
    //   262: anewarray java/lang/Object
    //   265: dup
    //   266: iconst_0
    //   267: iload #9
    //   269: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   272: aastore
    //   273: dup
    //   274: iconst_1
    //   275: aload_1
    //   276: invokevirtual c : ()Ljava/lang/String;
    //   279: aastore
    //   280: invokestatic b : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   283: aload #6
    //   285: ifnull -> 464
    //   288: new com/tencent/lbssearch/a/d/h
    //   291: astore #7
    //   293: aload #7
    //   295: iload #9
    //   297: aload #6
    //   299: aload #11
    //   301: iconst_0
    //   302: invokestatic elapsedRealtime : ()J
    //   305: lload_2
    //   306: lsub
    //   307: invokespecial <init> : (I[BLjava/util/Map;ZJ)V
    //   310: iload #9
    //   312: sipush #401
    //   315: if_icmpeq -> 440
    //   318: iload #9
    //   320: sipush #403
    //   323: if_icmpne -> 329
    //   326: goto -> 440
    //   329: iload #9
    //   331: sipush #400
    //   334: if_icmplt -> 363
    //   337: iload #9
    //   339: sipush #499
    //   342: if_icmple -> 348
    //   345: goto -> 363
    //   348: new com/tencent/lbssearch/a/d/b
    //   351: astore #6
    //   353: aload #6
    //   355: aload #7
    //   357: invokespecial <init> : (Lcom/tencent/lbssearch/a/d/h;)V
    //   360: aload #6
    //   362: athrow
    //   363: iload #9
    //   365: sipush #500
    //   368: if_icmplt -> 425
    //   371: iload #9
    //   373: sipush #599
    //   376: if_icmpgt -> 425
    //   379: aload_1
    //   380: invokevirtual n : ()Z
    //   383: ifeq -> 410
    //   386: new com/tencent/lbssearch/a/d/o
    //   389: astore #6
    //   391: aload #6
    //   393: aload #7
    //   395: invokespecial <init> : (Lcom/tencent/lbssearch/a/d/h;)V
    //   398: ldc_w 'server'
    //   401: aload_1
    //   402: aload #6
    //   404: invokestatic a : (Ljava/lang/String;Lcom/tencent/lbssearch/a/d/j;Lcom/tencent/lbssearch/a/d/q;)V
    //   407: goto -> 585
    //   410: new com/tencent/lbssearch/a/d/o
    //   413: astore #6
    //   415: aload #6
    //   417: aload #7
    //   419: invokespecial <init> : (Lcom/tencent/lbssearch/a/d/h;)V
    //   422: aload #6
    //   424: athrow
    //   425: new com/tencent/lbssearch/a/d/o
    //   428: astore #6
    //   430: aload #6
    //   432: aload #7
    //   434: invokespecial <init> : (Lcom/tencent/lbssearch/a/d/h;)V
    //   437: aload #6
    //   439: athrow
    //   440: new com/tencent/lbssearch/a/d/a
    //   443: astore #6
    //   445: aload #6
    //   447: aload #7
    //   449: invokespecial <init> : (Lcom/tencent/lbssearch/a/d/h;)V
    //   452: ldc_w 'auth'
    //   455: aload_1
    //   456: aload #6
    //   458: invokestatic a : (Ljava/lang/String;Lcom/tencent/lbssearch/a/d/j;Lcom/tencent/lbssearch/a/d/q;)V
    //   461: goto -> 585
    //   464: new com/tencent/lbssearch/a/d/g
    //   467: astore #7
    //   469: aload #7
    //   471: invokespecial <init> : ()V
    //   474: ldc_w 'network'
    //   477: aload_1
    //   478: aload #7
    //   480: invokestatic a : (Ljava/lang/String;Lcom/tencent/lbssearch/a/d/j;Lcom/tencent/lbssearch/a/d/q;)V
    //   483: goto -> 585
    //   486: new com/tencent/lbssearch/a/d/i
    //   489: astore #6
    //   491: aload #6
    //   493: aload #7
    //   495: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   498: aload #6
    //   500: athrow
    //   501: astore #7
    //   503: ldc_w '%s:Can not get HttpURLConnection ResponsCode.'
    //   506: iconst_1
    //   507: anewarray java/lang/Object
    //   510: dup
    //   511: iconst_0
    //   512: aload #7
    //   514: invokevirtual getMessage : ()Ljava/lang/String;
    //   517: aastore
    //   518: invokestatic b : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   521: goto -> 585
    //   524: astore #6
    //   526: new java/lang/StringBuilder
    //   529: dup
    //   530: invokespecial <init> : ()V
    //   533: astore #7
    //   535: aload #7
    //   537: ldc_w 'Bad URL '
    //   540: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   543: pop
    //   544: aload #7
    //   546: aload_1
    //   547: invokevirtual c : ()Ljava/lang/String;
    //   550: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   553: pop
    //   554: new java/lang/RuntimeException
    //   557: dup
    //   558: aload #7
    //   560: invokevirtual toString : ()Ljava/lang/String;
    //   563: aload #6
    //   565: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   568: athrow
    //   569: astore #7
    //   571: ldc_w 'socket'
    //   574: aload_1
    //   575: new com/tencent/lbssearch/a/d/p
    //   578: dup
    //   579: invokespecial <init> : ()V
    //   582: invokestatic a : (Ljava/lang/String;Lcom/tencent/lbssearch/a/d/j;Lcom/tencent/lbssearch/a/d/q;)V
    //   585: iinc #4, 1
    //   588: goto -> 7
    //   591: aconst_null
    //   592: areturn
    // Exception table:
    //   from	to	target	type
    //   24	48	569	java/net/SocketTimeoutException
    //   24	48	524	java/net/MalformedURLException
    //   24	48	230	java/io/IOException
    //   48	70	569	java/net/SocketTimeoutException
    //   48	70	524	java/net/MalformedURLException
    //   48	70	210	java/io/IOException
    //   78	98	569	java/net/SocketTimeoutException
    //   78	98	524	java/net/MalformedURLException
    //   78	98	98	java/io/IOException
    //   119	127	569	java/net/SocketTimeoutException
    //   119	127	524	java/net/MalformedURLException
    //   119	127	98	java/io/IOException
    //   127	144	569	java/net/SocketTimeoutException
    //   127	144	524	java/net/MalformedURLException
    //   127	144	193	java/io/IOException
    //   160	180	569	java/net/SocketTimeoutException
    //   160	180	524	java/net/MalformedURLException
    //   160	180	193	java/io/IOException
    //   180	193	569	java/net/SocketTimeoutException
    //   180	193	524	java/net/MalformedURLException
    //   180	193	193	java/io/IOException
    //   252	283	501	java/io/IOException
    //   288	310	501	java/io/IOException
    //   348	363	501	java/io/IOException
    //   379	407	501	java/io/IOException
    //   410	425	501	java/io/IOException
    //   425	440	501	java/io/IOException
    //   440	461	501	java/io/IOException
    //   464	483	501	java/io/IOException
    //   486	501	501	java/io/IOException
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\d\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */