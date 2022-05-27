package com.lody.virtual.server.pm;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;
import com.lody.virtual.client.core.VirtualCore;
import java.util.Map;

public class ComponentStateManager {
  private static SparseArray<UserComponentState> helpers = new SparseArray();
  
  public static UserComponentState user(int paramInt) {
    // Byte code:
    //   0: ldc com/lody/virtual/server/pm/ComponentStateManager
    //   2: monitorenter
    //   3: getstatic com/lody/virtual/server/pm/ComponentStateManager.helpers : Landroid/util/SparseArray;
    //   6: iload_0
    //   7: invokevirtual get : (I)Ljava/lang/Object;
    //   10: checkcast com/lody/virtual/server/pm/ComponentStateManager$UserComponentState
    //   13: astore_1
    //   14: aload_1
    //   15: astore_2
    //   16: aload_1
    //   17: ifnonnull -> 38
    //   20: new com/lody/virtual/server/pm/ComponentStateManager$UserComponentState
    //   23: astore_2
    //   24: aload_2
    //   25: iload_0
    //   26: aconst_null
    //   27: invokespecial <init> : (ILcom/lody/virtual/server/pm/ComponentStateManager$1;)V
    //   30: getstatic com/lody/virtual/server/pm/ComponentStateManager.helpers : Landroid/util/SparseArray;
    //   33: iload_0
    //   34: aload_2
    //   35: invokevirtual put : (ILjava/lang/Object;)V
    //   38: ldc com/lody/virtual/server/pm/ComponentStateManager
    //   40: monitorexit
    //   41: aload_2
    //   42: areturn
    //   43: astore_2
    //   44: ldc com/lody/virtual/server/pm/ComponentStateManager
    //   46: monitorexit
    //   47: aload_2
    //   48: athrow
    // Exception table:
    //   from	to	target	type
    //   3	14	43	finally
    //   20	38	43	finally
  }
  
  public static class UserComponentState {
    private SharedPreferences sharedPreferences;
    
    private UserComponentState(int param1Int) {
      Context context = VirtualCore.get().getContext();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("va_components_state_u");
      stringBuilder.append(param1Int);
      this.sharedPreferences = context.getSharedPreferences(stringBuilder.toString(), 0);
    }
    
    private String componentKey(ComponentName param1ComponentName) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(param1ComponentName.getPackageName());
      stringBuilder.append("@");
      stringBuilder.append(param1ComponentName.getClassName());
      return stringBuilder.toString();
    }
    
    public void clear(String param1String) {
      Map map = this.sharedPreferences.getAll();
      if (map == null)
        return; 
      for (String str : map.keySet()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(param1String);
        stringBuilder.append("@");
        if (str.startsWith(stringBuilder.toString()))
          this.sharedPreferences.edit().remove(str).apply(); 
      } 
    }
    
    public void clearAll() {
      this.sharedPreferences.edit().clear().apply();
    }
    
    public int get(ComponentName param1ComponentName) {
      return this.sharedPreferences.getInt(componentKey(param1ComponentName), 0);
    }
    
    public void set(ComponentName param1ComponentName, int param1Int) {
      this.sharedPreferences.edit().putInt(componentKey(param1ComponentName), param1Int).apply();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\ComponentStateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */