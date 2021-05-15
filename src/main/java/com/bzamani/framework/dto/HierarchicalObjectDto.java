package com.bzamani.framework.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HierarchicalObjectDto {
    private Long id;
    private String title;
    private Long parentId;
    private Integer childCount;

    public HierarchicalObjectDto(Long id,
                                 String title,
                                 Long parentId,
                                 Integer childCount) {
        this.id = id;
        this.title = title;
        this.parentId = parentId;
        this.childCount = childCount;
    }
}
