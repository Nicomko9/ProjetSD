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
			Graph g = userhandler.getGraph();
			g.calculerItineraireMinimisantNombreVol("LAX", "PPT", "output2.xml");
			// g.calculerItineraireMinimisantDistance("BRU", "PPT", "output.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
