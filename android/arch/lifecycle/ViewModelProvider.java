package android.arch.lifecycle;

import android.app.Application;
import java.lang.reflect.InvocationTargetException;

public class ViewModelProvider {
  private static final String DEFAULT_KEY = "android.arch.lifecycle.ViewModelProvider.DefaultKey";
  
  private final Factory mFactory;
  
  private final ViewModelStore mViewModelStore;
  
  public ViewModelProvider(ViewModelStore paramViewModelStore, Factory paramFactory) {
    this.mFactory = paramFactory;
    this.mViewModelStore = paramViewModelStore;
  }
  
  public ViewModelProvider(ViewModelStoreOwner paramViewModelStoreOwner, Factory paramFactory) {
    this(paramViewModelStoreOwner.getViewModelStore(), paramFactory);
  }
  
  public <T extends ViewModel> T get(Class<T> paramClass) {
    String str = paramClass.getCanonicalName();
    if (str != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("android.arch.lifecycle.ViewModelProvider.DefaultKey:");
      stringBuilder.append(str);
      return get(stringBuilder.toString(), paramClass);
    } 
    throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
  }
  
  public <T extends ViewModel> T get(String paramString, Class<T> paramClass) {
    ViewModel viewModel = this.mViewModelStore.get(paramString);
    if (paramClass.isInstance(viewModel))
      return (T)viewModel; 
    paramClass = this.mFactory.create((Class)paramClass);
    this.mViewModelStore.put(paramString, (ViewModel)paramClass);
    return (T)paramClass;
  }
  
  public static class AndroidViewModelFactory extends NewInstanceFactory {
    private static AndroidViewModelFactory sInstance;
    
    private Application mApplication;
    
    public AndroidViewModelFactory(Application param1Application) {
      this.mApplication = param1Application;
    }
    
    public static AndroidViewModelFactory getInstance(Application param1Application) {
      if (sInstance == null)
        sInstance = new AndroidViewModelFactory(param1Application); 
      return sInstance;
    }
    
    public <T extends ViewModel> T create(Class<T> param1Class) {
      if (AndroidViewModel.class.isAssignableFrom(param1Class))
        try {
          return (T)param1Class.getConstructor(new Class[] { Application.class }).newInstance(new Object[] { this.mApplication });
        } catch (NoSuchMethodException noSuchMethodException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Cannot create an instance of ");
          stringBuilder.append(param1Class);
          throw new RuntimeException(stringBuilder.toString(), noSuchMethodException);
        } catch (IllegalAccessException illegalAccessException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Cannot create an instance of ");
          stringBuilder.append(param1Class);
          throw new RuntimeException(stringBuilder.toString(), illegalAccessException);
        } catch (InstantiationException instantiationException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Cannot create an instance of ");
          stringBuilder.append(param1Class);
          throw new RuntimeException(stringBuilder.toString(), instantiationException);
        } catch (InvocationTargetException invocationTargetException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Cannot create an instance of ");
          stringBuilder.append(param1Class);
          throw new RuntimeException(stringBuilder.toString(), invocationTargetException);
        }  
      return super.create(param1Class);
    }
  }
  
  public static interface Factory {
    <T extends ViewModel> T create(Class<T> param1Class);
  }
  
  public static class NewInstanceFactory implements Factory {
    public <T extends ViewModel> T create(Class<T> param1Class) {
      try {
        return param1Class.newInstance();
      } catch (InstantiationException instantiationException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot create an instance of ");
        stringBuilder.append(param1Class);
        throw new RuntimeException(stringBuilder.toString(), instantiationException);
      } catch (IllegalAccessException illegalAccessException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot create an instance of ");
        stringBuilder.append(param1Class);
        throw new RuntimeException(stringBuilder.toString(), illegalAccessException);
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\ViewModelProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */