package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.profile.ProfileRequestForAdmin;
import com.tsoftware.qtd.dto.profile.ProfileResponse;
import com.tsoftware.qtd.entity.Profile;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface ProfileMapper {
  @Mapping(target = "userId", ignore = true)
  @Mapping(source = "address", target = "address")
  Profile toProfile(ProfileRequestForAdmin request);

  @Mapping(target = "roles", ignore = true)
  @Mapping(source = "address", target = "address")
  ProfileResponse toProfileResponse(Profile profile);

  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(source = "address", target = "address")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateProfileFromRequest(ProfileRequestForAdmin request, @MappingTarget Profile profile);
}
