package domain;

import java.util.HashMap;
import java.util.Map;

public class Graph {
	
	// Airports by iata (String)
	private Map<String, Airport> airports;
	
	// Airlines by iata (String)	
	private Map<String, Airline> airlines;
	
	public Graph() {
		this.airports = new HashMap<String, Airport>();
		this.airlines = new HashMap<String, Airline>();
	}
	
	public void calculerItineraireMinimisantDistance(String src, String dest, String outputFile){
		// BFS
		this.bfsSearch(src, dest);
	}
	
	public void calculerItineraireMinimisantNombreVol(String src, String dest, String outputFile){
		// DIJKSTRA
		this.dijkstra(src, dest);
	}
	
	public boolean bfsSearch(String src, String dest) {
		//TODO
		return false;
	}
	
	public boolean dijkstra(String src, String dest) {
		//TODO
		return false;
	}
	
	public boolean addObject(Object obj) {
		if (obj instanceof Airport) {
			if (this.airports.containsValue(obj))
				return false;
			this.airports.put(((Airport) obj).getIata(), (Airport) obj);
		}
		else if (obj instanceof Airline) {
			if (this.airlines.containsValue(obj))
				return false;
			this.airlines.put(((Airline) obj).getIata(), (Airline) obj);
		}
		else if (obj instanceof Route) {
			Airport src = ((Route) obj).getSource();
			src.addRoute((Route) obj);
		}
		
		return true;
	}
}
