package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;

final class FragmentManagerState implements Parcelable {
  public static final Parcelable.Creator<FragmentManagerState> CREATOR = new Parcelable.Creator<FragmentManagerState>() {
      public FragmentManagerState createFromParcel(Parcel param1Parcel) {
        return new FragmentManagerState(param1Parcel);
      }
      
      public FragmentManagerState[] newArray(int param1Int) {
        return new FragmentManagerState[param1Int];
      }
    };
  
  FragmentState[] mActive;
  
  int[] mAdded;
  
  BackStackState[] mBackStack;
  
  int mNextFragmentIndex;
  
  int mPrimaryNavActiveIndex = -1;
  
  public FragmentManagerState() {}
  
  public FragmentManagerState(Parcel paramParcel) {
    this.mActive = (FragmentState[])paramParcel.createTypedArray(FragmentState.CREATOR);
    this.mAdded = paramParcel.createIntArray();
    this.mBackStack = (BackStackState[])paramParcel.createTypedArray(BackStackState.CREATOR);
    this.mPrimaryNavActiveIndex = paramParcel.readInt();
    this.mNextFragmentIndex = paramParcel.readInt();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeTypedArray((Parcelable[])this.mActive, paramInt);
    paramParcel.writeIntArray(this.mAdded);
    paramParcel.writeTypedArray((Parcelable[])this.mBackStack, paramInt);
    paramParcel.writeInt(this.mPrimaryNavActiveIndex);
    paramParcel.writeInt(this.mNextFragmentIndex);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\FragmentManagerState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */