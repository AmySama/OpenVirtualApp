package io.virtualapp.abs.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseAdapterPlus<T> extends BaseAdapter implements SpinnerAdapter {
  protected Context context;
  
  protected final List<T> mItems = new ArrayList<T>();
  
  private LayoutInflater mLayoutInflater;
  
  public BaseAdapterPlus(Context paramContext) {
    this.context = paramContext;
    this.mLayoutInflater = LayoutInflater.from(paramContext);
  }
  
  public boolean add(int paramInt, T paramT, boolean paramBoolean) {
    if (paramT != null) {
      if (paramBoolean && exist(paramT))
        return false; 
      if (paramInt >= 0) {
        this.mItems.add(paramInt, paramT);
      } else {
        this.mItems.add(paramT);
      } 
    } 
    return true;
  }
  
  public boolean add(T paramT) {
    return add(-1, paramT, false);
  }
  
  public void addAll(Collection<T> paramCollection) {
    if (paramCollection != null)
      this.mItems.addAll(paramCollection); 
  }
  
  protected abstract void attach(View paramView, T paramT, int paramInt);
  
  public void clear() {
    this.mItems.clear();
  }
  
  protected abstract View createView(int paramInt, ViewGroup paramViewGroup);
  
  public boolean exist(T paramT) {
    return (paramT == null) ? false : this.mItems.contains(paramT);
  }
  
  public int findItem(T paramT) {
    return this.mItems.indexOf(paramT);
  }
  
  public Context getContext() {
    return this.context;
  }
  
  public final int getCount() {
    return this.mItems.size();
  }
  
  public final T getDataItem(int paramInt) {
    return this.mItems.get(paramInt);
  }
  
  public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    View view = paramView;
    if (paramView == null)
      view = createView(paramInt, paramViewGroup); 
    attach(view, getItem(paramInt), paramInt);
    return view;
  }
  
  public final T getItem(int paramInt) {
    return (paramInt >= 0 && paramInt < getCount()) ? this.mItems.get(paramInt) : null;
  }
  
  public final T getItemById(long paramLong) {
    return getItem((int)paramLong);
  }
  
  public long getItemId(int paramInt) {
    return paramInt;
  }
  
  public List<T> getItems() {
    return this.mItems;
  }
  
  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    View view = paramView;
    if (paramView == null)
      view = createView(paramInt, paramViewGroup); 
    attach(view, getItem(paramInt), paramInt);
    return view;
  }
  
  protected <VW extends View> VW inflate(int paramInt, ViewGroup paramViewGroup) {
    return (VW)this.mLayoutInflater.inflate(paramInt, paramViewGroup);
  }
  
  protected <VW extends View> VW inflate(int paramInt, ViewGroup paramViewGroup, boolean paramBoolean) {
    return (VW)this.mLayoutInflater.inflate(paramInt, paramViewGroup, paramBoolean);
  }
  
  public T remove(int paramInt) {
    return this.mItems.remove(paramInt);
  }
  
  public void set(Collection<T> paramCollection) {
    clear();
    addAll(paramCollection);
  }
  
  public static class BaseViewHolder {
    protected Context context;
    
    protected View view;
    
    public BaseViewHolder(View param1View) {
      this.view = param1View;
      this.context = param1View.getContext();
    }
    
    protected <T extends View> T $(int param1Int) {
      return (T)this.view.findViewById(param1Int);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\ab\\ui\BaseAdapterPlus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */