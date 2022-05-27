package com.lody.virtual.server.memory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemoryRegionParser {
  public static final Pattern MAPS_LINE_PATTERN = Pattern.compile("([0-9a-f]+)-([0-9a-f]+)\\s([r-])([w-])([x-])([sp])\\s([0-9a-f]+)\\s([0-9a-f]+):([0-9a-f]+)\\s(\\d+)\\s?(.*)", 2);
  
  public static final String PATTERN = "([0-9a-f]+)-([0-9a-f]+)\\s([r-])([w-])([x-])([sp])\\s([0-9a-f]+)\\s([0-9a-f]+):([0-9a-f]+)\\s(\\d+)\\s?(.*)";
  
  public static List<MappedMemoryRegion> getMemoryRegions(int paramInt) throws IOException {
    LinkedList<MappedMemoryRegion> linkedList = new LinkedList();
    BufferedReader bufferedReader = new BufferedReader(new FileReader(String.format(Locale.ENGLISH, "/proc/%d/maps", new Object[] { Integer.valueOf(paramInt) })));
    while (true) {
      String str = bufferedReader.readLine();
      if (str != null) {
        MappedMemoryRegion mappedMemoryRegion = parseMapLine(str);
        if (mappedMemoryRegion.isReadable && mappedMemoryRegion.isWritable && !mappedMemoryRegion.description.endsWith("(deleted)"))
          linkedList.add(mappedMemoryRegion); 
        continue;
      } 
      return linkedList;
    } 
  }
  
  private static long parseHex(String paramString) {
    return Long.parseLong(paramString, 16);
  }
  
  private static MappedMemoryRegion parseMapLine(String paramString) {
    String str = paramString.trim();
    Matcher matcher = MAPS_LINE_PATTERN.matcher(str);
    if (matcher.matches()) {
      if (matcher.groupCount() == 11)
        return new MappedMemoryRegion(parseHex(matcher.group(1)), parseHex(matcher.group(2)), matcher.group(3).equals("r"), matcher.group(4).equals("w"), matcher.group(5).equals("x"), matcher.group(6).equals("s"), parseHex(matcher.group(7)), parseHex(matcher.group(8)), parseHex(matcher.group(9)), parseHex(matcher.group(10)), matcher.group(11)); 
      throw new InternalError(String.format(Locale.ENGLISH, "Invalid group count: Found %d, but expected %d", new Object[] { Integer.valueOf(matcher.groupCount()), Integer.valueOf(12) }));
    } 
    throw new IllegalArgumentException(String.format("The provided line does not match the pattern for /proc/$pid/maps lines. Given: %s", new Object[] { str }));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\memory\MemoryRegionParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */