package net.jellycopter.lib;

public class MemoryException extends Exception {
	private static final long serialVersionUID = 2570074006637951026L;
	private String msg;
	
	public MemoryException(){
		this.msg = "";
	};
	public MemoryException(String msg){
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return this.msg;
	}
}
