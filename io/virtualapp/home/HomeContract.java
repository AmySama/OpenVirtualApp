package io.virtualapp.home;

import io.virtualapp.abs.BasePresenter;
import io.virtualapp.abs.BaseView;
import io.virtualapp.home.models.AppData;
import io.virtualapp.home.models.AppInfoLite;
import java.util.List;

class HomeContract {
  static interface HomePresenter extends BasePresenter {
    void addApp(AppInfoLite param1AppInfoLite);
    
    boolean checkExtPackageBootPermission();
    
    void dataChanged();
    
    void deleteApp(AppData param1AppData);
    
    void enterAppSetting(AppData param1AppData);
    
    int getAppCount();
    
    String getLabel(String param1String);
    
    void launchApp(AppData param1AppData);
  }
  
  static interface HomeView extends BaseView<HomePresenter> {
    void addAppToLauncher(AppData param1AppData);
    
    void askInstallGms();
    
    void hideBottomAction();
    
    void hideLoading();
    
    void hideRank();
    
    void loadError(Throwable param1Throwable);
    
    void loadFinish(List<AppData> param1List);
    
    void refreshLauncherItem(AppData param1AppData);
    
    void removeAppToLauncher(AppData param1AppData);
    
    void showBottomAction();
    
    void showGuide();
    
    void showLoading();
    
    void showOverlayPermissionDialog();
    
    void showPermissionDialog();
    
    void showRank(String param1String);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\HomeContract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */