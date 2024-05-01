import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class Station extends Entity {
    static ArrayList<Station> list = new ArrayList<Station>();

    boolean occupied;
    Lock lock;
    ArrayList<Passenger> passengers = new ArrayList<Passenger>();

    private Station(String name) { super(name); lock = new ReentrantLock(); }

    public static Station make(String name) {
        for (Station s : list)
            if (s.toString().equals(name))
                return s;
        Station newStation = new Station(name);
        list.add(newStation);
        return newStation;
    }
    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}
