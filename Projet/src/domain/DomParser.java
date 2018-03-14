package domain;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DomParser{

   public static void main(String[] args) {

      try {
         File xmlFile = new File("flight.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(xmlFile);
         doc.getDocumentElement().normalize();
         NodeList flights = doc.getElementsByTagName("flight");
         
         for (int i = 0; i < flights.getLength(); i++) {
            Node nFlight = flights.item(i);
            Element eFlight = (Element) nFlight;
            System.out.println("Trajet:\n\tAeroport de depart: " + eFlight.getAttribute("source") + "\nAeroport d'arrivee: " + eFlight.getAttribute("destination") + "\nDistance: " + eFlight.getAttribute("distance"));
            NodeList routes = eFlight.getElementsByTagName("route");
            System.out.println("Liste des vols pour cet itineraire:");
            System.out.println("===========================================================");
            for (int j = 0; j < routes.getLength(); j++){
            	Node nRoute = routes.item(i);
            	Element eRoute = (Element) nRoute;
            	System.out.println("\tVol n°" + eRoute.getAttribute("number") + ", Compagnie aerienne: " + eRoute.getAttribute("airline") + ", distance: " + eRoute.getAttribute("distance") + "km");
            	Element eSource = (Element) eRoute.getElementsByTagName("source");
            	Element eDestination = (Element) eRoute.getElementsByTagName("destination");
            	System.out.println("\tDepart depuis " + eSource.getTextContent() + " (" + eSource.getAttribute("city") + ", " + eSource.getAttribute("country"));
            	System.out.println("\tArrive à " + eDestination.getTextContent() + " (" + eDestination.getAttribute("city") + ", " + eDestination.getAttribute("country") +"\n");
            }         
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
