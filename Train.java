import java.util.ArrayList;
import java.util.List;

public class Train extends Entity {
    static ArrayList<Train> list = new ArrayList<Train>();

    ArrayList<Station> line = new ArrayList<Station>();
    Station currStation;
    ArrayList<Passenger> passengers = new ArrayList<Passenger>();
    boolean goingForward;

    private Train(String name) { super(name); }

    public static Train make(String name) {
        for (Train t : list)
            if (t.toString().equals(name))
                return t;
        Train newTrain = new Train(name);
        newTrain.goingForward = true;
        list.add(newTrain);
        return newTrain;
    }

    public void addLine(List<String> stations) {
        for (String s : stations) {
            line.add(Station.make(s));
            Station.make(s).occupied = false;
        }

    }

    public Station getNextStation() {
        int pos1 = -1;
        for (int i = 0; i < line.size(); i++) {
            Station s = line.get(i);
            if (s == currStation) {
                pos1 = i;
            }
        }

        int pos2 = -1;
        if (goingForward) pos2 = pos1 + 1;
        else pos2 = pos1 - 1;

        if (pos2 == 0 || pos2 == line.size() - 1) reverse();

        return line.get(pos2);
    }

    public void reset() {
        line.clear();
        passengers.clear();
    }

    public static void restart() {
        list.clear();
    }

    public ArrayList<Station> getLine() {
        return line;
    }

    public Station currStation() {
        return currStation;
    }

    public void setCurrStation(Station s) {
        currStation = s;
    }

    public void board(Passenger p) {
        passengers.add(p);
    }

    public void deboard(Passenger p) {
        passengers.remove(p);
    }

    public ArrayList<Passenger> passengers() {
        return passengers;
    }

    public void reverse() {
        goingForward = !goingForward;
    }

    public boolean goingForward() {
        return goingForward;
    }
}
