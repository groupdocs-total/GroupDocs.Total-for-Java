package com.groupdocs.ui.viewer.exception;

import com.groupdocs.ui.exception.TotalGroupDocsException;

/**
 * Wrapper for reading/writing exceptions
 */
public class ReadWriteException extends TotalGroupDocsException {

    public ReadWriteException(String message) {
        super(message);
    }

    public ReadWriteException(Throwable cause) {
        super(cause);
    }
}
