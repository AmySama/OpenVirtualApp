package io.virtualapp.home.platform;

import android.content.pm.PackageInfo;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class PlatformInfo {
  private final Set<String> platformPkgs;
  
  public PlatformInfo(String... paramVarArgs) {
    HashSet<String> hashSet = new HashSet();
    this.platformPkgs = hashSet;
    Collections.addAll(hashSet, paramVarArgs);
  }
  
  public abstract boolean relyOnPlatform(PackageInfo paramPackageInfo);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\platform\PlatformInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */