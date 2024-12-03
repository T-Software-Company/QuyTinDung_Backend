package com.tsoftware.qtd.kcTransactionManager;

import com.tsoftware.qtd.service.KeycloakService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KcGroupTransactionManager implements KcTransactionHandler {

  private static final ThreadLocal<
          KcContext<GroupRepresentation, RoleRepresentation, UserRepresentation>>
      context = new ThreadLocal<>();

  public static KcContext<GroupRepresentation, RoleRepresentation, UserRepresentation>
      getContext() {
    return context.get();
  }

  public static void setContext(
      KcContext<GroupRepresentation, RoleRepresentation, UserRepresentation> c) {
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
    keycloakService.deleteGroup(getContext().getId());
  }

  @Override
  public void handleUpdate(String id) {
    var groupRepresentation = keycloakService.getGroup(id);
    var roleRepresentations = keycloakService.getRolesByGroup(id);
    setContext(new KcContext<>(id, groupRepresentation, roleRepresentations, null, null));
  }

  @Override
  public void handleUpdateRollback() {
    if (getContext() == null) return;
    keycloakService.updateGroup(getContext().getRoot(), getContext().getIncludes());
  }

  @Override
  public void handleAddRole(String id) {
    var groupRepresentation = keycloakService.getGroup(id);
    var roleRepresentations = keycloakService.getRolesByGroup(id);
    setContext(new KcContext<>(id, groupRepresentation, roleRepresentations, null, null));
  }

  @Override
  public void handleAddRoleRollback() {
    if (getContext() == null) return;
    keycloakService.resetRoles(getContext().getRoot(), getContext().getIncludes());
  }

  @Override
  public void handleRemoveRole(String id) {
    var groupRepresentation = keycloakService.getGroup(id);
    var roleRepresentations = keycloakService.getRolesByGroup(id);
    setContext(new KcContext<>(id, groupRepresentation, roleRepresentations, null, null));
  }

  @Override
  public void handleRemoveRoleRollback() {
    if (getContext() == null) return;
    keycloakService.resetRoles(getContext().getRoot(), getContext().getIncludes());
  }

  public void handleDelete(String id) {
    GroupRepresentation groupRepresentation = keycloakService.getGroup(id);
    List<RoleRepresentation> roleRepresentations = keycloakService.getRolesByGroup(id);
    List<UserRepresentation> userRepresentations = keycloakService.getUsersByGroup(id);
    setContext(
        new KcContext<>(id, groupRepresentation, roleRepresentations, userRepresentations, null));
  }

  public void handleDeleteRollback() {
    if (getContext() == null) return;
    keycloakService.createGroup(
        getContext().getRoot(),
        getContext().getIncludes(),
        (List<UserRepresentation>) getContext().getOtherIncludes());
  }

  public void handleAddUser(String id, String target) {
    setContext(new KcContext<>(id, null, null, null, target));
  }

  public void handleRemoveUser(String id, String target) {
    setContext(new KcContext<>(id, null, null, null, target));
  }

  public void handleRemoveUserRollback() {
    if (getContext() == null) return;
    keycloakService.addUserToGroup(getContext().getId(), getContext().getTarget());
  }

  public void handleAddUserRollback() {
    if (getContext() == null) return;
    keycloakService.removeUserOnGroup(getContext().getId(), getContext().getTarget());
  }
}
