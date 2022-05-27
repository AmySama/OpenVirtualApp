package com.tencent.lbssearch.a.a.b.a;

import com.tencent.lbssearch.a.a.a.b;
import com.tencent.lbssearch.a.a.b.f;
import com.tencent.lbssearch.a.a.d.b;
import com.tencent.lbssearch.a.a.d.c;
import com.tencent.lbssearch.a.a.f;
import com.tencent.lbssearch.a.a.i;
import com.tencent.lbssearch.a.a.m;
import com.tencent.lbssearch.a.a.n;
import com.tencent.lbssearch.a.a.o;
import com.tencent.lbssearch.a.a.q;
import com.tencent.lbssearch.a.a.t;
import com.tencent.lbssearch.a.a.w;
import com.tencent.lbssearch.a.a.x;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

public final class l {
  public static final w<StringBuffer> A;
  
  public static final x B;
  
  public static final w<URL> C;
  
  public static final x D;
  
  public static final w<URI> E;
  
  public static final x F;
  
  public static final w<InetAddress> G;
  
  public static final x H;
  
  public static final w<UUID> I;
  
  public static final x J;
  
  public static final x K;
  
  public static final w<Calendar> L;
  
  public static final x M;
  
  public static final w<Locale> N;
  
  public static final x O;
  
  public static final w<com.tencent.lbssearch.a.a.l> P;
  
  public static final x Q;
  
  public static final x R;
  
  public static final w<Class> a;
  
  public static final x b;
  
  public static final w<BitSet> c;
  
  public static final x d;
  
  public static final w<Boolean> e = new w<Boolean>() {
      public Boolean a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
        if (param1a.f() == b.i) {
          param1a.j();
          return null;
        } 
        return (param1a.f() == b.f) ? Boolean.valueOf(Boolean.parseBoolean(param1a.h())) : Boolean.valueOf(param1a.i());
      }
      
      public void a(c param1c, Boolean param1Boolean) throws IOException {
        if (param1Boolean == null) {
          param1c.f();
          return;
        } 
        param1c.a(param1Boolean.booleanValue());
      }
    };
  
  public static final w<Boolean> f = new w<Boolean>() {
      public Boolean a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
        if (param1a.f() == b.i) {
          param1a.j();
          return null;
        } 
        return Boolean.valueOf(param1a.h());
      }
      
      public void a(c param1c, Boolean param1Boolean) throws IOException {
        String str;
        if (param1Boolean == null) {
          str = "null";
        } else {
          str = str.toString();
        } 
        param1c.b(str);
      }
    };
  
  public static final x g = a(boolean.class, (Class)Boolean.class, (w)e);
  
  public static final w<Number> h = new w<Number>() {
      public Number a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
        if (param1a.f() == b.i) {
          param1a.j();
          return null;
        } 
        try {
          byte b = (byte)param1a.m();
          return Byte.valueOf(b);
        } catch (NumberFormatException numberFormatException) {
          throw new t(numberFormatException);
        } 
      }
      
      public void a(c param1c, Number param1Number) throws IOException {
        param1c.a(param1Number);
      }
    };
  
  public static final x i = a(byte.class, (Class)Byte.class, (w)h);
  
  public static final w<Number> j = new w<Number>() {
      public Number a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
        if (param1a.f() == b.i) {
          param1a.j();
          return null;
        } 
        try {
          short s = (short)param1a.m();
          return Short.valueOf(s);
        } catch (NumberFormatException numberFormatException) {
          throw new t(numberFormatException);
        } 
      }
      
      public void a(c param1c, Number param1Number) throws IOException {
        param1c.a(param1Number);
      }
    };
  
  public static final x k = a(short.class, (Class)Short.class, (w)j);
  
  public static final w<Number> l = new w<Number>() {
      public Number a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
        if (param1a.f() == b.i) {
          param1a.j();
          return null;
        } 
        try {
          int i = param1a.m();
          return Integer.valueOf(i);
        } catch (NumberFormatException numberFormatException) {
          throw new t(numberFormatException);
        } 
      }
      
      public void a(c param1c, Number param1Number) throws IOException {
        param1c.a(param1Number);
      }
    };
  
  public static final x m = a(int.class, (Class)Integer.class, (w)l);
  
  public static final w<Number> n = new w<Number>() {
      public Number a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
        if (param1a.f() == b.i) {
          param1a.j();
          return null;
        } 
        try {
          long l = param1a.l();
          return Long.valueOf(l);
        } catch (NumberFormatException numberFormatException) {
          throw new t(numberFormatException);
        } 
      }
      
      public void a(c param1c, Number param1Number) throws IOException {
        param1c.a(param1Number);
      }
    };
  
  public static final w<Number> o = new w<Number>() {
      public Number a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
        if (param1a.f() == b.i) {
          param1a.j();
          return null;
        } 
        return Float.valueOf((float)param1a.k());
      }
      
      public void a(c param1c, Number param1Number) throws IOException {
        param1c.a(param1Number);
      }
    };
  
  public static final w<Number> p = new w<Number>() {
      public Number a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
        if (param1a.f() == b.i) {
          param1a.j();
          return null;
        } 
        return Double.valueOf(param1a.k());
      }
      
      public void a(c param1c, Number param1Number) throws IOException {
        param1c.a(param1Number);
      }
    };
  
  public static final w<Number> q;
  
  public static final x r;
  
  public static final w<Character> s = new w<Character>() {
      public Character a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
        if (param1a.f() == b.i) {
          param1a.j();
          return null;
        } 
        String str = param1a.h();
        if (str.length() == 1)
          return Character.valueOf(str.charAt(0)); 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expecting character, got: ");
        stringBuilder.append(str);
        throw new t(stringBuilder.toString());
      }
      
      public void a(c param1c, Character param1Character) throws IOException {
        String str;
        if (param1Character == null) {
          param1Character = null;
        } else {
          str = String.valueOf(param1Character);
        } 
        param1c.b(str);
      }
    };
  
  public static final x t = a(char.class, (Class)Character.class, (w)s);
  
  public static final w<String> u = new w<String>() {
      public String a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
        b b = param1a.f();
        if (b == b.i) {
          param1a.j();
          return null;
        } 
        return (b == b.h) ? Boolean.toString(param1a.i()) : param1a.h();
      }
      
      public void a(c param1c, String param1String) throws IOException {
        param1c.b(param1String);
      }
    };
  
  public static final w<BigDecimal> v = new w<BigDecimal>() {
      public BigDecimal a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
        if (param1a.f() == b.i) {
          param1a.j();
          return null;
        } 
        try {
          return new BigDecimal(param1a.h());
        } catch (NumberFormatException numberFormatException) {
          throw new t(numberFormatException);
        } 
      }
      
      public void a(c param1c, BigDecimal param1BigDecimal) throws IOException {
        param1c.a(param1BigDecimal);
      }
    };
  
  public static final w<BigInteger> w = new w<BigInteger>() {
      public BigInteger a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
        if (param1a.f() == b.i) {
          param1a.j();
          return null;
        } 
        try {
          return new BigInteger(param1a.h());
        } catch (NumberFormatException numberFormatException) {
          throw new t(numberFormatException);
        } 
      }
      
      public void a(c param1c, BigInteger param1BigInteger) throws IOException {
        param1c.a(param1BigInteger);
      }
    };
  
  public static final x x = a(String.class, u);
  
  public static final w<StringBuilder> y;
  
  public static final x z;
  
  static {
    w<StringBuilder> w9 = new w<StringBuilder>() {
        public StringBuilder a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
          if (param1a.f() == b.i) {
            param1a.j();
            return null;
          } 
          return new StringBuilder(param1a.h());
        }
        
        public void a(c param1c, StringBuilder param1StringBuilder) throws IOException {
          String str;
          if (param1StringBuilder == null) {
            param1StringBuilder = null;
          } else {
            str = param1StringBuilder.toString();
          } 
          param1c.b(str);
        }
      };
    y = w9;
    z = a(StringBuilder.class, w9);
    w<StringBuffer> w8 = new w<StringBuffer>() {
        public StringBuffer a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
          if (param1a.f() == b.i) {
            param1a.j();
            return null;
          } 
          return new StringBuffer(param1a.h());
        }
        
        public void a(c param1c, StringBuffer param1StringBuffer) throws IOException {
          String str;
          if (param1StringBuffer == null) {
            param1StringBuffer = null;
          } else {
            str = param1StringBuffer.toString();
          } 
          param1c.b(str);
        }
      };
    A = w8;
    B = a(StringBuffer.class, w8);
    w<URL> w7 = new w<URL>() {
        public URL a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
          URL uRL;
          b b1 = param1a.f();
          b b2 = b.i;
          String str2 = null;
          if (b1 == b2) {
            param1a.j();
            return null;
          } 
          String str1 = param1a.h();
          if ("null".equals(str1)) {
            str1 = str2;
          } else {
            uRL = new URL(str1);
          } 
          return uRL;
        }
        
        public void a(c param1c, URL param1URL) throws IOException {
          String str;
          if (param1URL == null) {
            param1URL = null;
          } else {
            str = param1URL.toExternalForm();
          } 
          param1c.b(str);
        }
      };
    C = w7;
    D = a(URL.class, w7);
    w<URI> w6 = new w<URI>() {
        public URI a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
          b b1 = param1a.f();
          b b2 = b.i;
          String str = null;
          if (b1 == b2) {
            param1a.j();
            return null;
          } 
          try {
            URI uRI;
            String str1 = param1a.h();
            if ("null".equals(str1)) {
              str1 = str;
            } else {
              uRI = new URI(str1);
            } 
            return uRI;
          } catch (URISyntaxException uRISyntaxException) {
            throw new m(uRISyntaxException);
          } 
        }
        
        public void a(c param1c, URI param1URI) throws IOException {
          String str;
          if (param1URI == null) {
            param1URI = null;
          } else {
            str = param1URI.toASCIIString();
          } 
          param1c.b(str);
        }
      };
    E = w6;
    F = a(URI.class, w6);
    w<InetAddress> w5 = new w<InetAddress>() {
        public InetAddress a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
          if (param1a.f() == b.i) {
            param1a.j();
            return null;
          } 
          return InetAddress.getByName(param1a.h());
        }
        
        public void a(c param1c, InetAddress param1InetAddress) throws IOException {
          String str;
          if (param1InetAddress == null) {
            param1InetAddress = null;
          } else {
            str = param1InetAddress.getHostAddress();
          } 
          param1c.b(str);
        }
      };
    G = w5;
    H = b(InetAddress.class, w5);
    w<UUID> w4 = new w<UUID>() {
        public UUID a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
          if (param1a.f() == b.i) {
            param1a.j();
            return null;
          } 
          return UUID.fromString(param1a.h());
        }
        
        public void a(c param1c, UUID param1UUID) throws IOException {
          String str;
          if (param1UUID == null) {
            param1UUID = null;
          } else {
            str = param1UUID.toString();
          } 
          param1c.b(str);
        }
      };
    I = w4;
    J = a(UUID.class, w4);
    K = new x() {
        public <T> w<T> a(f param1f, com.tencent.lbssearch.a.a.c.a<T> param1a) {
          return (w)((param1a.a() != Timestamp.class) ? null : new w<Timestamp>(this, param1f.a(Date.class)) {
              public Timestamp a(com.tencent.lbssearch.a.a.d.a param2a) throws IOException {
                Date date = (Date)this.a.b(param2a);
                if (date != null) {
                  date = new Timestamp(date.getTime());
                } else {
                  date = null;
                } 
                return (Timestamp)date;
              }
              
              public void a(c param2c, Timestamp param2Timestamp) throws IOException {
                this.a.a(param2c, param2Timestamp);
              }
            });
        }
      };
    w<Calendar> w3 = new w<Calendar>() {
        public Calendar a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
          if (param1a.f() == b.i) {
            param1a.j();
            return null;
          } 
          param1a.c();
          int i = 0;
          int j = 0;
          int k = 0;
          int m = 0;
          int n = 0;
          int i1 = 0;
          while (param1a.f() != b.d) {
            String str = param1a.g();
            int i2 = param1a.m();
            if ("year".equals(str)) {
              i = i2;
              continue;
            } 
            if ("month".equals(str)) {
              j = i2;
              continue;
            } 
            if ("dayOfMonth".equals(str)) {
              k = i2;
              continue;
            } 
            if ("hourOfDay".equals(str)) {
              m = i2;
              continue;
            } 
            if ("minute".equals(str)) {
              n = i2;
              continue;
            } 
            if ("second".equals(str))
              i1 = i2; 
          } 
          param1a.d();
          return new GregorianCalendar(i, j, k, m, n, i1);
        }
        
        public void a(c param1c, Calendar param1Calendar) throws IOException {
          if (param1Calendar == null) {
            param1c.f();
            return;
          } 
          param1c.d();
          param1c.a("year");
          param1c.a(param1Calendar.get(1));
          param1c.a("month");
          param1c.a(param1Calendar.get(2));
          param1c.a("dayOfMonth");
          param1c.a(param1Calendar.get(5));
          param1c.a("hourOfDay");
          param1c.a(param1Calendar.get(11));
          param1c.a("minute");
          param1c.a(param1Calendar.get(12));
          param1c.a("second");
          param1c.a(param1Calendar.get(13));
          param1c.e();
        }
      };
    L = w3;
    M = b(Calendar.class, (Class)GregorianCalendar.class, w3);
    w<Locale> w2 = new w<Locale>() {
        public Locale a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
          b b1 = param1a.f();
          b b2 = b.i;
          String str = null;
          if (b1 == b2) {
            param1a.j();
            return null;
          } 
          StringTokenizer stringTokenizer = new StringTokenizer(param1a.h(), "_");
          if (stringTokenizer.hasMoreElements()) {
            String str1 = stringTokenizer.nextToken();
          } else {
            param1a = null;
          } 
          if (stringTokenizer.hasMoreElements()) {
            String str1 = stringTokenizer.nextToken();
          } else {
            b2 = null;
          } 
          if (stringTokenizer.hasMoreElements())
            str = stringTokenizer.nextToken(); 
          return (b2 == null && str == null) ? new Locale((String)param1a) : ((str == null) ? new Locale((String)param1a, (String)b2) : new Locale((String)param1a, (String)b2, str));
        }
        
        public void a(c param1c, Locale param1Locale) throws IOException {
          String str;
          if (param1Locale == null) {
            param1Locale = null;
          } else {
            str = param1Locale.toString();
          } 
          param1c.b(str);
        }
      };
    N = w2;
    O = a(Locale.class, w2);
    w<com.tencent.lbssearch.a.a.l> w1 = new w<com.tencent.lbssearch.a.a.l>() {
        public com.tencent.lbssearch.a.a.l a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
          o o;
          i i;
          switch (l.null.a[param1a.f().ordinal()]) {
            default:
              throw new IllegalArgumentException();
            case 6:
              o = new o();
              param1a.c();
              while (param1a.e())
                o.a(param1a.g(), a(param1a)); 
              param1a.d();
              return (com.tencent.lbssearch.a.a.l)o;
            case 5:
              i = new i();
              param1a.a();
              while (param1a.e())
                i.a(a(param1a)); 
              param1a.b();
              return (com.tencent.lbssearch.a.a.l)i;
            case 4:
              param1a.j();
              return (com.tencent.lbssearch.a.a.l)n.a;
            case 3:
              return (com.tencent.lbssearch.a.a.l)new q(param1a.h());
            case 2:
              return (com.tencent.lbssearch.a.a.l)new q(Boolean.valueOf(param1a.i()));
            case 1:
              break;
          } 
          return (com.tencent.lbssearch.a.a.l)new q((Number)new f(param1a.h()));
        }
        
        public void a(c param1c, com.tencent.lbssearch.a.a.l param1l) throws IOException {
          q q;
          if (param1l == null || param1l.l()) {
            param1c.f();
            return;
          } 
          if (param1l.k()) {
            q = param1l.o();
            if (q.q()) {
              param1c.a(q.b());
            } else if (q.a()) {
              param1c.a(q.h());
            } else {
              param1c.b(q.c());
            } 
          } else {
            Iterator<com.tencent.lbssearch.a.a.l> iterator;
            if (q.i()) {
              param1c.b();
              iterator = q.n().iterator();
              while (iterator.hasNext())
                a(param1c, iterator.next()); 
              param1c.c();
            } else if (iterator.j()) {
              param1c.d();
              for (Map.Entry entry : iterator.m().a()) {
                param1c.a((String)entry.getKey());
                a(param1c, (com.tencent.lbssearch.a.a.l)entry.getValue());
              } 
              param1c.e();
            } else {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Couldn't write ");
              stringBuilder.append(iterator.getClass());
              throw new IllegalArgumentException(stringBuilder.toString());
            } 
          } 
        }
      };
    P = w1;
    Q = b(com.tencent.lbssearch.a.a.l.class, w1);
    R = a();
  }
  
  public static x a() {
    return new x() {
        public <T> w<T> a(f param1f, com.tencent.lbssearch.a.a.c.a<T> param1a) {
          Class<?> clazz2 = param1a.a();
          if (!Enum.class.isAssignableFrom(clazz2) || clazz2 == Enum.class)
            return null; 
          Class<?> clazz1 = clazz2;
          if (!clazz2.isEnum())
            clazz1 = clazz2.getSuperclass(); 
          return new l.a(clazz1);
        }
      };
  }
  
  public static <TT> x a(com.tencent.lbssearch.a.a.c.a<TT> parama, w<TT> paramw) {
    return new x(parama, paramw) {
        public <T> w<T> a(f param1f, com.tencent.lbssearch.a.a.c.a<T> param1a) {
          if (param1a.equals(this.a)) {
            w w1 = this.b;
          } else {
            param1f = null;
          } 
          return (w<T>)param1f;
        }
      };
  }
  
  public static <TT> x a(Class<TT> paramClass, w<TT> paramw) {
    return new x(paramClass, paramw) {
        public <T> w<T> a(f param1f, com.tencent.lbssearch.a.a.c.a<T> param1a) {
          if (param1a.a() == this.a) {
            w w1 = this.b;
          } else {
            param1f = null;
          } 
          return (w<T>)param1f;
        }
        
        public String toString() {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Factory[type=");
          stringBuilder.append(this.a.getName());
          stringBuilder.append(",adapter=");
          stringBuilder.append(this.b);
          stringBuilder.append("]");
          return stringBuilder.toString();
        }
      };
  }
  
  public static <TT> x a(Class<TT> paramClass1, Class<TT> paramClass2, w<? super TT> paramw) {
    return new x(paramClass1, paramClass2, paramw) {
        public <T> w<T> a(f param1f, com.tencent.lbssearch.a.a.c.a<T> param1a) {
          null = param1a.a();
          return (null == this.a || null == this.b) ? this.c : null;
        }
        
        public String toString() {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Factory[type=");
          stringBuilder.append(this.b.getName());
          stringBuilder.append("+");
          stringBuilder.append(this.a.getName());
          stringBuilder.append(",adapter=");
          stringBuilder.append(this.c);
          stringBuilder.append("]");
          return stringBuilder.toString();
        }
      };
  }
  
  public static <TT> x b(Class<TT> paramClass, w<TT> paramw) {
    return new x(paramClass, paramw) {
        public <T> w<T> a(f param1f, com.tencent.lbssearch.a.a.c.a<T> param1a) {
          if (this.a.isAssignableFrom(param1a.a())) {
            w w1 = this.b;
          } else {
            param1f = null;
          } 
          return (w<T>)param1f;
        }
        
        public String toString() {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Factory[typeHierarchy=");
          stringBuilder.append(this.a.getName());
          stringBuilder.append(",adapter=");
          stringBuilder.append(this.b);
          stringBuilder.append("]");
          return stringBuilder.toString();
        }
      };
  }
  
  public static <TT> x b(Class<TT> paramClass, Class<? extends TT> paramClass1, w<? super TT> paramw) {
    return new x(paramClass, paramClass1, paramw) {
        public <T> w<T> a(f param1f, com.tencent.lbssearch.a.a.c.a<T> param1a) {
          null = param1a.a();
          return (null == this.a || null == this.b) ? this.c : null;
        }
        
        public String toString() {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Factory[type=");
          stringBuilder.append(this.a.getName());
          stringBuilder.append("+");
          stringBuilder.append(this.b.getName());
          stringBuilder.append(",adapter=");
          stringBuilder.append(this.c);
          stringBuilder.append("]");
          return stringBuilder.toString();
        }
      };
  }
  
  static {
    w<Class> w12 = new w<Class>() {
        public Class a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
          throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }
        
        public void a(c param1c, Class param1Class) throws IOException {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Attempted to serialize java.lang.Class: ");
          stringBuilder.append(param1Class.getName());
          stringBuilder.append(". Forgot to register a type adapter?");
          throw new UnsupportedOperationException(stringBuilder.toString());
        }
      };
    a = w12;
    b = a(Class.class, w12);
    w<BitSet> w11 = new w<BitSet>() {
        public BitSet a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
          // Byte code:
          //   0: aload_1
          //   1: invokevirtual f : ()Lcom/tencent/lbssearch/a/a/d/b;
          //   4: getstatic com/tencent/lbssearch/a/a/d/b.i : Lcom/tencent/lbssearch/a/a/d/b;
          //   7: if_acmpne -> 16
          //   10: aload_1
          //   11: invokevirtual j : ()V
          //   14: aconst_null
          //   15: areturn
          //   16: new java/util/BitSet
          //   19: dup
          //   20: invokespecial <init> : ()V
          //   23: astore_2
          //   24: aload_1
          //   25: invokevirtual a : ()V
          //   28: aload_1
          //   29: invokevirtual f : ()Lcom/tencent/lbssearch/a/a/d/b;
          //   32: astore_3
          //   33: iconst_0
          //   34: istore #4
          //   36: aload_3
          //   37: getstatic com/tencent/lbssearch/a/a/d/b.b : Lcom/tencent/lbssearch/a/a/d/b;
          //   40: if_acmpeq -> 204
          //   43: getstatic com/tencent/lbssearch/a/a/b/a/l$26.a : [I
          //   46: aload_3
          //   47: invokevirtual ordinal : ()I
          //   50: iaload
          //   51: istore #5
          //   53: iconst_1
          //   54: istore #6
          //   56: iload #5
          //   58: iconst_1
          //   59: if_icmpeq -> 175
          //   62: iload #5
          //   64: iconst_2
          //   65: if_icmpeq -> 166
          //   68: iload #5
          //   70: iconst_3
          //   71: if_icmpne -> 133
          //   74: aload_1
          //   75: invokevirtual h : ()Ljava/lang/String;
          //   78: astore_3
          //   79: aload_3
          //   80: invokestatic parseInt : (Ljava/lang/String;)I
          //   83: istore #5
          //   85: iload #5
          //   87: ifeq -> 93
          //   90: goto -> 182
          //   93: iconst_0
          //   94: istore #6
          //   96: goto -> 182
          //   99: astore_1
          //   100: new java/lang/StringBuilder
          //   103: dup
          //   104: invokespecial <init> : ()V
          //   107: astore_1
          //   108: aload_1
          //   109: ldc 'Error: Expecting: bitset number value (1, 0), Found: '
          //   111: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   114: pop
          //   115: aload_1
          //   116: aload_3
          //   117: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   120: pop
          //   121: new com/tencent/lbssearch/a/a/t
          //   124: dup
          //   125: aload_1
          //   126: invokevirtual toString : ()Ljava/lang/String;
          //   129: invokespecial <init> : (Ljava/lang/String;)V
          //   132: athrow
          //   133: new java/lang/StringBuilder
          //   136: dup
          //   137: invokespecial <init> : ()V
          //   140: astore_1
          //   141: aload_1
          //   142: ldc 'Invalid bitset value type: '
          //   144: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   147: pop
          //   148: aload_1
          //   149: aload_3
          //   150: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
          //   153: pop
          //   154: new com/tencent/lbssearch/a/a/t
          //   157: dup
          //   158: aload_1
          //   159: invokevirtual toString : ()Ljava/lang/String;
          //   162: invokespecial <init> : (Ljava/lang/String;)V
          //   165: athrow
          //   166: aload_1
          //   167: invokevirtual i : ()Z
          //   170: istore #6
          //   172: goto -> 182
          //   175: aload_1
          //   176: invokevirtual m : ()I
          //   179: ifeq -> 93
          //   182: iload #6
          //   184: ifeq -> 193
          //   187: aload_2
          //   188: iload #4
          //   190: invokevirtual set : (I)V
          //   193: iinc #4, 1
          //   196: aload_1
          //   197: invokevirtual f : ()Lcom/tencent/lbssearch/a/a/d/b;
          //   200: astore_3
          //   201: goto -> 36
          //   204: aload_1
          //   205: invokevirtual b : ()V
          //   208: aload_2
          //   209: areturn
          // Exception table:
          //   from	to	target	type
          //   79	85	99	java/lang/NumberFormatException
        }
        
        public void a(c param1c, BitSet param1BitSet) throws IOException {
          if (param1BitSet == null) {
            param1c.f();
            return;
          } 
          param1c.b();
          for (byte b = 0; b < param1BitSet.length(); b++)
            param1c.a(param1BitSet.get(b)); 
          param1c.c();
        }
      };
    c = w11;
    d = a(BitSet.class, w11);
  }
  
  static {
    w<Number> w10 = new w<Number>() {
        public Number a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
          StringBuilder stringBuilder;
          b b = param1a.f();
          int i = l.null.a[b.ordinal()];
          if (i != 1) {
            if (i == 4) {
              param1a.j();
              return null;
            } 
            stringBuilder = new StringBuilder();
            stringBuilder.append("Expecting number, got: ");
            stringBuilder.append(b);
            throw new t(stringBuilder.toString());
          } 
          return (Number)new f(stringBuilder.h());
        }
        
        public void a(c param1c, Number param1Number) throws IOException {
          param1c.a(param1Number);
        }
      };
    q = w10;
    r = a(Number.class, w10);
  }
  
  private static final class a<T extends Enum<T>> extends w<T> {
    private final Map<String, T> a = new HashMap<String, T>();
    
    private final Map<T, String> b = new HashMap<T, String>();
    
    public a(Class<T> param1Class) {
      try {
        for (Enum enum_ : (Enum[])param1Class.getEnumConstants()) {
          String str = enum_.name();
          b b = param1Class.getField(str).<b>getAnnotation(b.class);
          if (b != null)
            str = b.a(); 
          this.a.put(str, (T)enum_);
          this.b.put((T)enum_, str);
        } 
        return;
      } catch (NoSuchFieldException noSuchFieldException) {
        throw new AssertionError();
      } 
    }
    
    public T a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
      if (param1a.f() == b.i) {
        param1a.j();
        return null;
      } 
      return this.a.get(param1a.h());
    }
    
    public void a(c param1c, T param1T) throws IOException {
      String str;
      if (param1T == null) {
        param1T = null;
      } else {
        str = this.b.get(param1T);
      } 
      param1c.b(str);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\a\l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */