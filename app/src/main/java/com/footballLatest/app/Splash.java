package com.footballLatest.app;


import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

public class Splash extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

        if(!isNetworkAvailable(Splash.this)) {
            Toast.makeText(Splash.this, "No Internet connection", Toast.LENGTH_LONG).show();
            finish(); //Calling this method to close this activity when internet is not available.
        }

		Thread t=new Thread()
		{
			public void run()
			{
				
				try {

					sleep(5000);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					Intent start=new Intent("com.footballLatest.app.MainActivity");
					startActivity(start);
					
				}
			}
			
		
		};
		t.start();
		
		
		
	}

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	

}
