package android.support.v4.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import java.util.ArrayList;

public class FragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {
  private boolean mAttached;
  
  private int mContainerId;
  
  private Context mContext;
  
  private FragmentManager mFragmentManager;
  
  private TabInfo mLastTab;
  
  private TabHost.OnTabChangeListener mOnTabChangeListener;
  
  private FrameLayout mRealTabContent;
  
  private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
  
  public FragmentTabHost(Context paramContext) {
    super(paramContext, null);
    initFragmentTabHost(paramContext, (AttributeSet)null);
  }
  
  public FragmentTabHost(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    initFragmentTabHost(paramContext, paramAttributeSet);
  }
  
  private FragmentTransaction doTabChanged(String paramString, FragmentTransaction paramFragmentTransaction) {
    TabInfo tabInfo = getTabInfoForTag(paramString);
    FragmentTransaction fragmentTransaction = paramFragmentTransaction;
    if (this.mLastTab != tabInfo) {
      fragmentTransaction = paramFragmentTransaction;
      if (paramFragmentTransaction == null)
        fragmentTransaction = this.mFragmentManager.beginTransaction(); 
      TabInfo tabInfo1 = this.mLastTab;
      if (tabInfo1 != null && tabInfo1.fragment != null)
        fragmentTransaction.detach(this.mLastTab.fragment); 
      if (tabInfo != null)
        if (tabInfo.fragment == null) {
          tabInfo.fragment = Fragment.instantiate(this.mContext, tabInfo.clss.getName(), tabInfo.args);
          fragmentTransaction.add(this.mContainerId, tabInfo.fragment, tabInfo.tag);
        } else {
          fragmentTransaction.attach(tabInfo.fragment);
        }  
      this.mLastTab = tabInfo;
    } 
    return fragmentTransaction;
  }
  
  private void ensureContent() {
    if (this.mRealTabContent == null) {
      FrameLayout frameLayout = (FrameLayout)findViewById(this.mContainerId);
      this.mRealTabContent = frameLayout;
      if (frameLayout == null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No tab content FrameLayout found for id ");
        stringBuilder.append(this.mContainerId);
        throw new IllegalStateException(stringBuilder.toString());
      } 
    } 
  }
  
  private void ensureHierarchy(Context paramContext) {
    if (findViewById(16908307) == null) {
      LinearLayout linearLayout = new LinearLayout(paramContext);
      linearLayout.setOrientation(1);
      addView((View)linearLayout, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
      TabWidget tabWidget = new TabWidget(paramContext);
      tabWidget.setId(16908307);
      tabWidget.setOrientation(0);
      linearLayout.addView((View)tabWidget, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2, 0.0F));
      FrameLayout frameLayout2 = new FrameLayout(paramContext);
      frameLayout2.setId(16908305);
      linearLayout.addView((View)frameLayout2, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, 0, 0.0F));
      FrameLayout frameLayout1 = new FrameLayout(paramContext);
      this.mRealTabContent = frameLayout1;
      frameLayout1.setId(this.mContainerId);
      linearLayout.addView((View)frameLayout1, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, 0, 1.0F));
    } 
  }
  
  private TabInfo getTabInfoForTag(String paramString) {
    int i = this.mTabs.size();
    for (byte b = 0; b < i; b++) {
      TabInfo tabInfo = this.mTabs.get(b);
      if (tabInfo.tag.equals(paramString))
        return tabInfo; 
    } 
    return null;
  }
  
  private void initFragmentTabHost(Context paramContext, AttributeSet paramAttributeSet) {
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, new int[] { 16842995 }, 0, 0);
    this.mContainerId = typedArray.getResourceId(0, 0);
    typedArray.recycle();
    super.setOnTabChangedListener(this);
  }
  
  public void addTab(TabHost.TabSpec paramTabSpec, Class<?> paramClass, Bundle paramBundle) {
    paramTabSpec.setContent(new DummyTabFactory(this.mContext));
    String str = paramTabSpec.getTag();
    TabInfo tabInfo = new TabInfo(str, paramClass, paramBundle);
    if (this.mAttached) {
      tabInfo.fragment = this.mFragmentManager.findFragmentByTag(str);
      if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
        FragmentTransaction fragmentTransaction = this.mFragmentManager.beginTransaction();
        fragmentTransaction.detach(tabInfo.fragment);
        fragmentTransaction.commit();
      } 
    } 
    this.mTabs.add(tabInfo);
    addTab(paramTabSpec);
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    String str = getCurrentTabTag();
    int i = this.mTabs.size();
    FragmentTransaction fragmentTransaction = null;
    byte b = 0;
    while (b < i) {
      TabInfo tabInfo = this.mTabs.get(b);
      tabInfo.fragment = this.mFragmentManager.findFragmentByTag(tabInfo.tag);
      FragmentTransaction fragmentTransaction1 = fragmentTransaction;
      if (tabInfo.fragment != null) {
        fragmentTransaction1 = fragmentTransaction;
        if (!tabInfo.fragment.isDetached())
          if (tabInfo.tag.equals(str)) {
            this.mLastTab = tabInfo;
            fragmentTransaction1 = fragmentTransaction;
          } else {
            fragmentTransaction1 = fragmentTransaction;
            if (fragmentTransaction == null)
              fragmentTransaction1 = this.mFragmentManager.beginTransaction(); 
            fragmentTransaction1.detach(tabInfo.fragment);
          }  
      } 
      b++;
      fragmentTransaction = fragmentTransaction1;
    } 
    this.mAttached = true;
    fragmentTransaction = doTabChanged(str, fragmentTransaction);
    if (fragmentTransaction != null) {
      fragmentTransaction.commit();
      this.mFragmentManager.executePendingTransactions();
    } 
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    this.mAttached = false;
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState)) {
      super.onRestoreInstanceState(paramParcelable);
      return;
    } 
    SavedState savedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(savedState.getSuperState());
    setCurrentTabByTag(savedState.curTab);
  }
  
  protected Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState(super.onSaveInstanceState());
    savedState.curTab = getCurrentTabTag();
    return (Parcelable)savedState;
  }
  
  public void onTabChanged(String paramString) {
    if (this.mAttached) {
      FragmentTransaction fragmentTransaction = doTabChanged(paramString, (FragmentTransaction)null);
      if (fragmentTransaction != null)
        fragmentTransaction.commit(); 
    } 
    TabHost.OnTabChangeListener onTabChangeListener = this.mOnTabChangeListener;
    if (onTabChangeListener != null)
      onTabChangeListener.onTabChanged(paramString); 
  }
  
  public void setOnTabChangedListener(TabHost.OnTabChangeListener paramOnTabChangeListener) {
    this.mOnTabChangeListener = paramOnTabChangeListener;
  }
  
  @Deprecated
  public void setup() {
    throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
  }
  
  public void setup(Context paramContext, FragmentManager paramFragmentManager) {
    ensureHierarchy(paramContext);
    super.setup();
    this.mContext = paramContext;
    this.mFragmentManager = paramFragmentManager;
    ensureContent();
  }
  
  public void setup(Context paramContext, FragmentManager paramFragmentManager, int paramInt) {
    ensureHierarchy(paramContext);
    super.setup();
    this.mContext = paramContext;
    this.mFragmentManager = paramFragmentManager;
    this.mContainerId = paramInt;
    ensureContent();
    this.mRealTabContent.setId(paramInt);
    if (getId() == -1)
      setId(16908306); 
  }
  
  static class DummyTabFactory implements TabHost.TabContentFactory {
    private final Context mContext;
    
    public DummyTabFactory(Context param1Context) {
      this.mContext = param1Context;
    }
    
    public View createTabContent(String param1String) {
      View view = new View(this.mContext);
      view.setMinimumWidth(0);
      view.setMinimumHeight(0);
      return view;
    }
  }
  
  static class SavedState extends View.BaseSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
        public FragmentTabHost.SavedState createFromParcel(Parcel param2Parcel) {
          return new FragmentTabHost.SavedState(param2Parcel);
        }
        
        public FragmentTabHost.SavedState[] newArray(int param2Int) {
          return new FragmentTabHost.SavedState[param2Int];
        }
      };
    
    String curTab;
    
    SavedState(Parcel param1Parcel) {
      super(param1Parcel);
      this.curTab = param1Parcel.readString();
    }
    
    SavedState(Parcelable param1Parcelable) {
      super(param1Parcelable);
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("FragmentTabHost.SavedState{");
      stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
      stringBuilder.append(" curTab=");
      stringBuilder.append(this.curTab);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      super.writeToParcel(param1Parcel, param1Int);
      param1Parcel.writeString(this.curTab);
    }
  }
  
  static final class null implements Parcelable.Creator<SavedState> {
    public FragmentTabHost.SavedState createFromParcel(Parcel param1Parcel) {
      return new FragmentTabHost.SavedState(param1Parcel);
    }
    
    public FragmentTabHost.SavedState[] newArray(int param1Int) {
      return new FragmentTabHost.SavedState[param1Int];
    }
  }
  
  static final class TabInfo {
    final Bundle args;
    
    final Class<?> clss;
    
    Fragment fragment;
    
    final String tag;
    
    TabInfo(String param1String, Class<?> param1Class, Bundle param1Bundle) {
      this.tag = param1String;
      this.clss = param1Class;
      this.args = param1Bundle;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\FragmentTabHost.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */