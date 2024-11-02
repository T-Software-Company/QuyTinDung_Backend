package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.response.ProfileResponse;
import com.tsoftware.qtd.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.tsoftware.qtd.dto.request.RegistrationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "profileId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Profile toProfile(RegistrationRequest request);

    ProfileResponse toAProfileResponse(Profile profile);  // MapStruct tự động ánh xạ các trường có cùng tên
}
