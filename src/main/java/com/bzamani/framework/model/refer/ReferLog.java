package com.bzamani.framework.model.refer;

import com.bzamani.framework.model.core.BaseEntity;
import com.bzamani.framework.model.core.personel.Personel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "med_refer_log")
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_med_refer_log", allocationSize = 1)
@Setter
@Getter
public class ReferLog extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refer_id", nullable = false)
    private Refer refer;

    @NotNull
    @Column(name = "operation_date_shamsi", nullable = false)
    private String operationDateShamsi;

    @NotNull
    @Column(name = "operation_time", nullable = false)
    private String operationTime;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id", nullable = false)
    private Personel operator;

    @NotNull
    @Column(name = "operation")
    private String operation;

    @Column(name = "old_status_PersianTitle")
    private String oldStatusPersianTitle;

    @Column(name = "new_status_PersianTitle")
    private String newStatusPersianTitle;

    public ReferLog(){};

    public ReferLog(Long id, Refer refer, String operationDateShamsi, String operationTime,
                    Personel operator, String operation, String oldStatusPersianTitle,
                    String newStatusPersianTitle) {
        super.setId(id);
        this.refer = refer;
        this.operationDateShamsi = operationDateShamsi;
        this.operationTime = operationTime;
        this.operator = operator;
        this.operation = operation;
        this.oldStatusPersianTitle = oldStatusPersianTitle;
        this.newStatusPersianTitle = newStatusPersianTitle;

    }

}
