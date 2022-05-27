package android.support.v4.widget;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class ResourceCursorAdapter extends CursorAdapter {
  private int mDropDownLayout;
  
  private LayoutInflater mInflater;
  
  private int mLayout;
  
  @Deprecated
  public ResourceCursorAdapter(Context paramContext, int paramInt, Cursor paramCursor) {
    super(paramContext, paramCursor);
    this.mDropDownLayout = paramInt;
    this.mLayout = paramInt;
    this.mInflater = (LayoutInflater)paramContext.getSystemService("layout_inflater");
  }
  
  public ResourceCursorAdapter(Context paramContext, int paramInt1, Cursor paramCursor, int paramInt2) {
    super(paramContext, paramCursor, paramInt2);
    this.mDropDownLayout = paramInt1;
    this.mLayout = paramInt1;
    this.mInflater = (LayoutInflater)paramContext.getSystemService("layout_inflater");
  }
  
  @Deprecated
  public ResourceCursorAdapter(Context paramContext, int paramInt, Cursor paramCursor, boolean paramBoolean) {
    super(paramContext, paramCursor, paramBoolean);
    this.mDropDownLayout = paramInt;
    this.mLayout = paramInt;
    this.mInflater = (LayoutInflater)paramContext.getSystemService("layout_inflater");
  }
  
  public View newDropDownView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup) {
    return this.mInflater.inflate(this.mDropDownLayout, paramViewGroup, false);
  }
  
  public View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup) {
    return this.mInflater.inflate(this.mLayout, paramViewGroup, false);
  }
  
  public void setDropDownViewResource(int paramInt) {
    this.mDropDownLayout = paramInt;
  }
  
  public void setViewResource(int paramInt) {
    this.mLayout = paramInt;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\ResourceCursorAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */