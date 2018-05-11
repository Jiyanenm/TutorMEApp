package za.co.tutormi.tpg30bd.project.dao;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.util.ArrayList;

import java.util.List;

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

import android.os.Bundle;

import android.os.StrictMode;

import android.annotation.SuppressLint;

import android.app.Activity;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.util.Log;

import android.view.MenuItem;
import android.view.View;

import android.view.Menu;

import android.widget.Button;

import android.widget.EditText;

import android.widget.TextView;

import android.widget.Toast;

public class UpdateTutorProfileActivity extends Activity {

	@SuppressLint("NewApi")

	@Override

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_update_tutor_profile);

		// Permission StrictMode

		if (android.os.Build.VERSION.SDK_INT > 9) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

			StrictMode.setThreadPolicy(policy);

		}

		showInfo();

		// btnSave

		Button btnSave = (Button) findViewById(R.id.btnUpdateSave);

		// Perform action on click

		btnSave.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				if(SaveData())

				{

					// When Save Complete

					Intent newActivity = new Intent(UpdateTutorProfileActivity.this,MainActivity.class);

					startActivity(newActivity);

				}

			}

		});


	}

	public void showInfo()

	{

		// txtMemberID,txtUsername,txtPassword,txtConPassword,txtName,txtEmail,txtTel

		final TextView username = (TextView)findViewById(R.id.txtUpdateUsernames);
		final TextView memberID = (TextView)findViewById(R.id.txtUpdateMemberID);
		final EditText password = (EditText) findViewById(R.id.editUpdatePassword);
		final EditText confiPassword = (EditText) findViewById(R.id.editUpdateConfPassword);
		final EditText subject = (EditText)findViewById(R.id.editUpdateSubject);

		final EditText email = (EditText)findViewById(R.id.editUpdateEmail);

		final EditText rate = (EditText)findViewById(R.id.editUpdateRatePerHour);
		
		//final EditText rate = (EditText)findViewById(R.id.editu);
		Button btnUpdateSave = (Button) findViewById(R.id.btnUpdateSave);

		String url = "http://tutormi.co.za/tutorme/getByMemberID.php";
		


		Intent intent= getIntent();

		final String MemberID = intent.getStringExtra("MemberID");

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("MemberID", MemberID));

		/** Get result from Server (Return the JSON Code)

		 *

		 * {"MemberID":"2","Username":"adisorn","Password":"adisorn@2","Name":"Adisorn Bunsong","Tel":"021978032","Email":"adisorn@thaicreate.com"}

		 */

		String resultServer  = getHttpPost(url,params);

		String strMemberID = "";

		String strUsername = "";

		String strPassword = "";

		String strSubject = "";

		String strEmail = "";

		String strRate = "";

		JSONObject c;

		try {

			c = new JSONObject(resultServer);

			strMemberID = c.getString("MemberID");

			strUsername = c.getString("username");

			strPassword = c.getString("password");

			strSubject = c.getString("subject");

			strEmail = c.getString("email");

			strRate = c.getString("rate");

			if(!strMemberID.equals(""))

			{

				memberID.setText(strMemberID);

				username.setText(strUsername);

				password.setText(strPassword);

				confiPassword.setText(strPassword);

				subject.setText(strSubject);

				email.setText(strEmail);

				rate.setText(strRate);

			}

			else

			{

				//tMemberID.setText("-");

				//tUsername.setText("-");

				//tName.setText("-");

				//tEmail.setText("-");

				//tTel.setText("-");

				btnUpdateSave.setEnabled(false);

				btnUpdateSave.requestFocus();

			}

		} catch (JSONException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

	public boolean SaveData()

	{

		// txtMemberID,txtPassword,txtName,txtEmail,txtTel

		final TextView txtMemberID = (TextView)findViewById(R.id.txtUpdateMemberID);

		final EditText txtPassword = (EditText)findViewById(R.id.editUpdatePassword);

		final EditText txtConPassword = (EditText)findViewById(R.id.editUpdateConfPassword);

		final EditText txtSubject = (EditText)findViewById(R.id.editUpdateSubject);

		final EditText txtEmail = (EditText)findViewById(R.id.editUpdateEmail);

		final EditText txtRate = (EditText)findViewById(R.id.editUpdateRatePerHour);

		// Dialog

		final AlertDialog.Builder ad = new AlertDialog.Builder(this);

		ad.setTitle("Error! ");

		ad.setIcon(android.R.drawable.btn_star_big_on);

		ad.setPositiveButton("Close", null);

		// Check Password

		if(txtPassword.getText().length() == 0 || txtConPassword.getText().length() == 0 )

		{

			ad.setMessage("Please input [Password/Confirm Password] ");

			ad.show();

			txtPassword.requestFocus();

			return false;

		}

		// Check Password and Confirm Password (Match)

		if(!txtPassword.getText().toString().equals(txtConPassword.getText().toString()))

		{

			ad.setMessage("Password and Confirm Password Not Match! ");

			ad.show();

			txtConPassword.requestFocus();

			return false;

		}

		// Check Name

		if(txtSubject.getText().length() == 0)

		{

			ad.setMessage("Please input [Subject] ");

			ad.show();

			txtSubject.requestFocus();

			return false;

		}

		// Check Email

		if(txtEmail.getText().length() == 0)

		{

			ad.setMessage("Please input [Email] ");

			ad.show();

			txtEmail.requestFocus();

			return false;

		}

		// Check Tel

		if(txtRate.getText().length() == 0)

		{

			ad.setMessage("Please input [Rate] ");

			ad.show();

			txtRate.requestFocus();

			return false;

		}

		String url = "http://tutormi.co.za/tutorme/updateData.php";
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("MemberID", txtMemberID.getText().toString()));

		params.add(new BasicNameValuePair("password", txtPassword.getText().toString()));

		params.add(new BasicNameValuePair("subject", txtSubject.getText().toString()));

		params.add(new BasicNameValuePair("email", txtEmail.getText().toString()));

		params.add(new BasicNameValuePair("rate", txtRate.getText().toString()));


		/** Get result from Server (Return the JSON Code)

		 * StatusID = ? [0=Failed,1=Complete]

		 * Error    = ? [On case error return custom error message]

		 *

		 * Eg Save Failed = {"StatusID":"0","Error":"Email Exists!"}

		 * Eg Save Complete = {"StatusID":"1","Error":""}

		 */

		String resultServer  = getHttpPost(url,params);

		/*** Default Value ***/

		String strStatusID = "0";

		String strError = "Unknow Status!";

		JSONObject c;

		try {

			c = new JSONObject(resultServer);

			strStatusID = c.getString("StatusID");

			strError = c.getString("Error");

		} catch (JSONException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		// Prepare Save Data

		if(strStatusID.equals("0"))

		{

			ad.setMessage(strError);

			ad.show();

			return false;

		}

		else

		{

			Toast.makeText(UpdateTutorProfileActivity.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();

		}

		return true;

	}

	public String getHttpPost(String url,List<NameValuePair> params) {

		StringBuilder str = new StringBuilder();

		HttpClient client = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(url);

		try {

			httpPost.setEntity(new UrlEncodedFormEntity(params));

			HttpResponse response = client.execute(httpPost);

			StatusLine statusLine = response.getStatusLine();

			int statusCode = statusLine.getStatusCode();

			if (statusCode == 200) { // Status OK

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

	@Override

	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.register_tutor, menu);

		return true;

	}
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub


		int id = item.getItemId();

		if (id == R.id.action_clear) {


			final EditText password = (EditText) findViewById(R.id.editUpdatePassword);
			final EditText confiPassword = (EditText) findViewById(R.id.editUpdateConfPassword);
			final EditText subject = (EditText)findViewById(R.id.editUpdateSubject);

			final EditText email = (EditText)findViewById(R.id.editUpdateEmail);

			final EditText rate = (EditText)findViewById(R.id.editUpdateRatePerHour);
			
			password.setText("");
			confiPassword.setText("");
			subject.setText("");
			email.setText("");
			rate.setText("");
			
			return true;
		}
		if (id == R.id.action_back) {

			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);

			return true;
		}

		if (id == R.id.action_settings) {

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}