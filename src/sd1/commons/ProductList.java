package sd1.commons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductList {

	private List<Product> productList;

	public int getLength() {
		return this.productList.size();
	}

	public boolean push(Product p) {
		return this.productList.add(p);
	}
	
	// só funciona se for a mesma referência
	public boolean contains(Product p) {
		return this.productList.contains(p);
	}
	
	// só funciona se for a mesma referência
	public int indexOf(Product p) {
		return this.productList.indexOf(p);
	}
	
	// só funciona se for a mesma referência
	public boolean remove(Product p) {
		return this.productList.remove(p);
	}
	public boolean remove(int i) {
		return this.productList.remove(i) != null;
	}
	
	// se estiver com a referencia não precisa usar isso aqui, é só alterar a referência
	public boolean update(Product p) {
		for (Product product : productList) {
			if (product.getCod() == p.getCod()) {
				product.setProduct(p);
				return true;
			}
		}
		return false;
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
	
	public ProductList() {
		this.productList = new ArrayList<Product>();
	}
	public ProductList(ArrayList<Product> pl) {
//		this.productList = new ArrayList<Product>(pl);
		this.productList = pl;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProductList pl = new ProductList();
		
		Product p1 = new Product(Product.FOOD,7.89,"Carne");
		Product p2 = new Product(Product.FOOD,5,"Frango");
		Product p3 = new Product(p1);
		p3.setName("Presunto");
		
		pl.push(p1);
		pl.push(p2);
		
//		System.out.print(pl.toString());
//		System.out.println(pl.update(p3));
//		System.out.println(pl.remove(p1));
		System.out.println(pl.indexOf(p1));
		
//		System.out.println();
//		System.out.print(pl.toString());
		
//		exemplo de referencia
//		p1.setName("Presunto");
//		System.out.println(pl.toString());
		
	}

}
