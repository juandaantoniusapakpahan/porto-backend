package com.portfolio.mapper;

import com.portfolio.dto.request.LanguageRequest;
import com.portfolio.dto.response.LanguageResponse;
import com.portfolio.entity.Language;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LanguageMapper {

    LanguageResponse toResponse(Language entity);

    List<LanguageResponse> toResponseList(List<Language> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Language toEntity(LanguageRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(LanguageRequest request, @MappingTarget Language entity);
}
