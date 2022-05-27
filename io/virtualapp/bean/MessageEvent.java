package io.virtualapp.bean;

public class MessageEvent {
  public static final int REFRESH_ACTIVITY_TO_INTEGRALCENTER = 4;
  
  public static final int REFRESH_APP_CREATE = 6;
  
  public static final int REFRESH_HOME_APP_LIST_EVENT = 1;
  
  public static final int REFRESH_MY_INTEGRAL_EVENT = 2;
  
  public static final int REFRESH_RENEW_EVENT = 3;
  
  public static final int REFRESH_REWARD_HINT = 5;
  
  private Object data;
  
  private int type;
  
  public MessageEvent(int paramInt) {
    this.type = paramInt;
  }
  
  public MessageEvent(int paramInt, Object paramObject) {
    this.type = paramInt;
    this.data = paramObject;
  }
  
  public Object getData() {
    return this.data;
  }
  
  public int getType() {
    return this.type;
  }
  
  public void setData(Object paramObject) {
    this.data = paramObject;
  }
  
  public void setType(int paramInt) {
    this.type = paramInt;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\bean\MessageEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */