package com.tencent.lbssearch.object.param;

import android.text.TextUtils;
import com.tencent.lbssearch.a.a;
import com.tencent.lbssearch.a.b.d;

public class SuggestionParam implements ParamObject {
  private static final String FILTER = "filter";
  
  private static final String KEYWORD = "keyword";
  
  private static final String REGION = "region";
  
  private String filter;
  
  private String keyword;
  
  private String region;
  
  public d buildParameters() {
    d d = new d();
    if (!TextUtils.isEmpty(this.keyword))
      d.b("keyword", this.keyword); 
    if (!TextUtils.isEmpty(this.region))
      d.b("region", this.region); 
    if (!TextUtils.isEmpty(this.filter))
      d.b("filter", this.filter); 
    return d;
  }
  
  public boolean checkParams() {
    return !TextUtils.isEmpty(this.keyword);
  }
  
  public SuggestionParam filter(String... paramVarArgs) {
    this.filter = a.a(paramVarArgs);
    return this;
  }
  
  public SuggestionParam keyword(String paramString) {
    this.keyword = paramString;
    return this;
  }
  
  public SuggestionParam region(String paramString) {
    this.region = paramString;
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\param\SuggestionParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */