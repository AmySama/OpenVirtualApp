package io.virtualapp.home;

import android.app.Activity;
import android.content.Context;
import io.virtualapp.home.repo.AppDataSource;
import io.virtualapp.home.repo.AppRepository;
import java.io.File;
import java.util.Objects;
import org.jdeferred.Promise;

class ListAppPresenterImpl implements ListAppContract.ListAppPresenter {
  private File from;
  
  private Activity mActivity;
  
  private AppDataSource mRepository;
  
  private ListAppContract.ListAppView mView;
  
  ListAppPresenterImpl(Activity paramActivity, ListAppContract.ListAppView paramListAppView, File paramFile) {
    this.mActivity = paramActivity;
    this.mView = paramListAppView;
    this.mRepository = (AppDataSource)new AppRepository((Context)paramActivity);
    this.mView.setPresenter(this);
    this.from = paramFile;
  }
  
  public void start() {
    this.mView.setPresenter(this);
    this.mView.startLoading();
    Promise promise = this.mRepository.getInstalledApps((Context)this.mActivity);
    ListAppContract.ListAppView listAppView = this.mView;
    Objects.requireNonNull(listAppView);
    promise.done(new _$$Lambda$NDo_FmuiGvFzj8jtKnikVXOa74A(listAppView));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\ListAppPresenterImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */