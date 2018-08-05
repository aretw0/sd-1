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
			/*System.out.println(this.dumpInfo());
			System.out.println("-- A enviar --\n" + res.toString());*/
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
				/*System.out.println(this.dumpInfo());
				System.out.println("-- Recebido --\n" + req.toString());*/
				
				switch (req.getCod()) {
					case Request.SHS:
						// shutdown server
						this.doResponse(new Response(Response.SUCCESS));
						StoreServer.shutdownServer();
						break;
					case Request.SHSC:
						// Confirmação do shutdown
						break;
					case Request.ADD:
						// Adicionar Produto
						if (req.getData() != null) {
							if (StoreServer.getPdl().push(JsonTools.gsonExpose.fromJson(req.getData().toString(),ProductTemp.class))) {								
								this.doResponse(new Response(Response.SUCCESS));
							} else {
								this.doResponse(new Response(Response.FAIL));
							}
						} else {
							this.doResponse(new Response(Response.NOT_FOUND));
						}
						break;
					case Request.DEL:
						// Remover produto por nome
						if (req.getData() != null) {
							if (StoreServer.getPdl().remove(req.getData().toString())) {
								this.doResponse(new Response(Response.SUCCESS));
							} else {
								this.doResponse(new Response(Response.FAIL));
							}
						} else {
							this.doResponse(new Response(Response.NOT_FOUND));
						}
						break;
					case Request.LIS:
						// Listar produtos
						this.doResponse(new Response(Response.SUCCESS,StoreServer.getPdl().getList()));
						break;
					case Request.SEA_C:
						// Procura por código
						if (req.getData() != null) {
							this.doResponse(new Response(Response.SUCCESS,StoreServer.getPdl().getProduct(Integer.parseInt(req.getData().toString()))));
						} else {
							this.doResponse(new Response(Response.NOT_FOUND));
						}
						break;
					case Request.SEA_N:
						// Procura por nome de produto
						if (req.getData() != null) {
							this.doResponse(new Response(Response.SUCCESS,StoreServer.getPdl().getProduct(req.getData().toString())));
						} else {
							this.doResponse(new Response(Response.NOT_FOUND));
						}
						break;
					case Request.UPD:
						// Atualizar produto
						if (req.getData() != null) {
							if (StoreServer.getPdl().update(JsonTools.gsonExpose.fromJson(req.getData().toString(), ProductChange.class))) {								
								this.doResponse(new Response(Response.SUCCESS));
							} else {
								this.doResponse(new Response(Response.NOT_MOD));
							}
						} else {
							this.doResponse(new Response(Response.NOT_FOUND));
						}
						break;
					case Request.AMO:
						// Quantidade de Produtos			
						this.doResponse(new Response(Response.SUCCESS,Integer.toString(StoreServer.getPdl().getLength())));
						break;
					case Request.BUY:
						if (req.getData() != null) {
							
							if (StoreServer.getPdl().buyProduct(Integer.parseInt(req.getData().toString()))) {
								this.doResponse(new Response(Response.SUCCESS));
							} else {
								this.doResponse(new Response(Response.NOT_MOD));
							}
							
						} else {
							this.doResponse(new Response(Response.NOT_FOUND));
						}
						break;
					case Request.REF:
						// Incrição das modificação na Lista de Produtos
						if (StoreServer.getPl().push(this.client.getInetAddress().getHostAddress())) {
							this.doResponse(new Response(Response.SUCCESS));
							this.doResponse(new Response(Response.SUCCESS,StoreServer.getPdl().getList()));
							ProductList copy = new ProductList(StoreServer.getPdl().copy());
							while(StoreServer.getPl().contains(this.client.getInetAddress().getHostAddress())) {
								if (StoreServer.getPdl().equals(copy.getList())) {
									this.doResponse(new Response(Response.NOT_MOD));
								} else {
									this.doResponse(new Response(Response.SUCCESS,StoreServer.getPdl().getList()));
									copy.setProductList(StoreServer.getPdl());
								}
								try {
									Thread.sleep(200);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}							
						} else {
							this.doResponse(new Response(Response.NOT_MOD));
						}
						break;
					case Request.CLO:
						// Cancelamento da inscrição
						if (StoreServer.getPl().remove(this.client.getInetAddress().getHostAddress())) {
							this.doResponse(new Response(Response.SUCCESS));
						} else {
							this.doResponse(new Response(Response.NOT_FOUND));
						}
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
