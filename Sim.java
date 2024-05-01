import java.io.*;
import java.util.*;

public class Sim {

    public static void run_sim(MBTA mbta, Log log) {
        ArrayList<PassengerThread> pthreads = new ArrayList<PassengerThread>();
        ArrayList<TrainThread> tthreads = new ArrayList<TrainThread>();
        HashMap<Passenger, PassengerThread> pmap = new HashMap<Passenger, PassengerThread>();
        ArrayList<Passenger> passengers = new ArrayList<Passenger>();

        for (Passenger p : mbta.passengers) {
            passengers.add(p);
        }

        for (Train t : mbta.trains) {
            TrainThread tt = new TrainThread(t, log, passengers);
            tt.start();
            tthreads.add(tt);
        }

        while (true) {
            int numalive = 0;
            for (Passenger p : passengers) {
                if (!p.finished)
                    numalive++;
            }
            if (numalive == 0) {
                for (TrainThread tt : tthreads)
                    tt.interrupt();
                return;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
          System.out.println("usage: ./sim <config file>");
          System.exit(1);
        }

        MBTA mbta = new MBTA();
        mbta.loadConfig(args[0]);

        Log log = new Log();

        run_sim(mbta, log);

        String s = new LogJson(log).toJson();
        PrintWriter out = new PrintWriter("log.json");
        out.print(s);
        out.close();

        mbta.reset();
        mbta.loadConfig(args[0]);
        Verify.verify(mbta, log);
    }
}
