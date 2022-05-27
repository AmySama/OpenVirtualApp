package com.tencent.mapsdk.rastercore.f;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.GeoPoint;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.rastercore.d.e;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class a {
  private static final int a = Float.floatToRawIntBits(0.0F);
  
  private static final int b = Float.floatToRawIntBits(0.0F);
  
  static {
    Double.longBitsToDouble(4368491638549381120L);
    Double.longBitsToDouble(4503599627370496L);
  }
  
  public static int a(Object[] paramArrayOfObject) {
    return Arrays.hashCode(paramArrayOfObject);
  }
  
  public static Bitmap a(Bitmap paramBitmap, float paramFloat) {
    return (paramBitmap == null) ? null : Bitmap.createScaledBitmap(paramBitmap, (int)(paramBitmap.getWidth() * paramFloat), (int)(paramBitmap.getHeight() * paramFloat), true);
  }
  
  public static Bitmap a(String paramString) {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      this("/assets/");
      stringBuilder.append(paramString);
      InputStream inputStream = BitmapDescriptorFactory.class.getResourceAsStream(stringBuilder.toString());
      Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
      inputStream.close();
      return bitmap;
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public static GeoPoint a(LatLng paramLatLng) {
    return new GeoPoint((int)(paramLatLng.getLatitude() * 1000000.0D), (int)(paramLatLng.getLongitude() * 1000000.0D));
  }
  
  public static LatLng a(GeoPoint paramGeoPoint) {
    return new LatLng(paramGeoPoint.getLatitudeE6() * 1.0D / 1000000.0D, paramGeoPoint.getLongitudeE6() * 1.0D / 1000000.0D);
  }
  
  public static String a(Context paramContext) {
    String str1;
    String str2 = "";
    if (paramContext == null)
      return ""; 
    String str3 = str2;
    try {
      ApplicationInfo applicationInfo = paramContext.getPackageManager().getApplicationInfo(e.a().getPackageName(), 128);
      str3 = str2;
      str2 = applicationInfo.metaData.getString("com.tencent.map.api_key");
      str3 = str2;
      str1 = str2;
      if (TextUtils.isEmpty(str2)) {
        str3 = str2;
        str1 = applicationInfo.metaData.getString("TencentMapSDK");
      } 
    } catch (Exception exception) {
      (new StringBuilder("error get mapkey:")).append(exception.getMessage());
      str1 = str3;
    } 
    return str1;
  }
  
  public static String a(String paramString, Object paramObject) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("=");
    stringBuilder.append(String.valueOf(paramObject));
    return stringBuilder.toString();
  }
  
  public static String a(String... paramVarArgs) {
    StringBuilder stringBuilder = new StringBuilder();
    int i = paramVarArgs.length;
    byte b1 = 0;
    byte b2 = 0;
    while (b1 < i) {
      stringBuilder.append(paramVarArgs[b1]);
      if (b2 != paramVarArgs.length - 1)
        stringBuilder.append(","); 
      b2++;
      b1++;
    } 
    return stringBuilder.toString();
  }
  
  public static void a(View paramView, int paramInt) {
    // Byte code:
    //   0: ldc android/view/View
    //   2: invokevirtual getMethods : ()[Ljava/lang/reflect/Method;
    //   5: astore_2
    //   6: aload_2
    //   7: arraylength
    //   8: istore_3
    //   9: iconst_0
    //   10: istore #4
    //   12: iload #4
    //   14: iload_3
    //   15: if_icmpge -> 46
    //   18: aload_2
    //   19: iload #4
    //   21: aaload
    //   22: astore #5
    //   24: aload #5
    //   26: invokevirtual getName : ()Ljava/lang/String;
    //   29: ldc 'setLayerType'
    //   31: invokevirtual equals : (Ljava/lang/Object;)Z
    //   34: ifeq -> 40
    //   37: goto -> 49
    //   40: iinc #4, 1
    //   43: goto -> 12
    //   46: aconst_null
    //   47: astore #5
    //   49: aload #5
    //   51: ifnull -> 160
    //   54: iload_1
    //   55: ifeq -> 101
    //   58: iload_1
    //   59: iconst_1
    //   60: if_icmpeq -> 90
    //   63: iload_1
    //   64: iconst_2
    //   65: if_icmpeq -> 73
    //   68: aconst_null
    //   69: astore_2
    //   70: goto -> 111
    //   73: ldc android/view/View
    //   75: astore #6
    //   77: ldc 'LAYER_TYPE_HARDWARE'
    //   79: astore_2
    //   80: aload #6
    //   82: aload_2
    //   83: invokevirtual getField : (Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   86: astore_2
    //   87: goto -> 111
    //   90: ldc android/view/View
    //   92: ldc 'LAYER_TYPE_SOFTWARE'
    //   94: invokevirtual getField : (Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   97: astore_2
    //   98: goto -> 111
    //   101: ldc android/view/View
    //   103: astore #6
    //   105: ldc 'LAYER_TYPE_NONE'
    //   107: astore_2
    //   108: goto -> 80
    //   111: aload_2
    //   112: astore #6
    //   114: aload_2
    //   115: ifnonnull -> 127
    //   118: ldc android/view/View
    //   120: ldc 'LAYER_TYPE_SOFTWARE'
    //   122: invokevirtual getField : (Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   125: astore #6
    //   127: aload #5
    //   129: aload_0
    //   130: iconst_2
    //   131: anewarray java/lang/Object
    //   134: dup
    //   135: iconst_0
    //   136: aload #6
    //   138: aconst_null
    //   139: invokevirtual getInt : (Ljava/lang/Object;)I
    //   142: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   145: aastore
    //   146: dup
    //   147: iconst_1
    //   148: aconst_null
    //   149: aastore
    //   150: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   153: pop
    //   154: return
    //   155: astore_0
    //   156: aload_0
    //   157: invokevirtual printStackTrace : ()V
    //   160: return
    // Exception table:
    //   from	to	target	type
    //   80	87	155	java/lang/Exception
    //   90	98	155	java/lang/Exception
    //   118	127	155	java/lang/Exception
    //   127	154	155	java/lang/Exception
  }
  
  private static boolean a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
    boolean bool;
    if (Math.abs((paramDouble3 - paramDouble1) * (paramDouble6 - paramDouble2) - (paramDouble5 - paramDouble1) * (paramDouble4 - paramDouble2)) < 1.0E-9D && (paramDouble1 - paramDouble3) * (paramDouble1 - paramDouble5) <= 0.0D && (paramDouble2 - paramDouble4) * (paramDouble2 - paramDouble6) <= 0.0D) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean a(float paramFloat1, float paramFloat2) {
    // Byte code:
    //   0: fload_0
    //   1: invokestatic floatToRawIntBits : (F)I
    //   4: istore_2
    //   5: fconst_0
    //   6: invokestatic floatToRawIntBits : (F)I
    //   9: istore_3
    //   10: iload_2
    //   11: iload_3
    //   12: ixor
    //   13: ldc -2147483648
    //   15: iand
    //   16: ifne -> 39
    //   19: iload_2
    //   20: iload_3
    //   21: isub
    //   22: invokestatic abs : (I)I
    //   25: iconst_1
    //   26: if_icmpgt -> 34
    //   29: iconst_1
    //   30: istore_2
    //   31: goto -> 97
    //   34: iconst_0
    //   35: istore_2
    //   36: goto -> 97
    //   39: getstatic com/tencent/mapsdk/rastercore/f/a.a : I
    //   42: istore #4
    //   44: iload_2
    //   45: iload_3
    //   46: if_icmpge -> 64
    //   49: iload_3
    //   50: iload #4
    //   52: isub
    //   53: istore #4
    //   55: iload_2
    //   56: getstatic com/tencent/mapsdk/rastercore/f/a.b : I
    //   59: isub
    //   60: istore_2
    //   61: goto -> 80
    //   64: getstatic com/tencent/mapsdk/rastercore/f/a.b : I
    //   67: istore #5
    //   69: iload_2
    //   70: iload #4
    //   72: isub
    //   73: istore #4
    //   75: iload_3
    //   76: iload #5
    //   78: isub
    //   79: istore_2
    //   80: iload #4
    //   82: iconst_1
    //   83: if_icmpgt -> 34
    //   86: iload_2
    //   87: iconst_1
    //   88: iload #4
    //   90: isub
    //   91: if_icmpgt -> 34
    //   94: goto -> 29
    //   97: iload_2
    //   98: ifeq -> 117
    //   101: fload_0
    //   102: invokestatic isNaN : (F)Z
    //   105: ifne -> 117
    //   108: fconst_0
    //   109: invokestatic isNaN : (F)Z
    //   112: ifne -> 117
    //   115: iconst_1
    //   116: ireturn
    //   117: iconst_0
    //   118: ireturn
  }
  
  public static boolean a(LatLng paramLatLng, List<LatLng> paramList) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getLongitude : ()D
    //   4: dstore_2
    //   5: aload_0
    //   6: invokevirtual getLatitude : ()D
    //   9: dstore #4
    //   11: aload_0
    //   12: invokevirtual getLatitude : ()D
    //   15: dstore #6
    //   17: iconst_0
    //   18: istore #8
    //   20: iconst_0
    //   21: istore #9
    //   23: iconst_0
    //   24: istore #10
    //   26: aload_1
    //   27: invokeinterface size : ()I
    //   32: istore #11
    //   34: iconst_1
    //   35: istore #12
    //   37: iload #9
    //   39: iload #11
    //   41: iconst_1
    //   42: isub
    //   43: if_icmpge -> 377
    //   46: aload_1
    //   47: iload #9
    //   49: invokeinterface get : (I)Ljava/lang/Object;
    //   54: checkcast com/tencent/mapsdk/raster/model/LatLng
    //   57: invokevirtual getLongitude : ()D
    //   60: dstore #13
    //   62: aload_1
    //   63: iload #9
    //   65: invokeinterface get : (I)Ljava/lang/Object;
    //   70: checkcast com/tencent/mapsdk/raster/model/LatLng
    //   73: invokevirtual getLatitude : ()D
    //   76: dstore #15
    //   78: iload #9
    //   80: iconst_1
    //   81: iadd
    //   82: istore #17
    //   84: aload_1
    //   85: iload #17
    //   87: invokeinterface get : (I)Ljava/lang/Object;
    //   92: checkcast com/tencent/mapsdk/raster/model/LatLng
    //   95: invokevirtual getLongitude : ()D
    //   98: dstore #18
    //   100: aload_1
    //   101: iload #17
    //   103: invokeinterface get : (I)Ljava/lang/Object;
    //   108: checkcast com/tencent/mapsdk/raster/model/LatLng
    //   111: invokevirtual getLatitude : ()D
    //   114: dstore #20
    //   116: dload_2
    //   117: dload #4
    //   119: dload #13
    //   121: dload #15
    //   123: dload #18
    //   125: dload #20
    //   127: invokestatic a : (DDDDDD)Z
    //   130: ifeq -> 135
    //   133: iconst_1
    //   134: ireturn
    //   135: dload #20
    //   137: dload #15
    //   139: dsub
    //   140: dstore #22
    //   142: iload #10
    //   144: istore #11
    //   146: dload #22
    //   148: invokestatic abs : (D)D
    //   151: ldc2_w 1.0E-9
    //   154: dcmpg
    //   155: iflt -> 366
    //   158: dload #13
    //   160: dload #15
    //   162: dload_2
    //   163: dload #4
    //   165: ldc2_w 180.0
    //   168: dload #6
    //   170: invokestatic a : (DDDDDD)Z
    //   173: ifeq -> 197
    //   176: iload #10
    //   178: istore #11
    //   180: dload #15
    //   182: dload #20
    //   184: dcmpl
    //   185: ifle -> 366
    //   188: iload #10
    //   190: iconst_1
    //   191: iadd
    //   192: istore #11
    //   194: goto -> 366
    //   197: dload #18
    //   199: dload #20
    //   201: dload_2
    //   202: dload #4
    //   204: ldc2_w 180.0
    //   207: dload #6
    //   209: invokestatic a : (DDDDDD)Z
    //   212: ifeq -> 230
    //   215: iload #10
    //   217: istore #11
    //   219: dload #20
    //   221: dload #15
    //   223: dcmpl
    //   224: ifle -> 366
    //   227: goto -> 188
    //   230: dload #18
    //   232: dload #13
    //   234: dsub
    //   235: dstore #18
    //   237: dload #6
    //   239: dload #4
    //   241: dsub
    //   242: dstore #24
    //   244: ldc2_w 180.0
    //   247: dload_2
    //   248: dsub
    //   249: dstore #26
    //   251: dload #18
    //   253: dload #24
    //   255: dmul
    //   256: dload #22
    //   258: dload #26
    //   260: dmul
    //   261: dsub
    //   262: dstore #20
    //   264: dload #20
    //   266: dconst_0
    //   267: dcmpl
    //   268: ifeq -> 351
    //   271: dload #15
    //   273: dload #4
    //   275: dsub
    //   276: dstore #15
    //   278: dload #13
    //   280: dload_2
    //   281: dsub
    //   282: dstore #13
    //   284: dload #26
    //   286: dload #15
    //   288: dmul
    //   289: dload #24
    //   291: dload #13
    //   293: dmul
    //   294: dsub
    //   295: dload #20
    //   297: ddiv
    //   298: dstore #24
    //   300: dload #15
    //   302: dload #18
    //   304: dmul
    //   305: dload #13
    //   307: dload #22
    //   309: dmul
    //   310: dsub
    //   311: dload #20
    //   313: ddiv
    //   314: dstore #22
    //   316: dload #24
    //   318: dconst_0
    //   319: dcmpl
    //   320: iflt -> 351
    //   323: dload #24
    //   325: dconst_1
    //   326: dcmpg
    //   327: ifgt -> 351
    //   330: dload #22
    //   332: dconst_0
    //   333: dcmpl
    //   334: iflt -> 351
    //   337: dload #22
    //   339: dconst_1
    //   340: dcmpg
    //   341: ifgt -> 351
    //   344: iload #12
    //   346: istore #9
    //   348: goto -> 354
    //   351: iconst_0
    //   352: istore #9
    //   354: iload #10
    //   356: istore #11
    //   358: iload #9
    //   360: ifeq -> 366
    //   363: goto -> 188
    //   366: iload #17
    //   368: istore #9
    //   370: iload #11
    //   372: istore #10
    //   374: goto -> 26
    //   377: iload #10
    //   379: iconst_2
    //   380: irem
    //   381: ifeq -> 387
    //   384: iconst_1
    //   385: istore #8
    //   387: iload #8
    //   389: ireturn
  }
  
  public static final boolean a(Collection<?> paramCollection) {
    return (paramCollection == null || paramCollection.size() <= 0);
  }
  
  static {
    Double.doubleToRawLongBits(0.0D);
    Double.doubleToRawLongBits(0.0D);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\f\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */