

public class PassengerThread extends Thread {
    Passenger p;

    public PassengerThread(Passenger p) {
        this.p = p;
    }

    public void run() {
        System.out.println(p + " started");
    }
}
