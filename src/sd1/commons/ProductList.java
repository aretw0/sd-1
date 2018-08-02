package sd1.commons;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

public class ProductList {

	@Expose
	private List<Product> productList = new ArrayList<Product>();

	public List<Product> getList() {
		return this.productList;
	}
	public int getLength() {
		return this.productList.size();
	}

	public boolean push(Product p) {
		return this.productList.add(p);
	}
	
	// Funciona se for a mesma referência ou o mesmo atributo cod
	// Atenção: não estou olhando os outros atributos
	public boolean contains(Product p) {
		if (this.productList.contains(p)) {
			return true;
		} else {
			for (Product product : productList) {
				if (product.getCod() == p.getCod()) {
					return true;
				}
			}
		}
		return false;
	}
	
	// Funciona se for a mesma referência ou o mesmo atributo cod
	// Atenção: não estou olhando os outros atributos
	public int indexOf(Product p) {
		int i = this.productList.indexOf(p);
		if (i == -1) {
			for (Product product : productList) {
				if (product.getCod() == p.getCod()) {
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
	
	// Se estiver com a referencia não precisa usar isso aqui, é só alterar a referência
	public boolean update(Product p) {
		for (Product product : productList) {
			if (product.getCod() == p.getCod()) {
				product.setProduct(p);
				return true;
			}
		}
		return false;
	}
	
	public Product getProduct(String name) {
		for (Product product : productList) {
			if (product.getName() == name) {
				return product;
			}
		}
		return null;
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
				this.push(new Product(product));
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
	
	public ProductList() {}
	
	// Cria uma nova lista a partir de outra sem pegar a referência
	public ProductList(List<Product> pl) {
		for (Product product : pl) {
			this.productList.add(new Product(product));
		}
	}
	// Cria uma nova lista a partir de outra sem pegar a referência
	public ProductList(ProductList pl) {
		for (Product product : pl.getList()) {
			this.productList.add(new Product(product));
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProductList pl = new ProductList();
		
		Product p1 = new Product(Product.FOOD,7.89,"Carne");
		Product p2 = new Product(Product.FOOD,5,"Frango");
		
		pl.push(p1);
		pl.push(p2);
		
		
		System.out.print(pl.toString());
		System.out.println();
//		String json = JsonTools.gsonExpose.toJson(pl);
		
		String json = JsonTools.gsonExpose.toJson(pl.getList());
		System.out.println(json);
		System.out.println();
		
//		ProductList pl2 = JsonTools.gsonExpose.fromJson(json, ProductList.class);
		
//		List<Product> pds = JsonTools.gsonExpose.fromJson(json, new TypeToken<List<Product>>(){}.getType());
//		List<Product> pds = JsonTools.PLFromJson(json);
		
		ProductList pl2 = new ProductList(JsonTools.PLFromJson(json));
		
		
		System.out.print(pl2.toString());
		System.out.println();
		
	}

}
