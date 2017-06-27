package za.co.dladle.entity;

import za.co.dladle.model.DocumentType;

/**
 * Created by prady on 6/27/2017.
 */
public class ServiceDocuments {
    private String base64;
    private DocumentType documentType;

    public ServiceDocuments() {
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
}
