package za.co.tutormi.tpg30bd.project.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import za.co.tutormi.tpg30bd.project.dao.services.SendMail2;

import com.example.tutorme.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") 
public class ForgotPasswordAdminActivity extends Activity {
	
	private Button btnRetrievePass;
	private Button  btnSendMessage;
	private EditText editEmailAddress;
	private EditText editSendMessage;
	private EditText editForgotUsername;
	private EditText editForgotPassSubject;
	private ProgressDialog progressBar;
	private Handler progressBarHandler = new Handler();
	private String passwordResults = "";
	String memberID = "";
	String passwords = "";
	String usernames = "";
	String firstName = "";
	String secondName = "";
	String lastname = "";
	String emails = "";
	String results = "";
	private long fileSize = 0;
	private int progressBarStatus = 0;
	ArrayList<HashMap<String, String>> MyArrList;
	
	private static String USER_NAME = "sibanyonibj@tutormi.co.za";  // AfriHost user name (just the part before "@gmail.com")
	private static String PASSWORD = "JohnBBesabakhe.12@#"; // AfriHost password

	private static String RECIPIENT = "nmjiyane1@gmail.com";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password_admin);
		
		// Permission StrictMode

		if (android.os.Build.VERSION.SDK_INT > 9) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

			StrictMode.setThreadPolicy(policy);
	
		}
		
		
		
		btnRetrievePass = (Button) findViewById(R.id.btnRetrieveMyPassword);
		btnSendMessage = (Button) findViewById(R.id.btnSendMessage);
		
		editSendMessage = (EditText) findViewById(R.id.editMessage);
		
		editEmailAddress = (EditText) findViewById(R.id.editEmail);
		
		editForgotUsername = (EditText) findViewById(R.id.editFogotUsername);
		
		editForgotPassSubject = (EditText) findViewById(R.id.editForgotPassSubject);
		
		
		

		btnRetrievePass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				final AlertDialog.Builder dialog = new AlertDialog.Builder(ForgotPasswordAdminActivity.this);
				final AlertDialog.Builder dialog1 = new AlertDialog.Builder(ForgotPasswordAdminActivity.this);
				
				
				dialog.setTitle("Error! ");

				dialog.setIcon(android.R.drawable.btn_star_big_on);

				dialog.setPositiveButton("Close", null);
				
				if(editEmailAddress.getText().length() == 0)
				{
					dialog.setMessage("Please input [ Your E-Mail Address ]");

					dialog.show();

					editEmailAddress.requestFocus();
				}
				else if(editForgotUsername.getText().length() == 0)
				{
					dialog.setMessage("Please input [ Your MemberID ]");

					dialog.show();

					editForgotUsername.requestFocus();
				}
				else 
				{
					
					dialog1.setTitle("Member Confirmation...");
					dialog1.setMessage("Did You Register as a TutorME Member?");
					dialog1.setIcon(android.R.drawable.btn_star_big_on);
					dialog1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							
							ShowData();
							
							String email = editEmailAddress.getText().toString();
						
							SendMail2 mail = new SendMail2();

							String from = USER_NAME;
							String pass = PASSWORD;
					   	 	String[] to = {email}; // list of recipient email addresses
					   	 	String subject = "Your Requested password is :";
					    
					   	 	results = "HI!... " + firstName + " " + secondName + " " + lastname + 
								  "\nYour Entered Member ID is : " + memberID +
								  "\nYour Username is : " + usernames + 
								  "\nAnd Your Password Requested is : " + passwords + 
								  "\nThank you Tutor By TutorME Admin.";
					    

					   	 	mail.sendFromGMail(from, pass, to, subject, results);
					    
					   	 	Toast.makeText(ForgotPasswordAdminActivity.this,"Password is Successfully sent to your Entered E-mail" + "\nThank You", Toast.LENGTH_LONG).show();

					   	 	editEmailAddress.setText(null);
					   	 	editForgotUsername.setText(null);
					   	 	
					   	 	System.exit(0);
					    
						}


					});


					dialog1.setNegativeButton("No", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							Toast.makeText(ForgotPasswordAdminActivity.this, "Sorry only Registered Members are allowed to Send Message to TutorME Admin", Toast.LENGTH_LONG).show();
							
							btnRetrievePass.setEnabled(false);
							editEmailAddress.setEnabled(false);
							editForgotUsername.setEnabled(false);
						}
						
					});
				}
				dialog1.show();
	 	
			}
		});
		
		btnSendMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				final AlertDialog.Builder dialog = new AlertDialog.Builder(ForgotPasswordAdminActivity.this);
				final AlertDialog.Builder dialog1 = new AlertDialog.Builder(ForgotPasswordAdminActivity.this);
				
				dialog.setTitle("Error! ");

				dialog.setIcon(android.R.drawable.btn_star_big_on);

				dialog.setPositiveButton("Close", null);
				
				if(editForgotPassSubject.getText().length() == 0)
				{
					dialog.setMessage("Please input [Your E-Mail Subject]");

					dialog.show();

					editForgotPassSubject.requestFocus();
				}
				else if(editSendMessage.getText().length() == 0)
				{
					dialog.setMessage("Please input [ Your Message ]");

					dialog.show();

					editSendMessage.requestFocus();
				}
				else 
				{
					
					dialog1.setTitle("Member Confirmation...");
					dialog1.setMessage("Did You Register as a TutorME Member?");
					dialog1.setIcon(android.R.drawable.btn_star_big_on);
					dialog1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							
							ShowData();
							
							String subject = editForgotPassSubject.getText().toString();
							String message = editSendMessage.getText().toString();
							
							SendMail2 mail = new SendMail2();

							String from = USER_NAME;
							String pass = PASSWORD;
					   	 	String[] to = {RECIPIENT}; // list of recipient email addresses
					   	 
					   	 	mail.sendFromGMail(from, pass, to, subject, message);
					    
					   	 	Toast.makeText(ForgotPasswordAdminActivity.this,"E-mail is Successfully sent to TutorME Admin" + "\nThank You", Toast.LENGTH_LONG).show();

					   	 	editForgotPassSubject.setText(null);
					   	 	editSendMessage.setText(null);

					   		 System.exit(0);
						}


					});


					dialog1.setNegativeButton("No", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							Toast.makeText(ForgotPasswordAdminActivity.this, "Sorry only Registered Members are allowed to Send Message to TutorME Admin", Toast.LENGTH_LONG).show();
							
							btnSendMessage.setEnabled(false);
							editForgotPassSubject.setEnabled(false);
							editSendMessage.setEnabled(false);
						}
						
					});
				}
				dialog1.show();
			}
	});

	}
	
	public void ShowData()

	{
		
		String url = "http://tutormi.co.za/tutorme/showforgotPasswordData.php";

		// Paste Parameters
		
		 String id = editForgotUsername.getText().toString();
	
		 List<NameValuePair> params = new ArrayList<NameValuePair>();

		
	  	 params.add(new BasicNameValuePair("MemberID", id));
		
		
		try {

			JSONArray data = new JSONArray(getJSONUrl(url,params));

			
			MyArrList = new ArrayList<HashMap<String, String>>();

			HashMap<String, String> map;

			for(int i = 0; i < data.length(); i++){

				JSONObject c = data.getJSONObject(i);

				map = new HashMap<String, String>();

				map.put("MemberID", c.getString("MemberID"));

				map.put("username", c.getString("username"));

				map.put("password", c.getString("password"));
				
				map.put("firstname", c.getString("firstname"));

				map.put("secondName", c.getString("secondName"));

				map.put("lastName", c.getString("lastName"));
				
				map.put("email", c.getString("email"));
				
				 memberID = c.getString("MemberID");
				 usernames = c.getString("username");
				 passwords = c.getString("password");
				 firstName = c.getString("firstname");
				 secondName = c.getString("secondName");
				 lastname = c.getString("lastName");
				 lastname = c.getString("email");
				
		
			}

		} catch (JSONException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forgotpassword, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		int id = item.getItemId();

		if(id == R.id.action_clear)
		{
			
			editEmailAddress.setText("");
			editForgotPassSubject.setText("");
			editForgotUsername.setText("");
			editSendMessage.setText("");
		}
		

		if(id == R.id.action_exit)
		{
			final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

			dialog.setTitle("Close / Exit TutorME App!");
			dialog.setMessage("Are you sure you want to Exit?");
			dialog.setIcon(android.R.drawable.btn_star_big_on);
			dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					Toast.makeText(ForgotPasswordAdminActivity.this, "Thank you for your Time Bye Bye!" + "\nBy TutorME!", Toast.LENGTH_LONG).show();

					finish();

				}


			});
			dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					Toast.makeText(ForgotPasswordAdminActivity.this, "You did Not Register as Tutor...Please Exit and register First", Toast.LENGTH_LONG).show();

				}


			});
			dialog.show();
			
		}
		return false;
		

	}
	public String getJSONUrl(String url,List<NameValuePair> params) {

		StringBuilder str = new StringBuilder();

		HttpClient client = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(url);

		try {

			httpPost.setEntity(new UrlEncodedFormEntity(params));

			HttpResponse response = client.execute(httpPost);

			StatusLine statusLine = response.getStatusLine();

			int statusCode = statusLine.getStatusCode();

			if (statusCode == 200) { // Download OK

				HttpEntity entity = response.getEntity();

				InputStream content = entity.getContent();

				BufferedReader reader = new BufferedReader(new InputStreamReader(content));

				String line;

				while ((line = reader.readLine()) != null) {

					str.append(line);

				}

			} else {

				Log.e("Log", "Failed to download result..");

			}

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return str.toString();

	}

}

