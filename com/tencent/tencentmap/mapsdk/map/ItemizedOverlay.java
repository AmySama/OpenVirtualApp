package com.tencent.tencentmap.mapsdk.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import com.tencent.mapsdk.raster.model.GeoPoint;
import com.tencent.mapsdk.rastercore.f.a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

@Deprecated
public abstract class ItemizedOverlay<Item extends OverlayItem> extends Overlay {
  private boolean boDrawShadow = true;
  
  private boolean boLongPress = false;
  
  private Drawable defaultItemMarker;
  
  private Item itemSelected;
  
  private ItemsContainer mContainer = null;
  
  private int mCurFocus = -1;
  
  private boolean mDrawFocusedItem = true;
  
  private int mLastFocus = -1;
  
  private OnFocusChangeListener mlistener = null;
  
  public ItemizedOverlay(Context paramContext) {
    Bitmap bitmap = a.a("marker.png");
    BitmapDrawable bitmapDrawable = new BitmapDrawable(paramContext.getResources(), bitmap);
    this.defaultItemMarker = (Drawable)bitmapDrawable;
    boundCenterBottom((Drawable)bitmapDrawable);
  }
  
  public ItemizedOverlay(Drawable paramDrawable) {
    this.defaultItemMarker = paramDrawable;
    if (paramDrawable != null) {
      Rect rect = paramDrawable.getBounds();
      if (rect == null) {
        boundCenterBottom(this.defaultItemMarker);
        return;
      } 
      if (rect.left == 0 && rect.right == 0 && rect.top == 0 && rect.bottom == 0)
        boundCenterBottom(this.defaultItemMarker); 
      return;
    } 
    throw new IllegalArgumentException("the drawable can not be null!");
  }
  
  private Item backwordFocus(int paramInt) {
    ItemsContainer itemsContainer = this.mContainer;
    return (itemsContainer == null) ? null : ((paramInt == 0) ? null : itemsContainer.getItemByPos(paramInt - 1));
  }
  
  public static Drawable boundCenter(Drawable paramDrawable) {
    return resetBound(paramDrawable, BoundPos.Center);
  }
  
  public static Drawable boundCenterBottom(Drawable paramDrawable) {
    return resetBound(paramDrawable, BoundPos.CenterBottom);
  }
  
  private void drawItem(Canvas paramCanvas, MapView paramMapView, boolean paramBoolean, Item paramItem, Point paramPoint) {
    Drawable drawable2 = paramItem.getMarker();
    Drawable drawable1 = drawable2;
    if (drawable2 == null)
      drawable1 = this.defaultItemMarker; 
    paramCanvas.save();
    if (paramBoolean) {
      paramCanvas.translate(paramPoint.x, paramPoint.y);
      drawShadow(drawable1, paramCanvas);
    } else {
      paramCanvas.translate(paramPoint.x, paramPoint.y);
      drawable1.draw(paramCanvas);
    } 
    paramCanvas.restore();
  }
  
  static void drawShadow(Drawable paramDrawable, Canvas paramCanvas) {
    paramDrawable.setColorFilter(2130706432, PorterDuff.Mode.SRC_IN);
    paramCanvas.skew(-0.89F, 0.0F);
    paramCanvas.scale(1.0F, 0.5F);
    paramDrawable.draw(paramCanvas);
    paramDrawable.clearColorFilter();
  }
  
  private Item forwordFocus(int paramInt) {
    ItemsContainer itemsContainer = this.mContainer;
    return (itemsContainer == null) ? null : ((paramInt == itemsContainer.count() - 1) ? null : this.mContainer.getItemByPos(paramInt + 1));
  }
  
  private static Drawable resetBound(Drawable paramDrawable, BoundPos paramBoundPos) {
    if (paramDrawable == null || BoundPos.Normal == paramBoundPos)
      return null; 
    int i = paramDrawable.getIntrinsicWidth();
    int j = paramDrawable.getIntrinsicHeight();
    int k = 0;
    paramDrawable.setBounds(0, 0, i, j);
    Rect rect = paramDrawable.getBounds();
    int m = rect.width() / 2;
    i = -rect.height();
    j = i;
    if (paramBoundPos == BoundPos.Center) {
      j = i / 2;
      k = -j;
    } 
    paramDrawable.setBounds(-m, j, m, k);
    return paramDrawable;
  }
  
  protected abstract Item createItem(int paramInt);
  
  public void draw(Canvas paramCanvas, MapView paramMapView) {
    int i = size();
    if (i <= 0)
      return; 
    Projection projection = paramMapView.getProjection();
    byte b = 0;
    while (true) {
      if (b < i) {
        try {
          Item item1 = getItem(getIndexToDraw(b));
          if (item1 != null) {
            Point point = projection.toPixels(item1.getPoint(), null);
            if (point != null) {
              if (this.boDrawShadow == true)
                drawItem(paramCanvas, paramMapView, true, item1, point); 
              drawItem(paramCanvas, paramMapView, false, item1, point);
            } 
          } 
        } catch (Exception exception) {}
        b++;
        continue;
      } 
      Item item = getFocus();
      if (this.mDrawFocusedItem && item != null) {
        Point point = projection.toPixels(item.getPoint(), null);
        if (this.boDrawShadow == true)
          drawItem(paramCanvas, paramMapView, true, item, point); 
        drawItem(paramCanvas, paramMapView, false, item, point);
      } 
      return;
    } 
  }
  
  public GeoPoint getCenter() {
    return (this.mContainer == null) ? null : new GeoPoint(this.mContainer.iLatitudeCenter, this.mContainer.iLongitudeCenter);
  }
  
  public Item getFocus() {
    ItemsContainer itemsContainer = this.mContainer;
    Item item = null;
    if (itemsContainer == null)
      return null; 
    int i = this.mCurFocus;
    if (i != -1)
      item = itemsContainer.getItemByPos(i); 
    return item;
  }
  
  protected int getIndexToDraw(int paramInt) {
    ItemsContainer itemsContainer = this.mContainer;
    return (itemsContainer == null) ? -1 : itemsContainer.getPosByRank(paramInt);
  }
  
  public final Item getItem(int paramInt) {
    ItemsContainer itemsContainer = this.mContainer;
    return (itemsContainer == null) ? null : itemsContainer.getItemByPos(paramInt);
  }
  
  public final int getLastFocusedIndex() {
    return this.mLastFocus;
  }
  
  public int getLatSpanE6() {
    ItemsContainer itemsContainer = this.mContainer;
    return (itemsContainer == null) ? 0 : itemsContainer.getSpan(true);
  }
  
  public int getLonSpanE6() {
    ItemsContainer itemsContainer = this.mContainer;
    return (itemsContainer == null) ? 0 : itemsContainer.getSpan(false);
  }
  
  public boolean isShadowEnable() {
    return this.boDrawShadow;
  }
  
  public Item nextFocus(boolean paramBoolean) {
    ItemsContainer itemsContainer = this.mContainer;
    if (itemsContainer == null || itemsContainer.count() == 0)
      return null; 
    int i = this.mLastFocus;
    if (i == -1)
      return (this.mCurFocus == -1) ? null : this.mContainer.getItemByPos(0); 
    int j = this.mCurFocus;
    if (j != -1)
      i = j; 
    return paramBoolean ? forwordFocus(i) : backwordFocus(i);
  }
  
  public void onEmptyTap(GeoPoint paramGeoPoint) {
    super.onEmptyTap(paramGeoPoint);
  }
  
  public boolean onLongPress(GeoPoint paramGeoPoint, MotionEvent paramMotionEvent, MapView paramMapView) {
    ItemsContainer itemsContainer = this.mContainer;
    if (itemsContainer == null)
      return false; 
    itemsContainer.HandleLongPress(paramGeoPoint, paramMapView);
    this.boLongPress = true;
    return false;
  }
  
  protected boolean onTap(int paramInt) {
    if (this.mContainer == null)
      return false; 
    if (paramInt != this.mCurFocus)
      setFocus(getItem(paramInt)); 
    return false;
  }
  
  public boolean onTap(GeoPoint paramGeoPoint, MapView paramMapView) {
    ItemsContainer itemsContainer = this.mContainer;
    return (itemsContainer == null) ? false : itemsContainer.HandleTap(paramGeoPoint, paramMapView);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent, MapView paramMapView) {
    boolean bool = this.boLongPress;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (bool) {
      Item item = this.itemSelected;
      if (item == null) {
        bool2 = bool1;
      } else {
        if (!item.isDragable())
          return false; 
        int i = paramMotionEvent.getAction();
        if (i != 1)
          if (i != 2) {
            if (i != 3 && i != 4)
              return bool1; 
          } else {
            int j = (int)paramMotionEvent.getX();
            i = (int)paramMotionEvent.getY();
            GeoPoint geoPoint = paramMapView.getProjection().fromPixels(j, i);
            this.itemSelected.setPoint(geoPoint);
            this.mapContext.c().invalidate();
            bool2 = true;
          }  
        this.boLongPress = false;
        this.itemSelected = null;
        bool2 = true;
      } 
    } 
    return bool2;
  }
  
  protected final void populate() {
    ItemsContainer itemsContainer = this.mContainer;
    if (itemsContainer != null)
      itemsContainer.release(); 
    this.mContainer = new ItemsContainer();
    this.mLastFocus = -1;
    this.mCurFocus = -1;
  }
  
  public void setDrawFocusedItem(boolean paramBoolean) {
    this.mDrawFocusedItem = paramBoolean;
  }
  
  public void setFocus(Item paramItem) {
    ItemsContainer itemsContainer = this.mContainer;
    if (itemsContainer == null)
      return; 
    if (paramItem != null && this.mCurFocus == itemsContainer.getIndexByItem(paramItem))
      return; 
    if (paramItem == null && this.mCurFocus != -1) {
      OnFocusChangeListener onFocusChangeListener = this.mlistener;
      if (onFocusChangeListener != null)
        onFocusChangeListener.onFocusChanged(this, (OverlayItem)paramItem); 
      this.mCurFocus = -1;
      return;
    } 
    int i = this.mContainer.getIndexByItem(paramItem);
    this.mCurFocus = i;
    if (i != -1) {
      setLastFocusedIndex(i);
      OnFocusChangeListener onFocusChangeListener = this.mlistener;
      if (onFocusChangeListener != null)
        onFocusChangeListener.onFocusChanged(this, (OverlayItem)paramItem); 
    } 
  }
  
  protected void setLastFocusedIndex(int paramInt) {
    this.mLastFocus = paramInt;
  }
  
  public void setOnFocusChangeListener(OnFocusChangeListener paramOnFocusChangeListener) {
    this.mlistener = paramOnFocusChangeListener;
  }
  
  public void setShadowEnable(boolean paramBoolean) {
    this.boDrawShadow = paramBoolean;
  }
  
  public abstract int size();
  
  enum BoundPos {
    Center, CenterBottom, Normal;
    
    static {
      BoundPos boundPos = new BoundPos("CenterBottom", 2);
      CenterBottom = boundPos;
      $VALUES = new BoundPos[] { Normal, Center, boundPos };
    }
  }
  
  class ItemsContainer implements Comparator<Integer> {
    private ArrayList<Integer> drawIndex = new ArrayList<Integer>();
    
    int iLatitudeCenter = 0;
    
    int iLongitudeCenter = 0;
    
    private ArrayList<Item> mItems = new ArrayList<Item>();
    
    public ItemsContainer() {
      ArrayList<Item> arrayList1 = this.mItems;
      if (arrayList1 != null)
        arrayList1.clear(); 
      ArrayList<Integer> arrayList = this.drawIndex;
      if (arrayList != null)
        arrayList.clear(); 
      int i = ItemizedOverlay.this.size();
      byte b = 0;
      int j = 0;
      int k = 0;
      while (b < i) {
        this.drawIndex.add(Integer.valueOf(b));
        arrayList = ItemizedOverlay.this.createItem(b);
        j += arrayList.getPoint().getLatitudeE6();
        k += arrayList.getPoint().getLongitudeE6();
        this.mItems.add((Item)arrayList);
        b++;
      } 
      if (i > 0) {
        this.iLatitudeCenter = j / i;
        this.iLongitudeCenter = k / i;
      } else {
        this.iLatitudeCenter = 0;
        this.iLongitudeCenter = 0;
      } 
      Collections.sort(this.drawIndex, this);
    }
    
    private int getHitItemIndex(GeoPoint param1GeoPoint, MapView param1MapView) {
      Projection projection = param1MapView.getProjection();
      Point point = projection.toPixels(param1GeoPoint, null);
      int i = count();
      byte b = -1;
      double d = Double.MAX_VALUE;
      int j = Integer.MAX_VALUE;
      byte b1 = 0;
      while (b1 < i) {
        OverlayItem overlayItem = (OverlayItem)this.mItems.get(b1);
        byte b2 = b;
        double d1 = d;
        int k = j;
        if (overlayItem != null) {
          double d2 = hitItemDis((Item)overlayItem, projection, point, b1);
          if (d2 >= 0.0D && d2 < d) {
            k = getPosByRank(b1);
            b2 = b1;
            d1 = d2;
          } else {
            b2 = b;
            d1 = d;
            k = j;
            if (d2 == d) {
              b2 = b;
              d1 = d;
              k = j;
              if (getPosByRank(b1) > j) {
                b2 = b1;
                k = j;
                d1 = d;
              } 
            } 
          } 
        } 
        b1++;
        b = b2;
        d = d1;
        j = k;
      } 
      return b;
    }
    
    private double hitItemDis(Item param1Item, Projection param1Projection, Point param1Point, int param1Int) {
      double d;
      Point point = param1Projection.toPixels(param1Item.getPoint(), null);
      if (isItemHited(param1Item.getMarker(), point, param1Projection, param1Point, param1Int)) {
        Point point1 = new Point(param1Point.x - point.x, param1Point.y - point.y);
        d = (point1.x * point1.x + point1.y * point1.y);
      } else {
        d = -1.0D;
      } 
      return d;
    }
    
    private boolean isItemHited(Drawable param1Drawable, Point param1Point1, Projection param1Projection, Point param1Point2, int param1Int) {
      Point point = new Point(param1Point2.x - param1Point1.x, param1Point2.y - param1Point1.y);
      Drawable drawable = param1Drawable;
      if (param1Drawable == null)
        drawable = ItemizedOverlay.this.defaultItemMarker; 
      return drawable.getBounds().contains(point.x, point.y);
    }
    
    public boolean HandleLongPress(GeoPoint param1GeoPoint, MapView param1MapView) {
      int i = getHitItemIndex(param1GeoPoint, param1MapView);
      if (i != -1)
        ItemizedOverlay.access$002(ItemizedOverlay.this, (OverlayItem)this.mItems.get(i)); 
      return false;
    }
    
    public boolean HandleTap(GeoPoint param1GeoPoint, MapView param1MapView) {
      boolean bool;
      int i = getHitItemIndex(param1GeoPoint, param1MapView);
      if (i >= 0) {
        bool = ItemizedOverlay.this.onTap(i);
      } else {
        ItemizedOverlay.this.setFocus(null);
        bool = false;
      } 
      param1MapView.postInvalidate();
      return bool;
    }
    
    public int compare(Integer param1Integer1, Integer param1Integer2) {
      GeoPoint geoPoint1 = ((OverlayItem)this.mItems.get(param1Integer1.intValue())).getPoint();
      GeoPoint geoPoint2 = ((OverlayItem)this.mItems.get(param1Integer2.intValue())).getPoint();
      if (geoPoint1 != null && geoPoint2 != null) {
        if (geoPoint1.getLatitudeE6() > geoPoint2.getLatitudeE6())
          return -1; 
        if (geoPoint1.getLatitudeE6() < geoPoint2.getLatitudeE6())
          return 1; 
        if (geoPoint1.getLongitudeE6() < geoPoint2.getLongitudeE6())
          return -1; 
        if (geoPoint1.getLongitudeE6() > geoPoint2.getLongitudeE6())
          return 1; 
      } 
      return 0;
    }
    
    public int count() {
      return this.mItems.size();
    }
    
    public int getIndexByItem(Item param1Item) {
      int i = count();
      if (param1Item != null) {
        byte b1 = 0;
        while (b1 < i) {
          if (!param1Item.equals(this.mItems.get(b1))) {
            b1++;
            continue;
          } 
          return b1;
        } 
      } 
      byte b = -1;
      while (b < i) {
        if (!param1Item.equals(this.mItems.get(b))) {
          b++;
          continue;
        } 
        return b;
      } 
    }
    
    public Item getItemByPos(int param1Int) {
      ArrayList<Item> arrayList = this.mItems;
      return (Item)((arrayList == null) ? null : ((arrayList.size() <= param1Int || param1Int < 0) ? null : (OverlayItem)this.mItems.get(param1Int)));
    }
    
    public int getPosByRank(int param1Int) {
      return (this.drawIndex.size() <= param1Int) ? -1 : ((Integer)this.drawIndex.get(param1Int)).intValue();
    }
    
    public int getSpan(boolean param1Boolean) {
      if (this.mItems.size() == 0)
        return 0; 
      int i = Integer.MIN_VALUE;
      int j = Integer.MAX_VALUE;
      Iterator<Item> iterator = this.mItems.iterator();
      while (iterator.hasNext()) {
        int k;
        GeoPoint geoPoint = ((OverlayItem)iterator.next()).getPoint();
        if (param1Boolean) {
          k = geoPoint.getLatitudeE6();
        } else {
          k = geoPoint.getLongitudeE6();
        } 
        int m = i;
        if (k > i)
          m = k; 
        i = m;
        if (k < j) {
          i = m;
          j = k;
        } 
      } 
      return i - j;
    }
    
    public void release() {
      ArrayList<Item> arrayList1 = this.mItems;
      if (arrayList1 != null)
        arrayList1.clear(); 
      ArrayList<Integer> arrayList = this.drawIndex;
      if (arrayList != null)
        arrayList.clear(); 
    }
  }
  
  public static interface OnFocusChangeListener {
    void onFocusChanged(ItemizedOverlay<?> param1ItemizedOverlay, OverlayItem param1OverlayItem);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\tencentmap\mapsdk\map\ItemizedOverlay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */