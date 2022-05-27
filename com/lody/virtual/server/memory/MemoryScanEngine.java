package com.lody.virtual.server.memory;

import com.lody.virtual.helper.utils.VLog;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MemoryScanEngine {
  private static final int PAGE = 4096;
  
  private List<Match> matches;
  
  private ProcessMemory memory;
  
  private int pid;
  
  private List<MappedMemoryRegion> regions;
  
  public MemoryScanEngine(int paramInt) throws IOException {
    this.pid = paramInt;
    this.memory = new ProcessMemory(paramInt);
    updateMemoryLayout();
  }
  
  private List<Match> matchBytes(MappedMemoryRegion paramMappedMemoryRegion, long paramLong, byte[] paramArrayOfbyte1, int paramInt, byte[] paramArrayOfbyte2) {
    LinkedList<Match> linkedList = new LinkedList();
    int i = paramArrayOfbyte2.length;
    for (byte b = 0; b < paramInt; b += 2) {
      byte b1 = 0;
      while (true) {
        if (b1 < i) {
          int j = b1 + b;
          if (j < paramInt) {
            if (paramArrayOfbyte1[j] != paramArrayOfbyte2[b1]) {
              b1 = 0;
              break;
            } 
            b1++;
            continue;
          } 
        } 
        b1 = 1;
        break;
      } 
      if (b1 != 0)
        linkedList.add(new Match(paramMappedMemoryRegion, paramLong + b, i)); 
    } 
    return linkedList;
  }
  
  public void close() {
    try {
      this.memory.close();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  public List<Match> getMatches() {
    return this.matches;
  }
  
  public void modify(Match paramMatch, MemoryValue paramMemoryValue) throws IOException {
    this.memory.write(paramMatch.address, paramMemoryValue.toBytes());
  }
  
  public void modifyAll(MemoryValue paramMemoryValue) throws IOException {
    Iterator<Match> iterator = this.matches.iterator();
    while (iterator.hasNext())
      modify(iterator.next(), paramMemoryValue); 
  }
  
  public void search(MemoryValue paramMemoryValue) throws IOException {
    this.matches = new LinkedList<Match>();
    byte[] arrayOfByte1 = new byte[4096];
    byte[] arrayOfByte2 = paramMemoryValue.toBytes();
    for (MappedMemoryRegion mappedMemoryRegion : this.regions) {
      long l1 = mappedMemoryRegion.startAddress;
      long l2 = mappedMemoryRegion.endAddress;
      while (l1 < l2) {
        int i = (int)(l2 - l1);
        try {
          i = Math.min(4096, i);
          i = this.memory.read(l1, arrayOfByte1, i);
          this.matches.addAll(matchBytes(mappedMemoryRegion, l1, arrayOfByte1, i, arrayOfByte2));
          l1 += 4096L;
        } catch (IOException iOException) {
          String str = getClass().getSimpleName();
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Unable to read region : ");
          stringBuilder.append(mappedMemoryRegion.description);
          VLog.e(str, stringBuilder.toString());
          break;
        } 
      } 
    } 
  }
  
  public void updateMemoryLayout() {
    try {
      this.regions = MemoryRegionParser.getMemoryRegions(this.pid);
      return;
    } catch (IOException iOException) {
      throw new IllegalStateException(iOException);
    } 
  }
  
  public class Match {
    long address;
    
    int len;
    
    MappedMemoryRegion region;
    
    public Match(MappedMemoryRegion param1MappedMemoryRegion, long param1Long, int param1Int) {
      this.region = param1MappedMemoryRegion;
      this.address = param1Long;
      this.len = param1Int;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\memory\MemoryScanEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */