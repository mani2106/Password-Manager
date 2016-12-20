package fingertest.android.fingertest;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Update extends Activity {
    Spinner spin;
    EditText a,b,d;
    SQLiteDatabase sampleDB=null;
    Cursor c,c1,c2;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_update2);
        Spinner appList=(Spinner)findViewById(R.id.applister);
        PackageManager packageManager=getApplicationContext().getPackageManager();
        List<ApplicationInfo> applicationInfoList=getPackageManager().getInstalledApplications(0);
        ArrayList<String> stringArrayList=new ArrayList<String>();
        for (int i=0;i<applicationInfoList.size();i++)
        {
            if (isSystemPackage(applicationInfoList.get(i)))
                stringArrayList.add(applicationInfoList.get(i).loadLabel(packageManager).toString());
        }
        Collections.sort(stringArrayList);
        ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,stringArrayList);
        appList.setAdapter(stringArrayAdapter);
        spin = (Spinner) findViewById(R.id.applister);
        a=(EditText)findViewById(R.id.email);
        b=(EditText)findViewById(R.id.editText2);
        d=(EditText)findViewById(R.id.editText1);
    }
    private boolean isSystemPackage(ApplicationInfo applicationInfo)
    {
        return ((applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.update, menu);
        return true;
    }
    private void toast(String string)
    {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public String check(String p, String o, String m)
    {
        String i="";
        try
        {
            sampleDB =  this.openOrCreateDatabase("VIT", MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS password (emailID VARCHAR,pwd VARCHAR,app VARCHAR);");
            c= sampleDB.rawQuery("SELECT * FROM password where app = '" + p +"' AND emailID='"+o+"'AND pwd='"+m+"'",null);
            if (c.moveToFirst())
            {
                int pl = c.getColumnIndex("emailID");
                i = c.getString(pl);
            }
        }
        catch(Exception e)
        {
            toast(e.getMessage());

        }
        return i;
    }
    public void back(View v)
    {
        Intent i=new Intent(this,Select.class);
        startActivity(i);
    }
    public void update(View v)
    {
        /*Context con=v.getContext();
        DatabaseHelper db=new DatabaseHelper(con);*/
         try{   String x,y,s,z,t;
            sampleDB =  this.openOrCreateDatabase("DSA", MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS password (emailID VARCHAR,pwd VARCHAR,app VARCHAR);");
            s=String.valueOf(spin.getSelectedItem());
            x=a.getText().toString();
            y=b.getText().toString();
            z=d.getText().toString();
            t=check(s,x,y);
            if(t.equals("") || t.equals("0"))
            {
                toast("Details Error");
                toast("Not Updated");
            }
            else
            {
                sampleDB.execSQL("UPDATE password SET pwd = '" + z + "' WHERE emailID = '" + x + "'");
                toast("Updated successfully");
            }
        }
        catch(Exception e)
        {
            toast(e.getMessage());
        }
       /* int update = db.update(v, x, y, s, z, t);
        if (update==1)
        {
            toast("Password Updated");
            Intent i=new Intent(Update.this,Select.class);
            startActivity(i);
        }
        else toast("Error password not updated");*/

    }


}
