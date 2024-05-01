import java.util.*;

public class MoveEvent implements Event {
    public final Train t; public final Station s1, s2;
    public MoveEvent(Train t, Station s1, Station s2) {
        this.t = t; this.s1 = s1; this.s2 = s2;
    }
    public boolean equals(Object o) {
        if (o instanceof MoveEvent e) {
          return t.equals(e.t) && s1.equals(e.s1) && s2.equals(e.s2);
        }
        return false;
    }
    public int hashCode() {
        return Objects.hash(t, s1, s2);
    }
    public String toString() {
        return "Train " + t + " moves from " + s1 + " to " + s2;
    }
    public List<String> toStringList() {
        return List.of(t.toString(), s1.toString(), s2.toString());
    }

    public void replayAndCheck(MBTA mbta) {
        List<Station> line = t.getLine();
        int pos1 = -1;
        int pos2;

        if (s1 != t.currStation())
            throw new RuntimeException(s1 + " is not correct start " + t.currStation());

        for (int i = 0; i < line.size(); i++)
            if (s1 == line.get(i))
                pos1 = i;

        if (t.goingForward())
            pos2 = pos1 + 1;
        else
            pos2 = pos1 - 1;
        if (pos2 == 0 || pos2 == line.size() - 1) t.reverse();

        if (s2.occupied) 
            throw new RuntimeException(s2 + " is occupied");
        t.setCurrStation(s2);
        s1.occupied = true;
        s2.occupied = true;
    }
}
