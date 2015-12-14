package com.vini.exceptions;

public class ExcelUploadException extends Exception{
    private static final long serialVersionUID = 1L;
    
    private final String errorCode;
    
    public ExcelUploadException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
    
    public ExcelUploadException(Throwable cause) {
        super(cause);
        this.errorCode = null;
    }
    
    public ExcelUploadException(String errorCode, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
       
    
}
