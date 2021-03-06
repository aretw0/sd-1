package sd1.commons;

import java.util.Random;

import com.google.gson.annotations.Expose;

public class Product {
	
	
	@Expose
	private int cod;
	
	@Expose
	private int type;
	
	@Expose
	private double price;
	// Problemas com a conversão da lib GSON implica que esse campo não pode ter espaços em branco
	// Por isso estou adicionando um replace em qualquer entrada desse campo, todo espaço vira agora "_"
	@Expose
	private String name;
	
	@Expose
	private int amount;
	
	private boolean empty = true;
	
	public static final int NONE = 0; 
	public static final int FOOD = 1; 
	public static final int PHONE = 2; 
	public static final int FURNITURE = 3;
	
	public static final String[] types = {"None","Alimentos","Telefonia","Móveis"};
	
	private static int codSeed = 1;
	
//	static final int MINC = 100;
//	static final int MAXC = 999;
	
//	private Random generator = new Random();

	public boolean isEmpty() {
		return (this.name == null) && (this.cod == 0);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name.replace(' ', '_');
	}
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int a) {
		this.amount = a;
	}
	
	public void incAmount() {
		++this.amount;
	}
	public boolean decAmount() {
		if (this.amount > 0) {			
			--this.amount;
			return true;
		}
		return false;
	}
	
	public boolean equals(Product p) {
		return ((p.getCod() == this.cod)&&(this.name.toLowerCase().equals(p.getName().toLowerCase()))&&(p.getAmount() == this.amount) && (p.getType() == this.type)&&(p.getPrice() == this.price));
	}
	
	public void setProduct(Product p) {
		this.cod = p.getCod();
		this.type = p.getType();
		this.price = p.getPrice();
		this.name = p.getName();
		this.amount = p.getAmount();
	}
	public void setProduct(ProductChange p) {
		this.cod = p.getCod();
		this.type = p.getType();
		this.price = p.getPrice();
		this.name = p.getName();
		this.amount = p.getAmount();
	}
	
	public Product() {}
	public Product(int type, double price, String name) {
//		this.cod = Product.MINC + (int)(this.generator.nextDouble() * ((Product.MAXC - Product.MINC) + 1));
		this.cod = Product.codSeed++;
		this.type = type;
		this.price = price;
		this.name = name.replace(' ', '_');
		this.amount = 0;
	}
	public Product(int type, double price, String name, int amount) {
		this.cod = Product.codSeed++;
		this.type = type;
		this.price = price;
		this.name = name.replace(' ', '_');
		this.amount = amount;
	}
	
	public Product(int cod,int type, double price, String name, int amount) {
		this.cod = cod;
		this.type = type;
		this.price = price;
		this.name = name.replace(' ', '_');
		this.amount = amount;
	}
	
	public Product(Product p) {
		this.cod = p.getCod();
		this.type = p.getType();
		this.price = p.getPrice();
		this.name = p.getName();
		this.amount = p.getAmount();
	}
	
	// usado so ao transferir 
	public Product(ProductTemp p) {
		this.cod = Product.codSeed++;
		this.type = p.getType();
		this.price = p.getPrice();
		this.name = p.getName().replace(' ', '_');
		this.amount = p.getAmount();
	}
	
	// usado so ao transferir 
	public Product(ProductChange p) {
		this.cod = p.getCod();
		this.type = p.getType();
		this.price = p.getPrice();
		this.name = p.getName().replace(' ', '_');
		this.amount = p.getAmount();
	}
	
	@Override
	public String toString() {
		return "\nCódigo:\t\t" + this.cod + "\nTipo:\t\t" + Product.types[this.type] + "\nPreço:\t\t" + this.price + "\nNome:\t\t" + this.name + "\nQuantidade:\t" + this.amount;
	}
	
	/*public static void main(String[] args) {
		
		Product p = new Product();
		if (p.isEmpty()) {
			System.out.println("Null");
		} else {
			System.out.println("Não null" + p.getName());
		}
//		Product p1 = new Product(Product.FOOD,7.89,"Frango");
//		Product p2 = new Product(p1);
//		
//		System.out.println(p1.toString());
		
//		String json = JsonTools.gsonExpose.toJson(p1);
		
//		System.out.println(json);
		
//		Product p2 = JsonTools.gsonExpose.fromJson(json, Product.class);
		
//		System.out.println(p2.toString());
//		
//		System.out.println(p2.equals(p1));
		
	}*/

}
