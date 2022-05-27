package io.virtualapp.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ImageCycleView extends FrameLayout {
  private List<ImageInfo> data = new ArrayList<ImageInfo>();
  
  private Bitmap focusIndicationStyle;
  
  private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message param1Message) {
          if (ImageCycleView.this.mViewPager != null) {
            ImageCycleView.this.mViewPager.setCurrentItem(ImageCycleView.this.mViewPager.getCurrentItem() + 1);
            ImageCycleView.this.handler.sendEmptyMessageDelayed(0, ImageCycleView.this.mCycleDelayed);
          } 
          return false;
        }
      });
  
  private float indication_self_margin_percent = 1.0F;
  
  private boolean isAutoCycle = true;
  
  private Context mContext;
  
  private int mCount = 0;
  
  private long mCycleDelayed = 5000L;
  
  private LinearLayout mIndicationGroup;
  
  private LoadImageCallBack mLoadImageCallBack;
  
  private OnPageClickListener mOnPageClickListener;
  
  private TextView mText;
  
  private ImageCycleViewPager mViewPager;
  
  private Bitmap unFocusIndicationStyle;
  
  public ImageCycleView(Context paramContext) {
    super(paramContext);
    init(paramContext);
  }
  
  public ImageCycleView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init(paramContext);
  }
  
  private Bitmap drawCircle(int paramInt1, int paramInt2) {
    Paint paint = new Paint(1);
    paint.setColor(paramInt2);
    Bitmap bitmap = Bitmap.createBitmap(paramInt1, paramInt1, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    float f = (paramInt1 / 2);
    canvas.drawCircle(f, f, f, paint);
    return bitmap;
  }
  
  private void init(Context paramContext) {
    this.mContext = paramContext;
    this.unFocusIndicationStyle = drawCircle(50, -7829368);
    this.focusIndicationStyle = drawCircle(50, -1);
    initView();
  }
  
  private void initIndication() {
    this.mIndicationGroup.removeAllViews();
    for (byte b = 0; b < this.mCount; b++) {
      ImageView imageView = new ImageView(this.mContext);
      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((this.mIndicationGroup.getLayoutParams()).height, -1);
      layoutParams.leftMargin = (int)((this.mIndicationGroup.getLayoutParams()).height * this.indication_self_margin_percent);
      imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
      imageView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      if (b == 0) {
        imageView.setImageBitmap(this.focusIndicationStyle);
      } else {
        imageView.setImageBitmap(this.unFocusIndicationStyle);
      } 
      this.mIndicationGroup.addView((View)imageView);
    } 
  }
  
  private void initView() {
    View.inflate(this.mContext, 2131427484, (ViewGroup)this);
    FrameLayout frameLayout = (FrameLayout)findViewById(2131296479);
    ImageCycleViewPager imageCycleViewPager = new ImageCycleViewPager(this.mContext);
    this.mViewPager = imageCycleViewPager;
    imageCycleViewPager.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    frameLayout.addView((View)this.mViewPager);
    this.mViewPager.setOnPageChangeListener(new ImageCyclePageChangeListener());
    this.mIndicationGroup = (LinearLayout)findViewById(2131296577);
    this.mText = (TextView)findViewById(2131296754);
  }
  
  private void startImageCycle() {
    this.handler.sendEmptyMessageDelayed(0, this.mCycleDelayed);
  }
  
  private void stopImageCycle() {
    this.handler.removeCallbacksAndMessages(null);
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    if (paramMotionEvent.getAction() == 1) {
      if (this.isAutoCycle)
        startImageCycle(); 
    } else if (this.isAutoCycle) {
      stopImageCycle();
    } 
    return super.dispatchTouchEvent(paramMotionEvent);
  }
  
  public void loadData(List<ImageInfo> paramList, LoadImageCallBack paramLoadImageCallBack) {
    this.data = paramList;
    this.mCount = paramList.size();
    initIndication();
    if (paramLoadImageCallBack == null)
      new IllegalArgumentException("LoadImageCallBack 回调函数不能为空！"); 
    this.mLoadImageCallBack = paramLoadImageCallBack;
    this.mViewPager.setAdapter(new ImageCycleAdapter());
    this.mViewPager.setCurrentItem(1073741823 - 1073741823 % this.mCount);
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (this.isAutoCycle)
      startImageCycle(); 
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    stopImageCycle();
  }
  
  public void setAutoCycle(Boolean paramBoolean) {
    this.isAutoCycle = paramBoolean.booleanValue();
  }
  
  public void setCycleDelayed(long paramLong) {
    this.mCycleDelayed = paramLong;
  }
  
  public void setIndicationStyle(IndicationStyle paramIndicationStyle, int paramInt1, int paramInt2, float paramFloat) {
    if (paramIndicationStyle == IndicationStyle.COLOR) {
      this.unFocusIndicationStyle = drawCircle(50, paramInt1);
      this.focusIndicationStyle = drawCircle(50, paramInt2);
    } else if (paramIndicationStyle == IndicationStyle.IMAGE) {
      this.unFocusIndicationStyle = BitmapFactory.decodeResource(this.mContext.getResources(), paramInt1);
      this.focusIndicationStyle = BitmapFactory.decodeResource(this.mContext.getResources(), paramInt2);
    } 
    this.indication_self_margin_percent = paramFloat;
    initIndication();
  }
  
  public void setOnPageClickListener(OnPageClickListener paramOnPageClickListener) {
    this.mOnPageClickListener = paramOnPageClickListener;
  }
  
  private class ImageCycleAdapter extends PagerAdapter {
    private ImageCycleAdapter() {}
    
    public void destroyItem(ViewGroup param1ViewGroup, int param1Int, Object param1Object) {
      param1ViewGroup.removeView((View)param1Object);
    }
    
    public int getCount() {
      return Integer.MAX_VALUE;
    }
    
    public Object instantiateItem(ViewGroup param1ViewGroup, int param1Int) {
      final ImageCycleView.ImageInfo imageInfo = ImageCycleView.this.data.get(param1Int % ImageCycleView.this.mCount);
      ImageView imageView = ImageCycleView.this.mLoadImageCallBack.loadAndDisplay(imageInfo);
      imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
      imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
      imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param2View) {
              if (ImageCycleView.this.mOnPageClickListener != null)
                ImageCycleView.this.mOnPageClickListener.onClick(param2View, imageInfo); 
            }
          });
      param1ViewGroup.addView((View)imageView);
      return imageView;
    }
    
    public boolean isViewFromObject(View param1View, Object param1Object) {
      boolean bool;
      if (param1View == param1Object) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
  }
  
  class null implements View.OnClickListener {
    public void onClick(View param1View) {
      if (ImageCycleView.this.mOnPageClickListener != null)
        ImageCycleView.this.mOnPageClickListener.onClick(param1View, imageInfo); 
    }
  }
  
  private final class ImageCyclePageChangeListener implements ViewPager.OnPageChangeListener {
    private int preIndex = 0;
    
    private ImageCyclePageChangeListener() {}
    
    public void onPageScrollStateChanged(int param1Int) {}
    
    public void onPageScrolled(int param1Int1, float param1Float, int param1Int2) {}
    
    public void onPageSelected(int param1Int) {
      param1Int %= ImageCycleView.this.mCount;
      String str1 = (ImageCycleView.this.data.get(param1Int)).text;
      TextView textView = ImageCycleView.this.mText;
      String str2 = str1;
      if (TextUtils.isEmpty(str1))
        str2 = ""; 
      textView.setText(str2);
      ((ImageView)ImageCycleView.this.mIndicationGroup.getChildAt(this.preIndex)).setImageBitmap(ImageCycleView.this.unFocusIndicationStyle);
      ((ImageView)ImageCycleView.this.mIndicationGroup.getChildAt(param1Int)).setImageBitmap(ImageCycleView.this.focusIndicationStyle);
      this.preIndex = param1Int;
    }
  }
  
  public class ImageCycleViewPager extends ViewPager {
    public ImageCycleViewPager(Context param1Context) {
      super(param1Context);
    }
    
    public ImageCycleViewPager(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
    }
    
    public boolean dispatchTouchEvent(MotionEvent param1MotionEvent) {
      getParent().requestDisallowInterceptTouchEvent(true);
      return super.dispatchTouchEvent(param1MotionEvent);
    }
    
    public boolean onInterceptTouchEvent(MotionEvent param1MotionEvent) {
      return super.onInterceptTouchEvent(param1MotionEvent);
    }
    
    public boolean onTouchEvent(MotionEvent param1MotionEvent) {
      return super.onTouchEvent(param1MotionEvent);
    }
  }
  
  public static class ImageInfo {
    public Object image;
    
    public String text = "";
    
    public Object value;
    
    public ImageInfo(Object param1Object1, String param1String, Object param1Object2) {
      this.image = param1Object1;
      this.text = param1String;
      this.value = param1Object2;
    }
  }
  
  public enum IndicationStyle {
    COLOR, IMAGE;
    
    static {
      IndicationStyle indicationStyle = new IndicationStyle("IMAGE", 1);
      IMAGE = indicationStyle;
      $VALUES = new IndicationStyle[] { COLOR, indicationStyle };
    }
  }
  
  public static interface LoadImageCallBack {
    ImageView loadAndDisplay(ImageCycleView.ImageInfo param1ImageInfo);
  }
  
  public static interface OnPageClickListener {
    void onClick(View param1View, ImageCycleView.ImageInfo param1ImageInfo);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\ImageCycleView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */