package domain;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {
	private boolean bAirport = false;
	private boolean bAirline = false;
	private boolean bRoute = false;

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

		if (qName.equalsIgnoreCase("airport")) {
			String iata = attributes.getValue(uri, "iata");
			String name = attributes.getValue(uri, "name");
			String city = attributes.getValue(uri, "city");
			String country = attributes.getValue(uri, "country");
			
			Airport airport = new Airport(iata, name, city, country, latitude, longitude);
			// TODO ajouter flight dans le graphe
		}
		if (qName.equalsIgnoreCase("airline")) {
			// TODO ajouter flight dans le graphe
		}
		if (qName.equalsIgnoreCase("route")) {
			// TODO ajouter flight dans le graphe
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		super.endElement(uri, localName, qName);
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		super.characters(ch, start, length);
	}

}
