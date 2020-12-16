package com.bzamani.framework.repository.core.file;

import com.bzamani.framework.model.core.file.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IFileAttachmentRepository extends JpaRepository<FileAttachment, Long> {

    FileAttachment getFileAttachmentByFileCode(String fileCode);

    @Modifying
    @Query("update FileAttachment set isFinal = true where fileCode = :fileCode")
    void finalizeAttachment(@Param("fileCode") String fileCode);

    @Modifying
    @Query("delete from FileAttachment where fileCode = :fileCode")
    void deleteAttachmentByFileCode(@Param("fileCode") String fileCode);


}
