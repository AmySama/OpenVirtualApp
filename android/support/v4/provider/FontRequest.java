package android.support.v4.provider;

import android.support.v4.util.Preconditions;
import android.util.Base64;
import java.util.List;

public final class FontRequest {
  private final List<List<byte[]>> mCertificates;
  
  private final int mCertificatesArray;
  
  private final String mIdentifier;
  
  private final String mProviderAuthority;
  
  private final String mProviderPackage;
  
  private final String mQuery;
  
  public FontRequest(String paramString1, String paramString2, String paramString3, int paramInt) {
    boolean bool;
    this.mProviderAuthority = (String)Preconditions.checkNotNull(paramString1);
    this.mProviderPackage = (String)Preconditions.checkNotNull(paramString2);
    this.mQuery = (String)Preconditions.checkNotNull(paramString3);
    this.mCertificates = null;
    if (paramInt != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    Preconditions.checkArgument(bool);
    this.mCertificatesArray = paramInt;
    StringBuilder stringBuilder = new StringBuilder(this.mProviderAuthority);
    stringBuilder.append("-");
    stringBuilder.append(this.mProviderPackage);
    stringBuilder.append("-");
    stringBuilder.append(this.mQuery);
    this.mIdentifier = stringBuilder.toString();
  }
  
  public FontRequest(String paramString1, String paramString2, String paramString3, List<List<byte[]>> paramList) {
    this.mProviderAuthority = (String)Preconditions.checkNotNull(paramString1);
    this.mProviderPackage = (String)Preconditions.checkNotNull(paramString2);
    this.mQuery = (String)Preconditions.checkNotNull(paramString3);
    this.mCertificates = (List<List<byte[]>>)Preconditions.checkNotNull(paramList);
    this.mCertificatesArray = 0;
    StringBuilder stringBuilder = new StringBuilder(this.mProviderAuthority);
    stringBuilder.append("-");
    stringBuilder.append(this.mProviderPackage);
    stringBuilder.append("-");
    stringBuilder.append(this.mQuery);
    this.mIdentifier = stringBuilder.toString();
  }
  
  public List<List<byte[]>> getCertificates() {
    return this.mCertificates;
  }
  
  public int getCertificatesArrayResId() {
    return this.mCertificatesArray;
  }
  
  public String getIdentifier() {
    return this.mIdentifier;
  }
  
  public String getProviderAuthority() {
    return this.mProviderAuthority;
  }
  
  public String getProviderPackage() {
    return this.mProviderPackage;
  }
  
  public String getQuery() {
    return this.mQuery;
  }
  
  public String toString() {
    StringBuilder stringBuilder1 = new StringBuilder();
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("FontRequest {mProviderAuthority: ");
    stringBuilder2.append(this.mProviderAuthority);
    stringBuilder2.append(", mProviderPackage: ");
    stringBuilder2.append(this.mProviderPackage);
    stringBuilder2.append(", mQuery: ");
    stringBuilder2.append(this.mQuery);
    stringBuilder2.append(", mCertificates:");
    stringBuilder1.append(stringBuilder2.toString());
    for (byte b = 0; b < this.mCertificates.size(); b++) {
      stringBuilder1.append(" [");
      List<byte[]> list = this.mCertificates.get(b);
      for (byte b1 = 0; b1 < list.size(); b1++) {
        stringBuilder1.append(" \"");
        stringBuilder1.append(Base64.encodeToString(list.get(b1), 0));
        stringBuilder1.append("\"");
      } 
      stringBuilder1.append(" ]");
    } 
    stringBuilder1.append("}");
    stringBuilder2 = new StringBuilder();
    stringBuilder2.append("mCertificatesArray: ");
    stringBuilder2.append(this.mCertificatesArray);
    stringBuilder1.append(stringBuilder2.toString());
    return stringBuilder1.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\provider\FontRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */