package fingertest.android.fingertest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Show extends Activity{

DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show2);
        myDb=new DatabaseHelper(this.getApplicationContext());
    }
    //Button btnViewAll=(Button)findViewById(R.id.button_viewAll);
    public void showMessage(String title,String Message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show, menu);
        return true;
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
    private boolean isSystemPackage(ApplicationInfo applicationInfo)
    {
        return ((applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==0);
    }
    private void toast(String string)
    {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
    }
//
    ArrayList<String> stringArrayList= new ArrayList<>();
    public void show(View v) {
        //PackageManager packageManager=getApplicationContext().getPackageManager();
        Spinner spinner=(Spinner) findViewById(R.id.spinner);
        /*List<ApplicationInfo> applicationInfoList=getPackageManager().getInstalledApplications(0);

        for (int i=0;i<applicationInfoList.size();i++)
        {
            if (isSystemPackage(applicationInfoList.get(i)))
                stringArrayList.add(applicationInfoList.get(i).loadLabel(packageManager).toString());
        }*/
        Button show=(Button) findViewById(R.id.button3);
        show.setEnabled(false);
                        ArrayAdapter<String> stringArrayAdapter= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, stringArrayList);

                        Cursor res=myDb.getAllData();
                        if(res.getCount()==0)
                        {
                            //show message
                            showMessage("Error","Nothing found");
                            return;
                        }
                       // StringBuffer buffer = new StringBuffer();

                        while(res.moveToNext())
                        {
                            stringArrayList.add(res.getString(2));
                            /*buffer.append("username: "+res.getString(0)+"\n");
                            buffer.append("password: "+res.getString(1)+"\n");
                            buffer.append("App: "+res.getString(2)+"\n\n");*/
                        }
                        Collections.sort(stringArrayList);
                        spinner.setAdapter(stringArrayAdapter);
                        //ShowAllData
                        //showMessage("Data",buffer.toString());


                    }
    public void copy(View v)
    {
        Spinner spinner=(Spinner) findViewById(R.id.spinner);
        String s=String.valueOf(spinner.getSelectedItem());
        String pass=myDb.getPass(s);
        //String col=pass.getString(1);
        android.content.ClipboardManager Clipboard=(android.content.ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip =android.content.ClipData.newPlainText(s,pass);
        Clipboard.setPrimaryClip(clip);
        toast("Password Copied to clipboard");
    }
}
