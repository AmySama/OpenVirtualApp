package com.android.dx.ssa;

import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.LocalItem;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.Rop;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.Hex;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class PhiInsn extends SsaInsn {
  private final ArrayList<Operand> operands = new ArrayList<Operand>();
  
  private final int ropResultReg;
  
  private RegisterSpecList sources;
  
  public PhiInsn(int paramInt, SsaBasicBlock paramSsaBasicBlock) {
    super(RegisterSpec.make(paramInt, (TypeBearer)Type.VOID), paramSsaBasicBlock);
    this.ropResultReg = paramInt;
  }
  
  public PhiInsn(RegisterSpec paramRegisterSpec, SsaBasicBlock paramSsaBasicBlock) {
    super(paramRegisterSpec, paramSsaBasicBlock);
    this.ropResultReg = paramRegisterSpec.getReg();
  }
  
  public void accept(SsaInsn.Visitor paramVisitor) {
    paramVisitor.visitPhiInsn(this);
  }
  
  public void addPhiOperand(RegisterSpec paramRegisterSpec, SsaBasicBlock paramSsaBasicBlock) {
    this.operands.add(new Operand(paramRegisterSpec, paramSsaBasicBlock.getIndex(), paramSsaBasicBlock.getRopLabel()));
    this.sources = null;
  }
  
  public boolean areAllOperandsEqual() {
    if (this.operands.size() == 0)
      return true; 
    int i = ((Operand)this.operands.get(0)).regSpec.getReg();
    Iterator<Operand> iterator = this.operands.iterator();
    while (iterator.hasNext()) {
      if (i != ((Operand)iterator.next()).regSpec.getReg())
        return false; 
    } 
    return true;
  }
  
  public boolean canThrow() {
    return false;
  }
  
  public void changeResultType(TypeBearer paramTypeBearer, LocalItem paramLocalItem) {
    setResult(RegisterSpec.makeLocalOptional(getResult().getReg(), paramTypeBearer, paramLocalItem));
  }
  
  public PhiInsn clone() {
    throw new UnsupportedOperationException("can't clone phi");
  }
  
  public Rop getOpcode() {
    return null;
  }
  
  public Insn getOriginalRopInsn() {
    return null;
  }
  
  public int getRopResultReg() {
    return this.ropResultReg;
  }
  
  public RegisterSpecList getSources() {
    RegisterSpecList registerSpecList = this.sources;
    if (registerSpecList != null)
      return registerSpecList; 
    if (this.operands.size() == 0)
      return RegisterSpecList.EMPTY; 
    int i = this.operands.size();
    this.sources = new RegisterSpecList(i);
    for (byte b = 0; b < i; b++) {
      Operand operand = this.operands.get(b);
      this.sources.set(b, operand.regSpec);
    } 
    this.sources.setImmutable();
    return this.sources;
  }
  
  public boolean hasSideEffect() {
    boolean bool;
    if (Optimizer.getPreserveLocals() && getLocalAssignment() != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isPhiOrMove() {
    return true;
  }
  
  public boolean isRegASource(int paramInt) {
    Iterator<Operand> iterator = this.operands.iterator();
    while (iterator.hasNext()) {
      if (((Operand)iterator.next()).regSpec.getReg() == paramInt)
        return true; 
    } 
    return false;
  }
  
  public final void mapSourceRegisters(RegisterMapper paramRegisterMapper) {
    for (Operand operand : this.operands) {
      RegisterSpec registerSpec = operand.regSpec;
      operand.regSpec = paramRegisterMapper.map(registerSpec);
      if (registerSpec != operand.regSpec)
        getBlock().getParent().onSourceChanged(this, registerSpec, operand.regSpec); 
    } 
    this.sources = null;
  }
  
  public int predBlockIndexForSourcesIndex(int paramInt) {
    return ((Operand)this.operands.get(paramInt)).blockIndex;
  }
  
  public List<SsaBasicBlock> predBlocksForReg(int paramInt, SsaMethod paramSsaMethod) {
    ArrayList<SsaBasicBlock> arrayList = new ArrayList();
    for (Operand operand : this.operands) {
      if (operand.regSpec.getReg() == paramInt)
        arrayList.add(paramSsaMethod.getBlocks().get(operand.blockIndex)); 
    } 
    return arrayList;
  }
  
  public void removePhiRegister(RegisterSpec paramRegisterSpec) {
    ArrayList<Operand> arrayList = new ArrayList();
    for (Operand operand : this.operands) {
      if (operand.regSpec.getReg() == paramRegisterSpec.getReg())
        arrayList.add(operand); 
    } 
    this.operands.removeAll(arrayList);
    this.sources = null;
  }
  
  public String toHuman() {
    return toHumanWithInline((String)null);
  }
  
  protected final String toHumanWithInline(String paramString) {
    StringBuilder stringBuilder = new StringBuilder(80);
    stringBuilder.append(SourcePosition.NO_INFO);
    stringBuilder.append(": phi");
    if (paramString != null) {
      stringBuilder.append("(");
      stringBuilder.append(paramString);
      stringBuilder.append(")");
    } 
    RegisterSpec registerSpec = getResult();
    if (registerSpec == null) {
      stringBuilder.append(" .");
    } else {
      stringBuilder.append(" ");
      stringBuilder.append(registerSpec.toHuman());
    } 
    stringBuilder.append(" <-");
    int i = getSources().size();
    if (i == 0) {
      stringBuilder.append(" .");
    } else {
      for (byte b = 0; b < i; b++) {
        stringBuilder.append(" ");
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(this.sources.get(b).toHuman());
        stringBuilder1.append("[b=");
        stringBuilder1.append(Hex.u2(((Operand)this.operands.get(b)).ropLabel));
        stringBuilder1.append("]");
        stringBuilder.append(stringBuilder1.toString());
      } 
    } 
    return stringBuilder.toString();
  }
  
  public Insn toRopInsn() {
    throw new IllegalArgumentException("Cannot convert phi insns to rop form");
  }
  
  public void updateSourcesToDefinitions(SsaMethod paramSsaMethod) {
    for (Operand operand : this.operands) {
      RegisterSpec registerSpec = paramSsaMethod.getDefinitionForRegister(operand.regSpec.getReg()).getResult();
      operand.regSpec = operand.regSpec.withType((TypeBearer)registerSpec.getType());
    } 
    this.sources = null;
  }
  
  private static class Operand {
    public final int blockIndex;
    
    public RegisterSpec regSpec;
    
    public final int ropLabel;
    
    public Operand(RegisterSpec param1RegisterSpec, int param1Int1, int param1Int2) {
      this.regSpec = param1RegisterSpec;
      this.blockIndex = param1Int1;
      this.ropLabel = param1Int2;
    }
  }
  
  public static interface Visitor {
    void visitPhiInsn(PhiInsn param1PhiInsn);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\PhiInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */