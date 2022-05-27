package com.lody.virtual.helper.compat;

import android.os.Build;

public class BuildCompat {
  private static ROMType sRomType;
  
  public static int getPreviewSDKInt() {
    if (Build.VERSION.SDK_INT >= 23)
      try {
        return Build.VERSION.PREVIEW_SDK_INT;
      } finally {
        Exception exception;
      }  
    return 0;
  }
  
  public static ROMType getROMType() {
    if (sRomType == null)
      if (isEMUI()) {
        sRomType = ROMType.EMUI;
      } else if (isMIUI()) {
        sRomType = ROMType.MIUI;
      } else if (isFlyme()) {
        sRomType = ROMType.FLYME;
      } else if (isColorOS()) {
        sRomType = ROMType.COLOR_OS;
      } else if (is360UI()) {
        sRomType = ROMType._360;
      } else if (isLetv()) {
        sRomType = ROMType.LETV;
      } else if (isVivo()) {
        sRomType = ROMType.VIVO;
      } else if (isSamsung()) {
        sRomType = ROMType.SAMSUNG;
      } else {
        sRomType = ROMType.OTHER;
      }  
    return sRomType;
  }
  
  public static boolean is360UI() {
    boolean bool;
    String str = SystemPropertiesCompat.get("ro.build.uiversion");
    if (str != null && str.toUpperCase().contains("360UI")) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isColorOS() {
    return (SystemPropertiesCompat.isExist("ro.build.version.opporom") || SystemPropertiesCompat.isExist("ro.rom.different.version"));
  }
  
  public static boolean isEMUI() {
    boolean bool = Build.DISPLAY.toUpperCase().startsWith("EMUI");
    boolean bool1 = true;
    if (bool)
      return true; 
    String str = SystemPropertiesCompat.get("ro.build.version.emui");
    if (str == null || !str.contains("EmotionUI"))
      bool1 = false; 
    return bool1;
  }
  
  public static boolean isFlyme() {
    return Build.DISPLAY.toLowerCase().contains("flyme");
  }
  
  public static boolean isLetv() {
    return Build.MANUFACTURER.equalsIgnoreCase("Letv");
  }
  
  public static boolean isMIUI() {
    boolean bool = false;
    if (SystemPropertiesCompat.getInt("ro.miui.ui.version.code", 0) > 0)
      bool = true; 
    return bool;
  }
  
  public static boolean isOreo() {
    return (Build.VERSION.SDK_INT > 25 || (Build.VERSION.SDK_INT == 25 && getPreviewSDKInt() > 0));
  }
  
  public static boolean isPie() {
    return (Build.VERSION.SDK_INT > 27 || (Build.VERSION.SDK_INT == 27 && getPreviewSDKInt() > 0));
  }
  
  public static boolean isQ() {
    return (Build.VERSION.SDK_INT > 28 || (Build.VERSION.SDK_INT == 28 && getPreviewSDKInt() > 0));
  }
  
  public static boolean isR() {
    return (Build.VERSION.SDK_INT > 29 || (Build.VERSION.SDK_INT == 29 && getPreviewSDKInt() > 0));
  }
  
  public static boolean isS() {
    return (Build.VERSION.SDK_INT > 30 || (Build.VERSION.SDK_INT == 30 && getPreviewSDKInt() > 0));
  }
  
  public static boolean isSamsung() {
    return ("samsung".equalsIgnoreCase(Build.BRAND) || "samsung".equalsIgnoreCase(Build.MANUFACTURER));
  }
  
  public static boolean isVivo() {
    return SystemPropertiesCompat.isExist("ro.vivo.os.build.display.id");
  }
  
  public enum ROMType {
    COLOR_OS, EMUI, FLYME, LETV, MIUI, OTHER, SAMSUNG, VIVO, _360;
    
    static {
      COLOR_OS = new ROMType("COLOR_OS", 3);
      LETV = new ROMType("LETV", 4);
      VIVO = new ROMType("VIVO", 5);
      _360 = new ROMType("_360", 6);
      SAMSUNG = new ROMType("SAMSUNG", 7);
      ROMType rOMType = new ROMType("OTHER", 8);
      OTHER = rOMType;
      $VALUES = new ROMType[] { EMUI, MIUI, FLYME, COLOR_OS, LETV, VIVO, _360, SAMSUNG, rOMType };
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\BuildCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */