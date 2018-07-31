package sd1.commons;

public class Product {
	
	private String name;
	private int cod;
	private int type; // Alimentos (0)
	private double price;
	
	static final int NONE = 1; 
	static final int FOOD = 1; 
	static final int PHONE = 2; 
	static final int FURNITURE = 3;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/

}
