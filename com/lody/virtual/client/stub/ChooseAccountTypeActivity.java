package com.lody.virtual.client.stub;

import android.accounts.AuthenticatorDescription;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lody.virtual.R;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VAccountManager;
import com.lody.virtual.helper.utils.VLog;
import com.stub.StubApp;
import java.util.ArrayList;
import java.util.HashMap;

public class ChooseAccountTypeActivity extends Activity {
  private static final boolean DEBUG = false;
  
  public static final String KEY_USER_ID = "userId";
  
  private static final String TAG = "AccountChooser";
  
  private ArrayList<AuthInfo> mAuthenticatorInfosToDisplay;
  
  private int mCallingUid;
  
  private HashMap<String, AuthInfo> mTypeToAuthenticatorInfo = new HashMap<String, AuthInfo>();
  
  static {
    StubApp.interface11(5980);
  }
  
  private void buildTypeToAuthDescriptionMap() {
    AuthenticatorDescription[] arrayOfAuthenticatorDescription = VAccountManager.get().getAuthenticatorTypes(this.mCallingUid);
    int i = arrayOfAuthenticatorDescription.length;
    for (byte b = 0;; b++) {
      AuthenticatorDescription authenticatorDescription;
      if (b < i) {
        authenticatorDescription = arrayOfAuthenticatorDescription[b];
        String str1 = null;
        String str2 = null;
        try {
          Resources resources = VirtualCore.get().getResources(authenticatorDescription.packageName);
          Drawable drawable = resources.getDrawable(authenticatorDescription.iconId);
          str2 = str1;
          try {
            CharSequence charSequence = resources.getText(authenticatorDescription.labelId);
            str2 = str1;
            str1 = charSequence.toString();
            str2 = str1;
            str1 = charSequence.toString();
            str2 = str1;
            authInfo = new AuthInfo(authenticatorDescription, str2, drawable);
            this.mTypeToAuthenticatorInfo.put(authenticatorDescription.type, authInfo);
            b++;
            continue;
          } catch (android.content.res.Resources.NotFoundException notFoundException1) {}
        } catch (android.content.res.Resources.NotFoundException notFoundException) {
          notFoundException = null;
        } 
      } else {
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("No icon resource for account type ");
      stringBuilder.append(authenticatorDescription.type);
      VLog.w("AccountChooser", stringBuilder.toString(), new Object[0]);
      AuthInfo authInfo = new AuthInfo(authenticatorDescription, (String)authInfo, (Drawable)notFoundException);
      this.mTypeToAuthenticatorInfo.put(authenticatorDescription.type, authInfo);
    } 
  }
  
  private void setResultAndFinish(String paramString) {
    Bundle bundle = new Bundle();
    bundle.putString("accountType", paramString);
    setResult(-1, (new Intent()).putExtras(bundle));
    finish();
  }
  
  public native void onCreate(Bundle paramBundle);
  
  private static class AccountArrayAdapter extends ArrayAdapter<AuthInfo> {
    private ArrayList<ChooseAccountTypeActivity.AuthInfo> mInfos;
    
    private LayoutInflater mLayoutInflater;
    
    AccountArrayAdapter(Context param1Context, int param1Int, ArrayList<ChooseAccountTypeActivity.AuthInfo> param1ArrayList) {
      super(param1Context, param1Int, param1ArrayList);
      this.mInfos = param1ArrayList;
      this.mLayoutInflater = (LayoutInflater)param1Context.getSystemService("layout_inflater");
    }
    
    public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
      ChooseAccountTypeActivity.ViewHolder viewHolder;
      if (param1View == null) {
        param1View = this.mLayoutInflater.inflate(R.layout.choose_account_row, null);
        viewHolder = new ChooseAccountTypeActivity.ViewHolder();
        viewHolder.text = (TextView)param1View.findViewById(R.id.account_row_text);
        viewHolder.icon = (ImageView)param1View.findViewById(R.id.account_row_icon);
        param1View.setTag(viewHolder);
      } else {
        viewHolder = (ChooseAccountTypeActivity.ViewHolder)param1View.getTag();
      } 
      viewHolder.text.setText(((ChooseAccountTypeActivity.AuthInfo)this.mInfos.get(param1Int)).name);
      viewHolder.icon.setImageDrawable(((ChooseAccountTypeActivity.AuthInfo)this.mInfos.get(param1Int)).drawable);
      return param1View;
    }
  }
  
  private static class AuthInfo {
    final AuthenticatorDescription desc;
    
    final Drawable drawable;
    
    final String name;
    
    AuthInfo(AuthenticatorDescription param1AuthenticatorDescription, String param1String, Drawable param1Drawable) {
      this.desc = param1AuthenticatorDescription;
      this.name = param1String;
      this.drawable = param1Drawable;
    }
  }
  
  private static class ViewHolder {
    ImageView icon;
    
    TextView text;
    
    private ViewHolder() {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\ChooseAccountTypeActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */