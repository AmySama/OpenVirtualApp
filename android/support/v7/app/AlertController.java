package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.appcompat.R;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import java.lang.ref.WeakReference;

class AlertController {
  ListAdapter mAdapter;
  
  private int mAlertDialogLayout;
  
  private final View.OnClickListener mButtonHandler = new View.OnClickListener() {
      public void onClick(View param1View) {
        Message message;
        if (param1View == AlertController.this.mButtonPositive && AlertController.this.mButtonPositiveMessage != null) {
          message = Message.obtain(AlertController.this.mButtonPositiveMessage);
        } else if (message == AlertController.this.mButtonNegative && AlertController.this.mButtonNegativeMessage != null) {
          message = Message.obtain(AlertController.this.mButtonNegativeMessage);
        } else if (message == AlertController.this.mButtonNeutral && AlertController.this.mButtonNeutralMessage != null) {
          message = Message.obtain(AlertController.this.mButtonNeutralMessage);
        } else {
          message = null;
        } 
        if (message != null)
          message.sendToTarget(); 
        AlertController.this.mHandler.obtainMessage(1, AlertController.this.mDialog).sendToTarget();
      }
    };
  
  private final int mButtonIconDimen;
  
  Button mButtonNegative;
  
  private Drawable mButtonNegativeIcon;
  
  Message mButtonNegativeMessage;
  
  private CharSequence mButtonNegativeText;
  
  Button mButtonNeutral;
  
  private Drawable mButtonNeutralIcon;
  
  Message mButtonNeutralMessage;
  
  private CharSequence mButtonNeutralText;
  
  private int mButtonPanelLayoutHint = 0;
  
  private int mButtonPanelSideLayout;
  
  Button mButtonPositive;
  
  private Drawable mButtonPositiveIcon;
  
  Message mButtonPositiveMessage;
  
  private CharSequence mButtonPositiveText;
  
  int mCheckedItem = -1;
  
  private final Context mContext;
  
  private View mCustomTitleView;
  
  final AppCompatDialog mDialog;
  
  Handler mHandler;
  
  private Drawable mIcon;
  
  private int mIconId = 0;
  
  private ImageView mIconView;
  
  int mListItemLayout;
  
  int mListLayout;
  
  ListView mListView;
  
  private CharSequence mMessage;
  
  private TextView mMessageView;
  
  int mMultiChoiceItemLayout;
  
  NestedScrollView mScrollView;
  
  private boolean mShowTitle;
  
  int mSingleChoiceItemLayout;
  
  private CharSequence mTitle;
  
  private TextView mTitleView;
  
  private View mView;
  
  private int mViewLayoutResId;
  
  private int mViewSpacingBottom;
  
  private int mViewSpacingLeft;
  
  private int mViewSpacingRight;
  
  private boolean mViewSpacingSpecified = false;
  
  private int mViewSpacingTop;
  
  private final Window mWindow;
  
  public AlertController(Context paramContext, AppCompatDialog paramAppCompatDialog, Window paramWindow) {
    this.mContext = paramContext;
    this.mDialog = paramAppCompatDialog;
    this.mWindow = paramWindow;
    this.mHandler = new ButtonHandler((DialogInterface)paramAppCompatDialog);
    TypedArray typedArray = paramContext.obtainStyledAttributes(null, R.styleable.AlertDialog, R.attr.alertDialogStyle, 0);
    this.mAlertDialogLayout = typedArray.getResourceId(R.styleable.AlertDialog_android_layout, 0);
    this.mButtonPanelSideLayout = typedArray.getResourceId(R.styleable.AlertDialog_buttonPanelSideLayout, 0);
    this.mListLayout = typedArray.getResourceId(R.styleable.AlertDialog_listLayout, 0);
    this.mMultiChoiceItemLayout = typedArray.getResourceId(R.styleable.AlertDialog_multiChoiceItemLayout, 0);
    this.mSingleChoiceItemLayout = typedArray.getResourceId(R.styleable.AlertDialog_singleChoiceItemLayout, 0);
    this.mListItemLayout = typedArray.getResourceId(R.styleable.AlertDialog_listItemLayout, 0);
    this.mShowTitle = typedArray.getBoolean(R.styleable.AlertDialog_showTitle, true);
    this.mButtonIconDimen = typedArray.getDimensionPixelSize(R.styleable.AlertDialog_buttonIconDimen, 0);
    typedArray.recycle();
    paramAppCompatDialog.supportRequestWindowFeature(1);
  }
  
  static boolean canTextInput(View paramView) {
    if (paramView.onCheckIsTextEditor())
      return true; 
    if (!(paramView instanceof ViewGroup))
      return false; 
    ViewGroup viewGroup = (ViewGroup)paramView;
    int i = viewGroup.getChildCount();
    while (i > 0) {
      int j = i - 1;
      i = j;
      if (canTextInput(viewGroup.getChildAt(j)))
        return true; 
    } 
    return false;
  }
  
  private void centerButton(Button paramButton) {
    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)paramButton.getLayoutParams();
    layoutParams.gravity = 1;
    layoutParams.weight = 0.5F;
    paramButton.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
  }
  
  static void manageScrollIndicators(View paramView1, View paramView2, View paramView3) {
    boolean bool = false;
    if (paramView2 != null) {
      byte b;
      if (paramView1.canScrollVertically(-1)) {
        b = 0;
      } else {
        b = 4;
      } 
      paramView2.setVisibility(b);
    } 
    if (paramView3 != null) {
      byte b;
      if (paramView1.canScrollVertically(1)) {
        b = bool;
      } else {
        b = 4;
      } 
      paramView3.setVisibility(b);
    } 
  }
  
  private ViewGroup resolvePanel(View paramView1, View paramView2) {
    if (paramView1 == null) {
      paramView1 = paramView2;
      if (paramView2 instanceof ViewStub)
        paramView1 = ((ViewStub)paramView2).inflate(); 
      return (ViewGroup)paramView1;
    } 
    if (paramView2 != null) {
      ViewParent viewParent = paramView2.getParent();
      if (viewParent instanceof ViewGroup)
        ((ViewGroup)viewParent).removeView(paramView2); 
    } 
    paramView2 = paramView1;
    if (paramView1 instanceof ViewStub)
      paramView2 = ((ViewStub)paramView1).inflate(); 
    return (ViewGroup)paramView2;
  }
  
  private int selectContentView() {
    int i = this.mButtonPanelSideLayout;
    return (i == 0) ? this.mAlertDialogLayout : ((this.mButtonPanelLayoutHint == 1) ? i : this.mAlertDialogLayout);
  }
  
  private void setScrollIndicators(ViewGroup paramViewGroup, final View top, int paramInt1, int paramInt2) {
    final View bottom = this.mWindow.findViewById(R.id.scrollIndicatorUp);
    View view2 = this.mWindow.findViewById(R.id.scrollIndicatorDown);
    if (Build.VERSION.SDK_INT >= 23) {
      ViewCompat.setScrollIndicators(top, paramInt1, paramInt2);
      if (view1 != null)
        paramViewGroup.removeView(view1); 
      if (view2 != null)
        paramViewGroup.removeView(view2); 
    } else {
      top = view1;
      if (view1 != null) {
        top = view1;
        if ((paramInt1 & 0x1) == 0) {
          paramViewGroup.removeView(view1);
          top = null;
        } 
      } 
      view1 = view2;
      if (view2 != null) {
        view1 = view2;
        if ((paramInt1 & 0x2) == 0) {
          paramViewGroup.removeView(view2);
          view1 = null;
        } 
      } 
      if (top != null || view1 != null)
        if (this.mMessage != null) {
          this.mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                public void onScrollChange(NestedScrollView param1NestedScrollView, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
                  AlertController.manageScrollIndicators((View)param1NestedScrollView, top, bottom);
                }
              });
          this.mScrollView.post(new Runnable() {
                public void run() {
                  AlertController.manageScrollIndicators((View)AlertController.this.mScrollView, top, bottom);
                }
              });
        } else {
          ListView listView = this.mListView;
          if (listView != null) {
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                  public void onScroll(AbsListView param1AbsListView, int param1Int1, int param1Int2, int param1Int3) {
                    AlertController.manageScrollIndicators((View)param1AbsListView, top, bottom);
                  }
                  
                  public void onScrollStateChanged(AbsListView param1AbsListView, int param1Int) {}
                });
            this.mListView.post(new Runnable() {
                  public void run() {
                    AlertController.manageScrollIndicators((View)AlertController.this.mListView, top, bottom);
                  }
                });
          } else {
            if (top != null)
              paramViewGroup.removeView(top); 
            if (view1 != null)
              paramViewGroup.removeView(view1); 
          } 
        }  
    } 
  }
  
  private void setupButtons(ViewGroup paramViewGroup) {
    int i;
    Button button = (Button)paramViewGroup.findViewById(16908313);
    this.mButtonPositive = button;
    button.setOnClickListener(this.mButtonHandler);
    boolean bool = TextUtils.isEmpty(this.mButtonPositiveText);
    boolean bool1 = true;
    if (bool && this.mButtonPositiveIcon == null) {
      this.mButtonPositive.setVisibility(8);
      i = 0;
    } else {
      this.mButtonPositive.setText(this.mButtonPositiveText);
      Drawable drawable = this.mButtonPositiveIcon;
      if (drawable != null) {
        int j = this.mButtonIconDimen;
        drawable.setBounds(0, 0, j, j);
        this.mButtonPositive.setCompoundDrawables(this.mButtonPositiveIcon, null, null, null);
      } 
      this.mButtonPositive.setVisibility(0);
      i = 1;
    } 
    button = (Button)paramViewGroup.findViewById(16908314);
    this.mButtonNegative = button;
    button.setOnClickListener(this.mButtonHandler);
    if (TextUtils.isEmpty(this.mButtonNegativeText) && this.mButtonNegativeIcon == null) {
      this.mButtonNegative.setVisibility(8);
    } else {
      this.mButtonNegative.setText(this.mButtonNegativeText);
      Drawable drawable = this.mButtonNegativeIcon;
      if (drawable != null) {
        int j = this.mButtonIconDimen;
        drawable.setBounds(0, 0, j, j);
        this.mButtonNegative.setCompoundDrawables(this.mButtonNegativeIcon, null, null, null);
      } 
      this.mButtonNegative.setVisibility(0);
      i |= 0x2;
    } 
    button = (Button)paramViewGroup.findViewById(16908315);
    this.mButtonNeutral = button;
    button.setOnClickListener(this.mButtonHandler);
    if (TextUtils.isEmpty(this.mButtonNeutralText) && this.mButtonNeutralIcon == null) {
      this.mButtonNeutral.setVisibility(8);
    } else {
      this.mButtonNeutral.setText(this.mButtonNeutralText);
      Drawable drawable = this.mButtonPositiveIcon;
      if (drawable != null) {
        int j = this.mButtonIconDimen;
        drawable.setBounds(0, 0, j, j);
        this.mButtonPositive.setCompoundDrawables(this.mButtonPositiveIcon, null, null, null);
      } 
      this.mButtonNeutral.setVisibility(0);
      i |= 0x4;
    } 
    if (shouldCenterSingleButton(this.mContext))
      if (i == 1) {
        centerButton(this.mButtonPositive);
      } else if (i == 2) {
        centerButton(this.mButtonNegative);
      } else if (i == 4) {
        centerButton(this.mButtonNeutral);
      }  
    if (i != 0) {
      i = bool1;
    } else {
      i = 0;
    } 
    if (i == 0)
      paramViewGroup.setVisibility(8); 
  }
  
  private void setupContent(ViewGroup paramViewGroup) {
    NestedScrollView nestedScrollView = (NestedScrollView)this.mWindow.findViewById(R.id.scrollView);
    this.mScrollView = nestedScrollView;
    nestedScrollView.setFocusable(false);
    this.mScrollView.setNestedScrollingEnabled(false);
    TextView textView = (TextView)paramViewGroup.findViewById(16908299);
    this.mMessageView = textView;
    if (textView == null)
      return; 
    CharSequence charSequence = this.mMessage;
    if (charSequence != null) {
      textView.setText(charSequence);
    } else {
      textView.setVisibility(8);
      this.mScrollView.removeView((View)this.mMessageView);
      if (this.mListView != null) {
        paramViewGroup = (ViewGroup)this.mScrollView.getParent();
        int i = paramViewGroup.indexOfChild((View)this.mScrollView);
        paramViewGroup.removeViewAt(i);
        paramViewGroup.addView((View)this.mListView, i, new ViewGroup.LayoutParams(-1, -1));
      } else {
        paramViewGroup.setVisibility(8);
      } 
    } 
  }
  
  private void setupCustomContent(ViewGroup paramViewGroup) {
    View view = this.mView;
    boolean bool = false;
    if (view == null)
      if (this.mViewLayoutResId != 0) {
        view = LayoutInflater.from(this.mContext).inflate(this.mViewLayoutResId, paramViewGroup, false);
      } else {
        view = null;
      }  
    if (view != null)
      bool = true; 
    if (!bool || !canTextInput(view))
      this.mWindow.setFlags(131072, 131072); 
    if (bool) {
      FrameLayout frameLayout = (FrameLayout)this.mWindow.findViewById(R.id.custom);
      frameLayout.addView(view, new ViewGroup.LayoutParams(-1, -1));
      if (this.mViewSpacingSpecified)
        frameLayout.setPadding(this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom); 
      if (this.mListView != null)
        ((LinearLayoutCompat.LayoutParams)paramViewGroup.getLayoutParams()).weight = 0.0F; 
    } else {
      paramViewGroup.setVisibility(8);
    } 
  }
  
  private void setupTitle(ViewGroup paramViewGroup) {
    if (this.mCustomTitleView != null) {
      ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
      paramViewGroup.addView(this.mCustomTitleView, 0, layoutParams);
      this.mWindow.findViewById(R.id.title_template).setVisibility(8);
    } else {
      Drawable drawable;
      this.mIconView = (ImageView)this.mWindow.findViewById(16908294);
      if ((TextUtils.isEmpty(this.mTitle) ^ true) != 0 && this.mShowTitle) {
        TextView textView = (TextView)this.mWindow.findViewById(R.id.alertTitle);
        this.mTitleView = textView;
        textView.setText(this.mTitle);
        int i = this.mIconId;
        if (i != 0) {
          this.mIconView.setImageResource(i);
        } else {
          drawable = this.mIcon;
          if (drawable != null) {
            this.mIconView.setImageDrawable(drawable);
          } else {
            this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom());
            this.mIconView.setVisibility(8);
          } 
        } 
      } else {
        this.mWindow.findViewById(R.id.title_template).setVisibility(8);
        this.mIconView.setVisibility(8);
        drawable.setVisibility(8);
      } 
    } 
  }
  
  private void setupView() {
    int i;
    int j;
    boolean bool;
    View view1 = this.mWindow.findViewById(R.id.parentPanel);
    View view2 = view1.findViewById(R.id.topPanel);
    View view3 = view1.findViewById(R.id.contentPanel);
    View view4 = view1.findViewById(R.id.buttonPanel);
    ViewGroup viewGroup1 = (ViewGroup)view1.findViewById(R.id.customPanel);
    setupCustomContent(viewGroup1);
    View view5 = viewGroup1.findViewById(R.id.topPanel);
    View view6 = viewGroup1.findViewById(R.id.contentPanel);
    View view7 = viewGroup1.findViewById(R.id.buttonPanel);
    ViewGroup viewGroup2 = resolvePanel(view5, view2);
    ViewGroup viewGroup3 = resolvePanel(view6, view3);
    ViewGroup viewGroup4 = resolvePanel(view7, view4);
    setupContent(viewGroup3);
    setupButtons(viewGroup4);
    setupTitle(viewGroup2);
    byte b = 0;
    if (viewGroup1 != null && viewGroup1.getVisibility() != 8) {
      i = 1;
    } else {
      i = 0;
    } 
    if (viewGroup2 != null && viewGroup2.getVisibility() != 8) {
      j = 1;
    } else {
      j = 0;
    } 
    if (viewGroup4 != null && viewGroup4.getVisibility() != 8) {
      bool = true;
    } else {
      bool = false;
    } 
    if (!bool && viewGroup3 != null) {
      View view = viewGroup3.findViewById(R.id.textSpacerNoButtons);
      if (view != null)
        view.setVisibility(0); 
    } 
    if (j) {
      View view;
      NestedScrollView nestedScrollView = this.mScrollView;
      if (nestedScrollView != null)
        nestedScrollView.setClipToPadding(true); 
      nestedScrollView = null;
      if (this.mMessage != null || this.mListView != null)
        view = viewGroup2.findViewById(R.id.titleDividerNoCustom); 
      if (view != null)
        view.setVisibility(0); 
    } else if (viewGroup3 != null) {
      View view = viewGroup3.findViewById(R.id.textSpacerNoTitle);
      if (view != null)
        view.setVisibility(0); 
    } 
    ListView listView1 = this.mListView;
    if (listView1 instanceof RecycleListView)
      ((RecycleListView)listView1).setHasDecor(j, bool); 
    if (!i) {
      NestedScrollView nestedScrollView;
      listView1 = this.mListView;
      if (listView1 == null)
        nestedScrollView = this.mScrollView; 
      if (nestedScrollView != null) {
        i = b;
        if (bool)
          i = 2; 
        setScrollIndicators(viewGroup3, (View)nestedScrollView, j | i, 3);
      } 
    } 
    ListView listView2 = this.mListView;
    if (listView2 != null) {
      ListAdapter listAdapter = this.mAdapter;
      if (listAdapter != null) {
        listView2.setAdapter(listAdapter);
        i = this.mCheckedItem;
        if (i > -1) {
          listView2.setItemChecked(i, true);
          listView2.setSelection(i);
        } 
      } 
    } 
  }
  
  private static boolean shouldCenterSingleButton(Context paramContext) {
    TypedValue typedValue = new TypedValue();
    Resources.Theme theme = paramContext.getTheme();
    int i = R.attr.alertDialogCenterButtons;
    boolean bool = true;
    theme.resolveAttribute(i, typedValue, true);
    if (typedValue.data == 0)
      bool = false; 
    return bool;
  }
  
  public Button getButton(int paramInt) {
    return (paramInt != -3) ? ((paramInt != -2) ? ((paramInt != -1) ? null : this.mButtonPositive) : this.mButtonNegative) : this.mButtonNeutral;
  }
  
  public int getIconAttributeResId(int paramInt) {
    TypedValue typedValue = new TypedValue();
    this.mContext.getTheme().resolveAttribute(paramInt, typedValue, true);
    return typedValue.resourceId;
  }
  
  public ListView getListView() {
    return this.mListView;
  }
  
  public void installContent() {
    int i = selectContentView();
    this.mDialog.setContentView(i);
    setupView();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    boolean bool;
    NestedScrollView nestedScrollView = this.mScrollView;
    if (nestedScrollView != null && nestedScrollView.executeKeyEvent(paramKeyEvent)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent) {
    boolean bool;
    NestedScrollView nestedScrollView = this.mScrollView;
    if (nestedScrollView != null && nestedScrollView.executeKeyEvent(paramKeyEvent)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void setButton(int paramInt, CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener, Message paramMessage, Drawable paramDrawable) {
    Message message = paramMessage;
    if (paramMessage == null) {
      message = paramMessage;
      if (paramOnClickListener != null)
        message = this.mHandler.obtainMessage(paramInt, paramOnClickListener); 
    } 
    if (paramInt != -3) {
      if (paramInt != -2) {
        if (paramInt == -1) {
          this.mButtonPositiveText = paramCharSequence;
          this.mButtonPositiveMessage = message;
          this.mButtonPositiveIcon = paramDrawable;
        } else {
          throw new IllegalArgumentException("Button does not exist");
        } 
      } else {
        this.mButtonNegativeText = paramCharSequence;
        this.mButtonNegativeMessage = message;
        this.mButtonNegativeIcon = paramDrawable;
      } 
    } else {
      this.mButtonNeutralText = paramCharSequence;
      this.mButtonNeutralMessage = message;
      this.mButtonNeutralIcon = paramDrawable;
    } 
  }
  
  public void setButtonPanelLayoutHint(int paramInt) {
    this.mButtonPanelLayoutHint = paramInt;
  }
  
  public void setCustomTitle(View paramView) {
    this.mCustomTitleView = paramView;
  }
  
  public void setIcon(int paramInt) {
    this.mIcon = null;
    this.mIconId = paramInt;
    ImageView imageView = this.mIconView;
    if (imageView != null)
      if (paramInt != 0) {
        imageView.setVisibility(0);
        this.mIconView.setImageResource(this.mIconId);
      } else {
        imageView.setVisibility(8);
      }  
  }
  
  public void setIcon(Drawable paramDrawable) {
    this.mIcon = paramDrawable;
    this.mIconId = 0;
    ImageView imageView = this.mIconView;
    if (imageView != null)
      if (paramDrawable != null) {
        imageView.setVisibility(0);
        this.mIconView.setImageDrawable(paramDrawable);
      } else {
        imageView.setVisibility(8);
      }  
  }
  
  public void setMessage(CharSequence paramCharSequence) {
    this.mMessage = paramCharSequence;
    TextView textView = this.mMessageView;
    if (textView != null)
      textView.setText(paramCharSequence); 
  }
  
  public void setTitle(CharSequence paramCharSequence) {
    this.mTitle = paramCharSequence;
    TextView textView = this.mTitleView;
    if (textView != null)
      textView.setText(paramCharSequence); 
  }
  
  public void setView(int paramInt) {
    this.mView = null;
    this.mViewLayoutResId = paramInt;
    this.mViewSpacingSpecified = false;
  }
  
  public void setView(View paramView) {
    this.mView = paramView;
    this.mViewLayoutResId = 0;
    this.mViewSpacingSpecified = false;
  }
  
  public void setView(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mView = paramView;
    this.mViewLayoutResId = 0;
    this.mViewSpacingSpecified = true;
    this.mViewSpacingLeft = paramInt1;
    this.mViewSpacingTop = paramInt2;
    this.mViewSpacingRight = paramInt3;
    this.mViewSpacingBottom = paramInt4;
  }
  
  public static class AlertParams {
    public ListAdapter mAdapter;
    
    public boolean mCancelable;
    
    public int mCheckedItem = -1;
    
    public boolean[] mCheckedItems;
    
    public final Context mContext;
    
    public Cursor mCursor;
    
    public View mCustomTitleView;
    
    public boolean mForceInverseBackground;
    
    public Drawable mIcon;
    
    public int mIconAttrId = 0;
    
    public int mIconId = 0;
    
    public final LayoutInflater mInflater;
    
    public String mIsCheckedColumn;
    
    public boolean mIsMultiChoice;
    
    public boolean mIsSingleChoice;
    
    public CharSequence[] mItems;
    
    public String mLabelColumn;
    
    public CharSequence mMessage;
    
    public Drawable mNegativeButtonIcon;
    
    public DialogInterface.OnClickListener mNegativeButtonListener;
    
    public CharSequence mNegativeButtonText;
    
    public Drawable mNeutralButtonIcon;
    
    public DialogInterface.OnClickListener mNeutralButtonListener;
    
    public CharSequence mNeutralButtonText;
    
    public DialogInterface.OnCancelListener mOnCancelListener;
    
    public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
    
    public DialogInterface.OnClickListener mOnClickListener;
    
    public DialogInterface.OnDismissListener mOnDismissListener;
    
    public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    
    public DialogInterface.OnKeyListener mOnKeyListener;
    
    public OnPrepareListViewListener mOnPrepareListViewListener;
    
    public Drawable mPositiveButtonIcon;
    
    public DialogInterface.OnClickListener mPositiveButtonListener;
    
    public CharSequence mPositiveButtonText;
    
    public boolean mRecycleOnMeasure = true;
    
    public CharSequence mTitle;
    
    public View mView;
    
    public int mViewLayoutResId;
    
    public int mViewSpacingBottom;
    
    public int mViewSpacingLeft;
    
    public int mViewSpacingRight;
    
    public boolean mViewSpacingSpecified = false;
    
    public int mViewSpacingTop;
    
    public AlertParams(Context param1Context) {
      this.mContext = param1Context;
      this.mCancelable = true;
      this.mInflater = (LayoutInflater)param1Context.getSystemService("layout_inflater");
    }
    
    private void createListView(final AlertController dialog) {
      ArrayAdapter<CharSequence> arrayAdapter;
      final AlertController.RecycleListView listView = (AlertController.RecycleListView)this.mInflater.inflate(dialog.mListLayout, null);
      if (this.mIsMultiChoice) {
        if (this.mCursor == null) {
          arrayAdapter = new ArrayAdapter<CharSequence>(this.mContext, dialog.mMultiChoiceItemLayout, 16908308, this.mItems) {
              public View getView(int param2Int, View param2View, ViewGroup param2ViewGroup) {
                param2View = super.getView(param2Int, param2View, param2ViewGroup);
                if (AlertController.AlertParams.this.mCheckedItems != null && AlertController.AlertParams.this.mCheckedItems[param2Int])
                  listView.setItemChecked(param2Int, true); 
                return param2View;
              }
            };
        } else {
          CursorAdapter cursorAdapter = new CursorAdapter(this.mContext, this.mCursor, false) {
              private final int mIsCheckedIndex;
              
              private final int mLabelIndex;
              
              public void bindView(View param2View, Context param2Context, Cursor param2Cursor) {
                ((CheckedTextView)param2View.findViewById(16908308)).setText(param2Cursor.getString(this.mLabelIndex));
                AlertController.RecycleListView recycleListView = listView;
                int i = param2Cursor.getPosition();
                int j = param2Cursor.getInt(this.mIsCheckedIndex);
                boolean bool = true;
                if (j != 1)
                  bool = false; 
                recycleListView.setItemChecked(i, bool);
              }
              
              public View newView(Context param2Context, Cursor param2Cursor, ViewGroup param2ViewGroup) {
                return AlertController.AlertParams.this.mInflater.inflate(dialog.mMultiChoiceItemLayout, param2ViewGroup, false);
              }
            };
        } 
      } else {
        int i;
        if (this.mIsSingleChoice) {
          i = dialog.mSingleChoiceItemLayout;
        } else {
          i = dialog.mListItemLayout;
        } 
        if (this.mCursor != null) {
          SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this.mContext, i, this.mCursor, new String[] { this.mLabelColumn }, new int[] { 16908308 });
        } else {
          ListAdapter listAdapter = this.mAdapter;
          if (listAdapter == null)
            arrayAdapter = new AlertController.CheckedItemAdapter(this.mContext, i, 16908308, this.mItems); 
        } 
      } 
      OnPrepareListViewListener onPrepareListViewListener = this.mOnPrepareListViewListener;
      if (onPrepareListViewListener != null)
        onPrepareListViewListener.onPrepareListView(recycleListView); 
      dialog.mAdapter = (ListAdapter)arrayAdapter;
      dialog.mCheckedItem = this.mCheckedItem;
      if (this.mOnClickListener != null) {
        recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              public void onItemClick(AdapterView<?> param2AdapterView, View param2View, int param2Int, long param2Long) {
                AlertController.AlertParams.this.mOnClickListener.onClick((DialogInterface)dialog.mDialog, param2Int);
                if (!AlertController.AlertParams.this.mIsSingleChoice)
                  dialog.mDialog.dismiss(); 
              }
            });
      } else if (this.mOnCheckboxClickListener != null) {
        recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              public void onItemClick(AdapterView<?> param2AdapterView, View param2View, int param2Int, long param2Long) {
                if (AlertController.AlertParams.this.mCheckedItems != null)
                  AlertController.AlertParams.this.mCheckedItems[param2Int] = listView.isItemChecked(param2Int); 
                AlertController.AlertParams.this.mOnCheckboxClickListener.onClick((DialogInterface)dialog.mDialog, param2Int, listView.isItemChecked(param2Int));
              }
            });
      } 
      AdapterView.OnItemSelectedListener onItemSelectedListener = this.mOnItemSelectedListener;
      if (onItemSelectedListener != null)
        recycleListView.setOnItemSelectedListener(onItemSelectedListener); 
      if (this.mIsSingleChoice) {
        recycleListView.setChoiceMode(1);
      } else if (this.mIsMultiChoice) {
        recycleListView.setChoiceMode(2);
      } 
      dialog.mListView = recycleListView;
    }
    
    public void apply(AlertController param1AlertController) {
      View view2 = this.mCustomTitleView;
      if (view2 != null) {
        param1AlertController.setCustomTitle(view2);
      } else {
        CharSequence charSequence1 = this.mTitle;
        if (charSequence1 != null)
          param1AlertController.setTitle(charSequence1); 
        Drawable drawable = this.mIcon;
        if (drawable != null)
          param1AlertController.setIcon(drawable); 
        int i = this.mIconId;
        if (i != 0)
          param1AlertController.setIcon(i); 
        i = this.mIconAttrId;
        if (i != 0)
          param1AlertController.setIcon(param1AlertController.getIconAttributeResId(i)); 
      } 
      CharSequence charSequence = this.mMessage;
      if (charSequence != null)
        param1AlertController.setMessage(charSequence); 
      if (this.mPositiveButtonText != null || this.mPositiveButtonIcon != null)
        param1AlertController.setButton(-1, this.mPositiveButtonText, this.mPositiveButtonListener, null, this.mPositiveButtonIcon); 
      if (this.mNegativeButtonText != null || this.mNegativeButtonIcon != null)
        param1AlertController.setButton(-2, this.mNegativeButtonText, this.mNegativeButtonListener, null, this.mNegativeButtonIcon); 
      if (this.mNeutralButtonText != null || this.mNeutralButtonIcon != null)
        param1AlertController.setButton(-3, this.mNeutralButtonText, this.mNeutralButtonListener, null, this.mNeutralButtonIcon); 
      if (this.mItems != null || this.mCursor != null || this.mAdapter != null)
        createListView(param1AlertController); 
      View view1 = this.mView;
      if (view1 != null) {
        if (this.mViewSpacingSpecified) {
          param1AlertController.setView(view1, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
        } else {
          param1AlertController.setView(view1);
        } 
      } else {
        int i = this.mViewLayoutResId;
        if (i != 0)
          param1AlertController.setView(i); 
      } 
    }
    
    public static interface OnPrepareListViewListener {
      void onPrepareListView(ListView param2ListView);
    }
  }
  
  class null extends ArrayAdapter<CharSequence> {
    null(Context param1Context, int param1Int1, int param1Int2, CharSequence[] param1ArrayOfCharSequence) {
      super(param1Context, param1Int1, param1Int2, (Object[])param1ArrayOfCharSequence);
    }
    
    public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
      param1View = super.getView(param1Int, param1View, param1ViewGroup);
      if (this.this$0.mCheckedItems != null && this.this$0.mCheckedItems[param1Int])
        listView.setItemChecked(param1Int, true); 
      return param1View;
    }
  }
  
  class null extends CursorAdapter {
    private final int mIsCheckedIndex;
    
    private final int mLabelIndex;
    
    null(Context param1Context, Cursor param1Cursor, boolean param1Boolean) {
      super(param1Context, param1Cursor, param1Boolean);
      Cursor cursor = getCursor();
      this.mLabelIndex = cursor.getColumnIndexOrThrow(this.this$0.mLabelColumn);
      this.mIsCheckedIndex = cursor.getColumnIndexOrThrow(this.this$0.mIsCheckedColumn);
    }
    
    public void bindView(View param1View, Context param1Context, Cursor param1Cursor) {
      ((CheckedTextView)param1View.findViewById(16908308)).setText(param1Cursor.getString(this.mLabelIndex));
      AlertController.RecycleListView recycleListView = listView;
      int i = param1Cursor.getPosition();
      int j = param1Cursor.getInt(this.mIsCheckedIndex);
      boolean bool = true;
      if (j != 1)
        bool = false; 
      recycleListView.setItemChecked(i, bool);
    }
    
    public View newView(Context param1Context, Cursor param1Cursor, ViewGroup param1ViewGroup) {
      return this.this$0.mInflater.inflate(dialog.mMultiChoiceItemLayout, param1ViewGroup, false);
    }
  }
  
  class null implements AdapterView.OnItemClickListener {
    public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
      this.this$0.mOnClickListener.onClick((DialogInterface)dialog.mDialog, param1Int);
      if (!this.this$0.mIsSingleChoice)
        dialog.mDialog.dismiss(); 
    }
  }
  
  class null implements AdapterView.OnItemClickListener {
    public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
      if (this.this$0.mCheckedItems != null)
        this.this$0.mCheckedItems[param1Int] = listView.isItemChecked(param1Int); 
      this.this$0.mOnCheckboxClickListener.onClick((DialogInterface)dialog.mDialog, param1Int, listView.isItemChecked(param1Int));
    }
  }
  
  public static interface OnPrepareListViewListener {
    void onPrepareListView(ListView param1ListView);
  }
  
  private static final class ButtonHandler extends Handler {
    private static final int MSG_DISMISS_DIALOG = 1;
    
    private WeakReference<DialogInterface> mDialog;
    
    public ButtonHandler(DialogInterface param1DialogInterface) {
      this.mDialog = new WeakReference<DialogInterface>(param1DialogInterface);
    }
    
    public void handleMessage(Message param1Message) {
      int i = param1Message.what;
      if (i != -3 && i != -2 && i != -1) {
        if (i == 1)
          ((DialogInterface)param1Message.obj).dismiss(); 
      } else {
        ((DialogInterface.OnClickListener)param1Message.obj).onClick(this.mDialog.get(), param1Message.what);
      } 
    }
  }
  
  private static class CheckedItemAdapter extends ArrayAdapter<CharSequence> {
    public CheckedItemAdapter(Context param1Context, int param1Int1, int param1Int2, CharSequence[] param1ArrayOfCharSequence) {
      super(param1Context, param1Int1, param1Int2, (Object[])param1ArrayOfCharSequence);
    }
    
    public long getItemId(int param1Int) {
      return param1Int;
    }
    
    public boolean hasStableIds() {
      return true;
    }
  }
  
  public static class RecycleListView extends ListView {
    private final int mPaddingBottomNoButtons;
    
    private final int mPaddingTopNoTitle;
    
    public RecycleListView(Context param1Context) {
      this(param1Context, (AttributeSet)null);
    }
    
    public RecycleListView(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, R.styleable.RecycleListView);
      this.mPaddingBottomNoButtons = typedArray.getDimensionPixelOffset(R.styleable.RecycleListView_paddingBottomNoButtons, -1);
      this.mPaddingTopNoTitle = typedArray.getDimensionPixelOffset(R.styleable.RecycleListView_paddingTopNoTitle, -1);
    }
    
    public void setHasDecor(boolean param1Boolean1, boolean param1Boolean2) {
      if (!param1Boolean2 || !param1Boolean1) {
        int j;
        int m;
        int i = getPaddingLeft();
        if (param1Boolean1) {
          j = getPaddingTop();
        } else {
          j = this.mPaddingTopNoTitle;
        } 
        int k = getPaddingRight();
        if (param1Boolean2) {
          m = getPaddingBottom();
        } else {
          m = this.mPaddingBottomNoButtons;
        } 
        setPadding(i, j, k, m);
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\app\AlertController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */