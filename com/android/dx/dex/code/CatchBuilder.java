package com.android.dx.dex.code;

import com.android.dx.rop.type.Type;
import java.util.HashSet;

public interface CatchBuilder {
  CatchTable build();
  
  HashSet<Type> getCatchTypes();
  
  boolean hasAnyCatches();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\CatchBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */