package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

public class ActionMenuView extends LinearLayoutCompat implements MenuBuilder.ItemInvoker, MenuView {
  static final int GENERATED_ITEM_PADDING = 4;
  
  static final int MIN_CELL_SIZE = 56;
  
  private static final String TAG = "ActionMenuView";
  
  private MenuPresenter.Callback mActionMenuPresenterCallback;
  
  private boolean mFormatItems;
  
  private int mFormatItemsWidth;
  
  private int mGeneratedItemPadding;
  
  private MenuBuilder mMenu;
  
  MenuBuilder.Callback mMenuBuilderCallback;
  
  private int mMinCellSize;
  
  OnMenuItemClickListener mOnMenuItemClickListener;
  
  private Context mPopupContext;
  
  private int mPopupTheme;
  
  private ActionMenuPresenter mPresenter;
  
  private boolean mReserveOverflow;
  
  public ActionMenuView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public ActionMenuView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    setBaselineAligned(false);
    float f = (paramContext.getResources().getDisplayMetrics()).density;
    this.mMinCellSize = (int)(56.0F * f);
    this.mGeneratedItemPadding = (int)(f * 4.0F);
    this.mPopupContext = paramContext;
    this.mPopupTheme = 0;
  }
  
  static int measureChildForCells(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    ActionMenuItemView actionMenuItemView;
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt3) - paramInt4, View.MeasureSpec.getMode(paramInt3));
    if (paramView instanceof ActionMenuItemView) {
      actionMenuItemView = (ActionMenuItemView)paramView;
    } else {
      actionMenuItemView = null;
    } 
    boolean bool = true;
    if (actionMenuItemView != null && actionMenuItemView.hasText()) {
      paramInt3 = 1;
    } else {
      paramInt3 = 0;
    } 
    paramInt4 = 2;
    if (paramInt2 > 0 && (paramInt3 == 0 || paramInt2 >= 2)) {
      paramView.measure(View.MeasureSpec.makeMeasureSpec(paramInt2 * paramInt1, -2147483648), i);
      int j = paramView.getMeasuredWidth();
      int k = j / paramInt1;
      paramInt2 = k;
      if (j % paramInt1 != 0)
        paramInt2 = k + 1; 
      if (paramInt3 != 0 && paramInt2 < 2)
        paramInt2 = paramInt4; 
    } else {
      paramInt2 = 0;
    } 
    if (layoutParams.isOverflowButton || paramInt3 == 0)
      bool = false; 
    layoutParams.expandable = bool;
    layoutParams.cellsUsed = paramInt2;
    paramView.measure(View.MeasureSpec.makeMeasureSpec(paramInt1 * paramInt2, 1073741824), i);
    return paramInt2;
  }
  
  private void onMeasureExactFormat(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: iload_2
    //   1: invokestatic getMode : (I)I
    //   4: istore_3
    //   5: iload_1
    //   6: invokestatic getSize : (I)I
    //   9: istore_1
    //   10: iload_2
    //   11: invokestatic getSize : (I)I
    //   14: istore #4
    //   16: aload_0
    //   17: invokevirtual getPaddingLeft : ()I
    //   20: istore #5
    //   22: aload_0
    //   23: invokevirtual getPaddingRight : ()I
    //   26: istore #6
    //   28: aload_0
    //   29: invokevirtual getPaddingTop : ()I
    //   32: aload_0
    //   33: invokevirtual getPaddingBottom : ()I
    //   36: iadd
    //   37: istore #7
    //   39: iload_2
    //   40: iload #7
    //   42: bipush #-2
    //   44: invokestatic getChildMeasureSpec : (III)I
    //   47: istore #8
    //   49: iload_1
    //   50: iload #5
    //   52: iload #6
    //   54: iadd
    //   55: isub
    //   56: istore #9
    //   58: aload_0
    //   59: getfield mMinCellSize : I
    //   62: istore_2
    //   63: iload #9
    //   65: iload_2
    //   66: idiv
    //   67: istore_1
    //   68: iload_1
    //   69: ifne -> 80
    //   72: aload_0
    //   73: iload #9
    //   75: iconst_0
    //   76: invokevirtual setMeasuredDimension : (II)V
    //   79: return
    //   80: iload_2
    //   81: iload #9
    //   83: iload_2
    //   84: irem
    //   85: iload_1
    //   86: idiv
    //   87: iadd
    //   88: istore #10
    //   90: aload_0
    //   91: invokevirtual getChildCount : ()I
    //   94: istore #11
    //   96: iconst_0
    //   97: istore_2
    //   98: iconst_0
    //   99: istore #12
    //   101: iconst_0
    //   102: istore #6
    //   104: iconst_0
    //   105: istore #13
    //   107: iconst_0
    //   108: istore #14
    //   110: iconst_0
    //   111: istore #15
    //   113: lconst_0
    //   114: lstore #16
    //   116: iload #12
    //   118: iload #11
    //   120: if_icmpge -> 375
    //   123: aload_0
    //   124: iload #12
    //   126: invokevirtual getChildAt : (I)Landroid/view/View;
    //   129: astore #18
    //   131: aload #18
    //   133: invokevirtual getVisibility : ()I
    //   136: bipush #8
    //   138: if_icmpne -> 148
    //   141: iload #15
    //   143: istore #5
    //   145: goto -> 365
    //   148: aload #18
    //   150: instanceof android/support/v7/view/menu/ActionMenuItemView
    //   153: istore #19
    //   155: iinc #13, 1
    //   158: iload #19
    //   160: ifeq -> 183
    //   163: aload_0
    //   164: getfield mGeneratedItemPadding : I
    //   167: istore #5
    //   169: aload #18
    //   171: iload #5
    //   173: iconst_0
    //   174: iload #5
    //   176: iconst_0
    //   177: invokevirtual setPadding : (IIII)V
    //   180: goto -> 183
    //   183: aload #18
    //   185: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   188: checkcast android/support/v7/widget/ActionMenuView$LayoutParams
    //   191: astore #20
    //   193: aload #20
    //   195: iconst_0
    //   196: putfield expanded : Z
    //   199: aload #20
    //   201: iconst_0
    //   202: putfield extraPixels : I
    //   205: aload #20
    //   207: iconst_0
    //   208: putfield cellsUsed : I
    //   211: aload #20
    //   213: iconst_0
    //   214: putfield expandable : Z
    //   217: aload #20
    //   219: iconst_0
    //   220: putfield leftMargin : I
    //   223: aload #20
    //   225: iconst_0
    //   226: putfield rightMargin : I
    //   229: iload #19
    //   231: ifeq -> 251
    //   234: aload #18
    //   236: checkcast android/support/v7/view/menu/ActionMenuItemView
    //   239: invokevirtual hasText : ()Z
    //   242: ifeq -> 251
    //   245: iconst_1
    //   246: istore #19
    //   248: goto -> 254
    //   251: iconst_0
    //   252: istore #19
    //   254: aload #20
    //   256: iload #19
    //   258: putfield preventEdgeOffset : Z
    //   261: aload #20
    //   263: getfield isOverflowButton : Z
    //   266: ifeq -> 275
    //   269: iconst_1
    //   270: istore #5
    //   272: goto -> 278
    //   275: iload_1
    //   276: istore #5
    //   278: aload #18
    //   280: iload #10
    //   282: iload #5
    //   284: iload #8
    //   286: iload #7
    //   288: invokestatic measureChildForCells : (Landroid/view/View;IIII)I
    //   291: istore #21
    //   293: iload #14
    //   295: iload #21
    //   297: invokestatic max : (II)I
    //   300: istore #14
    //   302: iload #15
    //   304: istore #5
    //   306: aload #20
    //   308: getfield expandable : Z
    //   311: ifeq -> 320
    //   314: iload #15
    //   316: iconst_1
    //   317: iadd
    //   318: istore #5
    //   320: aload #20
    //   322: getfield isOverflowButton : Z
    //   325: ifeq -> 331
    //   328: iconst_1
    //   329: istore #6
    //   331: iload_1
    //   332: iload #21
    //   334: isub
    //   335: istore_1
    //   336: iload_2
    //   337: aload #18
    //   339: invokevirtual getMeasuredHeight : ()I
    //   342: invokestatic max : (II)I
    //   345: istore_2
    //   346: iload #21
    //   348: iconst_1
    //   349: if_icmpne -> 365
    //   352: lload #16
    //   354: iconst_1
    //   355: iload #12
    //   357: ishl
    //   358: i2l
    //   359: lor
    //   360: lstore #16
    //   362: goto -> 365
    //   365: iinc #12, 1
    //   368: iload #5
    //   370: istore #15
    //   372: goto -> 116
    //   375: iload #6
    //   377: ifeq -> 392
    //   380: iload #13
    //   382: iconst_2
    //   383: if_icmpne -> 392
    //   386: iconst_1
    //   387: istore #12
    //   389: goto -> 395
    //   392: iconst_0
    //   393: istore #12
    //   395: iconst_0
    //   396: istore #5
    //   398: iload_1
    //   399: istore #7
    //   401: iload #12
    //   403: istore #21
    //   405: iload #9
    //   407: istore #12
    //   409: iload #15
    //   411: ifle -> 725
    //   414: iload #7
    //   416: ifle -> 725
    //   419: iconst_0
    //   420: istore #22
    //   422: iconst_0
    //   423: istore #23
    //   425: ldc 2147483647
    //   427: istore #9
    //   429: lconst_0
    //   430: lstore #24
    //   432: iload #23
    //   434: iload #11
    //   436: if_icmpge -> 559
    //   439: aload_0
    //   440: iload #23
    //   442: invokevirtual getChildAt : (I)Landroid/view/View;
    //   445: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   448: checkcast android/support/v7/widget/ActionMenuView$LayoutParams
    //   451: astore #20
    //   453: aload #20
    //   455: getfield expandable : Z
    //   458: ifne -> 475
    //   461: iload #22
    //   463: istore_1
    //   464: iload #9
    //   466: istore #26
    //   468: lload #24
    //   470: lstore #27
    //   472: goto -> 542
    //   475: aload #20
    //   477: getfield cellsUsed : I
    //   480: iload #9
    //   482: if_icmpge -> 503
    //   485: aload #20
    //   487: getfield cellsUsed : I
    //   490: istore #26
    //   492: lconst_1
    //   493: iload #23
    //   495: lshl
    //   496: lstore #27
    //   498: iconst_1
    //   499: istore_1
    //   500: goto -> 542
    //   503: iload #22
    //   505: istore_1
    //   506: iload #9
    //   508: istore #26
    //   510: lload #24
    //   512: lstore #27
    //   514: aload #20
    //   516: getfield cellsUsed : I
    //   519: iload #9
    //   521: if_icmpne -> 542
    //   524: iload #22
    //   526: iconst_1
    //   527: iadd
    //   528: istore_1
    //   529: lload #24
    //   531: lconst_1
    //   532: iload #23
    //   534: lshl
    //   535: lor
    //   536: lstore #27
    //   538: iload #9
    //   540: istore #26
    //   542: iinc #23, 1
    //   545: iload_1
    //   546: istore #22
    //   548: iload #26
    //   550: istore #9
    //   552: lload #27
    //   554: lstore #24
    //   556: goto -> 432
    //   559: iload #5
    //   561: istore_1
    //   562: lload #16
    //   564: lload #24
    //   566: lor
    //   567: lstore #16
    //   569: iload #22
    //   571: iload #7
    //   573: if_icmple -> 579
    //   576: goto -> 728
    //   579: iconst_0
    //   580: istore_1
    //   581: iload_1
    //   582: iload #11
    //   584: if_icmpge -> 719
    //   587: aload_0
    //   588: iload_1
    //   589: invokevirtual getChildAt : (I)Landroid/view/View;
    //   592: astore #20
    //   594: aload #20
    //   596: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   599: checkcast android/support/v7/widget/ActionMenuView$LayoutParams
    //   602: astore #18
    //   604: iconst_1
    //   605: iload_1
    //   606: ishl
    //   607: i2l
    //   608: lstore #29
    //   610: lload #24
    //   612: lload #29
    //   614: land
    //   615: lconst_0
    //   616: lcmp
    //   617: ifne -> 650
    //   620: lload #16
    //   622: lstore #27
    //   624: aload #18
    //   626: getfield cellsUsed : I
    //   629: iload #9
    //   631: iconst_1
    //   632: iadd
    //   633: if_icmpne -> 643
    //   636: lload #16
    //   638: lload #29
    //   640: lor
    //   641: lstore #27
    //   643: lload #27
    //   645: lstore #16
    //   647: goto -> 713
    //   650: iload #21
    //   652: ifeq -> 692
    //   655: aload #18
    //   657: getfield preventEdgeOffset : Z
    //   660: ifeq -> 692
    //   663: iload #7
    //   665: iconst_1
    //   666: if_icmpne -> 692
    //   669: aload_0
    //   670: getfield mGeneratedItemPadding : I
    //   673: istore #5
    //   675: aload #20
    //   677: iload #5
    //   679: iload #10
    //   681: iadd
    //   682: iconst_0
    //   683: iload #5
    //   685: iconst_0
    //   686: invokevirtual setPadding : (IIII)V
    //   689: goto -> 692
    //   692: aload #18
    //   694: aload #18
    //   696: getfield cellsUsed : I
    //   699: iconst_1
    //   700: iadd
    //   701: putfield cellsUsed : I
    //   704: aload #18
    //   706: iconst_1
    //   707: putfield expanded : Z
    //   710: iinc #7, -1
    //   713: iinc #1, 1
    //   716: goto -> 581
    //   719: iconst_1
    //   720: istore #5
    //   722: goto -> 409
    //   725: iload #5
    //   727: istore_1
    //   728: iload #6
    //   730: ifne -> 745
    //   733: iload #13
    //   735: iconst_1
    //   736: if_icmpne -> 745
    //   739: iconst_1
    //   740: istore #5
    //   742: goto -> 748
    //   745: iconst_0
    //   746: istore #5
    //   748: iload #7
    //   750: ifle -> 1098
    //   753: lload #16
    //   755: lconst_0
    //   756: lcmp
    //   757: ifeq -> 1098
    //   760: iload #7
    //   762: iload #13
    //   764: iconst_1
    //   765: isub
    //   766: if_icmplt -> 780
    //   769: iload #5
    //   771: ifne -> 780
    //   774: iload #14
    //   776: iconst_1
    //   777: if_icmple -> 1098
    //   780: lload #16
    //   782: invokestatic bitCount : (J)I
    //   785: i2f
    //   786: fstore #31
    //   788: iload #5
    //   790: ifne -> 889
    //   793: fload #31
    //   795: fstore #32
    //   797: lload #16
    //   799: lconst_1
    //   800: land
    //   801: lconst_0
    //   802: lcmp
    //   803: ifeq -> 834
    //   806: fload #31
    //   808: fstore #32
    //   810: aload_0
    //   811: iconst_0
    //   812: invokevirtual getChildAt : (I)Landroid/view/View;
    //   815: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   818: checkcast android/support/v7/widget/ActionMenuView$LayoutParams
    //   821: getfield preventEdgeOffset : Z
    //   824: ifne -> 834
    //   827: fload #31
    //   829: ldc 0.5
    //   831: fsub
    //   832: fstore #32
    //   834: iload #11
    //   836: iconst_1
    //   837: isub
    //   838: istore #5
    //   840: fload #32
    //   842: fstore #31
    //   844: lload #16
    //   846: iconst_1
    //   847: iload #5
    //   849: ishl
    //   850: i2l
    //   851: land
    //   852: lconst_0
    //   853: lcmp
    //   854: ifeq -> 889
    //   857: fload #32
    //   859: fstore #31
    //   861: aload_0
    //   862: iload #5
    //   864: invokevirtual getChildAt : (I)Landroid/view/View;
    //   867: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   870: checkcast android/support/v7/widget/ActionMenuView$LayoutParams
    //   873: getfield preventEdgeOffset : Z
    //   876: ifne -> 889
    //   879: fload #32
    //   881: ldc 0.5
    //   883: fsub
    //   884: fstore #31
    //   886: goto -> 889
    //   889: fload #31
    //   891: fconst_0
    //   892: fcmpl
    //   893: ifle -> 911
    //   896: iload #7
    //   898: iload #10
    //   900: imul
    //   901: i2f
    //   902: fload #31
    //   904: fdiv
    //   905: f2i
    //   906: istore #5
    //   908: goto -> 914
    //   911: iconst_0
    //   912: istore #5
    //   914: iconst_0
    //   915: istore #6
    //   917: iload_1
    //   918: istore #15
    //   920: iload #6
    //   922: iload #11
    //   924: if_icmpge -> 1101
    //   927: lload #16
    //   929: iconst_1
    //   930: iload #6
    //   932: ishl
    //   933: i2l
    //   934: land
    //   935: lconst_0
    //   936: lcmp
    //   937: ifne -> 946
    //   940: iload_1
    //   941: istore #15
    //   943: goto -> 1089
    //   946: aload_0
    //   947: iload #6
    //   949: invokevirtual getChildAt : (I)Landroid/view/View;
    //   952: astore #20
    //   954: aload #20
    //   956: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   959: checkcast android/support/v7/widget/ActionMenuView$LayoutParams
    //   962: astore #18
    //   964: aload #20
    //   966: instanceof android/support/v7/view/menu/ActionMenuItemView
    //   969: ifeq -> 1014
    //   972: aload #18
    //   974: iload #5
    //   976: putfield extraPixels : I
    //   979: aload #18
    //   981: iconst_1
    //   982: putfield expanded : Z
    //   985: iload #6
    //   987: ifne -> 1011
    //   990: aload #18
    //   992: getfield preventEdgeOffset : Z
    //   995: ifne -> 1011
    //   998: aload #18
    //   1000: iload #5
    //   1002: ineg
    //   1003: iconst_2
    //   1004: idiv
    //   1005: putfield leftMargin : I
    //   1008: goto -> 1011
    //   1011: goto -> 1045
    //   1014: aload #18
    //   1016: getfield isOverflowButton : Z
    //   1019: ifeq -> 1051
    //   1022: aload #18
    //   1024: iload #5
    //   1026: putfield extraPixels : I
    //   1029: aload #18
    //   1031: iconst_1
    //   1032: putfield expanded : Z
    //   1035: aload #18
    //   1037: iload #5
    //   1039: ineg
    //   1040: iconst_2
    //   1041: idiv
    //   1042: putfield rightMargin : I
    //   1045: iconst_1
    //   1046: istore #15
    //   1048: goto -> 1089
    //   1051: iload #6
    //   1053: ifeq -> 1065
    //   1056: aload #18
    //   1058: iload #5
    //   1060: iconst_2
    //   1061: idiv
    //   1062: putfield leftMargin : I
    //   1065: iload_1
    //   1066: istore #15
    //   1068: iload #6
    //   1070: iload #11
    //   1072: iconst_1
    //   1073: isub
    //   1074: if_icmpeq -> 1089
    //   1077: aload #18
    //   1079: iload #5
    //   1081: iconst_2
    //   1082: idiv
    //   1083: putfield rightMargin : I
    //   1086: iload_1
    //   1087: istore #15
    //   1089: iinc #6, 1
    //   1092: iload #15
    //   1094: istore_1
    //   1095: goto -> 917
    //   1098: iload_1
    //   1099: istore #15
    //   1101: iload #15
    //   1103: ifeq -> 1174
    //   1106: iconst_0
    //   1107: istore_1
    //   1108: iload_1
    //   1109: iload #11
    //   1111: if_icmpge -> 1174
    //   1114: aload_0
    //   1115: iload_1
    //   1116: invokevirtual getChildAt : (I)Landroid/view/View;
    //   1119: astore #20
    //   1121: aload #20
    //   1123: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   1126: checkcast android/support/v7/widget/ActionMenuView$LayoutParams
    //   1129: astore #18
    //   1131: aload #18
    //   1133: getfield expanded : Z
    //   1136: ifne -> 1142
    //   1139: goto -> 1168
    //   1142: aload #20
    //   1144: aload #18
    //   1146: getfield cellsUsed : I
    //   1149: iload #10
    //   1151: imul
    //   1152: aload #18
    //   1154: getfield extraPixels : I
    //   1157: iadd
    //   1158: ldc 1073741824
    //   1160: invokestatic makeMeasureSpec : (II)I
    //   1163: iload #8
    //   1165: invokevirtual measure : (II)V
    //   1168: iinc #1, 1
    //   1171: goto -> 1108
    //   1174: iload_3
    //   1175: ldc 1073741824
    //   1177: if_icmpeq -> 1183
    //   1180: goto -> 1186
    //   1183: iload #4
    //   1185: istore_2
    //   1186: aload_0
    //   1187: iload #12
    //   1189: iload_2
    //   1190: invokevirtual setMeasuredDimension : (II)V
    //   1193: return
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    boolean bool;
    if (paramLayoutParams != null && paramLayoutParams instanceof LayoutParams) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void dismissPopupMenus() {
    ActionMenuPresenter actionMenuPresenter = this.mPresenter;
    if (actionMenuPresenter != null)
      actionMenuPresenter.dismissPopupMenus(); 
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {
    return false;
  }
  
  protected LayoutParams generateDefaultLayoutParams() {
    LayoutParams layoutParams = new LayoutParams(-2, -2);
    layoutParams.gravity = 16;
    return layoutParams;
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    if (paramLayoutParams != null) {
      LayoutParams layoutParams;
      if (paramLayoutParams instanceof LayoutParams) {
        layoutParams = new LayoutParams((LayoutParams)paramLayoutParams);
      } else {
        layoutParams = new LayoutParams((ViewGroup.LayoutParams)layoutParams);
      } 
      if (layoutParams.gravity <= 0)
        layoutParams.gravity = 16; 
      return layoutParams;
    } 
    return generateDefaultLayoutParams();
  }
  
  public LayoutParams generateOverflowButtonLayoutParams() {
    LayoutParams layoutParams = generateDefaultLayoutParams();
    layoutParams.isOverflowButton = true;
    return layoutParams;
  }
  
  public Menu getMenu() {
    if (this.mMenu == null) {
      Context context = getContext();
      MenuBuilder menuBuilder = new MenuBuilder(context);
      this.mMenu = menuBuilder;
      menuBuilder.setCallback(new MenuBuilderCallback());
      ActionMenuPresenter actionMenuPresenter2 = new ActionMenuPresenter(context);
      this.mPresenter = actionMenuPresenter2;
      actionMenuPresenter2.setReserveOverflow(true);
      ActionMenuPresenter actionMenuPresenter1 = this.mPresenter;
      MenuPresenter.Callback callback = this.mActionMenuPresenterCallback;
      if (callback == null)
        callback = new ActionMenuPresenterCallback(); 
      actionMenuPresenter1.setCallback(callback);
      this.mMenu.addMenuPresenter((MenuPresenter)this.mPresenter, this.mPopupContext);
      this.mPresenter.setMenuView(this);
    } 
    return (Menu)this.mMenu;
  }
  
  public Drawable getOverflowIcon() {
    getMenu();
    return this.mPresenter.getOverflowIcon();
  }
  
  public int getPopupTheme() {
    return this.mPopupTheme;
  }
  
  public int getWindowAnimations() {
    return 0;
  }
  
  protected boolean hasSupportDividerBeforeChildAt(int paramInt) {
    boolean bool;
    int i = 0;
    if (paramInt == 0)
      return false; 
    View view1 = getChildAt(paramInt - 1);
    View view2 = getChildAt(paramInt);
    int j = i;
    if (paramInt < getChildCount()) {
      j = i;
      if (view1 instanceof ActionMenuChildView)
        j = false | ((ActionMenuChildView)view1).needsDividerAfter(); 
    } 
    i = j;
    if (paramInt > 0) {
      i = j;
      if (view2 instanceof ActionMenuChildView)
        bool = j | ((ActionMenuChildView)view2).needsDividerBefore(); 
    } 
    return bool;
  }
  
  public boolean hideOverflowMenu() {
    boolean bool;
    ActionMenuPresenter actionMenuPresenter = this.mPresenter;
    if (actionMenuPresenter != null && actionMenuPresenter.hideOverflowMenu()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void initialize(MenuBuilder paramMenuBuilder) {
    this.mMenu = paramMenuBuilder;
  }
  
  public boolean invokeItem(MenuItemImpl paramMenuItemImpl) {
    return this.mMenu.performItemAction((MenuItem)paramMenuItemImpl, 0);
  }
  
  public boolean isOverflowMenuShowPending() {
    boolean bool;
    ActionMenuPresenter actionMenuPresenter = this.mPresenter;
    if (actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowPending()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isOverflowMenuShowing() {
    boolean bool;
    ActionMenuPresenter actionMenuPresenter = this.mPresenter;
    if (actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowing()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isOverflowReserved() {
    return this.mReserveOverflow;
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    super.onConfigurationChanged(paramConfiguration);
    ActionMenuPresenter actionMenuPresenter = this.mPresenter;
    if (actionMenuPresenter != null) {
      actionMenuPresenter.updateMenuView(false);
      if (this.mPresenter.isOverflowMenuShowing()) {
        this.mPresenter.hideOverflowMenu();
        this.mPresenter.showOverflowMenu();
      } 
    } 
  }
  
  public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    dismissPopupMenus();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (!this.mFormatItems) {
      super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
      return;
    } 
    int i = getChildCount();
    int j = (paramInt4 - paramInt2) / 2;
    int k = getDividerWidth();
    int m = paramInt3 - paramInt1;
    paramInt1 = m - getPaddingRight() - getPaddingLeft();
    paramBoolean = ViewUtils.isLayoutRtl((View)this);
    paramInt3 = 0;
    paramInt4 = 0;
    paramInt2 = 0;
    while (paramInt3 < i) {
      View view = getChildAt(paramInt3);
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.isOverflowButton) {
          int i2;
          int n = view.getMeasuredWidth();
          paramInt4 = n;
          if (hasSupportDividerBeforeChildAt(paramInt3))
            paramInt4 = n + k; 
          int i1 = view.getMeasuredHeight();
          if (paramBoolean) {
            i2 = getPaddingLeft() + layoutParams.leftMargin;
            n = i2 + paramInt4;
          } else {
            n = getWidth() - getPaddingRight() - layoutParams.rightMargin;
            i2 = n - paramInt4;
          } 
          int i3 = j - i1 / 2;
          view.layout(i2, i3, n, i1 + i3);
          paramInt1 -= paramInt4;
          paramInt4 = 1;
        } else {
          paramInt1 -= view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
          hasSupportDividerBeforeChildAt(paramInt3);
          paramInt2++;
        } 
      } 
      paramInt3++;
    } 
    if (i == 1 && paramInt4 == 0) {
      View view = getChildAt(0);
      paramInt2 = view.getMeasuredWidth();
      paramInt1 = view.getMeasuredHeight();
      paramInt3 = m / 2 - paramInt2 / 2;
      paramInt4 = j - paramInt1 / 2;
      view.layout(paramInt3, paramInt4, paramInt2 + paramInt3, paramInt1 + paramInt4);
      return;
    } 
    paramInt2 -= paramInt4 ^ 0x1;
    if (paramInt2 > 0) {
      paramInt1 /= paramInt2;
    } else {
      paramInt1 = 0;
    } 
    paramInt4 = Math.max(0, paramInt1);
    if (paramBoolean) {
      paramInt2 = getWidth() - getPaddingRight();
      paramInt1 = 0;
      while (paramInt1 < i) {
        View view = getChildAt(paramInt1);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        paramInt3 = paramInt2;
        if (view.getVisibility() != 8)
          if (layoutParams.isOverflowButton) {
            paramInt3 = paramInt2;
          } else {
            int i1 = paramInt2 - layoutParams.rightMargin;
            paramInt2 = view.getMeasuredWidth();
            int n = view.getMeasuredHeight();
            paramInt3 = j - n / 2;
            view.layout(i1 - paramInt2, paramInt3, i1, n + paramInt3);
            paramInt3 = i1 - paramInt2 + layoutParams.leftMargin + paramInt4;
          }  
        paramInt1++;
        paramInt2 = paramInt3;
      } 
    } else {
      paramInt2 = getPaddingLeft();
      paramInt1 = 0;
      while (paramInt1 < i) {
        View view = getChildAt(paramInt1);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        paramInt3 = paramInt2;
        if (view.getVisibility() != 8)
          if (layoutParams.isOverflowButton) {
            paramInt3 = paramInt2;
          } else {
            int i1 = paramInt2 + layoutParams.leftMargin;
            paramInt2 = view.getMeasuredWidth();
            int n = view.getMeasuredHeight();
            paramInt3 = j - n / 2;
            view.layout(i1, paramInt3, i1 + paramInt2, n + paramInt3);
            paramInt3 = i1 + paramInt2 + layoutParams.rightMargin + paramInt4;
          }  
        paramInt1++;
        paramInt2 = paramInt3;
      } 
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    boolean bool2;
    boolean bool1 = this.mFormatItems;
    if (View.MeasureSpec.getMode(paramInt1) == 1073741824) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    this.mFormatItems = bool2;
    if (bool1 != bool2)
      this.mFormatItemsWidth = 0; 
    int i = View.MeasureSpec.getSize(paramInt1);
    if (this.mFormatItems) {
      MenuBuilder menuBuilder = this.mMenu;
      if (menuBuilder != null && i != this.mFormatItemsWidth) {
        this.mFormatItemsWidth = i;
        menuBuilder.onItemsChanged(true);
      } 
    } 
    int j = getChildCount();
    if (this.mFormatItems && j > 0) {
      onMeasureExactFormat(paramInt1, paramInt2);
    } else {
      for (i = 0; i < j; i++) {
        LayoutParams layoutParams = (LayoutParams)getChildAt(i).getLayoutParams();
        layoutParams.rightMargin = 0;
        layoutParams.leftMargin = 0;
      } 
      super.onMeasure(paramInt1, paramInt2);
    } 
  }
  
  public MenuBuilder peekMenu() {
    return this.mMenu;
  }
  
  public void setExpandedActionViewsExclusive(boolean paramBoolean) {
    this.mPresenter.setExpandedActionViewsExclusive(paramBoolean);
  }
  
  public void setMenuCallbacks(MenuPresenter.Callback paramCallback, MenuBuilder.Callback paramCallback1) {
    this.mActionMenuPresenterCallback = paramCallback;
    this.mMenuBuilderCallback = paramCallback1;
  }
  
  public void setOnMenuItemClickListener(OnMenuItemClickListener paramOnMenuItemClickListener) {
    this.mOnMenuItemClickListener = paramOnMenuItemClickListener;
  }
  
  public void setOverflowIcon(Drawable paramDrawable) {
    getMenu();
    this.mPresenter.setOverflowIcon(paramDrawable);
  }
  
  public void setOverflowReserved(boolean paramBoolean) {
    this.mReserveOverflow = paramBoolean;
  }
  
  public void setPopupTheme(int paramInt) {
    if (this.mPopupTheme != paramInt) {
      this.mPopupTheme = paramInt;
      if (paramInt == 0) {
        this.mPopupContext = getContext();
      } else {
        this.mPopupContext = (Context)new ContextThemeWrapper(getContext(), paramInt);
      } 
    } 
  }
  
  public void setPresenter(ActionMenuPresenter paramActionMenuPresenter) {
    this.mPresenter = paramActionMenuPresenter;
    paramActionMenuPresenter.setMenuView(this);
  }
  
  public boolean showOverflowMenu() {
    boolean bool;
    ActionMenuPresenter actionMenuPresenter = this.mPresenter;
    if (actionMenuPresenter != null && actionMenuPresenter.showOverflowMenu()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static interface ActionMenuChildView {
    boolean needsDividerAfter();
    
    boolean needsDividerBefore();
  }
  
  private static class ActionMenuPresenterCallback implements MenuPresenter.Callback {
    public void onCloseMenu(MenuBuilder param1MenuBuilder, boolean param1Boolean) {}
    
    public boolean onOpenSubMenu(MenuBuilder param1MenuBuilder) {
      return false;
    }
  }
  
  public static class LayoutParams extends LinearLayoutCompat.LayoutParams {
    @ExportedProperty
    public int cellsUsed;
    
    @ExportedProperty
    public boolean expandable;
    
    boolean expanded;
    
    @ExportedProperty
    public int extraPixels;
    
    @ExportedProperty
    public boolean isOverflowButton;
    
    @ExportedProperty
    public boolean preventEdgeOffset;
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
      this.isOverflowButton = false;
    }
    
    LayoutParams(int param1Int1, int param1Int2, boolean param1Boolean) {
      super(param1Int1, param1Int2);
      this.isOverflowButton = param1Boolean;
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
    }
    
    public LayoutParams(LayoutParams param1LayoutParams) {
      super((ViewGroup.LayoutParams)param1LayoutParams);
      this.isOverflowButton = param1LayoutParams.isOverflowButton;
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
  }
  
  private class MenuBuilderCallback implements MenuBuilder.Callback {
    public boolean onMenuItemSelected(MenuBuilder param1MenuBuilder, MenuItem param1MenuItem) {
      boolean bool;
      if (ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(param1MenuItem)) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void onMenuModeChange(MenuBuilder param1MenuBuilder) {
      if (ActionMenuView.this.mMenuBuilderCallback != null)
        ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(param1MenuBuilder); 
    }
  }
  
  public static interface OnMenuItemClickListener {
    boolean onMenuItemClick(MenuItem param1MenuItem);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\ActionMenuView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */