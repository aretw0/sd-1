package sd1.threads;

/** A interface Runnable oferece um contrato que torna
 * a classe que a implementa executável.
 * Há a opção de estender a classe Thread. No entanto, herdamos 
 * um monte de métodos e só implementamos o run.
 * Runnable estabelece que a classe deve implementar somente o run
 * tornando-a uma opção mais compacta e compatível com polimorfismo */

public class Programa implements Runnable {

	 private int id;   
	 
	 public void setId(int i){
		 this.id = i;
	 } 

	 public int getId(){
		 return this.id;
	 }
	 
	 public void run () {

	     for (int i = 0; i < 1000; i++) {

	         System.out.println("Programa " + id + " valor: " + i);
	         
	         
	         try {
				Thread.sleep(500);
	         } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	         }
			 	
	     }

	  }

}
