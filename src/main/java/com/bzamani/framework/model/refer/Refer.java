package com.bzamani.framework.model.refer;

import com.bzamani.framework.model.clinic.Clinic;
import com.bzamani.framework.model.core.BaseEntity;
import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.bzamani.framework.model.core.personel.Personel;
import com.bzamani.framework.model.doctor.Doctor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "med_refer")
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_med_refer", allocationSize = 1)
@Setter
@Getter
public class Refer extends BaseEntity {

    @NotNull
    @Column(name = "refer_date_shamsi", nullable = false)
    private String referDateShamsi;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Personel patient;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sickness_id", nullable = false)
    private BaseInfo sickness;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treatment_id", nullable = false)
    private BaseInfo treatment;

    @NotNull
    @Column(name = "session_Count_ToDo", nullable = false)
    private int sessionCountToDo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private BaseInfo unit;

    @Column(name = "reception_date_shamsi")
    private String receptionDateShamsi;

    @Column(name = "session_Count_Done")
    private Integer sessionCountDone;

    @Column(name = "payment")
    private Long payment;  //پرداخت بیمار به کلینیک


    @Column(name = "finish_date_shamsi")
    private String finishDateShamsi;

    @Column(name = "medik_earn")
    private Long medikEarn;  //سهم مدیک =  پورسانت کلینیک ضربدر پرداخت بیمار

    // تسویه تکی معنی نداره و باید صورتجلسه تسویه ثبت شود و چند ارجاع یکجا با هم تسویه شوند.
   /* @Column(name = "settlement_date_shamsi")
    private String settlementDateShamsi;

    @Column(name = "medik_earn_final")
    private Long medikEarnFinal;  //مبلغی نهایی که واقعا از کلینیک به مدیک بابت این ارجاع پرداخت شده است.

    @Column(name = "medik_earn_final_shamsi_date")
    private String medikEarnFinalShamsiDate;  //تاریخ و ساعت واریز مبلغ نهایی*/

    @NotNull
    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ReferStatus status;

    @Column(name = "fileCode")
    private String fileCode;

    @Transient
    private String statusPersianTitle;

    public String getStatusPersianTitle() {
        return this.status.getPersianTitle();
    }
}
