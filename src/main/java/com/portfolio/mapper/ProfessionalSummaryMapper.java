package com.portfolio.mapper;

import com.portfolio.dto.request.ProfessionalSummaryRequest;
import com.portfolio.dto.response.ProfessionalSummaryResponse;
import com.portfolio.entity.ProfessionalSummary;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfessionalSummaryMapper {

    ProfessionalSummaryResponse toResponse(ProfessionalSummary entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProfessionalSummary toEntity(ProfessionalSummaryRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(ProfessionalSummaryRequest request, @MappingTarget ProfessionalSummary entity);
}
