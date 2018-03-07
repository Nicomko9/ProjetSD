package entryPoint;
import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import domain.Graph;
import domain.SAXHandler;

public class Main {
	public static void main(String[] args) {
		try {
			File inputFile = new File("flight.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			SAXHandler userhandler = new SAXHandler();
			saxParser.parse(inputFile, userhandler);
			// Graph g = userhandler.getGraph();
			Graph g = new Graph();
			g.calculerItineraireMinimisantDistance("BRU", "PPT", "output.xml");
			g.calculerItineraireMinimisantNombreVol("BRU", "PPT", "output2.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
