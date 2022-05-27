package io.virtualapp.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.stub.StubApp;
import io.virtualapp.abs.ui.VActivity;
import java.util.ArrayList;

public class ListAppActivity extends VActivity {
  private boolean formAppManager;
  
  private TabLayout mTabLayout;
  
  private Toolbar mToolBar;
  
  private ViewPager mViewPager;
  
  static {
    StubApp.interface11(9700);
  }
  
  public static void gotoListApp(Activity paramActivity) {
    paramActivity.startActivityForResult(new Intent((Context)paramActivity, ListAppActivity.class), 5);
  }
  
  public static void gotoListApp(Activity paramActivity, boolean paramBoolean) {
    Intent intent = new Intent((Context)paramActivity, ListAppActivity.class);
    intent.putExtra("formAppManager", paramBoolean);
    paramActivity.startActivity(intent);
  }
  
  private void load() {}
  
  private void setupToolBar() {
    setSupportActionBar(this.mToolBar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null)
      actionBar.setDisplayHomeAsUpEnabled(true); 
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1 && paramIntent != null) {
      ArrayList arrayList1 = paramIntent.getParcelableArrayListExtra("va.extra.APP_INFO_LIST");
      Intent intent = new Intent();
      ArrayList arrayList2 = new ArrayList();
      arrayList2.addAll(arrayList1);
      intent.putParcelableArrayListExtra("va.extra.APP_INFO_LIST", arrayList2);
      getActivity().setResult(-1, intent);
      finish();
    } 
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
    if (paramMenuItem.getItemId() == 16908332) {
      finish();
      return true;
    } 
    return super.onOptionsItemSelected(paramMenuItem);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\ListAppActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */