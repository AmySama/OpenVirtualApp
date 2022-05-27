package io.virtualapp.home.location;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VLocationManager;
import com.lody.virtual.client.ipc.VirtualLocationManager;
import com.lody.virtual.remote.InstalledAppInfo;
import com.lody.virtual.remote.vloc.VLocation;
import com.stub.StubApp;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.abs.ui.VUiKit;
import io.virtualapp.home.adapters.AppLocationAdapter;
import io.virtualapp.home.models.LocationData;
import java.util.ArrayList;
import java.util.List;

public class LocationSettingsActivity extends VActivity implements AdapterView.OnItemClickListener {
  private static final int REQUEST_CODE = 1001;
  
  private AppLocationAdapter mAppLocationAdapter;
  
  private LocationData mSelectData;
  
  static {
    StubApp.interface11(9764);
  }
  
  private void loadData() {
    ProgressDialog progressDialog = ProgressDialog.show((Context)this, null, "loading");
    VUiKit.defer().when(new _$$Lambda$LocationSettingsActivity$JOC_xRm6JswGpAsWI0rmkGKUEsU(this)).done(new _$$Lambda$LocationSettingsActivity$c1MdueNcF_WamWJmn8r0XhYU_5o(this, progressDialog)).fail(new _$$Lambda$LocationSettingsActivity$f5r_CaW2vU0WuKCmGzrDQ_tLZhg(progressDialog));
  }
  
  private void readLocation(LocationData paramLocationData) {
    paramLocationData.mode = VirtualLocationManager.get().getMode(paramLocationData.userId, paramLocationData.packageName);
    paramLocationData.location = VLocationManager.get().getLocation(paramLocationData.packageName, paramLocationData.userId);
  }
  
  private void saveLocation(LocationData paramLocationData) {
    VirtualCore.get().killApp(paramLocationData.packageName, paramLocationData.userId);
    if (paramLocationData.location == null || paramLocationData.location.isEmpty()) {
      VirtualLocationManager.get().setMode(paramLocationData.userId, paramLocationData.packageName, 0);
    } else if (paramLocationData.mode != 2) {
      VirtualLocationManager.get().setMode(paramLocationData.userId, paramLocationData.packageName, 2);
    } 
    VirtualLocationManager.get().setLocation(paramLocationData.userId, paramLocationData.packageName, paramLocationData.location);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    if (paramInt1 == 1001 && paramInt2 == -1) {
      VLocation vLocation = (VLocation)paramIntent.getParcelableExtra("virtual_location");
      LocationData locationData = this.mSelectData;
      if (locationData != null) {
        locationData.location = vLocation;
        saveLocation(this.mSelectData);
        this.mSelectData = null;
        loadData();
      } 
    } 
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
    this.mSelectData = (LocationData)this.mAppLocationAdapter.getItem(paramInt);
    Intent intent = new Intent((Context)this, ChooseLocationActivity.class);
    if (this.mSelectData.location != null)
      intent.putExtra("virtual_location", (Parcelable)this.mSelectData.location); 
    intent.putExtra("virtual.extras.package", this.mSelectData.packageName);
    intent.putExtra("virtual.extras.userid", this.mSelectData.userId);
    startActivityForResult(intent, 1001);
  }
  
  public native void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\location\LocationSettingsActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */