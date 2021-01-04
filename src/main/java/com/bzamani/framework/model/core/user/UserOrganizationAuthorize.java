package com.bzamani.framework.model.core.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CORE_ORGANIZATION_AUTHORIZE")
@Immutable
@Setter
@Getter
public class UserOrganizationAuthorize {
    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ORGANIZATION_ID")
    private Long organizationId;
}
