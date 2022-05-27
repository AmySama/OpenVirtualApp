package io.virtualapp.home.location;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VirtualLocationManager;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.remote.vloc.VLocation;
import com.stub.StubApp;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.Location;
import com.tencent.lbssearch.object.param.Geo2AddressParam;
import com.tencent.lbssearch.object.param.SearchParam;
import com.tencent.lbssearch.object.result.Geo2AddressResultObject;
import com.tencent.lbssearch.object.result.SearchResultObject;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.CameraPosition;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.tencentmap.mapsdk.map.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import io.virtualapp.Utils.StringUtils;
import io.virtualapp.abs.ui.VActivity;

public class ChooseLocationActivity extends VActivity implements TencentLocationListener {
  private boolean isFindLocation;
  
  private String mAddress;
  
  private TextView mAddressText;
  
  private String mCity;
  
  private String mCurPkg;
  
  private int mCurUserId;
  
  private TextView mLatText;
  
  private TextView mLngText;
  
  private VLocation mLocation;
  
  private TencentMap mMap;
  
  private View mMockBtn;
  
  private View mMockImg;
  
  private TextView mMockText;
  
  private boolean mMocking;
  
  private View mMockingView;
  
  private ArrayAdapter<MapSearchResult> mSearchAdapter;
  
  private View mSearchLayout;
  
  private MenuItem mSearchMenuItem;
  
  private View mSearchTip;
  
  private TencentSearch mSearcher;
  
  private MapView mapView;
  
  static {
    StubApp.interface11(9763);
  }
  
  private void gotoLocation(String paramString, double paramDouble1, double paramDouble2, boolean paramBoolean) {
    Geo2AddressParam geo2AddressParam;
    if (paramBoolean) {
      int i = Math.max(this.mMap.getZoomLevel(), this.mMap.getMaxZoomLevel() / 3 * 2);
      this.mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(paramDouble1, paramDouble2), i)));
    } else {
      this.mLocation.latitude = StringUtils.doubleFor8(paramDouble1);
      this.mLocation.longitude = StringUtils.doubleFor8(paramDouble2);
      this.mLatText.setText(String.valueOf(this.mLocation.latitude));
      this.mLngText.setText(String.valueOf(this.mLocation.longitude));
    } 
    if (TextUtils.isEmpty(paramString)) {
      geo2AddressParam = (new Geo2AddressParam()).location((new Location()).lat((float)paramDouble1).lng((float)paramDouble2));
      this.mSearcher.geo2address(geo2AddressParam, new HttpResponseListener() {
            public void onFailure(int param1Int, String param1String, Throwable param1Throwable) {
              ChooseLocationActivity chooseLocationActivity = ChooseLocationActivity.this;
              chooseLocationActivity.setAddress(chooseLocationActivity.getString(2131624083));
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("no find address:");
              stringBuilder.append(param1String);
              Log.e("kk", stringBuilder.toString(), param1Throwable);
            }
            
            public void onSuccess(int param1Int, BaseObject param1BaseObject) {
              Geo2AddressResultObject geo2AddressResultObject = (Geo2AddressResultObject)param1BaseObject;
              if (geo2AddressResultObject.result != null) {
                ChooseLocationActivity.access$602(ChooseLocationActivity.this, geo2AddressResultObject.result.address_component.city);
                ChooseLocationActivity.this.setAddress(geo2AddressResultObject.result.address);
              } 
            }
          });
    } else {
      setAddress((String)geo2AddressParam);
    } 
  }
  
  private void searchLocation(final String city) {
    String str;
    int i = city.indexOf("@");
    if (i > 0) {
      String str1 = city.substring(0, i);
      str = city.substring(i + 1);
      city = str1;
    } else {
      String str1 = null;
      str = city;
      city = str1;
    } 
    if (TextUtils.isEmpty(city))
      if (TextUtils.isEmpty(this.mCity)) {
        city = "中国";
      } else {
        city = this.mCity;
      }  
    SearchParam.Region region = (new SearchParam.Region()).poi(city);
    SearchParam searchParam = (new SearchParam()).keyword(str).boundary((SearchParam.Boundary)region).page_size(50);
    this.mSearcher.search(searchParam, new HttpResponseListener() {
          public void onFailure(int param1Int, String param1String, Throwable param1Throwable) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("error:");
            stringBuilder.append(param1String);
            Log.e("kk", stringBuilder.toString(), param1Throwable);
            ChooseLocationActivity.this.mSearchAdapter.clear();
            ChooseLocationActivity.this.mSearchAdapter.add(ChooseLocationActivity.MapSearchResult.NULL);
            ChooseLocationActivity.this.mSearchAdapter.notifyDataSetChanged();
          }
          
          public void onSuccess(int param1Int, BaseObject param1BaseObject) {
            if (ChooseLocationActivity.this.mSearchTip.getVisibility() != 8)
              ChooseLocationActivity.this.runOnUiThread(new _$$Lambda$ChooseLocationActivity$5$fOAF_xI7oJTClbLQkt07yS3ZfOo(this)); 
            SearchResultObject searchResultObject = (SearchResultObject)param1BaseObject;
            ChooseLocationActivity.this.mSearchAdapter.clear();
            if (searchResultObject.count == 0) {
              ChooseLocationActivity.this.mSearchAdapter.add(ChooseLocationActivity.MapSearchResult.NULL);
            } else {
              for (SearchResultObject.SearchResultData searchResultData : searchResultObject.data) {
                ChooseLocationActivity.MapSearchResult mapSearchResult = new ChooseLocationActivity.MapSearchResult(searchResultData.address, searchResultData.location.lat, searchResultData.location.lng);
                mapSearchResult.setCity(city);
                ChooseLocationActivity.this.mSearchAdapter.add(mapSearchResult);
              } 
            } 
            ChooseLocationActivity.this.mSearchAdapter.notifyDataSetChanged();
          }
        });
  }
  
  private void setAddress(String paramString) {
    runOnUiThread(new _$$Lambda$ChooseLocationActivity$SWoBRTETnY1Ir9MzixY6LDAW2fY(this, paramString));
  }
  
  private void showInputWindow() {
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this, 2131689777);
    View view = getLayoutInflater().inflate(2131427422, null);
    builder.setView(view);
    AlertDialog alertDialog = builder.show();
    alertDialog.setCanceledOnTouchOutside(false);
    EditText editText1 = (EditText)view.findViewById(2131296460);
    editText1.setText(StringUtils.doubleFor8String(this.mLocation.getLatitude()));
    EditText editText2 = (EditText)view.findViewById(2131296461);
    editText2.setText(StringUtils.doubleFor8String(this.mLocation.getLongitude()));
    alertDialog.setCancelable(false);
    view.findViewById(2131296381).setOnClickListener(new _$$Lambda$ChooseLocationActivity$Hflcam00Vpa6FYuLBv7HflmRylY((Dialog)alertDialog));
    view.findViewById(2131296386).setOnClickListener(new _$$Lambda$ChooseLocationActivity$HdINuz0vzRwBe5DNNQPPjnBoGFw(this, (Dialog)alertDialog, editText1, editText2));
  }
  
  private void startLocation() {
    if (this.isFindLocation) {
      Toast.makeText((Context)this, 2131624071, 0).show();
      return;
    } 
    this.isFindLocation = true;
    Toast.makeText((Context)this, 2131624076, 0).show();
    TencentLocationRequest tencentLocationRequest = TencentLocationRequest.create().setRequestLevel(0).setAllowGPS(true);
    int i = TencentLocationManager.getInstance((Context)this).requestLocationUpdates(tencentLocationRequest, this);
    if (i != 0) {
      this.isFindLocation = false;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("startLocation:error=");
      stringBuilder.append(i);
      VLog.w("TMap", stringBuilder.toString(), new Object[0]);
    } 
  }
  
  private void updateMock(boolean paramBoolean) {
    this.mMocking = paramBoolean;
    this.mMockImg.setSelected(paramBoolean);
    if (paramBoolean) {
      this.mMockText.setText(2131624033);
      this.mMockingView.setVisibility(0);
      this.mMockBtn.setVisibility(8);
      MenuItem menuItem = this.mSearchMenuItem;
      if (menuItem != null)
        menuItem.setEnabled(false); 
    } else {
      this.mMockText.setText(2131624037);
      this.mMockingView.setVisibility(8);
      this.mMockBtn.setVisibility(0);
      MenuItem menuItem = this.mSearchMenuItem;
      if (menuItem != null)
        menuItem.setEnabled(true); 
    } 
    this.mMockText.setSelected(paramBoolean);
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public boolean onCreateOptionsMenu(Menu paramMenu) {
    getMenuInflater().inflate(2131492864, paramMenu);
    MenuItem menuItem = paramMenu.findItem(2131296293);
    this.mSearchMenuItem = menuItem;
    menuItem.setEnabled(this.mMocking ^ true);
    SearchView searchView = (SearchView)menuItem.getActionView();
    searchView.setImeOptions(3);
    searchView.setQueryHint(getString(2131624072));
    menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
          public boolean onMenuItemActionCollapse(MenuItem param1MenuItem) {
            ChooseLocationActivity.this.mSearchLayout.setVisibility(8);
            return true;
          }
          
          public boolean onMenuItemActionExpand(MenuItem param1MenuItem) {
            ChooseLocationActivity.this.mSearchLayout.setVisibility(0);
            return true;
          }
        });
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
          public boolean onQueryTextChange(String param1String) {
            return true;
          }
          
          public boolean onQueryTextSubmit(String param1String) {
            if (!TextUtils.isEmpty(param1String)) {
              ChooseLocationActivity.this.searchLocation(param1String);
            } else {
              ChooseLocationActivity.this.mSearchAdapter.clear();
              ChooseLocationActivity.this.mSearchAdapter.notifyDataSetChanged();
            } 
            return true;
          }
        });
    return true;
  }
  
  protected void onDestroy() {
    super.onDestroy();
    this.mapView.onDestroy();
  }
  
  public void onLocationChanged(TencentLocation paramTencentLocation, int paramInt, String paramString) {
    this.isFindLocation = false;
    if (paramTencentLocation != null) {
      TencentLocationManager.getInstance((Context)this).removeUpdates(this);
      this.mLocation.accuracy = paramTencentLocation.getAccuracy();
      this.mLocation.bearing = paramTencentLocation.getBearing();
      this.mLocation.altitude = paramTencentLocation.getAltitude();
      this.mLocation.latitude = paramTencentLocation.getLatitude();
      this.mLocation.longitude = paramTencentLocation.getLongitude();
      gotoLocation((String)null, this.mLocation.getLatitude(), this.mLocation.getLongitude(), true);
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("定位失败,");
      stringBuilder.append(paramInt);
      stringBuilder.append(": ");
      stringBuilder.append(paramString);
      VLog.e("TMap", stringBuilder.toString());
    } 
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
    return (paramMenuItem.getItemId() != 2131296293) ? super.onOptionsItemSelected(paramMenuItem) : true;
  }
  
  protected void onPause() {
    super.onPause();
    this.mapView.onPause();
  }
  
  protected void onResume() {
    super.onResume();
    this.mapView.onResume();
  }
  
  protected void onSaveInstanceState(Bundle paramBundle) {
    super.onSaveInstanceState(paramBundle);
    this.mapView.onSaveInstanceState(paramBundle);
  }
  
  public void onStatusUpdate(String paramString1, int paramInt, String paramString2) {}
  
  private static class MapSearchResult {
    private static final MapSearchResult NULL = new MapSearchResult();
    
    private String address;
    
    private String city;
    
    private double lat;
    
    private double lng;
    
    private MapSearchResult() {}
    
    public MapSearchResult(String param1String) {
      this.address = param1String;
    }
    
    private MapSearchResult(String param1String, double param1Double1, double param1Double2) {
      this.address = param1String;
      this.lat = param1Double1;
      this.lng = param1Double2;
    }
    
    private void setAddress(String param1String) {
      this.address = param1String;
    }
    
    private void setCity(String param1String) {
      this.city = param1String;
    }
    
    public String toString() {
      return this.address;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\location\ChooseLocationActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */