package com.bzamani.framework.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationDto {
    private Long id;
    private String title;
    private Long parentId;
    private Boolean authorized;
    private String hierarchyCode;
    private Integer childCount;

    public OrganizationDto(Long id,
                           String title,
                           Long parentId,
                           Boolean authorized,
                           String hierarchyCode,
                           Integer childCount) {
        this.id = id;
        this.title = title;
        this.parentId = parentId;
        this.hierarchyCode = hierarchyCode;
        this.authorized = authorized;
        this.childCount = childCount;

    }
}
