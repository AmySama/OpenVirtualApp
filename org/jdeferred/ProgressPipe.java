package org.jdeferred;

public interface ProgressPipe<P, D_OUT, F_OUT, P_OUT> {
  Promise<D_OUT, F_OUT, P_OUT> pipeProgress(P paramP);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\ProgressPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */