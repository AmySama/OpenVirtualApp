package android.support.v4.view;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

public abstract class PagerAdapter {
  public static final int POSITION_NONE = -2;
  
  public static final int POSITION_UNCHANGED = -1;
  
  private final DataSetObservable mObservable = new DataSetObservable();
  
  private DataSetObserver mViewPagerObserver;
  
  @Deprecated
  public void destroyItem(View paramView, int paramInt, Object paramObject) {
    throw new UnsupportedOperationException("Required method destroyItem was not overridden");
  }
  
  public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject) {
    destroyItem((View)paramViewGroup, paramInt, paramObject);
  }
  
  @Deprecated
  public void finishUpdate(View paramView) {}
  
  public void finishUpdate(ViewGroup paramViewGroup) {
    finishUpdate((View)paramViewGroup);
  }
  
  public abstract int getCount();
  
  public int getItemPosition(Object paramObject) {
    return -1;
  }
  
  public CharSequence getPageTitle(int paramInt) {
    return null;
  }
  
  public float getPageWidth(int paramInt) {
    return 1.0F;
  }
  
  @Deprecated
  public Object instantiateItem(View paramView, int paramInt) {
    throw new UnsupportedOperationException("Required method instantiateItem was not overridden");
  }
  
  public Object instantiateItem(ViewGroup paramViewGroup, int paramInt) {
    return instantiateItem((View)paramViewGroup, paramInt);
  }
  
  public abstract boolean isViewFromObject(View paramView, Object paramObject);
  
  public void notifyDataSetChanged() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mViewPagerObserver : Landroid/database/DataSetObserver;
    //   6: ifnull -> 16
    //   9: aload_0
    //   10: getfield mViewPagerObserver : Landroid/database/DataSetObserver;
    //   13: invokevirtual onChanged : ()V
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_0
    //   19: getfield mObservable : Landroid/database/DataSetObservable;
    //   22: invokevirtual notifyChanged : ()V
    //   25: return
    //   26: astore_1
    //   27: aload_0
    //   28: monitorexit
    //   29: aload_1
    //   30: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	26	finally
    //   16	18	26	finally
    //   27	29	26	finally
  }
  
  public void registerDataSetObserver(DataSetObserver paramDataSetObserver) {
    this.mObservable.registerObserver(paramDataSetObserver);
  }
  
  public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader) {}
  
  public Parcelable saveState() {
    return null;
  }
  
  @Deprecated
  public void setPrimaryItem(View paramView, int paramInt, Object paramObject) {}
  
  public void setPrimaryItem(ViewGroup paramViewGroup, int paramInt, Object paramObject) {
    setPrimaryItem((View)paramViewGroup, paramInt, paramObject);
  }
  
  void setViewPagerObserver(DataSetObserver paramDataSetObserver) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: putfield mViewPagerObserver : Landroid/database/DataSetObserver;
    //   7: aload_0
    //   8: monitorexit
    //   9: return
    //   10: astore_1
    //   11: aload_0
    //   12: monitorexit
    //   13: aload_1
    //   14: athrow
    // Exception table:
    //   from	to	target	type
    //   2	9	10	finally
    //   11	13	10	finally
  }
  
  @Deprecated
  public void startUpdate(View paramView) {}
  
  public void startUpdate(ViewGroup paramViewGroup) {
    startUpdate((View)paramViewGroup);
  }
  
  public void unregisterDataSetObserver(DataSetObserver paramDataSetObserver) {
    this.mObservable.unregisterObserver(paramDataSetObserver);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\PagerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */