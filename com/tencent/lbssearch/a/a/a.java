package com.tencent.lbssearch.a.a;

import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

final class a implements k<Date>, s<Date> {
  private final DateFormat a;
  
  private final DateFormat b;
  
  private final DateFormat c;
  
  a() {
    this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
  }
  
  public a(int paramInt1, int paramInt2) {
    this(DateFormat.getDateTimeInstance(paramInt1, paramInt2, Locale.US), DateFormat.getDateTimeInstance(paramInt1, paramInt2));
  }
  
  a(String paramString) {
    this(new SimpleDateFormat(paramString, Locale.US), new SimpleDateFormat(paramString));
  }
  
  a(DateFormat paramDateFormat1, DateFormat paramDateFormat2) {
    this.a = paramDateFormat1;
    this.b = paramDateFormat2;
    paramDateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
    this.c = paramDateFormat1;
    paramDateFormat1.setTimeZone(TimeZone.getTimeZone("UTC"));
  }
  
  private Date a(l paraml) {
    DateFormat dateFormat = this.b;
    /* monitor enter ClassFileLocalVariableReferenceExpression{type=ObjectType{java/text/DateFormat}, name=null} */
    try {
      Date date = this.b.parse(paraml.c());
      /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/text/DateFormat}, name=null} */
      return date;
    } catch (ParseException parseException) {
      try {
        Date date = this.a.parse(paraml.c());
        /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/text/DateFormat}, name=null} */
        return date;
      } catch (ParseException parseException1) {
        try {
          Date date = this.c.parse(paraml.c());
          /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/text/DateFormat}, name=null} */
          return date;
        } catch (ParseException parseException2) {
          t t = new t();
          this(paraml.c(), parseException2);
          throw t;
        } 
      } 
    } finally {}
    /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/text/DateFormat}, name=null} */
    throw paraml;
  }
  
  public l a(Date paramDate, Type paramType, r paramr) {
    synchronized (this.b) {
      String str = this.a.format(paramDate);
      q q = new q();
      this(str);
      return q;
    } 
  }
  
  public Date a(l paraml, Type paramType, j paramj) throws p {
    if (paraml instanceof q) {
      Date date = a(paraml);
      if (paramType == Date.class)
        return date; 
      if (paramType == Timestamp.class)
        return new Timestamp(date.getTime()); 
      if (paramType == Date.class)
        return new Date(date.getTime()); 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(getClass());
      stringBuilder.append(" cannot deserialize to ");
      stringBuilder.append(paramType);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    throw new p("The date should be a string value");
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(a.class.getSimpleName());
    stringBuilder.append('(');
    stringBuilder.append(this.b.getClass().getSimpleName());
    stringBuilder.append(')');
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */