package com.tencent.mm.opensdk.diffdev.a;

import android.os.AsyncTask;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;

class c extends AsyncTask<Void, Void, c.a> {
  private String a;
  
  private String b;
  
  private OAuthListener c;
  
  private int d;
  
  public c(String paramString, OAuthListener paramOAuthListener) {
    this.a = paramString;
    this.c = paramOAuthListener;
    this.b = String.format("https://long.open.weixin.qq.com/connect/l/qrconnect?f=json&uuid=%s", new Object[] { paramString });
  }
  
  protected Object doInBackground(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: checkcast [Ljava/lang/Void;
    //   4: astore_1
    //   5: invokestatic currentThread : ()Ljava/lang/Thread;
    //   8: ldc 'OpenSdkNoopingTask'
    //   10: invokevirtual setName : (Ljava/lang/String;)V
    //   13: aload_0
    //   14: getfield a : Ljava/lang/String;
    //   17: astore_1
    //   18: aload_1
    //   19: ifnull -> 642
    //   22: aload_1
    //   23: invokevirtual length : ()I
    //   26: ifne -> 32
    //   29: goto -> 642
    //   32: new java/lang/StringBuilder
    //   35: dup
    //   36: invokespecial <init> : ()V
    //   39: astore_1
    //   40: aload_1
    //   41: ldc 'doInBackground start '
    //   43: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: pop
    //   47: aload_1
    //   48: aload_0
    //   49: invokevirtual isCancelled : ()Z
    //   52: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   55: pop
    //   56: ldc 'MicroMsg.SDK.NoopingTask'
    //   58: aload_1
    //   59: invokevirtual toString : ()Ljava/lang/String;
    //   62: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)V
    //   65: aload_0
    //   66: invokevirtual isCancelled : ()Z
    //   69: ifne -> 620
    //   72: new java/lang/StringBuilder
    //   75: dup
    //   76: invokespecial <init> : ()V
    //   79: astore_2
    //   80: aload_2
    //   81: aload_0
    //   82: getfield b : Ljava/lang/String;
    //   85: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: pop
    //   89: aload_0
    //   90: getfield d : I
    //   93: ifne -> 102
    //   96: ldc ''
    //   98: astore_1
    //   99: goto -> 131
    //   102: new java/lang/StringBuilder
    //   105: dup
    //   106: invokespecial <init> : ()V
    //   109: astore_1
    //   110: aload_1
    //   111: ldc '&last='
    //   113: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: pop
    //   117: aload_1
    //   118: aload_0
    //   119: getfield d : I
    //   122: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   125: pop
    //   126: aload_1
    //   127: invokevirtual toString : ()Ljava/lang/String;
    //   130: astore_1
    //   131: aload_2
    //   132: aload_1
    //   133: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   136: pop
    //   137: aload_2
    //   138: invokevirtual toString : ()Ljava/lang/String;
    //   141: astore_3
    //   142: invokestatic currentTimeMillis : ()J
    //   145: lstore #4
    //   147: aload_3
    //   148: ldc 60000
    //   150: invokestatic a : (Ljava/lang/String;I)[B
    //   153: astore #6
    //   155: invokestatic currentTimeMillis : ()J
    //   158: lstore #7
    //   160: new com/tencent/mm/opensdk/diffdev/a/c$a
    //   163: dup
    //   164: invokespecial <init> : ()V
    //   167: astore_2
    //   168: ldc 'MicroMsg.SDK.NoopingResult'
    //   170: ldc 'star parse NoopingResult'
    //   172: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)V
    //   175: aload #6
    //   177: ifnull -> 413
    //   180: aload #6
    //   182: arraylength
    //   183: ifne -> 189
    //   186: goto -> 413
    //   189: new java/lang/String
    //   192: astore_1
    //   193: aload_1
    //   194: aload #6
    //   196: ldc 'utf-8'
    //   198: invokespecial <init> : ([BLjava/lang/String;)V
    //   201: new org/json/JSONObject
    //   204: astore #6
    //   206: aload #6
    //   208: aload_1
    //   209: invokespecial <init> : (Ljava/lang/String;)V
    //   212: aload #6
    //   214: ldc 'wx_errcode'
    //   216: invokevirtual getInt : (Ljava/lang/String;)I
    //   219: istore #9
    //   221: aload_2
    //   222: iload #9
    //   224: putfield c : I
    //   227: ldc 'MicroMsg.SDK.NoopingResult'
    //   229: ldc 'nooping uuidStatusCode = %d'
    //   231: iconst_1
    //   232: anewarray java/lang/Object
    //   235: dup
    //   236: iconst_0
    //   237: iload #9
    //   239: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   242: aastore
    //   243: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   246: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)V
    //   249: aload_2
    //   250: getfield c : I
    //   253: istore #9
    //   255: iload #9
    //   257: sipush #408
    //   260: if_icmpeq -> 349
    //   263: iload #9
    //   265: sipush #500
    //   268: if_icmpeq -> 342
    //   271: iload #9
    //   273: tableswitch default -> 304, 402 -> 335, 403 -> 328, 404 -> 349, 405 -> 307
    //   304: goto -> 342
    //   307: aload_2
    //   308: getstatic com/tencent/mm/opensdk/diffdev/OAuthErrCode.WechatAuth_Err_OK : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   311: putfield a : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   314: aload_2
    //   315: aload #6
    //   317: ldc 'wx_code'
    //   319: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   322: putfield b : Ljava/lang/String;
    //   325: goto -> 429
    //   328: getstatic com/tencent/mm/opensdk/diffdev/OAuthErrCode.WechatAuth_Err_Cancel : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   331: astore_1
    //   332: goto -> 353
    //   335: getstatic com/tencent/mm/opensdk/diffdev/OAuthErrCode.WechatAuth_Err_Timeout : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   338: astore_1
    //   339: goto -> 353
    //   342: getstatic com/tencent/mm/opensdk/diffdev/OAuthErrCode.WechatAuth_Err_NormalErr : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   345: astore_1
    //   346: goto -> 353
    //   349: getstatic com/tencent/mm/opensdk/diffdev/OAuthErrCode.WechatAuth_Err_OK : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   352: astore_1
    //   353: aload_2
    //   354: aload_1
    //   355: putfield a : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   358: goto -> 429
    //   361: astore_1
    //   362: ldc 'parse json fail, ex = %s'
    //   364: iconst_1
    //   365: anewarray java/lang/Object
    //   368: dup
    //   369: iconst_0
    //   370: aload_1
    //   371: invokevirtual getMessage : ()Ljava/lang/String;
    //   374: aastore
    //   375: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   378: astore_1
    //   379: goto -> 400
    //   382: astore_1
    //   383: ldc 'parse fail, build String fail, ex = %s'
    //   385: iconst_1
    //   386: anewarray java/lang/Object
    //   389: dup
    //   390: iconst_0
    //   391: aload_1
    //   392: invokevirtual getMessage : ()Ljava/lang/String;
    //   395: aastore
    //   396: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   399: astore_1
    //   400: ldc 'MicroMsg.SDK.NoopingResult'
    //   402: aload_1
    //   403: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   406: getstatic com/tencent/mm/opensdk/diffdev/OAuthErrCode.WechatAuth_Err_NormalErr : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   409: astore_1
    //   410: goto -> 424
    //   413: ldc 'MicroMsg.SDK.NoopingResult'
    //   415: ldc 'parse fail, buf is null'
    //   417: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   420: getstatic com/tencent/mm/opensdk/diffdev/OAuthErrCode.WechatAuth_Err_NetworkErr : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   423: astore_1
    //   424: aload_2
    //   425: aload_1
    //   426: putfield a : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   429: ldc 'MicroMsg.SDK.NoopingTask'
    //   431: ldc 'nooping, url = %s, errCode = %s, uuidStatusCode = %d, time consumed = %d(ms)'
    //   433: iconst_4
    //   434: anewarray java/lang/Object
    //   437: dup
    //   438: iconst_0
    //   439: aload_3
    //   440: aastore
    //   441: dup
    //   442: iconst_1
    //   443: aload_2
    //   444: getfield a : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   447: invokevirtual toString : ()Ljava/lang/String;
    //   450: aastore
    //   451: dup
    //   452: iconst_2
    //   453: aload_2
    //   454: getfield c : I
    //   457: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   460: aastore
    //   461: dup
    //   462: iconst_3
    //   463: lload #7
    //   465: lload #4
    //   467: lsub
    //   468: invokestatic valueOf : (J)Ljava/lang/Long;
    //   471: aastore
    //   472: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   475: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)V
    //   478: aload_2
    //   479: getfield a : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   482: astore_1
    //   483: aload_1
    //   484: getstatic com/tencent/mm/opensdk/diffdev/OAuthErrCode.WechatAuth_Err_OK : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   487: if_acmpne -> 584
    //   490: aload_2
    //   491: getfield c : I
    //   494: istore #9
    //   496: aload_0
    //   497: iload #9
    //   499: putfield d : I
    //   502: iload #9
    //   504: getstatic com/tencent/mm/opensdk/diffdev/a/d.d : Lcom/tencent/mm/opensdk/diffdev/a/d;
    //   507: invokevirtual a : ()I
    //   510: if_icmpne -> 525
    //   513: aload_0
    //   514: getfield c : Lcom/tencent/mm/opensdk/diffdev/OAuthListener;
    //   517: invokeinterface onQrcodeScanned : ()V
    //   522: goto -> 65
    //   525: aload_2
    //   526: getfield c : I
    //   529: getstatic com/tencent/mm/opensdk/diffdev/a/d.f : Lcom/tencent/mm/opensdk/diffdev/a/d;
    //   532: invokevirtual a : ()I
    //   535: if_icmpne -> 541
    //   538: goto -> 65
    //   541: aload_2
    //   542: getfield c : I
    //   545: getstatic com/tencent/mm/opensdk/diffdev/a/d.e : Lcom/tencent/mm/opensdk/diffdev/a/d;
    //   548: invokevirtual a : ()I
    //   551: if_icmpne -> 65
    //   554: aload_2
    //   555: getfield b : Ljava/lang/String;
    //   558: astore_3
    //   559: aload_3
    //   560: ifnull -> 572
    //   563: aload_2
    //   564: astore_1
    //   565: aload_3
    //   566: invokevirtual length : ()I
    //   569: ifne -> 666
    //   572: ldc 'MicroMsg.SDK.NoopingTask'
    //   574: ldc 'nooping fail, confirm with an empty code!!!'
    //   576: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   579: aload_2
    //   580: astore_1
    //   581: goto -> 657
    //   584: ldc 'MicroMsg.SDK.NoopingTask'
    //   586: ldc 'nooping fail, errCode = %s, uuidStatusCode = %d'
    //   588: iconst_2
    //   589: anewarray java/lang/Object
    //   592: dup
    //   593: iconst_0
    //   594: aload_1
    //   595: invokevirtual toString : ()Ljava/lang/String;
    //   598: aastore
    //   599: dup
    //   600: iconst_1
    //   601: aload_2
    //   602: getfield c : I
    //   605: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   608: aastore
    //   609: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   612: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   615: aload_2
    //   616: astore_1
    //   617: goto -> 666
    //   620: ldc 'MicroMsg.SDK.NoopingTask'
    //   622: ldc 'IDiffDevOAuth.stopAuth / detach invoked'
    //   624: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)V
    //   627: new com/tencent/mm/opensdk/diffdev/a/c$a
    //   630: dup
    //   631: invokespecial <init> : ()V
    //   634: astore_1
    //   635: getstatic com/tencent/mm/opensdk/diffdev/OAuthErrCode.WechatAuth_Err_Auth_Stopped : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   638: astore_2
    //   639: goto -> 661
    //   642: ldc 'MicroMsg.SDK.NoopingTask'
    //   644: ldc 'run fail, uuid is null'
    //   646: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   649: new com/tencent/mm/opensdk/diffdev/a/c$a
    //   652: dup
    //   653: invokespecial <init> : ()V
    //   656: astore_1
    //   657: getstatic com/tencent/mm/opensdk/diffdev/OAuthErrCode.WechatAuth_Err_NormalErr : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   660: astore_2
    //   661: aload_1
    //   662: aload_2
    //   663: putfield a : Lcom/tencent/mm/opensdk/diffdev/OAuthErrCode;
    //   666: aload_1
    //   667: areturn
    // Exception table:
    //   from	to	target	type
    //   189	201	382	java/lang/Exception
    //   201	227	361	java/lang/Exception
    //   227	255	361	java/lang/Exception
    //   307	325	361	java/lang/Exception
    //   328	332	361	java/lang/Exception
    //   335	339	361	java/lang/Exception
    //   342	346	361	java/lang/Exception
    //   349	353	361	java/lang/Exception
    //   353	358	361	java/lang/Exception
  }
  
  protected void onPostExecute(Object paramObject) {
    paramObject = paramObject;
    this.c.onAuthFinish(((a)paramObject).a, ((a)paramObject).b);
  }
  
  static class a {
    public OAuthErrCode a;
    
    public String b;
    
    public int c;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\diffdev\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */