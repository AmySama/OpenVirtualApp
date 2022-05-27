package android.support.v4.widget;

import android.database.Cursor;
import android.widget.Filter;

class CursorFilter extends Filter {
  CursorFilterClient mClient;
  
  CursorFilter(CursorFilterClient paramCursorFilterClient) {
    this.mClient = paramCursorFilterClient;
  }
  
  public CharSequence convertResultToString(Object paramObject) {
    return this.mClient.convertToString((Cursor)paramObject);
  }
  
  protected Filter.FilterResults performFiltering(CharSequence paramCharSequence) {
    Cursor cursor = this.mClient.runQueryOnBackgroundThread(paramCharSequence);
    Filter.FilterResults filterResults = new Filter.FilterResults();
    if (cursor != null) {
      filterResults.count = cursor.getCount();
      filterResults.values = cursor;
    } else {
      filterResults.count = 0;
      filterResults.values = null;
    } 
    return filterResults;
  }
  
  protected void publishResults(CharSequence paramCharSequence, Filter.FilterResults paramFilterResults) {
    Cursor cursor = this.mClient.getCursor();
    if (paramFilterResults.values != null && paramFilterResults.values != cursor)
      this.mClient.changeCursor((Cursor)paramFilterResults.values); 
  }
  
  static interface CursorFilterClient {
    void changeCursor(Cursor param1Cursor);
    
    CharSequence convertToString(Cursor param1Cursor);
    
    Cursor getCursor();
    
    Cursor runQueryOnBackgroundThread(CharSequence param1CharSequence);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\CursorFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */