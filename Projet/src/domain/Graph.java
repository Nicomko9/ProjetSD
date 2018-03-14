package domain;

import java.util.ArrayDeque;
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
	
	public void calculerItineraireMinimisantNombreVol(String src, String dest, String outputFile){
		// BFS
		Map<String, String> enfants = new HashMap<String, String>();
		this.bfsSearch(src, dest, enfants);
	}
	
	public void calculerItineraireMinimisantDistance(String src, String dest, String outputFile){
		// DIJKSTRA
		this.dijkstra(src, dest);
	}
	
	public boolean bfsSearch(String src, String dest, Map<String, String> enfants) {
		//TODO
		ArrayDeque<String> queue = new ArrayDeque<String>();
		queue.add(src);
		enfants.put(src, null);
		
		do {
			src = queue.removeFirst();
			Airport current = airports.get(src);
			for (Route out : current.getRoutes()) {
				enfants.put(out.getSource().getIata(), src);
				
			}
			
		} while (!queue.isEmpty());
		
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
			Airport airport = airports.get(src.getIata());
			airport.addRoute((Route) obj);
		}
		
		return true;
	}
	
	public Airline getAirline(String iata) {
		return this.airlines.get(iata);
	}
	
	public Airport getAirport(String iata) {
		return this.airports.get(iata);
	}
	
	
	
	
}
