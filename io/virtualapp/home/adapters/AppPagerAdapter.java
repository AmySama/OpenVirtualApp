package io.virtualapp.home.adapters;

import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.lody.virtual.helper.utils.Reflect;
import io.virtualapp.App;
import io.virtualapp.home.ListAppFragment;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppPagerAdapter extends FragmentPagerAdapter {
  private List<File> dirs = new ArrayList<File>();
  
  private boolean formAM;
  
  private File mDir = null;
  
  private String mTitle = "制作分身";
  
  private List<String> titles = new ArrayList<String>();
  
  public AppPagerAdapter(FragmentManager paramFragmentManager, boolean paramBoolean) {
    super(paramFragmentManager);
    this.formAM = paramBoolean;
    if (Build.VERSION.SDK_INT >= 24) {
      for (StorageVolume storageVolume : ((StorageManager)App.getApp().getSystemService("storage")).getStorageVolumes()) {
        File file = (File)Reflect.on(storageVolume).call("getPathFile").get();
        String str = (String)Reflect.on(storageVolume).call("getUserLabel").get();
        if (file.listFiles() != null) {
          this.mTitle = "制作分身";
          this.mDir = file;
        } 
      } 
    } else {
      File file = Environment.getExternalStorageDirectory();
      if (file != null && file.isDirectory()) {
        this.mTitle = "制作分身";
        this.mDir = file;
      } 
    } 
    this.titles.add(this.mTitle);
    this.dirs.add(this.mDir);
  }
  
  public int getCount() {
    return this.titles.size();
  }
  
  public Fragment getItem(int paramInt) {
    return (Fragment)ListAppFragment.newInstance(this.dirs.get(paramInt), this.formAM);
  }
  
  public CharSequence getPageTitle(int paramInt) {
    return this.titles.get(paramInt);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\adapters\AppPagerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */