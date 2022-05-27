package android.support.v7.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.v4.view.ActionProvider;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public class ShareActionProvider extends ActionProvider {
  private static final int DEFAULT_INITIAL_ACTIVITY_COUNT = 4;
  
  public static final String DEFAULT_SHARE_HISTORY_FILE_NAME = "share_history.xml";
  
  final Context mContext;
  
  private int mMaxShownActivityCount = 4;
  
  private ActivityChooserModel.OnChooseActivityListener mOnChooseActivityListener;
  
  private final ShareMenuItemOnMenuItemClickListener mOnMenuItemClickListener = new ShareMenuItemOnMenuItemClickListener();
  
  OnShareTargetSelectedListener mOnShareTargetSelectedListener;
  
  String mShareHistoryFileName = "share_history.xml";
  
  public ShareActionProvider(Context paramContext) {
    super(paramContext);
    this.mContext = paramContext;
  }
  
  private void setActivityChooserPolicyIfNeeded() {
    if (this.mOnShareTargetSelectedListener == null)
      return; 
    if (this.mOnChooseActivityListener == null)
      this.mOnChooseActivityListener = new ShareActivityChooserModelPolicy(); 
    ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName).setOnChooseActivityListener(this.mOnChooseActivityListener);
  }
  
  public boolean hasSubMenu() {
    return true;
  }
  
  public View onCreateActionView() {
    ActivityChooserView activityChooserView = new ActivityChooserView(this.mContext);
    if (!activityChooserView.isInEditMode())
      activityChooserView.setActivityChooserModel(ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName)); 
    TypedValue typedValue = new TypedValue();
    this.mContext.getTheme().resolveAttribute(R.attr.actionModeShareDrawable, typedValue, true);
    activityChooserView.setExpandActivityOverflowButtonDrawable(AppCompatResources.getDrawable(this.mContext, typedValue.resourceId));
    activityChooserView.setProvider(this);
    activityChooserView.setDefaultActionButtonContentDescription(R.string.abc_shareactionprovider_share_with_application);
    activityChooserView.setExpandActivityOverflowButtonContentDescription(R.string.abc_shareactionprovider_share_with);
    return (View)activityChooserView;
  }
  
  public void onPrepareSubMenu(SubMenu paramSubMenu) {
    paramSubMenu.clear();
    ActivityChooserModel activityChooserModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
    PackageManager packageManager = this.mContext.getPackageManager();
    int i = activityChooserModel.getActivityCount();
    int j = Math.min(i, this.mMaxShownActivityCount);
    byte b;
    for (b = 0; b < j; b++) {
      ResolveInfo resolveInfo = activityChooserModel.getActivity(b);
      paramSubMenu.add(0, b, b, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
    } 
    if (j < i) {
      paramSubMenu = paramSubMenu.addSubMenu(0, j, j, this.mContext.getString(R.string.abc_activity_chooser_view_see_all));
      for (b = 0; b < i; b++) {
        ResolveInfo resolveInfo = activityChooserModel.getActivity(b);
        paramSubMenu.add(0, b, b, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
      } 
    } 
  }
  
  public void setOnShareTargetSelectedListener(OnShareTargetSelectedListener paramOnShareTargetSelectedListener) {
    this.mOnShareTargetSelectedListener = paramOnShareTargetSelectedListener;
    setActivityChooserPolicyIfNeeded();
  }
  
  public void setShareHistoryFileName(String paramString) {
    this.mShareHistoryFileName = paramString;
    setActivityChooserPolicyIfNeeded();
  }
  
  public void setShareIntent(Intent paramIntent) {
    if (paramIntent != null) {
      String str = paramIntent.getAction();
      if ("android.intent.action.SEND".equals(str) || "android.intent.action.SEND_MULTIPLE".equals(str))
        updateIntent(paramIntent); 
    } 
    ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName).setIntent(paramIntent);
  }
  
  void updateIntent(Intent paramIntent) {
    if (Build.VERSION.SDK_INT >= 21) {
      paramIntent.addFlags(134742016);
    } else {
      paramIntent.addFlags(524288);
    } 
  }
  
  public static interface OnShareTargetSelectedListener {
    boolean onShareTargetSelected(ShareActionProvider param1ShareActionProvider, Intent param1Intent);
  }
  
  private class ShareActivityChooserModelPolicy implements ActivityChooserModel.OnChooseActivityListener {
    public boolean onChooseActivity(ActivityChooserModel param1ActivityChooserModel, Intent param1Intent) {
      if (ShareActionProvider.this.mOnShareTargetSelectedListener != null)
        ShareActionProvider.this.mOnShareTargetSelectedListener.onShareTargetSelected(ShareActionProvider.this, param1Intent); 
      return false;
    }
  }
  
  private class ShareMenuItemOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
    public boolean onMenuItemClick(MenuItem param1MenuItem) {
      Intent intent = ActivityChooserModel.get(ShareActionProvider.this.mContext, ShareActionProvider.this.mShareHistoryFileName).chooseActivity(param1MenuItem.getItemId());
      if (intent != null) {
        String str = intent.getAction();
        if ("android.intent.action.SEND".equals(str) || "android.intent.action.SEND_MULTIPLE".equals(str))
          ShareActionProvider.this.updateIntent(intent); 
        ShareActionProvider.this.mContext.startActivity(intent);
      } 
      return true;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\ShareActionProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */