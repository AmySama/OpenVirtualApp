package android.support.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatorInflaterCompat {
  private static final boolean DBG_ANIMATOR_INFLATER = false;
  
  private static final int MAX_NUM_POINTS = 100;
  
  private static final String TAG = "AnimatorInflater";
  
  private static final int TOGETHER = 0;
  
  private static final int VALUE_TYPE_COLOR = 3;
  
  private static final int VALUE_TYPE_FLOAT = 0;
  
  private static final int VALUE_TYPE_INT = 1;
  
  private static final int VALUE_TYPE_PATH = 2;
  
  private static final int VALUE_TYPE_UNDEFINED = 4;
  
  private static Animator createAnimatorFromXml(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, float paramFloat) throws XmlPullParserException, IOException {
    return createAnimatorFromXml(paramContext, paramResources, paramTheme, paramXmlPullParser, Xml.asAttributeSet(paramXmlPullParser), null, 0, paramFloat);
  }
  
  private static Animator createAnimatorFromXml(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, AnimatorSet paramAnimatorSet, int paramInt, float paramFloat) throws XmlPullParserException, IOException {
    int j;
    int i = paramXmlPullParser.getDepth();
    TypedArray typedArray = null;
    ArrayList<TypedArray> arrayList = null;
    while (true) {
      int k = paramXmlPullParser.next();
      j = 0;
      boolean bool = false;
      if ((k != 3 || paramXmlPullParser.getDepth() > i) && k != 1) {
        ObjectAnimator objectAnimator;
        TypedArray typedArray1;
        if (k != 2)
          continue; 
        String str = paramXmlPullParser.getName();
        if (str.equals("objectAnimator")) {
          objectAnimator = loadObjectAnimator(paramContext, paramResources, paramTheme, paramAttributeSet, paramFloat, paramXmlPullParser);
        } else {
          ValueAnimator valueAnimator;
          if (objectAnimator.equals("animator")) {
            valueAnimator = loadAnimator(paramContext, paramResources, paramTheme, paramAttributeSet, null, paramFloat, paramXmlPullParser);
          } else {
            AnimatorSet animatorSet;
            if (valueAnimator.equals("set")) {
              animatorSet = new AnimatorSet();
              typedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_ANIMATOR_SET);
              j = TypedArrayUtils.getNamedInt(typedArray, paramXmlPullParser, "ordering", 0, 0);
              createAnimatorFromXml(paramContext, paramResources, paramTheme, paramXmlPullParser, paramAttributeSet, animatorSet, j, paramFloat);
              typedArray.recycle();
            } else if (animatorSet.equals("propertyValuesHolder")) {
              PropertyValuesHolder[] arrayOfPropertyValuesHolder = loadValues(paramContext, paramResources, paramTheme, paramXmlPullParser, Xml.asAttributeSet(paramXmlPullParser));
              if (arrayOfPropertyValuesHolder != null && typedArray != null && typedArray instanceof ValueAnimator)
                ((ValueAnimator)typedArray).setValues(arrayOfPropertyValuesHolder); 
              bool = true;
              typedArray1 = typedArray;
            } else {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Unknown animator name: ");
              stringBuilder.append(paramXmlPullParser.getName());
              throw new RuntimeException(stringBuilder.toString());
            } 
          } 
        } 
        typedArray = typedArray1;
        if (paramAnimatorSet != null) {
          typedArray = typedArray1;
          if (!bool) {
            ArrayList<TypedArray> arrayList1 = arrayList;
            if (arrayList == null)
              arrayList1 = new ArrayList(); 
            arrayList1.add(typedArray1);
            typedArray = typedArray1;
            arrayList = arrayList1;
          } 
        } 
        continue;
      } 
      break;
    } 
    if (paramAnimatorSet != null && arrayList != null) {
      Animator[] arrayOfAnimator = new Animator[arrayList.size()];
      Iterator<TypedArray> iterator = arrayList.iterator();
      for (int k = j; iterator.hasNext(); k++)
        arrayOfAnimator[k] = (Animator)iterator.next(); 
      if (paramInt == 0) {
        paramAnimatorSet.playTogether(arrayOfAnimator);
      } else {
        paramAnimatorSet.playSequentially(arrayOfAnimator);
      } 
    } 
    return (Animator)typedArray;
  }
  
  private static Keyframe createNewKeyframe(Keyframe paramKeyframe, float paramFloat) {
    if (paramKeyframe.getType() == float.class) {
      paramKeyframe = Keyframe.ofFloat(paramFloat);
    } else if (paramKeyframe.getType() == int.class) {
      paramKeyframe = Keyframe.ofInt(paramFloat);
    } else {
      paramKeyframe = Keyframe.ofObject(paramFloat);
    } 
    return paramKeyframe;
  }
  
  private static void distributeKeyframes(Keyframe[] paramArrayOfKeyframe, float paramFloat, int paramInt1, int paramInt2) {
    paramFloat /= (paramInt2 - paramInt1 + 2);
    while (paramInt1 <= paramInt2) {
      paramArrayOfKeyframe[paramInt1].setFraction(paramArrayOfKeyframe[paramInt1 - 1].getFraction() + paramFloat);
      paramInt1++;
    } 
  }
  
  private static void dumpKeyframes(Object[] paramArrayOfObject, String paramString) {
    if (paramArrayOfObject != null && paramArrayOfObject.length != 0) {
      Log.d("AnimatorInflater", paramString);
      int i = paramArrayOfObject.length;
      for (byte b = 0; b < i; b++) {
        Float float_;
        Keyframe keyframe = (Keyframe)paramArrayOfObject[b];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Keyframe ");
        stringBuilder.append(b);
        stringBuilder.append(": fraction ");
        float f = keyframe.getFraction();
        String str = "null";
        if (f < 0.0F) {
          paramString = "null";
        } else {
          float_ = Float.valueOf(keyframe.getFraction());
        } 
        stringBuilder.append(float_);
        stringBuilder.append(", ");
        stringBuilder.append(", value : ");
        Object object = str;
        if (keyframe.hasValue())
          object = keyframe.getValue(); 
        stringBuilder.append(object);
        Log.d("AnimatorInflater", stringBuilder.toString());
      } 
    } 
  }
  
  private static PropertyValuesHolder getPVH(TypedArray paramTypedArray, int paramInt1, int paramInt2, int paramInt3, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: iload_2
    //   2: invokevirtual peekValue : (I)Landroid/util/TypedValue;
    //   5: astore #5
    //   7: aload #5
    //   9: ifnull -> 18
    //   12: iconst_1
    //   13: istore #6
    //   15: goto -> 21
    //   18: iconst_0
    //   19: istore #6
    //   21: iload #6
    //   23: ifeq -> 36
    //   26: aload #5
    //   28: getfield type : I
    //   31: istore #7
    //   33: goto -> 39
    //   36: iconst_0
    //   37: istore #7
    //   39: aload_0
    //   40: iload_3
    //   41: invokevirtual peekValue : (I)Landroid/util/TypedValue;
    //   44: astore #5
    //   46: aload #5
    //   48: ifnull -> 57
    //   51: iconst_1
    //   52: istore #8
    //   54: goto -> 60
    //   57: iconst_0
    //   58: istore #8
    //   60: iload #8
    //   62: ifeq -> 75
    //   65: aload #5
    //   67: getfield type : I
    //   70: istore #9
    //   72: goto -> 78
    //   75: iconst_0
    //   76: istore #9
    //   78: iload_1
    //   79: istore #10
    //   81: iload_1
    //   82: iconst_4
    //   83: if_icmpne -> 121
    //   86: iload #6
    //   88: ifeq -> 99
    //   91: iload #7
    //   93: invokestatic isColorType : (I)Z
    //   96: ifne -> 112
    //   99: iload #8
    //   101: ifeq -> 118
    //   104: iload #9
    //   106: invokestatic isColorType : (I)Z
    //   109: ifeq -> 118
    //   112: iconst_3
    //   113: istore #10
    //   115: goto -> 121
    //   118: iconst_0
    //   119: istore #10
    //   121: iload #10
    //   123: ifne -> 131
    //   126: iconst_1
    //   127: istore_1
    //   128: goto -> 133
    //   131: iconst_0
    //   132: istore_1
    //   133: aconst_null
    //   134: astore #5
    //   136: aconst_null
    //   137: astore #11
    //   139: iload #10
    //   141: iconst_2
    //   142: if_icmpne -> 342
    //   145: aload_0
    //   146: iload_2
    //   147: invokevirtual getString : (I)Ljava/lang/String;
    //   150: astore #12
    //   152: aload_0
    //   153: iload_3
    //   154: invokevirtual getString : (I)Ljava/lang/String;
    //   157: astore #11
    //   159: aload #12
    //   161: invokestatic createNodesFromPathData : (Ljava/lang/String;)[Landroid/support/v4/graphics/PathParser$PathDataNode;
    //   164: astore #13
    //   166: aload #11
    //   168: invokestatic createNodesFromPathData : (Ljava/lang/String;)[Landroid/support/v4/graphics/PathParser$PathDataNode;
    //   171: astore #14
    //   173: aload #13
    //   175: ifnonnull -> 186
    //   178: aload #5
    //   180: astore_0
    //   181: aload #14
    //   183: ifnull -> 730
    //   186: aload #13
    //   188: ifnull -> 308
    //   191: new android/support/graphics/drawable/AnimatorInflaterCompat$PathDataEvaluator
    //   194: dup
    //   195: aconst_null
    //   196: invokespecial <init> : (Landroid/support/graphics/drawable/AnimatorInflaterCompat$1;)V
    //   199: astore_0
    //   200: aload #14
    //   202: ifnull -> 289
    //   205: aload #13
    //   207: aload #14
    //   209: invokestatic canMorph : ([Landroid/support/v4/graphics/PathParser$PathDataNode;[Landroid/support/v4/graphics/PathParser$PathDataNode;)Z
    //   212: ifeq -> 239
    //   215: aload #4
    //   217: aload_0
    //   218: iconst_2
    //   219: anewarray java/lang/Object
    //   222: dup
    //   223: iconst_0
    //   224: aload #13
    //   226: aastore
    //   227: dup
    //   228: iconst_1
    //   229: aload #14
    //   231: aastore
    //   232: invokestatic ofObject : (Ljava/lang/String;Landroid/animation/TypeEvaluator;[Ljava/lang/Object;)Landroid/animation/PropertyValuesHolder;
    //   235: astore_0
    //   236: goto -> 305
    //   239: new java/lang/StringBuilder
    //   242: dup
    //   243: invokespecial <init> : ()V
    //   246: astore_0
    //   247: aload_0
    //   248: ldc_w ' Can't morph from '
    //   251: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   254: pop
    //   255: aload_0
    //   256: aload #12
    //   258: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   261: pop
    //   262: aload_0
    //   263: ldc_w ' to '
    //   266: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: pop
    //   270: aload_0
    //   271: aload #11
    //   273: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   276: pop
    //   277: new android/view/InflateException
    //   280: dup
    //   281: aload_0
    //   282: invokevirtual toString : ()Ljava/lang/String;
    //   285: invokespecial <init> : (Ljava/lang/String;)V
    //   288: athrow
    //   289: aload #4
    //   291: aload_0
    //   292: iconst_1
    //   293: anewarray java/lang/Object
    //   296: dup
    //   297: iconst_0
    //   298: aload #13
    //   300: aastore
    //   301: invokestatic ofObject : (Ljava/lang/String;Landroid/animation/TypeEvaluator;[Ljava/lang/Object;)Landroid/animation/PropertyValuesHolder;
    //   304: astore_0
    //   305: goto -> 730
    //   308: aload #5
    //   310: astore_0
    //   311: aload #14
    //   313: ifnull -> 730
    //   316: aload #4
    //   318: new android/support/graphics/drawable/AnimatorInflaterCompat$PathDataEvaluator
    //   321: dup
    //   322: aconst_null
    //   323: invokespecial <init> : (Landroid/support/graphics/drawable/AnimatorInflaterCompat$1;)V
    //   326: iconst_1
    //   327: anewarray java/lang/Object
    //   330: dup
    //   331: iconst_0
    //   332: aload #14
    //   334: aastore
    //   335: invokestatic ofObject : (Ljava/lang/String;Landroid/animation/TypeEvaluator;[Ljava/lang/Object;)Landroid/animation/PropertyValuesHolder;
    //   338: astore_0
    //   339: goto -> 730
    //   342: iload #10
    //   344: iconst_3
    //   345: if_icmpne -> 356
    //   348: invokestatic getInstance : ()Landroid/support/graphics/drawable/ArgbEvaluator;
    //   351: astore #12
    //   353: goto -> 359
    //   356: aconst_null
    //   357: astore #12
    //   359: iload_1
    //   360: ifeq -> 507
    //   363: iload #6
    //   365: ifeq -> 462
    //   368: iload #7
    //   370: iconst_5
    //   371: if_icmpne -> 385
    //   374: aload_0
    //   375: iload_2
    //   376: fconst_0
    //   377: invokevirtual getDimension : (IF)F
    //   380: fstore #15
    //   382: goto -> 393
    //   385: aload_0
    //   386: iload_2
    //   387: fconst_0
    //   388: invokevirtual getFloat : (IF)F
    //   391: fstore #15
    //   393: iload #8
    //   395: ifeq -> 445
    //   398: iload #9
    //   400: iconst_5
    //   401: if_icmpne -> 415
    //   404: aload_0
    //   405: iload_3
    //   406: fconst_0
    //   407: invokevirtual getDimension : (IF)F
    //   410: fstore #16
    //   412: goto -> 423
    //   415: aload_0
    //   416: iload_3
    //   417: fconst_0
    //   418: invokevirtual getFloat : (IF)F
    //   421: fstore #16
    //   423: aload #4
    //   425: iconst_2
    //   426: newarray float
    //   428: dup
    //   429: iconst_0
    //   430: fload #15
    //   432: fastore
    //   433: dup
    //   434: iconst_1
    //   435: fload #16
    //   437: fastore
    //   438: invokestatic ofFloat : (Ljava/lang/String;[F)Landroid/animation/PropertyValuesHolder;
    //   441: astore_0
    //   442: goto -> 501
    //   445: aload #4
    //   447: iconst_1
    //   448: newarray float
    //   450: dup
    //   451: iconst_0
    //   452: fload #15
    //   454: fastore
    //   455: invokestatic ofFloat : (Ljava/lang/String;[F)Landroid/animation/PropertyValuesHolder;
    //   458: astore_0
    //   459: goto -> 501
    //   462: iload #9
    //   464: iconst_5
    //   465: if_icmpne -> 479
    //   468: aload_0
    //   469: iload_3
    //   470: fconst_0
    //   471: invokevirtual getDimension : (IF)F
    //   474: fstore #15
    //   476: goto -> 487
    //   479: aload_0
    //   480: iload_3
    //   481: fconst_0
    //   482: invokevirtual getFloat : (IF)F
    //   485: fstore #15
    //   487: aload #4
    //   489: iconst_1
    //   490: newarray float
    //   492: dup
    //   493: iconst_0
    //   494: fload #15
    //   496: fastore
    //   497: invokestatic ofFloat : (Ljava/lang/String;[F)Landroid/animation/PropertyValuesHolder;
    //   500: astore_0
    //   501: aload_0
    //   502: astore #5
    //   504: goto -> 704
    //   507: iload #6
    //   509: ifeq -> 639
    //   512: iload #7
    //   514: iconst_5
    //   515: if_icmpne -> 529
    //   518: aload_0
    //   519: iload_2
    //   520: fconst_0
    //   521: invokevirtual getDimension : (IF)F
    //   524: f2i
    //   525: istore_1
    //   526: goto -> 554
    //   529: iload #7
    //   531: invokestatic isColorType : (I)Z
    //   534: ifeq -> 547
    //   537: aload_0
    //   538: iload_2
    //   539: iconst_0
    //   540: invokevirtual getColor : (II)I
    //   543: istore_1
    //   544: goto -> 554
    //   547: aload_0
    //   548: iload_2
    //   549: iconst_0
    //   550: invokevirtual getInt : (II)I
    //   553: istore_1
    //   554: iload #8
    //   556: ifeq -> 622
    //   559: iload #9
    //   561: iconst_5
    //   562: if_icmpne -> 576
    //   565: aload_0
    //   566: iload_3
    //   567: fconst_0
    //   568: invokevirtual getDimension : (IF)F
    //   571: f2i
    //   572: istore_2
    //   573: goto -> 601
    //   576: iload #9
    //   578: invokestatic isColorType : (I)Z
    //   581: ifeq -> 594
    //   584: aload_0
    //   585: iload_3
    //   586: iconst_0
    //   587: invokevirtual getColor : (II)I
    //   590: istore_2
    //   591: goto -> 601
    //   594: aload_0
    //   595: iload_3
    //   596: iconst_0
    //   597: invokevirtual getInt : (II)I
    //   600: istore_2
    //   601: aload #4
    //   603: iconst_2
    //   604: newarray int
    //   606: dup
    //   607: iconst_0
    //   608: iload_1
    //   609: iastore
    //   610: dup
    //   611: iconst_1
    //   612: iload_2
    //   613: iastore
    //   614: invokestatic ofInt : (Ljava/lang/String;[I)Landroid/animation/PropertyValuesHolder;
    //   617: astore #5
    //   619: goto -> 704
    //   622: aload #4
    //   624: iconst_1
    //   625: newarray int
    //   627: dup
    //   628: iconst_0
    //   629: iload_1
    //   630: iastore
    //   631: invokestatic ofInt : (Ljava/lang/String;[I)Landroid/animation/PropertyValuesHolder;
    //   634: astore #5
    //   636: goto -> 704
    //   639: aload #11
    //   641: astore #5
    //   643: iload #8
    //   645: ifeq -> 704
    //   648: iload #9
    //   650: iconst_5
    //   651: if_icmpne -> 665
    //   654: aload_0
    //   655: iload_3
    //   656: fconst_0
    //   657: invokevirtual getDimension : (IF)F
    //   660: f2i
    //   661: istore_1
    //   662: goto -> 690
    //   665: iload #9
    //   667: invokestatic isColorType : (I)Z
    //   670: ifeq -> 683
    //   673: aload_0
    //   674: iload_3
    //   675: iconst_0
    //   676: invokevirtual getColor : (II)I
    //   679: istore_1
    //   680: goto -> 690
    //   683: aload_0
    //   684: iload_3
    //   685: iconst_0
    //   686: invokevirtual getInt : (II)I
    //   689: istore_1
    //   690: aload #4
    //   692: iconst_1
    //   693: newarray int
    //   695: dup
    //   696: iconst_0
    //   697: iload_1
    //   698: iastore
    //   699: invokestatic ofInt : (Ljava/lang/String;[I)Landroid/animation/PropertyValuesHolder;
    //   702: astore #5
    //   704: aload #5
    //   706: astore_0
    //   707: aload #5
    //   709: ifnull -> 730
    //   712: aload #5
    //   714: astore_0
    //   715: aload #12
    //   717: ifnull -> 730
    //   720: aload #5
    //   722: aload #12
    //   724: invokevirtual setEvaluator : (Landroid/animation/TypeEvaluator;)V
    //   727: aload #5
    //   729: astore_0
    //   730: aload_0
    //   731: areturn
  }
  
  private static int inferValueTypeFromValues(TypedArray paramTypedArray, int paramInt1, int paramInt2) {
    boolean bool2;
    TypedValue typedValue2 = paramTypedArray.peekValue(paramInt1);
    int i = 1;
    boolean bool1 = false;
    if (typedValue2 != null) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    } 
    if (paramInt1 != 0) {
      bool2 = typedValue2.type;
    } else {
      bool2 = false;
    } 
    TypedValue typedValue1 = paramTypedArray.peekValue(paramInt2);
    if (typedValue1 != null) {
      paramInt2 = i;
    } else {
      paramInt2 = 0;
    } 
    if (paramInt2 != 0) {
      i = typedValue1.type;
    } else {
      i = 0;
    } 
    if (paramInt1 == 0 || !isColorType(bool2)) {
      paramInt1 = bool1;
      if (paramInt2 != 0) {
        paramInt1 = bool1;
        if (isColorType(i))
          return 3; 
      } 
      return paramInt1;
    } 
    return 3;
  }
  
  private static int inferValueTypeOfKeyframe(Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, XmlPullParser paramXmlPullParser) {
    boolean bool;
    TypedArray typedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_KEYFRAME);
    byte b1 = 0;
    TypedValue typedValue = TypedArrayUtils.peekNamedValue(typedArray, paramXmlPullParser, "value", 0);
    if (typedValue != null) {
      bool = true;
    } else {
      bool = false;
    } 
    byte b2 = b1;
    if (bool) {
      b2 = b1;
      if (isColorType(typedValue.type))
        b2 = 3; 
    } 
    typedArray.recycle();
    return b2;
  }
  
  private static boolean isColorType(int paramInt) {
    boolean bool;
    if (paramInt >= 28 && paramInt <= 31) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static Animator loadAnimator(Context paramContext, int paramInt) throws Resources.NotFoundException {
    Animator animator;
    if (Build.VERSION.SDK_INT >= 24) {
      animator = AnimatorInflater.loadAnimator(paramContext, paramInt);
    } else {
      animator = loadAnimator((Context)animator, animator.getResources(), animator.getTheme(), paramInt);
    } 
    return animator;
  }
  
  public static Animator loadAnimator(Context paramContext, Resources paramResources, Resources.Theme paramTheme, int paramInt) throws Resources.NotFoundException {
    return loadAnimator(paramContext, paramResources, paramTheme, paramInt, 1.0F);
  }
  
  public static Animator loadAnimator(Context paramContext, Resources paramResources, Resources.Theme paramTheme, int paramInt, float paramFloat) throws Resources.NotFoundException {
    XmlResourceParser xmlResourceParser1 = null;
    XmlResourceParser xmlResourceParser2 = null;
    XmlResourceParser xmlResourceParser3 = null;
    try {
      XmlResourceParser xmlResourceParser = paramResources.getAnimation(paramInt);
      xmlResourceParser3 = xmlResourceParser;
      xmlResourceParser1 = xmlResourceParser;
      xmlResourceParser2 = xmlResourceParser;
      Animator animator = createAnimatorFromXml(paramContext, paramResources, paramTheme, (XmlPullParser)xmlResourceParser, paramFloat);
      if (xmlResourceParser != null)
        xmlResourceParser.close(); 
      return animator;
    } catch (XmlPullParserException xmlPullParserException) {
      xmlResourceParser3 = xmlResourceParser2;
      Resources.NotFoundException notFoundException = new Resources.NotFoundException();
      xmlResourceParser3 = xmlResourceParser2;
      StringBuilder stringBuilder = new StringBuilder();
      xmlResourceParser3 = xmlResourceParser2;
      this();
      xmlResourceParser3 = xmlResourceParser2;
      stringBuilder.append("Can't load animation resource ID #0x");
      xmlResourceParser3 = xmlResourceParser2;
      stringBuilder.append(Integer.toHexString(paramInt));
      xmlResourceParser3 = xmlResourceParser2;
      this(stringBuilder.toString());
      xmlResourceParser3 = xmlResourceParser2;
      notFoundException.initCause((Throwable)xmlPullParserException);
      xmlResourceParser3 = xmlResourceParser2;
      throw notFoundException;
    } catch (IOException iOException) {
      xmlResourceParser3 = xmlResourceParser1;
      Resources.NotFoundException notFoundException = new Resources.NotFoundException();
      xmlResourceParser3 = xmlResourceParser1;
      StringBuilder stringBuilder = new StringBuilder();
      xmlResourceParser3 = xmlResourceParser1;
      this();
      xmlResourceParser3 = xmlResourceParser1;
      stringBuilder.append("Can't load animation resource ID #0x");
      xmlResourceParser3 = xmlResourceParser1;
      stringBuilder.append(Integer.toHexString(paramInt));
      xmlResourceParser3 = xmlResourceParser1;
      this(stringBuilder.toString());
      xmlResourceParser3 = xmlResourceParser1;
      notFoundException.initCause(iOException);
      xmlResourceParser3 = xmlResourceParser1;
      throw notFoundException;
    } finally {}
    if (xmlResourceParser3 != null)
      xmlResourceParser3.close(); 
    throw paramContext;
  }
  
  private static ValueAnimator loadAnimator(Context paramContext, Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, ValueAnimator paramValueAnimator, float paramFloat, XmlPullParser paramXmlPullParser) throws Resources.NotFoundException {
    TypedArray typedArray2 = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_ANIMATOR);
    TypedArray typedArray1 = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_PROPERTY_ANIMATOR);
    ValueAnimator valueAnimator = paramValueAnimator;
    if (paramValueAnimator == null)
      valueAnimator = new ValueAnimator(); 
    parseAnimatorFromTypeArray(valueAnimator, typedArray2, typedArray1, paramFloat, paramXmlPullParser);
    int i = TypedArrayUtils.getNamedResourceId(typedArray2, paramXmlPullParser, "interpolator", 0, 0);
    if (i > 0)
      valueAnimator.setInterpolator((TimeInterpolator)AnimationUtilsCompat.loadInterpolator(paramContext, i)); 
    typedArray2.recycle();
    if (typedArray1 != null)
      typedArray1.recycle(); 
    return valueAnimator;
  }
  
  private static Keyframe loadKeyframe(Context paramContext, Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, int paramInt, XmlPullParser paramXmlPullParser) throws XmlPullParserException, IOException {
    Keyframe keyframe;
    boolean bool;
    TypedArray typedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_KEYFRAME);
    float f = TypedArrayUtils.getNamedFloat(typedArray, paramXmlPullParser, "fraction", 3, -1.0F);
    TypedValue typedValue = TypedArrayUtils.peekNamedValue(typedArray, paramXmlPullParser, "value", 0);
    if (typedValue != null) {
      bool = true;
    } else {
      bool = false;
    } 
    int i = paramInt;
    if (paramInt == 4)
      if (bool && isColorType(typedValue.type)) {
        i = 3;
      } else {
        i = 0;
      }  
    if (bool) {
      if (i != 0) {
        if (i != 1 && i != 3) {
          typedValue = null;
        } else {
          keyframe = Keyframe.ofInt(f, TypedArrayUtils.getNamedInt(typedArray, paramXmlPullParser, "value", 0, 0));
        } 
      } else {
        keyframe = Keyframe.ofFloat(f, TypedArrayUtils.getNamedFloat(typedArray, paramXmlPullParser, "value", 0, 0.0F));
      } 
    } else if (i == 0) {
      keyframe = Keyframe.ofFloat(f);
    } else {
      keyframe = Keyframe.ofInt(f);
    } 
    paramInt = TypedArrayUtils.getNamedResourceId(typedArray, paramXmlPullParser, "interpolator", 1, 0);
    if (paramInt > 0)
      keyframe.setInterpolator((TimeInterpolator)AnimationUtilsCompat.loadInterpolator(paramContext, paramInt)); 
    typedArray.recycle();
    return keyframe;
  }
  
  private static ObjectAnimator loadObjectAnimator(Context paramContext, Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, float paramFloat, XmlPullParser paramXmlPullParser) throws Resources.NotFoundException {
    ObjectAnimator objectAnimator = new ObjectAnimator();
    loadAnimator(paramContext, paramResources, paramTheme, paramAttributeSet, (ValueAnimator)objectAnimator, paramFloat, paramXmlPullParser);
    return objectAnimator;
  }
  
  private static PropertyValuesHolder loadPvh(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, String paramString, int paramInt) throws XmlPullParserException, IOException {
    PropertyValuesHolder propertyValuesHolder;
    Context context = null;
    ArrayList<Keyframe> arrayList = null;
    int i = paramInt;
    while (true) {
      paramInt = paramXmlPullParser.next();
      if (paramInt != 3 && paramInt != 1) {
        if (paramXmlPullParser.getName().equals("keyframe")) {
          paramInt = i;
          if (i == 4)
            paramInt = inferValueTypeOfKeyframe(paramResources, paramTheme, Xml.asAttributeSet(paramXmlPullParser), paramXmlPullParser); 
          Keyframe keyframe = loadKeyframe(paramContext, paramResources, paramTheme, Xml.asAttributeSet(paramXmlPullParser), paramInt, paramXmlPullParser);
          ArrayList<Keyframe> arrayList1 = arrayList;
          if (keyframe != null) {
            arrayList1 = arrayList;
            if (arrayList == null)
              arrayList1 = new ArrayList(); 
            arrayList1.add(keyframe);
          } 
          paramXmlPullParser.next();
          arrayList = arrayList1;
          i = paramInt;
        } 
        continue;
      } 
      break;
    } 
    paramContext = context;
    if (arrayList != null) {
      int j = arrayList.size();
      paramContext = context;
      if (j > 0) {
        int k = 0;
        Keyframe keyframe1 = arrayList.get(0);
        Keyframe keyframe2 = arrayList.get(j - 1);
        float f = keyframe2.getFraction();
        paramInt = j;
        if (f < 1.0F)
          if (f < 0.0F) {
            keyframe2.setFraction(1.0F);
            paramInt = j;
          } else {
            arrayList.add(arrayList.size(), createNewKeyframe(keyframe2, 1.0F));
            paramInt = j + 1;
          }  
        f = keyframe1.getFraction();
        j = paramInt;
        if (f != 0.0F)
          if (f < 0.0F) {
            keyframe1.setFraction(0.0F);
            j = paramInt;
          } else {
            arrayList.add(0, createNewKeyframe(keyframe1, 0.0F));
            j = paramInt + 1;
          }  
        Keyframe[] arrayOfKeyframe = new Keyframe[j];
        arrayList.toArray(arrayOfKeyframe);
        for (paramInt = k; paramInt < j; paramInt++) {
          keyframe2 = arrayOfKeyframe[paramInt];
          if (keyframe2.getFraction() < 0.0F)
            if (paramInt == 0) {
              keyframe2.setFraction(0.0F);
            } else {
              int m = j - 1;
              if (paramInt == m) {
                keyframe2.setFraction(1.0F);
              } else {
                k = paramInt + 1;
                int n = paramInt;
                while (k < m && arrayOfKeyframe[k].getFraction() < 0.0F) {
                  n = k;
                  k++;
                } 
                distributeKeyframes(arrayOfKeyframe, arrayOfKeyframe[n + 1].getFraction() - arrayOfKeyframe[paramInt - 1].getFraction(), paramInt, n);
              } 
            }  
        } 
        PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofKeyframe(paramString, arrayOfKeyframe);
        propertyValuesHolder = propertyValuesHolder1;
        if (i == 3) {
          propertyValuesHolder1.setEvaluator(ArgbEvaluator.getInstance());
          propertyValuesHolder = propertyValuesHolder1;
        } 
      } 
    } 
    return propertyValuesHolder;
  }
  
  private static PropertyValuesHolder[] loadValues(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet) throws XmlPullParserException, IOException {
    PropertyValuesHolder[] arrayOfPropertyValuesHolder;
    ArrayList<PropertyValuesHolder> arrayList;
    int i;
    Context context = null;
    PropertyValuesHolder propertyValuesHolder = null;
    while (true) {
      int j = paramXmlPullParser.getEventType();
      i = 0;
      if (j != 3 && j != 1) {
        if (j != 2) {
          paramXmlPullParser.next();
          continue;
        } 
        if (paramXmlPullParser.getName().equals("propertyValuesHolder")) {
          ArrayList<PropertyValuesHolder> arrayList1;
          TypedArray typedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_PROPERTY_VALUES_HOLDER);
          String str = TypedArrayUtils.getNamedString(typedArray, paramXmlPullParser, "propertyName", 3);
          i = TypedArrayUtils.getNamedInt(typedArray, paramXmlPullParser, "valueType", 2, 4);
          PropertyValuesHolder propertyValuesHolder1 = loadPvh(paramContext, paramResources, paramTheme, paramXmlPullParser, str, i);
          PropertyValuesHolder propertyValuesHolder2 = propertyValuesHolder1;
          if (propertyValuesHolder1 == null)
            propertyValuesHolder2 = getPVH(typedArray, i, 0, 1, str); 
          propertyValuesHolder1 = propertyValuesHolder;
          if (propertyValuesHolder2 != null) {
            propertyValuesHolder1 = propertyValuesHolder;
            if (propertyValuesHolder == null)
              arrayList1 = new ArrayList(); 
            arrayList1.add(propertyValuesHolder2);
          } 
          typedArray.recycle();
          arrayList = arrayList1;
        } 
        paramXmlPullParser.next();
        continue;
      } 
      break;
    } 
    paramContext = context;
    if (arrayList != null) {
      int j = arrayList.size();
      PropertyValuesHolder[] arrayOfPropertyValuesHolder1 = new PropertyValuesHolder[j];
      while (true) {
        arrayOfPropertyValuesHolder = arrayOfPropertyValuesHolder1;
        if (i < j) {
          arrayOfPropertyValuesHolder1[i] = arrayList.get(i);
          i++;
          continue;
        } 
        break;
      } 
    } 
    return arrayOfPropertyValuesHolder;
  }
  
  private static void parseAnimatorFromTypeArray(ValueAnimator paramValueAnimator, TypedArray paramTypedArray1, TypedArray paramTypedArray2, float paramFloat, XmlPullParser paramXmlPullParser) {
    long l1 = TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "duration", 1, 300);
    long l2 = TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "startOffset", 2, 0);
    int i = TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "valueType", 7, 4);
    int j = i;
    if (TypedArrayUtils.hasAttribute(paramXmlPullParser, "valueFrom")) {
      j = i;
      if (TypedArrayUtils.hasAttribute(paramXmlPullParser, "valueTo")) {
        int k = i;
        if (i == 4)
          k = inferValueTypeFromValues(paramTypedArray1, 5, 6); 
        PropertyValuesHolder propertyValuesHolder = getPVH(paramTypedArray1, k, 5, 6, "");
        j = k;
        if (propertyValuesHolder != null) {
          paramValueAnimator.setValues(new PropertyValuesHolder[] { propertyValuesHolder });
          j = k;
        } 
      } 
    } 
    paramValueAnimator.setDuration(l1);
    paramValueAnimator.setStartDelay(l2);
    paramValueAnimator.setRepeatCount(TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "repeatCount", 3, 0));
    paramValueAnimator.setRepeatMode(TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "repeatMode", 4, 1));
    if (paramTypedArray2 != null)
      setupObjectAnimator(paramValueAnimator, paramTypedArray2, j, paramFloat, paramXmlPullParser); 
  }
  
  private static void setupObjectAnimator(ValueAnimator paramValueAnimator, TypedArray paramTypedArray, int paramInt, float paramFloat, XmlPullParser paramXmlPullParser) {
    // Byte code:
    //   0: aload_0
    //   1: checkcast android/animation/ObjectAnimator
    //   4: astore #5
    //   6: aload_1
    //   7: aload #4
    //   9: ldc_w 'pathData'
    //   12: iconst_1
    //   13: invokestatic getNamedString : (Landroid/content/res/TypedArray;Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;I)Ljava/lang/String;
    //   16: astore #6
    //   18: aload #6
    //   20: ifnull -> 121
    //   23: aload_1
    //   24: aload #4
    //   26: ldc_w 'propertyXName'
    //   29: iconst_2
    //   30: invokestatic getNamedString : (Landroid/content/res/TypedArray;Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;I)Ljava/lang/String;
    //   33: astore_0
    //   34: aload_1
    //   35: aload #4
    //   37: ldc_w 'propertyYName'
    //   40: iconst_3
    //   41: invokestatic getNamedString : (Landroid/content/res/TypedArray;Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;I)Ljava/lang/String;
    //   44: astore #4
    //   46: iload_2
    //   47: iconst_2
    //   48: if_icmpeq -> 51
    //   51: aload_0
    //   52: ifnonnull -> 100
    //   55: aload #4
    //   57: ifnull -> 63
    //   60: goto -> 100
    //   63: new java/lang/StringBuilder
    //   66: dup
    //   67: invokespecial <init> : ()V
    //   70: astore_0
    //   71: aload_0
    //   72: aload_1
    //   73: invokevirtual getPositionDescription : ()Ljava/lang/String;
    //   76: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: pop
    //   80: aload_0
    //   81: ldc_w ' propertyXName or propertyYName is needed for PathData'
    //   84: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: pop
    //   88: new android/view/InflateException
    //   91: dup
    //   92: aload_0
    //   93: invokevirtual toString : ()Ljava/lang/String;
    //   96: invokespecial <init> : (Ljava/lang/String;)V
    //   99: athrow
    //   100: aload #6
    //   102: invokestatic createPathFromPathData : (Ljava/lang/String;)Landroid/graphics/Path;
    //   105: aload #5
    //   107: fload_3
    //   108: ldc_w 0.5
    //   111: fmul
    //   112: aload_0
    //   113: aload #4
    //   115: invokestatic setupPathMotion : (Landroid/graphics/Path;Landroid/animation/ObjectAnimator;FLjava/lang/String;Ljava/lang/String;)V
    //   118: goto -> 136
    //   121: aload #5
    //   123: aload_1
    //   124: aload #4
    //   126: ldc_w 'propertyName'
    //   129: iconst_0
    //   130: invokestatic getNamedString : (Landroid/content/res/TypedArray;Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;I)Ljava/lang/String;
    //   133: invokevirtual setPropertyName : (Ljava/lang/String;)V
    //   136: return
  }
  
  private static void setupPathMotion(Path paramPath, ObjectAnimator paramObjectAnimator, float paramFloat, String paramString1, String paramString2) {
    PathMeasure pathMeasure = new PathMeasure(paramPath, false);
    ArrayList<Float> arrayList = new ArrayList();
    float f1 = 0.0F;
    arrayList.add(Float.valueOf(0.0F));
    float f2 = 0.0F;
    while (true) {
      float f = f2 + pathMeasure.getLength();
      arrayList.add(Float.valueOf(f));
      f2 = f;
      if (!pathMeasure.nextContour()) {
        PathMeasure pathMeasure1 = new PathMeasure(paramPath, false);
        int i = Math.min(100, (int)(f / paramFloat) + 1);
        float[] arrayOfFloat2 = new float[i];
        float[] arrayOfFloat3 = new float[i];
        float[] arrayOfFloat1 = new float[2];
        f /= (i - 1);
        byte b = 0;
        int j = 0;
        paramFloat = f1;
        while (true) {
          PropertyValuesHolder propertyValuesHolder;
          pathMeasure = null;
          if (b < i) {
            pathMeasure1.getPosTan(paramFloat, arrayOfFloat1, null);
            arrayOfFloat2[b] = arrayOfFloat1[0];
            arrayOfFloat3[b] = arrayOfFloat1[1];
            f2 = paramFloat + f;
            int k = j + 1;
            paramFloat = f2;
            int m = j;
            if (k < arrayList.size()) {
              paramFloat = f2;
              m = j;
              if (f2 > ((Float)arrayList.get(k)).floatValue()) {
                paramFloat = f2 - ((Float)arrayList.get(k)).floatValue();
                pathMeasure1.nextContour();
                m = k;
              } 
            } 
            b++;
            j = m;
            continue;
          } 
          if (paramString1 != null) {
            PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat(paramString1, arrayOfFloat2);
          } else {
            arrayOfFloat1 = null;
          } 
          PathMeasure pathMeasure2 = pathMeasure;
          if (paramString2 != null)
            propertyValuesHolder = PropertyValuesHolder.ofFloat(paramString2, arrayOfFloat3); 
          if (arrayOfFloat1 == null) {
            paramObjectAnimator.setValues(new PropertyValuesHolder[] { propertyValuesHolder });
          } else if (propertyValuesHolder == null) {
            paramObjectAnimator.setValues(new PropertyValuesHolder[] { (PropertyValuesHolder)arrayOfFloat1 });
          } else {
            paramObjectAnimator.setValues(new PropertyValuesHolder[] { (PropertyValuesHolder)arrayOfFloat1, propertyValuesHolder });
          } 
          return;
        } 
        break;
      } 
    } 
  }
  
  private static class PathDataEvaluator implements TypeEvaluator<PathParser.PathDataNode[]> {
    private PathParser.PathDataNode[] mNodeArray;
    
    private PathDataEvaluator() {}
    
    PathDataEvaluator(PathParser.PathDataNode[] param1ArrayOfPathDataNode) {
      this.mNodeArray = param1ArrayOfPathDataNode;
    }
    
    public PathParser.PathDataNode[] evaluate(float param1Float, PathParser.PathDataNode[] param1ArrayOfPathDataNode1, PathParser.PathDataNode[] param1ArrayOfPathDataNode2) {
      if (PathParser.canMorph(param1ArrayOfPathDataNode1, param1ArrayOfPathDataNode2)) {
        PathParser.PathDataNode[] arrayOfPathDataNode = this.mNodeArray;
        if (arrayOfPathDataNode == null || !PathParser.canMorph(arrayOfPathDataNode, param1ArrayOfPathDataNode1))
          this.mNodeArray = PathParser.deepCopyNodes(param1ArrayOfPathDataNode1); 
        for (byte b = 0; b < param1ArrayOfPathDataNode1.length; b++)
          this.mNodeArray[b].interpolatePathDataNode(param1ArrayOfPathDataNode1[b], param1ArrayOfPathDataNode2[b], param1Float); 
        return this.mNodeArray;
      } 
      throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\graphics\drawable\AnimatorInflaterCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */