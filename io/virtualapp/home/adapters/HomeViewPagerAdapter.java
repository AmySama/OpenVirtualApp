package io.virtualapp.home.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
  private FragmentManager fragmetnmanager;
  
  private List<Fragment> listfragment;
  
  public HomeViewPagerAdapter(FragmentManager paramFragmentManager) {
    super(paramFragmentManager);
    this.fragmetnmanager = paramFragmentManager;
    this.listfragment = new ArrayList<Fragment>();
  }
  
  public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject) {
    super.destroyItem(paramViewGroup, paramInt, paramObject);
  }
  
  public int getCount() {
    return this.listfragment.size();
  }
  
  public Fragment getItem(int paramInt) {
    return this.listfragment.get(paramInt);
  }
  
  public long getItemId(int paramInt) {
    return ((Fragment)this.listfragment.get(paramInt)).hashCode();
  }
  
  public boolean isViewFromObject(View paramView, Object paramObject) {
    return super.isViewFromObject(paramView, paramObject);
  }
  
  public void setListfragment(List<Fragment> paramList) {
    this.listfragment = paramList;
    notifyDataSetChanged();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\adapters\HomeViewPagerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */