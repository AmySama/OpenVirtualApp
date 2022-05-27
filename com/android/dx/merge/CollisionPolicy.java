package com.android.dx.merge;

public enum CollisionPolicy {
  FAIL, KEEP_FIRST;
  
  static {
    CollisionPolicy collisionPolicy = new CollisionPolicy("FAIL", 1);
    FAIL = collisionPolicy;
    $VALUES = new CollisionPolicy[] { KEEP_FIRST, collisionPolicy };
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\merge\CollisionPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */