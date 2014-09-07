package mg.colorfilters;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends ActionBarActivity {
	private InterstitialAd interstitial;
	private static final Handler handler = new Handler();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		// Create the interstitial.
	    interstitial = new InterstitialAd(this);
	    interstitial.setAdUnitId("ca-app-pub-2738757694043962/2467102137");


	    interstitial.setAdListener(new AdListener() {
	        @Override
	        public void onAdLoaded() {
	          
	        	displayInterstitial();

	          
	        }
	        @Override
	        public void onAdFailedToLoad(int errorCode) {
	          
	        }
	    });
		
	    // Create ad request.
	    AdRequest adRequest = new AdRequest.Builder().build();
	    // Begin loading your interstitial.
	    interstitial.loadAd(adRequest);
		
	}
	
	@Override
	@TargetApi(16)
	protected void onResume () {
		
		super.onResume();

		if (Build.VERSION.SDK_INT < 16) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
		    
		} else{
			View decorView = getWindow().getDecorView();
			// Hide the status bar.
			int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
			decorView.setSystemUiVisibility(uiOptions);
			// Remember that you should never show the action bar if the
			// status bar is hidden, so hide that too if necessary.
			ActionBar actionBar = getActionBar();
			actionBar.hide();
		}
		try{
			
			handler.postDelayed(new Runnable() {
			    @Override
			    public void run() {
			        // Do something after 3s = 3000ms
			    	Intent intent = new Intent(MainActivity.this, ColorActivity.class);
					startActivity(intent);
			    }
			}, 3000);
			}
			catch(Exception e)
			{
				
			}
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			return rootView;
		}
	}
	
	// Invoke displayInterstitial() when you are ready to display an interstitial.
		  public void displayInterstitial() {
		    if (interstitial.isLoaded()) {
		      interstitial.show();
		    }
		  }
	
	

}
