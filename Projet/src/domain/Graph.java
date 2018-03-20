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
		ArrayDeque<Route> chemin = new ArrayDeque<Route>();
		
		System.out.println("Source : " + src + " \t Destination: " + dest);
		if (bfsSearch(src, dest, chemin)) {
			// Affichage
			System.out.println("\nLe chemin (" + chemin.size() + ") à suivre est : ");
			for (Route route : chemin){
				System.out.println("\t" + route.toString());
			}
		}
		else System.out.println("Aucun chemin n'a été trouvé");
		
		//TODO Ecrire le chemin dans un le fichier xml 'outputFile'
		
	}
	
	public void calculerItineraireMinimisantDistance(String src, String dest, String outputFile){
		// DIJKSTRA
		this.dijkstra(src, dest);
	}
	
	public boolean bfsSearch(String src, String dest, ArrayDeque<Route> chemin) {
		ArrayDeque<Airport> queue = new ArrayDeque<Airport>();
		Map<Airport, Airport> enfants = new HashMap<Airport, Airport>();
		Airport airportSrc = this.airports.get(src);
		Airport airportDest = this.airports.get(dest);
		queue.add(airportSrc);
		enfants.put(airportSrc, null);
		
		do {
			System.out.println("First : " + queue.peekFirst().getIata());
			airportSrc = queue.removeFirst();
			
			for (Route out : airportSrc.getRoutes()) {
				if (!enfants.containsKey(out.getDestination())) {
					enfants.put(out.getDestination(), airportSrc);
					queue.addLast(out.getDestination());
				}
				
				if (out.getDestination().getIata().equals(dest) || enfants.containsKey(airportDest)) {
					Airport iataDest = airportDest;
					chemin.add(out);
					Airport airport = enfants.get(iataDest);
					Airport child = enfants.get(airport);
					
					while (!child.equals(airportSrc)) {
						System.out.println("\n" + airport.getIata());
						for (Route route : child.getRoutes()) {
							if (route.getDestination().equals(airport)) {
								out = route;
								break;
							}
						}
						chemin.addFirst(out);
						iataDest = airport;
						airport = enfants.get(iataDest);
						child = enfants.get(airport);
						if (child == null)
							break;
					}
					return true;
				}
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
			return true;
		}
		else if (obj instanceof Airline) {
			if (this.airlines.containsValue(obj))
				return false;
			this.airlines.put(((Airline) obj).getIata(), (Airline) obj);
			return true;
		}
		else if (obj instanceof Route) {
			Airport src = ((Route) obj).getSource();
			Airport airport = airports.get(src.getIata());
			if (airport.getIata().equals(src.getIata()))
				return airport.addRoute((Route) obj);
		}
		
		return false;
	}
	
	public Airline getAirline(String iata) {
		return this.airlines.get(iata);
	}
	
	public Airport getAirport(String iata) {
		return this.airports.get(iata);
	}
	
}
