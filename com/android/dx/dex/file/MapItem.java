package com.android.dx.dex.file;

import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.util.ArrayList;
import java.util.Iterator;

public final class MapItem extends OffsettedItem {
  private static final int ALIGNMENT = 4;
  
  private static final int WRITE_SIZE = 12;
  
  private final Item firstItem;
  
  private final int itemCount;
  
  private final Item lastItem;
  
  private final Section section;
  
  private final ItemType type;
  
  private MapItem(ItemType paramItemType, Section paramSection, Item paramItem1, Item paramItem2, int paramInt) {
    super(4, 12);
    if (paramItemType != null) {
      if (paramSection != null) {
        if (paramItem1 != null) {
          if (paramItem2 != null) {
            if (paramInt > 0) {
              this.type = paramItemType;
              this.section = paramSection;
              this.firstItem = paramItem1;
              this.lastItem = paramItem2;
              this.itemCount = paramInt;
              return;
            } 
            throw new IllegalArgumentException("itemCount <= 0");
          } 
          throw new NullPointerException("lastItem == null");
        } 
        throw new NullPointerException("firstItem == null");
      } 
      throw new NullPointerException("section == null");
    } 
    throw new NullPointerException("type == null");
  }
  
  private MapItem(Section paramSection) {
    super(4, 12);
    if (paramSection != null) {
      this.type = ItemType.TYPE_MAP_LIST;
      this.section = paramSection;
      this.firstItem = null;
      this.lastItem = null;
      this.itemCount = 1;
      return;
    } 
    throw new NullPointerException("section == null");
  }
  
  public static void addMap(Section[] paramArrayOfSection, MixedItemSection paramMixedItemSection) {
    if (paramArrayOfSection != null) {
      if (paramMixedItemSection.items().size() == 0) {
        ArrayList<MapItem> arrayList = new ArrayList(50);
        int i = paramArrayOfSection.length;
        for (byte b = 0; b < i; b++) {
          Item item1;
          Item item2;
          Section section = paramArrayOfSection[b];
          Iterator<? extends Item> iterator = section.items().iterator();
          ItemType itemType1 = null;
          ItemType itemType2 = itemType1;
          ItemType itemType3 = itemType2;
          int j = 0;
          while (iterator.hasNext()) {
            Item item4;
            Item item3 = iterator.next();
            ItemType itemType4 = item3.itemType();
            ItemType itemType5 = itemType1;
            ItemType itemType6 = itemType2;
            int k = j;
            if (itemType4 != itemType1) {
              if (j)
                arrayList.add(new MapItem(itemType1, section, (Item)itemType2, (Item)itemType3, j)); 
              item4 = item3;
              itemType5 = itemType4;
              k = 0;
            } 
            j = k + 1;
            itemType1 = itemType5;
            item1 = item4;
            item2 = item3;
          } 
          if (j != 0) {
            arrayList.add(new MapItem(itemType1, section, item1, item2, j));
          } else if (section == paramMixedItemSection) {
            arrayList.add(new MapItem(paramMixedItemSection));
          } 
        } 
        paramMixedItemSection.add(new UniformListItem<MapItem>(ItemType.TYPE_MAP_LIST, arrayList));
        return;
      } 
      throw new IllegalArgumentException("mapSection.items().size() != 0");
    } 
    throw new NullPointerException("sections == null");
  }
  
  public void addContents(DexFile paramDexFile) {}
  
  public ItemType itemType() {
    return ItemType.TYPE_MAP_ITEM;
  }
  
  public final String toHuman() {
    return toString();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(100);
    stringBuilder.append(getClass().getName());
    stringBuilder.append('{');
    stringBuilder.append(this.section.toString());
    stringBuilder.append(' ');
    stringBuilder.append(this.type.toHuman());
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  protected void writeTo0(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    int j;
    int i = this.type.getMapValue();
    Item item = this.firstItem;
    if (item == null) {
      j = this.section.getFileOffset();
    } else {
      j = this.section.getAbsoluteItemOffset(item);
    } 
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(offsetString());
      stringBuilder.append(' ');
      stringBuilder.append(this.type.getTypeName());
      stringBuilder.append(" map");
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  type:   ");
      stringBuilder.append(Hex.u2(i));
      stringBuilder.append(" // ");
      stringBuilder.append(this.type.toString());
      paramAnnotatedOutput.annotate(2, stringBuilder.toString());
      paramAnnotatedOutput.annotate(2, "  unused: 0");
      stringBuilder = new StringBuilder();
      stringBuilder.append("  size:   ");
      stringBuilder.append(Hex.u4(this.itemCount));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  offset: ");
      stringBuilder.append(Hex.u4(j));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
    } 
    paramAnnotatedOutput.writeShort(i);
    paramAnnotatedOutput.writeShort(0);
    paramAnnotatedOutput.writeInt(this.itemCount);
    paramAnnotatedOutput.writeInt(j);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\MapItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */