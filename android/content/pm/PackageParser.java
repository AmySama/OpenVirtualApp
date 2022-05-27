package android.content.pm;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.os.Bundle;
import java.io.File;
import java.security.cert.CertificateException;
import java.util.ArrayList;

public class PackageParser {
  public static final int PARSE_COLLECT_CERTIFICATES = 32;
  
  public static final int PARSE_ENFORCE_CODE = 64;
  
  public static final int PARSE_IS_SYSTEM = 1;
  
  public static final int PARSE_IS_SYSTEM_DIR = 16;
  
  public static ApkLite parseApkLite(File paramFile, int paramInt) throws PackageParserException {
    throw new RuntimeException("Stub!");
  }
  
  public void setCallback(Callback paramCallback) {
    throw new RuntimeException("Stub!");
  }
  
  public static final class Activity extends Component<ActivityIntentInfo> {
    public ActivityInfo info;
  }
  
  public class ActivityIntentInfo extends IntentInfo {
    public PackageParser.Activity activity;
  }
  
  public static class ApkLite {
    public String codePath;
    
    public boolean coreApp;
    
    public boolean extractNativeLibs;
    
    public int installLocation;
    
    public boolean multiArch;
    
    public String packageName;
    
    public Signature[] signatures;
    
    public String splitName;
    
    public boolean use32bitAbi;
    
    public int versionCode;
  }
  
  public static class Builder {
    private Signature[] mSignatures;
    
    public PackageParser.SigningDetails build() throws CertificateException {
      return new PackageParser.SigningDetails();
    }
    
    public Builder setSignatures(Signature[] param1ArrayOfSignature) {
      this.mSignatures = param1ArrayOfSignature;
      return this;
    }
  }
  
  public static interface Callback {}
  
  public static final class CallbackImpl implements Callback {
    public CallbackImpl(PackageManager param1PackageManager) {
      throw new RuntimeException("Stub!");
    }
  }
  
  public static class Component<II extends IntentInfo> {
    public String className;
    
    public ArrayList<II> intents;
    
    public Bundle metaData;
    
    public PackageParser.Package owner;
    
    public ComponentName getComponentName() {
      return null;
    }
  }
  
  public final class Instrumentation extends Component<IntentInfo> {
    public InstrumentationInfo info;
  }
  
  public static class IntentInfo extends IntentFilter {
    public int banner;
    
    public boolean hasDefault;
    
    public int icon;
    
    public int labelRes;
    
    public int logo;
    
    public CharSequence nonLocalizedLabel;
  }
  
  public class Package {
    public final ArrayList<PackageParser.Activity> activities = new ArrayList<PackageParser.Activity>(0);
    
    public ApplicationInfo applicationInfo;
    
    public ArrayList<ConfigurationInfo> configPreferences = null;
    
    public final ArrayList<PackageParser.Instrumentation> instrumentation = new ArrayList<PackageParser.Instrumentation>(0);
    
    public Bundle mAppMetaData;
    
    public Object mExtras;
    
    public int mPreferredOrder;
    
    public String mSharedUserId;
    
    public int mSharedUserLabel;
    
    public Signature[] mSignatures;
    
    public PackageParser.SigningDetails mSigningDetails;
    
    public int mVersionCode;
    
    public String mVersionName;
    
    public String packageName;
    
    public final ArrayList<PackageParser.PermissionGroup> permissionGroups = new ArrayList<PackageParser.PermissionGroup>(0);
    
    public final ArrayList<PackageParser.Permission> permissions = new ArrayList<PackageParser.Permission>(0);
    
    public final ArrayList<PackageParser.Provider> providers = new ArrayList<PackageParser.Provider>(0);
    
    public final ArrayList<PackageParser.Activity> receivers = new ArrayList<PackageParser.Activity>(0);
    
    public ArrayList<FeatureInfo> reqFeatures = null;
    
    public final ArrayList<String> requestedPermissions = new ArrayList<String>();
    
    public final ArrayList<PackageParser.Service> services = new ArrayList<PackageParser.Service>(0);
    
    public boolean use32bitAbi;
    
    public ArrayList<String> usesLibraries;
    
    public ArrayList<String> usesOptionalLibraries;
  }
  
  public static class PackageParserException extends Exception {
    public final int error;
    
    public PackageParserException(int param1Int, String param1String) {
      super(param1String);
      this.error = param1Int;
    }
    
    public PackageParserException(int param1Int, String param1String, Throwable param1Throwable) {
      super(param1String, param1Throwable);
      this.error = param1Int;
    }
  }
  
  public final class Permission extends Component<IntentInfo> {
    public PermissionInfo info;
  }
  
  public final class PermissionGroup extends Component<IntentInfo> {
    public PermissionGroupInfo info;
  }
  
  public final class Provider extends Component<ProviderIntentInfo> {
    public ProviderInfo info;
  }
  
  public class ProviderIntentInfo extends IntentInfo {
    public PackageParser.Provider provider;
  }
  
  public final class Service extends Component<ServiceIntentInfo> {
    public ServiceInfo info;
  }
  
  public class ServiceIntentInfo extends IntentInfo {
    public PackageParser.Service service;
  }
  
  public static final class SigningDetails {
    public static final SigningDetails UNKNOWN;
    
    public Signature[] signatures;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\content\pm\PackageParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */