package com.lody.virtual.client.badger;

import android.content.Intent;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.remote.BadgerInfo;
import java.util.HashMap;
import java.util.Map;

public class BadgerManager {
  private static final Map<String, IBadger> BADGERS = new HashMap<String, IBadger>(10);
  
  static {
    addBadger(new BroadcastBadger1.AdwHomeBadger());
    addBadger(new BroadcastBadger1.AospHomeBadger());
    addBadger(new BroadcastBadger1.LGHomeBadger());
    addBadger(new BroadcastBadger1.NewHtcHomeBadger2());
    addBadger(new BroadcastBadger1.OPPOHomeBader());
    addBadger(new BroadcastBadger2.NewHtcHomeBadger1());
  }
  
  private static void addBadger(IBadger paramIBadger) {
    BADGERS.put(paramIBadger.getAction(), paramIBadger);
  }
  
  public static boolean handleBadger(Intent paramIntent) {
    IBadger iBadger = BADGERS.get(paramIntent.getAction());
    if (iBadger != null) {
      BadgerInfo badgerInfo = iBadger.handleBadger(paramIntent);
      VActivityManager.get().notifyBadgerChange(badgerInfo);
      return true;
    } 
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\badger\BadgerManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */