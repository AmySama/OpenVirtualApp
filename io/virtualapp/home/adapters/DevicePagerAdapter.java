package io.virtualapp.home.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import io.virtualapp.App;
import io.virtualapp.home.device.DeviceFragment;
import java.util.ArrayList;
import java.util.List;

public class DevicePagerAdapter extends FragmentPagerAdapter {
  private List<String> titles;
  
  public DevicePagerAdapter(FragmentManager paramFragmentManager) {
    super(paramFragmentManager);
    ArrayList<String> arrayList = new ArrayList();
    this.titles = arrayList;
    arrayList.add(App.getApp().getResources().getString(2131624079));
  }
  
  public int getCount() {
    return this.titles.size();
  }
  
  public Fragment getItem(int paramInt) {
    return (Fragment)DeviceFragment.newInstance();
  }
  
  public CharSequence getPageTitle(int paramInt) {
    return this.titles.get(paramInt);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\adapters\DevicePagerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */