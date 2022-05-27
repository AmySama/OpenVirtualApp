package com.lody.virtual.helper.utils;

import android.content.pm.Signature;
import java.util.ArrayList;

public class SignaturesUtils {
  public static int compareSignatures(Signature[] paramArrayOfSignature1, Signature[] paramArrayOfSignature2) {
    byte b = 1;
    if (paramArrayOfSignature1 == null) {
      if (paramArrayOfSignature2 != null)
        b = -1; 
      return b;
    } 
    if (paramArrayOfSignature2 == null)
      return -2; 
    int i = paramArrayOfSignature1.length;
    int j = paramArrayOfSignature2.length;
    b = -3;
    if (i != j)
      return -3; 
    if (paramArrayOfSignature1.length == 1) {
      if (paramArrayOfSignature1[0].equals(paramArrayOfSignature2[0]))
        b = 0; 
      return b;
    } 
    ArrayList<Signature> arrayList2 = new ArrayList();
    j = paramArrayOfSignature1.length;
    for (b = 0; b < j; b++)
      arrayList2.add(paramArrayOfSignature1[b]); 
    ArrayList<Signature> arrayList1 = new ArrayList();
    j = paramArrayOfSignature2.length;
    for (b = 0; b < j; b++)
      arrayList1.add(paramArrayOfSignature2[b]); 
    return arrayList2.equals(arrayList1) ? 0 : -3;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\SignaturesUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */