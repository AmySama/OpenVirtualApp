package com.android.dx.dex.file;

import com.android.dx.rop.cst.CstString;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;

public final class HeaderItem extends IndexedItem {
  public void addContents(DexFile paramDexFile) {}
  
  public ItemType itemType() {
    return ItemType.TYPE_HEADER_ITEM;
  }
  
  public int writeSize() {
    return 112;
  }
  
  public void writeTo(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    int i = paramDexFile.getMap().getFileOffset();
    Section section1 = paramDexFile.getFirstDataSection();
    Section section2 = paramDexFile.getLastDataSection();
    int j = section1.getFileOffset();
    int k = section2.getFileOffset() + section2.writeSize() - j;
    String str = paramDexFile.getDexOptions().getMagic();
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("magic: ");
      stringBuilder.append((new CstString(str)).toQuoted());
      paramAnnotatedOutput.annotate(8, stringBuilder.toString());
      paramAnnotatedOutput.annotate(4, "checksum");
      paramAnnotatedOutput.annotate(20, "signature");
      stringBuilder = new StringBuilder();
      stringBuilder.append("file_size:       ");
      stringBuilder.append(Hex.u4(paramDexFile.getFileSize()));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("header_size:     ");
      stringBuilder.append(Hex.u4(112));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("endian_tag:      ");
      stringBuilder.append(Hex.u4(305419896));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      paramAnnotatedOutput.annotate(4, "link_size:       0");
      paramAnnotatedOutput.annotate(4, "link_off:        0");
      stringBuilder = new StringBuilder();
      stringBuilder.append("map_off:         ");
      stringBuilder.append(Hex.u4(i));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
    } 
    for (byte b = 0; b < 8; b++)
      paramAnnotatedOutput.writeByte(str.charAt(b)); 
    paramAnnotatedOutput.writeZeroes(24);
    paramAnnotatedOutput.writeInt(paramDexFile.getFileSize());
    paramAnnotatedOutput.writeInt(112);
    paramAnnotatedOutput.writeInt(305419896);
    paramAnnotatedOutput.writeZeroes(8);
    paramAnnotatedOutput.writeInt(i);
    paramDexFile.getStringIds().writeHeaderPart(paramAnnotatedOutput);
    paramDexFile.getTypeIds().writeHeaderPart(paramAnnotatedOutput);
    paramDexFile.getProtoIds().writeHeaderPart(paramAnnotatedOutput);
    paramDexFile.getFieldIds().writeHeaderPart(paramAnnotatedOutput);
    paramDexFile.getMethodIds().writeHeaderPart(paramAnnotatedOutput);
    paramDexFile.getClassDefs().writeHeaderPart(paramAnnotatedOutput);
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("data_size:       ");
      stringBuilder.append(Hex.u4(k));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("data_off:        ");
      stringBuilder.append(Hex.u4(j));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
    } 
    paramAnnotatedOutput.writeInt(k);
    paramAnnotatedOutput.writeInt(j);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\HeaderItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */