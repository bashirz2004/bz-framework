package com.bzamani.framework.service.core.file;

import com.bzamani.framework.model.core.file.FileAttachment;
import com.bzamani.framework.service.core.IGenericService;

public interface IFileAttachmentService extends IGenericService<FileAttachment, Long> {

    FileAttachment getFileAttachmentByFileCode(String fileCode);
    void finalizeNewAndDeleteOldAttachment(String newFileCode,String oldFileCode);
}
