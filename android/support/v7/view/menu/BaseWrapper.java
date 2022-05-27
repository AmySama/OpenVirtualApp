package android.support.v7.view.menu;

class BaseWrapper<T> {
  final T mWrappedObject;
  
  BaseWrapper(T paramT) {
    if (paramT != null) {
      this.mWrappedObject = paramT;
      return;
    } 
    throw new IllegalArgumentException("Wrapped Object can not be null.");
  }
  
  public T getWrappedObject() {
    return this.mWrappedObject;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\menu\BaseWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */