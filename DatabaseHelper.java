package fingertest.android.fingertest;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SONY on 19-10-2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    //Button btnViewAll=(Button)findViewById(R.id.button_viewAll);

    DatabaseHelper(Context context) {
        super(context, "VIT",null,1);
        //SQLiteDatabase db=this.getDatabaseName();
    }
    //SQLiteDatabase sampleDB=null;
    //Cursor c;
    public Cursor getAllData()
    {
        //Next n=new Next();

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from password",null);
        return res;
    }
    public String getPass(String app) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res1;
        String pas="";
        res1 = db.rawQuery("select * from password where app='" + app + "'", null);
        if (res1!=null){
            if (res1.moveToFirst()){
        pas=res1.getString(1);}
        }
        return pas;
    }

    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        

    }
}
