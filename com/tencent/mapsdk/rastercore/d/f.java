package com.tencent.mapsdk.rastercore.d;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Scroller;
import com.stub.StubApp;
import com.tencent.mapsdk.raster.model.CameraPosition;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.rastercore.b.a;
import com.tencent.mapsdk.rastercore.f.a;
import com.tencent.tencentmap.mapsdk.map.CancelableCallback;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

public final class f implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener, View.OnKeyListener {
  private long A = 0L;
  
  private Point B;
  
  private e a;
  
  private b b;
  
  private a c;
  
  private GestureDetector d;
  
  private MotionEvent e;
  
  private TencentMap.OnMapClickListener f;
  
  private TencentMap.OnMapLongClickListener g;
  
  private TencentMap.OnMapCameraChangeListener h;
  
  private TencentMap.OnInfoWindowClickListener i;
  
  private TencentMap.InfoWindowAdapter j;
  
  private TencentMap.OnMarkerDraggedListener k;
  
  private TencentMap.OnMarkerClickListener l;
  
  private float m = 1.0F;
  
  private float n = 1.0F;
  
  private float o = 1.0F;
  
  private boolean p = false;
  
  private boolean q = false;
  
  private boolean r = false;
  
  private Scroller s;
  
  private int t = 0;
  
  private int u = 0;
  
  private long v;
  
  private float w;
  
  private float x;
  
  private float y;
  
  private float z;
  
  public f(e parame) {
    this.a = parame;
    this.b = parame.c();
    this.c = parame.e();
    this.d = new GestureDetector(StubApp.getOrigApplicationContext(e.a().getApplicationContext()), this);
    this.s = new Scroller(e.a());
    new DisplayMetrics();
    DisplayMetrics displayMetrics = StubApp.getOrigApplicationContext(e.a().getApplicationContext()).getResources().getDisplayMetrics();
    this.t = displayMetrics.widthPixels / 2;
    this.u = displayMetrics.heightPixels / 2;
  }
  
  private static float c(MotionEvent paramMotionEvent) {
    float f1 = paramMotionEvent.getX(0) - paramMotionEvent.getX(1);
    float f2 = paramMotionEvent.getY(0) - paramMotionEvent.getY(1);
    StringBuilder stringBuilder = new StringBuilder("event0.x:");
    stringBuilder.append(paramMotionEvent.getX(0));
    stringBuilder.append(",event0.y:");
    stringBuilder.append(paramMotionEvent.getY(0));
    stringBuilder.append(";  event1.x:");
    stringBuilder.append(paramMotionEvent.getX(1));
    stringBuilder.append(",event1.y:");
    stringBuilder.append(paramMotionEvent.getY(1));
    return (float)Math.sqrt((f1 * f1 + f2 * f2));
  }
  
  public final void a() {
    if (this.s.computeScrollOffset()) {
      int i = this.s.getCurrX() - this.t;
      int j = this.s.getCurrY() - this.u;
      this.t = this.s.getCurrX();
      this.u = this.s.getCurrY();
      this.b.scrollBy(i, j);
      if (this.s.isFinished()) {
        if (this.h != null)
          a(true); 
      } else if (Math.abs(i) < 6) {
        Math.abs(j);
      } 
      this.a.a(false, false);
      return;
    } 
  }
  
  public final void a(TencentMap.InfoWindowAdapter paramInfoWindowAdapter) {
    this.j = paramInfoWindowAdapter;
  }
  
  public final void a(TencentMap.OnInfoWindowClickListener paramOnInfoWindowClickListener) {
    this.i = paramOnInfoWindowClickListener;
  }
  
  public final void a(TencentMap.OnMapCameraChangeListener paramOnMapCameraChangeListener) {
    this.h = paramOnMapCameraChangeListener;
  }
  
  public final void a(TencentMap.OnMapClickListener paramOnMapClickListener) {
    this.f = paramOnMapClickListener;
  }
  
  public final void a(TencentMap.OnMapLongClickListener paramOnMapLongClickListener) {
    this.g = paramOnMapLongClickListener;
  }
  
  public final void a(TencentMap.OnMarkerClickListener paramOnMarkerClickListener) {
    this.l = paramOnMarkerClickListener;
  }
  
  public final void a(TencentMap.OnMarkerDraggedListener paramOnMarkerDraggedListener) {
    this.k = paramOnMarkerDraggedListener;
  }
  
  public final void a(boolean paramBoolean) {
    if (this.h != null) {
      CameraPosition cameraPosition = this.a.b().d();
      if (paramBoolean) {
        this.h.onCameraChangeFinish(cameraPosition);
        this.r = false;
        return;
      } 
      this.h.onCameraChange(cameraPosition);
      this.r = true;
    } 
  }
  
  public final boolean a(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getAction : ()I
    //   4: sipush #255
    //   7: iand
    //   8: istore_2
    //   9: iconst_0
    //   10: istore_3
    //   11: iload_2
    //   12: ifeq -> 735
    //   15: iload_2
    //   16: iconst_1
    //   17: if_icmpeq -> 488
    //   20: iload_2
    //   21: iconst_2
    //   22: if_icmpeq -> 304
    //   25: iload_2
    //   26: iconst_5
    //   27: if_icmpeq -> 233
    //   30: iload_2
    //   31: bipush #6
    //   33: if_icmpeq -> 41
    //   36: iload_3
    //   37: istore_2
    //   38: goto -> 810
    //   41: iload_3
    //   42: istore_2
    //   43: aload_1
    //   44: invokevirtual getPointerCount : ()I
    //   47: iconst_2
    //   48: if_icmpne -> 810
    //   51: new java/lang/StringBuilder
    //   54: dup
    //   55: ldc 'event0.x:'
    //   57: invokespecial <init> : (Ljava/lang/String;)V
    //   60: astore #4
    //   62: aload #4
    //   64: aload_1
    //   65: iconst_0
    //   66: invokevirtual getX : (I)F
    //   69: invokevirtual append : (F)Ljava/lang/StringBuilder;
    //   72: pop
    //   73: aload #4
    //   75: ldc ',event0.y:'
    //   77: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: pop
    //   81: aload #4
    //   83: aload_1
    //   84: iconst_0
    //   85: invokevirtual getY : (I)F
    //   88: invokevirtual append : (F)Ljava/lang/StringBuilder;
    //   91: pop
    //   92: aload #4
    //   94: ldc ';  event1.x:'
    //   96: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: pop
    //   100: aload #4
    //   102: aload_1
    //   103: iconst_1
    //   104: invokevirtual getX : (I)F
    //   107: invokevirtual append : (F)Ljava/lang/StringBuilder;
    //   110: pop
    //   111: aload #4
    //   113: ldc ',event1.y:'
    //   115: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: pop
    //   119: aload #4
    //   121: aload_1
    //   122: iconst_1
    //   123: invokevirtual getY : (I)F
    //   126: invokevirtual append : (F)Ljava/lang/StringBuilder;
    //   129: pop
    //   130: aload_0
    //   131: getfield q : Z
    //   134: ifeq -> 223
    //   137: aload_0
    //   138: getfield n : F
    //   141: aload_0
    //   142: getfield m : F
    //   145: fmul
    //   146: f2d
    //   147: invokestatic sqrt : (D)D
    //   150: dstore #5
    //   152: new android/graphics/PointF
    //   155: dup
    //   156: aload_0
    //   157: getfield b : Lcom/tencent/mapsdk/rastercore/d/b;
    //   160: invokevirtual getWidth : ()I
    //   163: iconst_2
    //   164: idiv
    //   165: i2f
    //   166: aload_0
    //   167: getfield b : Lcom/tencent/mapsdk/rastercore/d/b;
    //   170: invokevirtual getHeight : ()I
    //   173: iconst_2
    //   174: idiv
    //   175: i2f
    //   176: invokespecial <init> : (FF)V
    //   179: astore #4
    //   181: aload_0
    //   182: getfield b : Lcom/tencent/mapsdk/rastercore/d/b;
    //   185: astore #7
    //   187: aload #7
    //   189: aload #7
    //   191: invokevirtual c : ()D
    //   194: dload #5
    //   196: dconst_1
    //   197: dsub
    //   198: ldc2_w 1.5
    //   201: dmul
    //   202: dadd
    //   203: aload #4
    //   205: iconst_1
    //   206: ldc2_w 200
    //   209: getstatic com/tencent/mapsdk/rastercore/a/a$a.b : Lcom/tencent/mapsdk/rastercore/a/a$a;
    //   212: new com/tencent/mapsdk/rastercore/d/f$1
    //   215: dup
    //   216: aload_0
    //   217: invokespecial <init> : (Lcom/tencent/mapsdk/rastercore/d/f;)V
    //   220: invokevirtual a : (DLandroid/graphics/PointF;ZJLcom/tencent/mapsdk/rastercore/a/a$a;Lcom/tencent/tencentmap/mapsdk/map/CancelableCallback;)V
    //   223: aload_0
    //   224: aconst_null
    //   225: putfield B : Landroid/graphics/Point;
    //   228: iload_3
    //   229: istore_2
    //   230: goto -> 810
    //   233: iload_3
    //   234: istore_2
    //   235: aload_1
    //   236: invokevirtual getPointerCount : ()I
    //   239: iconst_1
    //   240: if_icmple -> 810
    //   243: aload_0
    //   244: aload_1
    //   245: invokevirtual getEventTime : ()J
    //   248: putfield v : J
    //   251: aload_0
    //   252: aload_1
    //   253: iconst_0
    //   254: invokevirtual getX : (I)F
    //   257: putfield x : F
    //   260: aload_0
    //   261: aload_1
    //   262: iconst_0
    //   263: invokevirtual getY : (I)F
    //   266: putfield z : F
    //   269: aload_0
    //   270: aload_1
    //   271: iconst_1
    //   272: invokevirtual getX : (I)F
    //   275: putfield w : F
    //   278: aload_0
    //   279: aload_1
    //   280: iconst_1
    //   281: invokevirtual getY : (I)F
    //   284: putfield y : F
    //   287: aload_1
    //   288: invokestatic c : (Landroid/view/MotionEvent;)F
    //   291: fstore #8
    //   293: aload_0
    //   294: fload #8
    //   296: putfield o : F
    //   299: iload_3
    //   300: istore_2
    //   301: goto -> 810
    //   304: iload_3
    //   305: istore_2
    //   306: aload_1
    //   307: invokevirtual getPointerCount : ()I
    //   310: iconst_1
    //   311: if_icmple -> 810
    //   314: aload_0
    //   315: getfield a : Lcom/tencent/mapsdk/rastercore/d/e;
    //   318: invokevirtual f : ()Lcom/tencent/mapsdk/rastercore/d/a$1;
    //   321: invokevirtual i : ()Z
    //   324: ifne -> 329
    //   327: iconst_0
    //   328: ireturn
    //   329: aload_1
    //   330: invokestatic c : (Landroid/view/MotionEvent;)F
    //   333: fstore #8
    //   335: iload_3
    //   336: istore_2
    //   337: fload #8
    //   339: ldc_w 10.0
    //   342: fcmpg
    //   343: iflt -> 810
    //   346: aload_0
    //   347: getfield o : F
    //   350: fstore #9
    //   352: fload #9
    //   354: ldc_w 10.0
    //   357: fcmpg
    //   358: ifge -> 364
    //   361: goto -> 293
    //   364: aload_0
    //   365: aload_0
    //   366: getfield n : F
    //   369: putfield m : F
    //   372: aload_0
    //   373: fload #8
    //   375: fload #9
    //   377: fdiv
    //   378: putfield n : F
    //   381: new java/lang/StringBuilder
    //   384: dup
    //   385: invokespecial <init> : ()V
    //   388: aload_0
    //   389: getfield n : F
    //   392: invokevirtual append : (F)Ljava/lang/StringBuilder;
    //   395: pop
    //   396: iload_3
    //   397: istore_2
    //   398: aload_0
    //   399: getfield n : F
    //   402: fconst_1
    //   403: fsub
    //   404: invokestatic abs : (F)F
    //   407: f2d
    //   408: ldc2_w 0.01
    //   411: dcmpg
    //   412: iflt -> 810
    //   415: new android/graphics/PointF
    //   418: dup
    //   419: aload_0
    //   420: getfield b : Lcom/tencent/mapsdk/rastercore/d/b;
    //   423: invokevirtual getWidth : ()I
    //   426: iconst_2
    //   427: idiv
    //   428: i2f
    //   429: aload_0
    //   430: getfield b : Lcom/tencent/mapsdk/rastercore/d/b;
    //   433: invokevirtual getHeight : ()I
    //   436: iconst_2
    //   437: idiv
    //   438: i2f
    //   439: invokespecial <init> : (FF)V
    //   442: astore #4
    //   444: aload_0
    //   445: getfield b : Lcom/tencent/mapsdk/rastercore/d/b;
    //   448: aload_0
    //   449: getfield n : F
    //   452: f2d
    //   453: aload #4
    //   455: invokevirtual a : (DLandroid/graphics/PointF;)V
    //   458: aload_0
    //   459: iconst_0
    //   460: invokevirtual a : (Z)V
    //   463: aload_0
    //   464: fload #8
    //   466: putfield o : F
    //   469: aload_0
    //   470: getfield a : Lcom/tencent/mapsdk/rastercore/d/e;
    //   473: iconst_0
    //   474: iconst_0
    //   475: invokevirtual a : (ZZ)V
    //   478: aload_0
    //   479: iconst_1
    //   480: putfield q : Z
    //   483: iconst_1
    //   484: istore_2
    //   485: goto -> 810
    //   488: aload_0
    //   489: aload_1
    //   490: invokevirtual getEventTime : ()J
    //   493: putfield A : J
    //   496: aload_0
    //   497: getfield a : Lcom/tencent/mapsdk/rastercore/d/e;
    //   500: invokevirtual f : ()Lcom/tencent/mapsdk/rastercore/d/a$1;
    //   503: invokevirtual g : ()Z
    //   506: ifeq -> 516
    //   509: aload_0
    //   510: getfield a : Lcom/tencent/mapsdk/rastercore/d/e;
    //   513: invokevirtual k : ()V
    //   516: aload_1
    //   517: invokevirtual getEventTime : ()J
    //   520: aload_0
    //   521: getfield v : J
    //   524: lsub
    //   525: ldc2_w 200
    //   528: lcmp
    //   529: ifge -> 705
    //   532: aload_1
    //   533: iconst_0
    //   534: invokevirtual getX : (I)F
    //   537: aload_0
    //   538: getfield x : F
    //   541: fsub
    //   542: invokestatic abs : (F)F
    //   545: ldc_w 10.0
    //   548: fcmpg
    //   549: ifge -> 572
    //   552: aload_1
    //   553: iconst_0
    //   554: invokevirtual getY : (I)F
    //   557: aload_0
    //   558: getfield z : F
    //   561: fsub
    //   562: invokestatic abs : (F)F
    //   565: ldc_w 10.0
    //   568: fcmpg
    //   569: iflt -> 612
    //   572: aload_1
    //   573: iconst_0
    //   574: invokevirtual getX : (I)F
    //   577: aload_0
    //   578: getfield w : F
    //   581: fsub
    //   582: invokestatic abs : (F)F
    //   585: ldc_w 10.0
    //   588: fcmpg
    //   589: ifge -> 705
    //   592: aload_1
    //   593: iconst_0
    //   594: invokevirtual getY : (I)F
    //   597: aload_0
    //   598: getfield y : F
    //   601: fsub
    //   602: invokestatic abs : (F)F
    //   605: ldc_w 10.0
    //   608: fcmpg
    //   609: ifge -> 705
    //   612: aload_0
    //   613: getfield b : Lcom/tencent/mapsdk/rastercore/d/b;
    //   616: invokevirtual d : ()Lcom/tencent/mapsdk/rastercore/b/a;
    //   619: astore #4
    //   621: aload_0
    //   622: getfield a : Lcom/tencent/mapsdk/rastercore/d/e;
    //   625: invokevirtual f : ()Lcom/tencent/mapsdk/rastercore/d/a$1;
    //   628: invokevirtual a : ()I
    //   631: iconst_3
    //   632: if_icmplt -> 655
    //   635: aload_0
    //   636: getfield a : Lcom/tencent/mapsdk/rastercore/d/e;
    //   639: invokevirtual f : ()Lcom/tencent/mapsdk/rastercore/d/a$1;
    //   642: invokevirtual b : ()F
    //   645: fconst_1
    //   646: fcmpl
    //   647: ifle -> 655
    //   650: iconst_1
    //   651: istore_2
    //   652: goto -> 657
    //   655: iconst_0
    //   656: istore_2
    //   657: iload_2
    //   658: ifeq -> 672
    //   661: aload #4
    //   663: ldc2_w 1.3
    //   666: invokevirtual a : (D)V
    //   669: goto -> 678
    //   672: aload #4
    //   674: dconst_1
    //   675: invokevirtual a : (D)V
    //   678: aload_0
    //   679: getfield a : Lcom/tencent/mapsdk/rastercore/d/e;
    //   682: invokevirtual f : ()Lcom/tencent/mapsdk/rastercore/d/a$1;
    //   685: invokevirtual i : ()Z
    //   688: ifeq -> 700
    //   691: aload_0
    //   692: getfield b : Lcom/tencent/mapsdk/rastercore/d/b;
    //   695: iconst_1
    //   696: aconst_null
    //   697: invokevirtual b : (ZLcom/tencent/tencentmap/mapsdk/map/CancelableCallback;)V
    //   700: iconst_1
    //   701: istore_2
    //   702: goto -> 707
    //   705: iconst_0
    //   706: istore_2
    //   707: aload_0
    //   708: lconst_0
    //   709: putfield v : J
    //   712: aload_0
    //   713: fconst_0
    //   714: putfield w : F
    //   717: aload_0
    //   718: fconst_0
    //   719: putfield y : F
    //   722: aload_0
    //   723: iconst_0
    //   724: putfield p : Z
    //   727: aload_0
    //   728: fconst_0
    //   729: putfield o : F
    //   732: goto -> 810
    //   735: new java/lang/StringBuilder
    //   738: dup
    //   739: ldc 'event0.x:'
    //   741: invokespecial <init> : (Ljava/lang/String;)V
    //   744: astore #4
    //   746: aload #4
    //   748: aload_1
    //   749: iconst_0
    //   750: invokevirtual getX : (I)F
    //   753: invokevirtual append : (F)Ljava/lang/StringBuilder;
    //   756: pop
    //   757: aload #4
    //   759: ldc ',event0.y:'
    //   761: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   764: pop
    //   765: aload #4
    //   767: aload_1
    //   768: iconst_0
    //   769: invokevirtual getY : (I)F
    //   772: invokevirtual append : (F)Ljava/lang/StringBuilder;
    //   775: pop
    //   776: aload_0
    //   777: getfield a : Lcom/tencent/mapsdk/rastercore/d/e;
    //   780: invokevirtual d : ()Lcom/tencent/tencentmap/mapsdk/map/MapView;
    //   783: invokevirtual stopAnimation : ()V
    //   786: iload_3
    //   787: istore_2
    //   788: aload_0
    //   789: getfield a : Lcom/tencent/mapsdk/rastercore/d/e;
    //   792: invokevirtual f : ()Lcom/tencent/mapsdk/rastercore/d/a$1;
    //   795: invokevirtual g : ()Z
    //   798: ifeq -> 810
    //   801: aload_0
    //   802: getfield a : Lcom/tencent/mapsdk/rastercore/d/e;
    //   805: invokevirtual j : ()V
    //   808: iload_3
    //   809: istore_2
    //   810: iload_2
    //   811: ifeq -> 822
    //   814: aload_0
    //   815: aload_1
    //   816: invokevirtual getEventTime : ()J
    //   819: putfield A : J
    //   822: iload_2
    //   823: ifne -> 842
    //   826: aload_0
    //   827: getfield q : Z
    //   830: ifne -> 842
    //   833: aload_0
    //   834: getfield d : Landroid/view/GestureDetector;
    //   837: aload_1
    //   838: invokevirtual onTouchEvent : (Landroid/view/MotionEvent;)Z
    //   841: pop
    //   842: aload_1
    //   843: invokevirtual getAction : ()I
    //   846: iconst_1
    //   847: if_icmpne -> 893
    //   850: aload_0
    //   851: getfield q : Z
    //   854: ifne -> 893
    //   857: aload_0
    //   858: getfield h : Lcom/tencent/tencentmap/mapsdk/map/TencentMap$OnMapCameraChangeListener;
    //   861: ifnull -> 893
    //   864: aload_0
    //   865: getfield s : Landroid/widget/Scroller;
    //   868: invokevirtual isFinished : ()Z
    //   871: ifeq -> 893
    //   874: aload_0
    //   875: getfield p : Z
    //   878: ifne -> 888
    //   881: aload_0
    //   882: getfield r : Z
    //   885: ifeq -> 893
    //   888: aload_0
    //   889: iconst_1
    //   890: invokevirtual a : (Z)V
    //   893: iload_2
    //   894: ifeq -> 911
    //   897: aload_1
    //   898: iconst_3
    //   899: invokevirtual setAction : (I)V
    //   902: aload_0
    //   903: getfield d : Landroid/view/GestureDetector;
    //   906: aload_1
    //   907: invokevirtual onTouchEvent : (Landroid/view/MotionEvent;)Z
    //   910: pop
    //   911: aload_0
    //   912: getfield c : Lcom/tencent/mapsdk/rastercore/d/a;
    //   915: aload_1
    //   916: invokevirtual a : (Landroid/view/MotionEvent;)Z
    //   919: pop
    //   920: iconst_1
    //   921: ireturn
  }
  
  public final void b() {
    this.s.abortAnimation();
  }
  
  public final void b(MotionEvent paramMotionEvent) {
    MotionEvent motionEvent = this.e;
    if (motionEvent != null)
      motionEvent.recycle(); 
    this.e = paramMotionEvent;
    if (paramMotionEvent.getAction() == 0) {
      if (this.B == null)
        this.B = new Point(); 
      this.B.x = (int)this.e.getX();
      this.B.y = (int)this.e.getY();
    } 
  }
  
  public final MotionEvent c() {
    return this.e;
  }
  
  public final TencentMap.OnMarkerClickListener d() {
    return this.l;
  }
  
  public final TencentMap.OnMarkerDraggedListener e() {
    return this.k;
  }
  
  public final TencentMap.OnInfoWindowClickListener f() {
    return this.i;
  }
  
  public final TencentMap.InfoWindowAdapter g() {
    return this.j;
  }
  
  public final boolean onDoubleTap(MotionEvent paramMotionEvent) {
    boolean bool;
    double d;
    if (!this.a.f().i())
      return true; 
    a a1 = this.b.d();
    if (this.a.f().a() >= 3 && this.a.f().b() > 1.0F) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool) {
      d = 1.3D;
    } else {
      d = 1.0D;
    } 
    a1.a(d);
    if (this.b.d().a() < this.a.b().i().a())
      this.b.a(new PointF(paramMotionEvent.getX(), paramMotionEvent.getY()), true, (CancelableCallback)null); 
    return true;
  }
  
  public final boolean onDoubleTapEvent(MotionEvent paramMotionEvent) {
    return false;
  }
  
  public final boolean onDown(MotionEvent paramMotionEvent) {
    this.p = false;
    return false;
  }
  
  public final boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
    this.p = false;
    if (!this.a.f().h())
      return true; 
    this.s.fling(this.t, this.u, (int)(-paramFloat1 * 0.6D), (int)(-paramFloat2 * 0.6D), -2147483647, 2147483647, -2147483647, 2147483647);
    this.a.a(false, false);
    return true;
  }
  
  public final boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent) {
    boolean bool = false;
    switch (paramInt) {
      default:
        return bool;
      case 22:
        this.b.scrollBy(10, 0);
        break;
      case 21:
        this.b.scrollBy(-10, 0);
        break;
      case 20:
        this.b.scrollBy(0, 10);
        break;
      case 19:
        this.b.scrollBy(0, -10);
        break;
    } 
    bool = true;
  }
  
  public final void onLongPress(MotionEvent paramMotionEvent) {
    this.p = false;
    LatLng latLng = this.a.b().a((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
    this.c.a(a.a(latLng), paramMotionEvent);
    TencentMap.OnMapLongClickListener onMapLongClickListener = this.g;
    if (onMapLongClickListener != null)
      onMapLongClickListener.onMapLongClick(latLng); 
  }
  
  public final boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
    if (!this.a.f().h()) {
      this.p = false;
      return true;
    } 
    if (this.q)
      return true; 
    if (paramMotionEvent2.getEventTime() - this.A < 30L)
      return true; 
    this.p = true;
    int i = (int)this.e.getX();
    int j = (int)this.e.getY();
    Point point = this.B;
    if (point == null) {
      point = new Point();
      this.B = point;
      point.x = i;
      this.B.y = j;
    } else {
      int k = point.x;
      int m = this.B.y;
      this.b.scrollBy(k - i, m - j);
      this.B.x = i;
      this.B.y = j;
      a(false);
    } 
    return true;
  }
  
  public final void onShowPress(MotionEvent paramMotionEvent) {}
  
  public final boolean onSingleTapConfirmed(MotionEvent paramMotionEvent) {
    return false;
  }
  
  public final boolean onSingleTapUp(MotionEvent paramMotionEvent) {
    this.p = false;
    try {
      LatLng latLng = this.a.b().a((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
      if (this.c.a(a.a(latLng)))
        return true; 
      if (this.f != null) {
        this.f.onMapClick(latLng);
        this.a.d().setFocusable(true);
        this.a.d().setFocusableInTouchMode(true);
        this.a.d().requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager)e.a().getSystemService("input_method");
        if (inputMethodManager.isActive())
          inputMethodManager.hideSoftInputFromWindow(this.a.d().getWindowToken(), 0); 
      } 
      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
      return true;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\d\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */