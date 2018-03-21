package entryPoint;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import domain.DomParser;
import domain.Graph;

public class MainDom {

	public static void main(String[] args) {
		try {
			File inputFile = new File("flight.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
	        
	        DomParser domParser = new DomParser(doc);
	        Graph g = domParser.getGraph();
	        g.calculerItineraireMinimisantNombreVol("BRU", "PPT", "output3.xml");
			g.calculerItineraireMinimisantDistance("BRU", "PPT", "output4.xml");
		} catch (Exception e) {
			e.printStackTrace();
	    }
	}
}
