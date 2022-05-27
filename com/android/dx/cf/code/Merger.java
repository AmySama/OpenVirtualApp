package com.android.dx.cf.code;

import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.Hex;

public final class Merger {
  public static boolean isPossiblyAssignableFrom(TypeBearer paramTypeBearer1, TypeBearer paramTypeBearer2) {
    Type type1 = paramTypeBearer1.getType();
    Type type2 = paramTypeBearer2.getType();
    boolean bool = type1.equals(type2);
    boolean bool1 = true;
    boolean bool2 = true;
    if (bool)
      return true; 
    int i = type1.getBasicType();
    int j = type2.getBasicType();
    int k = i;
    if (i == 10) {
      type1 = Type.OBJECT;
      k = 9;
    } 
    i = j;
    if (j == 10) {
      type2 = Type.OBJECT;
      i = 9;
    } 
    if (k != 9 || i != 9) {
      if (!type1.isIntlike() || !type2.isIntlike())
        bool1 = false; 
      return bool1;
    } 
    if (type1 == Type.KNOWN_NULL)
      return false; 
    if (type2 == Type.KNOWN_NULL)
      return true; 
    if (type1 == Type.OBJECT)
      return true; 
    if (type1.isArray()) {
      Type type4;
      Type type3 = type2;
      if (!type2.isArray())
        return false; 
      while (true) {
        type2 = type1.getComponentType();
        type4 = type3.getComponentType();
        if (type2.isArray()) {
          type1 = type2;
          type3 = type4;
          if (!type4.isArray())
            break; 
          continue;
        } 
        break;
      } 
      return isPossiblyAssignableFrom((TypeBearer)type2, (TypeBearer)type4);
    } 
    bool1 = bool2;
    if (type2.isArray()) {
      bool1 = bool2;
      if (type1 != Type.SERIALIZABLE)
        if (type1 == Type.CLONEABLE) {
          bool1 = bool2;
        } else {
          bool1 = false;
        }  
    } 
    return bool1;
  }
  
  public static OneLocalsArray mergeLocals(OneLocalsArray paramOneLocalsArray1, OneLocalsArray paramOneLocalsArray2) {
    if (paramOneLocalsArray1 == paramOneLocalsArray2)
      return paramOneLocalsArray1; 
    int i = paramOneLocalsArray1.getMaxLocals();
    OneLocalsArray oneLocalsArray = null;
    if (paramOneLocalsArray2.getMaxLocals() == i) {
      byte b = 0;
      while (b < i) {
        TypeBearer typeBearer1 = paramOneLocalsArray1.getOrNull(b);
        TypeBearer typeBearer2 = mergeType(typeBearer1, paramOneLocalsArray2.getOrNull(b));
        OneLocalsArray oneLocalsArray1 = oneLocalsArray;
        if (typeBearer2 != typeBearer1) {
          oneLocalsArray1 = oneLocalsArray;
          if (oneLocalsArray == null)
            oneLocalsArray1 = paramOneLocalsArray1.copy(); 
          if (typeBearer2 == null) {
            oneLocalsArray1.invalidate(b);
          } else {
            oneLocalsArray1.set(b, typeBearer2);
          } 
        } 
        b++;
        oneLocalsArray = oneLocalsArray1;
      } 
      if (oneLocalsArray == null)
        return paramOneLocalsArray1; 
      oneLocalsArray.setImmutable();
      return oneLocalsArray;
    } 
    throw new SimException("mismatched maxLocals values");
  }
  
  public static ExecutionStack mergeStack(ExecutionStack paramExecutionStack1, ExecutionStack paramExecutionStack2) {
    if (paramExecutionStack1 == paramExecutionStack2)
      return paramExecutionStack1; 
    int i = paramExecutionStack1.size();
    ExecutionStack executionStack = null;
    if (paramExecutionStack2.size() == i) {
      SimException simException;
      byte b = 0;
      while (b < i) {
        TypeBearer typeBearer1 = paramExecutionStack1.peek(b);
        TypeBearer typeBearer2 = paramExecutionStack2.peek(b);
        TypeBearer typeBearer3 = mergeType(typeBearer1, typeBearer2);
        ExecutionStack executionStack1 = executionStack;
        if (typeBearer3 != typeBearer1) {
          executionStack1 = executionStack;
          if (executionStack == null)
            executionStack1 = paramExecutionStack1.copy(); 
          if (typeBearer3 != null) {
            try {
              executionStack1.change(b, typeBearer3);
            } catch (SimException null) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("...while merging stack[");
              stringBuilder.append(Hex.u2(b));
              stringBuilder.append("]");
              simException.addContext(stringBuilder.toString());
              throw simException;
            } 
          } else {
            simException = new SimException();
            StringBuilder stringBuilder = new StringBuilder();
            this();
            stringBuilder.append("incompatible: ");
            stringBuilder.append(typeBearer1);
            stringBuilder.append(", ");
            stringBuilder.append(typeBearer2);
            this(stringBuilder.toString());
            throw simException;
          } 
        } 
        b++;
        executionStack = executionStack1;
      } 
      if (executionStack == null)
        return (ExecutionStack)simException; 
      executionStack.setImmutable();
      return executionStack;
    } 
    throw new SimException("mismatched stack depths");
  }
  
  public static TypeBearer mergeType(TypeBearer paramTypeBearer1, TypeBearer paramTypeBearer2) {
    TypeBearer typeBearer;
    if (paramTypeBearer1 == null || paramTypeBearer1.equals(paramTypeBearer2))
      return paramTypeBearer1; 
    if (paramTypeBearer2 == null)
      return null; 
    Type type1 = paramTypeBearer1.getType();
    Type type2 = paramTypeBearer2.getType();
    if (type1 == type2)
      return (TypeBearer)type1; 
    if (type1.isReference() && type2.isReference()) {
      if (type1 == Type.KNOWN_NULL)
        return (TypeBearer)type2; 
      if (type2 == Type.KNOWN_NULL)
        return (TypeBearer)type1; 
      if (type1.isArray() && type2.isArray()) {
        typeBearer = mergeType((TypeBearer)type1.getComponentType(), (TypeBearer)type2.getComponentType());
        return (TypeBearer)((typeBearer == null) ? Type.OBJECT : ((Type)typeBearer).getArrayType());
      } 
      return (TypeBearer)Type.OBJECT;
    } 
    return (TypeBearer)((typeBearer.isIntlike() && type2.isIntlike()) ? Type.INT : null);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\Merger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */