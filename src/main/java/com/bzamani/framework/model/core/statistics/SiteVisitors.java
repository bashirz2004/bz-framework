package com.bzamani.framework.model.core.statistics;

import com.bzamani.framework.model.core.BaseEntity;
import com.bzamani.framework.model.core.action.Action;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "CORE_SITE_VISITORS", uniqueConstraints = {@UniqueConstraint(name = "unq_visitor_ip_date", columnNames = {"date_shamsi", "ip"})})
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_core_site_visitors", allocationSize = 1)
@Setter
@Getter
public class SiteVisitors extends BaseEntity {

    @Column(name = "ip")
    private String ip;

    @Column(name = "date_shamsi")
    private String dateShamsi;
}
