package com.bzamani.framework.model.core.personel;

import com.bzamani.framework.model.core.BaseEntity;
import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.bzamani.framework.model.core.organization.Organization;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "core_personel",uniqueConstraints = {@UniqueConstraint(name = "unq_personel_nationalCode", columnNames = "nationalCode"),@UniqueConstraint(name = "unq_personel_email", columnNames = "email")})
@FilterDefs({@FilterDef(name = "organizationAuthorize", parameters = {@ParamDef(name = "username", type = "string")})})
@Filters({@Filter(name = "organizationAuthorize", condition = " exists ( select 1 from core_user_organization uo join core_user u on u.id = uo.user_id and u.username = :username and uo.organization_id = organization_id ) ")})
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_core_personel", allocationSize = 1)
@Setter
@Getter
public class Personel extends BaseEntity {

    @NotNull
    @Column(name = "firstname")
    private String firstname;

    @NotNull
    @Column(name = "lastname")
    private String lastname;

    @NotNull
    @Column(name = "male")
    private boolean male;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "birth_certificate_number")
    private String birthCertificateNumber;

    @NotNull
    @Column(name = "nationalCode")
    private String nationalCode;

    @NotNull
    @Column(name = "email")
    private String email;

    @Column(name = "birth_place")
    private String birthPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_level_id")
    private BaseInfo educationLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "military_service_status_id")
    private BaseInfo militaryServiceStatus;

    @Column(name = "address")
    private String address;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "mobile")
    private String mobile;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(name = "fileCode")
    private String fileCode;


}
