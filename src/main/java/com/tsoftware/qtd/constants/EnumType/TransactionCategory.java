// package com.tsoftware.qtd.constants.EnumType;
//
// import java.util.List;
// import java.util.Map;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
//
// @Getter
// @AllArgsConstructor
// public enum TransactionCategory {
////  CUSTOMER(
////      "Customer",
////      "customerExecutor",
////      List.of(TransactionType.CREATE_CUSTOMER, TransactionType.UPDATE_CUSTOMER)),
////  ACCOUNT(
////      "Account",
////      "accountExecutor",
////      List.of(TransactionType.CREATE_ACCOUNT, TransactionType.UPDATE_ACCOUNT)),
////  DOCUMENT(
////      "Document",
////      "documentExecutor",
////      List.of(TransactionType.CREATE_DOCUMENT, TransactionType.UPDATE_DOCUMENT)),
//  UNKNOWN("Unknown", "", List.of());
//
//  private final TransactionType type;
//  private final String executor;
////  private final List<TransactionType> transactionTypes;
//
//  public static final Map<String, String> executorMap =
//      Stream.of(values())
//          .flatMap(
//              category ->
//                  category.transactionTypes.stream()
//                      .map(type -> Map.entry(type.name(), category.executor)))
//          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//
//  public static String getExecutor(TransactionType type) {
//    return executorMap.getOrDefault(type.name(), "");
//  }
// }
