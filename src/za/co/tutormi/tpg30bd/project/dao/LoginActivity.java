package za.co.tutormi.tpg30bd.project.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.app.Fragment.SavedState;
import android.app.ListActivity;
import android.app.ProgressDialog;

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
import org.json.JSONException;
import org.json.JSONObject;

import com.example.tutorme.R;



import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.DialogPreference;
import android.provider.ContactsContract.RawContacts.DisplayPhoto;
import android.provider.Settings.System;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button btnLogin;
	private EditText username;
	private EditText password;
	private CheckBox chRememberMe;
	private TextView tvForgotPassword;
	private ProgressDialog progressDialog;
	private Context  context;
	public String PREFS_USERNAME = "prefsUsername";
	public String PREFS_PASSWORD= "prefsPassword";
	private SharedPreferences loginPreference;
	private SharedPreferences.Editor loginPrefEditor;
	String resultServer;
	private Boolean saveLogin;
	private String user,pass;
	int countLogins = 0;



	@SuppressLint("NewApi")
	@Override

	public void onCreate(Bundle savedInstanceState) 
	{
		try
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_login);


			final AlertDialog.Builder dialogBox = new AlertDialog.Builder(this);
			final AlertDialog.Builder dialogBox1 = new AlertDialog.Builder(this);


			btnLogin = (Button) findViewById(R.id.btnLogin);
			username = (EditText) findViewById(R.id.editLoginUsername);
			password = (EditText) findViewById(R.id.editLoginPassword);
			chRememberMe = (CheckBox) findViewById(R.id.checkBoxRemeberMe);
			tvForgotPassword = (TextView) findViewById(R.id.textViewForgotPassword);
			context = this;

			loginPreference = getSharedPreferences("loginPrefs", MODE_PRIVATE);
			loginPrefEditor = loginPreference.edit();

			saveLogin = loginPreference.getBoolean("saveLogin", false);

			if(saveLogin == true)
			{
				password.setText(loginPreference.getString("password", ""));
				username.setText(loginPreference.getString("username", ""));
				chRememberMe.setChecked(true);
			}



			// Permission StrictMode

			if (android.os.Build.VERSION.SDK_INT > 9) {

				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

				StrictMode.setThreadPolicy(policy);

			}


			btnLogin.setOnClickListener(new OnClickListener() {


				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					
					
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(username.getWindowToken(), 0);

					user = username.getText().toString();
					pass = password.getText().toString();

					if(chRememberMe.isChecked())
					{
						loginPrefEditor.putBoolean("saveLogin", true);
						loginPrefEditor.putString("password", pass);
						loginPrefEditor.putString("username", user);
						loginPrefEditor.commit();
					}
					else
					{
						loginPrefEditor.clear();
						loginPrefEditor.commit();
					}

					v.setEnabled(false);
					
					//Progress Dialog
					
					AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>()
							{
						@Override
						protected void onPreExecute() {
							// TODO Auto-generated method stub

							progressDialog = new ProgressDialog(LoginActivity.this);
							progressDialog.setTitle("Processing...");
							progressDialog.setMessage("Loggin In Please Wait...");
							progressDialog.setCancelable(false);
							progressDialog.setIndeterminate(true);
							progressDialog.show();

							
						}
						

						@Override
						protected Void doInBackground(Void... params) {
							// TODO Auto-generated method stub

							try {
								Thread.sleep(10000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return null;
						}

						@Override
						protected void onPostExecute(Void resultServer) {
							// TODO Auto-generated method stub
							if(progressDialog != null)
							{
								
								
								
								progressDialog.dismiss();
								
								btnLogin.setEnabled(true);
							}
						}

							};

							task.execute((Void[])null);
							
							
							
							String url = "http://tutormi.co.za/tutorme/checkLogin.php";

							
							List<NameValuePair> params = new ArrayList<NameValuePair>();

							params.add(new BasicNameValuePair("strUser", username.getText()
									.toString()));

							params.add(new BasicNameValuePair("strPass", password.getText()
									.toString()));

							
							/**
							 * Get result from Server (Return the JSON Code)
							 * 
							 * StatusID = ? [0=Failed,1=Complete]
							 * 
							 * MemberID = ? [Eg : 1]
							 * 
							 * Error = ? [On case error return custom error message]
							 * 
							 * 
							 * 
							 * Eg Login Failed = {"StatusID":"0","MemberID":"0","Error":
							 * "Incorrect Username and Password"}
							 * 
							 * Eg Login Complete =
							 * {"StatusID":"1","MemberID":"2","Error":""}
							 */

							resultServer = getHttpPost(url, params);

							/*** Default Value ***/

							String strStatusID = "0";

							String strMemberID = "0";

							String strError = "Incorrect Password Entered Please Try Again...";

							JSONObject c;

							try {

								c = new JSONObject(resultServer);

								strStatusID = c.getString("StatusID");

								strMemberID = c.getString("MemberID");

								strError = c.getString("Error");

							} 
							catch (JSONException e)
							{

								// TODO Auto-generated catch block

								e.printStackTrace();

							}

							// Prepare Login



							if (strStatusID.equals("0"))
							{


								
								// Dialog
								dialogBox.setTitle("Incorrect Credentials Entered!...");

								dialogBox.setIcon(android.R.drawable.btn_star_big_on);

								dialogBox.setPositiveButton("Close", null);

								dialogBox.setMessage(strError);

								username.setText("");

								password.setText("");

								checkLogins();
								dialogBox.show();

								countLogins++;




							}

							else if(password.getText().toString().equals("") || username.getText().toString().equals(""))
							{

								dialogBox1.setTitle("No Credentials entered!...");

								dialogBox1.setIcon(android.R.drawable.btn_star_big_on);
								dialogBox1.setMessage("You need to enter your Credentials to Login...\t" + "\t" + "\nDid you register yourself as a Tutor?");
								dialogBox1.setPositiveButton("Yes",  new AlertDialog.OnClickListener(){

									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub


										Toast.makeText(LoginActivity.this, "Enter the correct Credentials to Login" + "\nThank You!....",Toast.LENGTH_SHORT).show();

									}

								});
								dialogBox1.setNegativeButton("No",new AlertDialog.OnClickListener(){

									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub

										Toast.makeText(LoginActivity.this, "Complete the registration form to Register" + "\nThank You",
												Toast.LENGTH_SHORT).show();
										Intent intent = new Intent(LoginActivity.this,RegisterTutorWebActivity.class);
										startActivity(intent);



									}

								});
								
								dialogBox1.show();
								
							}

							else

							{	 
								Toast.makeText(LoginActivity.this, "Successfully Login",Toast.LENGTH_SHORT).show();

								Intent newActivity = new Intent(LoginActivity.this,MainActivity.class);

								newActivity.putExtra("MemberID",strMemberID);

								startActivity(newActivity);

								username.setText(null);
								password.setText(null);		 

							}
							

				}


			});
			
			tvForgotPassword.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent intent = new Intent(LoginActivity.this,ForgotPasswordAdminActivity.class);
					startActivity(intent);

				}
			});

		}
		catch(Exception err)
		{
			err.printStackTrace();
		}



	}
	public String getHttpPost(String url,List<NameValuePair> params) 
	{

		StringBuilder str = new StringBuilder();

		HttpClient client = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(url);



		try {

			httpPost.setEntity(new UrlEncodedFormEntity(params));

			HttpResponse response = client.execute(httpPost);

			StatusLine statusLine = response.getStatusLine();

			int statusCode = statusLine.getStatusCode();

			if (statusCode == 200) 
			{ 
				// Status OK

				HttpEntity entity = response.getEntity();

				InputStream content = entity.getContent();

				BufferedReader reader = new BufferedReader(new InputStreamReader(content));

				String line;

				while ((line = reader.readLine()) != null) {

					str.append(line);

				}

			} 
			else 
			{

				Log.e("Log", "Failed to download result..");

			}

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return str.toString();

	}


	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.login, menu);

		return true;


	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub


		int id = item.getItemId();

		if (id == R.id.Search_tutor) {

			Intent intent = new Intent(this,SearchAvailTutorActivity.class);
			startActivity(intent);

			return true;
		}
		if (id == R.id.Register_tutor) {

			Intent intent = new Intent(this,RegisterTutorWebActivity.class);
			startActivity(intent);

			return true;
		}
		if (id == R.id.action_exit) {

			final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

			dialog.setTitle("Close / Exit TutorME App!");
			dialog.setMessage("Are you sure you want to Exit?");
			dialog.setIcon(android.R.drawable.btn_star_big_on);
			dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					Toast.makeText(LoginActivity.this, "Thank you for your Time Bye Bye!" + "\nBy TutorME!", Toast.LENGTH_LONG).show();

					finish();

				}


			});


			dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					Toast.makeText(LoginActivity.this, "Thank you!...Register or Search Tutors", Toast.LENGTH_LONG).show();
				}


			});
			dialog.show();

			return true;
		}
		if (id == R.id.action_help) {

			Intent intent = new Intent(this,ForgotPasswordAdminActivity.class);
			startActivity(intent);

			return true;
		}
		if (id == R.id.action_settings) {

			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	public int checkLogins()
	{
		if(countLogins > 3)
		{
			Toast.makeText(LoginActivity.this, "Sorry Tutor It Looks like You dont know your Credentials Bye Bye!" + "\nBy TutorME Admin", Toast.LENGTH_LONG).show();

			finish();
		}

		return countLogins;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		if(progressDialog != null)
		{
			progressDialog.dismiss();
			btnLogin.setEnabled(true);
		}

		super.onDestroy();
	}


}


