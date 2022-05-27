package android.arch.lifecycle;

import android.app.Application;

public class AndroidViewModel extends ViewModel {
  private Application mApplication;
  
  public AndroidViewModel(Application paramApplication) {
    this.mApplication = paramApplication;
  }
  
  public <T extends Application> T getApplication() {
    return (T)this.mApplication;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\AndroidViewModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */