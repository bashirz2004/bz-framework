package com.bzamani.framework.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostCategoryDto {
    private Long id;
    private String title;
    private Long numberOfPosts;

    public PostCategoryDto(Long id, String title, Long numberOfPosts) {
        this.id = id;
        this.title = title;
        this.numberOfPosts = numberOfPosts;
    }
}
