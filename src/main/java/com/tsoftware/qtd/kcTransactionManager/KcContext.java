package com.tsoftware.qtd.kcTransactionManager;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class KcContext<T, M, N> {
  String id;
  T root;
  List<M> includes;
  List<N> otherIncludes;
  String target;
}
