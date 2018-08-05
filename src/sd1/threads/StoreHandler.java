package sd1.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import sd1.commons.*;
import sd1.servers.StoreServer;

public class StoreHandler implements Runnable {

	private int id;
	private boolean setup = false;
	private Socket client;
	private DataInputStream in;
	private DataOutputStream out;
	
	private static int idSeed = 1;
	
	public Socket getClient() {
		return client;
	}
	public void setClient(Socket client) {
		this.client = client;
		setupIO();
	}
	
	private void setupIO() {
		try {
			this.in = new DataInputStream(client.getInputStream());
			this.out = new DataOutputStream(client.getOutputStream());
			
			this.setup = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void closeConn() {
		try {
			if (this.setup) {
				//Fecha os canais de entrada e saída. 
				this.in.close();
				this.out.close();
			}
			//Fecha o socket. 
			this.client.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private String dumpInfo() {
		return "-- StoreHandler " + this.id + " --\nCliente " + this.client.getInetAddress().getHostAddress();
	}
	
	private void doResponse(Response res) {
		try {
//			System.out.println(this.dumpInfo());
//			System.out.println("-- A enviar --\n" + res.toString());
			out.writeUTF(JsonTools.gsonExpose.toJson(res));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Request getRequest() {
		try {
			return JsonTools.gsonExpose.fromJson(this.in.readUTF(), Request.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
//		System.out.println(this.dumpInfo());
		
		if (!this.setup) {
			System.out.println("\nNão foi possível configurar IN|OUT\n");
		} else {
			// Aqui esta tudo certo
			
			// Pegue a requisição do cliente
			Request req = this.getRequest();
			
			if (req != null) {
//				System.out.println(this.dumpInfo());
//				System.out.println("-- Recebido --\n" + req.toString());
				
				switch (req.getCod()) {
					case Request.SHS:
						// shutdown server
						StoreServer.shutdownServer();
						this.doResponse(new Response(Response.SUCCESS));
						break;
					case Request.SHSC:
						// Confirmação do shutdown
						break;
					case Request.ADD:
						// Adicionar Produto
						break;
					case Request.DEL:
						// Remover produto por nome
						break;
					case Request.LIS:
						// Listar produtos
						this.doResponse(new Response(Response.SUCCESS,StoreServer.getPl().getList()));
						break;
					case Request.SEA_C:
						// Procura por código
						break;
					case Request.SEA_N:
						// Procura por nome de produto
						break;
					case Request.UPD:
						// Atualizar produto
						break;
					case Request.AMO:
						// Quantidade de Produtos
						break;
					case Request.REF:
						// Incrição das modificação na Lista de Produtos
						break;
					case Request.CLO:
						// Cancelamento da inscrição
						break;
				}
				
			} else {
				// aqui a requisição nao pode ser montada
				this.doResponse(new Response(Response.FAIL));
				
			}			
		}
		// Fechando tudo
		this.closeConn();
		
		// fins de debug
//		StoreServer.shutdownServer();
	}
	
	public StoreHandler() {
		this.id = StoreHandler.idSeed++;
	}
	public StoreHandler(Socket client) {
		this.id = StoreHandler.idSeed++;
		this.client = client;
		setupIO();
	}

}
