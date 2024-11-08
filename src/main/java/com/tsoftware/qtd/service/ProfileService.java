package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.profile.ProfileRequest;
import com.tsoftware.qtd.dto.profile.ProfileRequestForAdmin;
import com.tsoftware.qtd.dto.profile.ProfileResponse;
import java.util.List;

public interface ProfileService {
  List<ProfileResponse> getAllProfiles();

  ProfileResponse getMyProfile();

  ProfileResponse registerProfile(ProfileRequestForAdmin request);

  void updateProfile(ProfileRequest request);

  void updateProfileByAdmin(String userId, ProfileRequestForAdmin request);

  void resetPassword(String userId, String newPassword);

  void activateUser(String userId);

  void deactivateUser(String userId);
}
