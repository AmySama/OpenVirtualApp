package com.lody.virtual.client.core;

import android.os.Build;
import android.os.StatsManagerServiceStub;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.instruments.InstrumentationVirtualApp;
import com.lody.virtual.client.hook.proxies.accessibility.AccessibilityManagerStub;
import com.lody.virtual.client.hook.proxies.account.AccountManagerStub;
import com.lody.virtual.client.hook.proxies.alarm.AlarmManagerStub;
import com.lody.virtual.client.hook.proxies.am.ActivityManagerStub;
import com.lody.virtual.client.hook.proxies.am.HCallbackStub;
import com.lody.virtual.client.hook.proxies.app.ActivityClientControllerStub;
import com.lody.virtual.client.hook.proxies.appops.AppOpsManagerStub;
import com.lody.virtual.client.hook.proxies.appops.FlymePermissionServiceStub;
import com.lody.virtual.client.hook.proxies.appops.SmtOpsManagerStub;
import com.lody.virtual.client.hook.proxies.appwidget.AppWidgetManagerStub;
import com.lody.virtual.client.hook.proxies.atm.ActivityTaskManagerStub;
import com.lody.virtual.client.hook.proxies.audio.AudioManagerStub;
import com.lody.virtual.client.hook.proxies.backup.BackupManagerStub;
import com.lody.virtual.client.hook.proxies.battery_stats.BatteryStatsHub;
import com.lody.virtual.client.hook.proxies.bluetooth.BluetoothStub;
import com.lody.virtual.client.hook.proxies.clipboard.ClipBoardStub;
import com.lody.virtual.client.hook.proxies.clipboard.SemClipBoardStub;
import com.lody.virtual.client.hook.proxies.connectivity.ConnectivityStub;
import com.lody.virtual.client.hook.proxies.content.ContentServiceStub;
import com.lody.virtual.client.hook.proxies.content.integrity.AppIntegrityManagerStub;
import com.lody.virtual.client.hook.proxies.context_hub.ContextHubServiceStub;
import com.lody.virtual.client.hook.proxies.cross_profile.CrossProfileAppsStub;
import com.lody.virtual.client.hook.proxies.dev_identifiers_policy.DeviceIdentifiersPolicyServiceHub;
import com.lody.virtual.client.hook.proxies.device.DeviceIdleControllerStub;
import com.lody.virtual.client.hook.proxies.devicepolicy.DevicePolicyManagerStub;
import com.lody.virtual.client.hook.proxies.display.DisplayStub;
import com.lody.virtual.client.hook.proxies.dropbox.DropBoxManagerStub;
import com.lody.virtual.client.hook.proxies.fingerprint.FingerprintManagerStub;
import com.lody.virtual.client.hook.proxies.graphics.GraphicsStatsStub;
import com.lody.virtual.client.hook.proxies.imms.MmsStub;
import com.lody.virtual.client.hook.proxies.input.InputMethodManagerStub;
import com.lody.virtual.client.hook.proxies.isms.ISmsStub;
import com.lody.virtual.client.hook.proxies.isub.ISubStub;
import com.lody.virtual.client.hook.proxies.job.JobServiceStub;
import com.lody.virtual.client.hook.proxies.libcore.LibCoreStub;
import com.lody.virtual.client.hook.proxies.location.LocationManagerStub;
import com.lody.virtual.client.hook.proxies.media.router.MediaRouterServiceStub;
import com.lody.virtual.client.hook.proxies.media.session.SessionManagerStub;
import com.lody.virtual.client.hook.proxies.mount.MountServiceStub;
import com.lody.virtual.client.hook.proxies.network.NetworkManagementStub;
import com.lody.virtual.client.hook.proxies.network.TetheringConnectorStub;
import com.lody.virtual.client.hook.proxies.notification.NotificationManagerStub;
import com.lody.virtual.client.hook.proxies.permissionmgr.PermissionManagerStub;
import com.lody.virtual.client.hook.proxies.persistent_data_block.PersistentDataBlockServiceStub;
import com.lody.virtual.client.hook.proxies.phonesubinfo.PhoneSubInfoStub;
import com.lody.virtual.client.hook.proxies.pm.PackageManagerStub;
import com.lody.virtual.client.hook.proxies.power.PowerManagerStub;
import com.lody.virtual.client.hook.proxies.restriction.RestrictionStub;
import com.lody.virtual.client.hook.proxies.role.RoleManagerStub;
import com.lody.virtual.client.hook.proxies.search.SearchManagerStub;
import com.lody.virtual.client.hook.proxies.shortcut.ShortcutServiceStub;
import com.lody.virtual.client.hook.proxies.slice.SliceManagerStub;
import com.lody.virtual.client.hook.proxies.system.LockSettingsStub;
import com.lody.virtual.client.hook.proxies.system.SystemUpdateStub;
import com.lody.virtual.client.hook.proxies.system.WifiScannerStub;
import com.lody.virtual.client.hook.proxies.telecom.TelecomManagerStub;
import com.lody.virtual.client.hook.proxies.telephony.HwTelephonyStub;
import com.lody.virtual.client.hook.proxies.telephony.TelephonyRegistryStub;
import com.lody.virtual.client.hook.proxies.telephony.TelephonyStub;
import com.lody.virtual.client.hook.proxies.uri_grants.UriGrantsManagerStub;
import com.lody.virtual.client.hook.proxies.usage.UsageStatsManagerStub;
import com.lody.virtual.client.hook.proxies.user.UserManagerStub;
import com.lody.virtual.client.hook.proxies.vibrator.VibratorStub;
import com.lody.virtual.client.hook.proxies.view.AutoFillManagerStub;
import com.lody.virtual.client.hook.proxies.wallpaper.WallpaperManagerStub;
import com.lody.virtual.client.hook.proxies.wifi.WifiManagerStub;
import com.lody.virtual.client.hook.proxies.window.WindowManagerStub;
import com.lody.virtual.client.interfaces.IInjector;
import com.lody.virtual.helper.compat.BuildCompat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import mirror.android.os.IDeviceIdleController;
import mirror.com.android.internal.app.ISmtOpsService;
import mirror.com.android.internal.telephony.IHwTelephony;
import mirror.oem.IFlymePermissionService;

public final class InvocationStubManager {
  private static boolean sInit;
  
  private static InvocationStubManager sInstance = new InvocationStubManager();
  
  private final Map<Class<?>, IInjector> mInjectors = new HashMap<Class<?>, IInjector>(20);
  
  public static InvocationStubManager getInstance() {
    return sInstance;
  }
  
  private void injectInternal() throws Throwable {
    if (VirtualCore.get().isMainProcess())
      return; 
    if (VirtualCore.get().isServerProcess()) {
      addInjector((IInjector)new ActivityManagerStub());
      return;
    } 
    if (VirtualCore.get().isVAppProcess()) {
      addInjector((IInjector)new LibCoreStub());
      addInjector((IInjector)new ActivityManagerStub());
      addInjector((IInjector)new PackageManagerStub());
      addInjector((IInjector)HCallbackStub.getDefault());
      addInjector((IInjector)new ISmsStub());
      addInjector((IInjector)new ISubStub());
      addInjector((IInjector)new DropBoxManagerStub());
      addInjector((IInjector)new NotificationManagerStub());
      addInjector((IInjector)new LocationManagerStub());
      addInjector((IInjector)new WindowManagerStub());
      addInjector((IInjector)new ClipBoardStub());
      addInjector((IInjector)new SemClipBoardStub());
      addInjector((IInjector)new MountServiceStub());
      addInjector((IInjector)new BackupManagerStub());
      addInjector((IInjector)new TelephonyStub());
      addInjector((IInjector)new AccessibilityManagerStub());
      if (BuildCompat.isOreo() && IHwTelephony.TYPE != null)
        addInjector((IInjector)new HwTelephonyStub()); 
      addInjector((IInjector)new TelephonyRegistryStub());
      addInjector((IInjector)new PhoneSubInfoStub());
      addInjector((IInjector)new PowerManagerStub());
      addInjector((IInjector)new AppWidgetManagerStub());
      addInjector((IInjector)new AccountManagerStub());
      addInjector((IInjector)new AudioManagerStub());
      addInjector((IInjector)new SearchManagerStub());
      addInjector((IInjector)new ContentServiceStub());
      addInjector((IInjector)new ConnectivityStub());
      addInjector((IInjector)new BluetoothStub());
      addInjector((IInjector)new VibratorStub());
      addInjector((IInjector)new WifiManagerStub());
      addInjector((IInjector)new ContextHubServiceStub());
      addInjector((IInjector)new UserManagerStub());
      addInjector((IInjector)new WallpaperManagerStub());
      addInjector((IInjector)new DisplayStub());
      addInjector((IInjector)new PersistentDataBlockServiceStub());
      addInjector((IInjector)new InputMethodManagerStub());
      addInjector((IInjector)new MmsStub());
      addInjector((IInjector)new SessionManagerStub());
      addInjector((IInjector)new JobServiceStub());
      addInjector((IInjector)new RestrictionStub());
      addInjector((IInjector)new TelecomManagerStub());
      addInjector((IInjector)new AlarmManagerStub());
      addInjector((IInjector)new AppOpsManagerStub());
      addInjector((IInjector)new MediaRouterServiceStub());
      if (ISmtOpsService.TYPE != null)
        addInjector((IInjector)new SmtOpsManagerStub()); 
      if (Build.VERSION.SDK_INT >= 22) {
        addInjector((IInjector)new GraphicsStatsStub());
        addInjector((IInjector)new UsageStatsManagerStub());
      } 
      if (Build.VERSION.SDK_INT >= 23) {
        addInjector((IInjector)new FingerprintManagerStub());
        addInjector((IInjector)new NetworkManagementStub());
      } 
      if (Build.VERSION.SDK_INT >= 24) {
        addInjector((IInjector)new WifiScannerStub());
        addInjector((IInjector)new ShortcutServiceStub());
        addInjector((IInjector)new DevicePolicyManagerStub());
        addInjector((IInjector)new BatteryStatsHub());
      } 
      if (BuildCompat.isOreo())
        addInjector((IInjector)new AutoFillManagerStub()); 
      if (BuildCompat.isPie()) {
        addInjector((IInjector)new SystemUpdateStub());
        addInjector((IInjector)new LockSettingsStub());
        addInjector((IInjector)new CrossProfileAppsStub());
        addInjector((IInjector)new SliceManagerStub());
      } 
      if (IFlymePermissionService.TYPE != null)
        addInjector((IInjector)new FlymePermissionServiceStub()); 
      if (BuildCompat.isQ()) {
        addInjector((IInjector)new ActivityTaskManagerStub());
        addInjector((IInjector)new DeviceIdentifiersPolicyServiceHub());
        addInjector((IInjector)new UriGrantsManagerStub());
        addInjector((IInjector)new RoleManagerStub());
      } 
      if (BuildCompat.isR()) {
        addInjector((IInjector)new PermissionManagerStub());
        addInjector((IInjector)new AppIntegrityManagerStub());
        addInjector((IInjector)new StatsManagerServiceStub());
        addInjector((IInjector)new TetheringConnectorStub());
      } 
      if (BuildCompat.isS())
        addInjector((IInjector)new ActivityClientControllerStub()); 
      if (IDeviceIdleController.TYPE != null)
        addInjector((IInjector)new DeviceIdleControllerStub()); 
      OemInjectManager.oemInject(this);
    } 
  }
  
  public void addInjector(IInjector paramIInjector) {
    this.mInjectors.put(paramIInjector.getClass(), paramIInjector);
  }
  
  public void checkAllEnv() {
    for (IInjector iInjector : this.mInjectors.values()) {
      if (iInjector.isEnvBad())
        try {
          iInjector.inject();
        } finally {
          iInjector = null;
        }  
    } 
  }
  
  public <T extends IInjector> void checkEnv(Class<T> paramClass) {
    paramClass = findInjector((Class)paramClass);
    if (paramClass != null && paramClass.isEnvBad())
      try {
        paramClass.inject();
      } finally {
        paramClass = null;
      }  
  }
  
  public <T extends IInjector> T findInjector(Class<T> paramClass) {
    return (T)this.mInjectors.get(paramClass);
  }
  
  public <T extends IInjector, H extends com.lody.virtual.client.hook.base.MethodInvocationStub> H getInvocationStub(Class<T> paramClass) {
    paramClass = findInjector((Class)paramClass);
    return (H)((paramClass instanceof MethodInvocationProxy) ? ((MethodInvocationProxy)paramClass).getInvocationStub() : null);
  }
  
  public void init() throws Throwable {
    if (!isInit()) {
      injectInternal();
      if (VirtualCore.get().isVAppProcess())
        addInjector((IInjector)InstrumentationVirtualApp.getDefault()); 
      sInit = true;
      return;
    } 
    throw new IllegalStateException("InvocationStubManager Has been initialized.");
  }
  
  void injectAll() throws Throwable {
    Iterator<IInjector> iterator = this.mInjectors.values().iterator();
    while (iterator.hasNext())
      ((IInjector)iterator.next()).inject(); 
  }
  
  public boolean isInit() {
    return sInit;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\core\InvocationStubManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */