package sd1.commons;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductList {

	@Expose
	private ObservableList<Product> productList = FXCollections.observableArrayList();
	
	public boolean equals(List<Product> pl) {
		if (this.productList.size() == pl.size()) {
			for (Product product : pl) {
				if (!this.contains(product)) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}
	public ObservableList<Product> copy() {
		ObservableList<Product> newOne = FXCollections.observableArrayList();
		for (Product product : this.productList) {
			newOne.add(new Product(product));
		}
		return newOne;
	}
	public ObservableList<Product> getList() {
		return this.productList;
	}
	public int getLength() {
		return this.productList.size();
	}

	// Retirando a referência sempre
	public boolean push(Product p) {
		if (contains(p.getCod(),p.getName())) {
			return false;
		}
		return this.productList.add(new Product(p));
	}
	// Retirando a referência sempre
	public boolean push(ProductTemp p) {
		if (contains(p.getName())) {
			return false;
		}
		return this.productList.add(new Product(p));
	}
	
	// Se igual o cod contém
	public boolean contains(int cod) {
		for (Product product : productList) {
			if (product.getCod() == cod) {
				return true;
			}
		}
		return false;
	}
	
	// se igual o nome contém
	public boolean contains(String name) {
		for (Product product : productList) {
			if (product.getName().toLowerCase().equals(name.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	// Se igual o cod ou o nome contém
	public boolean contains(int cod,String name) {
		for (Product product : productList) {
			if ((product.getName().toLowerCase().equals(name.toLowerCase())) || (product.getCod() == cod)) {
				return true;
			}
		}
		return false;
	}
	
	// Funciona se for a mesma referência ou os mesmos atributos
	public boolean contains(Product p) {
		if (this.productList.contains(p)) {
			return true;
		} else {
			for (Product product : productList) {
				if (product.equals(p)) {
					return true;
				}
			}
		}
		return false;
	}
	
	// Funciona se for a mesma referência ou os mesmso atributos
	public int indexOf(Product p) {
		int i = this.productList.indexOf(p);
		if (i == -1) {
			for (Product product : productList) {
				if (product.equals(p)) {
					return this.productList.indexOf(product);
				}
			}
		}
		return i;
	}
	
	// Funciona se for a mesma referência ou o mesmo atributo cod
	// Atenção: não estou olhando os outros atributos
	public boolean remove(Product p) {
		if (!this.productList.remove(p)) {
			for (Product product : productList) {
				if (product.getCod() == p.getCod()) {
					return this.productList.remove(product);
				}
			}
		} else {
			return true;
		}
		return false;
	}
	// Funciona se for o mesmo atributo cod
	public boolean remove(int cod) {
		for (Product product : productList) {
			if (product.getCod() == cod) {
				return this.productList.remove(product);
			}
		}
		return false;
	}
	
	public boolean remove(String name) {
//		System.out.println(name);
		for (Product product : this.productList) {
			// Por algum motivo a string do Gson vem diferente então precisa ser comparado assim
			if (product.getName().toLowerCase().equals(name.toLowerCase())) {
				return this.productList.remove(product);
			}
		}
		return false;
	}
	
	public boolean update(Product p) {
		for (Product product : productList) {
			if (product.getCod() == p.getCod()) {
				product.setProduct(p);
				return true;
			}
		}
		return false;
	}
	public boolean update(ProductChange p) {
		// TODO revisar isso
		for (Product product : productList) {
			// se código novo for igual a alguém mas esse alguém não o código velho então não pode atualizar
			if ((product.getCod() == p.getCod()) && (product.getCod() != p.getOldCod())) {
				return false;
			}
			// se o novo nome for igual ao de alguém mas esse alguém não é quem estamos atualizando então não
			if (product.getName().toLowerCase().equals(p.getName().toLowerCase())) {
				if (product.getCod() != p.getOldCod()) {
					return false;
				}
			}
			
			// se achamos quem vamos atualizar
			if (product.getCod() == p.getOldCod()) {
				// e o campo novo vai ser atualizado
				if (!product.getName().toLowerCase().equals(p.getName().toLowerCase())) {
					// se esse nome ja existir não atualize
					if (contains(p.getName())) {
						return false;
					}
				}
				// se chegar aqui então tudo bem, pode atualizar
				product.setProduct(p);
				return true;
			}
		}
		return false;
	}
	
	public Product getProduct(String name) {
		for (Product product : productList) {
			if (product.getName().toLowerCase().equals(name.toLowerCase())) {
				return product;
			}
		}
		return null;
	}
	
	public boolean buyProduct(Product pd) {
		Product pB = getProduct(pd.getCod());
		if (pB == null) {
			return false;
		}
		return pB.decAmount();
	}
	public boolean buyProduct(int cod) {
		Product pB = getProduct(cod);
		if (pB == null) {
			return false;
		}
		return pB.decAmount();
	}
	public Product getProduct(int cod) {
		for (Product product : productList) {
			if (product.getCod() == cod) {
				return product;
			}
		}
		return null;
	}
	
	public void updateList(List<Product> pl) {
		for (Product product : pl) {
			if (!this.update(product)) {
				this.push(product);
			}
		}
	}
	
	public void clear() {
		this.productList.clear();
	}
	
	@Override
	public String toString() {
		String result = "";
		
		for (Product product : productList) {
			result += (product.toString() + "\n\n");
		}
		return result;
	}
	
	public void setProductList(ProductList pl) {
		clear();
		for (Product product : pl.getList()) {
			this.productList.add(new Product(product));
		}
	}

	public void setProductList(List<Product> pl) {
		clear();
		for (Product product : pl) {
			this.productList.add(new Product(product));
		}
	}
	
	public ProductList() {}
	
	// Cria uma nova lista a partir de outra sem pegar a referência
	public ProductList(ObservableList<Product> list) {
		this.productList = list;
	}
	// Cria uma nova lista a partir de outra sem pegar a referência
	public ProductList(ProductList pl) {
		for (Product product : pl.getList()) {
			this.productList.add(new Product(product));
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Product p1 = new Product(Product.FOOD,7.89,"Carne",8);
		Product p2 = new Product(Product.FOOD,5,"Pastel",5);
		
		Product p3 = new Product(Product.FOOD,7.89,"Carne",8);
		
		
		ProductList pl = new ProductList();
		
		pl.push(p1);
		pl.push(p2);
		
//		List<Product> ps = pl.copy();
//		ps.add(p1);
//		ps.add(p2);
		
		System.out.print(pl.toString());
		System.out.println();
		
		System.out.println(pl.buyProduct(p1));
		
		System.out.print(pl.toString());
		System.out.println();
		
		
		
//		System.out.println(pl.contains("Pastel"));
		
//		String json = JsonTools.gsonExpose.toJson(pl);
		
//		String json = JsonTools.gsonExpose.toJson(pl.getList());
//		System.out.println(json);
//		System.out.println();
		
//		ProductList pl2 = JsonTools.gsonExpose.fromJson(json, ProductList.class);
		
//		List<Product> pds = JsonTools.gsonExpose.fromJson(json, new TypeToken<List<Product>>(){}.getType());
//		List<Product> pds = JsonTools.PLFromJson(json);
		
//		ProductList pl2 = new ProductList(JsonTools.PLFromJson(json));
		
		
//		System.out.print(pl2.toString());
//		System.out.println();
		
	}

}
