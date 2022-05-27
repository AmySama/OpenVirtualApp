package android.support.v4.app;

import android.arch.lifecycle.ViewModelStore;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

final class FragmentState implements Parcelable {
  public static final Parcelable.Creator<FragmentState> CREATOR = new Parcelable.Creator<FragmentState>() {
      public FragmentState createFromParcel(Parcel param1Parcel) {
        return new FragmentState(param1Parcel);
      }
      
      public FragmentState[] newArray(int param1Int) {
        return new FragmentState[param1Int];
      }
    };
  
  final Bundle mArguments;
  
  final String mClassName;
  
  final int mContainerId;
  
  final boolean mDetached;
  
  final int mFragmentId;
  
  final boolean mFromLayout;
  
  final boolean mHidden;
  
  final int mIndex;
  
  Fragment mInstance;
  
  final boolean mRetainInstance;
  
  Bundle mSavedFragmentState;
  
  final String mTag;
  
  FragmentState(Parcel paramParcel) {
    boolean bool2;
    this.mClassName = paramParcel.readString();
    this.mIndex = paramParcel.readInt();
    int i = paramParcel.readInt();
    boolean bool1 = true;
    if (i != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    this.mFromLayout = bool2;
    this.mFragmentId = paramParcel.readInt();
    this.mContainerId = paramParcel.readInt();
    this.mTag = paramParcel.readString();
    if (paramParcel.readInt() != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    this.mRetainInstance = bool2;
    if (paramParcel.readInt() != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    this.mDetached = bool2;
    this.mArguments = paramParcel.readBundle();
    if (paramParcel.readInt() != 0) {
      bool2 = bool1;
    } else {
      bool2 = false;
    } 
    this.mHidden = bool2;
    this.mSavedFragmentState = paramParcel.readBundle();
  }
  
  FragmentState(Fragment paramFragment) {
    this.mClassName = paramFragment.getClass().getName();
    this.mIndex = paramFragment.mIndex;
    this.mFromLayout = paramFragment.mFromLayout;
    this.mFragmentId = paramFragment.mFragmentId;
    this.mContainerId = paramFragment.mContainerId;
    this.mTag = paramFragment.mTag;
    this.mRetainInstance = paramFragment.mRetainInstance;
    this.mDetached = paramFragment.mDetached;
    this.mArguments = paramFragment.mArguments;
    this.mHidden = paramFragment.mHidden;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public Fragment instantiate(FragmentHostCallback paramFragmentHostCallback, FragmentContainer paramFragmentContainer, Fragment paramFragment, FragmentManagerNonConfig paramFragmentManagerNonConfig, ViewModelStore paramViewModelStore) {
    if (this.mInstance == null) {
      Context context = paramFragmentHostCallback.getContext();
      Bundle bundle2 = this.mArguments;
      if (bundle2 != null)
        bundle2.setClassLoader(context.getClassLoader()); 
      if (paramFragmentContainer != null) {
        this.mInstance = paramFragmentContainer.instantiate(context, this.mClassName, this.mArguments);
      } else {
        this.mInstance = Fragment.instantiate(context, this.mClassName, this.mArguments);
      } 
      Bundle bundle1 = this.mSavedFragmentState;
      if (bundle1 != null) {
        bundle1.setClassLoader(context.getClassLoader());
        this.mInstance.mSavedFragmentState = this.mSavedFragmentState;
      } 
      this.mInstance.setIndex(this.mIndex, paramFragment);
      this.mInstance.mFromLayout = this.mFromLayout;
      this.mInstance.mRestored = true;
      this.mInstance.mFragmentId = this.mFragmentId;
      this.mInstance.mContainerId = this.mContainerId;
      this.mInstance.mTag = this.mTag;
      this.mInstance.mRetainInstance = this.mRetainInstance;
      this.mInstance.mDetached = this.mDetached;
      this.mInstance.mHidden = this.mHidden;
      this.mInstance.mFragmentManager = paramFragmentHostCallback.mFragmentManager;
      if (FragmentManagerImpl.DEBUG) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Instantiated fragment ");
        stringBuilder.append(this.mInstance);
        Log.v("FragmentManager", stringBuilder.toString());
      } 
    } 
    this.mInstance.mChildNonConfig = paramFragmentManagerNonConfig;
    this.mInstance.mViewModelStore = paramViewModelStore;
    return this.mInstance;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.mClassName);
    paramParcel.writeInt(this.mIndex);
    paramParcel.writeInt(this.mFromLayout);
    paramParcel.writeInt(this.mFragmentId);
    paramParcel.writeInt(this.mContainerId);
    paramParcel.writeString(this.mTag);
    paramParcel.writeInt(this.mRetainInstance);
    paramParcel.writeInt(this.mDetached);
    paramParcel.writeBundle(this.mArguments);
    paramParcel.writeInt(this.mHidden);
    paramParcel.writeBundle(this.mSavedFragmentState);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\FragmentState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */