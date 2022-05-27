package android.support.v4.os;

import java.util.Locale;

interface LocaleListInterface {
  boolean equals(Object paramObject);
  
  Locale get(int paramInt);
  
  Locale getFirstMatch(String[] paramArrayOfString);
  
  Object getLocaleList();
  
  int hashCode();
  
  int indexOf(Locale paramLocale);
  
  boolean isEmpty();
  
  void setLocaleList(Locale... paramVarArgs);
  
  int size();
  
  String toLanguageTags();
  
  String toString();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\os\LocaleListInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */