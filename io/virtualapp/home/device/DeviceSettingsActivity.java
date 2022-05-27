package io.virtualapp.home.device;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.stub.StubApp;
import io.virtualapp.abs.ui.VActivity;

public class DeviceSettingsActivity extends VActivity {
  private TabLayout mTabLayout;
  
  private Toolbar mToolBar;
  
  private ViewPager mViewPager;
  
  static {
    StubApp.interface11(9743);
  }
  
  private void setupToolBar() {
    setSupportActionBar(this.mToolBar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null)
      actionBar.setDisplayHomeAsUpEnabled(true); 
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
    if (paramMenuItem.getItemId() == 16908332) {
      finish();
      return true;
    } 
    return super.onOptionsItemSelected(paramMenuItem);
  }
  
  public native void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\device\DeviceSettingsActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */