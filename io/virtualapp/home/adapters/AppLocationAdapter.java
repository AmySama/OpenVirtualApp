package io.virtualapp.home.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import io.virtualapp.abs.ui.BaseAdapterPlus;
import io.virtualapp.home.models.LocationData;

public class AppLocationAdapter extends BaseAdapterPlus<LocationData> {
  public AppLocationAdapter(Context paramContext) {
    super(paramContext);
  }
  
  protected void attach(View paramView, LocationData paramLocationData, int paramInt) {
    TextView textView;
    ViewHolder viewHolder = (ViewHolder)paramView.getTag();
    viewHolder.icon.setImageDrawable(paramLocationData.icon);
    if (paramLocationData.userId > 0) {
      TextView textView1 = viewHolder.label;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramLocationData.name);
      stringBuilder.append(" (");
      stringBuilder.append(paramLocationData.userId + 1);
      stringBuilder.append(")");
      textView1.setText(stringBuilder.toString());
    } else {
      viewHolder.label.setText(paramLocationData.name);
    } 
    if (paramLocationData.location != null && paramLocationData.mode != 0) {
      textView = viewHolder.location;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramLocationData.location.latitude);
      stringBuilder.append(",");
      stringBuilder.append(paramLocationData.location.longitude);
      textView.setText(stringBuilder.toString());
    } else {
      ((ViewHolder)textView).location.setText(2131624055);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\adapters\AppLocationAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */