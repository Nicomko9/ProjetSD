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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airline == null) ? 0 : airline.hashCode());
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		if (airline == null) {
			if (other.airline != null)
				return false;
		} else if (!airline.equals(other.airline))
			return false;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Route with " + airline.getName() + ", from " + source.getName() + " to " + destination.getName() + ".";
	}
	
}
