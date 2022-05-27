package com.lody.virtual.client.stub;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PatternMatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.lody.virtual.R;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.helper.utils.VLog;
import com.stub.StubApp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ResolverActivity extends Activity implements AdapterView.OnItemClickListener {
  private static final boolean DEBUG = false;
  
  private static final String TAG = "ResolverActivity";
  
  private AlertDialog dialog;
  
  private ResolveListAdapter mAdapter;
  
  private Button mAlwaysButton;
  
  private boolean mAlwaysUseOption;
  
  private int mIconDpi;
  
  private int mIconSize;
  
  private int mLastSelected = -1;
  
  private int mLaunchedFromUid;
  
  private ListView mListView;
  
  private int mMaxColumns;
  
  private Button mOnceButton;
  
  protected Bundle mOptions;
  
  private PackageManager mPm;
  
  private boolean mRegistered;
  
  protected int mRequestCode;
  
  protected IBinder mResultTo;
  
  protected String mResultWho;
  
  private boolean mShowExtended;
  
  static {
    StubApp.interface11(5998);
  }
  
  private Intent makeMyIntent() {
    Intent intent = new Intent(getIntent());
    intent.setComponent(null);
    intent.setFlags(intent.getFlags() & 0xFF7FFFFF);
    return intent;
  }
  
  Drawable getIcon(Resources paramResources, int paramInt) {
    try {
      Drawable drawable = paramResources.getDrawableForDensity(paramInt, this.mIconDpi);
    } catch (android.content.res.Resources.NotFoundException notFoundException) {
      notFoundException = null;
    } 
    return (Drawable)notFoundException;
  }
  
  Drawable loadIconForResolveInfo(ResolveInfo paramResolveInfo) {
    try {
      if (paramResolveInfo.resolvePackageName != null && paramResolveInfo.icon != 0) {
        Drawable drawable = getIcon(this.mPm.getResourcesForApplication(paramResolveInfo.resolvePackageName), paramResolveInfo.icon);
        if (drawable != null)
          return drawable; 
      } 
      int i = paramResolveInfo.getIconResource();
      if (i != 0) {
        Drawable drawable = getIcon(this.mPm.getResourcesForApplication(paramResolveInfo.activityInfo.packageName), i);
        if (drawable != null)
          return drawable; 
      } 
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Couldn't find resources for package\n");
      stringBuilder.append(VLog.getStackTraceString((Throwable)nameNotFoundException));
      VLog.e("ResolverActivity", stringBuilder.toString());
    } 
    return paramResolveInfo.loadIcon(this.mPm);
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  protected void onCreate(Bundle paramBundle, Intent paramIntent, CharSequence paramCharSequence, Intent[] paramArrayOfIntent, List<ResolveInfo> paramList, boolean paramBoolean, int paramInt) {
    super.onCreate(paramBundle);
    this.mLaunchedFromUid = paramInt;
    this.mPm = getPackageManager();
    this.mAlwaysUseOption = paramBoolean;
    this.mMaxColumns = getResources().getInteger(R.integer.config_maxResolverActivityColumns);
    this.mRegistered = true;
    ActivityManager activityManager = (ActivityManager)getSystemService("activity");
    this.mIconDpi = activityManager.getLauncherLargeIconDensity();
    this.mIconSize = activityManager.getLauncherLargeIconSize();
    ResolveListAdapter resolveListAdapter = new ResolveListAdapter((Context)this, paramIntent, paramArrayOfIntent, paramList, this.mLaunchedFromUid);
    this.mAdapter = resolveListAdapter;
    paramInt = resolveListAdapter.getCount();
    if (Build.VERSION.SDK_INT >= 17 && this.mLaunchedFromUid < 0) {
      finish();
      return;
    } 
    if (paramInt == 1) {
      startSelected(0, false);
      this.mRegistered = false;
      finish();
      return;
    } 
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
    builder.setTitle(paramCharSequence);
    if (paramInt > 1) {
      ListView listView = new ListView((Context)this);
      this.mListView = listView;
      listView.setAdapter((ListAdapter)this.mAdapter);
      this.mListView.setOnItemClickListener(this);
      this.mListView.setOnItemLongClickListener(new ItemLongClickListener());
      builder.setView((View)this.mListView);
      if (paramBoolean)
        this.mListView.setChoiceMode(1); 
    } else {
      builder.setMessage(R.string.noApplications);
    } 
    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
          public void onCancel(DialogInterface param1DialogInterface) {
            ResolverActivity.this.finish();
          }
        });
    this.dialog = builder.show();
  }
  
  protected void onDestroy() {
    AlertDialog alertDialog = this.dialog;
    if (alertDialog != null && alertDialog.isShowing())
      this.dialog.dismiss(); 
    super.onDestroy();
  }
  
  protected void onIntentSelected(ResolveInfo paramResolveInfo, Intent paramIntent, boolean paramBoolean) {
    if (this.mAlwaysUseOption && this.mAdapter.mOrigResolveList != null) {
      IntentFilter intentFilter1 = new IntentFilter();
      if (paramIntent.getAction() != null)
        intentFilter1.addAction(paramIntent.getAction()); 
      Set set = paramIntent.getCategories();
      if (set != null) {
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext())
          intentFilter1.addCategory(iterator.next()); 
      } 
      intentFilter1.addCategory("android.intent.category.DEFAULT");
      int i = paramResolveInfo.match & 0xFFF0000;
      Uri uri = paramIntent.getData();
      String str = null;
      IntentFilter intentFilter2 = intentFilter1;
      if (i == 6291456) {
        String str1 = paramIntent.resolveType((Context)this);
        intentFilter2 = intentFilter1;
        if (str1 != null)
          try {
            intentFilter1.addDataType(str1);
            intentFilter2 = intentFilter1;
          } catch (android.content.IntentFilter.MalformedMimeTypeException malformedMimeTypeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("mimeType\n");
            stringBuilder.append(VLog.getStackTraceString((Throwable)malformedMimeTypeException));
            VLog.w("ResolverActivity", stringBuilder.toString(), new Object[0]);
            malformedMimeTypeException = null;
          }  
      } 
      if (uri != null && uri.getScheme() != null && (i != 6291456 || (!"file".equals(uri.getScheme()) && !"content".equals(uri.getScheme())))) {
        malformedMimeTypeException.addDataScheme(uri.getScheme());
        if (Build.VERSION.SDK_INT >= 19) {
          Iterator<PatternMatcher> iterator2 = paramResolveInfo.filter.schemeSpecificPartsIterator();
          if (iterator2 != null) {
            String str1 = uri.getSchemeSpecificPart();
            while (str1 != null && iterator2.hasNext()) {
              PatternMatcher patternMatcher = iterator2.next();
              if (patternMatcher.match(str1)) {
                malformedMimeTypeException.addDataSchemeSpecificPart(patternMatcher.getPath(), patternMatcher.getType());
                break;
              } 
            } 
          } 
          iterator2 = paramResolveInfo.filter.authoritiesIterator();
          if (iterator2 != null)
            while (iterator2.hasNext()) {
              IntentFilter.AuthorityEntry authorityEntry = (IntentFilter.AuthorityEntry)iterator2.next();
              if (authorityEntry.match(uri) >= 0) {
                i = authorityEntry.getPort();
                String str1 = authorityEntry.getHost();
                if (i >= 0)
                  str = Integer.toString(i); 
                malformedMimeTypeException.addDataAuthority(str1, str);
                break;
              } 
            }  
          Iterator<PatternMatcher> iterator1 = paramResolveInfo.filter.pathsIterator();
          if (iterator1 != null) {
            str = uri.getPath();
            while (str != null && iterator1.hasNext()) {
              PatternMatcher patternMatcher = iterator1.next();
              if (patternMatcher.match(str)) {
                malformedMimeTypeException.addDataPath(patternMatcher.getPath(), patternMatcher.getType());
                break;
              } 
            } 
          } 
        } 
      } 
      if (malformedMimeTypeException != null) {
        int j = this.mAdapter.mOrigResolveList.size();
        ComponentName[] arrayOfComponentName = new ComponentName[j];
        byte b = 0;
        for (i = 0; b < j; i = k) {
          ResolveInfo resolveInfo = this.mAdapter.mOrigResolveList.get(b);
          arrayOfComponentName[b] = new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
          int k = i;
          if (resolveInfo.match > i)
            k = resolveInfo.match; 
          b++;
        } 
        if (paramBoolean) {
          getPackageManager().addPreferredActivity((IntentFilter)malformedMimeTypeException, i, arrayOfComponentName, paramIntent.getComponent());
        } else {
          try {
            Reflect.on(VClient.get().getCurrentApplication().getPackageManager()).call("setLastChosenActivity", new Object[] { paramIntent, paramIntent.resolveTypeIfNeeded(getContentResolver()), Integer.valueOf(65536), malformedMimeTypeException, Integer.valueOf(i), paramIntent.getComponent() });
          } catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error calling setLastChosenActivity\n");
            stringBuilder.append(VLog.getStackTraceString(exception));
            VLog.d("ResolverActivity", stringBuilder.toString(), new Object[0]);
          } 
        } 
      } 
    } 
    if (paramIntent != null) {
      paramIntent = ComponentUtils.processOutsideIntent(this.mLaunchedFromUid, VirtualCore.get().isExtPackage(), new Intent(paramIntent));
      ActivityInfo activityInfo = VirtualCore.get().resolveActivityInfo(paramIntent, this.mLaunchedFromUid);
      if (activityInfo == null) {
        startActivity(paramIntent);
      } else {
        paramIntent.addFlags(33554432);
        if (VActivityManager.get().startActivity(paramIntent, activityInfo, this.mResultTo, this.mOptions, this.mResultWho, this.mRequestCode, null, this.mLaunchedFromUid) != 0 && this.mResultTo != null && this.mRequestCode > 0)
          VActivityManager.get().sendCancelActivityResult(this.mResultTo, this.mResultWho, this.mRequestCode); 
      } 
    } 
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
    boolean bool;
    int i = this.mListView.getCheckedItemPosition();
    if (i != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    if (this.mAlwaysUseOption && (!bool || this.mLastSelected != i)) {
      this.mAlwaysButton.setEnabled(bool);
      this.mOnceButton.setEnabled(bool);
      if (bool)
        this.mListView.smoothScrollToPosition(i); 
      this.mLastSelected = i;
    } else {
      startSelected(paramInt, false);
    } 
  }
  
  protected void onRestart() {
    super.onRestart();
    if (!this.mRegistered)
      this.mRegistered = true; 
    this.mAdapter.handlePackagesChanged();
  }
  
  protected void onRestoreInstanceState(Bundle paramBundle) {
    super.onRestoreInstanceState(paramBundle);
    if (this.mAlwaysUseOption) {
      boolean bool;
      int i = this.mListView.getCheckedItemPosition();
      if (i != -1) {
        bool = true;
      } else {
        bool = false;
      } 
      this.mLastSelected = i;
      this.mAlwaysButton.setEnabled(bool);
      this.mOnceButton.setEnabled(bool);
      if (bool)
        this.mListView.setSelection(i); 
    } 
  }
  
  protected void onStop() {
    super.onStop();
    if (this.mRegistered)
      this.mRegistered = false; 
    if ((getIntent().getFlags() & 0x10000000) != 0 && !isChangingConfigurations())
      finish(); 
  }
  
  void showAppDetails(ResolveInfo paramResolveInfo) {
    startActivity((new Intent()).setAction("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", paramResolveInfo.activityInfo.packageName, null)).addFlags(524288));
  }
  
  void startSelected(int paramInt, boolean paramBoolean) {
    if (isFinishing())
      return; 
    onIntentSelected(this.mAdapter.resolveInfoForPosition(paramInt), this.mAdapter.intentForPosition(paramInt), paramBoolean);
    finish();
  }
  
  private final class DisplayResolveInfo {
    Drawable displayIcon;
    
    CharSequence displayLabel;
    
    CharSequence extendedInfo;
    
    Intent origIntent;
    
    ResolveInfo ri;
    
    DisplayResolveInfo(ResolveInfo param1ResolveInfo, CharSequence param1CharSequence1, CharSequence param1CharSequence2, Intent param1Intent) {
      this.ri = param1ResolveInfo;
      this.displayLabel = param1CharSequence1;
      this.extendedInfo = param1CharSequence2;
      this.origIntent = param1Intent;
    }
  }
  
  class ItemLongClickListener implements AdapterView.OnItemLongClickListener {
    public boolean onItemLongClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
      ResolveInfo resolveInfo = ResolverActivity.this.mAdapter.resolveInfoForPosition(param1Int);
      ResolverActivity.this.showAppDetails(resolveInfo);
      return true;
    }
  }
  
  class LoadIconTask extends AsyncTask<DisplayResolveInfo, Void, DisplayResolveInfo> {
    protected ResolverActivity.DisplayResolveInfo doInBackground(ResolverActivity.DisplayResolveInfo... param1VarArgs) {
      ResolverActivity.DisplayResolveInfo displayResolveInfo = param1VarArgs[0];
      if (displayResolveInfo.displayIcon == null)
        displayResolveInfo.displayIcon = ResolverActivity.this.loadIconForResolveInfo(displayResolveInfo.ri); 
      return displayResolveInfo;
    }
    
    protected void onPostExecute(ResolverActivity.DisplayResolveInfo param1DisplayResolveInfo) {
      ResolverActivity.this.mAdapter.notifyDataSetChanged();
    }
  }
  
  private final class ResolveListAdapter extends BaseAdapter {
    private final List<ResolveInfo> mBaseResolveList;
    
    private final LayoutInflater mInflater;
    
    private int mInitialHighlight = -1;
    
    private final Intent[] mInitialIntents;
    
    private final Intent mIntent;
    
    private ResolveInfo mLastChosen;
    
    private final int mLaunchedFromUid;
    
    List<ResolverActivity.DisplayResolveInfo> mList;
    
    List<ResolveInfo> mOrigResolveList;
    
    public ResolveListAdapter(Context param1Context, Intent param1Intent, Intent[] param1ArrayOfIntent, List<ResolveInfo> param1List, int param1Int) {
      this.mIntent = new Intent(param1Intent);
      this.mInitialIntents = param1ArrayOfIntent;
      this.mBaseResolveList = param1List;
      this.mLaunchedFromUid = param1Int;
      this.mInflater = (LayoutInflater)param1Context.getSystemService("layout_inflater");
      this.mList = new ArrayList<ResolverActivity.DisplayResolveInfo>();
      rebuildList();
    }
    
    private final void bindView(View param1View, ResolverActivity.DisplayResolveInfo param1DisplayResolveInfo) {
      ResolverActivity.ViewHolder viewHolder = (ResolverActivity.ViewHolder)param1View.getTag();
      viewHolder.text.setText(param1DisplayResolveInfo.displayLabel);
      if (ResolverActivity.this.mShowExtended) {
        viewHolder.text2.setVisibility(0);
        viewHolder.text2.setText(param1DisplayResolveInfo.extendedInfo);
      } else {
        viewHolder.text2.setVisibility(8);
      } 
      if (param1DisplayResolveInfo.displayIcon == null)
        (new ResolverActivity.LoadIconTask()).execute((Object[])new ResolverActivity.DisplayResolveInfo[] { param1DisplayResolveInfo }); 
      viewHolder.icon.setImageDrawable(param1DisplayResolveInfo.displayIcon);
    }
    
    private void processGroup(List<ResolveInfo> param1List, int param1Int1, int param1Int2, ResolveInfo param1ResolveInfo, CharSequence param1CharSequence) {
      // Byte code:
      //   0: iconst_1
      //   1: istore #6
      //   3: iload_3
      //   4: iload_2
      //   5: isub
      //   6: iconst_1
      //   7: iadd
      //   8: iconst_1
      //   9: if_icmpne -> 109
      //   12: aload_0
      //   13: getfield mLastChosen : Landroid/content/pm/ResolveInfo;
      //   16: astore_1
      //   17: aload_1
      //   18: ifnull -> 79
      //   21: aload_1
      //   22: getfield activityInfo : Landroid/content/pm/ActivityInfo;
      //   25: getfield packageName : Ljava/lang/String;
      //   28: aload #4
      //   30: getfield activityInfo : Landroid/content/pm/ActivityInfo;
      //   33: getfield packageName : Ljava/lang/String;
      //   36: invokevirtual equals : (Ljava/lang/Object;)Z
      //   39: ifeq -> 79
      //   42: aload_0
      //   43: getfield mLastChosen : Landroid/content/pm/ResolveInfo;
      //   46: getfield activityInfo : Landroid/content/pm/ActivityInfo;
      //   49: getfield name : Ljava/lang/String;
      //   52: aload #4
      //   54: getfield activityInfo : Landroid/content/pm/ActivityInfo;
      //   57: getfield name : Ljava/lang/String;
      //   60: invokevirtual equals : (Ljava/lang/Object;)Z
      //   63: ifeq -> 79
      //   66: aload_0
      //   67: aload_0
      //   68: getfield mList : Ljava/util/List;
      //   71: invokeinterface size : ()I
      //   76: putfield mInitialHighlight : I
      //   79: aload_0
      //   80: getfield mList : Ljava/util/List;
      //   83: new com/lody/virtual/client/stub/ResolverActivity$DisplayResolveInfo
      //   86: dup
      //   87: aload_0
      //   88: getfield this$0 : Lcom/lody/virtual/client/stub/ResolverActivity;
      //   91: aload #4
      //   93: aload #5
      //   95: aconst_null
      //   96: aconst_null
      //   97: invokespecial <init> : (Lcom/lody/virtual/client/stub/ResolverActivity;Landroid/content/pm/ResolveInfo;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Landroid/content/Intent;)V
      //   100: invokeinterface add : (Ljava/lang/Object;)Z
      //   105: pop
      //   106: goto -> 451
      //   109: aload_0
      //   110: getfield this$0 : Lcom/lody/virtual/client/stub/ResolverActivity;
      //   113: iconst_1
      //   114: invokestatic access$202 : (Lcom/lody/virtual/client/stub/ResolverActivity;Z)Z
      //   117: pop
      //   118: iconst_0
      //   119: istore #7
      //   121: aload #4
      //   123: getfield activityInfo : Landroid/content/pm/ActivityInfo;
      //   126: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
      //   129: aload_0
      //   130: getfield this$0 : Lcom/lody/virtual/client/stub/ResolverActivity;
      //   133: invokestatic access$100 : (Lcom/lody/virtual/client/stub/ResolverActivity;)Landroid/content/pm/PackageManager;
      //   136: invokevirtual loadLabel : (Landroid/content/pm/PackageManager;)Ljava/lang/CharSequence;
      //   139: astore #8
      //   141: aload #8
      //   143: ifnonnull -> 149
      //   146: iconst_1
      //   147: istore #7
      //   149: iload #7
      //   151: istore #9
      //   153: iload_2
      //   154: istore #10
      //   156: iload #7
      //   158: ifne -> 270
      //   161: new java/util/HashSet
      //   164: dup
      //   165: invokespecial <init> : ()V
      //   168: astore #4
      //   170: aload #4
      //   172: aload #8
      //   174: invokevirtual add : (Ljava/lang/Object;)Z
      //   177: pop
      //   178: iload_2
      //   179: iconst_1
      //   180: iadd
      //   181: istore #10
      //   183: iload #10
      //   185: iload_3
      //   186: if_icmpgt -> 258
      //   189: aload_1
      //   190: iload #10
      //   192: invokeinterface get : (I)Ljava/lang/Object;
      //   197: checkcast android/content/pm/ResolveInfo
      //   200: getfield activityInfo : Landroid/content/pm/ActivityInfo;
      //   203: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
      //   206: aload_0
      //   207: getfield this$0 : Lcom/lody/virtual/client/stub/ResolverActivity;
      //   210: invokestatic access$100 : (Lcom/lody/virtual/client/stub/ResolverActivity;)Landroid/content/pm/PackageManager;
      //   213: invokevirtual loadLabel : (Landroid/content/pm/PackageManager;)Ljava/lang/CharSequence;
      //   216: astore #8
      //   218: iload #6
      //   220: istore #9
      //   222: aload #8
      //   224: ifnull -> 262
      //   227: aload #4
      //   229: aload #8
      //   231: invokevirtual contains : (Ljava/lang/Object;)Z
      //   234: ifeq -> 244
      //   237: iload #6
      //   239: istore #9
      //   241: goto -> 262
      //   244: aload #4
      //   246: aload #8
      //   248: invokevirtual add : (Ljava/lang/Object;)Z
      //   251: pop
      //   252: iinc #10, 1
      //   255: goto -> 183
      //   258: iload #7
      //   260: istore #9
      //   262: aload #4
      //   264: invokevirtual clear : ()V
      //   267: iload_2
      //   268: istore #10
      //   270: iload #10
      //   272: iload_3
      //   273: if_icmpgt -> 451
      //   276: aload_1
      //   277: iload #10
      //   279: invokeinterface get : (I)Ljava/lang/Object;
      //   284: checkcast android/content/pm/ResolveInfo
      //   287: astore #8
      //   289: aload_0
      //   290: getfield mLastChosen : Landroid/content/pm/ResolveInfo;
      //   293: astore #4
      //   295: aload #4
      //   297: ifnull -> 359
      //   300: aload #4
      //   302: getfield activityInfo : Landroid/content/pm/ActivityInfo;
      //   305: getfield packageName : Ljava/lang/String;
      //   308: aload #8
      //   310: getfield activityInfo : Landroid/content/pm/ActivityInfo;
      //   313: getfield packageName : Ljava/lang/String;
      //   316: invokevirtual equals : (Ljava/lang/Object;)Z
      //   319: ifeq -> 359
      //   322: aload_0
      //   323: getfield mLastChosen : Landroid/content/pm/ResolveInfo;
      //   326: getfield activityInfo : Landroid/content/pm/ActivityInfo;
      //   329: getfield name : Ljava/lang/String;
      //   332: aload #8
      //   334: getfield activityInfo : Landroid/content/pm/ActivityInfo;
      //   337: getfield name : Ljava/lang/String;
      //   340: invokevirtual equals : (Ljava/lang/Object;)Z
      //   343: ifeq -> 359
      //   346: aload_0
      //   347: aload_0
      //   348: getfield mList : Ljava/util/List;
      //   351: invokeinterface size : ()I
      //   356: putfield mInitialHighlight : I
      //   359: iload #9
      //   361: ifeq -> 401
      //   364: aload_0
      //   365: getfield mList : Ljava/util/List;
      //   368: new com/lody/virtual/client/stub/ResolverActivity$DisplayResolveInfo
      //   371: dup
      //   372: aload_0
      //   373: getfield this$0 : Lcom/lody/virtual/client/stub/ResolverActivity;
      //   376: aload #8
      //   378: aload #5
      //   380: aload #8
      //   382: getfield activityInfo : Landroid/content/pm/ActivityInfo;
      //   385: getfield packageName : Ljava/lang/String;
      //   388: aconst_null
      //   389: invokespecial <init> : (Lcom/lody/virtual/client/stub/ResolverActivity;Landroid/content/pm/ResolveInfo;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Landroid/content/Intent;)V
      //   392: invokeinterface add : (Ljava/lang/Object;)Z
      //   397: pop
      //   398: goto -> 445
      //   401: aload_0
      //   402: getfield mList : Ljava/util/List;
      //   405: new com/lody/virtual/client/stub/ResolverActivity$DisplayResolveInfo
      //   408: dup
      //   409: aload_0
      //   410: getfield this$0 : Lcom/lody/virtual/client/stub/ResolverActivity;
      //   413: aload #8
      //   415: aload #5
      //   417: aload #8
      //   419: getfield activityInfo : Landroid/content/pm/ActivityInfo;
      //   422: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
      //   425: aload_0
      //   426: getfield this$0 : Lcom/lody/virtual/client/stub/ResolverActivity;
      //   429: invokestatic access$100 : (Lcom/lody/virtual/client/stub/ResolverActivity;)Landroid/content/pm/PackageManager;
      //   432: invokevirtual loadLabel : (Landroid/content/pm/PackageManager;)Ljava/lang/CharSequence;
      //   435: aconst_null
      //   436: invokespecial <init> : (Lcom/lody/virtual/client/stub/ResolverActivity;Landroid/content/pm/ResolveInfo;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Landroid/content/Intent;)V
      //   439: invokeinterface add : (Ljava/lang/Object;)Z
      //   444: pop
      //   445: iinc #10, 1
      //   448: goto -> 270
      //   451: return
    }
    
    private void rebuildList() {
      this.mList.clear();
      List<ResolveInfo> list = this.mBaseResolveList;
      if (list != null) {
        this.mOrigResolveList = null;
      } else {
        boolean bool;
        PackageManager packageManager = ResolverActivity.this.mPm;
        Intent intent = this.mIntent;
        if (ResolverActivity.this.mAlwaysUseOption) {
          bool = true;
        } else {
          bool = false;
        } 
        list = packageManager.queryIntentActivities(intent, 0x10000 | bool);
        this.mOrigResolveList = list;
      } 
      if (list != null) {
        int i = list.size();
        if (i > 0) {
          Object object;
          ResolveInfo resolveInfo1 = list.get(0);
          byte b1 = 1;
          while (b1 < object) {
            ResolveInfo resolveInfo = list.get(b1);
            Object object1 = object;
            if (resolveInfo1.priority == resolveInfo.priority) {
              Object object2 = object;
              if (resolveInfo1.isDefault != resolveInfo.isDefault) {
                object1 = object;
              } else {
                continue;
              } 
            } 
            while (true) {
              Object object2 = object1;
              if (b1 < object1) {
                if (this.mOrigResolveList == list)
                  this.mOrigResolveList = new ArrayList<ResolveInfo>(this.mOrigResolveList); 
                list.remove(b1);
                object1--;
                continue;
              } 
              break;
            } 
            continue;
            b1++;
            object = SYNTHETIC_LOCAL_VARIABLE_7;
          } 
          if (object > true)
            Collections.sort(list, (Comparator<? super ResolveInfo>)new ResolveInfo.DisplayNameComparator(ResolverActivity.this.mPm)); 
          if (this.mInitialIntents != null) {
            byte b = 0;
            while (true) {
              Intent[] arrayOfIntent = this.mInitialIntents;
              if (b < arrayOfIntent.length) {
                Intent intent = arrayOfIntent[b];
                if (intent != null) {
                  ActivityInfo activityInfo = intent.resolveActivityInfo(ResolverActivity.this.getPackageManager(), 0);
                  if (activityInfo == null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("No activity found for ");
                    stringBuilder.append(intent);
                    VLog.w("ResolverActivity", stringBuilder.toString(), new Object[0]);
                  } else {
                    ResolveInfo resolveInfo = new ResolveInfo();
                    resolveInfo.activityInfo = activityInfo;
                    if (intent instanceof LabeledIntent) {
                      LabeledIntent labeledIntent = (LabeledIntent)intent;
                      resolveInfo.resolvePackageName = labeledIntent.getSourcePackage();
                      resolveInfo.labelRes = labeledIntent.getLabelResource();
                      resolveInfo.nonLocalizedLabel = labeledIntent.getNonLocalizedLabel();
                      resolveInfo.icon = labeledIntent.getIconResource();
                    } 
                    List<ResolverActivity.DisplayResolveInfo> list1 = this.mList;
                    ResolverActivity resolverActivity = ResolverActivity.this;
                    list1.add(new ResolverActivity.DisplayResolveInfo(resolveInfo, resolveInfo.loadLabel(resolverActivity.getPackageManager()), null, intent));
                  } 
                } 
                b++;
                continue;
              } 
              break;
            } 
          } 
          ResolveInfo resolveInfo2 = list.get(0);
          CharSequence charSequence = resolveInfo2.loadLabel(ResolverActivity.this.mPm);
          ResolverActivity.access$202(ResolverActivity.this, false);
          b1 = 0;
          byte b2 = 1;
          while (b2 < object) {
            CharSequence charSequence1 = charSequence;
            if (charSequence == null)
              charSequence1 = resolveInfo2.activityInfo.packageName; 
            ResolveInfo resolveInfo = list.get(b2);
            CharSequence charSequence2 = resolveInfo.loadLabel(ResolverActivity.this.mPm);
            charSequence = charSequence2;
            if (charSequence2 == null)
              charSequence = resolveInfo.activityInfo.packageName; 
            if (!charSequence.equals(charSequence1)) {
              processGroup(list, b1, b2 - 1, resolveInfo2, charSequence1);
              b1 = b2;
              resolveInfo2 = resolveInfo;
              charSequence1 = charSequence;
            } 
            b2++;
            charSequence = charSequence1;
          } 
          processGroup(list, b1, object - 1, resolveInfo2, charSequence);
        } 
      } 
    }
    
    public int getCount() {
      return this.mList.size();
    }
    
    public int getInitialHighlight() {
      return this.mInitialHighlight;
    }
    
    public Object getItem(int param1Int) {
      return this.mList.get(param1Int);
    }
    
    public long getItemId(int param1Int) {
      return param1Int;
    }
    
    public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
      View view = param1View;
      if (param1View == null) {
        view = this.mInflater.inflate(R.layout.resolve_list_item, param1ViewGroup, false);
        ResolverActivity.ViewHolder viewHolder = new ResolverActivity.ViewHolder(view);
        view.setTag(viewHolder);
        ViewGroup.LayoutParams layoutParams = viewHolder.icon.getLayoutParams();
        int i = ResolverActivity.this.mIconSize;
        layoutParams.height = i;
        layoutParams.width = i;
      } 
      bindView(view, this.mList.get(param1Int));
      return view;
    }
    
    public void handlePackagesChanged() {
      getCount();
      rebuildList();
      notifyDataSetChanged();
      if (getCount() == 0)
        ResolverActivity.this.finish(); 
    }
    
    public Intent intentForPosition(int param1Int) {
      ResolverActivity.DisplayResolveInfo displayResolveInfo = this.mList.get(param1Int);
      if (displayResolveInfo.origIntent != null) {
        intent = displayResolveInfo.origIntent;
      } else {
        intent = this.mIntent;
      } 
      Intent intent = new Intent(intent);
      intent.addFlags(50331648);
      ActivityInfo activityInfo = displayResolveInfo.ri.activityInfo;
      intent.setComponent(new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name));
      return intent;
    }
    
    public ResolveInfo resolveInfoForPosition(int param1Int) {
      return ((ResolverActivity.DisplayResolveInfo)this.mList.get(param1Int)).ri;
    }
  }
  
  static class ViewHolder {
    public ImageView icon;
    
    public TextView text;
    
    public TextView text2;
    
    public ViewHolder(View param1View) {
      this.text = (TextView)param1View.findViewById(R.id.text1);
      this.text2 = (TextView)param1View.findViewById(R.id.text2);
      this.icon = (ImageView)param1View.findViewById(R.id.icon);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\ResolverActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */