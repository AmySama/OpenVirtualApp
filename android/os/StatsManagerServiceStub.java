package android.os;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import mirror.android.os.IStatsManagerService;

public class StatsManagerServiceStub extends BinderInvocationProxy {
  private static final String SERVER_NAME = "statsmanager";
  
  public StatsManagerServiceStub() {
    super(IStatsManagerService.Stub.asInterface, "statsmanager");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setDataFetchOperation", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("removeDataFetchOperation", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setActiveConfigsChangedOperation", new long[0]));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("removeActiveConfigsChangedOperation", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setBroadcastSubscriber", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("unsetBroadcastSubscriber", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getRegisteredExperimentIds", new long[0]));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getMetadata", new byte[0]));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getData", new byte[0]));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("addConfiguration", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("registerPullAtomCallback", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("unregisterPullAtomCallback", null));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\os\StatsManagerServiceStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */