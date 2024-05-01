import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.Gson;

public class MBTA {
    ArrayList<Train> trains = new ArrayList<Train>();
    ArrayList<Passenger> passengers = new ArrayList<Passenger>();

    // Creates an initially empty simulation
    public MBTA() { }

    // Adds a new transit line with given name and stations
    public void addLine(String name, List<String> stations) {
        Train t = Train.make(name);
        t.addLine(stations);
        trains.add(t);
    }

    // Adds a new planned journey to the simulation
    public void addJourney(String name, List<String> stations) {
        Passenger p = Passenger.make(name);
        p.addJourney(stations);
        passengers.add(p);
    }

    // Return normally if initial simulation conditions are satisfied, otherwise
    // raises an exception
    public void checkStart() {
        for (Train t : trains)
            if (t.currStation != t.getLine().get(0))
                throw new RuntimeException(t + " not at correct start station");
        for (Passenger p : passengers)
            if (p.currStation != p.journey.get(0)){
                throw new RuntimeException();
            }
    }

    // Return normally if final simulation conditions are satisfied, otherwise
    // raises an exception
    public void checkEnd() {
        for (Passenger p : passengers)
            if (p.currStation != p.journey.get(p.journey.size() - 1))
                throw new RuntimeException(p + " not at ending station");
    }

    // reset to an empty simulation
    public void reset() {
        for (Passenger p : passengers) p.journey.clear(); // resets each journey
        for (Train t : trains) t.reset(); // resets each line
        passengers = new ArrayList<Passenger>(); // empties passengers
        trains =  new ArrayList<Train>();// empties trains
        Passenger.list = new ArrayList<Passenger>();
        Train.list = new ArrayList<Train>();
        Station.list = new ArrayList<Station>();
    }

    // adds simulation configuration from a file
    public void loadConfig(String filename) {
        Passenger.list = new ArrayList<Passenger>();
        Train.list = new ArrayList<Train>();
        Station.list= new ArrayList<Station>();
        Gson gson = new Gson();
		Configuration c;
		try {
			c = gson.fromJson(Files.readString(Paths.get(filename)),
			 											Configuration.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

        for (String line : c.lines.keySet()) {
            List<String> list = c.lines.get(line);
            addLine(line, list);
        }
        for (String name : c.trips.keySet()) {
            List<String> list = c.trips.get(name);
            addJourney(name, list);
        }
        for (Passenger p : passengers) { 
            p.currStation = p.journey.get(0);
            p.journey.get(0).passengers.add(p); 
            p.currStation = p.journey.get(1); 
        }
        for (Train t : trains) {
            t.currStation = t.getLine().get(0);
            t.getLine().get(0).occupied = true; 
        }
    }
}
