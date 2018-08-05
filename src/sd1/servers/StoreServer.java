package sd1.servers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import sd1.commons.Product;
import sd1.commons.ProductList;
import sd1.threads.StoreHandler;

public class StoreServer {
	private static ProductList pl = new ProductList();
	
	private static boolean doServer = true;
	
	private static void populateList() {
		Product p1 = new Product(Product.FOOD,7.89,"Carne");
        Product p2 = new Product(Product.FOOD,5,"Frango");
        Product p3  = new Product(Product.PHONE,700.82,"Moto G");
        Product p4  = new Product(Product.FURNITURE,50.61,"Cadeira");
        Product p5  = new Product(Product.FOOD,15.65,"Pizza M");
        Product p6  = new Product(Product.FOOD,78.42,"Queijo Gorgonzola");
        Product p7  = new Product(Product.FURNITURE,38.73,"Tamburete");
        Product p8  = new Product(Product.FOOD,64.69,"Torre de Chopp");
        Product p9  = new Product(Product.FOOD,25.69,"Maminha");
        Product p10  = new Product(Product.FOOD,4.69,"Pastel");
        
        StoreServer.pl.push(p1);
        StoreServer.pl.push(p2);
        StoreServer.pl.push(p3);
        StoreServer.pl.push(p4);
        StoreServer.pl.push(p5);
        StoreServer.pl.push(p6);
        StoreServer.pl.push(p7);
        StoreServer.pl.push(p8);
        StoreServer.pl.push(p9);
        StoreServer.pl.push(p10);
	}
	
	public static void shutdownServer() {
		StoreServer.doServer = false;
	}
	
	public static ProductList getPl() {
		return pl;
	}
	public static void setPl(ProductList listP) {
		pl.setProductList(pl);
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
		
		 StoreServer.populateList();
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
		
		startServer();
//		testServer();
		
		
		
	}
}
