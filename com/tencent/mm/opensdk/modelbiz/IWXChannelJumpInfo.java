package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;

public interface IWXChannelJumpInfo {
  public static final int WX_CHANNEL_JUMP_TYPE_MINI_PROGRAM = 1;
  
  public static final int WX_CHANNEL_JUMP_TYPE_UNKNOWN = 0;
  
  public static final int WX_CHANNEL_JUMP_TYPE_URL = 2;
  
  boolean checkArgs();
  
  void serialize(Bundle paramBundle);
  
  int type();
  
  void unserialize(Bundle paramBundle);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\IWXChannelJumpInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */