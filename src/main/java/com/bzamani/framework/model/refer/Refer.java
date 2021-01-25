package com.bzamani.framework.model.refer;

import com.bzamani.framework.model.clinic.Clinic;
import com.bzamani.framework.model.core.BaseEntity;
import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.bzamani.framework.model.core.personel.Personel;
import com.bzamani.framework.model.doctor.Doctor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "med_refer")
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_med_refer", allocationSize = 1)
@FilterDefs({@FilterDef(name = "organizationAuthorize", parameters = {@ParamDef(name = "username", type = "string")})})
@Filters({@Filter(name = "organizationAuthorize", condition =
        " (exists ( select 1 from med_doctor d join core_personel p on p.id = d.personel_id " +
                "    join core_organization_authorize oa on oa.organization_id = p.organization_id " +
                "           join core_user u on u.id = oa.user_id " +
                "          where d.id = doctor_id and u.username = :username ) " +
                " or " +
                " exists ( select 1 from med_clinic c " +
                "    join core_organization_authorize oa on oa.organization_id = c.organization_id " +
                "           join core_user u on u.id = oa.user_id " +
                "          where c.id = clinic_id and u.username = :username ))")})
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
+96
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
