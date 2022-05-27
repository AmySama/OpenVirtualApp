package com.lody.virtual.helper.utils;

import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlUtils {
  public static void nextElement(XmlPullParser paramXmlPullParser) throws XmlPullParserException, IOException {
    while (true) {
      int i = paramXmlPullParser.next();
      if (i != 2 && i != 1)
        continue; 
      break;
    } 
  }
  
  public static void skipCurrentTag(XmlPullParser paramXmlPullParser) throws XmlPullParserException, IOException {
    int i = paramXmlPullParser.getDepth();
    while (true) {
      int j = paramXmlPullParser.next();
      if (j != 1 && (j != 3 || paramXmlPullParser.getDepth() > i))
        continue; 
      break;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\XmlUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */