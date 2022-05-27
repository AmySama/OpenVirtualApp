package android.support.v4.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.BaseColumns;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.TypefaceCompat;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v4.util.LruCache;
import android.support.v4.util.Preconditions;
import android.support.v4.util.SimpleArrayMap;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class FontsContractCompat {
  private static final int BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS = 10000;
  
  public static final String PARCEL_FONT_RESULTS = "font_results";
  
  static final int RESULT_CODE_PROVIDER_NOT_FOUND = -1;
  
  static final int RESULT_CODE_WRONG_CERTIFICATES = -2;
  
  private static final String TAG = "FontsContractCompat";
  
  private static final SelfDestructiveThread sBackgroundThread;
  
  private static final Comparator<byte[]> sByteArrayComparator;
  
  private static final Object sLock;
  
  private static final SimpleArrayMap<String, ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>>> sPendingReplies;
  
  private static final LruCache<String, Typeface> sTypefaceCache = new LruCache(16);
  
  static {
    sBackgroundThread = new SelfDestructiveThread("fonts", 10, 10000);
    sLock = new Object();
    sPendingReplies = new SimpleArrayMap();
    sByteArrayComparator = new Comparator<byte[]>() {
        public int compare(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2) {
          if (param1ArrayOfbyte1.length != param1ArrayOfbyte2.length) {
            int i = param1ArrayOfbyte1.length;
            int j = param1ArrayOfbyte2.length;
            return i - j;
          } 
          for (byte b = 0; b < param1ArrayOfbyte1.length; b++) {
            if (param1ArrayOfbyte1[b] != param1ArrayOfbyte2[b]) {
              byte b1 = param1ArrayOfbyte1[b];
              b = param1ArrayOfbyte2[b];
              return b1 - b;
            } 
          } 
          return 0;
        }
      };
  }
  
  public static Typeface buildTypeface(Context paramContext, CancellationSignal paramCancellationSignal, FontInfo[] paramArrayOfFontInfo) {
    return TypefaceCompat.createFromFontInfo(paramContext, paramCancellationSignal, paramArrayOfFontInfo, 0);
  }
  
  private static List<byte[]> convertToByteArrayList(Signature[] paramArrayOfSignature) {
    ArrayList<byte[]> arrayList = new ArrayList();
    for (byte b = 0; b < paramArrayOfSignature.length; b++)
      arrayList.add(paramArrayOfSignature[b].toByteArray()); 
    return (List<byte[]>)arrayList;
  }
  
  private static boolean equalsByteArrayList(List<byte[]> paramList1, List<byte[]> paramList2) {
    if (paramList1.size() != paramList2.size())
      return false; 
    for (byte b = 0; b < paramList1.size(); b++) {
      if (!Arrays.equals(paramList1.get(b), paramList2.get(b)))
        return false; 
    } 
    return true;
  }
  
  public static FontFamilyResult fetchFonts(Context paramContext, CancellationSignal paramCancellationSignal, FontRequest paramFontRequest) throws PackageManager.NameNotFoundException {
    ProviderInfo providerInfo = getProvider(paramContext.getPackageManager(), paramFontRequest, paramContext.getResources());
    return (providerInfo == null) ? new FontFamilyResult(1, null) : new FontFamilyResult(0, getFontFromProvider(paramContext, paramFontRequest, providerInfo.authority, paramCancellationSignal));
  }
  
  private static List<List<byte[]>> getCertificates(FontRequest paramFontRequest, Resources paramResources) {
    return (paramFontRequest.getCertificates() != null) ? paramFontRequest.getCertificates() : FontResourcesParserCompat.readCerts(paramResources, paramFontRequest.getCertificatesArrayResId());
  }
  
  static FontInfo[] getFontFromProvider(Context paramContext, FontRequest paramFontRequest, String paramString, CancellationSignal paramCancellationSignal) {
    // Byte code:
    //   0: new java/util/ArrayList
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #4
    //   9: new android/net/Uri$Builder
    //   12: dup
    //   13: invokespecial <init> : ()V
    //   16: ldc 'content'
    //   18: invokevirtual scheme : (Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   21: aload_2
    //   22: invokevirtual authority : (Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   25: invokevirtual build : ()Landroid/net/Uri;
    //   28: astore #5
    //   30: new android/net/Uri$Builder
    //   33: dup
    //   34: invokespecial <init> : ()V
    //   37: ldc 'content'
    //   39: invokevirtual scheme : (Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   42: aload_2
    //   43: invokevirtual authority : (Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   46: ldc 'file'
    //   48: invokevirtual appendPath : (Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   51: invokevirtual build : ()Landroid/net/Uri;
    //   54: astore #6
    //   56: aconst_null
    //   57: astore #7
    //   59: aload #7
    //   61: astore_2
    //   62: getstatic android/os/Build$VERSION.SDK_INT : I
    //   65: bipush #16
    //   67: if_icmple -> 159
    //   70: aload #7
    //   72: astore_2
    //   73: aload_0
    //   74: invokevirtual getContentResolver : ()Landroid/content/ContentResolver;
    //   77: astore_0
    //   78: aload #7
    //   80: astore_2
    //   81: aload_1
    //   82: invokevirtual getQuery : ()Ljava/lang/String;
    //   85: astore_1
    //   86: aload #7
    //   88: astore_2
    //   89: aload_0
    //   90: aload #5
    //   92: bipush #7
    //   94: anewarray java/lang/String
    //   97: dup
    //   98: iconst_0
    //   99: ldc '_id'
    //   101: aastore
    //   102: dup
    //   103: iconst_1
    //   104: ldc_w 'file_id'
    //   107: aastore
    //   108: dup
    //   109: iconst_2
    //   110: ldc_w 'font_ttc_index'
    //   113: aastore
    //   114: dup
    //   115: iconst_3
    //   116: ldc_w 'font_variation_settings'
    //   119: aastore
    //   120: dup
    //   121: iconst_4
    //   122: ldc_w 'font_weight'
    //   125: aastore
    //   126: dup
    //   127: iconst_5
    //   128: ldc_w 'font_italic'
    //   131: aastore
    //   132: dup
    //   133: bipush #6
    //   135: ldc_w 'result_code'
    //   138: aastore
    //   139: ldc_w 'query = ?'
    //   142: iconst_1
    //   143: anewarray java/lang/String
    //   146: dup
    //   147: iconst_0
    //   148: aload_1
    //   149: aastore
    //   150: aconst_null
    //   151: aload_3
    //   152: invokevirtual query : (Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   155: astore_0
    //   156: goto -> 244
    //   159: aload #7
    //   161: astore_2
    //   162: aload_0
    //   163: invokevirtual getContentResolver : ()Landroid/content/ContentResolver;
    //   166: astore_0
    //   167: aload #7
    //   169: astore_2
    //   170: aload_1
    //   171: invokevirtual getQuery : ()Ljava/lang/String;
    //   174: astore_1
    //   175: aload #7
    //   177: astore_2
    //   178: aload_0
    //   179: aload #5
    //   181: bipush #7
    //   183: anewarray java/lang/String
    //   186: dup
    //   187: iconst_0
    //   188: ldc '_id'
    //   190: aastore
    //   191: dup
    //   192: iconst_1
    //   193: ldc_w 'file_id'
    //   196: aastore
    //   197: dup
    //   198: iconst_2
    //   199: ldc_w 'font_ttc_index'
    //   202: aastore
    //   203: dup
    //   204: iconst_3
    //   205: ldc_w 'font_variation_settings'
    //   208: aastore
    //   209: dup
    //   210: iconst_4
    //   211: ldc_w 'font_weight'
    //   214: aastore
    //   215: dup
    //   216: iconst_5
    //   217: ldc_w 'font_italic'
    //   220: aastore
    //   221: dup
    //   222: bipush #6
    //   224: ldc_w 'result_code'
    //   227: aastore
    //   228: ldc_w 'query = ?'
    //   231: iconst_1
    //   232: anewarray java/lang/String
    //   235: dup
    //   236: iconst_0
    //   237: aload_1
    //   238: aastore
    //   239: aconst_null
    //   240: invokevirtual query : (Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   243: astore_0
    //   244: aload #4
    //   246: astore_1
    //   247: aload_0
    //   248: ifnull -> 546
    //   251: aload #4
    //   253: astore_1
    //   254: aload_0
    //   255: astore_2
    //   256: aload_0
    //   257: invokeinterface getCount : ()I
    //   262: ifle -> 546
    //   265: aload_0
    //   266: astore_2
    //   267: aload_0
    //   268: ldc_w 'result_code'
    //   271: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   276: istore #8
    //   278: aload_0
    //   279: astore_2
    //   280: new java/util/ArrayList
    //   283: astore_3
    //   284: aload_0
    //   285: astore_2
    //   286: aload_3
    //   287: invokespecial <init> : ()V
    //   290: aload_0
    //   291: astore_2
    //   292: aload_0
    //   293: ldc '_id'
    //   295: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   300: istore #9
    //   302: aload_0
    //   303: astore_2
    //   304: aload_0
    //   305: ldc_w 'file_id'
    //   308: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   313: istore #10
    //   315: aload_0
    //   316: astore_2
    //   317: aload_0
    //   318: ldc_w 'font_ttc_index'
    //   321: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   326: istore #11
    //   328: aload_0
    //   329: astore_2
    //   330: aload_0
    //   331: ldc_w 'font_weight'
    //   334: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   339: istore #12
    //   341: aload_0
    //   342: astore_2
    //   343: aload_0
    //   344: ldc_w 'font_italic'
    //   347: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   352: istore #13
    //   354: aload_0
    //   355: astore_2
    //   356: aload_0
    //   357: invokeinterface moveToNext : ()Z
    //   362: ifeq -> 544
    //   365: iload #8
    //   367: iconst_m1
    //   368: if_icmpeq -> 386
    //   371: aload_0
    //   372: astore_2
    //   373: aload_0
    //   374: iload #8
    //   376: invokeinterface getInt : (I)I
    //   381: istore #14
    //   383: goto -> 389
    //   386: iconst_0
    //   387: istore #14
    //   389: iload #11
    //   391: iconst_m1
    //   392: if_icmpeq -> 410
    //   395: aload_0
    //   396: astore_2
    //   397: aload_0
    //   398: iload #11
    //   400: invokeinterface getInt : (I)I
    //   405: istore #15
    //   407: goto -> 413
    //   410: iconst_0
    //   411: istore #15
    //   413: iload #10
    //   415: iconst_m1
    //   416: if_icmpne -> 438
    //   419: aload_0
    //   420: astore_2
    //   421: aload #5
    //   423: aload_0
    //   424: iload #9
    //   426: invokeinterface getLong : (I)J
    //   431: invokestatic withAppendedId : (Landroid/net/Uri;J)Landroid/net/Uri;
    //   434: astore_1
    //   435: goto -> 454
    //   438: aload_0
    //   439: astore_2
    //   440: aload #6
    //   442: aload_0
    //   443: iload #10
    //   445: invokeinterface getLong : (I)J
    //   450: invokestatic withAppendedId : (Landroid/net/Uri;J)Landroid/net/Uri;
    //   453: astore_1
    //   454: iload #12
    //   456: iconst_m1
    //   457: if_icmpeq -> 475
    //   460: aload_0
    //   461: astore_2
    //   462: aload_0
    //   463: iload #12
    //   465: invokeinterface getInt : (I)I
    //   470: istore #16
    //   472: goto -> 480
    //   475: sipush #400
    //   478: istore #16
    //   480: iload #13
    //   482: iconst_m1
    //   483: if_icmpeq -> 506
    //   486: aload_0
    //   487: astore_2
    //   488: aload_0
    //   489: iload #13
    //   491: invokeinterface getInt : (I)I
    //   496: iconst_1
    //   497: if_icmpne -> 506
    //   500: iconst_1
    //   501: istore #17
    //   503: goto -> 509
    //   506: iconst_0
    //   507: istore #17
    //   509: aload_0
    //   510: astore_2
    //   511: new android/support/v4/provider/FontsContractCompat$FontInfo
    //   514: astore #4
    //   516: aload_0
    //   517: astore_2
    //   518: aload #4
    //   520: aload_1
    //   521: iload #15
    //   523: iload #16
    //   525: iload #17
    //   527: iload #14
    //   529: invokespecial <init> : (Landroid/net/Uri;IIZI)V
    //   532: aload_0
    //   533: astore_2
    //   534: aload_3
    //   535: aload #4
    //   537: invokevirtual add : (Ljava/lang/Object;)Z
    //   540: pop
    //   541: goto -> 354
    //   544: aload_3
    //   545: astore_1
    //   546: aload_0
    //   547: ifnull -> 556
    //   550: aload_0
    //   551: invokeinterface close : ()V
    //   556: aload_1
    //   557: iconst_0
    //   558: anewarray android/support/v4/provider/FontsContractCompat$FontInfo
    //   561: invokevirtual toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   564: checkcast [Landroid/support/v4/provider/FontsContractCompat$FontInfo;
    //   567: areturn
    //   568: astore_0
    //   569: aload_2
    //   570: ifnull -> 579
    //   573: aload_2
    //   574: invokeinterface close : ()V
    //   579: aload_0
    //   580: athrow
    // Exception table:
    //   from	to	target	type
    //   62	70	568	finally
    //   73	78	568	finally
    //   81	86	568	finally
    //   89	156	568	finally
    //   162	167	568	finally
    //   170	175	568	finally
    //   178	244	568	finally
    //   256	265	568	finally
    //   267	278	568	finally
    //   280	284	568	finally
    //   286	290	568	finally
    //   292	302	568	finally
    //   304	315	568	finally
    //   317	328	568	finally
    //   330	341	568	finally
    //   343	354	568	finally
    //   356	365	568	finally
    //   373	383	568	finally
    //   397	407	568	finally
    //   421	435	568	finally
    //   440	454	568	finally
    //   462	472	568	finally
    //   488	500	568	finally
    //   511	516	568	finally
    //   518	532	568	finally
    //   534	541	568	finally
  }
  
  private static TypefaceResult getFontInternal(Context paramContext, FontRequest paramFontRequest, int paramInt) {
    try {
      FontFamilyResult fontFamilyResult = fetchFonts(paramContext, null, paramFontRequest);
      int i = fontFamilyResult.getStatusCode();
      byte b = -3;
      if (i == 0) {
        Typeface typeface = TypefaceCompat.createFromFontInfo(paramContext, null, fontFamilyResult.getFonts(), paramInt);
        if (typeface != null)
          b = 0; 
        return new TypefaceResult(typeface, b);
      } 
      if (fontFamilyResult.getStatusCode() == 1)
        b = -2; 
      return new TypefaceResult(null, b);
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      return new TypefaceResult(null, -1);
    } 
  }
  
  public static Typeface getFontSync(Context paramContext, final FontRequest request, final ResourcesCompat.FontCallback fontCallback, final Handler handler, boolean paramBoolean, int paramInt1, final int style) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(request.getIdentifier());
    stringBuilder.append("-");
    stringBuilder.append(style);
    final String id = stringBuilder.toString();
    Typeface typeface = (Typeface)sTypefaceCache.get(str);
    if (typeface != null) {
      if (fontCallback != null)
        fontCallback.onFontRetrieved(typeface); 
      return typeface;
    } 
    if (paramBoolean && paramInt1 == -1) {
      typefaceResult = getFontInternal(paramContext, request, style);
      if (fontCallback != null)
        if (typefaceResult.mResult == 0) {
          fontCallback.callbackSuccessAsync(typefaceResult.mTypeface, handler);
        } else {
          fontCallback.callbackFailAsync(typefaceResult.mResult, handler);
        }  
      return typefaceResult.mTypeface;
    } 
    Callable<TypefaceResult> callable = new Callable<TypefaceResult>() {
        public FontsContractCompat.TypefaceResult call() throws Exception {
          FontsContractCompat.TypefaceResult typefaceResult = FontsContractCompat.getFontInternal(context, request, style);
          if (typefaceResult.mTypeface != null)
            FontsContractCompat.sTypefaceCache.put(id, typefaceResult.mTypeface); 
          return typefaceResult;
        }
      };
    final TypefaceResult context = null;
    if (paramBoolean) {
      Typeface typeface1;
      try {
        Typeface typeface2 = ((TypefaceResult)sBackgroundThread.postAndWait((Callable)callable, paramInt1)).mTypeface;
        typeface1 = typeface2;
      } catch (InterruptedException interruptedException) {}
      return typeface1;
    } 
    if (fontCallback == null) {
      typefaceResult = null;
    } else {
      null = new SelfDestructiveThread.ReplyCallback<TypefaceResult>() {
          public void onReply(FontsContractCompat.TypefaceResult param1TypefaceResult) {
            if (param1TypefaceResult == null) {
              fontCallback.callbackFailAsync(1, handler);
            } else if (param1TypefaceResult.mResult == 0) {
              fontCallback.callbackSuccessAsync(param1TypefaceResult.mTypeface, handler);
            } else {
              fontCallback.callbackFailAsync(param1TypefaceResult.mResult, handler);
            } 
          }
        };
    } 
    synchronized (sLock) {
      if (sPendingReplies.containsKey(str)) {
        if (null != null)
          ((ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>>)sPendingReplies.get(str)).add(null); 
        return null;
      } 
      if (null != null) {
        ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>> arrayList = new ArrayList();
        this();
        arrayList.add(null);
        sPendingReplies.put(str, arrayList);
      } 
      sBackgroundThread.postAndReply((Callable<?>)interruptedException, new SelfDestructiveThread.ReplyCallback<TypefaceResult>() {
            public void onReply(FontsContractCompat.TypefaceResult param1TypefaceResult) {
              synchronized (FontsContractCompat.sLock) {
                ArrayList<SelfDestructiveThread.ReplyCallback<FontsContractCompat.TypefaceResult>> arrayList = (ArrayList)FontsContractCompat.sPendingReplies.get(id);
                if (arrayList == null)
                  return; 
                FontsContractCompat.sPendingReplies.remove(id);
                for (byte b = 0; b < arrayList.size(); b++)
                  ((SelfDestructiveThread.ReplyCallback<FontsContractCompat.TypefaceResult>)arrayList.get(b)).onReply(param1TypefaceResult); 
                return;
              } 
            }
          });
      return null;
    } 
  }
  
  public static ProviderInfo getProvider(PackageManager paramPackageManager, FontRequest paramFontRequest, Resources paramResources) throws PackageManager.NameNotFoundException {
    String str = paramFontRequest.getProviderAuthority();
    byte b = 0;
    ProviderInfo providerInfo = paramPackageManager.resolveContentProvider(str, 0);
    if (providerInfo != null) {
      ArrayList<byte> arrayList;
      if (providerInfo.packageName.equals(paramFontRequest.getProviderPackage())) {
        List<byte[]> list = convertToByteArrayList((paramPackageManager.getPackageInfo(providerInfo.packageName, 64)).signatures);
        Collections.sort((List)list, (Comparator)sByteArrayComparator);
        List<List<byte[]>> list1 = getCertificates(paramFontRequest, paramResources);
        while (b < list1.size()) {
          arrayList = new ArrayList(list1.get(b));
          Collections.sort(arrayList, (Comparator)sByteArrayComparator);
          if (equalsByteArrayList(list, (List)arrayList))
            return providerInfo; 
          b++;
        } 
        return null;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Found content provider ");
      stringBuilder1.append(str);
      stringBuilder1.append(", but package was not ");
      stringBuilder1.append(arrayList.getProviderPackage());
      throw new PackageManager.NameNotFoundException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No package found for authority: ");
    stringBuilder.append(str);
    throw new PackageManager.NameNotFoundException(stringBuilder.toString());
  }
  
  public static Map<Uri, ByteBuffer> prepareFontData(Context paramContext, FontInfo[] paramArrayOfFontInfo, CancellationSignal paramCancellationSignal) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    int i = paramArrayOfFontInfo.length;
    for (byte b = 0; b < i; b++) {
      FontInfo fontInfo = paramArrayOfFontInfo[b];
      if (fontInfo.getResultCode() == 0) {
        Uri uri = fontInfo.getUri();
        if (!hashMap.containsKey(uri))
          hashMap.put(uri, TypefaceCompatUtil.mmap(paramContext, paramCancellationSignal, uri)); 
      } 
    } 
    return (Map)Collections.unmodifiableMap(hashMap);
  }
  
  public static void requestFont(final Context context, final FontRequest request, final FontRequestCallback callback, Handler paramHandler) {
    paramHandler.post(new Runnable() {
          public void run() {
            try {
              FontsContractCompat.FontFamilyResult fontFamilyResult = FontsContractCompat.fetchFonts(context, null, request);
              if (fontFamilyResult.getStatusCode() != 0) {
                int k = fontFamilyResult.getStatusCode();
                if (k != 1) {
                  if (k != 2) {
                    callerThreadHandler.post(new Runnable() {
                          public void run() {
                            callback.onTypefaceRequestFailed(-3);
                          }
                        });
                    return;
                  } 
                  callerThreadHandler.post(new Runnable() {
                        public void run() {
                          callback.onTypefaceRequestFailed(-3);
                        }
                      });
                  return;
                } 
                callerThreadHandler.post(new Runnable() {
                      public void run() {
                        callback.onTypefaceRequestFailed(-2);
                      }
                    });
                return;
              } 
              FontsContractCompat.FontInfo[] arrayOfFontInfo = fontFamilyResult.getFonts();
              if (arrayOfFontInfo == null || arrayOfFontInfo.length == 0) {
                callerThreadHandler.post(new Runnable() {
                      public void run() {
                        callback.onTypefaceRequestFailed(1);
                      }
                    });
                return;
              } 
              int j = arrayOfFontInfo.length;
              for (final int resultCode = 0; i < j; i++) {
                FontsContractCompat.FontInfo fontInfo = arrayOfFontInfo[i];
                if (fontInfo.getResultCode() != 0) {
                  i = fontInfo.getResultCode();
                  if (i < 0) {
                    callerThreadHandler.post(new Runnable() {
                          public void run() {
                            callback.onTypefaceRequestFailed(-3);
                          }
                        });
                  } else {
                    callerThreadHandler.post(new Runnable() {
                          public void run() {
                            callback.onTypefaceRequestFailed(resultCode);
                          }
                        });
                  } 
                  return;
                } 
              } 
              final Typeface typeface = FontsContractCompat.buildTypeface(context, null, arrayOfFontInfo);
              if (typeface == null) {
                callerThreadHandler.post(new Runnable() {
                      public void run() {
                        callback.onTypefaceRequestFailed(-3);
                      }
                    });
                return;
              } 
              callerThreadHandler.post(new Runnable() {
                    public void run() {
                      callback.onTypefaceRetrieved(typeface);
                    }
                  });
              return;
            } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
              callerThreadHandler.post(new Runnable() {
                    public void run() {
                      callback.onTypefaceRequestFailed(-1);
                    }
                  });
              return;
            } 
          }
        });
  }
  
  public static void resetCache() {
    sTypefaceCache.evictAll();
  }
  
  public static final class Columns implements BaseColumns {
    public static final String FILE_ID = "file_id";
    
    public static final String ITALIC = "font_italic";
    
    public static final String RESULT_CODE = "result_code";
    
    public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
    
    public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
    
    public static final int RESULT_CODE_MALFORMED_QUERY = 3;
    
    public static final int RESULT_CODE_OK = 0;
    
    public static final String TTC_INDEX = "font_ttc_index";
    
    public static final String VARIATION_SETTINGS = "font_variation_settings";
    
    public static final String WEIGHT = "font_weight";
  }
  
  public static class FontFamilyResult {
    public static final int STATUS_OK = 0;
    
    public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
    
    public static final int STATUS_WRONG_CERTIFICATES = 1;
    
    private final FontsContractCompat.FontInfo[] mFonts;
    
    private final int mStatusCode;
    
    public FontFamilyResult(int param1Int, FontsContractCompat.FontInfo[] param1ArrayOfFontInfo) {
      this.mStatusCode = param1Int;
      this.mFonts = param1ArrayOfFontInfo;
    }
    
    public FontsContractCompat.FontInfo[] getFonts() {
      return this.mFonts;
    }
    
    public int getStatusCode() {
      return this.mStatusCode;
    }
    
    @Retention(RetentionPolicy.SOURCE)
    static @interface FontResultStatus {}
  }
  
  @Retention(RetentionPolicy.SOURCE)
  static @interface FontResultStatus {}
  
  public static class FontInfo {
    private final boolean mItalic;
    
    private final int mResultCode;
    
    private final int mTtcIndex;
    
    private final Uri mUri;
    
    private final int mWeight;
    
    public FontInfo(Uri param1Uri, int param1Int1, int param1Int2, boolean param1Boolean, int param1Int3) {
      this.mUri = (Uri)Preconditions.checkNotNull(param1Uri);
      this.mTtcIndex = param1Int1;
      this.mWeight = param1Int2;
      this.mItalic = param1Boolean;
      this.mResultCode = param1Int3;
    }
    
    public int getResultCode() {
      return this.mResultCode;
    }
    
    public int getTtcIndex() {
      return this.mTtcIndex;
    }
    
    public Uri getUri() {
      return this.mUri;
    }
    
    public int getWeight() {
      return this.mWeight;
    }
    
    public boolean isItalic() {
      return this.mItalic;
    }
  }
  
  public static class FontRequestCallback {
    public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
    
    public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
    
    public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
    
    public static final int FAIL_REASON_MALFORMED_QUERY = 3;
    
    public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
    
    public static final int FAIL_REASON_SECURITY_VIOLATION = -4;
    
    public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;
    
    public static final int RESULT_OK = 0;
    
    public void onTypefaceRequestFailed(int param1Int) {}
    
    public void onTypefaceRetrieved(Typeface param1Typeface) {}
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface FontRequestFailReason {}
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FontRequestFailReason {}
  
  private static final class TypefaceResult {
    final int mResult;
    
    final Typeface mTypeface;
    
    TypefaceResult(Typeface param1Typeface, int param1Int) {
      this.mTypeface = param1Typeface;
      this.mResult = param1Int;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\provider\FontsContractCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */