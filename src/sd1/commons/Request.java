package sd1.commons;

import com.google.gson.annotations.Expose;

public class Request {
	public static final int ADD = 1;
	public static final int DEL = 2;
	public static final int LIS = 3;
	public static final int SEA = 4;
	public static final int UPD = 5;
	public static final int REF = 6;
	public static final int AMO = 7;
	
	@Expose
	private int cod;
	
	@Expose
	private Object data;

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "Código:\t"+ this.cod + "\nDados:\t" + ((this.data != null) ? this.data.toString(): "{}");
	}

	public Request(int cod, Object data) {
		this.cod = cod;
		this.data = data;
	}
	public Request(int cod) {
		this.cod = cod;
		this.data = null;
	}
	
	public Request() {}
}