package app.sirius.spacecup.siriusapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by nando on 15/10/2015.
 */
public class DAO {

    private static DatabaseHelper helper;
    private static SQLiteDatabase db;
    private boolean opened = false;

    public DAO(Context context){
        helper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDB(){

        if(db == null){
            db = helper.getWritableDatabase();
            opened = true;
        }
        return db;
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    public void close(){
        if(opened)
            helper.close();
    }
}
