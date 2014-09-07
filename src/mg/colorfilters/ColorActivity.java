package mg.colorfilters;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class ColorActivity extends Activity {
	public final static String EXTRA_MESSAGE = "mg.colorfilters.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.color, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_color,
					container, false);
			return rootView;
		}
	}
	
	public void startResultActivity(View view) {
		EditText mET=(EditText)findViewById(R.id.editText1);
		String message=mET.getText().toString();
		if(!checkHexcode(message))
		{
			Context context = getApplicationContext();
		    CharSequence text = "Invalid hexcode. Sorry!";
		    int duration = Toast.LENGTH_SHORT;

		    Toast toast = Toast.makeText(context, text, duration);
		    toast.show();
		    return;
		}
		Intent intent = new Intent(this, ResultActivity.class);
		
		intent.putExtra(EXTRA_MESSAGE,message );
		startActivity(intent);
	}
	
	public boolean checkHexcode(String message)
	{
		if(message.length()!=6) return false;
		for(int i=0;i<=5;i++)
			if(!(message.charAt(i)=='0'||message.charAt(i)=='1'||message.charAt(i)=='2'||message.charAt(i)=='3'||message.charAt(i)=='4'||message.charAt(i)=='5'||message.charAt(i)=='6'||message.charAt(i)=='7'||message.charAt(i)=='8'||message.charAt(i)=='9'||message.charAt(i)=='a'||message.charAt(i)=='b'||message.charAt(i)=='c'||message.charAt(i)=='d'||message.charAt(i)=='e'||message.charAt(i)=='f'||message.charAt(i)=='A'||message.charAt(i)=='B'||message.charAt(i)=='C'||message.charAt(i)=='D'||message.charAt(i)=='E'||message.charAt(i)=='F')) return false;
		
		return true;
	}

}
