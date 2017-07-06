package cn.itcast.bos.exception;

/**
 * 收派标准异常
 * 
 * @author 王伟   
 * @version 1.0, 2017-7-2 08:12:43
 */
public class StandardException extends Exception{
	
	public StandardException() {
	}
	
	public StandardException(String msg) {
		super(msg);
	}

}
