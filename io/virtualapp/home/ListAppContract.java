package io.virtualapp.home;

import io.virtualapp.abs.BasePresenter;
import io.virtualapp.abs.BaseView;
import io.virtualapp.home.models.AppInfo;
import java.util.List;

class ListAppContract {
  static interface ListAppPresenter extends BasePresenter {}
  
  static interface ListAppView extends BaseView<ListAppPresenter> {
    void loadFinish(List<AppInfo> param1List);
    
    void startLoading();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\ListAppContract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */