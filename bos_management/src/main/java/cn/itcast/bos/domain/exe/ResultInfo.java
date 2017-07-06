package cn.itcast.bos.domain.exe;

public class ResultInfo {
	
	/** 结果状态  */
	private String status;
	
	/** 结果信息 */
	private String msg;
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public ResultInfo() {
	}
	
	public ResultInfo(String status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}
	
	
	
	
}
