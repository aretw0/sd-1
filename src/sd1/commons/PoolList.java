package sd1.commons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PoolList {
	private List<String> clients = new ArrayList<String>();
	
	public boolean push(String ip) {
		for (String string : clients) {
			if (string.equals(ip)) {
				return false;
			}
		}
		return this.clients.add(ip);
	}
	public boolean remove(String ip) {
		for (String string : clients) {
			if (string.equals(ip)) {
				return this.clients.remove(string);
			}
		}
		return false;
	}
	public boolean contains(String ip) {
		for (String string : clients) {
			if (string.equals(ip)) {
				return true;
			}
		}
		return false;
	}
	public PoolList() {}
}
