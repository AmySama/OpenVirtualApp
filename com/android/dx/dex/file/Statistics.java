package com.android.dx.dex.file;

import com.android.dx.util.AnnotatedOutput;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

public final class Statistics {
  private final HashMap<String, Data> dataMap = new HashMap<String, Data>(50);
  
  public void add(Item paramItem) {
    String str = paramItem.typeName();
    Data data = this.dataMap.get(str);
    if (data == null) {
      this.dataMap.put(str, new Data(paramItem, str));
    } else {
      data.add(paramItem);
    } 
  }
  
  public void addAll(Section paramSection) {
    Iterator<? extends Item> iterator = paramSection.items().iterator();
    while (iterator.hasNext())
      add(iterator.next()); 
  }
  
  public String toHuman() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Statistics:\n");
    TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
    for (Data data : this.dataMap.values())
      treeMap.put(data.name, data); 
    Iterator<Data> iterator = treeMap.values().iterator();
    while (iterator.hasNext())
      stringBuilder.append(((Data)iterator.next()).toHuman()); 
    return stringBuilder.toString();
  }
  
  public final void writeAnnotation(AnnotatedOutput paramAnnotatedOutput) {
    if (this.dataMap.size() == 0)
      return; 
    paramAnnotatedOutput.annotate(0, "\nstatistics:\n");
    TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
    for (Data data : this.dataMap.values())
      treeMap.put(data.name, data); 
    Iterator<Data> iterator = treeMap.values().iterator();
    while (iterator.hasNext())
      ((Data)iterator.next()).writeAnnotation(paramAnnotatedOutput); 
  }
  
  private static class Data {
    private int count;
    
    private int largestSize;
    
    private final String name;
    
    private int smallestSize;
    
    private int totalSize;
    
    public Data(Item param1Item, String param1String) {
      int i = param1Item.writeSize();
      this.name = param1String;
      this.count = 1;
      this.totalSize = i;
      this.largestSize = i;
      this.smallestSize = i;
    }
    
    public void add(Item param1Item) {
      int i = param1Item.writeSize();
      this.count++;
      this.totalSize += i;
      if (i > this.largestSize)
        this.largestSize = i; 
      if (i < this.smallestSize)
        this.smallestSize = i; 
    }
    
    public String toHuman() {
      String str;
      StringBuilder stringBuilder1 = new StringBuilder();
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("  ");
      stringBuilder2.append(this.name);
      stringBuilder2.append(": ");
      stringBuilder2.append(this.count);
      stringBuilder2.append(" item");
      if (this.count == 1) {
        str = "";
      } else {
        str = "s";
      } 
      stringBuilder2.append(str);
      stringBuilder2.append("; ");
      stringBuilder2.append(this.totalSize);
      stringBuilder2.append(" bytes total\n");
      stringBuilder1.append(stringBuilder2.toString());
      if (this.smallestSize == this.largestSize) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("    ");
        stringBuilder.append(this.smallestSize);
        stringBuilder.append(" bytes/item\n");
        stringBuilder1.append(stringBuilder.toString());
      } else {
        int i = this.totalSize / this.count;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("    ");
        stringBuilder.append(this.smallestSize);
        stringBuilder.append("..");
        stringBuilder.append(this.largestSize);
        stringBuilder.append(" bytes/item; average ");
        stringBuilder.append(i);
        stringBuilder.append("\n");
        stringBuilder1.append(stringBuilder.toString());
      } 
      return stringBuilder1.toString();
    }
    
    public void writeAnnotation(AnnotatedOutput param1AnnotatedOutput) {
      param1AnnotatedOutput.annotate(toHuman());
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\Statistics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */