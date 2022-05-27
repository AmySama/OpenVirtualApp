package android.support.v4.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;

public abstract class CursorAdapter extends BaseAdapter implements Filterable, CursorFilter.CursorFilterClient {
  @Deprecated
  public static final int FLAG_AUTO_REQUERY = 1;
  
  public static final int FLAG_REGISTER_CONTENT_OBSERVER = 2;
  
  protected boolean mAutoRequery;
  
  protected ChangeObserver mChangeObserver;
  
  protected Context mContext;
  
  protected Cursor mCursor;
  
  protected CursorFilter mCursorFilter;
  
  protected DataSetObserver mDataSetObserver;
  
  protected boolean mDataValid;
  
  protected FilterQueryProvider mFilterQueryProvider;
  
  protected int mRowIDColumn;
  
  @Deprecated
  public CursorAdapter(Context paramContext, Cursor paramCursor) {
    init(paramContext, paramCursor, 1);
  }
  
  public CursorAdapter(Context paramContext, Cursor paramCursor, int paramInt) {
    init(paramContext, paramCursor, paramInt);
  }
  
  public CursorAdapter(Context paramContext, Cursor paramCursor, boolean paramBoolean) {
    byte b;
    if (paramBoolean) {
      b = 1;
    } else {
      b = 2;
    } 
    init(paramContext, paramCursor, b);
  }
  
  public abstract void bindView(View paramView, Context paramContext, Cursor paramCursor);
  
  public void changeCursor(Cursor paramCursor) {
    paramCursor = swapCursor(paramCursor);
    if (paramCursor != null)
      paramCursor.close(); 
  }
  
  public CharSequence convertToString(Cursor paramCursor) {
    String str;
    if (paramCursor == null) {
      str = "";
    } else {
      str = str.toString();
    } 
    return str;
  }
  
  public int getCount() {
    if (this.mDataValid) {
      Cursor cursor = this.mCursor;
      if (cursor != null)
        return cursor.getCount(); 
    } 
    return 0;
  }
  
  public Cursor getCursor() {
    return this.mCursor;
  }
  
  public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (this.mDataValid) {
      this.mCursor.moveToPosition(paramInt);
      View view = paramView;
      if (paramView == null)
        view = newDropDownView(this.mContext, this.mCursor, paramViewGroup); 
      bindView(view, this.mContext, this.mCursor);
      return view;
    } 
    return null;
  }
  
  public Filter getFilter() {
    if (this.mCursorFilter == null)
      this.mCursorFilter = new CursorFilter(this); 
    return this.mCursorFilter;
  }
  
  public FilterQueryProvider getFilterQueryProvider() {
    return this.mFilterQueryProvider;
  }
  
  public Object getItem(int paramInt) {
    if (this.mDataValid) {
      Cursor cursor = this.mCursor;
      if (cursor != null) {
        cursor.moveToPosition(paramInt);
        return this.mCursor;
      } 
    } 
    return null;
  }
  
  public long getItemId(int paramInt) {
    if (this.mDataValid) {
      Cursor cursor = this.mCursor;
      if (cursor != null && cursor.moveToPosition(paramInt))
        return this.mCursor.getLong(this.mRowIDColumn); 
    } 
    return 0L;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (this.mDataValid) {
      if (this.mCursor.moveToPosition(paramInt)) {
        View view = paramView;
        if (paramView == null)
          view = newView(this.mContext, this.mCursor, paramViewGroup); 
        bindView(view, this.mContext, this.mCursor);
        return view;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("couldn't move cursor to position ");
      stringBuilder.append(paramInt);
      throw new IllegalStateException(stringBuilder.toString());
    } 
    throw new IllegalStateException("this should only be called when the cursor is valid");
  }
  
  public boolean hasStableIds() {
    return true;
  }
  
  void init(Context paramContext, Cursor paramCursor, int paramInt) {
    byte b;
    boolean bool = false;
    if ((paramInt & 0x1) == 1) {
      paramInt |= 0x2;
      this.mAutoRequery = true;
    } else {
      this.mAutoRequery = false;
    } 
    if (paramCursor != null)
      bool = true; 
    this.mCursor = paramCursor;
    this.mDataValid = bool;
    this.mContext = paramContext;
    if (bool) {
      b = paramCursor.getColumnIndexOrThrow("_id");
    } else {
      b = -1;
    } 
    this.mRowIDColumn = b;
    if ((paramInt & 0x2) == 2) {
      this.mChangeObserver = new ChangeObserver();
      this.mDataSetObserver = new MyDataSetObserver();
    } else {
      this.mChangeObserver = null;
      this.mDataSetObserver = null;
    } 
    if (bool) {
      ChangeObserver changeObserver = this.mChangeObserver;
      if (changeObserver != null)
        paramCursor.registerContentObserver(changeObserver); 
      DataSetObserver dataSetObserver = this.mDataSetObserver;
      if (dataSetObserver != null)
        paramCursor.registerDataSetObserver(dataSetObserver); 
    } 
  }
  
  @Deprecated
  protected void init(Context paramContext, Cursor paramCursor, boolean paramBoolean) {
    byte b;
    if (paramBoolean) {
      b = 1;
    } else {
      b = 2;
    } 
    init(paramContext, paramCursor, b);
  }
  
  public View newDropDownView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup) {
    return newView(paramContext, paramCursor, paramViewGroup);
  }
  
  public abstract View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup);
  
  protected void onContentChanged() {
    if (this.mAutoRequery) {
      Cursor cursor = this.mCursor;
      if (cursor != null && !cursor.isClosed())
        this.mDataValid = this.mCursor.requery(); 
    } 
  }
  
  public Cursor runQueryOnBackgroundThread(CharSequence paramCharSequence) {
    FilterQueryProvider filterQueryProvider = this.mFilterQueryProvider;
    return (filterQueryProvider != null) ? filterQueryProvider.runQuery(paramCharSequence) : this.mCursor;
  }
  
  public void setFilterQueryProvider(FilterQueryProvider paramFilterQueryProvider) {
    this.mFilterQueryProvider = paramFilterQueryProvider;
  }
  
  public Cursor swapCursor(Cursor paramCursor) {
    Cursor cursor = this.mCursor;
    if (paramCursor == cursor)
      return null; 
    if (cursor != null) {
      ChangeObserver changeObserver = this.mChangeObserver;
      if (changeObserver != null)
        cursor.unregisterContentObserver(changeObserver); 
      DataSetObserver dataSetObserver = this.mDataSetObserver;
      if (dataSetObserver != null)
        cursor.unregisterDataSetObserver(dataSetObserver); 
    } 
    this.mCursor = paramCursor;
    if (paramCursor != null) {
      ChangeObserver changeObserver = this.mChangeObserver;
      if (changeObserver != null)
        paramCursor.registerContentObserver(changeObserver); 
      DataSetObserver dataSetObserver = this.mDataSetObserver;
      if (dataSetObserver != null)
        paramCursor.registerDataSetObserver(dataSetObserver); 
      this.mRowIDColumn = paramCursor.getColumnIndexOrThrow("_id");
      this.mDataValid = true;
      notifyDataSetChanged();
    } else {
      this.mRowIDColumn = -1;
      this.mDataValid = false;
      notifyDataSetInvalidated();
    } 
    return cursor;
  }
  
  private class ChangeObserver extends ContentObserver {
    ChangeObserver() {
      super(new Handler());
    }
    
    public boolean deliverSelfNotifications() {
      return true;
    }
    
    public void onChange(boolean param1Boolean) {
      CursorAdapter.this.onContentChanged();
    }
  }
  
  private class MyDataSetObserver extends DataSetObserver {
    public void onChanged() {
      CursorAdapter.this.mDataValid = true;
      CursorAdapter.this.notifyDataSetChanged();
    }
    
    public void onInvalidated() {
      CursorAdapter.this.mDataValid = false;
      CursorAdapter.this.notifyDataSetInvalidated();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\CursorAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */