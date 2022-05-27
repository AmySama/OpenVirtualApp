package com.lody.virtual.client.hook.proxies.telephony;

import android.os.Bundle;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.NeighboringCellInfo;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import com.lody.virtual.client.hook.annotations.SkipInject;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.ipc.VirtualLocationManager;
import com.lody.virtual.remote.VDeviceConfig;
import com.lody.virtual.remote.vloc.VCell;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mirror.android.telephony.CellIdentityCdma;
import mirror.android.telephony.CellIdentityGsm;
import mirror.android.telephony.CellInfoCdma;
import mirror.android.telephony.CellInfoGsm;
import mirror.android.telephony.CellSignalStrengthCdma;
import mirror.android.telephony.CellSignalStrengthGsm;
import mirror.android.telephony.NeighboringCellInfo;

class MethodProxies {
  private static CellInfo createCellInfo(VCell paramVCell) {
    if (paramVCell.type == 2) {
      CellInfoCdma cellInfoCdma = (CellInfoCdma)CellInfoCdma.ctor.newInstance();
      CellIdentityCdma cellIdentityCdma = (CellIdentityCdma)CellInfoCdma.mCellIdentityCdma.get(cellInfoCdma);
      CellSignalStrengthCdma cellSignalStrengthCdma = (CellSignalStrengthCdma)CellInfoCdma.mCellSignalStrengthCdma.get(cellInfoCdma);
      CellIdentityCdma.mNetworkId.set(cellIdentityCdma, paramVCell.networkId);
      CellIdentityCdma.mSystemId.set(cellIdentityCdma, paramVCell.systemId);
      CellIdentityCdma.mBasestationId.set(cellIdentityCdma, paramVCell.baseStationId);
      CellSignalStrengthCdma.mCdmaDbm.set(cellSignalStrengthCdma, -74);
      CellSignalStrengthCdma.mCdmaEcio.set(cellSignalStrengthCdma, -91);
      CellSignalStrengthCdma.mEvdoDbm.set(cellSignalStrengthCdma, -64);
      CellSignalStrengthCdma.mEvdoSnr.set(cellSignalStrengthCdma, 7);
      return (CellInfo)cellInfoCdma;
    } 
    CellInfoGsm cellInfoGsm = (CellInfoGsm)CellInfoGsm.ctor.newInstance();
    CellIdentityGsm cellIdentityGsm = (CellIdentityGsm)CellInfoGsm.mCellIdentityGsm.get(cellInfoGsm);
    CellSignalStrengthGsm cellSignalStrengthGsm = (CellSignalStrengthGsm)CellInfoGsm.mCellSignalStrengthGsm.get(cellInfoGsm);
    if (CellIdentityGsm.mMcc != null)
      CellIdentityGsm.mMcc.set(cellIdentityGsm, paramVCell.mcc); 
    if (CellIdentityGsm.mMnc != null)
      CellIdentityGsm.mMnc.set(cellIdentityGsm, paramVCell.mnc); 
    CellIdentityGsm.mLac.set(cellIdentityGsm, paramVCell.lac);
    CellIdentityGsm.mCid.set(cellIdentityGsm, paramVCell.cid);
    if (CellSignalStrengthGsm.mSignalStrength != null)
      CellSignalStrengthGsm.mSignalStrength.set(cellSignalStrengthGsm, 20); 
    if (CellSignalStrengthGsm.mRssi != null)
      CellSignalStrengthGsm.mRssi.set(cellSignalStrengthGsm, -60); 
    if (CellSignalStrengthGsm.mTimingAdvance != null)
      CellSignalStrengthGsm.mTimingAdvance.set(cellSignalStrengthGsm, 0); 
    CellSignalStrengthGsm.mBitErrorRate.set(cellSignalStrengthGsm, 0);
    return (CellInfo)cellInfoGsm;
  }
  
  private static Bundle getCellLocationInternal(VCell paramVCell) {
    if (paramVCell != null) {
      Bundle bundle = new Bundle();
      if (paramVCell.type == 2) {
        try {
          CdmaCellLocation cdmaCellLocation = new CdmaCellLocation();
          this();
          cdmaCellLocation.setCellLocationData(paramVCell.baseStationId, 2147483647, 2147483647, paramVCell.systemId, paramVCell.networkId);
          cdmaCellLocation.fillInNotifierBundle(bundle);
        } finally {
          Exception exception = null;
          bundle.putInt("baseStationId", paramVCell.baseStationId);
          bundle.putInt("baseStationLatitude", 2147483647);
          bundle.putInt("baseStationLongitude", 2147483647);
          bundle.putInt("systemId", paramVCell.systemId);
        } 
      } else {
        try {
          GsmCellLocation gsmCellLocation = new GsmCellLocation();
          this();
          gsmCellLocation.setLacAndCid(paramVCell.lac, paramVCell.cid);
          gsmCellLocation.fillInNotifierBundle(bundle);
        } finally {
          Exception exception = null;
          bundle.putInt("lac", paramVCell.lac);
          bundle.putInt("cid", paramVCell.cid);
        } 
      } 
      return bundle;
    } 
    return null;
  }
  
  @SkipInject
  static class EnableLocationUpdates extends ReplaceCallingPkgMethodProxy {
    public EnableLocationUpdates() {
      super("enableLocationUpdates");
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      isFakeLocationEnable();
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
  
  @SkipInject
  static class GetAllCellInfo extends ReplaceCallingPkgMethodProxy {
    public GetAllCellInfo() {
      super("getAllCellInfo");
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Iterator<VCell> iterator;
      if (isFakeLocationEnable()) {
        List list = VirtualLocationManager.get().getAllCell(getAppUserId(), getAppPkg());
        if (list != null) {
          param1Object = new ArrayList();
          iterator = list.iterator();
          while (iterator.hasNext())
            param1Object.add(MethodProxies.createCellInfo(iterator.next())); 
          return param1Object;
        } 
        return null;
      } 
      return super.call(param1Object, (Method)iterator, param1VarArgs);
    }
  }
  
  static class GetAllCellInfoUsingSubId extends ReplaceCallingPkgMethodProxy {
    public GetAllCellInfoUsingSubId() {
      super("getAllCellInfoUsingSubId");
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return isFakeLocationEnable() ? null : super.call(param1Object, param1Method, param1VarArgs);
    }
  }
  
  @SkipInject
  static class GetAvailableNetworks extends ReplaceCallingPkgMethodProxy {
    public GetAvailableNetworks() {
      super("getAvailableNetworks");
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      isFakeLocationEnable();
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
  
  @SkipInject
  static class GetCellLocation extends ReplaceCallingPkgMethodProxy {
    public GetCellLocation() {
      super("getCellLocation");
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (isFakeLocationEnable()) {
        param1Object = VirtualLocationManager.get().getCell(getAppUserId(), getAppPkg());
        return (param1Object != null) ? MethodProxies.getCellLocationInternal((VCell)param1Object) : null;
      } 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
  
  static class GetDeviceId extends ReplaceLastPkgMethodProxy {
    public GetDeviceId() {
      super("getDeviceId");
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      VDeviceConfig vDeviceConfig = getDeviceConfig();
      if (vDeviceConfig.enable) {
        String str = vDeviceConfig.deviceId;
        if (!TextUtils.isEmpty(str))
          return str; 
      } 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
  
  static class GetImeiForSlot extends GetDeviceId {
    public String getMethodName() {
      return "getImeiForSlot";
    }
  }
  
  static class GetMeidForSlot extends GetDeviceId {
    public String getMethodName() {
      return "getMeidForSlot";
    }
  }
  
  @SkipInject
  static class GetNeighboringCellInfo extends ReplaceCallingPkgMethodProxy {
    public GetNeighboringCellInfo() {
      super("getNeighboringCellInfo");
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (isFakeLocationEnable()) {
        List list = VirtualLocationManager.get().getNeighboringCell(getAppUserId(), getAppPkg());
        if (list != null) {
          param1Object = new ArrayList();
          for (VCell vCell : list) {
            NeighboringCellInfo neighboringCellInfo = new NeighboringCellInfo();
            NeighboringCellInfo.mLac.set(neighboringCellInfo, vCell.lac);
            NeighboringCellInfo.mCid.set(neighboringCellInfo, vCell.cid);
            NeighboringCellInfo.mRssi.set(neighboringCellInfo, 6);
            param1Object.add(neighboringCellInfo);
          } 
          return param1Object;
        } 
        return null;
      } 
      return super.call(param1Object, param1Method, (Object[])vCell);
    }
  }
  
  @SkipInject
  static class RequestCellInfoUpdate extends ReplaceCallingPkgMethodProxy {
    public RequestCellInfoUpdate() {
      super("requestCellInfoUpdate");
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (isFakeLocationEnable()) {
        param1Object = VirtualLocationManager.get().getCell(getAppUserId(), getAppPkg());
        return (param1Object != null) ? MethodProxies.getCellLocationInternal((VCell)param1Object) : null;
      } 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\telephony\MethodProxies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */