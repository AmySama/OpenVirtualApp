package com.android.dx;

import com.android.dx.rop.code.BasicBlock;
import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.InsnList;
import com.android.dx.util.IntList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Label {
  Label alternateSuccessor;
  
  List<Label> catchLabels = Collections.emptyList();
  
  Code code;
  
  int id = -1;
  
  final List<Insn> instructions = new ArrayList<Insn>();
  
  boolean marked = false;
  
  Label primarySuccessor;
  
  void compact() {
    for (byte b = 0; b < this.catchLabels.size(); b++) {
      while (((Label)this.catchLabels.get(b)).isEmpty()) {
        List<Label> list = this.catchLabels;
        list.set(b, ((Label)list.get(b)).primarySuccessor);
      } 
    } 
    while (true) {
      Label label = this.primarySuccessor;
      if (label != null && label.isEmpty()) {
        this.primarySuccessor = this.primarySuccessor.primarySuccessor;
        continue;
      } 
      break;
    } 
    while (true) {
      Label label = this.alternateSuccessor;
      if (label != null && label.isEmpty()) {
        this.alternateSuccessor = this.alternateSuccessor.primarySuccessor;
        continue;
      } 
      break;
    } 
  }
  
  boolean isEmpty() {
    return this.instructions.isEmpty();
  }
  
  BasicBlock toBasicBlock() {
    InsnList insnList = new InsnList(this.instructions.size());
    int i;
    for (i = 0; i < this.instructions.size(); i++)
      insnList.set(i, this.instructions.get(i)); 
    insnList.setImmutable();
    i = -1;
    IntList intList = new IntList();
    Iterator<Label> iterator = this.catchLabels.iterator();
    while (iterator.hasNext())
      intList.add(((Label)iterator.next()).id); 
    Label label = this.primarySuccessor;
    if (label != null) {
      i = label.id;
      intList.add(i);
    } 
    label = this.alternateSuccessor;
    if (label != null)
      intList.add(label.id); 
    intList.setImmutable();
    return new BasicBlock(this.id, insnList, intList, i);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\Label.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */