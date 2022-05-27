package com.lody.virtual.client.badger;

import android.content.Intent;
import com.lody.virtual.remote.BadgerInfo;

public interface IBadger {
  String getAction();
  
  BadgerInfo handleBadger(Intent paramIntent);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\badger\IBadger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */