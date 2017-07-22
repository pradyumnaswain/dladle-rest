package za.co.dladle.entity;

import za.co.dladle.model.DocumentType;

/**
 * Created by prady on 6/25/2017.
 */
public class PropertyAddDocument {
    private String base64Document;
    private String fileName;
    private DocumentType documentType;

    public PropertyAddDocument() {
    }

    public String getBase64Document() {
        return base64Document;
    }

    public void setBase64Document(String base64Document) {
        this.base64Document = base64Document;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
