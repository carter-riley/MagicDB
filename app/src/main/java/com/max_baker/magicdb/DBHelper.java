package com.max_baker.magicdb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;

/**
 * Created by max_baker on 12/13/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    public ArrayList<Card> cardsList;

    static final String DATABASE_NAME = "databaseMagic";

    static final int DATABASE_VERSION = 1;





    // Tables

    static final String TABLE_STRING = "tableCard";

    static final String DECK_STRING = "tableDeck";

    static final String OWNER_STRING = "tableOwner";







    //Attributes



    // Tag for logcat

    static final String TAG = "MagicDatabaseHelper";



    // Constructor

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }



    @Override

    public void onCreate(SQLiteDatabase sqLiteDatabase) {




        // Create owner table

        String createUserTable =   "CREATE TABLE user (" +

                "               name TEXT PRIMARY KEY" +

                ")";

        sqLiteDatabase.execSQL(createUserTable);



        // Create deck table

        String createDeckTable =    "CREATE TABLE deck (" +

                "        name TEXT PRIMARY KEY," +

                "        owner TEXT" +

                "        , FOREIGN KEY (owner) REFERENCES user (name)" +

                ")";

        sqLiteDatabase.execSQL(createDeckTable);



        // create deck table

        String createNumCardTable =    "CREATE TABLE numCard (" +

                "        name TEXT PRIMARY KEY," +

                "        deck TEXT," +

                "        quantity INTEGER," +

                "        FOREIGN KEY (deck) REFERENCES deck (name)," +

                "        FOREIGN KEY (name) REFERENCES card (name)" +



                ")";

        sqLiteDatabase.execSQL(createNumCardTable);



        // create deck table

        String createCardTable =    "CREATE TABLE card (" +

                "         name TEXT PRIMARY KEY," +

                "        _set TEXT," +

                "        white INTEGER," +

                "        blue INTEGER," +

                "        black INTEGER," +

                "        red INTEGER," +

                "        green INTEGER," +

                "        cost INTEGER," +

                "        type TEXT," +

                "        power TEXT," +

                "        toughness TEXT" +

                ")";

        sqLiteDatabase.execSQL(createCardTable);

    }



    @Override

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {



    }

    public void countCards() {
        String sqlUser = "SELECT COUNT(*) FROM card";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlUser, null);

        Log.d("Number of cards",cursor.getString(0));

    }

    public void getCards() {
        String sqlUser = "SELECT * FROM card";
        SQLiteDatabase db = this.getReadableDatabase();
        // call rawQuery(), which returns a Cursor reference
        Cursor cursor = db.rawQuery(sqlUser, null);

        cursor.moveToFirst();
        Log.d("db",cursor.getString(0));

    }

    public void insertCard(Card card) {
        if(card.getName().contains("'")) {return;}

        String sqlInsertCard =  "INSERT INTO card ";

        sqlInsertCard +=        " VALUES(";

        sqlInsertCard +=        "'" + card.getName() + "', ";

        sqlInsertCard +=        "'" + card.getSetName() + "', ";

        if(isIn(card.getColorIdentity(), "W")) {

            sqlInsertCard += "'" + "1" + "', ";

        } else {

            sqlInsertCard += "'" + "0" + "', ";

        }

        if(isIn(card.getColorIdentity(), "U")) {

            sqlInsertCard += "'" + "1" + "', ";

        } else {

            sqlInsertCard += "'" + "0" + "', ";

        }

        if(isIn(card.getColorIdentity(), "B")) {

            sqlInsertCard += "'" + "1" + "', ";

        } else {

            sqlInsertCard += "'" + "0" + "', ";

        }

        if(isIn(card.getColorIdentity(), "R")) {

            sqlInsertCard += "'" + "1" + "', ";

        } else {

            sqlInsertCard += "'" + "0" + "', ";

        }

        if(isIn(card.getColorIdentity(), "G")) {

            sqlInsertCard += "'" + "1" + "', ";

        } else {

            sqlInsertCard += "'" + "0" + "', ";

        }

        sqlInsertCard +=        "'" + card.getCmc() + "', ";

        sqlInsertCard +=        "'" + card.getType() + "', ";

        if(card.getPower() == null || card.getToughness() == null) {

            sqlInsertCard +=        "'" + "NULL" + "', ";

            sqlInsertCard +=        "'" + "NULL" + "')";

        } else {

            sqlInsertCard += "'" + card.getPower() + "', ";

            sqlInsertCard += "'" + card.getToughness() + "')";

        }

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(sqlInsertCard);
        } catch(Exception e) {

        }

    }

    public void insertAllCards() {
        // SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DELETE FROM card");

        int counter = 0;
        for (Card card : cardsList) {
            if(card.getName().contains("'")) {continue;}

            String sqlInsertCard =  "INSERT INTO card ";

            sqlInsertCard +=        " VALUES(";

            sqlInsertCard +=        "'" + card.getName() + "', ";

            sqlInsertCard +=        "'" + card.getSetName() + "', ";

            if(isIn(card.getColorIdentity(), "W")) {

                sqlInsertCard += "'" + "1" + "', ";

            } else {

                sqlInsertCard += "'" + "0" + "', ";

            }

            if(isIn(card.getColorIdentity(), "U")) {

                sqlInsertCard += "'" + "1" + "', ";

            } else {

                sqlInsertCard += "'" + "0" + "', ";

            }

            if(isIn(card.getColorIdentity(), "B")) {

                sqlInsertCard += "'" + "1" + "', ";

            } else {

                sqlInsertCard += "'" + "0" + "', ";

            }

            if(isIn(card.getColorIdentity(), "R")) {

                sqlInsertCard += "'" + "1" + "', ";

            } else {

                sqlInsertCard += "'" + "0" + "', ";

            }

            if(isIn(card.getColorIdentity(), "G")) {

                sqlInsertCard += "'" + "1" + "', ";

            } else {

                sqlInsertCard += "'" + "0" + "', ";

            }

            sqlInsertCard +=        "'" + card.getCmc() + "', ";

            sqlInsertCard +=        "'" + card.getType() + "', ";

            if(card.getPower() == null || card.getToughness() == null) {

                sqlInsertCard +=        "'" + "NULL" + "', ";

                sqlInsertCard +=        "'" + "NULL" + "')";

            } else {

                sqlInsertCard += "'" + card.getPower() + "', ";

                sqlInsertCard += "'" + card.getToughness() + "')";

            }

            SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL(sqlInsertCard);

        }



    }



    public boolean isIn(String[] color, String w) {
        if(color == null) {return false;}
        for(String c : color) {

            if (c.equals(w)) {

                return true;

            }

        }

        return false;

    }



    public Cursor getUserCursor() {



        String sqlUser = "SELECT * FROM user";



        SQLiteDatabase db = this.getReadableDatabase();

        // call rawQuery(), which returns a Cursor reference

        Cursor cursor = db.rawQuery(sqlUser, null);



        return cursor;

    }



    public Cursor getDeckCursor(String user) {

        String sqlDeck = "SELECT * FROM deck " +

                "WHERE owner = '" + user+"'";



        SQLiteDatabase db = this.getReadableDatabase();

        // call rawQuery(), which returns a Cursor reference

        Cursor cursor = db.rawQuery(sqlDeck, null);



        return cursor;

    }



    public Cursor GetComplexQuery(String agr, String attr, String attr2, String symbol, String having) {

        String sqlQuery = "SELECT " + attr2 + ", " + agr + "(" + attr + ")" +

                " FROM card" +

                " GROUP BY " + attr2

             +  " HAVING " + agr + "(" + attr + ") " + symbol + " " + having+""
            ;


        Log.d("Complex", sqlQuery);

        SQLiteDatabase db = this.getReadableDatabase();

        // call rawQuery(), which returns a Cursor reference

        Cursor cursor = db.rawQuery(sqlQuery, null);



        return cursor;

    }



    public Cursor GetCardCursor(String user, String deck) {

        String sqlQuery = "SELECT * " +

                "FROM numCard " +

                "WHERE deck = '" + deck+"'";

        Log.d("card Cursor", sqlQuery);

        SQLiteDatabase db = this.getReadableDatabase();

        // call rawQuery(), which returns a Cursor reference

        Cursor cursor = db.rawQuery(sqlQuery, null);



        return cursor;

    }



    public Cursor GetCardResults(String name, boolean white, boolean blue, boolean black, boolean red, boolean green, String minCost, String maxCost) {

        String sqlQuery =   "SELECT name " +

                "FROM card" +

                " WHERE 1 = 1";

        if(white) {

            sqlQuery += " AND white = 1";

        } else if(blue) {

            sqlQuery += " AND blue = 1";

        } else if(red) {

            sqlQuery += " AND red = 1";

        } else if(black) {

            sqlQuery += " AND black = 1";

        } else if(green) {

            sqlQuery += " AND green = 1";

        }
        if(!name.equals("")){
            sqlQuery+=" AND name = '"+name+"'";}

        sqlQuery += " AND cost > " + minCost + " AND cost < " + maxCost;



        SQLiteDatabase db = this.getReadableDatabase();

        // call rawQuery(), which returns a Cursor reference

        Log.d("search", sqlQuery);
        Cursor cursor = db.rawQuery(sqlQuery, null);



        return cursor;

    }



    public void AddUser(String user) {

        String sqlUpdate = "INSERT INTO user " +

                "VALUES('" +

                user +

                "')";

        Log.d(TAG, "updateContactById: " + sqlUpdate);

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(sqlUpdate);

        db.close();

    }
    public void deleteAllUsers() {

        String sqlUpdate = "DELETE * FROM user";

        Log.d(TAG, "updateContactById: " + sqlUpdate);

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(sqlUpdate);

        db.close();

    }

    public void deleteAllDecks() {

        String sqlUpdate = "DELETE * FROM deck";

        Log.d(TAG, "updateContactById: " + sqlUpdate);

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(sqlUpdate);

        db.close();

    }



    public void AddDeck(String user, String DeckName) {

        String sqlUpdate = "INSERT INTO deck " +
                "VALUES('" + DeckName + "','"
                + user +
                "')";

        Log.d(TAG, "create deck: " + sqlUpdate);

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlUpdate);

        db.close();

    }



    public void AddCard(String cardName, String deck, String quantity) {

        String sqlUpdate = "INSERT INTO numCard" +

                " VALUES('" + cardName + "','"

                + deck + "', "

                + quantity +

                ")";

        Log.d(TAG, "updateContactById: " + sqlUpdate);

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(sqlUpdate);

        db.close();

    }


}
