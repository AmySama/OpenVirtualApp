package android.support.v7.widget;

import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

class ViewBoundsCheck {
  static final int CVE_PVE_POS = 12;
  
  static final int CVE_PVS_POS = 8;
  
  static final int CVS_PVE_POS = 4;
  
  static final int CVS_PVS_POS = 0;
  
  static final int EQ = 2;
  
  static final int FLAG_CVE_EQ_PVE = 8192;
  
  static final int FLAG_CVE_EQ_PVS = 512;
  
  static final int FLAG_CVE_GT_PVE = 4096;
  
  static final int FLAG_CVE_GT_PVS = 256;
  
  static final int FLAG_CVE_LT_PVE = 16384;
  
  static final int FLAG_CVE_LT_PVS = 1024;
  
  static final int FLAG_CVS_EQ_PVE = 32;
  
  static final int FLAG_CVS_EQ_PVS = 2;
  
  static final int FLAG_CVS_GT_PVE = 16;
  
  static final int FLAG_CVS_GT_PVS = 1;
  
  static final int FLAG_CVS_LT_PVE = 64;
  
  static final int FLAG_CVS_LT_PVS = 4;
  
  static final int GT = 1;
  
  static final int LT = 4;
  
  static final int MASK = 7;
  
  BoundFlags mBoundFlags;
  
  final Callback mCallback;
  
  ViewBoundsCheck(Callback paramCallback) {
    this.mCallback = paramCallback;
    this.mBoundFlags = new BoundFlags();
  }
  
  View findOneViewWithinBoundFlags(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    byte b;
    int i = this.mCallback.getParentStart();
    int j = this.mCallback.getParentEnd();
    if (paramInt2 > paramInt1) {
      b = 1;
    } else {
      b = -1;
    } 
    View view;
    for (view = null; paramInt1 != paramInt2; view = view2) {
      View view1 = this.mCallback.getChildAt(paramInt1);
      int k = this.mCallback.getChildStart(view1);
      int m = this.mCallback.getChildEnd(view1);
      this.mBoundFlags.setBounds(i, j, k, m);
      if (paramInt3 != 0) {
        this.mBoundFlags.resetFlags();
        this.mBoundFlags.addFlags(paramInt3);
        if (this.mBoundFlags.boundsMatch())
          return view1; 
      } 
      View view2 = view;
      if (paramInt4 != 0) {
        this.mBoundFlags.resetFlags();
        this.mBoundFlags.addFlags(paramInt4);
        view2 = view;
        if (this.mBoundFlags.boundsMatch())
          view2 = view1; 
      } 
      paramInt1 += b;
    } 
    return view;
  }
  
  boolean isViewWithinBoundFlags(View paramView, int paramInt) {
    this.mBoundFlags.setBounds(this.mCallback.getParentStart(), this.mCallback.getParentEnd(), this.mCallback.getChildStart(paramView), this.mCallback.getChildEnd(paramView));
    if (paramInt != 0) {
      this.mBoundFlags.resetFlags();
      this.mBoundFlags.addFlags(paramInt);
      return this.mBoundFlags.boundsMatch();
    } 
    return false;
  }
  
  static class BoundFlags {
    int mBoundFlags = 0;
    
    int mChildEnd;
    
    int mChildStart;
    
    int mRvEnd;
    
    int mRvStart;
    
    void addFlags(int param1Int) {
      this.mBoundFlags = param1Int | this.mBoundFlags;
    }
    
    boolean boundsMatch() {
      int i = this.mBoundFlags;
      if ((i & 0x7) != 0 && (i & compare(this.mChildStart, this.mRvStart) << 0) == 0)
        return false; 
      i = this.mBoundFlags;
      if ((i & 0x70) != 0 && (i & compare(this.mChildStart, this.mRvEnd) << 4) == 0)
        return false; 
      i = this.mBoundFlags;
      if ((i & 0x700) != 0 && (i & compare(this.mChildEnd, this.mRvStart) << 8) == 0)
        return false; 
      i = this.mBoundFlags;
      return !((i & 0x7000) != 0 && (i & compare(this.mChildEnd, this.mRvEnd) << 12) == 0);
    }
    
    int compare(int param1Int1, int param1Int2) {
      return (param1Int1 > param1Int2) ? 1 : ((param1Int1 == param1Int2) ? 2 : 4);
    }
    
    void resetFlags() {
      this.mBoundFlags = 0;
    }
    
    void setBounds(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      this.mRvStart = param1Int1;
      this.mRvEnd = param1Int2;
      this.mChildStart = param1Int3;
      this.mChildEnd = param1Int4;
    }
    
    void setFlags(int param1Int1, int param1Int2) {
      this.mBoundFlags = param1Int1 & param1Int2 | this.mBoundFlags & param1Int2;
    }
  }
  
  static interface Callback {
    View getChildAt(int param1Int);
    
    int getChildCount();
    
    int getChildEnd(View param1View);
    
    int getChildStart(View param1View);
    
    View getParent();
    
    int getParentEnd();
    
    int getParentStart();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ViewBounds {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\ViewBoundsCheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */