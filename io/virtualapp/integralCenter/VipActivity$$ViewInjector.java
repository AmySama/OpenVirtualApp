package io.virtualapp.integralCenter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import io.virtualapp.widgets.MyListView;

public class VipActivity$$ViewInjector {
  public static void inject(ButterKnife.Finder paramFinder, VipActivity paramVipActivity, Object paramObject) {
    View view = paramFinder.findById(paramObject, 2131296283);
    if (view != null) {
      paramVipActivity.actionLayout = (RelativeLayout)view;
      view = paramFinder.findById(paramObject, 2131296568);
      if (view != null) {
        paramVipActivity.leftLayout = (LinearLayout)view;
        view = paramFinder.findById(paramObject, 2131296780);
        if (view != null) {
          paramVipActivity.vipGoodsList = (MyListView)view;
          view = paramFinder.findById(paramObject, 2131296811);
          if (view != null) {
            paramVipActivity.zixun = (LinearLayout)view;
            view = paramFinder.findById(paramObject, 2131296808);
            if (view != null) {
              paramVipActivity.xieyiLayout = (LinearLayout)view;
              view = paramFinder.findById(paramObject, 2131296787);
              if (view != null) {
                paramVipActivity.vipOpen = (TextView)view;
                view = paramFinder.findById(paramObject, 2131296781);
                if (view != null) {
                  paramVipActivity.vipIcon = (ImageView)view;
                  view = paramFinder.findById(paramObject, 2131296444);
                  if (view != null) {
                    paramVipActivity.vipText = (TextView)view;
                    view = paramFinder.findById(paramObject, 2131296784);
                    if (view != null) {
                      paramVipActivity.vipLayout = (LinearLayout)view;
                      view = paramFinder.findById(paramObject, 2131296611);
                      if (view != null) {
                        paramVipActivity.payPrice = (TextView)view;
                        view = paramFinder.findById(paramObject, 2131296792);
                        if (view != null) {
                          paramVipActivity.vipName = (TextView)view;
                          view = paramFinder.findById(paramObject, 2131296806);
                          if (view != null) {
                            paramVipActivity.xieyiCheckBox = (CheckBox)view;
                            View view1 = paramFinder.findById(paramObject, 2131296719);
                            if (view1 != null) {
                              paramVipActivity.changeLayout = (LinearLayout)view1;
                              return;
                            } 
                            throw new IllegalStateException("Required view with id '2131296719' for field 'changeLayout' was not found. If this view is optional add '@Optional' annotation.");
                          } 
                          throw new IllegalStateException("Required view with id '2131296806' for field 'xieyiCheckBox' was not found. If this view is optional add '@Optional' annotation.");
                        } 
                        throw new IllegalStateException("Required view with id '2131296792' for field 'vipName' was not found. If this view is optional add '@Optional' annotation.");
                      } 
                      throw new IllegalStateException("Required view with id '2131296611' for field 'payPrice' was not found. If this view is optional add '@Optional' annotation.");
                    } 
                    throw new IllegalStateException("Required view with id '2131296784' for field 'vipLayout' was not found. If this view is optional add '@Optional' annotation.");
                  } 
                  throw new IllegalStateException("Required view with id '2131296444' for field 'vipText' was not found. If this view is optional add '@Optional' annotation.");
                } 
                throw new IllegalStateException("Required view with id '2131296781' for field 'vipIcon' was not found. If this view is optional add '@Optional' annotation.");
              } 
              throw new IllegalStateException("Required view with id '2131296787' for field 'vipOpen' was not found. If this view is optional add '@Optional' annotation.");
            } 
            throw new IllegalStateException("Required view with id '2131296808' for field 'xieyiLayout' was not found. If this view is optional add '@Optional' annotation.");
          } 
          throw new IllegalStateException("Required view with id '2131296811' for field 'zixun' was not found. If this view is optional add '@Optional' annotation.");
        } 
        throw new IllegalStateException("Required view with id '2131296780' for field 'vipGoodsList' was not found. If this view is optional add '@Optional' annotation.");
      } 
      throw new IllegalStateException("Required view with id '2131296568' for field 'leftLayout' was not found. If this view is optional add '@Optional' annotation.");
    } 
    throw new IllegalStateException("Required view with id '2131296283' for field 'actionLayout' was not found. If this view is optional add '@Optional' annotation.");
  }
  
  public static void reset(VipActivity paramVipActivity) {
    paramVipActivity.actionLayout = null;
    paramVipActivity.leftLayout = null;
    paramVipActivity.vipGoodsList = null;
    paramVipActivity.zixun = null;
    paramVipActivity.xieyiLayout = null;
    paramVipActivity.vipOpen = null;
    paramVipActivity.vipIcon = null;
    paramVipActivity.vipText = null;
    paramVipActivity.vipLayout = null;
    paramVipActivity.payPrice = null;
    paramVipActivity.vipName = null;
    paramVipActivity.xieyiCheckBox = null;
    paramVipActivity.changeLayout = null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\integralCenter\VipActivity$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */