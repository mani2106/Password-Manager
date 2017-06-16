/**
 * Created by SONY on 13-10-2016.
 */
import java.sql.DriverManager;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ActivityMain extends Activity {
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    ProgressDialog progressBar;
    SQLiteDatabase sampleDB=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    private void toast(String string)
    {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
    }
    public void next(View v)
    {
        try
        {
            sampleDB =  this.openOrCreateDatabase("VIT", MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS password (emailID VARCHAR,pwd VARCHAR,app VARCHAR);");

            Intent i=new Intent(MainActivity.this,Next.class);
            startActivity(i);
        }
        catch(Exception e)
        {
            toast(e.getMessage());

        }
        progressBar = new ProgressDialog(v.getContext());
        progressBar.setCancelable(true);
        progressBar.setMessage("Loading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();

        progressBarStatus = 0;

        new Thread(new Runnable()
        {
            public void run()
            {

                while (progressBarStatus < 100)
                {
                    // performing operation
                    progressBarStatus+=10;
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    // Updating the progress bar
                    progressBarHandler.post(new Runnable()
                    {
                        public void run()
                        {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }
                // performing operation if file is downloaded,
                if (progressBarStatus >= 100)
                {
                    // sleeping for 1 second after operation completed
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    // close the progress bar dialog
                    progressBar.dismiss();
                }
                Intent i=new Intent(MainActivity.this,Select.class);
                startActivity(i);
            }
        }).start();


    }
}

