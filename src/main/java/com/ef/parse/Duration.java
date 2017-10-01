package com.ef.parse;

import java.util.Date;

/**
 * Created by Xervier on 9/29/2017.
 */
public class Duration {
    private Date start;
    private Date end;

    public Duration(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
