package com.bzamani.framework.model.core.file;

import com.bzamani.framework.model.core.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CORE_FILE")
@SequenceGenerator(name = "sequence_db", sequenceName = "SEQ_CORE_FILE", allocationSize = 1)
@Setter
@Getter
public class FileAttachment extends BaseEntity {

    @Column(name = "FILE_CODE")
    private String fileCode;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "EXTENSION")
    private String extension;

    @Lob
    @Column(name = "ATTACHMENT")
    private byte[] attachment;

    @Column(name = "IS_FINAL")
    private Boolean isFinal;

}