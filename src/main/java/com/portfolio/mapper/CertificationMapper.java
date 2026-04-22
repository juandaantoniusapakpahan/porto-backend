package com.portfolio.mapper;

import com.portfolio.dto.request.CertificationRequest;
import com.portfolio.dto.response.CertificationResponse;
import com.portfolio.entity.Certification;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CertificationMapper {

    CertificationResponse toResponse(Certification entity);

    List<CertificationResponse> toResponseList(List<Certification> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Certification toEntity(CertificationRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(CertificationRequest request, @MappingTarget Certification entity);
}
