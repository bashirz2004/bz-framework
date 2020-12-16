package com.bzamani.framework.service.impl.core.file;

import com.bzamani.framework.model.core.file.FileAttachment;
import com.bzamani.framework.repository.core.file.IFileAttachmentRepository;
import com.bzamani.framework.service.core.file.IFileAttachmentService;
import com.bzamani.framework.service.impl.core.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class FileAttachmentService extends GenericService<FileAttachment, Long> implements IFileAttachmentService {

    @Autowired
    IFileAttachmentRepository iFileAttachmentRepository;

    @Override
    protected JpaRepository<FileAttachment, Long> getGenericRepo() {
        return iFileAttachmentRepository;
    }

    @Override
    public FileAttachment getFileAttachmentByFileCode(String fileCode) {
        return iFileAttachmentRepository.getFileAttachmentByFileCode(fileCode);
    }

    @Override
    public void finalizeNewAndDeleteOldAttachment(String newFileCode, String oldFileCode) {
        if (newFileCode == null && oldFileCode == null)
            return;
        if (oldFileCode != null && !oldFileCode.equals(newFileCode))
            iFileAttachmentRepository.deleteAttachmentByFileCode(oldFileCode);
        if (newFileCode != null && !newFileCode.equals(oldFileCode))
            iFileAttachmentRepository.finalizeAttachment(newFileCode);
    }
}
