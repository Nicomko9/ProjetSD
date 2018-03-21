package domain;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
		writeInFile(src, dest, chemin, outputFile);
	}
	
	public void calculerItineraireMinimisantDistance(String src, String dest, String outputFile){
		
		LinkedList<Airport> chemin = this.dijkstra(src, dest);
		System.out.println("Source : " + src + " \t Destination: " + dest);
		if (!chemin.isEmpty()){
			System.out.println("\nLe chemin (" + chemin.size() + ") à suivre est : ");
			for (int i = 0; i < chemin.size(); i++){
				Airport airport = chemin.get(i);
				System.out.println("Route " + (i+1) +", " + airport.getName());
			}
			Double distance = chemin.getLast().getTraveledDistance();
			System.out.println("Distance total: "+ distance);
			
			//reset all traveleddistance
			for(Airport airport:airports.values()){
				airport.setTraveledDistance(Double.MAX_VALUE);
			}
		}else{
			System.out.println("Aucun chemin n'a été trouvé");
		}
	}
	
	public boolean bfsSearch(String src, String dest, ArrayDeque<Route> chemin) {
		ArrayDeque<Airport> queue = new ArrayDeque<Airport>();
		Map<Airport, Airport> enfants = new HashMap<Airport, Airport>();
		Airport airportSrc = this.airports.get(src);
		Airport airportDest = this.airports.get(dest);
		queue.add(airportSrc);
		enfants.put(airportSrc, null);
		
		do {
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
	
	private void writeInFile(String src, String dest, ArrayDeque<Route> chemin, String outputFile) {
		try {
			Airport airportSrc = this.airports.get(src);
			Airport airportDest = this.airports.get(dest);
			
			double distanceTotale = 0;
			for (Route route : chemin) {
				distanceTotale += route.getKms();
			}
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			
			// Element root trajet
			Element rootElement = doc.createElement("trajet");
			doc.appendChild(rootElement);
			
			Attr depart = doc.createAttribute("depart");
			depart.setValue(airportSrc.getName());
			rootElement.setAttributeNode(depart);
			
			Attr destination = doc.createAttribute("destination");
			destination.setValue(airportDest.getName());
			rootElement.setAttributeNode(destination);
			
			Attr distance = doc.createAttribute("distance");
			distance.setValue(distanceTotale + "");
			rootElement.setAttributeNode(distance); 
			
			int i=0;
			for (Route route : chemin) {
				// Element vol (Route)
				Element vol = doc.createElement("vol");
				rootElement.appendChild(vol);
				
				Attr compagnie = doc.createAttribute("compagnie");
				compagnie.setValue(route.getAirline().getName());
				vol.setAttributeNode(compagnie);
				
				Attr nombreKm = doc.createAttribute("nombreKm");
				nombreKm.setValue(route.getKms() + "");
				vol.setAttributeNode(nombreKm);
				
				Attr numero = doc.createAttribute("numero");
				numero.setValue(++i +"");
				vol.setAttributeNode(numero);
				
				// Aéroport source
				Element nodeSource = doc.createElement("source");
				vol.appendChild(nodeSource);
				
				Attr iataSrc = doc.createAttribute("iataCode");
				iataSrc.setValue(route.getSource().getIata());
				nodeSource.setAttributeNode(iataSrc);
				
				Attr paysSrc = doc.createAttribute("pays");
				paysSrc.setValue(route.getSource().getCountry());
				nodeSource.setAttributeNode(paysSrc);
				
				Attr villeSrc = doc.createAttribute("ville");
				villeSrc.setValue(route.getSource().getCity());
				nodeSource.setAttributeNode(villeSrc);
				
				nodeSource.appendChild(doc.createTextNode(route.getSource().getName()));
				
				// Aéroport destination
				Element nodeDestination = doc.createElement("destination");
				vol.appendChild(nodeDestination);
				
				Attr iataDest = doc.createAttribute("iataCode");
				iataDest.setValue(route.getSource().getIata());
				nodeDestination.setAttributeNode(iataDest);
				
				Attr paysDest = doc.createAttribute("pays");
				paysDest.setValue(route.getSource().getCountry());
				nodeDestination.setAttributeNode(paysDest);
				
				Attr villeDest = doc.createAttribute("ville");
				villeDest.setValue(route.getSource().getCity());
				nodeDestination.setAttributeNode(villeDest);
				
				nodeDestination.appendChild(doc.createTextNode(route.getDestination().getName()));
			}
			
			// Enregistrer dans le fichier
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(outputFile));
			transformer.transform(domSource, result);
			
		} catch (ParserConfigurationException pe) {
			pe.printStackTrace();
		} catch (TransformerConfigurationException te) {
			te.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
	}
	
	public LinkedList<Airport> dijkstra(String src, String dest) {
		Airport source = airports.get(src);
		Airport destination = airports.get(dest);		
		LinkedList<Airport> path = new LinkedList<Airport>();
		
		Set<Airport> unsettledNodes = new HashSet<Airport>();
		Set<Airport> settledNodes = new HashSet<Airport>();
		
		Map<Airport, Airport> predecessors = new HashMap<Airport,Airport>();
		
		unsettledNodes.add(source);
		source.setTraveledDistance(0.0);
		
		while (unsettledNodes.size() != 0){
			Airport currentNode = getClosestAirport(unsettledNodes);
			unsettledNodes.remove(currentNode);
			settledNodes.add(currentNode);
			for(Airport a:getAdjacentAirports(currentNode, settledNodes)){
				double distance = currentNode.getTraveledDistance() + distanceBetweenAirports(currentNode, a);
				if(a.getTraveledDistance() > distance){
					a.setTraveledDistance(distance);
					predecessors.put(a, currentNode);
					unsettledNodes.add(a);
				}
			}
		}
		
		if(predecessors.get(destination) == null){
			return null;
		}
		Airport step = destination;
		path.add(step);
		
		while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
		
		Collections.reverse(path);
		return path;
	}
	
	public double distanceBetweenAirports(Airport source, Airport destination){
		Set<Route> routes = source.getRoutes();
		for (Route r:routes){
			if(r.getDestination().equals(destination)){
				return r.getKms();
			}
		}
		return 0.0;
	}
	
	public Airport getClosestAirport(Set<Airport> airports){
		Airport airport = null;
		double distance = Double.MAX_VALUE;	
		for (Airport a : airports){
			if (a.getTraveledDistance() < distance){
				distance = a.getTraveledDistance();
				airport = a;
			}
		}
		return airport;
	}
	
	public Set<Airport> getAdjacentAirports(Airport source, Set<Airport> settled){
		Set<Airport> airports = new HashSet<Airport>();
		for(Route route: source.getRoutes()){
			if(!settled.contains(route.getDestination())){
				airports.add(route.getDestination());	
			}
					
		}
		return airports;
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
