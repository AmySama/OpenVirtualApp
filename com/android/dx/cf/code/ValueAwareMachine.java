package com.android.dx.cf.code;

import com.android.dx.rop.cst.CstCallSiteRef;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Prototype;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.Hex;

public class ValueAwareMachine extends BaseMachine {
  public ValueAwareMachine(Prototype paramPrototype) {
    super(paramPrototype);
  }
  
  public void run(Frame paramFrame, int paramInt1, int paramInt2) {
    if (paramInt2 != 0)
      if (paramInt2 != 20)
        if (paramInt2 != 21)
          if (paramInt2 != 171 && paramInt2 != 172) {
            StringBuilder stringBuilder;
            Type type;
            switch (paramInt2) {
              default:
                switch (paramInt2) {
                  default:
                    switch (paramInt2) {
                      default:
                        switch (paramInt2) {
                          default:
                            switch (paramInt2) {
                              default:
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("shouldn't happen: ");
                                stringBuilder.append(Hex.u1(paramInt2));
                                throw new RuntimeException(stringBuilder.toString());
                              case 197:
                                setResult((TypeBearer)((CstType)getAuxCst()).getClassType());
                                break;
                              case 198:
                              case 199:
                                break;
                            } 
                            break;
                          case 193:
                            setResult((TypeBearer)Type.INT);
                            break;
                          case 189:
                            setResult((TypeBearer)((CstType)getAuxCst()).getClassType().getArrayType());
                            break;
                          case 188:
                          case 192:
                          
                          case 187:
                            setResult((TypeBearer)((CstType)getAuxCst()).getClassType().asUninitialized(paramInt1));
                            break;
                          case 186:
                            type = ((CstCallSiteRef)getAuxCst()).getReturnType();
                            if (type == Type.VOID) {
                              clearResult();
                              break;
                            } 
                            setResult((TypeBearer)type);
                            break;
                          case 183:
                            type = arg(0).getType();
                            if (type.isUninitialized())
                              stringBuilder.makeInitialized(type); 
                            type = ((TypeBearer)getAuxCst()).getType();
                            if (type == Type.VOID) {
                              clearResult();
                              break;
                            } 
                            setResult((TypeBearer)type);
                            break;
                          case 178:
                          case 180:
                          case 182:
                          case 184:
                          case 185:
                            type = ((TypeBearer)getAuxCst()).getType();
                            if (type == Type.VOID) {
                              clearResult();
                              break;
                            } 
                            setResult((TypeBearer)type);
                            break;
                          case 190:
                            setResult((TypeBearer)getAuxType());
                            break;
                          case 177:
                          case 179:
                          case 181:
                          case 191:
                          case 194:
                          case 195:
                            break;
                        } 
                        break;
                      case 168:
                        setResult(new ReturnAddress(getAuxTarget()));
                        break;
                      case 132:
                      case 133:
                      case 134:
                      case 135:
                      case 136:
                      case 137:
                      case 138:
                      case 139:
                      case 140:
                      case 141:
                      case 142:
                      case 143:
                      case 144:
                      case 145:
                      case 146:
                      case 147:
                      case 148:
                      case 149:
                      case 150:
                      case 151:
                      case 152:
                      
                      case 153:
                      case 154:
                      case 155:
                      case 156:
                      case 157:
                      case 158:
                      case 159:
                      case 160:
                      case 161:
                      case 162:
                      case 163:
                      case 164:
                      case 165:
                      case 166:
                      case 167:
                      case 169:
                        break;
                    } 
                    break;
                  case 89:
                  case 90:
                  case 91:
                  case 92:
                  case 93:
                  case 94:
                  case 95:
                    clearResult();
                    for (paramInt1 = getAuxInt(); paramInt1 != 0; paramInt1 >>= 4)
                      addResult(arg((paramInt1 & 0xF) - 1)); 
                    break;
                  case 96:
                  
                  case 87:
                  case 88:
                    break;
                } 
              case 46:
              case 100:
              case 104:
              case 108:
              case 112:
              case 116:
              case 120:
              case 122:
              case 124:
              case 126:
              case 128:
              case 130:
              
              case 54:
                setResult(arg(0));
                break;
              case 18:
                setResult((TypeBearer)getAuxCst());
                break;
              case 0:
              case 79:
                clearResult();
                break;
            } 
            storeResults((Frame)stringBuilder);
            return;
          }    
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\ValueAwareMachine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */