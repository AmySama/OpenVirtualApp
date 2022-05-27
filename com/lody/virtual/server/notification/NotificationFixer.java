package com.lody.virtual.server.notification;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.widget.RemoteViews;
import com.lody.virtual.helper.utils.BitmapUtils;
import com.lody.virtual.helper.utils.Reflect;
import mirror.android.graphics.drawable.Icon;
import mirror.com.android.internal.R_Hide;

class NotificationFixer {
  private static final String TAG = NotificationCompat.TAG;
  
  private NotificationCompat mNotificationCompat;
  
  NotificationFixer(NotificationCompat paramNotificationCompat) {
    this.mNotificationCompat = paramNotificationCompat;
  }
  
  private static void fixNotificationIcon(Context paramContext, Notification paramNotification, Notification.Builder paramBuilder) {
    if (Build.VERSION.SDK_INT < 23) {
      paramBuilder.setSmallIcon(paramNotification.icon);
      paramBuilder.setLargeIcon(paramNotification.largeIcon);
    } else {
      Icon icon2 = paramNotification.getSmallIcon();
      if (icon2 != null) {
        Bitmap bitmap = BitmapUtils.drawableToBitmap(icon2.loadDrawable(paramContext));
        if (bitmap != null)
          paramBuilder.setSmallIcon(Icon.createWithBitmap(bitmap)); 
      } 
      Icon icon1 = paramNotification.getLargeIcon();
      if (icon1 != null) {
        Bitmap bitmap = BitmapUtils.drawableToBitmap(icon1.loadDrawable(paramContext));
        if (bitmap != null)
          paramBuilder.setLargeIcon(Icon.createWithBitmap(bitmap)); 
      } 
    } 
  }
  
  void fixIcon(Icon paramIcon, Context paramContext, boolean paramBoolean) {
    if (paramIcon == null)
      return; 
    if (((Integer)Icon.mType.get(paramIcon)).intValue() == 2)
      if (paramBoolean) {
        Icon.mObj1.set(paramIcon, paramContext.getResources());
        Icon.mString1.set(paramIcon, paramContext.getPackageName());
      } else {
        Bitmap bitmap = BitmapUtils.drawableToBitmap(paramIcon.loadDrawable(paramContext));
        Icon.mObj1.set(paramIcon, bitmap);
        Icon.mString1.set(paramIcon, null);
        Icon.mType.set(paramIcon, Integer.valueOf(1));
      }  
  }
  
  void fixIconImage(Resources paramResources, RemoteViews paramRemoteViews, boolean paramBoolean, Notification paramNotification) {
    if (paramRemoteViews != null && paramNotification.icon != 0) {
      if (!this.mNotificationCompat.isSystemLayout(paramRemoteViews))
        return; 
      try {
        int i = R_Hide.id.icon.get();
      } finally {
        paramResources = null;
      } 
    } 
  }
  
  void fixNotificationRemoteViews(Context paramContext, Notification paramNotification) {
    try {
      Notification.Builder builder = (Notification.Builder)Reflect.on(Notification.Builder.class).create(new Object[] { paramContext, paramNotification }).get();
    } catch (Exception exception) {
      exception = null;
    } 
    if (exception != null) {
      Notification notification = exception.build();
      if (paramNotification.tickerView == null)
        paramNotification.tickerView = notification.tickerView; 
      if (paramNotification.contentView == null)
        paramNotification.contentView = notification.contentView; 
      if (paramNotification.bigContentView == null)
        paramNotification.bigContentView = notification.bigContentView; 
      if (paramNotification.headsUpContentView == null)
        paramNotification.headsUpContentView = notification.headsUpContentView; 
    } 
  }
  
  boolean fixRemoteViewActions(Context paramContext, boolean paramBoolean, RemoteViews paramRemoteViews) {
    // Byte code:
    //   0: aload_3
    //   1: ifnull -> 617
    //   4: getstatic mirror/com/android/internal/R_Hide$id.icon : Lmirror/RefStaticInt;
    //   7: invokevirtual get : ()I
    //   10: istore #4
    //   12: new java/util/ArrayList
    //   15: dup
    //   16: invokespecial <init> : ()V
    //   19: astore #5
    //   21: aload_3
    //   22: invokestatic on : (Ljava/lang/Object;)Lcom/lody/virtual/helper/utils/Reflect;
    //   25: ldc 'mActions'
    //   27: invokevirtual get : (Ljava/lang/String;)Ljava/lang/Object;
    //   30: checkcast java/util/ArrayList
    //   33: astore #6
    //   35: aload #6
    //   37: ifnull -> 584
    //   40: aload #6
    //   42: invokevirtual size : ()I
    //   45: iconst_1
    //   46: isub
    //   47: istore #7
    //   49: iconst_0
    //   50: istore #8
    //   52: iload #7
    //   54: iflt -> 528
    //   57: aload #6
    //   59: iload #7
    //   61: invokevirtual get : (I)Ljava/lang/Object;
    //   64: astore #9
    //   66: aload #9
    //   68: ifnonnull -> 78
    //   71: iload #8
    //   73: istore #10
    //   75: goto -> 309
    //   78: aload #9
    //   80: invokevirtual getClass : ()Ljava/lang/Class;
    //   83: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   86: ldc 'TextViewDrawableAction'
    //   88: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   91: ifeq -> 109
    //   94: aload #6
    //   96: aload #9
    //   98: invokevirtual remove : (Ljava/lang/Object;)Z
    //   101: pop
    //   102: iload #8
    //   104: istore #10
    //   106: goto -> 309
    //   109: iload #8
    //   111: istore #10
    //   113: aload #9
    //   115: invokestatic isInstance : (Ljava/lang/Object;)Z
    //   118: ifeq -> 309
    //   121: aload #9
    //   123: invokestatic on : (Ljava/lang/Object;)Lcom/lody/virtual/helper/utils/Reflect;
    //   126: ldc 'viewId'
    //   128: invokevirtual get : (Ljava/lang/String;)Ljava/lang/Object;
    //   131: checkcast java/lang/Integer
    //   134: invokevirtual intValue : ()I
    //   137: istore #11
    //   139: aload #9
    //   141: invokestatic on : (Ljava/lang/Object;)Lcom/lody/virtual/helper/utils/Reflect;
    //   144: ldc 'methodName'
    //   146: invokevirtual get : (Ljava/lang/String;)Ljava/lang/Object;
    //   149: checkcast java/lang/String
    //   152: astore #12
    //   154: aload #9
    //   156: invokestatic on : (Ljava/lang/Object;)Lcom/lody/virtual/helper/utils/Reflect;
    //   159: ldc_w 'type'
    //   162: invokevirtual get : (Ljava/lang/String;)Ljava/lang/Object;
    //   165: checkcast java/lang/Integer
    //   168: invokevirtual intValue : ()I
    //   171: istore #13
    //   173: aload #9
    //   175: invokestatic on : (Ljava/lang/Object;)Lcom/lody/virtual/helper/utils/Reflect;
    //   178: ldc_w 'value'
    //   181: invokevirtual get : (Ljava/lang/String;)Ljava/lang/Object;
    //   184: astore #14
    //   186: iload #8
    //   188: istore #15
    //   190: iload #8
    //   192: ifne -> 248
    //   195: iload #11
    //   197: iload #4
    //   199: if_icmpne -> 208
    //   202: iconst_1
    //   203: istore #8
    //   205: goto -> 211
    //   208: iconst_0
    //   209: istore #8
    //   211: iload #8
    //   213: istore #15
    //   215: iload #8
    //   217: ifeq -> 248
    //   220: iload #8
    //   222: istore #15
    //   224: iload #13
    //   226: iconst_4
    //   227: if_icmpne -> 248
    //   230: iload #8
    //   232: istore #15
    //   234: aload #14
    //   236: checkcast java/lang/Integer
    //   239: invokevirtual intValue : ()I
    //   242: ifne -> 248
    //   245: iconst_0
    //   246: istore #15
    //   248: aload #12
    //   250: ldc_w 'setImageResource'
    //   253: invokevirtual equals : (Ljava/lang/Object;)Z
    //   256: ifeq -> 316
    //   259: aload #5
    //   261: new com/lody/virtual/server/notification/NotificationFixer$BitmapReflectionAction
    //   264: dup
    //   265: iload #11
    //   267: ldc_w 'setImageBitmap'
    //   270: aload_1
    //   271: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   274: aload #14
    //   276: checkcast java/lang/Integer
    //   279: invokevirtual intValue : ()I
    //   282: invokevirtual getDrawable : (I)Landroid/graphics/drawable/Drawable;
    //   285: invokestatic drawableToBitmap : (Landroid/graphics/drawable/Drawable;)Landroid/graphics/Bitmap;
    //   288: invokespecial <init> : (ILjava/lang/String;Landroid/graphics/Bitmap;)V
    //   291: invokeinterface add : (Ljava/lang/Object;)Z
    //   296: pop
    //   297: aload #6
    //   299: aload #9
    //   301: invokevirtual remove : (Ljava/lang/Object;)Z
    //   304: pop
    //   305: iload #15
    //   307: istore #10
    //   309: iload #10
    //   311: istore #15
    //   313: goto -> 518
    //   316: aload #12
    //   318: ldc_w 'setText'
    //   321: invokevirtual equals : (Ljava/lang/Object;)Z
    //   324: ifeq -> 384
    //   327: iload #13
    //   329: iconst_4
    //   330: if_icmpne -> 384
    //   333: aload #9
    //   335: invokestatic on : (Ljava/lang/Object;)Lcom/lody/virtual/helper/utils/Reflect;
    //   338: ldc_w 'type'
    //   341: bipush #9
    //   343: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   346: invokevirtual set : (Ljava/lang/String;Ljava/lang/Object;)Lcom/lody/virtual/helper/utils/Reflect;
    //   349: pop
    //   350: aload #9
    //   352: invokestatic on : (Ljava/lang/Object;)Lcom/lody/virtual/helper/utils/Reflect;
    //   355: ldc_w 'value'
    //   358: aload_1
    //   359: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   362: aload #14
    //   364: checkcast java/lang/Integer
    //   367: invokevirtual intValue : ()I
    //   370: invokevirtual getString : (I)Ljava/lang/String;
    //   373: invokevirtual set : (Ljava/lang/String;Ljava/lang/Object;)Lcom/lody/virtual/helper/utils/Reflect;
    //   376: pop
    //   377: iload #15
    //   379: istore #10
    //   381: goto -> 309
    //   384: aload #12
    //   386: ldc_w 'setLabelFor'
    //   389: invokevirtual equals : (Ljava/lang/Object;)Z
    //   392: ifeq -> 410
    //   395: aload #6
    //   397: aload #9
    //   399: invokevirtual remove : (Ljava/lang/Object;)Z
    //   402: pop
    //   403: iload #15
    //   405: istore #10
    //   407: goto -> 309
    //   410: aload #12
    //   412: ldc_w 'setBackgroundResource'
    //   415: invokevirtual equals : (Ljava/lang/Object;)Z
    //   418: ifeq -> 436
    //   421: aload #6
    //   423: aload #9
    //   425: invokevirtual remove : (Ljava/lang/Object;)Z
    //   428: pop
    //   429: iload #15
    //   431: istore #10
    //   433: goto -> 309
    //   436: aload #12
    //   438: ldc_w 'setImageURI'
    //   441: invokevirtual equals : (Ljava/lang/Object;)Z
    //   444: ifeq -> 483
    //   447: iload #15
    //   449: istore #10
    //   451: aload #14
    //   453: checkcast android/net/Uri
    //   456: invokevirtual getScheme : ()Ljava/lang/String;
    //   459: ldc_w 'http'
    //   462: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   465: ifne -> 309
    //   468: aload #6
    //   470: aload #9
    //   472: invokevirtual remove : (Ljava/lang/Object;)Z
    //   475: pop
    //   476: iload #15
    //   478: istore #10
    //   480: goto -> 309
    //   483: iload #15
    //   485: istore #10
    //   487: getstatic android/os/Build$VERSION.SDK_INT : I
    //   490: bipush #23
    //   492: if_icmplt -> 309
    //   495: iload #15
    //   497: istore #10
    //   499: aload #14
    //   501: instanceof android/graphics/drawable/Icon
    //   504: ifeq -> 309
    //   507: aload_0
    //   508: aload #14
    //   510: checkcast android/graphics/drawable/Icon
    //   513: aload_1
    //   514: iload_2
    //   515: invokevirtual fixIcon : (Landroid/graphics/drawable/Icon;Landroid/content/Context;Z)V
    //   518: iinc #7, -1
    //   521: iload #15
    //   523: istore #8
    //   525: goto -> 52
    //   528: aload #5
    //   530: invokeinterface iterator : ()Ljava/util/Iterator;
    //   535: astore_1
    //   536: aload_1
    //   537: invokeinterface hasNext : ()Z
    //   542: ifeq -> 578
    //   545: aload_1
    //   546: invokeinterface next : ()Ljava/lang/Object;
    //   551: checkcast com/lody/virtual/server/notification/NotificationFixer$BitmapReflectionAction
    //   554: astore #6
    //   556: aload_3
    //   557: aload #6
    //   559: getfield viewId : I
    //   562: aload #6
    //   564: getfield methodName : Ljava/lang/String;
    //   567: aload #6
    //   569: getfield bitmap : Landroid/graphics/Bitmap;
    //   572: invokevirtual setBitmap : (ILjava/lang/String;Landroid/graphics/Bitmap;)V
    //   575: goto -> 536
    //   578: iload #8
    //   580: istore_2
    //   581: goto -> 586
    //   584: iconst_0
    //   585: istore_2
    //   586: iload_2
    //   587: istore #15
    //   589: getstatic android/os/Build$VERSION.SDK_INT : I
    //   592: bipush #21
    //   594: if_icmpge -> 620
    //   597: getstatic mirror/android/widget/RemoteViews.mPackage : Lmirror/RefObject;
    //   600: aload_3
    //   601: invokestatic get : ()Lcom/lody/virtual/client/core/VirtualCore;
    //   604: invokevirtual getHostPkg : ()Ljava/lang/String;
    //   607: invokevirtual set : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   610: pop
    //   611: iload_2
    //   612: istore #15
    //   614: goto -> 620
    //   617: iconst_0
    //   618: istore #15
    //   620: iload #15
    //   622: ireturn
  }
  
  private static class BitmapReflectionAction {
    Bitmap bitmap;
    
    String methodName;
    
    int viewId;
    
    BitmapReflectionAction(int param1Int, String param1String, Bitmap param1Bitmap) {
      this.viewId = param1Int;
      this.methodName = param1String;
      this.bitmap = param1Bitmap;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\notification\NotificationFixer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */