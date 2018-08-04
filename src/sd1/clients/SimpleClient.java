package sd1.clients;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import sd1.commons.*;

public class SimpleClient {
	
	private static Scanner sc;

	public static void main(String args []) throws IOException{ 

        String req = "";
        String res = "";
        
        Request rq = new Request(Request.LIS,null);

        req = JsonTools.gsonExpose.toJson(rq);
		// Conectar ao servidor 
		Socket client = new Socket("localhost", 5000);

		// Cria canal para receber dados 
		DataInputStream in = new DataInputStream(client.getInputStream()); 

		// Cria canal para enviar dados 
		DataOutputStream out = new DataOutputStream(client.getOutputStream()); 

//		sc = new Scanner(System.in);
//		System.out.println("Digite um valor entre 0 e 9");
		
//		s = sc.nextLine();
//		Product p1 = new Product(Product.FOOD,7.89,"Carne");
//		s = JsonTools.gsonExpose.toJson(p1);
		 
		//k = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite um valor entre 0 e 9.")); 

//		System.out.println("\n\n <-- Valor enviado ao servidor: "+ k); 

		out.writeUTF(req); 

		res = in.readUTF(); //Aguarda o recebimento de uma string. 
		
		Response rs = JsonTools.gsonExpose.fromJson(res, Response.class);
		
		if (rs.getData() != null) {
			System.out.println("Mas o que!!");
//			System.out.println(rs.getClass().toString());
			
			// IMPORTANTE FORMA DE RECUPERAÇÃO DA LISTA!!!!
			ProductList pl = new ProductList(JsonTools.PLFromJson(rs.getData().toString()));
			System.out.println("\n\n --> Mensagem recebida \nValor por extenso é:\n"+ pl.toString()); 
		} else {
			System.out.println("Mas como!!");
		}

//		System.out.println("\n\n --> Mensagem recebida \nValor por extenso é:\n"+ res + "\n\n"); 
//		System.out.println("\n\n --> Mensagem recebida \nValor por extenso é:\n"+ rs.getData()); 

		//Fecha os canais de entrada e saída. 
		in.close(); 

		out.close(); 

		//Fecha o socket. 
		client.close(); 
	}

}