package com.lody.virtual.client.hook.proxies.job;

import android.app.job.JobInfo;
import android.app.job.JobWorkItem;
import android.os.Build;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.ipc.VJobScheduler;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.compat.JobWorkItemCompat;
import com.lody.virtual.os.VUserHandle;
import java.lang.reflect.Method;
import java.util.List;
import mirror.android.app.job.IJobScheduler;
import mirror.android.content.pm.ParceledListSlice;

public class JobServiceStub extends BinderInvocationProxy {
  public JobServiceStub() {
    super(IJobScheduler.Stub.asInterface, "jobscheduler");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy(new schedule());
    addMethodProxy(new getAllPendingJobs());
    addMethodProxy(new cancelAll());
    addMethodProxy(new cancel());
    if (Build.VERSION.SDK_INT >= 24)
      addMethodProxy(new getPendingJob()); 
    if (Build.VERSION.SDK_INT >= 26)
      addMethodProxy(new enqueue()); 
  }
  
  private class cancel extends MethodProxy {
    private cancel() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      int i = ((Integer)param1VarArgs[0]).intValue();
      VJobScheduler.get().cancel(i);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "cancel";
    }
  }
  
  private class cancelAll extends MethodProxy {
    private cancelAll() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      VJobScheduler.get().cancelAll();
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "cancelAll";
    }
  }
  
  private class enqueue extends MethodProxy {
    private enqueue() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      JobWorkItem jobWorkItem = JobWorkItemCompat.redirect(VUserHandle.myUserId(), (JobWorkItem)param1VarArgs[1], getAppPkg());
      return Integer.valueOf(VJobScheduler.get().enqueue((JobInfo)param1Object, jobWorkItem));
    }
    
    public String getMethodName() {
      return "enqueue";
    }
  }
  
  private class getAllPendingJobs extends MethodProxy {
    private getAllPendingJobs() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      List list = VJobScheduler.get().getAllPendingJobs();
      if (list == null)
        return null; 
      param1Object = list;
      if (BuildCompat.isQ())
        param1Object = ParceledListSlice.ctorQ.newInstance(new Object[] { list }); 
      return param1Object;
    }
    
    public String getMethodName() {
      return "getAllPendingJobs";
    }
  }
  
  private class getPendingJob extends MethodProxy {
    private getPendingJob() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      int i = ((Integer)param1VarArgs[0]).intValue();
      return VJobScheduler.get().getPendingJob(i);
    }
    
    public String getMethodName() {
      return "getPendingJob";
    }
  }
  
  private class schedule extends MethodProxy {
    private schedule() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      return Integer.valueOf(VJobScheduler.get().schedule((JobInfo)param1Object));
    }
    
    public String getMethodName() {
      return "schedule";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\job\JobServiceStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */