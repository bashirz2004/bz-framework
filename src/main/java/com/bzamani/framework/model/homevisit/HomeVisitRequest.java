package com.bzamani.framework.model.homevisit;

import com.bzamani.framework.model.core.BaseEntity;
import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.bzamani.framework.model.core.personel.Personel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "med_home_visit")
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_med_home_visit", allocationSize = 1)
@Setter
@Getter
public class HomeVisitRequest extends BaseEntity {

    @NotNull
    @Column(name = "firstname")
    private String firstname;

    @NotNull
    @Column(name = "lastname")
    private String lastname;

    @NotNull
    @Column(name = "mobile")
    private String mobile;

    @NotNull
    @Column(name = "request_type")
    private Integer requestType;

    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "done")
    private boolean done;


}
