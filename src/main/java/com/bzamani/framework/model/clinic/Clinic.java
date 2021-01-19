package com.bzamani.framework.model.clinic;

import com.bzamani.framework.model.core.BaseEntity;
import com.bzamani.framework.model.core.organization.Organization;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "med_clinic",uniqueConstraints = {@UniqueConstraint(name = "unq_clinic_organization", columnNames = {"organization_id"})})
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_med_clinic", allocationSize = 1)
@Setter
@Getter
public class Clinic extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Column(name = "percent")
    private Integer percent;

    @Column(name = "discount")
    private Integer discount;


}
