package com.bzamani.framework.model.doctor;

import com.bzamani.framework.model.core.BaseEntity;
import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.model.core.personel.Personel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "med_doctor", uniqueConstraints = {
        @UniqueConstraint(name = "unq_doctor_personel_", columnNames = "personel_id")})
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_med_dpctor", allocationSize = 1)
@Setter
@Getter
public class Doctor extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personel_id", nullable = false)
    private Personel personel;

    @Column(name = "medical_national_number")
    private String medicalNationalNumber;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speciality_id", nullable = false)
    private BaseInfo speciality;


}
