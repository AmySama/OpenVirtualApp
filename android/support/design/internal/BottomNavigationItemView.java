package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.design.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.TooltipCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class BottomNavigationItemView extends FrameLayout implements MenuView.ItemView {
  private static final int[] CHECKED_STATE_SET = new int[] { 16842912 };
  
  public static final int INVALID_ITEM_POSITION = -1;
  
  private final int mDefaultMargin;
  
  private ImageView mIcon;
  
  private ColorStateList mIconTint;
  
  private MenuItemImpl mItemData;
  
  private int mItemPosition = -1;
  
  private final TextView mLargeLabel;
  
  private final float mScaleDownFactor;
  
  private final float mScaleUpFactor;
  
  private final int mShiftAmount;
  
  private boolean mShiftingMode;
  
  private final TextView mSmallLabel;
  
  public BottomNavigationItemView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public BottomNavigationItemView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public BottomNavigationItemView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    Resources resources = getResources();
    paramInt = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_text_size);
    int i = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_text_size);
    this.mDefaultMargin = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_margin);
    this.mShiftAmount = paramInt - i;
    float f1 = i;
    float f2 = paramInt;
    this.mScaleUpFactor = f1 * 1.0F / f2;
    this.mScaleDownFactor = f2 * 1.0F / f1;
    LayoutInflater.from(paramContext).inflate(R.layout.design_bottom_navigation_item, (ViewGroup)this, true);
    setBackgroundResource(R.drawable.design_bottom_navigation_item_background);
    this.mIcon = (ImageView)findViewById(R.id.icon);
    this.mSmallLabel = (TextView)findViewById(R.id.smallLabel);
    this.mLargeLabel = (TextView)findViewById(R.id.largeLabel);
  }
  
  public MenuItemImpl getItemData() {
    return this.mItemData;
  }
  
  public int getItemPosition() {
    return this.mItemPosition;
  }
  
  public void initialize(MenuItemImpl paramMenuItemImpl, int paramInt) {
    this.mItemData = paramMenuItemImpl;
    setCheckable(paramMenuItemImpl.isCheckable());
    setChecked(paramMenuItemImpl.isChecked());
    setEnabled(paramMenuItemImpl.isEnabled());
    setIcon(paramMenuItemImpl.getIcon());
    setTitle(paramMenuItemImpl.getTitle());
    setId(paramMenuItemImpl.getItemId());
    setContentDescription(paramMenuItemImpl.getContentDescription());
    TooltipCompat.setTooltipText((View)this, paramMenuItemImpl.getTooltipText());
  }
  
  public int[] onCreateDrawableState(int paramInt) {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 1);
    MenuItemImpl menuItemImpl = this.mItemData;
    if (menuItemImpl != null && menuItemImpl.isCheckable() && this.mItemData.isChecked())
      mergeDrawableStates(arrayOfInt, CHECKED_STATE_SET); 
    return arrayOfInt;
  }
  
  public boolean prefersCondensedTitle() {
    return false;
  }
  
  public void setCheckable(boolean paramBoolean) {
    refreshDrawableState();
  }
  
  public void setChecked(boolean paramBoolean) {
    TextView textView = this.mLargeLabel;
    textView.setPivotX((textView.getWidth() / 2));
    textView = this.mLargeLabel;
    textView.setPivotY(textView.getBaseline());
    textView = this.mSmallLabel;
    textView.setPivotX((textView.getWidth() / 2));
    textView = this.mSmallLabel;
    textView.setPivotY(textView.getBaseline());
    if (this.mShiftingMode) {
      if (paramBoolean) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.mIcon.getLayoutParams();
        layoutParams.gravity = 49;
        layoutParams.topMargin = this.mDefaultMargin;
        this.mIcon.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.mLargeLabel.setVisibility(0);
        this.mLargeLabel.setScaleX(1.0F);
        this.mLargeLabel.setScaleY(1.0F);
      } else {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.mIcon.getLayoutParams();
        layoutParams.gravity = 17;
        layoutParams.topMargin = this.mDefaultMargin;
        this.mIcon.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.mLargeLabel.setVisibility(4);
        this.mLargeLabel.setScaleX(0.5F);
        this.mLargeLabel.setScaleY(0.5F);
      } 
      this.mSmallLabel.setVisibility(4);
    } else if (paramBoolean) {
      FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.mIcon.getLayoutParams();
      layoutParams.gravity = 49;
      layoutParams.topMargin = this.mDefaultMargin + this.mShiftAmount;
      this.mIcon.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      this.mLargeLabel.setVisibility(0);
      this.mSmallLabel.setVisibility(4);
      this.mLargeLabel.setScaleX(1.0F);
      this.mLargeLabel.setScaleY(1.0F);
      this.mSmallLabel.setScaleX(this.mScaleUpFactor);
      this.mSmallLabel.setScaleY(this.mScaleUpFactor);
    } else {
      FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.mIcon.getLayoutParams();
      layoutParams.gravity = 49;
      layoutParams.topMargin = this.mDefaultMargin;
      this.mIcon.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      this.mLargeLabel.setVisibility(4);
      this.mSmallLabel.setVisibility(0);
      this.mLargeLabel.setScaleX(this.mScaleDownFactor);
      this.mLargeLabel.setScaleY(this.mScaleDownFactor);
      this.mSmallLabel.setScaleX(1.0F);
      this.mSmallLabel.setScaleY(1.0F);
    } 
    refreshDrawableState();
  }
  
  public void setEnabled(boolean paramBoolean) {
    super.setEnabled(paramBoolean);
    this.mSmallLabel.setEnabled(paramBoolean);
    this.mLargeLabel.setEnabled(paramBoolean);
    this.mIcon.setEnabled(paramBoolean);
    if (paramBoolean) {
      ViewCompat.setPointerIcon((View)this, PointerIconCompat.getSystemIcon(getContext(), 1002));
    } else {
      ViewCompat.setPointerIcon((View)this, null);
    } 
  }
  
  public void setIcon(Drawable paramDrawable) {
    Drawable drawable = paramDrawable;
    if (paramDrawable != null) {
      Drawable.ConstantState constantState = paramDrawable.getConstantState();
      if (constantState != null)
        paramDrawable = constantState.newDrawable(); 
      drawable = DrawableCompat.wrap(paramDrawable).mutate();
      DrawableCompat.setTintList(drawable, this.mIconTint);
    } 
    this.mIcon.setImageDrawable(drawable);
  }
  
  public void setIconTintList(ColorStateList paramColorStateList) {
    this.mIconTint = paramColorStateList;
    MenuItemImpl menuItemImpl = this.mItemData;
    if (menuItemImpl != null)
      setIcon(menuItemImpl.getIcon()); 
  }
  
  public void setItemBackground(int paramInt) {
    Drawable drawable;
    if (paramInt == 0) {
      drawable = null;
    } else {
      drawable = ContextCompat.getDrawable(getContext(), paramInt);
    } 
    ViewCompat.setBackground((View)this, drawable);
  }
  
  public void setItemPosition(int paramInt) {
    this.mItemPosition = paramInt;
  }
  
  public void setShiftingMode(boolean paramBoolean) {
    this.mShiftingMode = paramBoolean;
  }
  
  public void setShortcut(boolean paramBoolean, char paramChar) {}
  
  public void setTextColor(ColorStateList paramColorStateList) {
    this.mSmallLabel.setTextColor(paramColorStateList);
    this.mLargeLabel.setTextColor(paramColorStateList);
  }
  
  public void setTitle(CharSequence paramCharSequence) {
    this.mSmallLabel.setText(paramCharSequence);
    this.mLargeLabel.setText(paramCharSequence);
  }
  
  public boolean showsIcon() {
    return true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\internal\BottomNavigationItemView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */