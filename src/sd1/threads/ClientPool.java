package sd1.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import sd1.clients.StoreClient;
import sd1.commons.JsonTools;
import sd1.commons.Request;
import sd1.commons.Response;

public class ClientPool implements Runnable {
	

	
	private String dumpInfo(Socket server) {
		return "-- ClientPool --\nServidor " + server.getInetAddress().getHostAddress();
	}
	
	@Override
	public void run() {
		Socket client = StoreClient.openConn();
		if (client != null) {
			DataInputStream in = StoreClient.getInputSt(client);
			DataOutputStream out = StoreClient.getOutputSt(client);
			if ((in != null) && (out != null)) {
				// Se chegou aqui é por que deu tudo certo na abertura da conexão
				// e dos canais de entrada e saída
				// Criando requisição
				Request req = new Request(Request.REF);
				// Debug 1
//				System.out.println("\n-- A enviar --\n" + req.toString());
				// String da requisição
				if (StoreClient.sendReq(out, JsonTools.gsonExpose.toJson(req))) {
					// Resposta
					String rs = StoreClient.catchRes(in);
					if (rs != null) {
						Response res = JsonTools.gsonExpose.fromJson(rs, Response.class);
						if (res != null) {
							// Debug 2
//							System.out.println("\n-- Recebido --\n" + res.toString());
							
							// Deu ruim?
							if ((res.getCod() == Response.SUCCESS)) {
								StoreClient.setflagPoolSt(true);
								
								while (!StoreClient.hasPoolClose()) {
									rs = StoreClient.catchRes(in);
									/*try {										
										
										
									} catch (Exception e1) {
										e1.printStackTrace();
										StoreClient.setflagPoolCl(true);
										break;
									}*/
									
									if (rs != null) {
										res = JsonTools.gsonExpose.fromJson(rs, Response.class);
										if (res != null) {
											// Debug 2
											System.out.println("\n-- Recebido --\n" + res.toString());
											if ((res.getCod() == Response.SUCCESS) || (res.getCod() == Response.NOT_MOD)) {
												if (res.getCod() == Response.SUCCESS) {
													StoreClient.getPdl().setProductList(JsonTools.PLFromJson(res.getData().toString()));
												}
											} else {
												break;
											}
										} else {
											break;
										}
									} else {
										break;
									}
								}
								
							} else {
								StoreClient.setflagPoolCl(true);
							}
						}
					}
				}
				
				
				try {
					//Fecha os canais de entrada e saída. 
					in.close();
					out.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			try {
				//Fecha o socket. 
				client.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		StoreClient.setflagPoolSt(false);
		StoreClient.setflagPoolCl(false);
	}
		
	public ClientPool() {}
}
