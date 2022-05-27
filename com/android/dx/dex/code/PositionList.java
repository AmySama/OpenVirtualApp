package com.android.dx.dex.code;

import com.android.dx.rop.code.SourcePosition;
import com.android.dx.util.FixedSizeList;

public final class PositionList extends FixedSizeList {
  public static final PositionList EMPTY = new PositionList(0);
  
  public static final int IMPORTANT = 3;
  
  public static final int LINES = 2;
  
  public static final int NONE = 1;
  
  public PositionList(int paramInt) {
    super(paramInt);
  }
  
  public static PositionList make(DalvInsnList paramDalvInsnList, int paramInt) {
    if (paramInt != 1) {
      if (paramInt == 2 || paramInt == 3) {
        SourcePosition sourcePosition1 = SourcePosition.NO_INFO;
        int i = paramDalvInsnList.size();
        Entry[] arrayOfEntry = new Entry[i];
        boolean bool1 = false;
        SourcePosition sourcePosition2 = sourcePosition1;
        byte b = 0;
        int j = 0;
        boolean bool2;
        for (bool2 = false; b < i; bool2 = bool) {
          boolean bool;
          int k;
          SourcePosition sourcePosition;
          DalvInsn dalvInsn = paramDalvInsnList.get(b);
          if (dalvInsn instanceof CodeAddress) {
            bool = true;
            k = j;
            sourcePosition = sourcePosition2;
          } else {
            SourcePosition sourcePosition3 = dalvInsn.getPosition();
            k = j;
            sourcePosition = sourcePosition2;
            bool = bool2;
            if (!sourcePosition3.equals(sourcePosition1))
              if (sourcePosition3.sameLine(sourcePosition2)) {
                k = j;
                sourcePosition = sourcePosition2;
                bool = bool2;
              } else if (paramInt == 3 && !bool2) {
                k = j;
                sourcePosition = sourcePosition2;
                bool = bool2;
              } else {
                arrayOfEntry[j] = new Entry(dalvInsn.getAddress(), sourcePosition3);
                k = j + 1;
                sourcePosition = sourcePosition3;
                bool = false;
              }  
          } 
          b++;
          j = k;
          sourcePosition2 = sourcePosition;
        } 
        PositionList positionList = new PositionList(j);
        for (paramInt = bool1; paramInt < j; paramInt++)
          positionList.set(paramInt, arrayOfEntry[paramInt]); 
        positionList.setImmutable();
        return positionList;
      } 
      throw new IllegalArgumentException("bogus howMuch");
    } 
    return EMPTY;
  }
  
  public Entry get(int paramInt) {
    return (Entry)get0(paramInt);
  }
  
  public void set(int paramInt, Entry paramEntry) {
    set0(paramInt, paramEntry);
  }
  
  public static class Entry {
    private final int address;
    
    private final SourcePosition position;
    
    public Entry(int param1Int, SourcePosition param1SourcePosition) {
      if (param1Int >= 0) {
        if (param1SourcePosition != null) {
          this.address = param1Int;
          this.position = param1SourcePosition;
          return;
        } 
        throw new NullPointerException("position == null");
      } 
      throw new IllegalArgumentException("address < 0");
    }
    
    public int getAddress() {
      return this.address;
    }
    
    public SourcePosition getPosition() {
      return this.position;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\PositionList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */