package mg.colorfilters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ResultActivity extends Activity {
	private static final int SELECT_PHOTO = 100;
	private static String message;
	private static int width;
	private static int height;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		Intent intent = getIntent();
	    message = intent.getStringExtra(ColorActivity.EXTRA_MESSAGE);
	    Context context = getApplicationContext();
	    CharSequence text = "#"+message;
	    int duration = Toast.LENGTH_SHORT;

	    Toast toast = Toast.makeText(context, text, duration);
	    toast.show();
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
		getMenuInflater().inflate(R.menu.result, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_result,
					container, false);
			
			return rootView;
		}
	}
	
	public void loadImageWithFilterOrSaveImage(View view) {
		try{
		Button button=(Button)view;
		if(button.getText().toString().equals("Load image"))
		{
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, SELECT_PHOTO);   
		
		}
		else if(button.getText().toString().equals("Save image"))
		{
		String SD_CARD = Environment.getExternalStorageDirectory().toString();
		File CFOD = new File(SD_CARD + "/OUTPUT");
		CFOD.mkdirs();
		SimpleDateFormat sDF = new SimpleDateFormat("ddMMyyyyhhmmss",Locale.US);
		
		String FILE_NAME=sDF.format(new Date()).toString()+".png";
		File file = new File (CFOD, FILE_NAME);
		if (file.exists()) file.delete (); 
		FileOutputStream out = new FileOutputStream(file);
		ImageView  mIV   = (ImageView)findViewById(R.id.imageView1);
		Bitmap mBM = loadBitmapFromView(mIV);
		mBM.compress(Bitmap.CompressFormat.PNG, 90, out);
		out.flush();
	    out.close();
	    sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
	    mIV.setImageResource(R.drawable.abc_ab_bottom_solid_dark_holo);
	    Context context = getApplicationContext();
	    CharSequence text = "Image saved. Good job!";
	    int duration = Toast.LENGTH_SHORT;

	    Toast toast = Toast.makeText(context, text, duration);
	    toast.show();
	    button.setText("Load image");	
		}
		}
		catch(Exception e)
		{
			
		}
	}
	
	private Bitmap loadBitmapFromView(View v) {
	    
	    final Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    final Canvas c = new  Canvas(b);
	    
	    v.layout(0, 0, width, height);
	    //v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
	    v.draw(c);
	    
	    return b;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
	    Button mButton=(Button)findViewById(R.id.button1);
	    
	    switch(requestCode) { 
	    case SELECT_PHOTO:
	        if(resultCode == RESULT_OK){  
	        	try{
	        	
	        	
	            Uri selectedImage = imageReturnedIntent.getData();
	            InputStream imageStream = getContentResolver().openInputStream(selectedImage);
	            Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
	            ImageView mImageView = (ImageView) findViewById(R.id.imageView1);
	            width = yourSelectedImage.getWidth();
	            height = yourSelectedImage.getHeight();
	            if (yourSelectedImage != null) {
	                mImageView.setImageBitmap(yourSelectedImage);
	                mImageView.setColorFilter(Color.parseColor("#"+message),PorterDuff.Mode.LIGHTEN );
	            }
	            
	            mButton.setText("Save image");
	        	}
	        	catch(Exception e)
	        	{
	        		
	        	}
	        }
	        
	        else
	        {
	        	
	        }
	    }
	}

}
