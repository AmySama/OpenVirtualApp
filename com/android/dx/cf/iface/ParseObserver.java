package com.android.dx.cf.iface;

import com.android.dx.util.ByteArray;

public interface ParseObserver {
  void changeIndent(int paramInt);
  
  void endParsingMember(ByteArray paramByteArray, int paramInt, String paramString1, String paramString2, Member paramMember);
  
  void parsed(ByteArray paramByteArray, int paramInt1, int paramInt2, String paramString);
  
  void startParsingMember(ByteArray paramByteArray, int paramInt, String paramString1, String paramString2);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\iface\ParseObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */