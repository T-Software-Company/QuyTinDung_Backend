package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.request.ProfileAdminUpdateRequest;
import com.tsoftware.qtd.dto.request.ProfileUpdateClientRequest;
import com.tsoftware.qtd.dto.request.RegistrationRequest;
import com.tsoftware.qtd.dto.response.ProfileResponse;
import java.util.List;

public interface ProfileService {
  List<ProfileResponse> getAllProfiles();

  ProfileResponse getMyProfile();

  ProfileResponse registerProfile(RegistrationRequest request);

  void updateProfile(ProfileUpdateClientRequest request);

  void updateProfileByAdmin(String userId, ProfileAdminUpdateRequest request);

  void resetPassword(String userId, String newPassword);

  void activateUser(String userId);

  void deactivateUser(String userId);
}
