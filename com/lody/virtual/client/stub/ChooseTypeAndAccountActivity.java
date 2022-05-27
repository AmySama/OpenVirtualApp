package com.lody.virtual.client.stub;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorDescription;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.lody.virtual.R;
import com.lody.virtual.client.ipc.VAccountManager;
import com.lody.virtual.helper.utils.VLog;
import com.stub.StubApp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ChooseTypeAndAccountActivity extends Activity implements AccountManagerCallback<Bundle> {
  private static final boolean DEBUG = false;
  
  public static final String EXTRA_ADD_ACCOUNT_AUTH_TOKEN_TYPE_STRING = "authTokenType";
  
  public static final String EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE = "addAccountOptions";
  
  public static final String EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY = "addAccountRequiredFeatures";
  
  public static final String EXTRA_ALLOWABLE_ACCOUNTS_ARRAYLIST = "allowableAccounts";
  
  public static final String EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY = "allowableAccountTypes";
  
  @Deprecated
  public static final String EXTRA_ALWAYS_PROMPT_FOR_ACCOUNT = "alwaysPromptForAccount";
  
  public static final String EXTRA_DESCRIPTION_TEXT_OVERRIDE = "descriptionTextOverride";
  
  public static final String EXTRA_SELECTED_ACCOUNT = "selectedAccount";
  
  private static final String KEY_INSTANCE_STATE_ACCOUNT_LIST = "accountList";
  
  private static final String KEY_INSTANCE_STATE_EXISTING_ACCOUNTS = "existingAccounts";
  
  private static final String KEY_INSTANCE_STATE_PENDING_REQUEST = "pendingRequest";
  
  private static final String KEY_INSTANCE_STATE_SELECTED_ACCOUNT_NAME = "selectedAccountName";
  
  private static final String KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT = "selectedAddAccount";
  
  public static final String KEY_USER_ID = "userId";
  
  public static final int REQUEST_ADD_ACCOUNT = 2;
  
  public static final int REQUEST_CHOOSE_TYPE = 1;
  
  public static final int REQUEST_NULL = 0;
  
  private static final int SELECTED_ITEM_NONE = -1;
  
  private static final String TAG = "AccountChooser";
  
  private ArrayList<Account> mAccounts;
  
  private int mCallingUserId;
  
  private String mDescriptionOverride;
  
  private boolean mDontShowPicker;
  
  private Parcelable[] mExistingAccounts = null;
  
  private Button mOkButton;
  
  private int mPendingRequest = 0;
  
  private String mSelectedAccountName = null;
  
  private boolean mSelectedAddNewAccount = false;
  
  private int mSelectedItemIndex;
  
  private Set<Account> mSetOfAllowableAccounts;
  
  private Set<String> mSetOfRelevantAccountTypes;
  
  static {
    StubApp.interface11(5982);
  }
  
  private ArrayList<Account> getAcceptableAccountChoices(VAccountManager paramVAccountManager) {
    Account[] arrayOfAccount = paramVAccountManager.getAccounts(this.mCallingUserId, null);
    ArrayList<Account> arrayList = new ArrayList(arrayOfAccount.length);
    int i = arrayOfAccount.length;
    for (byte b = 0; b < i; b++) {
      Account account = arrayOfAccount[b];
      Set<Account> set = this.mSetOfAllowableAccounts;
      if (set == null || set.contains(account)) {
        Set<String> set1 = this.mSetOfRelevantAccountTypes;
        if (set1 == null || set1.contains(account.type))
          arrayList.add(account); 
      } 
    } 
    return arrayList;
  }
  
  private Set<Account> getAllowableAccountSet(Intent paramIntent) {
    ArrayList arrayList = paramIntent.getParcelableArrayListExtra("allowableAccounts");
    if (arrayList != null) {
      HashSet<Account> hashSet = new HashSet(arrayList.size());
      Iterator<Account> iterator = arrayList.iterator();
      while (true) {
        HashSet<Account> hashSet1 = hashSet;
        if (iterator.hasNext()) {
          hashSet.add(iterator.next());
          continue;
        } 
        break;
      } 
    } else {
      arrayList = null;
    } 
    return (Set<Account>)arrayList;
  }
  
  private int getItemIndexToSelect(ArrayList<Account> paramArrayList, String paramString, boolean paramBoolean) {
    if (paramBoolean)
      return paramArrayList.size(); 
    for (byte b = 0; b < paramArrayList.size(); b++) {
      if (((Account)paramArrayList.get(b)).name.equals(paramString))
        return b; 
    } 
    return -1;
  }
  
  private String[] getListOfDisplayableOptions(ArrayList<Account> paramArrayList) {
    String[] arrayOfString = new String[paramArrayList.size() + 1];
    for (byte b = 0; b < paramArrayList.size(); b++)
      arrayOfString[b] = ((Account)paramArrayList.get(b)).name; 
    arrayOfString[paramArrayList.size()] = getResources().getString(R.string.add_account_button_label);
    return arrayOfString;
  }
  
  private Set<String> getReleventAccountTypes(Intent paramIntent) {
    String[] arrayOfString = paramIntent.getStringArrayExtra("allowableAccountTypes");
    AuthenticatorDescription[] arrayOfAuthenticatorDescription = VAccountManager.get().getAuthenticatorTypes(this.mCallingUserId);
    HashSet<String> hashSet2 = new HashSet(arrayOfAuthenticatorDescription.length);
    int i = arrayOfAuthenticatorDescription.length;
    for (byte b = 0; b < i; b++)
      hashSet2.add((arrayOfAuthenticatorDescription[b]).type); 
    HashSet<String> hashSet1 = hashSet2;
    if (arrayOfString != null) {
      hashSet1 = new HashSet<String>();
      Collections.addAll(hashSet1, arrayOfString);
      hashSet1.retainAll(hashSet2);
    } 
    return hashSet1;
  }
  
  private void onAccountSelected(Account paramAccount) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("selected account ");
    stringBuilder.append(paramAccount);
    Log.d("AccountChooser", stringBuilder.toString());
    setResultAndFinish(paramAccount.name, paramAccount.type);
  }
  
  private void overrideDescriptionIfSupplied(String paramString) {
    TextView textView = (TextView)findViewById(R.id.description);
    if (!TextUtils.isEmpty(paramString)) {
      textView.setText(paramString);
    } else {
      textView.setVisibility(8);
    } 
  }
  
  private void populateUIAccountList(String[] paramArrayOfString) {
    ListView listView = (ListView)findViewById(16908298);
    listView.setAdapter((ListAdapter)new ArrayAdapter((Context)this, 17367055, (Object[])paramArrayOfString));
    listView.setChoiceMode(1);
    listView.setItemsCanFocus(false);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
            ChooseTypeAndAccountActivity.access$002(ChooseTypeAndAccountActivity.this, param1Int);
            ChooseTypeAndAccountActivity.this.mOkButton.setEnabled(true);
          }
        });
    int i = this.mSelectedItemIndex;
    if (i != -1)
      listView.setItemChecked(i, true); 
  }
  
  private void setNonLabelThemeAndCallSuperCreate(Bundle paramBundle) {
    if (Build.VERSION.SDK_INT >= 21) {
      setTheme(16974396);
    } else {
      setTheme(16973941);
    } 
    super.onCreate(paramBundle);
  }
  
  private void setResultAndFinish(String paramString1, String paramString2) {
    Bundle bundle = new Bundle();
    bundle.putString("authAccount", paramString1);
    bundle.putString("accountType", paramString2);
    setResult(-1, (new Intent()).putExtras(bundle));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("ChooseTypeAndAccountActivity.setResultAndFinish: selected account ");
    stringBuilder.append(paramString1);
    stringBuilder.append(", ");
    stringBuilder.append(paramString2);
    VLog.v("AccountChooser", stringBuilder.toString());
    finish();
  }
  
  private void startChooseAccountTypeActivity() {
    Intent intent = new Intent((Context)this, ChooseAccountTypeActivity.class);
    intent.setFlags(524288);
    intent.putExtra("allowableAccountTypes", getIntent().getStringArrayExtra("allowableAccountTypes"));
    intent.putExtra("addAccountOptions", getIntent().getBundleExtra("addAccountOptions"));
    intent.putExtra("addAccountRequiredFeatures", getIntent().getStringArrayExtra("addAccountRequiredFeatures"));
    intent.putExtra("authTokenType", getIntent().getStringExtra("authTokenType"));
    intent.putExtra("userId", this.mCallingUserId);
    startActivityForResult(intent, 1);
    this.mPendingRequest = 1;
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    // Byte code:
    //   0: ldc 'AccountChooser'
    //   2: iconst_2
    //   3: invokestatic isLoggable : (Ljava/lang/String;I)Z
    //   6: ifeq -> 122
    //   9: aload_3
    //   10: ifnull -> 28
    //   13: aload_3
    //   14: invokevirtual getExtras : ()Landroid/os/Bundle;
    //   17: ifnull -> 28
    //   20: aload_3
    //   21: invokevirtual getExtras : ()Landroid/os/Bundle;
    //   24: invokevirtual keySet : ()Ljava/util/Set;
    //   27: pop
    //   28: aload_3
    //   29: ifnull -> 41
    //   32: aload_3
    //   33: invokevirtual getExtras : ()Landroid/os/Bundle;
    //   36: astore #4
    //   38: goto -> 44
    //   41: aconst_null
    //   42: astore #4
    //   44: new java/lang/StringBuilder
    //   47: dup
    //   48: invokespecial <init> : ()V
    //   51: astore #5
    //   53: aload #5
    //   55: ldc_w 'ChooseTypeAndAccountActivity.onActivityResult(reqCode='
    //   58: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: pop
    //   62: aload #5
    //   64: iload_1
    //   65: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   68: pop
    //   69: aload #5
    //   71: ldc_w ', resCode='
    //   74: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: pop
    //   78: aload #5
    //   80: iload_2
    //   81: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   84: pop
    //   85: aload #5
    //   87: ldc_w ', extras='
    //   90: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: pop
    //   94: aload #5
    //   96: aload #4
    //   98: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   101: pop
    //   102: aload #5
    //   104: ldc_w ')'
    //   107: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: pop
    //   111: ldc 'AccountChooser'
    //   113: aload #5
    //   115: invokevirtual toString : ()Ljava/lang/String;
    //   118: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   121: pop
    //   122: aload_0
    //   123: iconst_0
    //   124: putfield mPendingRequest : I
    //   127: iload_2
    //   128: ifne -> 151
    //   131: aload_0
    //   132: getfield mAccounts : Ljava/util/ArrayList;
    //   135: invokevirtual isEmpty : ()Z
    //   138: ifeq -> 150
    //   141: aload_0
    //   142: iconst_0
    //   143: invokevirtual setResult : (I)V
    //   146: aload_0
    //   147: invokevirtual finish : ()V
    //   150: return
    //   151: iload_2
    //   152: iconst_m1
    //   153: if_icmpne -> 393
    //   156: iload_1
    //   157: iconst_1
    //   158: if_icmpne -> 195
    //   161: aload_3
    //   162: ifnull -> 183
    //   165: aload_3
    //   166: ldc_w 'accountType'
    //   169: invokevirtual getStringExtra : (Ljava/lang/String;)Ljava/lang/String;
    //   172: astore_3
    //   173: aload_3
    //   174: ifnull -> 183
    //   177: aload_0
    //   178: aload_3
    //   179: invokevirtual runAddAccountForAuthenticator : (Ljava/lang/String;)V
    //   182: return
    //   183: ldc 'AccountChooser'
    //   185: ldc_w 'ChooseTypeAndAccountActivity.onActivityResult: unable to find account type, pretending the request was canceled'
    //   188: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)I
    //   191: pop
    //   192: goto -> 384
    //   195: iload_1
    //   196: iconst_2
    //   197: if_icmpne -> 384
    //   200: aload_3
    //   201: ifnull -> 224
    //   204: aload_3
    //   205: ldc_w 'authAccount'
    //   208: invokevirtual getStringExtra : (Ljava/lang/String;)Ljava/lang/String;
    //   211: astore #4
    //   213: aload_3
    //   214: ldc_w 'accountType'
    //   217: invokevirtual getStringExtra : (Ljava/lang/String;)Ljava/lang/String;
    //   220: astore_3
    //   221: goto -> 230
    //   224: aconst_null
    //   225: astore #4
    //   227: aload #4
    //   229: astore_3
    //   230: aload #4
    //   232: ifnull -> 246
    //   235: aload #4
    //   237: astore #5
    //   239: aload_3
    //   240: astore #6
    //   242: aload_3
    //   243: ifnonnull -> 365
    //   246: invokestatic get : ()Lcom/lody/virtual/client/ipc/VAccountManager;
    //   249: aload_0
    //   250: getfield mCallingUserId : I
    //   253: aconst_null
    //   254: invokevirtual getAccounts : (ILjava/lang/String;)[Landroid/accounts/Account;
    //   257: astore #7
    //   259: new java/util/HashSet
    //   262: dup
    //   263: invokespecial <init> : ()V
    //   266: astore #8
    //   268: aload_0
    //   269: getfield mExistingAccounts : [Landroid/os/Parcelable;
    //   272: astore #5
    //   274: aload #5
    //   276: arraylength
    //   277: istore_2
    //   278: iconst_0
    //   279: istore_1
    //   280: iload_1
    //   281: iload_2
    //   282: if_icmpge -> 306
    //   285: aload #8
    //   287: aload #5
    //   289: iload_1
    //   290: aaload
    //   291: checkcast android/accounts/Account
    //   294: invokeinterface add : (Ljava/lang/Object;)Z
    //   299: pop
    //   300: iinc #1, 1
    //   303: goto -> 280
    //   306: aload #7
    //   308: arraylength
    //   309: istore_2
    //   310: iconst_0
    //   311: istore_1
    //   312: aload #4
    //   314: astore #5
    //   316: aload_3
    //   317: astore #6
    //   319: iload_1
    //   320: iload_2
    //   321: if_icmpge -> 365
    //   324: aload #7
    //   326: iload_1
    //   327: aaload
    //   328: astore #6
    //   330: aload #8
    //   332: aload #6
    //   334: invokeinterface contains : (Ljava/lang/Object;)Z
    //   339: ifne -> 359
    //   342: aload #6
    //   344: getfield name : Ljava/lang/String;
    //   347: astore #5
    //   349: aload #6
    //   351: getfield type : Ljava/lang/String;
    //   354: astore #6
    //   356: goto -> 365
    //   359: iinc #1, 1
    //   362: goto -> 312
    //   365: aload #5
    //   367: ifnonnull -> 375
    //   370: aload #6
    //   372: ifnull -> 384
    //   375: aload_0
    //   376: aload #5
    //   378: aload #6
    //   380: invokespecial setResultAndFinish : (Ljava/lang/String;Ljava/lang/String;)V
    //   383: return
    //   384: ldc 'AccountChooser'
    //   386: ldc_w 'ChooseTypeAndAccountActivity.onActivityResult: unable to find added account, pretending the request was canceled'
    //   389: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)I
    //   392: pop
    //   393: ldc 'AccountChooser'
    //   395: iconst_2
    //   396: invokestatic isLoggable : (Ljava/lang/String;I)Z
    //   399: ifeq -> 411
    //   402: ldc 'AccountChooser'
    //   404: ldc_w 'ChooseTypeAndAccountActivity.onActivityResult: canceled'
    //   407: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   410: pop
    //   411: aload_0
    //   412: iconst_0
    //   413: invokevirtual setResult : (I)V
    //   416: aload_0
    //   417: invokevirtual finish : ()V
    //   420: return
  }
  
  public void onCancelButtonClicked(View paramView) {
    onBackPressed();
  }
  
  public native void onCreate(Bundle paramBundle);
  
  protected void onDestroy() {
    if (Log.isLoggable("AccountChooser", 2))
      Log.v("AccountChooser", "ChooseTypeAndAccountActivity.onDestroy()"); 
    super.onDestroy();
  }
  
  public void onOkButtonClicked(View paramView) {
    if (this.mSelectedItemIndex == this.mAccounts.size()) {
      startChooseAccountTypeActivity();
    } else {
      int i = this.mSelectedItemIndex;
      if (i != -1)
        onAccountSelected(this.mAccounts.get(i)); 
    } 
  }
  
  protected void onSaveInstanceState(Bundle paramBundle) {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("pendingRequest", this.mPendingRequest);
    if (this.mPendingRequest == 2)
      paramBundle.putParcelableArray("existingAccounts", this.mExistingAccounts); 
    int i = this.mSelectedItemIndex;
    if (i != -1)
      if (i == this.mAccounts.size()) {
        paramBundle.putBoolean("selectedAddAccount", true);
      } else {
        paramBundle.putBoolean("selectedAddAccount", false);
        paramBundle.putString("selectedAccountName", ((Account)this.mAccounts.get(this.mSelectedItemIndex)).name);
      }  
    paramBundle.putParcelableArrayList("accountList", this.mAccounts);
  }
  
  public void run(AccountManagerFuture<Bundle> paramAccountManagerFuture) {
    // Byte code:
    //   0: aload_1
    //   1: invokeinterface getResult : ()Ljava/lang/Object;
    //   6: checkcast android/os/Bundle
    //   9: ldc_w 'intent'
    //   12: invokevirtual getParcelable : (Ljava/lang/String;)Landroid/os/Parcelable;
    //   15: checkcast android/content/Intent
    //   18: astore_1
    //   19: aload_1
    //   20: ifnull -> 72
    //   23: aload_0
    //   24: iconst_2
    //   25: putfield mPendingRequest : I
    //   28: aload_0
    //   29: invokestatic get : ()Lcom/lody/virtual/client/ipc/VAccountManager;
    //   32: aload_0
    //   33: getfield mCallingUserId : I
    //   36: aconst_null
    //   37: invokevirtual getAccounts : (ILjava/lang/String;)[Landroid/accounts/Account;
    //   40: putfield mExistingAccounts : [Landroid/os/Parcelable;
    //   43: aload_1
    //   44: aload_1
    //   45: invokevirtual getFlags : ()I
    //   48: ldc_w -268435457
    //   51: iand
    //   52: invokevirtual setFlags : (I)Landroid/content/Intent;
    //   55: pop
    //   56: aload_0
    //   57: aload_1
    //   58: iconst_2
    //   59: invokevirtual startActivityForResult : (Landroid/content/Intent;I)V
    //   62: return
    //   63: astore_1
    //   64: goto -> 68
    //   67: astore_1
    //   68: aload_1
    //   69: invokevirtual printStackTrace : ()V
    //   72: new android/os/Bundle
    //   75: dup
    //   76: invokespecial <init> : ()V
    //   79: astore_1
    //   80: aload_1
    //   81: ldc_w 'errorMessage'
    //   84: ldc_w 'error communicating with server'
    //   87: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   90: aload_0
    //   91: iconst_m1
    //   92: new android/content/Intent
    //   95: dup
    //   96: invokespecial <init> : ()V
    //   99: aload_1
    //   100: invokevirtual putExtras : (Landroid/os/Bundle;)Landroid/content/Intent;
    //   103: invokevirtual setResult : (ILandroid/content/Intent;)V
    //   106: aload_0
    //   107: invokevirtual finish : ()V
    //   110: return
    //   111: astore_1
    //   112: aload_0
    //   113: iconst_0
    //   114: invokevirtual setResult : (I)V
    //   117: aload_0
    //   118: invokevirtual finish : ()V
    //   121: return
    // Exception table:
    //   from	to	target	type
    //   0	19	111	android/accounts/OperationCanceledException
    //   0	19	67	java/io/IOException
    //   0	19	63	android/accounts/AuthenticatorException
    //   23	62	111	android/accounts/OperationCanceledException
    //   23	62	67	java/io/IOException
    //   23	62	63	android/accounts/AuthenticatorException
  }
  
  protected void runAddAccountForAuthenticator(String paramString) {
    if (Log.isLoggable("AccountChooser", 2)) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("runAddAccountForAuthenticator: ");
      stringBuilder.append(paramString);
      Log.v("AccountChooser", stringBuilder.toString());
    } 
    Bundle bundle = getIntent().getBundleExtra("addAccountOptions");
    String[] arrayOfString = getIntent().getStringArrayExtra("addAccountRequiredFeatures");
    String str = getIntent().getStringExtra("authTokenType");
    VAccountManager.get().addAccount(this.mCallingUserId, paramString, str, arrayOfString, bundle, null, this, null);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\ChooseTypeAndAccountActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */