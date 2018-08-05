package sd1.clients;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import sd1.commons.*;

public class StoreClient {
	private static ProductList pl = new ProductList();
	private static boolean poolStarted = false;
	private static boolean poolClose = false;
	private static String hostAdd = "localhost";
	private static int hostPort = 5000;
	
	private static ProductList getPl() {
		return pl;
	}
	private static void setPl(ProductList listP) {
		pl.setProductList(pl);
	}
	
	public static String getHostAdd() {
		return hostAdd;
	}
	
	public static void setHostAdd(String host) {
		hostAdd = host;
	}
	
	public static int getHostPort() {
		return hostPort;
	}
	
	public static void setHostPort(int host) {
		hostPort = host;
	}
	
	public static void testServer() throws UnknownHostException, IOException {
		// Conectar ao servidor 
		Socket client = new Socket(hostAdd, 5000);
		
		//Fecha o socket. 
		client.close(); 
	}
	
	public static Socket openConn() {
		try {
			return new Socket(hostAdd, hostPort);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static DataInputStream getInputSt(Socket client) {
		
		try {
			// Cria canal para receber dados 
			return new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}
	
	private static DataOutputStream getOutputSt(Socket client) {
		
		try {
			// Cria canal para enviar dados 
			return new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}
	
	private static boolean sendReq(DataOutputStream out, String req) {
		try {
			out.writeUTF(req);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	private static String catchRes(DataInputStream in) {
		try {
			return in.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static void StartListPool() {
		poolStarted = true;
		System.out.println("\nIniciando Pool da Lista!\n");
		/*while(!poolClose) {
			System.out.println("\nIteração do Pool!\n");
		}*/
		
	}
	
	public static Response doRequest(Request req) {
		
		// Este inicio vai estar em todas as requisições
		Socket client = openConn();
		if (client == null) {
			return null;
		}
		DataInputStream in = getInputSt(client);
		DataOutputStream out = getOutputSt(client);
		if ((in == null) || (out == null)) {
			return null;
		}
		// Se chegou aqui é por que deu tudo certo na abertura da conexão
		// e dos canais de entrada e saída
		
		// Debug
		System.out.println("\n-- A enviar --\n" + req.toString());
		// String da requisição
		if (!sendReq(out, JsonTools.gsonExpose.toJson(req))) {
			return null;
		}
		// Se for uma requisição de desligamento do servidor não precisa de resposta.
		if (req.getCod() == Request.SHSC) {
			return null;
		}
		// Resposta
		String rs = catchRes(in);
		
		try {
			//Fecha os canais de entrada e saída. 
			in.close();
			out.close();
			//Fecha o socket. 
			client.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (rs == null) {
			return null;
		}
		
		Response res = JsonTools.gsonExpose.fromJson(rs, Response.class);
		if (res == null) {
			return null;
		}
		// Debug
		System.out.println("\n-- Recebido --\n" + res.toString());
		// Deu ruim?
		if ((res.getCod() == Response.FAIL) || (res.getCod() == Response.NOT_FOUND)) {
			
			return null;
		}
		
		return res;
	}
	
	// ADD PRODUCT
	// ProductTemp não tem cod
	public static boolean addProduct(ProductTemp pd) {
		
		Response res = doRequest(new Request(Request.ADD,pd));
		
		if (res == null) {
			return false;
		}
		
		return true;
	}
	// ADD PRODUCT
	// Product tem cod e pode ser definido
	public static boolean addProduct(Product pd) {
		Response res = doRequest(new Request(Request.ADD,pd));
		
		if (res == null) {
			return false;
		}
		
		return true;
	}
	
	// DEL PRODUCT
	public static boolean delProduct(String pd) {
		Response res = doRequest(new Request(Request.DEL,pd));
		
		if (res == null) {
			return false;
		}
		
		return true;
	}
	
	// GETLIST
	public static ProductList getList() {
		Response res = doRequest(new Request(Request.LIS));
		
		if (res == null) {
			return null;
		}
		
		// Lista vazia
		if (res.getData() == null) {
			return new ProductList();
		}
	
		// IMPORTANTE FORMA DE RECUPERAÇÃO!!!!
		return new ProductList(JsonTools.PLFromJson(res.getData().toString()));
	}
	
	public static Product getProduct(int cod) {
		Response res = doRequest(new Request(Request.SEA_C,Integer.toString(cod)));
		
		if (res == null) {
			return null;
		}
		
		// Não há objeto
		if (res.getData() == null) {
			return new Product();
		}
			
		// IMPORTANTE FORMA DE RECUPERAÇÃO!!!!
		return JsonTools.gsonExpose.fromJson(res.getData().toString(),Product.class);

	}
	
	public static Product getProduct(String pd) {
		Response res = doRequest(new Request(Request.SEA_N,pd));
		
		if (res == null) {
			return null;
		}
		
		// Não há objeto
		if (res.getData() == null) {
			return new Product();
		}
			
		// IMPORTANTE FORMA DE RECUPERAÇÃO!!!!
		return JsonTools.gsonExpose.fromJson(res.getData().toString(),Product.class);

	}
	
	public static boolean updProduct(Product pd) {
		Response res = doRequest(new Request(Request.UPD,pd));
		
		if (res == null) {
			return false;
		}
		
		return true;
	}
	
	public static boolean shutDownServer() {
		Response res = doRequest(new Request(Request.SHS));
		
		if (res == null) {
			return false;
		}
		return true;
	}
	
	public static void shutdownServerConfirm() {
		doRequest(new Request(Request.SHSC));
	}
	
	public static void main(String args []) throws IOException{
		// Conectar ao servidor 
//		Socket client = new Socket("localhost", 5000);
		
		//Fecha o socket. 
//		client.close(); 
		
//		testServer();
//		System.out.println("\nEndereço de servidor: "+ getHostAdd() +"\n");
//		StartListPool();
		ProductList pla = getList();
		
		
		/*if (pla != null) {
			System.out.println("\n\n --> Lista recebida \nValor por extenso é:\n"+ pla.toString());
		}*/	
		
		if (shutDownServer()) {
			 shutdownServerConfirm();
		}
	}
}
