package com.android.dx.ssa;

import com.android.dx.util.IntSet;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;

public class DomFront {
  private static final boolean DEBUG = false;
  
  private final DomInfo[] domInfos;
  
  private final SsaMethod meth;
  
  private final ArrayList<SsaBasicBlock> nodes;
  
  public DomFront(SsaMethod paramSsaMethod) {
    this.meth = paramSsaMethod;
    ArrayList<SsaBasicBlock> arrayList = paramSsaMethod.getBlocks();
    this.nodes = arrayList;
    int i = arrayList.size();
    this.domInfos = new DomInfo[i];
    for (byte b = 0; b < i; b++)
      this.domInfos[b] = new DomInfo(); 
  }
  
  private void buildDomTree() {
    int i = this.nodes.size();
    for (byte b = 0; b < i; b++) {
      DomInfo domInfo = this.domInfos[b];
      if (domInfo.idom != -1)
        ((SsaBasicBlock)this.nodes.get(domInfo.idom)).addDomChild(this.nodes.get(b)); 
    } 
  }
  
  private void calcDomFronts() {
    int i = this.nodes.size();
    for (byte b = 0; b < i; b++) {
      SsaBasicBlock ssaBasicBlock = this.nodes.get(b);
      DomInfo domInfo = this.domInfos[b];
      BitSet bitSet = ssaBasicBlock.getPredecessors();
      if (bitSet.cardinality() > 1) {
        int j;
        for (j = bitSet.nextSetBit(0); j >= 0; j = bitSet.nextSetBit(j + 1)) {
          int k;
          for (k = j; k != domInfo.idom && k != -1; k = domInfo1.idom) {
            DomInfo domInfo1 = this.domInfos[k];
            if (domInfo1.dominanceFrontiers.has(b))
              break; 
            domInfo1.dominanceFrontiers.add(b);
          } 
        } 
      } 
    } 
  }
  
  private void debugPrintDomChildren() {
    int i = this.nodes.size();
    for (byte b = 0; b < i; b++) {
      SsaBasicBlock ssaBasicBlock = this.nodes.get(b);
      StringBuffer stringBuffer = new StringBuffer();
      stringBuffer.append('{');
      Iterator<SsaBasicBlock> iterator = ssaBasicBlock.getDomChildren().iterator();
      boolean bool;
      for (bool = false; iterator.hasNext(); bool = true) {
        SsaBasicBlock ssaBasicBlock1 = iterator.next();
        if (bool)
          stringBuffer.append(','); 
        stringBuffer.append(ssaBasicBlock1);
      } 
      stringBuffer.append('}');
      PrintStream printStream = System.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("domChildren[");
      stringBuilder.append(ssaBasicBlock);
      stringBuilder.append("]: ");
      stringBuilder.append(stringBuffer);
      printStream.println(stringBuilder.toString());
    } 
  }
  
  public DomInfo[] run() {
    int i = this.nodes.size();
    SsaMethod ssaMethod = this.meth;
    DomInfo[] arrayOfDomInfo = this.domInfos;
    byte b = 0;
    Dominators.make(ssaMethod, arrayOfDomInfo, false);
    buildDomTree();
    while (b < i) {
      (this.domInfos[b]).dominanceFrontiers = SetFactory.makeDomFrontSet(i);
      b++;
    } 
    calcDomFronts();
    return this.domInfos;
  }
  
  public static class DomInfo {
    public IntSet dominanceFrontiers;
    
    public int idom = -1;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\DomFront.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */