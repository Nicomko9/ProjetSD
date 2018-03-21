package entryPoint;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;

import domain.DomParser;
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
			g.calculerItineraireMinimisantNombreVol("BRU", "PPT", "output2.xml");
			g.calculerItineraireMinimisantDistance("BRU", "PPT", "output.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
