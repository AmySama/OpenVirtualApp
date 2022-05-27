package external.org.apache.commons.lang3.tuple;

public final class ImmutablePair<L, R> extends Pair<L, R> {
  private static final long serialVersionUID = 4954918890077093841L;
  
  public final L left;
  
  public final R right;
  
  public ImmutablePair(L paramL, R paramR) {
    this.left = paramL;
    this.right = paramR;
  }
  
  public static <L, R> ImmutablePair<L, R> of(L paramL, R paramR) {
    return new ImmutablePair<L, R>(paramL, paramR);
  }
  
  public L getLeft() {
    return this.left;
  }
  
  public R getRight() {
    return this.right;
  }
  
  public R setValue(R paramR) {
    throw new UnsupportedOperationException();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\tuple\ImmutablePair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */