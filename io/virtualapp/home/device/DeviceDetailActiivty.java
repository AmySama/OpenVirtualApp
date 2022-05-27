package io.virtualapp.home.device;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VDeviceManager;
import com.lody.virtual.remote.VDeviceConfig;
import com.stub.StubApp;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.home.models.DeviceData;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DeviceDetailActiivty extends VActivity {
  private static final String TAG = "DeviceData";
  
  private EditText edt_androidId;
  
  private EditText edt_board;
  
  private EditText edt_brand;
  
  private EditText edt_device;
  
  private EditText edt_display;
  
  private EditText edt_fingerprint;
  
  private EditText edt_id;
  
  private EditText edt_imei;
  
  private EditText edt_imsi;
  
  private EditText edt_mac;
  
  private EditText edt_manufacturer;
  
  private EditText edt_model;
  
  private EditText edt_name;
  
  private EditText edt_serial;
  
  private VDeviceConfig mDeviceConfig;
  
  private String mPackageName;
  
  private int mPosition;
  
  private TelephonyManager mTelephonyManager;
  
  private String mTitle;
  
  private int mUserId;
  
  private WifiManager mWifiManager;
  
  static {
    StubApp.interface11(9741);
  }
  
  private void fillConfig() {
    this.mDeviceConfig.setProp("BRAND", getValue(this.edt_brand));
    this.mDeviceConfig.setProp("MODEL", getValue(this.edt_model));
    this.mDeviceConfig.setProp("PRODUCT", getValue(this.edt_name));
    this.mDeviceConfig.setProp("DEVICE", getValue(this.edt_device));
    this.mDeviceConfig.setProp("BOARD", getValue(this.edt_board));
    this.mDeviceConfig.setProp("DISPLAY", getValue(this.edt_display));
    this.mDeviceConfig.setProp("ID", getValue(this.edt_id));
    this.mDeviceConfig.setProp("MANUFACTURER", getValue(this.edt_manufacturer));
    this.mDeviceConfig.setProp("FINGERPRINT", getValue(this.edt_fingerprint));
    this.mDeviceConfig.serial = getValue(this.edt_serial);
    this.mDeviceConfig.deviceId = getValue(this.edt_imei);
    this.mDeviceConfig.iccId = getValue(this.edt_imsi);
    this.mDeviceConfig.wifiMac = getValue(this.edt_mac);
    this.mDeviceConfig.androidId = getValue(this.edt_androidId);
  }
  
  private String getDefaultWifiMac() {
    String str1 = this.mWifiManager.getConnectionInfo().getMacAddress();
    String str2 = str1;
    if (TextUtils.isEmpty(str1)) {
      byte b = 0;
      while (true) {
        str2 = str1;
        if (b < 3) {
          (new String[3])[0] = "/sys/class/net/wlan0/address";
          (new String[3])[1] = "/sys/class/net/eth0/address";
          (new String[3])[2] = "/sys/class/net/wifi/address";
          str2 = (new String[3])[b];
          try {
            str2 = readFileAsString(str2);
            str1 = str2;
          } catch (IOException iOException) {
            iOException.printStackTrace();
          } 
          if (!TextUtils.isEmpty(str1)) {
            str2 = str1;
            break;
          } 
          b++;
          continue;
        } 
        break;
      } 
    } 
    return str2;
  }
  
  private String getValue(EditText paramEditText) {
    return paramEditText.getText().toString().trim();
  }
  
  private void killApp() {
    if (TextUtils.isEmpty(this.mPackageName)) {
      VirtualCore.get().killAllApps();
    } else {
      VirtualCore.get().killApp(this.mPackageName, this.mUserId);
    } 
  }
  
  public static void open(Fragment paramFragment, DeviceData paramDeviceData, int paramInt) {
    Intent intent = new Intent(paramFragment.getContext(), DeviceDetailActiivty.class);
    intent.putExtra("title", paramDeviceData.name);
    intent.putExtra("pkg", paramDeviceData.packageName);
    intent.putExtra("user", paramDeviceData.userId);
    intent.putExtra("pos", paramInt);
    paramFragment.startActivityForResult(intent, 1001);
  }
  
  private String readFileAsString(String paramString) throws IOException {
    StringBuilder stringBuilder = new StringBuilder(1000);
    BufferedReader bufferedReader = new BufferedReader(new FileReader(paramString));
    char[] arrayOfChar = new char[1024];
    while (true) {
      int i = bufferedReader.read(arrayOfChar);
      if (i != -1) {
        stringBuilder.append(String.valueOf(arrayOfChar, 0, i));
        continue;
      } 
      bufferedReader.close();
      return stringBuilder.toString().trim();
    } 
  }
  
  private void setValue(EditText paramEditText, String paramString1, String paramString2) {
    if (TextUtils.isEmpty(paramString1)) {
      paramEditText.setText(paramString2);
      return;
    } 
    paramEditText.setText(paramString1);
  }
  
  private void updateConfig() {
    setValue(this.edt_brand, this.mDeviceConfig.getProp("BRAND"), Build.BRAND);
    setValue(this.edt_model, this.mDeviceConfig.getProp("MODEL"), Build.MODEL);
    setValue(this.edt_name, this.mDeviceConfig.getProp("PRODUCT"), Build.PRODUCT);
    setValue(this.edt_device, this.mDeviceConfig.getProp("DEVICE"), Build.DEVICE);
    setValue(this.edt_board, this.mDeviceConfig.getProp("BOARD"), Build.BOARD);
    setValue(this.edt_display, this.mDeviceConfig.getProp("DISPLAY"), Build.DISPLAY);
    setValue(this.edt_id, this.mDeviceConfig.getProp("ID"), Build.ID);
    setValue(this.edt_manufacturer, this.mDeviceConfig.getProp("MANUFACTURER"), Build.MANUFACTURER);
    setValue(this.edt_fingerprint, this.mDeviceConfig.getProp("FINGERPRINT"), Build.FINGERPRINT);
    setValue(this.edt_serial, this.mDeviceConfig.serial, Build.SERIAL);
    setValue(this.edt_imei, this.mDeviceConfig.deviceId, this.mTelephonyManager.getDeviceId());
    setValue(this.edt_imsi, this.mDeviceConfig.iccId, this.mTelephonyManager.getSimSerialNumber());
    setValue(this.edt_mac, this.mDeviceConfig.wifiMac, getDefaultWifiMac());
    setValue(this.edt_androidId, this.mDeviceConfig.androidId, Settings.Secure.getString(getContentResolver(), "android_id"));
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public boolean onCreateOptionsMenu(Menu paramMenu) {
    getMenuInflater().inflate(2131492866, paramMenu);
    return true;
  }
  
  protected void onNewIntent(Intent paramIntent) {
    super.onNewIntent(paramIntent);
    this.mPackageName = paramIntent.getStringExtra("pkg");
    this.mUserId = paramIntent.getIntExtra("user", 0);
    this.mTitle = paramIntent.getStringExtra("title");
    this.mPosition = paramIntent.getIntExtra("pos", -1);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
    Intent intent;
    switch (paramMenuItem.getItemId()) {
      default:
        return super.onOptionsItemSelected(paramMenuItem);
      case 2131296292:
        this.mDeviceConfig.enable = true;
        fillConfig();
        updateConfig();
        VDeviceManager.get().updateDeviceConfig(this.mUserId, this.mDeviceConfig);
        intent = new Intent();
        intent.putExtra("pkg", this.mPackageName);
        intent.putExtra("user", this.mUserId);
        intent.putExtra("pos", this.mPosition);
        intent.putExtra("result", "save");
        setResult(-1, intent);
        if (TextUtils.isEmpty(this.mPackageName)) {
          VirtualCore.get().killAllApps();
        } else {
          VirtualCore.get().killApp(this.mPackageName, this.mUserId);
        } 
        killApp();
        Toast.makeText((Context)this, "保存成功", 0).show();
        return true;
      case 2131296291:
        break;
    } 
    (new AlertDialog.Builder((Context)this)).setMessage(2131624000).setPositiveButton(17039370, new _$$Lambda$DeviceDetailActiivty$iHF0LKjZrunn_kjDqpoPI7jg4eo(this)).setNegativeButton(17039360, (DialogInterface.OnClickListener)_$$Lambda$DeviceDetailActiivty$QsIOO_4X0o4qp7s4PV2jnZnZSN8.INSTANCE).setCancelable(false).show();
    return true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\device\DeviceDetailActiivty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */