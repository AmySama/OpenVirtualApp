package io.virtualapp.home.location;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.stub.StubApp;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.LocationBean;
import io.virtualapp.widgets.CostomSearchView;
import java.util.ArrayList;
import java.util.List;

public class SearchAddressActivity extends VActivity implements OnGetSuggestionResultListener {
  public static final String KEY_PICKED_CITY = "picked_city";
  
  private List<LocationBean> LocationBeans;
  
  private MyListAdapter mAdapter;
  
  private String mCityName = "长沙市";
  
  private ListView mListView;
  
  private CostomSearchView mSearchView;
  
  private SuggestionSearch mSuggestionSearch = null;
  
  static {
    StubApp.interface11(9771);
  }
  
  private void initSuggestionSearch() {
    SuggestionSearch suggestionSearch = SuggestionSearch.newInstance();
    this.mSuggestionSearch = suggestionSearch;
    suggestionSearch.setOnGetSuggestionResultListener(this);
  }
  
  private void initView() {
    CostomSearchView costomSearchView = (CostomSearchView)findViewById(2131296665);
    this.mSearchView = costomSearchView;
    costomSearchView.getLeftTextView().setText(this.mCityName);
    this.mSearchView.setBackground(2131099881);
    this.mSearchView.setEditTextHint("请输入地址");
    this.mSearchView.getLeftTextView().setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {}
        });
    this.mSearchView.getRightTextView().setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            SearchAddressActivity.this.finish();
          }
        });
    this.mSearchView.getCenterEditText().setFocusableInTouchMode(true);
    this.mSearchView.getCenterEditText().setFocusable(true);
    this.mSearchView.getCenterEditText().addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {
            String str = param1Editable.toString();
            if (!TextUtils.isEmpty(str))
              SearchAddressActivity.this.mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(str).city(SearchAddressActivity.this.mCityName)); 
          }
          
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
        });
    this.mListView = (ListView)findViewById(2131296575);
    MyListAdapter myListAdapter = new MyListAdapter((Context)this);
    this.mAdapter = myListAdapter;
    this.mListView.setAdapter((ListAdapter)myListAdapter);
    this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
            LocationBean locationBean = SearchAddressActivity.this.LocationBeans.get(param1Int);
            if (locationBean != null && locationBean.getLocation() != null && (locationBean.getLocation()).latitude != 0.0D && (locationBean.getLocation()).longitude != 0.0D) {
              Intent intent = new Intent();
              intent.putExtra("picked_city", (Parcelable)locationBean.getLocation());
              SearchAddressActivity.this.setResult(-1, intent);
              SearchAddressActivity.this.finish();
            } 
          }
        });
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  protected void onDestroy() {
    this.mSuggestionSearch.destroy();
    super.onDestroy();
  }
  
  public void onGetSuggestionResult(SuggestionResult paramSuggestionResult) {
    if (paramSuggestionResult != null && paramSuggestionResult.getAllSuggestions() != null) {
      this.LocationBeans = new ArrayList<LocationBean>();
      for (SuggestionResult.SuggestionInfo suggestionInfo : paramSuggestionResult.getAllSuggestions()) {
        LocationBean locationBean = new LocationBean();
        locationBean.setName(suggestionInfo.key);
        locationBean.setCityName(suggestionInfo.city);
        locationBean.setLocation(suggestionInfo.pt);
        locationBean.setUid(suggestionInfo.uid);
        locationBean.setAddress(suggestionInfo.district);
        this.LocationBeans.add(locationBean);
      } 
      this.mAdapter.setData(this.LocationBeans);
    } 
  }
  
  class MyListAdapter extends BaseAdapter {
    private List<LocationBean> address;
    
    private Context context;
    
    public MyListAdapter(Context param1Context) {
      this.context = param1Context;
      this.address = new ArrayList<LocationBean>();
    }
    
    private void setData(List<LocationBean> param1List) {
      this.address = param1List;
      notifyDataSetChanged();
    }
    
    public int getCount() {
      return this.address.size();
    }
    
    public Object getItem(int param1Int) {
      return this.address.get(param1Int);
    }
    
    public long getItemId(int param1Int) {
      return param1Int;
    }
    
    public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
      SearchAddressActivity.ViewHolder viewHolder;
      if (param1View == null) {
        param1View = LayoutInflater.from(this.context).inflate(2131427456, null);
        viewHolder = new SearchAddressActivity.ViewHolder();
        param1View.setTag(viewHolder);
      } else {
        viewHolder = (SearchAddressActivity.ViewHolder)param1View.getTag();
      } 
      viewHolder.addressNameView = (TextView)param1View.findViewById(2131296303);
      viewHolder.addressCodeView = (TextView)param1View.findViewById(2131296302);
      if (!TextUtils.isEmpty(((LocationBean)this.address.get(param1Int)).getName()))
        viewHolder.addressNameView.setText(((LocationBean)this.address.get(param1Int)).getName()); 
      if (!TextUtils.isEmpty(((LocationBean)this.address.get(param1Int)).getAddress()))
        viewHolder.addressCodeView.setText(((LocationBean)this.address.get(param1Int)).getAddress()); 
      return param1View;
    }
  }
  
  class ViewHolder {
    TextView addressCodeView;
    
    TextView addressNameView;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\location\SearchAddressActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */