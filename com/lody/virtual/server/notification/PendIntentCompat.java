package com.lody.virtual.server.notification;

import android.app.PendingIntent;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RemoteViews;
import com.lody.virtual.helper.utils.Reflect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class PendIntentCompat {
  private Map<Integer, PendingIntent> clickIntents;
  
  private RemoteViews mRemoteViews;
  
  PendIntentCompat(RemoteViews paramRemoteViews) {
    this.mRemoteViews = paramRemoteViews;
  }
  
  private RectInfo findIntent(Rect paramRect, List<RectInfo> paramList) {
    RectInfo rectInfo;
    Iterator<RectInfo> iterator = paramList.iterator();
    int i = 0;
    paramList = null;
    while (iterator.hasNext()) {
      RectInfo rectInfo1 = iterator.next();
      int j = getOverlapArea(paramRect, rectInfo1.rect);
      if (j > i) {
        if (j == 0) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("find two:");
          stringBuilder.append(rectInfo1.rect);
          Log.w("PendingIntentCompat", stringBuilder.toString());
        } 
        rectInfo = rectInfo1;
        i = j;
      } 
    } 
    return rectInfo;
  }
  
  private Map<Integer, PendingIntent> getClickIntents(RemoteViews paramRemoteViews) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    if (paramRemoteViews == null)
      return (Map)hashMap; 
    Exception exception = null;
    try {
      object = Reflect.on(paramRemoteViews).get("mActions");
    } catch (Exception object) {
      object.printStackTrace();
      object = exception;
    } 
    if (object == null)
      return (Map)hashMap; 
    if (object instanceof java.util.Collection)
      for (Object object1 : object) {
        if (object1 != null) {
          String str;
          try {
            object = Reflect.on(object1).call("getActionName").get();
          } catch (Exception exception1) {
            str = object1.getClass().getSimpleName();
          } 
          if ("SetOnClickPendingIntent".equalsIgnoreCase(str))
            hashMap.put(Integer.valueOf(((Integer)Reflect.on(object1).get("viewId")).intValue()), Reflect.on(object1).get("pendingIntent")); 
        } 
      }  
    return (Map)hashMap;
  }
  
  private int getOverlapArea(Rect paramRect1, Rect paramRect2) {
    Rect rect = new Rect();
    rect.left = Math.max(paramRect1.left, paramRect2.left);
    rect.top = Math.max(paramRect1.top, paramRect2.top);
    rect.right = Math.min(paramRect1.right, paramRect2.right);
    rect.bottom = Math.min(paramRect1.bottom, paramRect2.bottom);
    return (rect.left < rect.right && rect.top < rect.bottom) ? ((rect.right - rect.left) * (rect.bottom - rect.top)) : 0;
  }
  
  private Rect getRect(View paramView) {
    Rect rect = new Rect();
    rect.top = paramView.getTop();
    rect.left = paramView.getLeft();
    rect.right = paramView.getRight();
    rect.bottom = paramView.getBottom();
    ViewParent viewParent = paramView.getParent();
    if (viewParent != null && viewParent instanceof ViewGroup) {
      Rect rect1 = getRect((View)viewParent);
      rect.top += rect1.top;
      rect.left += rect1.left;
      rect.right += rect1.left;
      rect.bottom += rect1.top;
    } 
    return rect;
  }
  
  private void setIntentByViewGroup(RemoteViews paramRemoteViews, ViewGroup paramViewGroup, List<RectInfo> paramList) {
    int i = paramViewGroup.getChildCount();
    paramViewGroup.getHitRect(new Rect());
    for (byte b = 0; b < i; b++) {
      View view = paramViewGroup.getChildAt(b);
      if (view instanceof ViewGroup) {
        setIntentByViewGroup(paramRemoteViews, (ViewGroup)view, paramList);
      } else if (view instanceof android.widget.TextView || view instanceof android.widget.ImageView) {
        RectInfo rectInfo = findIntent(getRect(view), paramList);
        if (rectInfo != null)
          paramRemoteViews.setOnClickPendingIntent(view.getId(), rectInfo.mPendingIntent); 
      } 
    } 
  }
  
  public int findPendIntents() {
    if (this.clickIntents == null)
      this.clickIntents = getClickIntents(this.mRemoteViews); 
    return this.clickIntents.size();
  }
  
  public void setPendIntent(RemoteViews paramRemoteViews, View paramView1, View paramView2) {
    if (findPendIntents() > 0) {
      Iterator<Map.Entry> iterator = this.clickIntents.entrySet().iterator();
      ArrayList<RectInfo> arrayList = new ArrayList();
      byte b = 0;
      while (iterator.hasNext()) {
        Map.Entry entry = iterator.next();
        View view = paramView2.findViewById(((Integer)entry.getKey()).intValue());
        if (view != null) {
          arrayList.add(new RectInfo(getRect(view), (PendingIntent)entry.getValue(), b));
          b++;
        } 
      } 
      if (paramView1 instanceof ViewGroup)
        setIntentByViewGroup(paramRemoteViews, (ViewGroup)paramView1, arrayList); 
    } 
  }
  
  class RectInfo {
    int index;
    
    PendingIntent mPendingIntent;
    
    Rect rect;
    
    public RectInfo(Rect param1Rect, PendingIntent param1PendingIntent, int param1Int) {
      this.rect = param1Rect;
      this.mPendingIntent = param1PendingIntent;
      this.index = param1Int;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("RectInfo{rect=");
      stringBuilder.append(this.rect);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\notification\PendIntentCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */