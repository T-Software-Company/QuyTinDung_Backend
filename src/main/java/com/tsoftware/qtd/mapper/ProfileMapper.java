package com.tsoftware.qtd.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.tsoftware.qtd.dto.request.ProfileUpdateRequest;
import com.tsoftware.qtd.dto.request.RegistrationRequest;
import com.tsoftware.qtd.dto.response.ProfileResponse;
import com.tsoftware.qtd.entity.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    // Chuyển đổi RegistrationRequest thành Profile, bỏ qua các trường profileId và userId (do chúng là auto-generated)
    @Mapping(target = "profileId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Profile toProfile(RegistrationRequest request);

    // Chuyển đổi Profile entity sang ProfileResponse DTO, bao gồm ánh xạ trường roles
    @Mapping(target = "roles",  ignore = true)
    ProfileResponse toProfileResponse(Profile profile);

    // Cập nhật Profile entity từ ProfileUpdateRequest DTO mà không ghi đè các trường có giá trị null
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "profileId",  ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfileFromRequest(ProfileUpdateRequest request, @MappingTarget Profile profile);
}
