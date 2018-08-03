package sd1.commons;

import com.google.gson.annotations.Expose;

public class ProductTemp {
	@Expose
	private int type;

	@Expose
	private double price;
	
	@Expose
	private String name;
	
	@Expose
	private int amount;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public ProductTemp(int type, double price, String name, int amount) {
		this.type = type;
		this.price = price;
		this.name = name;
		this.amount = amount;
	}
	public ProductTemp() {}
	
}
