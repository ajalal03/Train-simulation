import java.util.*;

public class TrainThread extends Thread {
    Train t;
    Log log;
    ArrayList<Passenger> passengers;

    public TrainThread(Train t, Log log, ArrayList<Passenger> passengers) {
        this.t = t;
        this.log = log;
        this.passengers = passengers;
    }

    public void run() {
        t.currStation.lock();
        boolean flag = true;
        while (true) {
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
            }
            Station currStation = t.currStation();
            ArrayList<Passenger> temp = new ArrayList<Passenger>();
            for (Passenger p : t.passengers) {
                if (p.nextStop == currStation) {
                    p.currStation = currStation;
                    log.passenger_deboards(p, t, currStation);
                    if (p.journey.get(p.journey.size() - 1) != currStation) { 
                        currStation.passengers.add(p);
                        p.determineNextStop();
                    }
                    else { 
                        p.finished = true;;
                    }
                }
                else {
                    temp.add(p);
                }
            }
            t.passengers = temp;

            temp = new ArrayList<Passenger>();
            for (Passenger p : currStation.passengers) {
                if (t.line.contains(p.nextStop)) {
                    t.passengers.add(p); 
                    log.passenger_boards(p, t, currStation); 
                }
                else {
                    temp.add(p);
                }
            }
            currStation.passengers = temp;

            Station next = t.getNextStation();
            next.lock(); 

            t.currStation = next;
            log.train_moves(t, currStation, next); 

            currStation.unlock();
        }
    }
}
