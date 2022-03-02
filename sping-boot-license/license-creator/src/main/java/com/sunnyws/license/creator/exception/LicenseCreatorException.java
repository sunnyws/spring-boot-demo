package com.sunnyws.license.creator.exception;

public class LicenseCreatorException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LicenseCreatorException(String message){
		super(message);
	}
	
	public LicenseCreatorException(Throwable cause)
	{
		super(cause);
	}
	
	public LicenseCreatorException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
