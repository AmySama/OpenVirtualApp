package com.lody.virtual.helper.utils;

import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public interface XmlSerializerAndParser<T> {
  T createFromXml(XmlPullParser paramXmlPullParser) throws IOException, XmlPullParserException;
  
  void writeAsXml(T paramT, XmlSerializer paramXmlSerializer) throws IOException;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\XmlSerializerAndParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */