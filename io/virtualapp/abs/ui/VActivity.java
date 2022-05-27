package io.virtualapp.abs.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import org.jdeferred.android.AndroidDeferredManager;

public abstract class VActivity extends AppCompatActivity {
  protected <T extends android.view.View> T bind(int paramInt) {
    return (T)findViewById(paramInt);
  }
  
  protected AndroidDeferredManager defer() {
    return VUiKit.defer();
  }
  
  public void enableBackHome() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null)
      actionBar.setDisplayHomeAsUpEnabled(true); 
  }
  
  public Fragment findFragmentById(int paramInt) {
    return getSupportFragmentManager().findFragmentById(paramInt);
  }
  
  public Activity getActivity() {
    return (Activity)this;
  }
  
  public Context getContext() {
    return (Context)this;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
    if (paramMenuItem.getItemId() == 16908332)
      finish(); 
    return super.onOptionsItemSelected(paramMenuItem);
  }
  
  protected void onStart() {
    super.onStart();
  }
  
  protected void onStop() {
    super.onStop();
  }
  
  public void replaceFragment(int paramInt, Fragment paramFragment) {
    getSupportFragmentManager().beginTransaction().replace(paramInt, paramFragment).commit();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\ab\\ui\VActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */