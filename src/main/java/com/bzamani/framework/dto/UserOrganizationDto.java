package com.bzamani.framework.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserOrganizationDto {
    private long userId;
    private List<Long> organizationIds;
}
