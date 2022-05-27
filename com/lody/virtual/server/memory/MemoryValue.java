package com.lody.virtual.server.memory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class MemoryValue {
  private static final ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;
  
  public abstract byte[] toBytes();
  
  public static class INT2 extends MemoryValue {
    private short val;
    
    public INT2(short param1Short) {
      this.val = (short)param1Short;
    }
    
    public byte[] toBytes() {
      return ByteBuffer.allocate(2).putShort(this.val).order(MemoryValue.BYTE_ORDER).array();
    }
  }
  
  public static class INT4 extends MemoryValue {
    private int val;
    
    public INT4(int param1Int) {
      this.val = param1Int;
    }
    
    public byte[] toBytes() {
      return ByteBuffer.allocate(4).order(MemoryValue.BYTE_ORDER).putInt(this.val).array();
    }
  }
  
  public static class INT8 extends MemoryValue {
    private long val;
    
    public INT8(long param1Long) {
      this.val = param1Long;
    }
    
    public byte[] toBytes() {
      return ByteBuffer.allocate(8).order(MemoryValue.BYTE_ORDER).putLong(this.val).array();
    }
  }
  
  public enum ValueType {
    INT2, INT4, INT8;
    
    static {
      ValueType valueType = new ValueType("INT8", 2);
      INT8 = valueType;
      $VALUES = new ValueType[] { INT2, INT4, valueType };
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\memory\MemoryValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */