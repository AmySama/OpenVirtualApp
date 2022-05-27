package io.virtualapp.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.virtualapp.App;
import io.virtualapp.abs.nestedadapter.SmartRecyclerAdapter;
import io.virtualapp.home.adapters.LaunchpadAdapter;
import io.virtualapp.home.models.AppData;
import java.util.ArrayList;
import java.util.List;

public class HomeAppListFragment extends Fragment {
  private static Activity mActivity;
  
  private List<AppData> datas;
  
  private RecyclerView mLauncherView;
  
  private LaunchpadAdapter mLaunchpadAdapter;
  
  private View mMainView;
  
  private void initView() {
    RecyclerView recyclerView = (RecyclerView)this.mMainView.findViewById(2131296500);
    this.mLauncherView = recyclerView;
    recyclerView.setHasFixedSize(true);
    GridLayoutManager gridLayoutManager = new GridLayoutManager((Context)getActivity(), 4);
    this.mLauncherView.setLayoutManager((RecyclerView.LayoutManager)gridLayoutManager);
    LaunchpadAdapter launchpadAdapter = new LaunchpadAdapter((Context)getActivity());
    this.mLaunchpadAdapter = launchpadAdapter;
    SmartRecyclerAdapter smartRecyclerAdapter = new SmartRecyclerAdapter((RecyclerView.Adapter)launchpadAdapter);
    this.mLauncherView.setAdapter((RecyclerView.Adapter)smartRecyclerAdapter);
    (new ItemTouchHelper((ItemTouchHelper.Callback)new LauncherTouchCallback())).attachToRecyclerView(this.mLauncherView);
    this.mLaunchpadAdapter.setAppClickListener(new LaunchpadAdapter.OnAppClickListener() {
          public void onAppClick(int param1Int, AppData param1AppData) {
            if (!param1AppData.isLoading())
              if (param1AppData.isSys()) {
                PackageManager packageManager = App.getApp().getPackageManager();
                param1AppData = HomeAppListFragment.this.mLaunchpadAdapter.getList().get(param1Int);
                try {
                  Intent intent = packageManager.getLaunchIntentForPackage(param1AppData.getPackageName());
                  if (intent != null)
                    App.getApp().startActivity(intent); 
                } catch (Exception exception) {
                  exception.printStackTrace();
                } 
              } else {
                HomeAppListFragment.this.mLaunchpadAdapter.notifyItemChanged(param1Int);
                HomeAppListFragment.this.launchApp((AppData)exception);
              }  
          }
        });
    this.mLaunchpadAdapter.setList(this.datas);
  }
  
  public static HomeAppListFragment newInstance(Activity paramActivity) {
    HomeAppListFragment homeAppListFragment = new HomeAppListFragment();
    mActivity = paramActivity;
    return homeAppListFragment;
  }
  
  public void launchApp(AppData paramAppData) {
    try {
    
    } finally {
      paramAppData = null;
    } 
  }
  
  public void onActivityCreated(Bundle paramBundle) {
    super.onActivityCreated(paramBundle);
    initView();
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    if (this.datas == null)
      this.datas = new ArrayList<AppData>(); 
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    View view = paramLayoutInflater.inflate(2131427441, paramViewGroup, false);
    this.mMainView = view;
    return view;
  }
  
  public void refresh(AppData paramAppData) {
    LaunchpadAdapter launchpadAdapter = this.mLaunchpadAdapter;
    if (launchpadAdapter != null)
      launchpadAdapter.refresh(paramAppData); 
  }
  
  public void remove(AppData paramAppData) {
    LaunchpadAdapter launchpadAdapter = this.mLaunchpadAdapter;
    if (launchpadAdapter != null)
      launchpadAdapter.remove(paramAppData); 
  }
  
  public void setData(List<AppData> paramList) {
    this.datas = new ArrayList<AppData>();
    this.datas = paramList;
    RecyclerView recyclerView = this.mLauncherView;
    if (recyclerView != null)
      recyclerView.removeAllViews(); 
    LaunchpadAdapter launchpadAdapter = this.mLaunchpadAdapter;
    if (launchpadAdapter != null)
      launchpadAdapter.setList(paramList); 
  }
  
  private class LauncherTouchCallback extends ItemTouchHelper.SimpleCallback {
    RecyclerView.ViewHolder dragHolder;
    
    int[] location = new int[2];
    
    boolean upAtCreateShortcutArea;
    
    boolean upAtDeleteAppArea;
    
    LauncherTouchCallback() {
      super(63, 0);
    }
    
    public boolean canDropOver(RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder1, RecyclerView.ViewHolder param1ViewHolder2) {
      if (!this.upAtCreateShortcutArea && !this.upAtDeleteAppArea)
        try {
          return ((AppData)HomeAppListFragment.this.mLaunchpadAdapter.getList().get(param1ViewHolder2.getAdapterPosition())).canReorder();
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
          indexOutOfBoundsException.printStackTrace();
        }  
      return false;
    }
    
    public void clearView(RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder) {
      if (param1ViewHolder instanceof LaunchpadAdapter.ViewHolder) {
        LaunchpadAdapter.ViewHolder viewHolder = (LaunchpadAdapter.ViewHolder)param1ViewHolder;
        param1ViewHolder.itemView.setScaleX(1.0F);
        param1ViewHolder.itemView.setScaleY(1.0F);
        param1ViewHolder.itemView.setBackgroundColor(viewHolder.color);
      } 
      super.clearView(param1RecyclerView, param1ViewHolder);
      if (this.dragHolder == param1ViewHolder)
        this.dragHolder = null; 
    }
    
    public int getMovementFlags(RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder) {
      try {
        if (!((AppData)HomeAppListFragment.this.mLaunchpadAdapter.getList().get(param1ViewHolder.getAdapterPosition())).canReorder())
          return makeMovementFlags(0, 0); 
      } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
        indexOutOfBoundsException.printStackTrace();
      } 
      return super.getMovementFlags(param1RecyclerView, param1ViewHolder);
    }
    
    public int interpolateOutOfBoundsScroll(RecyclerView param1RecyclerView, int param1Int1, int param1Int2, int param1Int3, long param1Long) {
      return 0;
    }
    
    public boolean isItemViewSwipeEnabled() {
      return false;
    }
    
    public boolean isLongPressDragEnabled() {
      return true;
    }
    
    public void onChildDraw(Canvas param1Canvas, RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder, float param1Float1, float param1Float2, int param1Int, boolean param1Boolean) {
      super.onChildDraw(param1Canvas, param1RecyclerView, param1ViewHolder, param1Float1, param1Float2, param1Int, param1Boolean);
      if (param1Int == 2 && param1Boolean)
        param1ViewHolder.itemView.getLocationInWindow(this.location); 
    }
    
    public boolean onMove(RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder1, RecyclerView.ViewHolder param1ViewHolder2) {
      int i = param1ViewHolder1.getAdapterPosition();
      int j = param1ViewHolder2.getAdapterPosition();
      HomeAppListFragment.this.mLaunchpadAdapter.moveItem(i, j);
      return true;
    }
    
    public void onSelectedChanged(RecyclerView.ViewHolder param1ViewHolder, int param1Int) {
      if (param1ViewHolder instanceof LaunchpadAdapter.ViewHolder && param1Int == 2 && this.dragHolder != param1ViewHolder) {
        this.dragHolder = param1ViewHolder;
        param1ViewHolder.itemView.setScaleX(1.2F);
        param1ViewHolder.itemView.setScaleY(1.2F);
      } 
      super.onSelectedChanged(param1ViewHolder, param1Int);
    }
    
    public void onSwiped(RecyclerView.ViewHolder param1ViewHolder, int param1Int) {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\HomeAppListFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */