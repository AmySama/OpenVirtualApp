package android.support.v7.widget;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v7.appcompat.R;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

class SuggestionsAdapter extends ResourceCursorAdapter implements View.OnClickListener {
  private static final boolean DBG = false;
  
  static final int INVALID_INDEX = -1;
  
  private static final String LOG_TAG = "SuggestionsAdapter";
  
  private static final int QUERY_LIMIT = 50;
  
  static final int REFINE_ALL = 2;
  
  static final int REFINE_BY_ENTRY = 1;
  
  static final int REFINE_NONE = 0;
  
  private boolean mClosed = false;
  
  private final int mCommitIconResId;
  
  private int mFlagsCol = -1;
  
  private int mIconName1Col = -1;
  
  private int mIconName2Col = -1;
  
  private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache;
  
  private final Context mProviderContext;
  
  private int mQueryRefinement = 1;
  
  private final SearchManager mSearchManager = (SearchManager)this.mContext.getSystemService("search");
  
  private final SearchView mSearchView;
  
  private final SearchableInfo mSearchable;
  
  private int mText1Col = -1;
  
  private int mText2Col = -1;
  
  private int mText2UrlCol = -1;
  
  private ColorStateList mUrlColor;
  
  public SuggestionsAdapter(Context paramContext, SearchView paramSearchView, SearchableInfo paramSearchableInfo, WeakHashMap<String, Drawable.ConstantState> paramWeakHashMap) {
    super(paramContext, paramSearchView.getSuggestionRowLayout(), null, true);
    this.mSearchView = paramSearchView;
    this.mSearchable = paramSearchableInfo;
    this.mCommitIconResId = paramSearchView.getSuggestionCommitIconResId();
    this.mProviderContext = paramContext;
    this.mOutsideDrawablesCache = paramWeakHashMap;
  }
  
  private Drawable checkIconCache(String paramString) {
    Drawable.ConstantState constantState = this.mOutsideDrawablesCache.get(paramString);
    return (constantState == null) ? null : constantState.newDrawable();
  }
  
  private CharSequence formatUrl(CharSequence paramCharSequence) {
    if (this.mUrlColor == null) {
      TypedValue typedValue = new TypedValue();
      this.mContext.getTheme().resolveAttribute(R.attr.textColorSearchUrl, typedValue, true);
      this.mUrlColor = this.mContext.getResources().getColorStateList(typedValue.resourceId);
    } 
    SpannableString spannableString = new SpannableString(paramCharSequence);
    spannableString.setSpan(new TextAppearanceSpan(null, 0, 0, this.mUrlColor, null), 0, paramCharSequence.length(), 33);
    return (CharSequence)spannableString;
  }
  
  private Drawable getActivityIcon(ComponentName paramComponentName) {
    PackageManager packageManager = this.mContext.getPackageManager();
    try {
      StringBuilder stringBuilder;
      ActivityInfo activityInfo = packageManager.getActivityInfo(paramComponentName, 128);
      int i = activityInfo.getIconResource();
      if (i == 0)
        return null; 
      Drawable drawable = packageManager.getDrawable(paramComponentName.getPackageName(), i, activityInfo.applicationInfo);
      if (drawable == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid icon resource ");
        stringBuilder.append(i);
        stringBuilder.append(" for ");
        stringBuilder.append(paramComponentName.flattenToShortString());
        Log.w("SuggestionsAdapter", stringBuilder.toString());
        return null;
      } 
      return (Drawable)stringBuilder;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      Log.w("SuggestionsAdapter", nameNotFoundException.toString());
      return null;
    } 
  }
  
  private Drawable getActivityIconWithCache(ComponentName paramComponentName) {
    Drawable drawable1;
    Drawable.ConstantState constantState1;
    String str = paramComponentName.flattenToShortString();
    boolean bool = this.mOutsideDrawablesCache.containsKey(str);
    Drawable drawable2 = null;
    Drawable.ConstantState constantState2 = null;
    if (bool) {
      constantState1 = this.mOutsideDrawablesCache.get(str);
      if (constantState1 == null) {
        constantState1 = constantState2;
      } else {
        drawable1 = constantState1.newDrawable(this.mProviderContext.getResources());
      } 
      return drawable1;
    } 
    Drawable drawable3 = getActivityIcon((ComponentName)drawable1);
    if (drawable3 == null) {
      drawable1 = drawable2;
    } else {
      constantState1 = drawable3.getConstantState();
    } 
    this.mOutsideDrawablesCache.put(str, constantState1);
    return drawable3;
  }
  
  public static String getColumnString(Cursor paramCursor, String paramString) {
    return getStringOrNull(paramCursor, paramCursor.getColumnIndex(paramString));
  }
  
  private Drawable getDefaultIcon1(Cursor paramCursor) {
    Drawable drawable = getActivityIconWithCache(this.mSearchable.getSearchActivity());
    return (drawable != null) ? drawable : this.mContext.getPackageManager().getDefaultActivityIcon();
  }
  
  private Drawable getDrawable(Uri paramUri) {
    try {
      boolean bool = "android.resource".equals(paramUri.getScheme());
      if (bool)
        try {
          return getDrawableFromResourceUri(paramUri);
        } catch (android.content.res.Resources.NotFoundException notFoundException) {
          FileNotFoundException fileNotFoundException1 = new FileNotFoundException();
          StringBuilder stringBuilder1 = new StringBuilder();
          this();
          stringBuilder1.append("Resource does not exist: ");
          stringBuilder1.append(paramUri);
          this(stringBuilder1.toString());
          throw fileNotFoundException1;
        }  
      InputStream inputStream = this.mProviderContext.getContentResolver().openInputStream(paramUri);
      if (inputStream != null)
        try {
          return Drawable.createFromStream(inputStream, null);
        } finally {
          try {
            iOException.close();
          } catch (IOException iOException1) {
            StringBuilder stringBuilder1 = new StringBuilder();
            this();
            stringBuilder1.append("Error closing icon stream for ");
            stringBuilder1.append(paramUri);
            Log.e("SuggestionsAdapter", stringBuilder1.toString(), iOException1);
          } 
        }  
      FileNotFoundException fileNotFoundException = new FileNotFoundException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("Failed to open ");
      stringBuilder.append(paramUri);
      this(stringBuilder.toString());
      throw fileNotFoundException;
    } catch (FileNotFoundException fileNotFoundException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Icon not found: ");
      stringBuilder.append(paramUri);
      stringBuilder.append(", ");
      stringBuilder.append(fileNotFoundException.getMessage());
      Log.w("SuggestionsAdapter", stringBuilder.toString());
      return null;
    } 
  }
  
  private Drawable getDrawableFromResourceValue(String paramString) {
    StringBuilder stringBuilder1 = null;
    StringBuilder stringBuilder2 = stringBuilder1;
    if (paramString != null) {
      stringBuilder2 = stringBuilder1;
      if (!paramString.isEmpty())
        if ("0".equals(paramString)) {
          stringBuilder2 = stringBuilder1;
        } else {
          try {
            int i = Integer.parseInt(paramString);
            stringBuilder2 = new StringBuilder();
            this();
            stringBuilder2.append("android.resource://");
            stringBuilder2.append(this.mProviderContext.getPackageName());
            stringBuilder2.append("/");
            stringBuilder2.append(i);
            String str = stringBuilder2.toString();
            Drawable drawable = checkIconCache(str);
            if (drawable != null)
              return drawable; 
            drawable = ContextCompat.getDrawable(this.mProviderContext, i);
            storeInIconCache(str, drawable);
            return drawable;
          } catch (NumberFormatException numberFormatException) {
            Drawable drawable = checkIconCache(paramString);
            if (drawable != null)
              return drawable; 
            drawable = getDrawable(Uri.parse(paramString));
            storeInIconCache(paramString, drawable);
          } catch (android.content.res.Resources.NotFoundException notFoundException) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Icon resource not found: ");
            stringBuilder2.append(paramString);
            Log.w("SuggestionsAdapter", stringBuilder2.toString());
            return null;
          } 
        }  
    } 
    return (Drawable)stringBuilder2;
  }
  
  private Drawable getIcon1(Cursor paramCursor) {
    int i = this.mIconName1Col;
    if (i == -1)
      return null; 
    Drawable drawable = getDrawableFromResourceValue(paramCursor.getString(i));
    return (drawable != null) ? drawable : getDefaultIcon1(paramCursor);
  }
  
  private Drawable getIcon2(Cursor paramCursor) {
    int i = this.mIconName2Col;
    return (i == -1) ? null : getDrawableFromResourceValue(paramCursor.getString(i));
  }
  
  private static String getStringOrNull(Cursor paramCursor, int paramInt) {
    if (paramInt == -1)
      return null; 
    try {
      return paramCursor.getString(paramInt);
    } catch (Exception exception) {
      Log.e("SuggestionsAdapter", "unexpected error retrieving valid column from cursor, did the remote process die?", exception);
      return null;
    } 
  }
  
  private void setViewDrawable(ImageView paramImageView, Drawable paramDrawable, int paramInt) {
    paramImageView.setImageDrawable(paramDrawable);
    if (paramDrawable == null) {
      paramImageView.setVisibility(paramInt);
    } else {
      paramImageView.setVisibility(0);
      paramDrawable.setVisible(false, false);
      paramDrawable.setVisible(true, false);
    } 
  }
  
  private void setViewText(TextView paramTextView, CharSequence paramCharSequence) {
    paramTextView.setText(paramCharSequence);
    if (TextUtils.isEmpty(paramCharSequence)) {
      paramTextView.setVisibility(8);
    } else {
      paramTextView.setVisibility(0);
    } 
  }
  
  private void storeInIconCache(String paramString, Drawable paramDrawable) {
    if (paramDrawable != null)
      this.mOutsideDrawablesCache.put(paramString, paramDrawable.getConstantState()); 
  }
  
  private void updateSpinnerState(Cursor paramCursor) {
    if (paramCursor != null) {
      Bundle bundle = paramCursor.getExtras();
    } else {
      paramCursor = null;
    } 
    if (paramCursor == null || paramCursor.getBoolean("in_progress"));
  }
  
  public void bindView(View paramView, Context paramContext, Cursor paramCursor) {
    ChildViewCache childViewCache = (ChildViewCache)paramView.getTag();
    int i = this.mFlagsCol;
    if (i != -1) {
      i = paramCursor.getInt(i);
    } else {
      i = 0;
    } 
    if (childViewCache.mText1 != null) {
      String str = getStringOrNull(paramCursor, this.mText1Col);
      setViewText(childViewCache.mText1, str);
    } 
    if (childViewCache.mText2 != null) {
      String str = getStringOrNull(paramCursor, this.mText2UrlCol);
      if (str != null) {
        CharSequence charSequence = formatUrl(str);
      } else {
        str = getStringOrNull(paramCursor, this.mText2Col);
      } 
      if (TextUtils.isEmpty(str)) {
        if (childViewCache.mText1 != null) {
          childViewCache.mText1.setSingleLine(false);
          childViewCache.mText1.setMaxLines(2);
        } 
      } else if (childViewCache.mText1 != null) {
        childViewCache.mText1.setSingleLine(true);
        childViewCache.mText1.setMaxLines(1);
      } 
      setViewText(childViewCache.mText2, str);
    } 
    if (childViewCache.mIcon1 != null)
      setViewDrawable(childViewCache.mIcon1, getIcon1(paramCursor), 4); 
    if (childViewCache.mIcon2 != null)
      setViewDrawable(childViewCache.mIcon2, getIcon2(paramCursor), 8); 
    int j = this.mQueryRefinement;
    if (j == 2 || (j == 1 && (i & 0x1) != 0)) {
      childViewCache.mIconRefine.setVisibility(0);
      childViewCache.mIconRefine.setTag(childViewCache.mText1.getText());
      childViewCache.mIconRefine.setOnClickListener(this);
      return;
    } 
    childViewCache.mIconRefine.setVisibility(8);
  }
  
  public void changeCursor(Cursor paramCursor) {
    if (this.mClosed) {
      Log.w("SuggestionsAdapter", "Tried to change cursor after adapter was closed.");
      if (paramCursor != null)
        paramCursor.close(); 
      return;
    } 
    try {
      super.changeCursor(paramCursor);
      if (paramCursor != null) {
        this.mText1Col = paramCursor.getColumnIndex("suggest_text_1");
        this.mText2Col = paramCursor.getColumnIndex("suggest_text_2");
        this.mText2UrlCol = paramCursor.getColumnIndex("suggest_text_2_url");
        this.mIconName1Col = paramCursor.getColumnIndex("suggest_icon_1");
        this.mIconName2Col = paramCursor.getColumnIndex("suggest_icon_2");
        this.mFlagsCol = paramCursor.getColumnIndex("suggest_flags");
      } 
    } catch (Exception exception) {
      Log.e("SuggestionsAdapter", "error changing cursor and caching columns", exception);
    } 
  }
  
  public void close() {
    changeCursor((Cursor)null);
    this.mClosed = true;
  }
  
  public CharSequence convertToString(Cursor paramCursor) {
    if (paramCursor == null)
      return null; 
    String str = getColumnString(paramCursor, "suggest_intent_query");
    if (str != null)
      return str; 
    if (this.mSearchable.shouldRewriteQueryFromData()) {
      str = getColumnString(paramCursor, "suggest_intent_data");
      if (str != null)
        return str; 
    } 
    if (this.mSearchable.shouldRewriteQueryFromText()) {
      String str1 = getColumnString(paramCursor, "suggest_text_1");
      if (str1 != null)
        return str1; 
    } 
    return null;
  }
  
  Drawable getDrawableFromResourceUri(Uri paramUri) throws FileNotFoundException {
    String str = paramUri.getAuthority();
    if (!TextUtils.isEmpty(str))
      try {
        Resources resources = this.mContext.getPackageManager().getResourcesForApplication(str);
        List<String> list = paramUri.getPathSegments();
        if (list != null) {
          int i = list.size();
          if (i == 1) {
            try {
              i = Integer.parseInt(list.get(0));
            } catch (NumberFormatException numberFormatException) {
              stringBuilder2 = new StringBuilder();
              stringBuilder2.append("Single path segment is not a resource ID: ");
              stringBuilder2.append(paramUri);
              throw new FileNotFoundException(stringBuilder2.toString());
            } 
          } else if (i == 2) {
            i = resources.getIdentifier(stringBuilder2.get(1), stringBuilder2.get(0), str);
          } else {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("More than two path segments: ");
            stringBuilder2.append(paramUri);
            throw new FileNotFoundException(stringBuilder2.toString());
          } 
          if (i != 0)
            return resources.getDrawable(i); 
          StringBuilder stringBuilder2 = new StringBuilder();
          stringBuilder2.append("No resource found for: ");
          stringBuilder2.append(paramUri);
          throw new FileNotFoundException(stringBuilder2.toString());
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("No path: ");
        stringBuilder1.append(paramUri);
        throw new FileNotFoundException(stringBuilder1.toString());
      } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("No package found for authority: ");
        stringBuilder1.append(paramUri);
        throw new FileNotFoundException(stringBuilder1.toString());
      }  
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No authority: ");
    stringBuilder.append(paramUri);
    throw new FileNotFoundException(stringBuilder.toString());
  }
  
  public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    try {
      return super.getDropDownView(paramInt, paramView, paramViewGroup);
    } catch (RuntimeException runtimeException) {
      Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", runtimeException);
      View view = newDropDownView(this.mContext, this.mCursor, paramViewGroup);
      if (view != null)
        ((ChildViewCache)view.getTag()).mText1.setText(runtimeException.toString()); 
      return view;
    } 
  }
  
  public int getQueryRefinement() {
    return this.mQueryRefinement;
  }
  
  Cursor getSearchManagerSuggestions(SearchableInfo paramSearchableInfo, String paramString, int paramInt) {
    SearchableInfo searchableInfo = null;
    if (paramSearchableInfo == null)
      return null; 
    String str1 = paramSearchableInfo.getSuggestAuthority();
    if (str1 == null)
      return null; 
    Uri.Builder builder = (new Uri.Builder()).scheme("content").authority(str1).query("").fragment("");
    String str2 = paramSearchableInfo.getSuggestPath();
    if (str2 != null)
      builder.appendEncodedPath(str2); 
    builder.appendPath("search_suggest_query");
    str2 = paramSearchableInfo.getSuggestSelection();
    if (str2 != null) {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramString;
    } else {
      builder.appendPath(paramString);
      paramSearchableInfo = searchableInfo;
    } 
    if (paramInt > 0)
      builder.appendQueryParameter("limit", String.valueOf(paramInt)); 
    Uri uri = builder.build();
    return this.mContext.getContentResolver().query(uri, null, str2, (String[])paramSearchableInfo, null);
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    try {
      return super.getView(paramInt, paramView, paramViewGroup);
    } catch (RuntimeException runtimeException) {
      Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", runtimeException);
      View view = newView(this.mContext, this.mCursor, paramViewGroup);
      if (view != null)
        ((ChildViewCache)view.getTag()).mText1.setText(runtimeException.toString()); 
      return view;
    } 
  }
  
  public boolean hasStableIds() {
    return false;
  }
  
  public View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup) {
    View view = super.newView(paramContext, paramCursor, paramViewGroup);
    view.setTag(new ChildViewCache(view));
    ((ImageView)view.findViewById(R.id.edit_query)).setImageResource(this.mCommitIconResId);
    return view;
  }
  
  public void notifyDataSetChanged() {
    super.notifyDataSetChanged();
    updateSpinnerState(getCursor());
  }
  
  public void notifyDataSetInvalidated() {
    super.notifyDataSetInvalidated();
    updateSpinnerState(getCursor());
  }
  
  public void onClick(View paramView) {
    Object object = paramView.getTag();
    if (object instanceof CharSequence)
      this.mSearchView.onQueryRefine((CharSequence)object); 
  }
  
  public Cursor runQueryOnBackgroundThread(CharSequence paramCharSequence) {
    if (paramCharSequence == null) {
      paramCharSequence = "";
    } else {
      paramCharSequence = paramCharSequence.toString();
    } 
    if (this.mSearchView.getVisibility() == 0 && this.mSearchView.getWindowVisibility() == 0)
      try {
        Cursor cursor = getSearchManagerSuggestions(this.mSearchable, (String)paramCharSequence, 50);
        if (cursor != null) {
          cursor.getCount();
          return cursor;
        } 
      } catch (RuntimeException runtimeException) {
        Log.w("SuggestionsAdapter", "Search suggestions query threw an exception.", runtimeException);
      }  
    return null;
  }
  
  public void setQueryRefinement(int paramInt) {
    this.mQueryRefinement = paramInt;
  }
  
  private static final class ChildViewCache {
    public final ImageView mIcon1;
    
    public final ImageView mIcon2;
    
    public final ImageView mIconRefine;
    
    public final TextView mText1;
    
    public final TextView mText2;
    
    public ChildViewCache(View param1View) {
      this.mText1 = (TextView)param1View.findViewById(16908308);
      this.mText2 = (TextView)param1View.findViewById(16908309);
      this.mIcon1 = (ImageView)param1View.findViewById(16908295);
      this.mIcon2 = (ImageView)param1View.findViewById(16908296);
      this.mIconRefine = (ImageView)param1View.findViewById(R.id.edit_query);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\SuggestionsAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */