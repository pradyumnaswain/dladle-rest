package za.co.dladle.entity;

import za.co.dladle.model.DocumentType;

import java.util.List;

/**
 * Created by prady on 6/3/2017.
 */
public class PropertyViewDocuments {
    private String documentUrl;
    private DocumentType documentType;

    public PropertyViewDocuments() {
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
}
