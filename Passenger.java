import java.util.ArrayList;
import java.util.List;

public class Passenger extends Entity {
    static ArrayList<Passenger> list = new ArrayList<Passenger>();

    ArrayList<Station> journey = new ArrayList<Station>();
    Station currStation;
    Station nextStop;
    boolean boarded;
    boolean finished = false;

    private Passenger(String name) { super(name); }

    public static Passenger make(String name) {
        for (Passenger p : list)
            if (p.toString().equals(name))
                return p;
        Passenger newPassenger = new Passenger(name);
        newPassenger.boarded = false;
        list.add(newPassenger);
        return newPassenger;
    }

    public void addJourney(List<String> stations) {
        for (String s : stations)
            journey.add(Station.make(s));
    }

    public void determineNextStop() {
        for (int i = 0; i < journey.size(); i++) {
            if (journey.get(i).equals(currStation)) {
                if (i == journey.size() - 1) { // last stop
                    finished = true;
                }
                else {
                    nextStop = journey.get(i + 1);
                }
            }
        }
    }

    public Station getLastStop() {
        return journey.get(journey.size() - 1);
    }

    public void setNextStop(Station s) {
        nextStop = s;
    }

    public void reset() {
        journey.clear();
    }

    public static void restart() {
        list.clear();
    }

    public ArrayList<Station> getJourney() {
        return journey;
    }

    public Station currStation() {
        return currStation;
    }

    public void setCurrStation(Station s) {
        currStation = s;
    }

    public void board() {
        boarded = true;
        for (int i = 0; i < journey.size(); i++) {
            if (journey.get(i) == currStation) {
                nextStop = journey.get(i + 1);
            }
        }
    }

    public void deboard() {
        boarded = false;
        currStation = nextStop;
    }
}
