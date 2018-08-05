package sd1.commons;

import com.google.gson.annotations.Expose;

public class Response {
	public static final int SUCCESS = 200;
	public static final int FAIL = 500;
	public static final int NOTMOD = 304;
	public static final int NOT_FOUND = 404;
	
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

	public Response(int cod, Object data) {
		this.cod = cod;
		this.data = data;
	}
	public Response(int cod) {
		// TODO Auto-generated constructor stub
		this.cod = cod;
		this.data = null;
	}
	public Response() {}	
}
