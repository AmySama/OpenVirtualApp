package com.android.dx.cf.code;

import com.android.dex.util.ExceptionWithContext;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.MutabilityControl;
import com.android.dx.util.ToHuman;

public abstract class LocalsArray extends MutabilityControl implements ToHuman {
  protected LocalsArray(boolean paramBoolean) {
    super(paramBoolean);
  }
  
  public abstract void annotate(ExceptionWithContext paramExceptionWithContext);
  
  public abstract LocalsArray copy();
  
  public abstract TypeBearer get(int paramInt);
  
  public abstract TypeBearer getCategory1(int paramInt);
  
  public abstract TypeBearer getCategory2(int paramInt);
  
  public abstract int getMaxLocals();
  
  public abstract TypeBearer getOrNull(int paramInt);
  
  protected abstract OneLocalsArray getPrimary();
  
  public abstract void invalidate(int paramInt);
  
  public abstract void makeInitialized(Type paramType);
  
  public abstract LocalsArray merge(LocalsArray paramLocalsArray);
  
  public abstract LocalsArraySet mergeWithSubroutineCaller(LocalsArray paramLocalsArray, int paramInt);
  
  public abstract void set(int paramInt, TypeBearer paramTypeBearer);
  
  public abstract void set(RegisterSpec paramRegisterSpec);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\LocalsArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */