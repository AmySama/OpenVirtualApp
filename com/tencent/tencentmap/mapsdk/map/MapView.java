package com.tencent.tencentmap.mapsdk.map;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.stub.StubApp;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.Circle;
import com.tencent.mapsdk.raster.model.CircleOptions;
import com.tencent.mapsdk.raster.model.GeoPoint;
import com.tencent.mapsdk.raster.model.GroundOverlay;
import com.tencent.mapsdk.raster.model.GroundOverlayOptions;
import com.tencent.mapsdk.raster.model.IOverlay;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.LatLngBounds;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.mapsdk.raster.model.Polygon;
import com.tencent.mapsdk.raster.model.PolygonOptions;
import com.tencent.mapsdk.raster.model.Polyline;
import com.tencent.mapsdk.raster.model.PolylineOptions;
import com.tencent.mapsdk.rastercore.c;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.d.f;
import com.tencent.mapsdk.rastercore.f.a;

public class MapView extends FrameLayout {
  @Deprecated
  private MapController controller;
  
  private f eventHandler;
  
  private e mapContext;
  
  private Projection projection;
  
  private TencentMap tencentMap;
  
  private UiSettings uiSettings;
  
  public MapView(Context paramContext) {
    super(paramContext);
    init();
  }
  
  public MapView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  private void doLayout(View paramView, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, int paramInt3) {
    // Byte code:
    //   0: iload #6
    //   2: bipush #7
    //   4: iand
    //   5: istore #7
    //   7: iload #6
    //   9: bipush #112
    //   11: iand
    //   12: istore #6
    //   14: iload #7
    //   16: iconst_5
    //   17: if_icmpne -> 34
    //   20: iload_2
    //   21: i2f
    //   22: fstore #8
    //   24: fload #4
    //   26: fload #8
    //   28: fsub
    //   29: fstore #8
    //   31: goto -> 53
    //   34: fload #4
    //   36: fstore #8
    //   38: iload #7
    //   40: iconst_1
    //   41: if_icmpne -> 53
    //   44: iload_2
    //   45: iconst_2
    //   46: idiv
    //   47: i2f
    //   48: fstore #8
    //   50: goto -> 24
    //   53: iload #6
    //   55: bipush #80
    //   57: if_icmpne -> 74
    //   60: iload_3
    //   61: i2f
    //   62: fstore #4
    //   64: fload #5
    //   66: fload #4
    //   68: fsub
    //   69: fstore #4
    //   71: goto -> 94
    //   74: fload #5
    //   76: fstore #4
    //   78: iload #6
    //   80: bipush #16
    //   82: if_icmpne -> 94
    //   85: iload_3
    //   86: iconst_2
    //   87: idiv
    //   88: i2f
    //   89: fstore #4
    //   91: goto -> 64
    //   94: fload #8
    //   96: invokestatic round : (F)I
    //   99: istore #6
    //   101: fload #4
    //   103: invokestatic round : (F)I
    //   106: istore #7
    //   108: aload_1
    //   109: iload #6
    //   111: iload #7
    //   113: iload_2
    //   114: iload #6
    //   116: iadd
    //   117: iload_3
    //   118: iload #7
    //   120: iadd
    //   121: invokevirtual layout : (IIII)V
    //   124: return
  }
  
  private void doMeasure(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfint) {
    if (paramView instanceof android.widget.ListView) {
      View view = (View)paramView.getParent();
      if (view != null) {
        paramArrayOfint[0] = view.getWidth();
        paramArrayOfint[1] = view.getHeight();
      } 
    } 
    if (paramInt1 <= 0 || paramInt2 <= 0)
      paramView.measure(0, 0); 
    if (paramInt1 == -2) {
      paramArrayOfint[0] = paramView.getMeasuredWidth();
    } else if (paramInt1 == -1) {
      paramArrayOfint[0] = getMeasuredWidth();
    } else {
      paramArrayOfint[0] = paramInt1;
    } 
    if (paramInt2 == -2) {
      paramArrayOfint[1] = paramView.getMeasuredHeight();
      return;
    } 
    if (paramInt2 == -1) {
      paramArrayOfint[1] = getMeasuredHeight();
      return;
    } 
    paramArrayOfint[1] = paramInt2;
  }
  
  private void init() {
    initForBugly();
    Context context = getContext();
    e e1 = new e(this);
    this.mapContext = e1;
    f f1 = e1.h();
    this.eventHandler = f1;
    setOnKeyListener((View.OnKeyListener)f1);
    this.projection = new Projection(this.mapContext);
    this.uiSettings = new UiSettings(this.mapContext.f());
    this.tencentMap = new TencentMap(this.mapContext);
    this.controller = new MapController(this);
    if (context instanceof MapActivity)
      ((MapActivity)context).setMapView(this); 
    setBackgroundColor(-657936);
  }
  
  private void initForBugly() {
    SharedPreferences.Editor editor = StubApp.getOrigApplicationContext(getContext().getApplicationContext()).getSharedPreferences("BuglySdkInfos", 0).edit();
    editor.putString("4e7cb4aa49", "1.2.6");
    editor.commit();
  }
  
  private void layout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    paramInt2 = getChildCount();
    for (paramInt1 = 0; paramInt1 < paramInt2; paramInt1++) {
      View view = getChildAt(paramInt1);
      if (view != null)
        layout(view); 
    } 
  }
  
  private void layoutMap(View paramView, LayoutParams paramLayoutParams) {
    int[] arrayOfInt = new int[2];
    doMeasure(paramView, paramLayoutParams.width, paramLayoutParams.height, arrayOfInt);
    if (paramLayoutParams.getPoint() != null) {
      PointF pointF = this.mapContext.b().a(paramLayoutParams.getPoint());
      if (pointF == null)
        return; 
      pointF.x += paramLayoutParams.x;
      pointF.y += paramLayoutParams.y;
      doLayout(paramView, arrayOfInt[0], arrayOfInt[1], pointF.x, pointF.y, paramLayoutParams.alignment);
    } 
  }
  
  private void layoutView(View paramView, LayoutParams paramLayoutParams) {
    int[] arrayOfInt = new int[2];
    doMeasure(paramView, paramLayoutParams.width, paramLayoutParams.height, arrayOfInt);
    doLayout(paramView, arrayOfInt[0], arrayOfInt[1], paramLayoutParams.x, paramLayoutParams.y, paramLayoutParams.alignment);
  }
  
  protected static void setIsChinese(boolean paramBoolean) {
    c.a(paramBoolean);
  }
  
  public Circle addCircle(CircleOptions paramCircleOptions) {
    return this.tencentMap.addCircle(paramCircleOptions);
  }
  
  public GroundOverlay addGroundOverlay(GroundOverlayOptions paramGroundOverlayOptions) {
    return this.tencentMap.addGroundOverlay(paramGroundOverlayOptions);
  }
  
  public final Marker addMarker(MarkerOptions paramMarkerOptions) {
    return this.tencentMap.addMarker(paramMarkerOptions);
  }
  
  public GroundOverlay addOverlay(Bitmap paramBitmap, LatLng paramLatLng1, LatLng paramLatLng2) {
    return this.tencentMap.addGroundOverlay((new GroundOverlayOptions()).positionFromBounds(new LatLngBounds(paramLatLng1, paramLatLng2)).anchor(0.5F, 0.5F).transparency(0.1F).image(BitmapDescriptorFactory.fromBitmap(paramBitmap)));
  }
  
  public final boolean addOverlay(Overlay paramOverlay) {
    if (paramOverlay == null)
      return false; 
    paramOverlay.init(this);
    this.mapContext.e().a(paramOverlay);
    return true;
  }
  
  public Polygon addPolygon(PolygonOptions paramPolygonOptions) {
    return this.tencentMap.addPolygon(paramPolygonOptions);
  }
  
  public Polyline addPolyline(PolylineOptions paramPolylineOptions) {
    return this.tencentMap.addPolyline(paramPolylineOptions);
  }
  
  public final void clearAllOverlays() {
    this.tencentMap.clearAllOverlays();
  }
  
  public void computeScroll() {
    super.computeScroll();
    this.eventHandler.a();
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    setClickable(false);
    this.eventHandler.b(MotionEvent.obtain(paramMotionEvent));
    getParent().requestDisallowInterceptTouchEvent(true);
    return (super.dispatchTouchEvent(paramMotionEvent) || this.eventHandler.a(paramMotionEvent));
  }
  
  public MapController getController() {
    return this.controller;
  }
  
  public int getLatitudeSpan() {
    return this.projection.getLatitudeSpan();
  }
  
  public int getLongitudeSpan() {
    return this.projection.getLongitudeSpan();
  }
  
  public TencentMap getMap() {
    return this.tencentMap;
  }
  
  public LatLng getMapCenter() {
    return this.tencentMap.getMapCenter();
  }
  
  protected e getMapContext() {
    return this.mapContext;
  }
  
  public MapController getMapController() {
    return this.controller;
  }
  
  public int getMaxZoomLevel() {
    return this.tencentMap.getMaxZoomLevel();
  }
  
  public int getMinZoomLevel() {
    return this.tencentMap.getMinZoomLevel();
  }
  
  public Projection getProjection() {
    return this.projection;
  }
  
  public float getScalePerPixel() {
    return this.projection.getScalePerPixel();
  }
  
  public UiSettings getUiSettings() {
    return this.uiSettings;
  }
  
  public final String getVersion() {
    return this.tencentMap.getVersion();
  }
  
  public int getZoomLevel() {
    return this.tencentMap.getZoomLevel();
  }
  
  public final boolean isAppKeyAvailable() {
    return this.tencentMap.isAppKeyAvailable();
  }
  
  public boolean isSatellite() {
    return this.tencentMap.isSatelliteEnabled();
  }
  
  public void layout() {
    layout(false, 0, 0, 0, 0);
  }
  
  public void layout(View paramView) {
    if (this == paramView.getParent())
      if (paramView.getLayoutParams() instanceof LayoutParams) {
        LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
        if (layoutParams.mode == 0) {
          layoutMap(paramView, layoutParams);
        } else {
          layoutView(paramView, layoutParams);
          return;
        } 
      } else {
        layoutView(paramView, new LayoutParams(paramView.getLayoutParams()));
      }  
  }
  
  public void moveCamera(CameraUpdate paramCameraUpdate) {
    this.tencentMap.moveCamera(paramCameraUpdate);
  }
  
  public void onCreate(Bundle paramBundle) {
    this.mapContext.a(paramBundle);
  }
  
  public void onDestroy() {
    this.mapContext.m();
  }
  
  public void onDestroyView() {}
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    layout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void onLowMemory() {}
  
  public void onPause() {
    e.n();
  }
  
  public void onRestart() {}
  
  public void onResume() {}
  
  public void onSaveInstanceState(Bundle paramBundle) {
    this.mapContext.b(paramBundle);
  }
  
  public void onStop() {}
  
  public void refreshMap() {
    postInvalidate();
  }
  
  public final void removeOverlay(IOverlay paramIOverlay) {
    this.tencentMap.removeOverlay(paramIOverlay);
  }
  
  protected void setLocation(double paramDouble1, double paramDouble2) {
    c.b(paramDouble1, paramDouble2);
  }
  
  public void setLogoPosition(int paramInt) {
    this.uiSettings.setLogoPosition(paramInt);
  }
  
  public void setPinchEnabeled(boolean paramBoolean) {
    this.uiSettings.setZoomGesturesEnabled(paramBoolean);
  }
  
  public void setSatellite(boolean paramBoolean) {
    this.tencentMap.setSatelliteEnabled(paramBoolean);
  }
  
  public void setScalControlsEnable(boolean paramBoolean) {
    this.uiSettings.setScaleControlsEnabled(paramBoolean);
  }
  
  public void setScaleControlsEnable(boolean paramBoolean) {
    this.uiSettings.setScaleControlsEnabled(paramBoolean);
  }
  
  public void setScaleViewPosition(int paramInt) {
    this.uiSettings.setScaleViewPosition(paramInt);
  }
  
  public void setScrollGesturesEnabled(boolean paramBoolean) {
    this.uiSettings.setScrollGesturesEnabled(paramBoolean);
  }
  
  public void stopAnimation() {
    clearAnimation();
    this.mapContext.c().clearAnimation();
    this.eventHandler.b();
  }
  
  public static class LayoutParams extends FrameLayout.LayoutParams {
    public static final int BOTTOM = 80;
    
    public static final int BOTTOM_CENTER = 81;
    
    public static final int CENTER = 17;
    
    public static final int CENTER_HORIZONTAL = 1;
    
    public static final int CENTER_VERTICAL = 16;
    
    public static final int LEFT = 3;
    
    public static final int MODE_MAP = 0;
    
    public static final int MODE_VIEW = 1;
    
    public static final int RIGHT = 5;
    
    public static final int TOP = 48;
    
    public static final int TOP_LEFT = 51;
    
    private int alignment = 51;
    
    public int mode = 1;
    
    private LatLng point = null;
    
    private int x = 0;
    
    private int y = 0;
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
    }
    
    public LayoutParams(int param1Int1, int param1Int2, GeoPoint param1GeoPoint, int param1Int3) {
      this(param1Int1, param1Int2, param1GeoPoint, 0, 0, param1Int3);
    }
    
    public LayoutParams(int param1Int1, int param1Int2, GeoPoint param1GeoPoint, int param1Int3, int param1Int4, int param1Int5) {
      this(param1Int1, param1Int2, a.a(param1GeoPoint), param1Int3, param1Int4, param1Int5);
    }
    
    public LayoutParams(int param1Int1, int param1Int2, LatLng param1LatLng, int param1Int3) {
      this(param1Int1, param1Int2, param1LatLng, 0, 0, param1Int3);
    }
    
    public LayoutParams(int param1Int1, int param1Int2, LatLng param1LatLng, int param1Int3, int param1Int4, int param1Int5) {
      super(param1Int1, param1Int2);
      this.mode = 0;
      setPoint(param1LatLng);
      this.x = param1Int3;
      this.y = param1Int4;
      this.alignment = param1Int5;
    }
    
    protected LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LatLng getPoint() {
      return this.point;
    }
    
    public void setPoint(LatLng param1LatLng) {
      this.point = param1LatLng;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\tencentmap\mapsdk\map\MapView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */