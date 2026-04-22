package com.portfolio.mapper;

import com.portfolio.dto.request.SkillRequest;
import com.portfolio.dto.response.SkillResponse;
import com.portfolio.entity.Skill;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SkillMapper {

    SkillResponse toResponse(Skill entity);

    List<SkillResponse> toResponseList(List<Skill> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Skill toEntity(SkillRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(SkillRequest request, @MappingTarget Skill entity);
}
