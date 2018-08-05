package sd1.commons;

import com.google.gson.annotations.Expose;

public class Request {
	public static final int ADD = 1; 	// Adicionar Produto 
	public static final int DEL = 2; 	// Remover Produto
	public static final int LIS = 3; 	// Listar Produtos
	public static final int SEA_N = 4; 	// Procurar Produto por nome
	public static final int SEA_C = 5; 	// Procurar Produto por código
	public static final int UPD = 6; 	// Atualizar Produto
	public static final int REF = 7; 	// Inscrição no pool de atualização da lista
	public static final int AMO = 8; 	// Quantidade de produtos
	public static final int BUY = 9;	// Comprar produto
	public static final int CLO = 10; 	// Fechar pool para este cliente
	public static final int SHS = 11; 	// Shutdown Server
	public static final int SHSC = 12; 	// Shutdown Server confirmação	
	
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