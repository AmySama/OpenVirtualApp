package android.support.design.internal;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.view.ViewGroup;

public class BottomNavigationPresenter implements MenuPresenter {
  private int mId;
  
  private MenuBuilder mMenu;
  
  private BottomNavigationMenuView mMenuView;
  
  private boolean mUpdateSuspended = false;
  
  public boolean collapseItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl) {
    return false;
  }
  
  public boolean expandItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl) {
    return false;
  }
  
  public boolean flagActionItems() {
    return false;
  }
  
  public int getId() {
    return this.mId;
  }
  
  public MenuView getMenuView(ViewGroup paramViewGroup) {
    return this.mMenuView;
  }
  
  public void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder) {
    this.mMenuView.initialize(this.mMenu);
    this.mMenu = paramMenuBuilder;
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {}
  
  public void onRestoreInstanceState(Parcelable paramParcelable) {
    if (paramParcelable instanceof SavedState)
      this.mMenuView.tryRestoreSelectedItemId(((SavedState)paramParcelable).selectedItemId); 
  }
  
  public Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState();
    savedState.selectedItemId = this.mMenuView.getSelectedItemId();
    return savedState;
  }
  
  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder) {
    return false;
  }
  
  public void setBottomNavigationMenuView(BottomNavigationMenuView paramBottomNavigationMenuView) {
    this.mMenuView = paramBottomNavigationMenuView;
  }
  
  public void setCallback(MenuPresenter.Callback paramCallback) {}
  
  public void setId(int paramInt) {
    this.mId = paramInt;
  }
  
  public void setUpdateSuspended(boolean paramBoolean) {
    this.mUpdateSuspended = paramBoolean;
  }
  
  public void updateMenuView(boolean paramBoolean) {
    if (this.mUpdateSuspended)
      return; 
    if (paramBoolean) {
      this.mMenuView.buildMenuView();
    } else {
      this.mMenuView.updateMenuView();
    } 
  }
  
  static class SavedState implements Parcelable {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
        public BottomNavigationPresenter.SavedState createFromParcel(Parcel param2Parcel) {
          return new BottomNavigationPresenter.SavedState(param2Parcel);
        }
        
        public BottomNavigationPresenter.SavedState[] newArray(int param2Int) {
          return new BottomNavigationPresenter.SavedState[param2Int];
        }
      };
    
    int selectedItemId;
    
    SavedState() {}
    
    SavedState(Parcel param1Parcel) {
      this.selectedItemId = param1Parcel.readInt();
    }
    
    public int describeContents() {
      return 0;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeInt(this.selectedItemId);
    }
  }
  
  static final class null implements Parcelable.Creator<SavedState> {
    public BottomNavigationPresenter.SavedState createFromParcel(Parcel param1Parcel) {
      return new BottomNavigationPresenter.SavedState(param1Parcel);
    }
    
    public BottomNavigationPresenter.SavedState[] newArray(int param1Int) {
      return new BottomNavigationPresenter.SavedState[param1Int];
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\internal\BottomNavigationPresenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */