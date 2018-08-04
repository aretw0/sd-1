package sd1.servers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import sd1.commons.*;

public class SimpleServer {
	
	public static void main(String args []) throws IOException{ 
		
		Product p1 = new Product(Product.FOOD,7.89,"Carne");
        Product p2 = new Product(Product.FOOD,5,"Frango");
        Product p3  = new Product(Product.FOOD,26.82,"Marybeth");
        Product p4  = new Product(Product.FOOD,50.61,"Eleonora");
        Product p5  = new Product(Product.FOOD,15.65,"Shauna");
        Product p6  = new Product(Product.FOOD,78.42,"Deonna");
        Product p7  = new Product(Product.FOOD,38.73,"Tara");
        Product p8  = new Product(Product.FOOD,64.69,"Paulita");

        Product p9  = new Product(Product.FOOD,64.69,"Paulita");
        Product p10  = new Product(Product.FOOD,64.69,"Paulita");
        Product p11  = new Product(Product.FOOD,64.69,"Paulita");
        Product p12  = new Product(Product.FOOD,64.69,"Paulita");
        Product p13 = new Product(Product.FOOD,64.69,"Paulita");
        Product p14  = new Product(Product.FOOD,64.69,"Paulita");
        Product p15  = new Product(Product.FOOD,64.69,"Paulita");
        Product p16  = new Product(Product.FOOD,64.69,"Paulita");
        Product p17  = new Product(Product.FOOD,64.69,"Paulita");
        Product p18  = new Product(Product.FOOD,64.69,"Paulita");
        Product p19  = new Product(Product.FOOD,64.69,"Paulita");
        Product p20  = new Product(Product.FOOD,64.69,"Paulita");
        Product p21  = new Product(Product.FOOD,64.69,"Paulita");
        Product p22  = new Product(Product.FOOD,64.69,"Paulita");
        Product p23  = new Product(Product.FOOD,64.69,"Paulita");
        Product p24  = new Product(Product.FOOD,64.69,"Paulita");
        Product p25  = new Product(Product.FOOD,64.69,"Paulita");
        Product p26  = new Product(Product.FOOD,64.69,"Paulita");
        Product p27  = new Product(Product.FOOD,64.69,"Paulita");
        Product p28  = new Product(Product.FOOD,64.69,"Paulita");
        Product p29  = new Product(Product.FOOD,64.69,"Paulita");
        Product p30  = new Product(Product.FOOD,64.69,"Paulita");
        Product p31  = new Product(Product.FOOD,64.69,"aMENHAS");

		
		
        ProductList pl = new ProductList();
		

		pl.push(p1);
		pl.push(p2);
                pl.push(p3);
                pl.push(p4);
                pl.push(p5);
                pl.push(p6);
                pl.push(p7);
                pl.push(p8);

                pl.push(p9);
                pl.push(p10);
                pl.push(p11);
                pl.push(p12);
                pl.push(p13);
                pl.push(p14);
                pl.push(p15);
                pl.push(p16);
                pl.push(p17);
                pl.push(p18);
                pl.push(p19);
                pl.push(p20);
                pl.push(p21);
                pl.push(p22);
                pl.push(p23);
                pl.push(p24);
                pl.push(p25);
                pl.push(p26);
                pl.push(p27);
                pl.push(p28);
                pl.push(p29);
                pl.push(p30);
                pl.push(p31);
                
        Response rs = new Response(Response.SUCCESS,pl.getList());
		 
		
        String res = "";
        res = JsonTools.gsonExpose.toJson(rs);
        
        String req = "";

        //Cria um socket servidor na porta 5000 
        ServerSocket serverSocket=new ServerSocket(5000); 
	 
        System.out.println("\n\nIniciando servidor TCP...\n");

        System.out.println("Servidor pronto para receber conexões...\n\n"); 

        // O metodo accept retorna um socket para comunicação com o próximo 
        // cliente a conectar. 
        // A execução do método bloqueia até que algum cliente conecte no  
        // servidor. 
        Socket socket = serverSocket.accept();	 
        
        // imprime o ip do cliente
        System.out.println("Nova conexão com o cliente " + socket.getInetAddress().getHostAddress()); 

        //Cria um canal para receber dados. 
        DataInputStream in = new DataInputStream(socket.getInputStream()); 

        //Cria um canal para enviar dados. 
        DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 

        req = in.readUTF();
        
        Request request = JsonTools.gsonExpose.fromJson(req, Request.class);
        
        if (request.getCod() == Request.LIS) {
        	out.writeUTF(res);
        }
        
//        out.writeUTF(s); //Envia uma string. 

//        System.out.println("--> Servidor recebido valor: " + req);
//        System.out.println("--> Servidor recebido valor:\n" + request.toString());
        //Fecha os canais in e out do socket que estão atendendo ao cliente 
        in.close(); 

        out.close(); 

        //Fecha o socket para o cliente. 
        socket.close(); 

//        System.out.println("*****Conexão finalizada*****\n"); 

        //Fechando o servidor. 
        serverSocket.close(); 

	} 

}