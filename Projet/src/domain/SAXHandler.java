package domain;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {
	private boolean bLatitude = false;
	private boolean bLongitude = false;
	private boolean bAirline = false;
	
	private Airport airport;
	private Airline airline;
	private Graph graph = new Graph();

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {

		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if(qName.equalsIgnoreCase("longitude")) {
			bLongitude = true;
		}
		if(qName.equalsIgnoreCase("latitude")) {
			bLatitude= true;
		}
		if (qName.equalsIgnoreCase("airport")) {
			String iata = attributes.getValue(uri, "iata");
			String name = attributes.getValue(uri, "name");
			String city = attributes.getValue(uri, "city");
			String country = attributes.getValue(uri, "country");
			airport = new Airport(iata, name, city, country);
			
			
		}
		if (qName.equalsIgnoreCase("airline")) {
			String iata = attributes.getValue(uri, "iata");
			String country = attributes.getValue(uri, "country");
			airline = new Airline(iata, country);
			
		}
		if (qName.equalsIgnoreCase("route")) {
			Airline airline = this.graph.getAirline(attributes.getValue(uri, "airline"));
			Airport source = this.graph.getAirport(attributes.getValue(uri, "source"));
			Airport destination = this.graph.getAirport(attributes.getValue(uri, "destination"));
			
			Route route = new Route(airline, source, destination);
			graph.addObject(route);
		}

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (bLongitude) {
			double longitude = Double.parseDouble(new String(ch, start, length));
			airport.setLongitude(longitude);
			bLongitude = false;
			
		}
		if(bLatitude) {
			double latitude = Double.parseDouble(new String(ch, start, length));
			airport.setLatitude(latitude);
			bLatitude = false;
		}
		if(bAirline) {
			airline.setName(new String(ch, start, length));
			bAirline = false;
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName) {
		case "airport":
			this.graph.addObject(airport);
			break;
		case "airline":
			this.graph.addObject(airline);
			break;

		default:
			break;
		}
		
		
	}
	
	public Graph getGraph() {
		return this.graph;
	}


}
