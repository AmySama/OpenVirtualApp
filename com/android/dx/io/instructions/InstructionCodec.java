package com.android.dx.io.instructions;

import com.android.dex.DexException;
import com.android.dx.io.IndexType;
import com.android.dx.io.OpcodeInfo;
import com.android.dx.util.Hex;
import java.io.EOFException;
import java.util.Arrays;

public enum InstructionCodec {
  FORMAT_00X {
    public DecodedInstruction decode(int param1Int, CodeInput param1CodeInput) throws EOFException {
      return new ZeroRegisterDecodedInstruction(this, param1Int, 0, null, 0, 0L);
    }
    
    public void encode(DecodedInstruction param1DecodedInstruction, CodeOutput param1CodeOutput) {
      param1CodeOutput.write(param1DecodedInstruction.getOpcodeUnit());
    }
  },
  FORMAT_10T,
  FORMAT_10X {
    public DecodedInstruction decode(int param1Int, CodeInput param1CodeInput) throws EOFException {
      return new ZeroRegisterDecodedInstruction(this, byte0(param1Int), 0, null, 0, byte1(param1Int));
    }
    
    public void encode(DecodedInstruction param1DecodedInstruction, CodeOutput param1CodeOutput) {
      param1CodeOutput.write(param1DecodedInstruction.getOpcodeUnit());
    }
  },
  FORMAT_11N,
  FORMAT_11X,
  FORMAT_12X {
    public DecodedInstruction decode(int param1Int, CodeInput param1CodeInput) throws EOFException {
      return new TwoRegisterDecodedInstruction(this, byte0(param1Int), 0, null, 0, 0L, nibble2(param1Int), nibble3(param1Int));
    }
    
    public void encode(DecodedInstruction param1DecodedInstruction, CodeOutput param1CodeOutput) {
      param1CodeOutput.write(codeUnit(param1DecodedInstruction.getOpcodeUnit(), makeByte(param1DecodedInstruction.getA(), param1DecodedInstruction.getB())));
    }
  },
  FORMAT_20BC,
  FORMAT_20T,
  FORMAT_21C,
  FORMAT_21H,
  FORMAT_21S,
  FORMAT_21T,
  FORMAT_22B,
  FORMAT_22C,
  FORMAT_22CS,
  FORMAT_22S,
  FORMAT_22T,
  FORMAT_22X,
  FORMAT_23X,
  FORMAT_30T,
  FORMAT_31C,
  FORMAT_31I,
  FORMAT_31T,
  FORMAT_32X,
  FORMAT_35C,
  FORMAT_35MI,
  FORMAT_35MS,
  FORMAT_3RC,
  FORMAT_3RMI,
  FORMAT_3RMS,
  FORMAT_45CC,
  FORMAT_4RCC,
  FORMAT_51L,
  FORMAT_FILL_ARRAY_DATA_PAYLOAD,
  FORMAT_PACKED_SWITCH_PAYLOAD,
  FORMAT_SPARSE_SWITCH_PAYLOAD;
  
  static {
    FORMAT_11N = new null("FORMAT_11N", 3);
    FORMAT_11X = new null("FORMAT_11X", 4);
    FORMAT_10T = new null("FORMAT_10T", 5);
    FORMAT_20T = new null("FORMAT_20T", 6);
    FORMAT_20BC = new null("FORMAT_20BC", 7);
    FORMAT_22X = new null("FORMAT_22X", 8);
    FORMAT_21T = new null("FORMAT_21T", 9);
    FORMAT_21S = new null("FORMAT_21S", 10);
    FORMAT_21H = new null("FORMAT_21H", 11);
    FORMAT_21C = new null("FORMAT_21C", 12);
    FORMAT_23X = new null("FORMAT_23X", 13);
    FORMAT_22B = new null("FORMAT_22B", 14);
    FORMAT_22T = new null("FORMAT_22T", 15);
    FORMAT_22S = new null("FORMAT_22S", 16);
    FORMAT_22C = new null("FORMAT_22C", 17);
    FORMAT_22CS = new null("FORMAT_22CS", 18);
    FORMAT_30T = new null("FORMAT_30T", 19);
    FORMAT_32X = new null("FORMAT_32X", 20);
    FORMAT_31I = new null("FORMAT_31I", 21);
    FORMAT_31T = new null("FORMAT_31T", 22);
    FORMAT_31C = new null("FORMAT_31C", 23);
    FORMAT_35C = new null("FORMAT_35C", 24);
    FORMAT_35MS = new null("FORMAT_35MS", 25);
    FORMAT_35MI = new null("FORMAT_35MI", 26);
    FORMAT_3RC = new null("FORMAT_3RC", 27);
    FORMAT_3RMS = new null("FORMAT_3RMS", 28);
    FORMAT_3RMI = new null("FORMAT_3RMI", 29);
    FORMAT_51L = new null("FORMAT_51L", 30);
    FORMAT_45CC = new null("FORMAT_45CC", 31);
    FORMAT_4RCC = new null("FORMAT_4RCC", 32);
    FORMAT_PACKED_SWITCH_PAYLOAD = new null("FORMAT_PACKED_SWITCH_PAYLOAD", 33);
    FORMAT_SPARSE_SWITCH_PAYLOAD = new null("FORMAT_SPARSE_SWITCH_PAYLOAD", 34);
    null  = new null("FORMAT_FILL_ARRAY_DATA_PAYLOAD", 35);
    FORMAT_FILL_ARRAY_DATA_PAYLOAD = ;
    $VALUES = new InstructionCodec[] { 
        FORMAT_00X, FORMAT_10X, FORMAT_12X, FORMAT_11N, FORMAT_11X, FORMAT_10T, FORMAT_20T, FORMAT_20BC, FORMAT_22X, FORMAT_21T, 
        FORMAT_21S, FORMAT_21H, FORMAT_21C, FORMAT_23X, FORMAT_22B, FORMAT_22T, FORMAT_22S, FORMAT_22C, FORMAT_22CS, FORMAT_30T, 
        FORMAT_32X, FORMAT_31I, FORMAT_31T, FORMAT_31C, FORMAT_35C, FORMAT_35MS, FORMAT_35MI, FORMAT_3RC, FORMAT_3RMS, FORMAT_3RMI, 
        FORMAT_51L, FORMAT_45CC, FORMAT_4RCC, FORMAT_PACKED_SWITCH_PAYLOAD, FORMAT_SPARSE_SWITCH_PAYLOAD,  };
  }
  
  private static short asUnsignedUnit(int paramInt) {
    if ((0xFFFF0000 & paramInt) == 0)
      return (short)paramInt; 
    throw new IllegalArgumentException("bogus unsigned code unit");
  }
  
  private static int byte0(int paramInt) {
    return paramInt & 0xFF;
  }
  
  private static int byte1(int paramInt) {
    return paramInt >> 8 & 0xFF;
  }
  
  private static int byte2(int paramInt) {
    return paramInt >> 16 & 0xFF;
  }
  
  private static int byte3(int paramInt) {
    return paramInt >>> 24;
  }
  
  private static short codeUnit(int paramInt1, int paramInt2) {
    if ((paramInt1 & 0xFFFFFF00) == 0) {
      if ((paramInt2 & 0xFFFFFF00) == 0)
        return (short)(paramInt1 | paramInt2 << 8); 
      throw new IllegalArgumentException("bogus highByte");
    } 
    throw new IllegalArgumentException("bogus lowByte");
  }
  
  private static short codeUnit(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if ((paramInt1 & 0xFFFFFFF0) == 0) {
      if ((paramInt2 & 0xFFFFFFF0) == 0) {
        if ((paramInt3 & 0xFFFFFFF0) == 0) {
          if ((paramInt4 & 0xFFFFFFF0) == 0)
            return (short)(paramInt1 | paramInt2 << 4 | paramInt3 << 8 | paramInt4 << 12); 
          throw new IllegalArgumentException("bogus nibble3");
        } 
        throw new IllegalArgumentException("bogus nibble2");
      } 
      throw new IllegalArgumentException("bogus nibble1");
    } 
    throw new IllegalArgumentException("bogus nibble0");
  }
  
  private static DecodedInstruction decodeRegisterList(InstructionCodec paramInstructionCodec, int paramInt, CodeInput paramCodeInput) throws EOFException {
    StringBuilder stringBuilder;
    int i = byte0(paramInt);
    int j = nibble2(paramInt);
    int k = nibble3(paramInt);
    int m = paramCodeInput.read();
    int n = paramCodeInput.read();
    paramInt = nibble0(n);
    int i1 = nibble1(n);
    int i2 = nibble2(n);
    n = nibble3(n);
    IndexType indexType = OpcodeInfo.getIndexType(i);
    if (k != 0) {
      if (k != 1) {
        if (k != 2) {
          if (k != 3) {
            if (k != 4) {
              if (k == 5)
                return new FiveRegisterDecodedInstruction(paramInstructionCodec, i, m, indexType, 0, 0L, paramInt, i1, i2, n, j); 
              stringBuilder = new StringBuilder();
              stringBuilder.append("bogus registerCount: ");
              stringBuilder.append(Hex.uNibble(k));
              throw new DexException(stringBuilder.toString());
            } 
            return new FourRegisterDecodedInstruction((InstructionCodec)stringBuilder, i, m, indexType, 0, 0L, paramInt, i1, i2, n);
          } 
          return new ThreeRegisterDecodedInstruction((InstructionCodec)stringBuilder, i, m, indexType, 0, 0L, paramInt, i1, i2);
        } 
        return new TwoRegisterDecodedInstruction((InstructionCodec)stringBuilder, i, m, indexType, 0, 0L, paramInt, i1);
      } 
      return new OneRegisterDecodedInstruction((InstructionCodec)stringBuilder, i, m, indexType, 0, 0L, paramInt);
    } 
    return new ZeroRegisterDecodedInstruction((InstructionCodec)stringBuilder, i, m, indexType, 0, 0L);
  }
  
  private static DecodedInstruction decodeRegisterRange(InstructionCodec paramInstructionCodec, int paramInt, CodeInput paramCodeInput) throws EOFException {
    int i = byte0(paramInt);
    int j = byte1(paramInt);
    int k = paramCodeInput.read();
    paramInt = paramCodeInput.read();
    return new RegisterRangeDecodedInstruction(paramInstructionCodec, i, k, OpcodeInfo.getIndexType(i), 0, 0L, paramInt, j);
  }
  
  private static void encodeRegisterList(DecodedInstruction paramDecodedInstruction, CodeOutput paramCodeOutput) {
    paramCodeOutput.write(codeUnit(paramDecodedInstruction.getOpcode(), makeByte(paramDecodedInstruction.getE(), paramDecodedInstruction.getRegisterCount())), paramDecodedInstruction.getIndexUnit(), codeUnit(paramDecodedInstruction.getA(), paramDecodedInstruction.getB(), paramDecodedInstruction.getC(), paramDecodedInstruction.getD()));
  }
  
  private static void encodeRegisterRange(DecodedInstruction paramDecodedInstruction, CodeOutput paramCodeOutput) {
    paramCodeOutput.write(codeUnit(paramDecodedInstruction.getOpcode(), paramDecodedInstruction.getRegisterCount()), paramDecodedInstruction.getIndexUnit(), paramDecodedInstruction.getAUnit());
  }
  
  private static int makeByte(int paramInt1, int paramInt2) {
    if ((paramInt1 & 0xFFFFFFF0) == 0) {
      if ((paramInt2 & 0xFFFFFFF0) == 0)
        return paramInt1 | paramInt2 << 4; 
      throw new IllegalArgumentException("bogus highNibble");
    } 
    throw new IllegalArgumentException("bogus lowNibble");
  }
  
  private static int nibble0(int paramInt) {
    return paramInt & 0xF;
  }
  
  private static int nibble1(int paramInt) {
    return paramInt >> 4 & 0xF;
  }
  
  private static int nibble2(int paramInt) {
    return paramInt >> 8 & 0xF;
  }
  
  private static int nibble3(int paramInt) {
    return paramInt >> 12 & 0xF;
  }
  
  private static short unit0(int paramInt) {
    return (short)paramInt;
  }
  
  private static short unit0(long paramLong) {
    return (short)(int)paramLong;
  }
  
  private static short unit1(int paramInt) {
    return (short)(paramInt >> 16);
  }
  
  private static short unit1(long paramLong) {
    return (short)(int)(paramLong >> 16L);
  }
  
  private static short unit2(long paramLong) {
    return (short)(int)(paramLong >> 32L);
  }
  
  private static short unit3(long paramLong) {
    return (short)(int)(paramLong >> 48L);
  }
  
  public abstract DecodedInstruction decode(int paramInt, CodeInput paramCodeInput) throws EOFException;
  
  public abstract void encode(DecodedInstruction paramDecodedInstruction, CodeOutput paramCodeOutput);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\instructions\InstructionCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */