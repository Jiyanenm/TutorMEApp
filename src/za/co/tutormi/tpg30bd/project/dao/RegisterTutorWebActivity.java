package za.co.tutormi.tpg30bd.project.dao;


import java.io.File;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.example.tutorme.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RegisterTutorWebActivity extends Activity {

	private WebView web;
	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_tutoe_web);
		
		final AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterTutorWebActivity.this);
		
		web = (WebView) findViewById(R.id.webViewRegister);
		
			 
			 WebSettings webSettings = web.getSettings();
			 webSettings.setBuiltInZoomControls(true);
			 webSettings.setGeolocationEnabled(true);
			 webSettings.setAllowFileAccess(true);
			 webSettings.setAllowContentAccess(true);
			 webSettings.setAllowFileAccessFromFileURLs(true);
			 webSettings.setJavaScriptEnabled(true);
			 web.setWebViewClient(new WebViewClient());
			 webSettings.setAllowUniversalAccessFromFileURLs(true);
			 webSettings.setDisplayZoomControls(true);
			 webSettings.setLoadsImagesAutomatically(true);
			 web.loadUrl("http://tutormi.co.za/tutorme/registerTutor.php");
		
			
		
			
			 getActionBar().setHomeButtonEnabled(true);
			 getActionBar().setDisplayHomeAsUpEnabled(true);
			 
			 setTitle("Welcome Tutor");
			 getActionBar().setIcon(R.drawable.tutorme);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_tutor, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		int id = item.getItemId();

		if(id == R.id.action_back)
		{
			Intent intent = new Intent(RegisterTutorWebActivity.this,LoginActivity.class);
			
			startActivity(intent);
		}

		if(id == R.id.action_settings)
		{
			
		}
		return super.onOptionsItemSelected(item);
	}
	
}
