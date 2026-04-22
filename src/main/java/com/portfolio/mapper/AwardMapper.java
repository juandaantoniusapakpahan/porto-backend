package com.portfolio.mapper;

import com.portfolio.dto.request.AwardRequest;
import com.portfolio.dto.response.AwardResponse;
import com.portfolio.entity.Award;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AwardMapper {

    AwardResponse toResponse(Award entity);

    List<AwardResponse> toResponseList(List<Award> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Award toEntity(AwardRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(AwardRequest request, @MappingTarget Award entity);
}
