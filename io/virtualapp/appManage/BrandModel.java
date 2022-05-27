package io.virtualapp.appManage;

import java.util.ArrayList;
import java.util.List;

public class BrandModel {
  public static final Model[] data = new Model[] { 
      new Model("小米", "小米MIX2S"), new Model("小米", "小米6"), new Model("小米", "小米MIX2"), new Model("小米", "小米MIX"), new Model("小米", "小米5"), new Model("小米", "小米5X"), new Model("小米", "红米4X"), new Model("小米", "红米Note 4X"), new Model("小米", "小米Note 2"), new Model("小米", "小米5s"), 
      new Model("小米", "小米5s plus"), new Model("小米", "红米4"), new Model("小米", "米4"), new Model("小米", "小米3"), new Model("小米", "红米3X"), new Model("小米", "红米NOTE 2"), new Model("小米", "小米NOTE"), new Model("华为", "荣耀 V10"), new Model("华为", "Mate 10"), new Model("华为", "Mate 10 Pro"), 
      new Model("华为", "Mate 10 保时捷版"), new Model("华为", "Mate 9"), new Model("华为", "Mate 9 Pro"), new Model("华为", "Mate 9 保时捷版"), new Model("华为", "Mate 8"), new Model("华为", "Mate 7"), new Model("华为", "P10"), new Model("华为", "P10 Plus"), new Model("华为", "P9"), new Model("华为", "P9 Plus"), 
      new Model("华为", "P8"), new Model("华为", "P7"), new Model("华为", "G9"), new Model("华为", "麦芒5"), new Model("华为", "畅玩7X"), new Model("华为", "荣耀 V9"), new Model("华为", "荣耀9"), new Model("华为", "荣耀8"), new Model("华为", "荣耀7"), new Model("华为", "荣耀6Plus"), 
      new Model("华为", "荣耀5"), new Model("华为", "荣耀4A"), new Model("华为", "荣耀3C"), new Model("OPPO", "R15"), new Model("OPPO", "R11 Plus"), new Model("OPPO", "R11"), new Model("OPPO", "R11s"), new Model("OPPO", "R9s"), new Model("OPPO", "R9m"), new Model("OPPO", "R9 Plus"), 
      new Model("OPPO", "R8"), new Model("OPPO", "R7"), new Model("OPPO", "R7 Plus"), new Model("OPPO", "Find 7"), new Model("OPPO", "N3"), new Model("OPPO", "N1T"), new Model("OPPO", "A77"), new Model("VIVO", "x21"), new Model("VIVO", "x20 Plus"), new Model("VIVO", "x20A"), 
      new Model("VIVO", "x20"), new Model("VIVO", "X9"), new Model("VIVO", "X9L"), new Model("VIVO", "X7Plus"), new Model("VIVO", "X6"), new Model("VIVO", "Y69"), new Model("VIVO", "Y67"), new Model("魅族", "PRO7 Plus"), new Model("魅族", "PRO7"), new Model("魅族", "PRO6 Plus"), 
      new Model("魅族", "PRO6"), new Model("魅族", "m6 note"), new Model("魅族", "MX6"), new Model("魅族", "U20"), new Model("三星", "Galaxy S9+"), new Model("三星", "Galaxy S9"), new Model("三星", "Galaxy S8"), new Model("三星", "Galaxy S7"), new Model("三星", "Galaxy A9"), new Model("三星", "Galaxy A8"), 
      new Model("三星", "Galaxy A7"), new Model("三星", "Galaxy C9"), new Model("三星", "Galaxy C7"), new Model("三星", "Galaxy Note8"), new Model("三星", "Galaxy Note5"), new Model("努比亚", "Z11 minis"), new Model("努比亚", "Z11"), new Model("努比亚", "My 布拉格"), new Model("努比亚", "Z9 mini"), new Model("努比亚", "Z9"), 
      new Model("谷歌", "Pixel 2XL"), new Model("谷歌", "Pixel 2"), new Model("谷歌", "Pixel XL"), new Model("谷歌", "Pixel"), new Model("iPhone", "iPhone X"), new Model("iPhone", "iPhone 8"), new Model("iPhone", "iPhone 8 Plus"), new Model("iPhone", "iPhone 7"), new Model("iPhone", "iPhone 7 Plus"), new Model("iPhone", "iPhone 6s"), 
      new Model("iPhone", "iPhone 6s Plus"), new Model("iPhone", "iPhone 6"), new Model("iPhone", "iPhone 6 Plus"), new Model("一加", "OnePlus 5T"), new Model("一加", "OnePlus 5"), new Model("一加", "OnePlus 3T"), new Model("一加", "OnePlus 3"), new Model("8848", "8848 M4"), new Model("8848", "8848 M3"), new Model("金立", "S11"), 
      new Model("金立", "S11S"), new Model("金立", "S10"), new Model("金立", "S9"), new Model("金立", "大金刚2"), new Model("金立", "M7"), new Model("金立", "M6"), new Model("金立", "M6 Plus"), new Model("金立", "F100L"), new Model("美图", "M8"), new Model("美图", "T8"), 
      new Model("美图", "M6"), new Model("美图", "M6s"), new Model("美图", "M4s"), new Model("美图", "V4s"), new Model("美图", "M4"), new Model("酷派", "Cool c105"), new Model("酷派", "大神 Note3"), new Model("酷派", "酷玩6"), new Model("酷派", "金魔王"), new Model("酷派", "大观铂顿"), 
      new Model("酷派", "S6"), new Model("酷派", "Cool S1"), new Model("酷派", "Cool 1c"), new Model("酷派", "大神F1"), new Model("酷派", "大神X7"), new Model("酷派", "小i"), new Model("酷派", "Cool1 dual"), new Model("酷派", "锋尚Pro2"), new Model("酷派", "锋尚"), new Model("360", "N5"), 
      new Model("360", "N5S"), new Model("360", "F4S"), new Model("360", "Q5"), new Model("360", "N4S"), new Model("360", "F5"), new Model("锤子", "T3"), new Model("锤子", "T2"), new Model("锤子", "T1"), new Model("锤子", "坚果Pro"), new Model("乐视", "乐Max"), 
      new Model("乐视", "乐1S Pro X800"), new Model("乐视", "乐1 X608"), new Model("nokia", "NOKIA 820"), new Model("nokia", "NOKIA 620") };
  
  public static List<String> findByBrand(String paramString) {
    ArrayList<String> arrayList = new ArrayList();
    byte b = 0;
    while (true) {
      Model[] arrayOfModel = data;
      if (b < arrayOfModel.length) {
        Model model = new Model((arrayOfModel[b]).brand, (data[b]).model);
        if (model.brand.equals(paramString))
          arrayList.add(model.model); 
        b++;
        continue;
      } 
      return arrayList;
    } 
  }
  
  public static class Model {
    public String brand;
    
    public String model;
    
    public Model(String param1String1, String param1String2) {
      this.brand = param1String1;
      this.model = param1String2;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\appManage\BrandModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */