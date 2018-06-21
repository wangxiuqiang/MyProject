package cn.ssm.crm.exception;

import java.net.Inet4Address;

public class MyIllException extends Exception {
	private int code;//错误码
    public MyIllException(int code ,String message) {
    	super(message);
    	this.code = code;
    }
	public MyIllException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MyIllException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	public MyIllException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	public MyIllException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	public MyIllException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
