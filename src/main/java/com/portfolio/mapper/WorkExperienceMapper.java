package com.portfolio.mapper;

import com.portfolio.dto.request.WorkExperienceRequest;
import com.portfolio.dto.response.WorkExperienceResponse;
import com.portfolio.entity.WorkExperience;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkExperienceMapper {

    @Mapping(target = "isCurrent", source = "current")
    WorkExperienceResponse toResponse(WorkExperience entity);

    List<WorkExperienceResponse> toResponseList(List<WorkExperience> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "current", source = "isCurrent")
    WorkExperience toEntity(WorkExperienceRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "current", source = "isCurrent")
    void updateFromRequest(WorkExperienceRequest request, @MappingTarget WorkExperience entity);
}
