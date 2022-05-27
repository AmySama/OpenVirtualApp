package com.android.dx.cf.code;

import com.android.dx.dex.DexOptions;
import com.android.dx.rop.code.LocalItem;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstCallSiteRef;
import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.cst.CstInterfaceMethodRef;
import com.android.dx.rop.cst.CstInvokeDynamic;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Prototype;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.Hex;
import java.util.ArrayList;

public class Simulator {
  private static final String LOCAL_MISMATCH_ERROR = "This is symptomatic of .class transformation tools that ignore local variable information.";
  
  private final BytecodeArray code;
  
  private final DexOptions dexOptions;
  
  private final LocalVariableList localVariables;
  
  private final Machine machine;
  
  private ConcreteMethod method;
  
  private final SimVisitor visitor;
  
  public Simulator(Machine paramMachine, ConcreteMethod paramConcreteMethod, DexOptions paramDexOptions) {
    if (paramMachine != null) {
      if (paramConcreteMethod != null) {
        if (paramDexOptions != null) {
          this.machine = paramMachine;
          this.code = paramConcreteMethod.getCode();
          this.method = paramConcreteMethod;
          this.localVariables = paramConcreteMethod.getLocalVariables();
          this.visitor = new SimVisitor();
          this.dexOptions = paramDexOptions;
          if (paramConcreteMethod.isDefaultOrStaticInterfaceMethod())
            checkInterfaceMethodDeclaration(paramConcreteMethod); 
          return;
        } 
        throw new NullPointerException("dexOptions == null");
      } 
      throw new NullPointerException("method == null");
    } 
    throw new NullPointerException("machine == null");
  }
  
  private void checkConstMethodHandleSupported(Constant paramConstant) throws SimException {
    if (!this.dexOptions.apiIsSupported(28))
      fail(String.format("invalid constant type %s requires --min-sdk-version >= %d (currently %d)", new Object[] { paramConstant.typeName(), Integer.valueOf(28), Integer.valueOf(this.dexOptions.minSdkVersion) })); 
  }
  
  private void checkInterfaceMethodDeclaration(ConcreteMethod paramConcreteMethod) {
    if (!this.dexOptions.apiIsSupported(24)) {
      String str;
      if (paramConcreteMethod.isStaticMethod()) {
        str = "static";
      } else {
        str = "default";
      } 
      warn(String.format("defining a %s interface method requires --min-sdk-version >= %d (currently %d) for interface methods: %s.%s", new Object[] { str, Integer.valueOf(24), Integer.valueOf(this.dexOptions.minSdkVersion), paramConcreteMethod.getDefiningClass().toHuman(), paramConcreteMethod.getNat().toHuman() }));
    } 
  }
  
  private void checkInvokeDynamicSupported(int paramInt) throws SimException {
    if (!this.dexOptions.apiIsSupported(26))
      fail(String.format("invalid opcode %02x - invokedynamic requires --min-sdk-version >= %d (currently %d)", new Object[] { Integer.valueOf(paramInt), Integer.valueOf(26), Integer.valueOf(this.dexOptions.minSdkVersion) })); 
  }
  
  private void checkInvokeInterfaceSupported(int paramInt, CstMethodRef paramCstMethodRef) {
    String str;
    if (paramInt == 185)
      return; 
    if (this.dexOptions.apiIsSupported(24))
      return; 
    boolean bool1 = this.dexOptions.allowAllInterfaceMethodInvokes;
    boolean bool2 = bool1;
    if (paramInt == 184)
      bool2 = bool1 & this.dexOptions.apiIsSupported(21); 
    if (paramInt == 184) {
      str = "static";
    } else {
      str = "default";
    } 
    if (bool2) {
      warn(String.format("invoking a %s interface method %s.%s strictly requires --min-sdk-version >= %d (experimental at current API level %d)", new Object[] { str, paramCstMethodRef.getDefiningClass().toHuman(), paramCstMethodRef.getNat().toHuman(), Integer.valueOf(24), Integer.valueOf(this.dexOptions.minSdkVersion) }));
    } else {
      fail(String.format("invoking a %s interface method %s.%s strictly requires --min-sdk-version >= %d (blocked at current API level %d)", new Object[] { str, paramCstMethodRef.getDefiningClass().toHuman(), paramCstMethodRef.getNat().toHuman(), Integer.valueOf(24), Integer.valueOf(this.dexOptions.minSdkVersion) }));
    } 
  }
  
  private void checkInvokeSignaturePolymorphic(int paramInt) {
    if (!this.dexOptions.apiIsSupported(26)) {
      fail(String.format("invoking a signature-polymorphic requires --min-sdk-version >= %d (currently %d)", new Object[] { Integer.valueOf(26), Integer.valueOf(this.dexOptions.minSdkVersion) }));
    } else if (paramInt != 182) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unsupported signature polymorphic invocation (");
      stringBuilder.append(ByteOps.opName(paramInt));
      stringBuilder.append(")");
      fail(stringBuilder.toString());
    } 
  }
  
  private void fail(String paramString) {
    throw new SimException(String.format("ERROR in %s.%s: %s", new Object[] { this.method.getDefiningClass().toHuman(), this.method.getNat().toHuman(), paramString }));
  }
  
  private static SimException illegalTos() {
    return new SimException("stack mismatch: illegal top-of-stack for opcode");
  }
  
  private static Type requiredArrayTypeFor(Type paramType1, Type paramType2) {
    if (paramType2 == Type.KNOWN_NULL) {
      if (paramType1.isReference()) {
        paramType1 = Type.KNOWN_NULL;
      } else {
        paramType1 = paramType1.getArrayType();
      } 
      return paramType1;
    } 
    return (paramType1 == Type.OBJECT && paramType2.isArray() && paramType2.getComponentType().isReference()) ? paramType2 : ((paramType1 == Type.BYTE && paramType2 == Type.BOOLEAN_ARRAY) ? Type.BOOLEAN_ARRAY : paramType1.getArrayType());
  }
  
  private void warn(String paramString) {
    paramString = String.format("WARNING in %s.%s: %s", new Object[] { this.method.getDefiningClass().toHuman(), this.method.getNat().toHuman(), paramString });
    this.dexOptions.err.println(paramString);
  }
  
  public int simulate(int paramInt, Frame paramFrame) {
    this.visitor.setFrame(paramFrame);
    return this.code.parseInstruction(paramInt, this.visitor);
  }
  
  public void simulate(ByteBlock paramByteBlock, Frame paramFrame) {
    int i = paramByteBlock.getEnd();
    this.visitor.setFrame(paramFrame);
    try {
      int j;
      for (j = paramByteBlock.getStart(); j < i; j += k) {
        int k = this.code.parseInstruction(j, this.visitor);
        this.visitor.setPreviousOffset(j);
      } 
      return;
    } catch (SimException simException) {
      paramFrame.annotate(simException);
      throw simException;
    } 
  }
  
  private class SimVisitor implements BytecodeArray.Visitor {
    private Frame frame = null;
    
    private final Machine machine = Simulator.this.machine;
    
    private int previousOffset;
    
    private void checkReturnType(Type param1Type) {
      Type type = this.machine.getPrototype().getReturnType();
      if (!Merger.isPossiblyAssignableFrom((TypeBearer)type, (TypeBearer)param1Type)) {
        Simulator simulator = Simulator.this;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("return type mismatch: prototype indicates ");
        stringBuilder.append(type.toHuman());
        stringBuilder.append(", but encountered type ");
        stringBuilder.append(param1Type.toHuman());
        simulator.fail(stringBuilder.toString());
      } 
    }
    
    public int getPreviousOffset() {
      return this.previousOffset;
    }
    
    public void setFrame(Frame param1Frame) {
      if (param1Frame != null) {
        this.frame = param1Frame;
        return;
      } 
      throw new NullPointerException("frame == null");
    }
    
    public void setPreviousOffset(int param1Int) {
      this.previousOffset = param1Int;
    }
    
    public void visitBranch(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      switch (param1Int1) {
        default:
          switch (param1Int1) {
            default:
              visitInvalid(param1Int1, param1Int2, param1Int3);
              return;
            case 198:
            case 199:
              this.machine.popArgs(this.frame, Type.OBJECT);
              break;
            case 200:
            case 201:
              break;
          } 
        case 167:
        case 168:
          this.machine.clearArgs();
          break;
        case 165:
        case 166:
          this.machine.popArgs(this.frame, Type.OBJECT, Type.OBJECT);
          break;
        case 159:
        case 160:
        case 161:
        case 162:
        case 163:
        case 164:
          this.machine.popArgs(this.frame, Type.INT, Type.INT);
          break;
        case 153:
        case 154:
        case 155:
        case 156:
        case 157:
        case 158:
          this.machine.popArgs(this.frame, Type.INT);
          break;
      } 
      this.machine.auxTargetArg(param1Int4);
      this.machine.run(this.frame, param1Int2, param1Int1);
    }
    
    public void visitConstant(int param1Int1, int param1Int2, int param1Int3, Constant param1Constant, int param1Int4) {
      CstMethodRef cstMethodRef;
      if (param1Int1 != 18 && param1Int1 != 19) {
        if (param1Int1 != 189) {
          if (param1Int1 != 197) {
            if (param1Int1 != 192 && param1Int1 != 193) {
              CstInvokeDynamic cstInvokeDynamic;
              CstCallSiteRef cstCallSiteRef1;
              Prototype prototype1;
              Prototype prototype2;
              CstCallSiteRef cstCallSiteRef2;
              CstMethodRef cstMethodRef1;
              Type type;
              boolean bool;
              switch (param1Int1) {
                default:
                  this.machine.clearArgs();
                  break;
                case 186:
                  Simulator.this.checkInvokeDynamicSupported(param1Int1);
                  cstInvokeDynamic = (CstInvokeDynamic)param1Constant;
                  prototype2 = cstInvokeDynamic.getPrototype();
                  this.machine.popArgs(this.frame, prototype2);
                  cstCallSiteRef1 = cstInvokeDynamic.addReference();
                  break;
                case 182:
                case 183:
                case 184:
                case 185:
                  cstCallSiteRef2 = cstCallSiteRef1;
                  if (cstCallSiteRef1 instanceof CstInterfaceMethodRef) {
                    cstMethodRef1 = ((CstInterfaceMethodRef)cstCallSiteRef1).toMethodRef();
                    Simulator.this.checkInvokeInterfaceSupported(param1Int1, cstMethodRef1);
                  } 
                  if (cstMethodRef1 instanceof CstMethodRef && cstMethodRef1.isSignaturePolymorphic())
                    Simulator.this.checkInvokeSignaturePolymorphic(param1Int1); 
                  if (param1Int1 == 184) {
                    bool = true;
                  } else {
                    bool = false;
                  } 
                  prototype1 = cstMethodRef1.getPrototype(bool);
                  this.machine.popArgs(this.frame, prototype1);
                  cstMethodRef = cstMethodRef1;
                  break;
                case 181:
                  type = ((CstFieldRef)cstMethodRef).getType();
                  this.machine.popArgs(this.frame, Type.OBJECT, type);
                  break;
                case 179:
                  type = ((CstFieldRef)cstMethodRef).getType();
                  this.machine.popArgs(this.frame, type);
                  break;
                case 180:
                  this.machine.popArgs(this.frame, Type.OBJECT);
                  break;
              } 
            } else {
            
            } 
          } else {
            Prototype prototype = Prototype.internInts(Type.VOID, param1Int4);
            this.machine.popArgs(this.frame, prototype);
          } 
        } else {
          this.machine.popArgs(this.frame, Type.INT);
        } 
      } else {
        if (cstMethodRef instanceof com.android.dx.rop.cst.CstMethodHandle || cstMethodRef instanceof com.android.dx.rop.cst.CstProtoRef)
          Simulator.this.checkConstMethodHandleSupported((Constant)cstMethodRef); 
        this.machine.clearArgs();
      } 
      this.machine.auxIntArg(param1Int4);
      this.machine.auxCstArg((Constant)cstMethodRef);
      this.machine.run(this.frame, param1Int2, param1Int1);
    }
    
    public void visitInvalid(int param1Int1, int param1Int2, int param1Int3) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("invalid opcode ");
      stringBuilder.append(Hex.u1(param1Int1));
      throw new SimException(stringBuilder.toString());
    }
    
    public void visitLocal(int param1Int1, int param1Int2, int param1Int3, int param1Int4, Type param1Type, int param1Int5) {
      int i;
      LocalItem localItem1;
      Type type;
      boolean bool;
      if (param1Int1 == 54) {
        i = param1Int2 + param1Int3;
      } else {
        i = param1Int2;
      } 
      LocalVariableList.Item item1 = Simulator.this.localVariables.pcAndIndexToLocal(i, param1Int4);
      LocalItem localItem2 = null;
      LocalVariableList.Item item2 = null;
      if (item1 != null) {
        Type type1 = item1.getType();
        type = type1;
        if (type1.getBasicFrameType() != param1Type.getBasicFrameType()) {
          type = param1Type;
          item1 = null;
        } 
      } else {
        type = param1Type;
      } 
      if (param1Int1 != 21)
        if (param1Int1 != 54) {
          if (param1Int1 != 132) {
            if (param1Int1 != 169) {
              visitInvalid(param1Int1, param1Int2, param1Int3);
              return;
            } 
          } else {
            if (item1 == null) {
              item1 = item2;
            } else {
              localItem1 = item1.getLocalItem();
            } 
            this.machine.localArg(this.frame, param1Int4);
            this.machine.localTarget(param1Int4, type, localItem1);
            this.machine.auxType(param1Type);
            this.machine.auxIntArg(param1Int5);
            this.machine.auxCstArg((Constant)CstInteger.make(param1Int5));
            this.machine.run(this.frame, param1Int2, param1Int1);
          } 
        } else {
          if (localItem1 == null) {
            localItem1 = localItem2;
          } else {
            localItem1 = localItem1.getLocalItem();
          } 
          this.machine.popArgs(this.frame, param1Type);
          this.machine.auxType(param1Type);
          this.machine.localTarget(param1Int4, type, localItem1);
          this.machine.run(this.frame, param1Int2, param1Int1);
        }  
      this.machine.localArg(this.frame, param1Int4);
      Machine machine = this.machine;
      if (localItem1 != null) {
        bool = true;
      } else {
        bool = false;
      } 
      machine.localInfo(bool);
      this.machine.auxType(param1Type);
      this.machine.run(this.frame, param1Int2, param1Int1);
    }
    
    public void visitNewarray(int param1Int1, int param1Int2, CstType param1CstType, ArrayList<Constant> param1ArrayList) {
      this.machine.popArgs(this.frame, Type.INT);
      this.machine.auxInitValues(param1ArrayList);
      this.machine.auxCstArg((Constant)param1CstType);
      this.machine.run(this.frame, param1Int1, 188);
    }
    
    public void visitNoArgs(int param1Int1, int param1Int2, int param1Int3, Type param1Type) {
      if (param1Int1 != 0) {
        Type type;
        if (param1Int1 != 190) {
          if (param1Int1 != 191 && param1Int1 != 194 && param1Int1 != 195) {
            Type type4;
            ExecutionStack executionStack4;
            Type type3;
            ExecutionStack executionStack3;
            Type type2;
            ExecutionStack executionStack2;
            Type type1;
            ExecutionStack executionStack1;
            Type type5;
            boolean bool;
            byte b = 3;
            switch (param1Int1) {
              default:
                b = 17;
                switch (param1Int1) {
                  default:
                    switch (param1Int1) {
                      default:
                        visitInvalid(param1Int1, param1Int2, param1Int3);
                        return;
                      case 151:
                      case 152:
                        this.machine.popArgs(this.frame, Type.DOUBLE, Type.DOUBLE);
                        type4 = param1Type;
                        break;
                      case 149:
                      case 150:
                        this.machine.popArgs(this.frame, Type.FLOAT, Type.FLOAT);
                        type4 = param1Type;
                        break;
                      case 148:
                        this.machine.popArgs(this.frame, Type.LONG, Type.LONG);
                        type4 = param1Type;
                        break;
                      case 142:
                      case 143:
                      case 144:
                        this.machine.popArgs(this.frame, Type.DOUBLE);
                        type4 = param1Type;
                        break;
                      case 139:
                      case 140:
                      case 141:
                        this.machine.popArgs(this.frame, Type.FLOAT);
                        type4 = param1Type;
                        break;
                      case 136:
                      case 137:
                      case 138:
                        this.machine.popArgs(this.frame, Type.LONG);
                        type4 = param1Type;
                        break;
                      case 133:
                      case 134:
                      case 135:
                      case 145:
                      case 146:
                      case 147:
                        break;
                    } 
                    this.machine.popArgs(this.frame, Type.INT);
                    type4 = param1Type;
                    break;
                  case 95:
                    executionStack4 = this.frame.getStack();
                    if (executionStack4.peekType(0).isCategory1() && executionStack4.peekType(1).isCategory1()) {
                      this.machine.popArgs(this.frame, 2);
                      this.machine.auxIntArg(18);
                      type3 = param1Type;
                      break;
                    } 
                    throw Simulator.illegalTos();
                  case 94:
                    executionStack4 = this.frame.getStack();
                    if (executionStack4.peekType(0).isCategory2()) {
                      if (executionStack4.peekType(2).isCategory2()) {
                        this.machine.popArgs(this.frame, 2);
                        this.machine.auxIntArg(530);
                        type3 = param1Type;
                        break;
                      } 
                      if (type3.peekType(3).isCategory1()) {
                        this.machine.popArgs(this.frame, 3);
                        this.machine.auxIntArg(12819);
                        type3 = param1Type;
                        break;
                      } 
                      throw Simulator.illegalTos();
                    } 
                    if (type3.peekType(1).isCategory1()) {
                      if (type3.peekType(2).isCategory2()) {
                        this.machine.popArgs(this.frame, 3);
                        this.machine.auxIntArg(205106);
                        type3 = param1Type;
                        break;
                      } 
                      if (type3.peekType(3).isCategory1()) {
                        this.machine.popArgs(this.frame, 4);
                        this.machine.auxIntArg(4399427);
                        type3 = param1Type;
                        break;
                      } 
                      throw Simulator.illegalTos();
                    } 
                    throw Simulator.illegalTos();
                  case 93:
                    executionStack3 = this.frame.getStack();
                    if (executionStack3.peekType(0).isCategory2()) {
                      if (!executionStack3.peekType(2).isCategory2()) {
                        this.machine.popArgs(this.frame, 2);
                        this.machine.auxIntArg(530);
                        type2 = param1Type;
                        break;
                      } 
                      throw Simulator.illegalTos();
                    } 
                    if (!type2.peekType(1).isCategory2() && !type2.peekType(2).isCategory2()) {
                      this.machine.popArgs(this.frame, 3);
                      this.machine.auxIntArg(205106);
                      type2 = param1Type;
                      break;
                    } 
                    throw Simulator.illegalTos();
                  case 91:
                    executionStack2 = this.frame.getStack();
                    if (!executionStack2.peekType(0).isCategory2()) {
                      Type type6;
                      if (executionStack2.peekType(1).isCategory2()) {
                        this.machine.popArgs(this.frame, 2);
                        this.machine.auxIntArg(530);
                        type6 = param1Type;
                        break;
                      } 
                      if (type6.peekType(2).isCategory1()) {
                        this.machine.popArgs(this.frame, 3);
                        this.machine.auxIntArg(12819);
                        type6 = param1Type;
                        break;
                      } 
                      throw Simulator.illegalTos();
                    } 
                    throw Simulator.illegalTos();
                  case 90:
                    executionStack2 = this.frame.getStack();
                    if (executionStack2.peekType(0).isCategory1() && executionStack2.peekType(1).isCategory1()) {
                      this.machine.popArgs(this.frame, 2);
                      this.machine.auxIntArg(530);
                      Type type6 = param1Type;
                      break;
                    } 
                    throw Simulator.illegalTos();
                  case 89:
                    if (!this.frame.getStack().peekType(0).isCategory2()) {
                      this.machine.popArgs(this.frame, 1);
                      this.machine.auxIntArg(17);
                      Type type6 = param1Type;
                      break;
                    } 
                    throw Simulator.illegalTos();
                  case 88:
                  case 92:
                    executionStack2 = this.frame.getStack();
                    if (executionStack2.peekType(0).isCategory2()) {
                      this.machine.popArgs(this.frame, 1);
                      param1Int3 = b;
                    } else if (executionStack2.peekType(1).isCategory1()) {
                      this.machine.popArgs(this.frame, 2);
                      param1Int3 = 8481;
                    } else {
                      throw Simulator.illegalTos();
                    } 
                    type1 = param1Type;
                    if (param1Int1 == 92) {
                      this.machine.auxIntArg(param1Int3);
                      type1 = param1Type;
                    } 
                    break;
                  case 87:
                    if (!this.frame.getStack().peekType(0).isCategory2()) {
                      this.machine.popArgs(this.frame, 1);
                      type1 = param1Type;
                      break;
                    } 
                    throw Simulator.illegalTos();
                  case 96:
                    break;
                } 
              case 177:
                this.machine.clearArgs();
                checkReturnType(Type.VOID);
                type1 = param1Type;
                break;
              case 172:
                if (param1Type == Type.OBJECT) {
                  type1 = this.frame.getStack().peekType(0);
                } else {
                  type1 = param1Type;
                } 
                this.machine.popArgs(this.frame, param1Type);
                checkReturnType(type1);
                type1 = param1Type;
                break;
              case 120:
              case 122:
              case 124:
                this.machine.popArgs(this.frame, param1Type, Type.INT);
                type1 = param1Type;
                break;
              case 116:
                this.machine.popArgs(this.frame, param1Type);
                type1 = param1Type;
                break;
              case 100:
              case 104:
              case 108:
              case 112:
              case 126:
              case 128:
              case 130:
                this.machine.popArgs(this.frame, param1Type, param1Type);
                type1 = param1Type;
                break;
              case 79:
                executionStack1 = this.frame.getStack();
                param1Int3 = b;
                if (param1Type.isCategory1())
                  param1Int3 = 2; 
                type5 = executionStack1.peekType(param1Int3);
                bool = executionStack1.peekLocal(param1Int3);
                type = Simulator.requiredArrayTypeFor(param1Type, type5);
                if (bool)
                  if (type == Type.KNOWN_NULL) {
                    param1Type = Type.KNOWN_NULL;
                  } else {
                    param1Type = type.getComponentType();
                  }  
                this.machine.popArgs(this.frame, type, Type.INT, param1Type);
                type = param1Type;
                break;
              case 46:
                param1Type = Simulator.requiredArrayTypeFor(param1Type, this.frame.getStack().peekType(1));
                if (param1Type == Type.KNOWN_NULL) {
                  type = Type.KNOWN_NULL;
                } else {
                  type = param1Type.getComponentType();
                } 
                this.machine.popArgs(this.frame, param1Type, Type.INT);
                break;
              case 0:
                this.machine.clearArgs();
                type = param1Type;
                break;
            } 
          } else {
            this.machine.popArgs(this.frame, Type.OBJECT);
            type = param1Type;
          } 
        } else {
          type = this.frame.getStack().peekType(0);
          if (!type.isArrayOrKnownNull()) {
            Simulator simulator = Simulator.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("type mismatch: expected array type but encountered ");
            stringBuilder.append(type.toHuman());
            simulator.fail(stringBuilder.toString());
          } 
          this.machine.popArgs(this.frame, Type.OBJECT);
          type = param1Type;
        } 
        this.machine.auxType(type);
        this.machine.run(this.frame, param1Int2, param1Int1);
        return;
      } 
    }
    
    public void visitSwitch(int param1Int1, int param1Int2, int param1Int3, SwitchList param1SwitchList, int param1Int4) {
      this.machine.popArgs(this.frame, Type.INT);
      this.machine.auxIntArg(param1Int4);
      this.machine.auxSwitchArg(param1SwitchList);
      this.machine.run(this.frame, param1Int2, param1Int1);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\Simulator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */