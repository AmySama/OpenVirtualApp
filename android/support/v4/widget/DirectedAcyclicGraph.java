package android.support.v4.widget;

import android.support.v4.util.Pools;
import android.support.v4.util.SimpleArrayMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class DirectedAcyclicGraph<T> {
  private final SimpleArrayMap<T, ArrayList<T>> mGraph = new SimpleArrayMap();
  
  private final Pools.Pool<ArrayList<T>> mListPool = (Pools.Pool<ArrayList<T>>)new Pools.SimplePool(10);
  
  private final ArrayList<T> mSortResult = new ArrayList<T>();
  
  private final HashSet<T> mSortTmpMarked = new HashSet<T>();
  
  private void dfs(T paramT, ArrayList<T> paramArrayList, HashSet<T> paramHashSet) {
    if (paramArrayList.contains(paramT))
      return; 
    if (!paramHashSet.contains(paramT)) {
      paramHashSet.add(paramT);
      ArrayList<T> arrayList = (ArrayList)this.mGraph.get(paramT);
      if (arrayList != null) {
        byte b = 0;
        int i = arrayList.size();
        while (b < i) {
          dfs(arrayList.get(b), paramArrayList, paramHashSet);
          b++;
        } 
      } 
      paramHashSet.remove(paramT);
      paramArrayList.add(paramT);
      return;
    } 
    throw new RuntimeException("This graph contains cyclic dependencies");
  }
  
  private ArrayList<T> getEmptyList() {
    ArrayList<T> arrayList1 = (ArrayList)this.mListPool.acquire();
    ArrayList<T> arrayList2 = arrayList1;
    if (arrayList1 == null)
      arrayList2 = new ArrayList(); 
    return arrayList2;
  }
  
  private void poolList(ArrayList<T> paramArrayList) {
    paramArrayList.clear();
    this.mListPool.release(paramArrayList);
  }
  
  public void addEdge(T paramT1, T paramT2) {
    if (this.mGraph.containsKey(paramT1) && this.mGraph.containsKey(paramT2)) {
      ArrayList<T> arrayList1 = (ArrayList)this.mGraph.get(paramT1);
      ArrayList<T> arrayList2 = arrayList1;
      if (arrayList1 == null) {
        arrayList2 = getEmptyList();
        this.mGraph.put(paramT1, arrayList2);
      } 
      arrayList2.add(paramT2);
      return;
    } 
    throw new IllegalArgumentException("All nodes must be present in the graph before being added as an edge");
  }
  
  public void addNode(T paramT) {
    if (!this.mGraph.containsKey(paramT))
      this.mGraph.put(paramT, null); 
  }
  
  public void clear() {
    int i = this.mGraph.size();
    for (byte b = 0; b < i; b++) {
      ArrayList<T> arrayList = (ArrayList)this.mGraph.valueAt(b);
      if (arrayList != null)
        poolList(arrayList); 
    } 
    this.mGraph.clear();
  }
  
  public boolean contains(T paramT) {
    return this.mGraph.containsKey(paramT);
  }
  
  public List getIncomingEdges(T paramT) {
    return (List)this.mGraph.get(paramT);
  }
  
  public List<T> getOutgoingEdges(T paramT) {
    int i = this.mGraph.size();
    ArrayList<Object> arrayList = null;
    byte b = 0;
    while (b < i) {
      ArrayList arrayList1 = (ArrayList)this.mGraph.valueAt(b);
      ArrayList<Object> arrayList2 = arrayList;
      if (arrayList1 != null) {
        arrayList2 = arrayList;
        if (arrayList1.contains(paramT)) {
          arrayList2 = arrayList;
          if (arrayList == null)
            arrayList2 = new ArrayList(); 
          arrayList2.add(this.mGraph.keyAt(b));
        } 
      } 
      b++;
      arrayList = arrayList2;
    } 
    return arrayList;
  }
  
  public ArrayList<T> getSortedList() {
    this.mSortResult.clear();
    this.mSortTmpMarked.clear();
    int i = this.mGraph.size();
    for (byte b = 0; b < i; b++)
      dfs((T)this.mGraph.keyAt(b), this.mSortResult, this.mSortTmpMarked); 
    return this.mSortResult;
  }
  
  public boolean hasOutgoingEdges(T paramT) {
    int i = this.mGraph.size();
    for (byte b = 0; b < i; b++) {
      ArrayList arrayList = (ArrayList)this.mGraph.valueAt(b);
      if (arrayList != null && arrayList.contains(paramT))
        return true; 
    } 
    return false;
  }
  
  int size() {
    return this.mGraph.size();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\DirectedAcyclicGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */