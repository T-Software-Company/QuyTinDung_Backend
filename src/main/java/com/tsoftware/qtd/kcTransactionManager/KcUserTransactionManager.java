package com.tsoftware.qtd.kcTransactionManager;

import com.tsoftware.qtd.service.KeycloakService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KcUserTransactionManager implements KcTransactionHandler {
  private static final ThreadLocal<KcContext<UserRepresentation, RoleRepresentation, ?>> context =
      new ThreadLocal<>();

  public static KcContext<UserRepresentation, RoleRepresentation, ?> getContext() {
    return context.get();
  }

  public static void setContext(KcContext<UserRepresentation, RoleRepresentation, ?> c) {
    context.set(c);
  }

  public static void clear() {
    context.remove();
  }

  private final KeycloakService keycloakService;

  @Override
  public void handleCreate(String id) {
    setContext(new KcContext<>(id, null, null, null, null));
  }

  @Override
  public void handleCreateRollback() {
    if (getContext() == null) return;
    keycloakService.deleteUser(getContext().getId());
  }

  @Override
  public void handleUpdate(String id) {
    UserRepresentation user = keycloakService.getUser(id);
    List<RoleRepresentation> roleRepresentations = keycloakService.getRolesByUser(id);
    setContext(new KcContext<>(id, user, roleRepresentations, null, null));
  }

  @Override
  public void handleUpdateRollback() {
    if (getContext() == null) return;
    keycloakService.updateUser(getContext().getRoot(), getContext().getIncludes());
  }

  @Override
  public void handleAddRole(String id) {
    UserRepresentation user = keycloakService.getUser(id);
    List<RoleRepresentation> roleRepresentations = keycloakService.getRolesByUser(id);
    setContext(new KcContext<>(id, user, roleRepresentations, null, null));
  }

  @Override
  public void handleAddRoleRollback() {
    if (getContext() == null) return;
    keycloakService.resetRoles(getContext().getRoot(), getContext().includes);
  }

  @Override
  public void handleRemoveRole(String id) {
    UserRepresentation user = keycloakService.getUser(id);
    List<RoleRepresentation> roleRepresentations = keycloakService.getRolesByUser(id);
    setContext(new KcContext<>(id, user, roleRepresentations, null, null));
  }

  @Override
  public void handleRemoveRoleRollback() {
    if (getContext() == null) return;
    keycloakService.resetRoles(getContext().getRoot(), getContext().includes);
  }
}
