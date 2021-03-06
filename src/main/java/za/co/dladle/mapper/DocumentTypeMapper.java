package za.co.dladle.mapper;

import za.co.dladle.model.DocumentType;

/**
 * Created by prady on 4/1/2017.
 */
public class DocumentTypeMapper {
    public static Integer getDocumentType(DocumentType documentType) {
        switch (documentType) {
            case AUDIO:
                return 1;
            case IMAGE:
                return 2;
            default:
                return null;
        }
    }

    public static DocumentType getDocumentType(Integer documentType) {
        switch (documentType) {
            case 1:
                return DocumentType.AUDIO;
            case 2:
                return DocumentType.IMAGE;
            default:
                return null;
        }
    }
}
