package com.android.dx.cf.code;

import com.android.dx.rop.code.LocalItem;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.type.Prototype;
import com.android.dx.rop.type.Type;
import java.util.ArrayList;

public interface Machine {
  void auxCstArg(Constant paramConstant);
  
  void auxInitValues(ArrayList<Constant> paramArrayList);
  
  void auxIntArg(int paramInt);
  
  void auxSwitchArg(SwitchList paramSwitchList);
  
  void auxTargetArg(int paramInt);
  
  void auxType(Type paramType);
  
  void clearArgs();
  
  Prototype getPrototype();
  
  void localArg(Frame paramFrame, int paramInt);
  
  void localInfo(boolean paramBoolean);
  
  void localTarget(int paramInt, Type paramType, LocalItem paramLocalItem);
  
  void popArgs(Frame paramFrame, int paramInt);
  
  void popArgs(Frame paramFrame, Prototype paramPrototype);
  
  void popArgs(Frame paramFrame, Type paramType);
  
  void popArgs(Frame paramFrame, Type paramType1, Type paramType2);
  
  void popArgs(Frame paramFrame, Type paramType1, Type paramType2, Type paramType3);
  
  void run(Frame paramFrame, int paramInt1, int paramInt2);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\Machine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */