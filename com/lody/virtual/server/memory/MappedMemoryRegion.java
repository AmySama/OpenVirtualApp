package com.lody.virtual.server.memory;

public class MappedMemoryRegion {
  public final String description;
  
  public final long endAddress;
  
  public final FileMapping fileMapInfo;
  
  public final boolean isExecutable;
  
  public final boolean isReadable;
  
  public final boolean isShared;
  
  public final boolean isWritable;
  
  public final long startAddress;
  
  public MappedMemoryRegion(long paramLong1, long paramLong2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, long paramLong3, long paramLong4, long paramLong5, long paramLong6, String paramString) {
    FileMapping fileMapping;
    this.startAddress = paramLong1;
    this.endAddress = paramLong2;
    this.isReadable = paramBoolean1;
    this.isWritable = paramBoolean2;
    this.isExecutable = paramBoolean3;
    this.isShared = paramBoolean4;
    if (paramLong6 == 0L) {
      fileMapping = null;
    } else {
      fileMapping = new FileMapping(paramLong3, paramLong4, paramLong5, paramLong6);
    } 
    this.fileMapInfo = fileMapping;
    this.description = paramString;
  }
  
  public boolean isMappedFromFile() {
    boolean bool;
    if (this.fileMapInfo != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static class FileMapping {
    public final long inode;
    
    public final long majorDeviceNumber;
    
    public final long minorDeviceNumber;
    
    public final long offset;
    
    public FileMapping(long param1Long1, long param1Long2, long param1Long3, long param1Long4) {
      this.offset = param1Long1;
      this.majorDeviceNumber = param1Long2;
      this.minorDeviceNumber = param1Long3;
      this.inode = param1Long4;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\memory\MappedMemoryRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */