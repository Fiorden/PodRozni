package com.fjord.podrozni;

/**
 * Created by fjord on 02.06.2017.
 */

public class Reminder {
    int id;
    long time;

    public Reminder(int id, long time) {
        setId(id);
        setTime(time);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
