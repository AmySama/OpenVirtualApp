package io.virtualapp.home.device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.lody.virtual.os.VUserInfo;
import com.lody.virtual.os.VUserManager;
import io.virtualapp.home.adapters.DeviceAdapter;
import io.virtualapp.home.models.DeviceData;
import java.util.ArrayList;

public class DeviceFragment extends Fragment {
  private DeviceAdapter mAppLocationAdapter;
  
  private ListView mListView;
  
  public static DeviceFragment newInstance() {
    return new DeviceFragment();
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1 && paramIntent != null && paramIntent.getIntExtra("pos", -1) >= 0)
      this.mAppLocationAdapter.notifyDataSetChanged(); 
  }
  
  public void onAttach(Context paramContext) {
    super.onAttach(paramContext);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    return paramLayoutInflater.inflate(2131427443, null);
  }
  
  public void onDetach() {
    super.onDetach();
  }
  
  public void onResume() {
    super.onResume();
    DeviceAdapter deviceAdapter = this.mAppLocationAdapter;
    if (deviceAdapter != null)
      deviceAdapter.notifyDataSetChanged(); 
  }
  
  public void onSaveInstanceState(Bundle paramBundle) {
    super.onSaveInstanceState(paramBundle);
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    this.mListView = (ListView)paramView.findViewById(2131296576);
    this.mAppLocationAdapter = new DeviceAdapter(getContext());
    int i = VUserManager.get().getUserCount();
    ArrayList<DeviceData> arrayList = new ArrayList(i);
    for (byte b = 0; b < i; b++) {
      VUserInfo vUserInfo = VUserManager.get().getUserInfo(b);
      if (vUserInfo != null) {
        DeviceData deviceData = new DeviceData(getContext(), null, vUserInfo.id);
        deviceData.name = vUserInfo.name;
        arrayList.add(deviceData);
      } 
    } 
    this.mAppLocationAdapter.set(arrayList);
    this.mListView.setAdapter((ListAdapter)this.mAppLocationAdapter);
    this.mListView.setOnItemClickListener(new _$$Lambda$DeviceFragment$Ad0c0ogK_fvzL8E61k7OEQAbNek(this));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\device\DeviceFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */