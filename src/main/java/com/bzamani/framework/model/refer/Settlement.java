package com.bzamani.framework.model.refer;

import com.bzamani.framework.model.clinic.Clinic;
import com.bzamani.framework.model.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "med_settlement")
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_med_settlement", allocationSize = 1)
@Setter
@Getter
public class Settlement extends BaseEntity {
    @NotNull
    @Column(name = "settlement_date_shamsi", nullable = false)
    private String settlementDateShamsi;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @Formula(" (select sum(r.medik_earn) from med_refer r where r.settlement_id = id ) ")
    private Long medikEarnsTotal;

    @Column(name = "medik_earns_received_finaly")
    private Long medikEarnsReceivedFinaly;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "IS_CONFIRMED", nullable = false)
    private boolean confirmed;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "settlement")
    @OrderBy("id")
    private Set<Refer> refers;


}
