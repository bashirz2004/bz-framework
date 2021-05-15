package com.bzamani.framework.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuTreeNodeDto {
    private Long id;
    private String title;
    private Long parentId;
    private Integer sortOrder;
    private String src;
    private String iconClass;
    private List<MenuTreeNodeDto> children;


    public MenuTreeNodeDto(Long id,
                           String title,
                           Long parentId,
                           Integer sortOrder,
                           String src,
                           String iconClass) {
        this.id = id;
        this.title = title;
        this.parentId = parentId;
        this.sortOrder = sortOrder;
        this.src = src;
        this.iconClass = iconClass;
    }

    public MenuTreeNodeDto(){}
}
