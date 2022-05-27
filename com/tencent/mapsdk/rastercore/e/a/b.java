package com.tencent.mapsdk.rastercore.e.a;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

final class b extends LinearLayout {
  public TextView a;
  
  public TextView b;
  
  public b(Context paramContext, String paramString1, String paramString2) {
    super(paramContext);
    setOrientation(1);
    TextView textView2 = new TextView(paramContext);
    this.a = textView2;
    textView2.setText(paramString1);
    this.a.setTextColor(-16777216);
    addView((View)this.a);
    TextView textView1 = new TextView(paramContext);
    this.b = textView1;
    textView1.setTextColor(-16777216);
    this.b.setText(paramString2);
    addView((View)this.b);
    try {
      setBackgroundDrawable(d.a(paramContext, "infowindow_bg.9.png"));
    } catch (Exception exception) {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\e\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */