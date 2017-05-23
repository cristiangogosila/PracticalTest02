package ro.pub.cs.systems.eim.practicaltest02;

/**
 * Created by student on 23.05.2017.
 */

public class StockInformation {
    String info;
    Long time;


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public StockInformation(Long time, String info) {
        this.time = time;
        this.info = info;

    }

    public String toString() {
        return time + " " + info;
    }
}
