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

public class Next extends Activity {
    Spinner spin;
    EditText a,b;
    SQLiteDatabase sampleDB=null;
    Cursor c;
    //DatabaseHelper db;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next2);
        Spinner appList=(Spinner)findViewById(R.id.applister);
        PackageManager packageManager=getApplicationContext().getPackageManager();
        List<ApplicationInfo> applicationInfoList=getPackageManager().getInstalledApplications(0);
        ArrayList<String> stringArrayList= new ArrayList<>();
        for (int i=0;i<applicationInfoList.size();i++)
        {
            if (isSystemPackage(applicationInfoList.get(i)))
                stringArrayList.add(applicationInfoList.get(i).loadLabel(packageManager).toString());
        }
        Collections.sort(stringArrayList);
        ArrayAdapter<String> stringArrayAdapter= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, stringArrayList);
        appList.setAdapter(stringArrayAdapter);
        spin = (Spinner) findViewById(R.id.applister);
        a=(EditText)findViewById(R.id.email);
        b=(EditText)findViewById(R.id.editText2);
    }
    private void toast(String string)
    {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.next, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
    public String verify(String p)
    {
        String i="";
        c= sampleDB.rawQuery("SELECT * FROM password where app = '" + p +"'",null);
        if (c.moveToFirst())
        {
            int pl = c.getColumnIndex("emailID");
            i = c.getString(pl);
        }
        return i;
    }
    private boolean isSystemPackage(ApplicationInfo applicationInfo)
    {
        return ((applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==0);
    }
    public void add(View v)
    {
        try
        {

            sampleDB =  this.openOrCreateDatabase("VIT", MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS password (emailID VARCHAR,pwd VARCHAR,app VARCHAR);");
            String x,y,s,k;
            s=String.valueOf(spin.getSelectedItem());
            x=a.getText().toString();
            y=b.getText().toString();
            k=verify(s);
            if(k.equals("") || k.equals("0"))
            {
                sampleDB.execSQL("INSERT INTO password Values ('"+x+"','"+y+"','"+s+"');");
                toast("inserted successfully");
            }
            else
            {
                toast("Account Available for the application- "+s);
            }
        }
        catch (Exception e)
        {
            toast(e.toString());
        }

        /*int chec=db.add(v,x,y,s,k);
        if(chec==1){
            toast("Password Saved");
            Intent i=new Intent(Next.this,Select.class);
            startActivity(i);
        }
        else toast("Error please insert again");
*/
    }
    public void back(View v)
    {
        Intent i=new Intent(this,Select.class);
        startActivity(i);
    }
}

