package com.portfolio.mapper;

import com.portfolio.dto.request.PersonalInfoRequest;
import com.portfolio.dto.response.PersonalInfoResponse;
import com.portfolio.entity.PersonalInfo;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonalInfoMapper {

    PersonalInfoResponse toResponse(PersonalInfo entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PersonalInfo toEntity(PersonalInfoRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "avatarUrl", ignore = true)
    void updateFromRequest(PersonalInfoRequest request, @MappingTarget PersonalInfo entity);
}
