package com.lody.virtual.client.hook.proxies.content;

import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.annotations.LogInvocation;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import mirror.android.content.ContentResolver;
import mirror.android.content.IContentService;

@Inject(MethodProxies.class)
@LogInvocation
public class ContentServiceStub extends BinderInvocationProxy {
  private static final String TAG = ContentServiceStub.class.getSimpleName();
  
  public ContentServiceStub() {
    super(IContentService.Stub.asInterface, "content");
  }
  
  public void inject() throws Throwable {
    super.inject();
    ContentResolver.sContentService.set(((BinderInvocationStub)getInvocationStub()).getProxyInterface());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\content\ContentServiceStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */