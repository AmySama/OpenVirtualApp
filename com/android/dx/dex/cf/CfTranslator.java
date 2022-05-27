package com.android.dx.dex.cf;

import com.android.dex.util.ExceptionWithContext;
import com.android.dx.cf.code.BootstrapMethodsList;
import com.android.dx.cf.code.ConcreteMethod;
import com.android.dx.cf.code.Ropper;
import com.android.dx.cf.direct.DirectClassFile;
import com.android.dx.cf.iface.ClassFile;
import com.android.dx.cf.iface.Field;
import com.android.dx.cf.iface.FieldList;
import com.android.dx.cf.iface.Method;
import com.android.dx.cf.iface.MethodList;
import com.android.dx.command.dexer.DxContext;
import com.android.dx.dex.DexOptions;
import com.android.dx.dex.code.DalvCode;
import com.android.dx.dex.code.RopTranslator;
import com.android.dx.dex.file.CallSiteIdsSection;
import com.android.dx.dex.file.ClassDefItem;
import com.android.dx.dex.file.DexFile;
import com.android.dx.dex.file.EncodedField;
import com.android.dx.dex.file.EncodedMethod;
import com.android.dx.dex.file.FieldIdsSection;
import com.android.dx.dex.file.MethodHandlesSection;
import com.android.dx.dex.file.MethodIdsSection;
import com.android.dx.rop.annotation.Annotations;
import com.android.dx.rop.annotation.AnnotationsList;
import com.android.dx.rop.code.AccessFlags;
import com.android.dx.rop.code.DexTranslationAdvice;
import com.android.dx.rop.code.LocalVariableExtractor;
import com.android.dx.rop.code.LocalVariableInfo;
import com.android.dx.rop.code.RopMethod;
import com.android.dx.rop.code.TranslationAdvice;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.ConstantPool;
import com.android.dx.rop.cst.CstBaseMethodRef;
import com.android.dx.rop.cst.CstBoolean;
import com.android.dx.rop.cst.CstByte;
import com.android.dx.rop.cst.CstCallSite;
import com.android.dx.rop.cst.CstCallSiteRef;
import com.android.dx.rop.cst.CstChar;
import com.android.dx.rop.cst.CstEnumRef;
import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.cst.CstInterfaceMethodRef;
import com.android.dx.rop.cst.CstInvokeDynamic;
import com.android.dx.rop.cst.CstMethodHandle;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstShort;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.cst.TypedConstant;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;
import com.android.dx.ssa.Optimizer;
import java.util.Iterator;

public class CfTranslator {
  private static final boolean DEBUG = false;
  
  private static TypedConstant coerceConstant(TypedConstant paramTypedConstant, Type paramType) {
    if (paramTypedConstant.getType().equals(paramType))
      return paramTypedConstant; 
    int i = paramType.getBasicType();
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i == 8)
            return (TypedConstant)CstShort.make(((CstInteger)paramTypedConstant).getValue()); 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("can't coerce ");
          stringBuilder.append(paramTypedConstant);
          stringBuilder.append(" to ");
          stringBuilder.append(paramType);
          throw new UnsupportedOperationException(stringBuilder.toString());
        } 
        return (TypedConstant)CstChar.make(((CstInteger)paramTypedConstant).getValue());
      } 
      return (TypedConstant)CstByte.make(((CstInteger)paramTypedConstant).getValue());
    } 
    return (TypedConstant)CstBoolean.make(((CstInteger)paramTypedConstant).getValue());
  }
  
  private static void processFields(DirectClassFile paramDirectClassFile, ClassDefItem paramClassDefItem, DexFile paramDexFile) {
    CstType cstType = paramDirectClassFile.getThisClass();
    FieldList fieldList = paramDirectClassFile.getFields();
    int i = fieldList.size();
    byte b = 0;
    while (b < i) {
      Field field = fieldList.get(b);
      try {
        CstFieldRef cstFieldRef = new CstFieldRef();
        this(cstType, field.getNat());
        int j = field.getAccessFlags();
        if (AccessFlags.isStatic(j)) {
          TypedConstant typedConstant2 = field.getConstantValue();
          EncodedField encodedField = new EncodedField();
          this(cstFieldRef, j);
          TypedConstant typedConstant1 = typedConstant2;
          if (typedConstant2 != null)
            typedConstant1 = coerceConstant(typedConstant2, cstFieldRef.getType()); 
          paramClassDefItem.addStaticField(encodedField, (Constant)typedConstant1);
        } else {
          EncodedField encodedField = new EncodedField();
          this(cstFieldRef, j);
          paramClassDefItem.addInstanceField(encodedField);
        } 
        Annotations annotations = AttributeTranslator.getAnnotations(field.getAttributes());
        if (annotations.size() != 0)
          paramClassDefItem.addFieldAnnotations(cstFieldRef, annotations, paramDexFile); 
        paramDexFile.getFieldIds().intern(cstFieldRef);
        b++;
      } catch (RuntimeException runtimeException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("...while processing ");
        stringBuilder.append(field.getName().toHuman());
        stringBuilder.append(" ");
        stringBuilder.append(field.getDescriptor().toHuman());
        throw ExceptionWithContext.withContext(runtimeException, stringBuilder.toString());
      } 
    } 
  }
  
  private static void processMethods(DxContext paramDxContext, DirectClassFile paramDirectClassFile, CfOptions paramCfOptions, DexOptions paramDexOptions, ClassDefItem paramClassDefItem, DexFile paramDexFile) {
    CstType cstType = paramDirectClassFile.getThisClass();
    MethodList methodList = paramDirectClassFile.getMethods();
    int i = methodList.size();
    byte b = 0;
    while (true) {
      DxContext dxContext = paramDxContext;
      if (b < i) {
        Method method = methodList.get(b);
        try {
          boolean bool7;
          DalvCode dalvCode;
          CstMethodRef cstMethodRef = new CstMethodRef();
          this(cstType, method.getNat());
          int j = method.getAccessFlags();
          boolean bool1 = AccessFlags.isStatic(j);
          boolean bool2 = AccessFlags.isPrivate(j);
          boolean bool3 = AccessFlags.isNative(j);
          boolean bool4 = AccessFlags.isAbstract(j);
          boolean bool5 = cstMethodRef.isInstanceInit();
          boolean bool6 = true;
          if (bool5 || cstMethodRef.isClassInit()) {
            bool7 = true;
          } else {
            bool7 = false;
          } 
          if (bool3 || bool4) {
            dalvCode = null;
          } else {
            ConcreteMethod concreteMethod = new ConcreteMethod();
            if (paramCfOptions.positionInfo == 1)
              bool6 = false; 
            this(method, (ClassFile)paramDirectClassFile, bool6, paramCfOptions.localInfo);
            DexTranslationAdvice dexTranslationAdvice = DexTranslationAdvice.THE_ONE;
            RopMethod ropMethod = Ropper.convert(concreteMethod, (TranslationAdvice)dexTranslationAdvice, methodList, paramDexOptions);
            int m = cstMethodRef.getParameterWordCount(bool1);
            StringBuilder stringBuilder = new StringBuilder();
            this();
            stringBuilder.append(cstType.getClassType().getDescriptor());
            stringBuilder.append(".");
            stringBuilder.append(method.getName().getString());
            String str = stringBuilder.toString();
            if (paramCfOptions.optimize && dxContext.optimizerOptions.shouldOptimize(str)) {
              RopMethod ropMethod2 = Optimizer.optimize(ropMethod, m, bool1, paramCfOptions.localInfo, (TranslationAdvice)dexTranslationAdvice);
              if (paramCfOptions.statistics)
                dxContext.codeStatistics.updateRopStatistics(ropMethod, ropMethod2); 
              RopMethod ropMethod1 = ropMethod;
              ropMethod = ropMethod2;
            } else {
              dxContext = null;
            } 
            if (paramCfOptions.localInfo) {
              LocalVariableInfo localVariableInfo = LocalVariableExtractor.extract(ropMethod);
            } else {
              dexTranslationAdvice = null;
            } 
            DalvCode dalvCode1 = RopTranslator.translate(ropMethod, paramCfOptions.positionInfo, (LocalVariableInfo)dexTranslationAdvice, m, paramDexOptions);
            if (paramCfOptions.statistics && dxContext != null) {
              int n = concreteMethod.getCode().size();
              try {
                updateDexStatistics(paramDxContext, paramCfOptions, paramDexOptions, ropMethod, (RopMethod)dxContext, (LocalVariableInfo)dexTranslationAdvice, m, n);
                DalvCode dalvCode2 = dalvCode1;
              } catch (RuntimeException runtimeException) {
                continue;
              } 
              continue;
            } 
            dalvCode = dalvCode1;
          } 
          Method method1 = method;
          int k = j;
          if (AccessFlags.isSynchronized(j)) {
            j |= 0x20000;
            k = j;
            if (!bool3)
              k = j & 0xFFFFFFDF; 
          } 
          j = k;
          if (bool7)
            j = k | 0x10000; 
          TypeList typeList = AttributeTranslator.getExceptions(method1);
          EncodedMethod encodedMethod = new EncodedMethod();
          this(cstMethodRef, j, dalvCode, typeList);
          if (cstMethodRef.isInstanceInit() || cstMethodRef.isClassInit() || bool1 || bool2) {
            paramClassDefItem.addDirectMethod(encodedMethod);
          } else {
            paramClassDefItem.addVirtualMethod(encodedMethod);
          } 
          Annotations annotations = AttributeTranslator.getMethodAnnotations(method1);
          if (annotations.size() != 0)
            paramClassDefItem.addMethodAnnotations(cstMethodRef, annotations, paramDexFile); 
          AnnotationsList annotationsList = AttributeTranslator.getParameterAnnotations(method1);
          if (annotationsList.size() != 0)
            paramClassDefItem.addParameterAnnotations(cstMethodRef, annotationsList, paramDexFile); 
          paramDexFile.getMethodIds().intern((CstBaseMethodRef)cstMethodRef);
          b++;
        } catch (RuntimeException runtimeException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("...while processing ");
          stringBuilder.append(method.getName().toHuman());
          stringBuilder.append(" ");
          stringBuilder.append(method.getDescriptor().toHuman());
          throw ExceptionWithContext.withContext(runtimeException, stringBuilder.toString());
        } 
      } 
      break;
    } 
  }
  
  public static ClassDefItem translate(DxContext paramDxContext, DirectClassFile paramDirectClassFile, byte[] paramArrayOfbyte, CfOptions paramCfOptions, DexOptions paramDexOptions, DexFile paramDexFile) {
    try {
      return translate0(paramDxContext, paramDirectClassFile, paramArrayOfbyte, paramCfOptions, paramDexOptions, paramDexFile);
    } catch (RuntimeException runtimeException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("...while processing ");
      stringBuilder.append(paramDirectClassFile.getFilePath());
      throw ExceptionWithContext.withContext(runtimeException, stringBuilder.toString());
    } 
  }
  
  private static ClassDefItem translate0(DxContext paramDxContext, DirectClassFile paramDirectClassFile, byte[] paramArrayOfbyte, CfOptions paramCfOptions, DexOptions paramDexOptions, DexFile paramDexFile) {
    CstString cstString;
    paramDxContext.optimizerOptions.loadOptimizeLists(paramCfOptions.optimizeListFile, paramCfOptions.dontOptimizeListFile);
    CstType cstType = paramDirectClassFile.getThisClass();
    int i = paramDirectClassFile.getAccessFlags();
    if (paramCfOptions.positionInfo == 1) {
      paramArrayOfbyte = null;
    } else {
      cstString = paramDirectClassFile.getSourceFile();
    } 
    ClassDefItem classDefItem = new ClassDefItem(cstType, i & 0xFFFFFFDF, paramDirectClassFile.getSuperclass(), paramDirectClassFile.getInterfaces(), cstString);
    Annotations annotations = AttributeTranslator.getClassAnnotations(paramDirectClassFile, paramCfOptions);
    if (annotations.size() != 0)
      classDefItem.setClassAnnotations(annotations, paramDexFile); 
    FieldIdsSection fieldIdsSection = paramDexFile.getFieldIds();
    MethodIdsSection methodIdsSection = paramDexFile.getMethodIds();
    MethodHandlesSection methodHandlesSection = paramDexFile.getMethodHandles();
    CallSiteIdsSection callSiteIdsSection = paramDexFile.getCallSiteIds();
    processFields(paramDirectClassFile, classDefItem, paramDexFile);
    processMethods(paramDxContext, paramDirectClassFile, paramCfOptions, paramDexOptions, classDefItem, paramDexFile);
    ConstantPool constantPool = paramDirectClassFile.getConstantPool();
    int j = constantPool.size();
    for (i = 0; i < j; i++) {
      Constant constant = constantPool.getOrNull(i);
      if (constant instanceof CstMethodRef) {
        methodIdsSection.intern((CstBaseMethodRef)constant);
      } else if (constant instanceof CstInterfaceMethodRef) {
        methodIdsSection.intern((CstBaseMethodRef)((CstInterfaceMethodRef)constant).toMethodRef());
      } else if (constant instanceof CstFieldRef) {
        fieldIdsSection.intern((CstFieldRef)constant);
      } else if (constant instanceof CstEnumRef) {
        fieldIdsSection.intern(((CstEnumRef)constant).getFieldRef());
      } else if (constant instanceof CstMethodHandle) {
        methodHandlesSection.intern((CstMethodHandle)constant);
      } else if (constant instanceof CstInvokeDynamic) {
        CstInvokeDynamic cstInvokeDynamic = (CstInvokeDynamic)constant;
        int k = cstInvokeDynamic.getBootstrapMethodIndex();
        BootstrapMethodsList.Item item = paramDirectClassFile.getBootstrapMethods().get(k);
        CstCallSite cstCallSite = CstCallSite.make(item.getBootstrapMethodHandle(), cstInvokeDynamic.getNat(), item.getBootstrapMethodArguments());
        cstInvokeDynamic.setDeclaringClass(paramDirectClassFile.getThisClass());
        cstInvokeDynamic.setCallSite(cstCallSite);
        Iterator<CstCallSiteRef> iterator = cstInvokeDynamic.getReferences().iterator();
        while (iterator.hasNext())
          callSiteIdsSection.intern(iterator.next()); 
      } 
    } 
    return classDefItem;
  }
  
  private static void updateDexStatistics(DxContext paramDxContext, CfOptions paramCfOptions, DexOptions paramDexOptions, RopMethod paramRopMethod1, RopMethod paramRopMethod2, LocalVariableInfo paramLocalVariableInfo, int paramInt1, int paramInt2) {
    DalvCode dalvCode2 = RopTranslator.translate(paramRopMethod1, paramCfOptions.positionInfo, paramLocalVariableInfo, paramInt1, paramDexOptions);
    DalvCode dalvCode1 = RopTranslator.translate(paramRopMethod2, paramCfOptions.positionInfo, paramLocalVariableInfo, paramInt1, paramDexOptions);
    DalvCode.AssignIndicesCallback assignIndicesCallback = new DalvCode.AssignIndicesCallback() {
        public int getIndex(Constant param1Constant) {
          return 0;
        }
      };
    dalvCode2.assignIndices(assignIndicesCallback);
    dalvCode1.assignIndices(assignIndicesCallback);
    paramDxContext.codeStatistics.updateDexStatistics(dalvCode1, dalvCode2);
    paramDxContext.codeStatistics.updateOriginalByteCount(paramInt2);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\cf\CfTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */