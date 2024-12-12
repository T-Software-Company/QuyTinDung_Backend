package com.tsoftware.qtd.kcTransactionManager;

public interface KcTransactionHandler {
  void handleCreate(String id);

  void handleCreateRollback();

  void handleUpdate(String id);

  void handleUpdateRollback();

  void handleAddRole(String id);

  void handleAddRoleRollback();

  void handleRemoveRole(String id);

  void handleRemoveRoleRollback();
}
