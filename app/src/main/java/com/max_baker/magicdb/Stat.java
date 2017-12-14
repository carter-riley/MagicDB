package com.max_baker.magicdb;

/**
 * Created by max_baker on 12/14/17.
 */

public class Stat {
    String stat1;
    String stat2;

    public Stat(String stat1, String stat2) {
        this.stat1 = stat1;
        this.stat2 = stat2;
    }

    public String getStat1() {
        return stat1;
    }

    public void setStat1(String stat1) {
        this.stat1 = stat1;
    }

    public String getStat2() {
        return stat2;
    }

    public void setStat2(String stat2) {
        this.stat2 = stat2;
    }
}
