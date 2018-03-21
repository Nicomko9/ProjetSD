package domain;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DomParser {

	private Graph graph = new Graph();

	private Document doc;

	public DomParser(Document doc) {
		this.doc = doc;
		fillGraph();
	}

	public Graph getGraph() {
		return graph;
	}

	public void fillGraph() {
		NodeList airports = doc.getElementsByTagName("airport");
		for (int i = 0; i < airports.getLength(); i++) {
			Node nAirport = airports.item(i);
			Element eAirport = (Element) nAirport;
			String iata = eAirport.getAttribute("iata");
			String name = eAirport.getAttribute("name");
			String city = eAirport.getAttribute("city");
			String country = eAirport.getAttribute("country");
			Airport airport = new Airport(iata, name, city, country);
			
			Node latitude = eAirport.getElementsByTagName("latitude").item(0);
			Element eLatitude = (Element) latitude;
			airport.setLatitude(Double.parseDouble(eLatitude.getTextContent()));
			
			Node longitude = eAirport.getElementsByTagName("longitude").item(0);
			Element eLongitude = (Element) longitude;
			airport.setLongitude(Double.parseDouble(eLongitude.getTextContent()));
			graph.addObject(airport);
		}

		NodeList airlines = doc.getElementsByTagName("airline");
		for (int i = 0; i < airlines.getLength(); i++) {
			Node nAirline = airlines.item(i);
			Element eAirline = (Element) nAirline;
			String iata = eAirline.getAttribute("iata");
			String country = eAirline.getAttribute("country");
			String name = eAirline.getTextContent();
			Airline airline = new Airline(iata, country);
			airline.setName(name);
			graph.addObject(airline);
		}

		NodeList routes = doc.getElementsByTagName("route");
		for (int i = 0; i < routes.getLength(); i++) {
			Node nRoute = routes.item(i);
			Element eRoute = (Element) nRoute;
			String strAirline = eRoute.getAttribute("airline");
			String strSource = eRoute.getAttribute("source");
			String strDestination = eRoute.getAttribute("destination");
			Airline airline = graph.getAirline(strAirline);
			Airport source = graph.getAirport(strSource);
			Airport destination = graph.getAirport(strDestination);
			Route route = new Route(airline, source, destination);
			graph.addObject(route);
		}
	}
}
