package sd1.commons;

import com.google.gson.annotations.Expose;

public class ProductChange {
	
	@Expose
	private int oldCod;
	
	@Expose
	private int cod;
	
	@Expose
	private int type;

	@Expose
	private double price;
	
	@Expose
	private String name;

	@Expose
	private int amount;
	
	public int getOldCod() {
		return oldCod;
	}
	
	public void setOldCod(int oldCod) {
		this.oldCod = oldCod;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.replace(' ', '_');;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "\nVelho Código:\t"+this.oldCod+"\nNovo Código:\t" + this.cod + "\nTipo:\t\t" + Product.types[this.type] + "\nPreço:\t\t" + this.price + "\nNome:\t\t" + this.name + "\nQuantidade:\t" + this.amount;
	}
	public ProductChange(int oldCod, int cod, int type, double price, String name, int amount) {
		this.oldCod = oldCod;
		this.cod = cod;
		this.type = type;
		this.price = price;
		this.name = name.replace(' ', '_');;
		this.amount = amount;
	}
	public ProductChange(int cod, int type, double price, String name, int amount) {
		this.oldCod = cod;
		this.cod = cod;
		this.type = type;
		this.price = price;
		this.name = name.replace(' ', '_');;
		this.amount = amount;
	}
	public ProductChange(int oldCod, Product p) {
		this.oldCod = oldCod;
		this.cod = p.getCod();
		this.type = p.getType();
		this.price = p.getPrice();
		this.name = p.getName();
		this.amount = p.getAmount();
	}
	
	public ProductChange(Product p) {
		this.oldCod = p.getCod();
		this.cod = p.getCod();
		this.type = p.getType();
		this.price = p.getPrice();
		this.name = p.getName();
		this.amount = p.getAmount();
	}
	
	public ProductChange() {}
}
