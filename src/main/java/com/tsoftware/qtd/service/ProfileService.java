package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.request.ProfileUpdateRequest;
import com.tsoftware.qtd.dto.request.RegistrationRequest;
import com.tsoftware.qtd.dto.response.ProfileResponse;
import java.util.List;

public interface ProfileService {
  List<ProfileResponse> getAllProfiles();

  ProfileResponse getMyProfile();

  ProfileResponse registerProfile(RegistrationRequest request);

  void updateProfile(ProfileUpdateRequest request);

  void updateProfileByAdmin(String userId, ProfileUpdateRequest request);

  void resetPassword(String userId, String newPassword);

  void activateUser(String userId);

  void deactivateUser(String userId);
}
