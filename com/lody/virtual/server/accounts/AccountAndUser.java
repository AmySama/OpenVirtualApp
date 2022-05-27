package com.lody.virtual.server.accounts;

import android.accounts.Account;

public class AccountAndUser {
  public Account account;
  
  public int userId;
  
  public AccountAndUser(Account paramAccount, int paramInt) {
    this.account = paramAccount;
    this.userId = paramInt;
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = true;
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof AccountAndUser))
      return false; 
    paramObject = paramObject;
    if (!this.account.equals(((AccountAndUser)paramObject).account) || this.userId != ((AccountAndUser)paramObject).userId)
      bool = false; 
    return bool;
  }
  
  public int hashCode() {
    return this.account.hashCode() + this.userId;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.account.toString());
    stringBuilder.append(" u");
    stringBuilder.append(this.userId);
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\accounts\AccountAndUser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */