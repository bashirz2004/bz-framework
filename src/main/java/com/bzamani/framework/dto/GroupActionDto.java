package com.bzamani.framework.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupActionDto {
    private long groupId;
    private List<Long> actionIds;
}
