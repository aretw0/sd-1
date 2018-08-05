package sd1.servers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import sd1.commons.*;
import sd1.threads.*;

public class StoreServer {
	private static ProductList pdl = new ProductList();
	private static PoolList pl = new PoolList();
	
	private static boolean doServer = true;
	
	private static void populateList() {
		Product p1 = new Product(Product.FOOD,7.89,"Carne",3);
        Product p2 = new Product(Product.FOOD,5,"Frango");
        Product p3  = new Product(Product.PHONE,700.82,"Moto G",2);
        Product p4  = new Product(Product.FURNITURE,50.61,"Cadeira");
        Product p5  = new Product(Product.FOOD,15.65,"Pizza M");
        Product p6  = new Product(Product.FOOD,78.42,"Queijo Gorgonzola",5);
        Product p7  = new Product(Product.FURNITURE,38.73,"Tamburete");
        Product p8  = new Product(Product.FOOD,64.69,"Torre de Chopp");
        Product p9  = new Product(Product.FOOD,25.69,"Maminha");
        Product p10  = new Product(Product.FOOD,4.69,"Pastel",10);
        
        StoreServer.pdl.push(p1);
        StoreServer.pdl.push(p2);
        StoreServer.pdl.push(p3);
        StoreServer.pdl.push(p4);
        StoreServer.pdl.push(p5);
        StoreServer.pdl.push(p6);
        StoreServer.pdl.push(p7);
        StoreServer.pdl.push(p8);
        StoreServer.pdl.push(p9);
        StoreServer.pdl.push(p10);
	}
	
	public static void shutdownServer() {
		StoreServer.doServer = false;
	}
	
	public static ProductList getPdl() {
		return StoreServer.pdl;
	}
	public static void setPl(ProductList listP) {
		StoreServer.pdl.setProductList(listP);
	}
	
	public static PoolList getPl() {
		return StoreServer.pl;
	}
	
	public static void testServer() {
		// Cria um socket servidor na porta 5000 
        ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(5000);
			System.out.println("\n\nIniciando servidor TCP...\n");
	        System.out.println("Servidor pronto para receber conexões...\n\n");
	        
	        // O metodo accept retorna um socket para comunicação com o próximo 
	        // cliente a conectar. 
	        // A execução do método bloqueia até que algum cliente conecte no  
	        // servidor. 
	        Socket socket = serverSocket.accept();
	        // imprime o ip do cliente
	        System.out.println("Nova conexão com o cliente " + socket.getInetAddress().getHostAddress());
	        
	        //Fecha o socket para o cliente. 
	        socket.close();
	        
	        System.out.println("***** Conexão finalizada *****\n"); 
	        
	        //Fechando o servidor. 
	        serverSocket.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void startServer() {
		// Cria um socket servidor na porta 5000 
        ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(5000);
			System.out.println("\n\nIniciando servidor TCP...\n");
	        System.out.println("Servidor pronto para receber conexões...\n\n");
	        
	        while(StoreServer.doServer) {
	        	// O metodo accept retorna um socket para comunicação com o próximo 
		        // cliente a conectar. 
		        // A execução do método bloqueia até que algum cliente conecte no  
		        // servidor. 
	        	Thread t = new Thread(new StoreHandler(serverSocket.accept()));
	        	t.start();
	        }
	        
	        //Fechando o servidor. 
	        serverSocket.close(); 
//	        System.out.println("\n\nServidor finalizado!\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args []) throws IOException{
		
		StoreServer.populateList();

		startServer();
//		testServer();
		
//		Request req = JsonTools.gsonExpose.fromJson(JsonTools.gsonExpose.toJson(new Request(Request.DEL,"Pastel")),Request.class);
		
//		System.out.println(req);
//		String name = (String) req.getData();
		/*for (int i = 0; i < name.length(); i++) {
			System.out.print(name.charAt(i));
			
		}*/
//		System.out.println(name);
//		System.out.println(name.equals("Pastel"));
//		System.out.println(StoreServer.getPl().remove(req.getData().toString()));
		
		
		
	}
}
