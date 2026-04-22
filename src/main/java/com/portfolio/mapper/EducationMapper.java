package com.portfolio.mapper;

import com.portfolio.dto.request.EducationRequest;
import com.portfolio.dto.response.EducationResponse;
import com.portfolio.entity.Education;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EducationMapper {

    EducationResponse toResponse(Education entity);

    List<EducationResponse> toResponseList(List<Education> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Education toEntity(EducationRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(EducationRequest request, @MappingTarget Education entity);
}
