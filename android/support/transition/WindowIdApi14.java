package android.support.transition;

import android.os.IBinder;

class WindowIdApi14 implements WindowIdImpl {
  private final IBinder mToken;
  
  WindowIdApi14(IBinder paramIBinder) {
    this.mToken = paramIBinder;
  }
  
  public boolean equals(Object paramObject) {
    boolean bool;
    if (paramObject instanceof WindowIdApi14 && ((WindowIdApi14)paramObject).mToken.equals(this.mToken)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int hashCode() {
    return this.mToken.hashCode();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\WindowIdApi14.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */