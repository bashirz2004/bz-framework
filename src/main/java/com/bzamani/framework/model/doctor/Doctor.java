package com.bzamani.framework.model.doctor;

import com.bzamani.framework.model.BaseEntity;
import com.bzamani.framework.model.baseinfo.BaseInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "med_doctor", uniqueConstraints = {@UniqueConstraint(name = "unq_medical_national_number", columnNames = {"medical_national_number"})})
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_med_dpctor", allocationSize = 1)
@Setter
@Getter
public class Doctor extends BaseEntity {

    @NotNull
    @Column(name = "firstname")
    private String firstname;

    @NotNull
    @Column(name = "lastname")
    private String lastname;

    @Column(name = "medical_national_number")
    private String medical_national_number;

    @NotNull
    @Column(name = "male")
    private boolean male;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id", nullable = false)
    private BaseInfo state;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id", nullable = false)
    private BaseInfo city;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id", nullable = false)
    private BaseInfo region;

    @Column(name = "address")
    private String address;

    @Column(name = "telephone")
    private String telephone;


}
