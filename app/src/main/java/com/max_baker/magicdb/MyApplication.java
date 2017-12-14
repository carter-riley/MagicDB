package com.max_baker.magicdb;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by max_baker on 12/13/17.
 */

public class MyApplication extends Application {
    DBHelper myDb = new DBHelper(this);
}
