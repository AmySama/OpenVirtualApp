package io.virtualapp.home.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import io.virtualapp.abs.ui.BaseAdapterPlus;
import io.virtualapp.home.models.DeviceData;

public class DeviceAdapter extends BaseAdapterPlus<DeviceData> {
  public DeviceAdapter(Context paramContext) {
    super(paramContext);
  }
  
  protected void attach(View paramView, DeviceData paramDeviceData, int paramInt) {
    ViewHolder viewHolder = (ViewHolder)paramView.getTag();
    if (paramDeviceData.icon == null) {
      viewHolder.icon.setImageResource(2131230940);
    } else {
      viewHolder.icon.setVisibility(0);
      viewHolder.icon.setImageDrawable(paramDeviceData.icon);
    } 
    viewHolder.label.setText(paramDeviceData.name);
    if (paramDeviceData.isMocking()) {
      viewHolder.location.setText(2131624030);
    } else {
      viewHolder.location.setText(2131624032);
    } 
  }
  
  protected View createView(int paramInt, ViewGroup paramViewGroup) {
    View view = inflate(2131427449, paramViewGroup, false);
    view.setTag(new ViewHolder(view));
    return view;
  }
  
  static class ViewHolder extends BaseAdapterPlus.BaseViewHolder {
    final ImageView icon = (ImageView)$(2131296536);
    
    final TextView label = (TextView)$(2131296537);
    
    final TextView location = (TextView)$(2131296545);
    
    public ViewHolder(View param1View) {
      super(param1View);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\adapters\DeviceAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */