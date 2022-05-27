package com.lody.virtual.client.stub;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.compat.BundleCompat;
import com.lody.virtual.remote.ClientConfig;

public class ShadowContentProvider extends ContentProvider {
  private Bundle initProcess(Bundle paramBundle) {
    VirtualCore.get().waitStartup();
    paramBundle.setClassLoader(ClientConfig.class.getClassLoader());
    ClientConfig clientConfig = (ClientConfig)paramBundle.getParcelable("_VA_|_client_config_");
    VClient vClient = VClient.get();
    vClient.initProcess(clientConfig);
    Bundle bundle = new Bundle();
    BundleCompat.putBinder(bundle, "_VA_|_client_", vClient.asBinder());
    bundle.putInt("_VA_|_pid_", Process.myPid());
    return bundle;
  }
  
  public Bundle call(String paramString1, String paramString2, Bundle paramBundle) {
    return "_VA_|_init_process_".equals(paramString1) ? initProcess(paramBundle) : null;
  }
  
  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString) {
    return 0;
  }
  
  public String getType(Uri paramUri) {
    return null;
  }
  
  public Uri insert(Uri paramUri, ContentValues paramContentValues) {
    return null;
  }
  
  public boolean onCreate() {
    return true;
  }
  
  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2) {
    return null;
  }
  
  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString) {
    return 0;
  }
  
  public static class P0 extends ShadowContentProvider {}
  
  public static class P1 extends ShadowContentProvider {}
  
  public static class P10 extends ShadowContentProvider {}
  
  public static class P11 extends ShadowContentProvider {}
  
  public static class P12 extends ShadowContentProvider {}
  
  public static class P13 extends ShadowContentProvider {}
  
  public static class P14 extends ShadowContentProvider {}
  
  public static class P15 extends ShadowContentProvider {}
  
  public static class P16 extends ShadowContentProvider {}
  
  public static class P17 extends ShadowContentProvider {}
  
  public static class P18 extends ShadowContentProvider {}
  
  public static class P19 extends ShadowContentProvider {}
  
  public static class P2 extends ShadowContentProvider {}
  
  public static class P20 extends ShadowContentProvider {}
  
  public static class P21 extends ShadowContentProvider {}
  
  public static class P22 extends ShadowContentProvider {}
  
  public static class P23 extends ShadowContentProvider {}
  
  public static class P24 extends ShadowContentProvider {}
  
  public static class P25 extends ShadowContentProvider {}
  
  public static class P26 extends ShadowContentProvider {}
  
  public static class P27 extends ShadowContentProvider {}
  
  public static class P28 extends ShadowContentProvider {}
  
  public static class P29 extends ShadowContentProvider {}
  
  public static class P3 extends ShadowContentProvider {}
  
  public static class P30 extends ShadowContentProvider {}
  
  public static class P31 extends ShadowContentProvider {}
  
  public static class P32 extends ShadowContentProvider {}
  
  public static class P33 extends ShadowContentProvider {}
  
  public static class P34 extends ShadowContentProvider {}
  
  public static class P35 extends ShadowContentProvider {}
  
  public static class P36 extends ShadowContentProvider {}
  
  public static class P37 extends ShadowContentProvider {}
  
  public static class P38 extends ShadowContentProvider {}
  
  public static class P39 extends ShadowContentProvider {}
  
  public static class P4 extends ShadowContentProvider {}
  
  public static class P40 extends ShadowContentProvider {}
  
  public static class P41 extends ShadowContentProvider {}
  
  public static class P42 extends ShadowContentProvider {}
  
  public static class P43 extends ShadowContentProvider {}
  
  public static class P44 extends ShadowContentProvider {}
  
  public static class P45 extends ShadowContentProvider {}
  
  public static class P46 extends ShadowContentProvider {}
  
  public static class P47 extends ShadowContentProvider {}
  
  public static class P48 extends ShadowContentProvider {}
  
  public static class P49 extends ShadowContentProvider {}
  
  public static class P5 extends ShadowContentProvider {}
  
  public static class P50 extends ShadowContentProvider {}
  
  public static class P51 extends ShadowContentProvider {}
  
  public static class P52 extends ShadowContentProvider {}
  
  public static class P53 extends ShadowContentProvider {}
  
  public static class P54 extends ShadowContentProvider {}
  
  public static class P55 extends ShadowContentProvider {}
  
  public static class P56 extends ShadowContentProvider {}
  
  public static class P57 extends ShadowContentProvider {}
  
  public static class P58 extends ShadowContentProvider {}
  
  public static class P59 extends ShadowContentProvider {}
  
  public static class P6 extends ShadowContentProvider {}
  
  public static class P60 extends ShadowContentProvider {}
  
  public static class P61 extends ShadowContentProvider {}
  
  public static class P62 extends ShadowContentProvider {}
  
  public static class P63 extends ShadowContentProvider {}
  
  public static class P64 extends ShadowContentProvider {}
  
  public static class P65 extends ShadowContentProvider {}
  
  public static class P66 extends ShadowContentProvider {}
  
  public static class P67 extends ShadowContentProvider {}
  
  public static class P68 extends ShadowContentProvider {}
  
  public static class P69 extends ShadowContentProvider {}
  
  public static class P7 extends ShadowContentProvider {}
  
  public static class P70 extends ShadowContentProvider {}
  
  public static class P71 extends ShadowContentProvider {}
  
  public static class P72 extends ShadowContentProvider {}
  
  public static class P73 extends ShadowContentProvider {}
  
  public static class P74 extends ShadowContentProvider {}
  
  public static class P75 extends ShadowContentProvider {}
  
  public static class P76 extends ShadowContentProvider {}
  
  public static class P77 extends ShadowContentProvider {}
  
  public static class P78 extends ShadowContentProvider {}
  
  public static class P79 extends ShadowContentProvider {}
  
  public static class P8 extends ShadowContentProvider {}
  
  public static class P80 extends ShadowContentProvider {}
  
  public static class P81 extends ShadowContentProvider {}
  
  public static class P82 extends ShadowContentProvider {}
  
  public static class P83 extends ShadowContentProvider {}
  
  public static class P84 extends ShadowContentProvider {}
  
  public static class P85 extends ShadowContentProvider {}
  
  public static class P86 extends ShadowContentProvider {}
  
  public static class P87 extends ShadowContentProvider {}
  
  public static class P88 extends ShadowContentProvider {}
  
  public static class P89 extends ShadowContentProvider {}
  
  public static class P9 extends ShadowContentProvider {}
  
  public static class P90 extends ShadowContentProvider {}
  
  public static class P91 extends ShadowContentProvider {}
  
  public static class P92 extends ShadowContentProvider {}
  
  public static class P93 extends ShadowContentProvider {}
  
  public static class P94 extends ShadowContentProvider {}
  
  public static class P95 extends ShadowContentProvider {}
  
  public static class P96 extends ShadowContentProvider {}
  
  public static class P97 extends ShadowContentProvider {}
  
  public static class P98 extends ShadowContentProvider {}
  
  public static class P99 extends ShadowContentProvider {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\ShadowContentProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */