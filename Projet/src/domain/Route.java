package domain;
import static utils.Util.distance;

public class Route {
	private Airline airline;
	private Airport source;
	private Airport destination;
	
	
	public Route(Airline airline, Airport source, Airport destination) {
		this.airline = airline;
		this.source = source;
		this.destination = destination;
	}


	public Airline getAirline() {
		return this.airline;
	}


	public Airport getSource() {
		return this.source;
	}


	public Airport getDestination() {
		return this.destination;
	}
	
	public double getKms() {
		
		return distance(this.source.getLatitude(), this.source.getLongitude(), this.destination.getLatitude(), this.destination.getLongitude());
	}

	
	
	
	

}
