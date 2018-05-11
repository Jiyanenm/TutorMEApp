package za.co.tutormi.tpg30bd.project.dao;


import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

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
import org.w3c.dom.Text;

import za.co.tutormi.tpg30bd.project.dao.services.SendMail2;

import com.example.tutorme.R;

import android.os.Bundle;

import android.os.StrictMode;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.app.AlertDialog.Builder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.util.Log;

import android.view.MenuItem;
import android.view.View;

import android.view.Menu;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import android.widget.EditText;

import android.widget.Toast;

public class MakePaymentActivity extends Activity {


	private  EditText name;
	private  EditText numberOfHours;
	private  EditText emailAddress;

	private EditText bookedTutorName;
	private EditText bookedRatePerHour;
	private Spinner  subjectSpinner;
	
	private Button btnBookAndSaveData;
	
	private DatePicker date;
	private TimePicker time;
	
	int memberID = 0;
	double totAmount = 0;
	double Drate = 0;
	 
	String tutorSubjects = "";
	String fullNames = "";
	int numOfHours = 0;
	String emailAdd = "";
	int counter = 0;
	int day = 0;
	int month = 0;
	int yearz = 0;
	int hourz = 0;
	int minutez = 0;
	String fullDate = "";
	String fullTime = "";
	String tutorEmailAddress = "";
	boolean HourView = true;
	
	ArrayList<String> listSubjects = new ArrayList<String>();
	
	ArrayAdapter<String> adapter;
	
	private static String USER_NAME = "sibanyonibj@tutormi.co.za";  // AfriHost user name (just the part before "@gmail.com")
	private static String PASSWORD = "JohnBBesabakhe.12@#"; // AfriHost password

	String RECIPIENT = tutorEmailAddress;

	
	@SuppressLint("NewApi")

	@Override

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_make_payment);

		// Permission StrictMode

		if (android.os.Build.VERSION.SDK_INT > 9) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

			StrictMode.setThreadPolicy(policy);
		}

		
		bookedRatePerHour = (EditText)findViewById(R.id.editUpdatePassword);
		bookedTutorName = (EditText) findViewById(R.id.txtUpdateUsername);
		
		
		 //Invoke Total Amount Method here
		
		
		Intent i = getIntent();
	
		memberID = i.getExtras().getInt("ID", 0) ;
		String firstName = i.getExtras().getString("FIRSTNAME");
		String secondName = i.getExtras().getString("SECONDNAME");
		String lastName = i.getExtras().getString("LASTNAME");
		Drate = i.getDoubleExtra("RATE", 0.0);
	    tutorSubjects = i.getExtras().getString("SUBJECT");
	    tutorEmailAddress = i.getExtras().getString("EMAIL");
	    
	    
	   String sub[] = {tutorSubjects};
	  
		subjectSpinner  = (Spinner) findViewById(R.id.spinnerTutorSubject);
		
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, sub);
	    
	    subjectSpinner.setAdapter(adapter);
		
		btnBookAndSaveData = (Button) findViewById(R.id.btnSaveUpdate);
		
		date = (DatePicker) findViewById(R.id.datePicker);
		time = (TimePicker) findViewById(R.id.timePicker);
		
		
		
		bookedRatePerHour.setEnabled(false);
		
		bookedRatePerHour.setText("R" + String.valueOf(Drate));
		
		bookedTutorName.setEnabled(false);
		bookedTutorName.setText(firstName + " " + secondName + " " + lastName);
		
	
		RECIPIENT = tutorEmailAddress;
		//time.setIs24HourView(HourView);
		
		
		
		// btnSave

		btnBookAndSaveData.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			
				SaveData();
			}
			
		});

	}
	public boolean SaveData()

	{
		
		final EditText name = (EditText) findViewById(R.id.editFullNames);
		final EditText emailAddress = (EditText) findViewById(R.id.editEmailAddress);
		final EditText numberOfHours = (EditText) findViewById(R.id.editNumOfHours);
		
		
		
			    
	
		fullNames = name.getText().toString();
		numOfHours = Integer.parseInt(numberOfHours.getText().toString());
		emailAdd = emailAddress.getText().toString();
		
		

		final AlertDialog.Builder ad = new AlertDialog.Builder(this);
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		
		
		final Calendar calenda = Calendar.getInstance();
		
		//hour = calenda.get(Calendar.HOUR_OF_DAY);
		//minute = calenda.get(Calendar.MINUTE);
		yearz = calenda.get(Calendar.YEAR);
		day = calenda.get(Calendar.DAY_OF_MONTH);
		month = calenda.get(Calendar.MONTH);
		
		
		
		
		int hours = Integer.parseInt(numberOfHours.getText().toString());
		
		calculateTotal(Drate, numOfHours);
	
		
		hourz = time.getCurrentHour();
		minutez = time.getCurrentMinute();
		
		fullDate = String.valueOf(day + " - " +  month + " - "+ yearz);
		fullTime = String.valueOf(+ hourz + ":" +  minutez);
		
		date.init(calenda.get(Calendar.YEAR), calenda.get(Calendar.MONTH), calenda.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				
				yearz =  year;
				month = monthOfYear;
				day = dayOfMonth;
				
				

			}
		});
		
	
		
		ad.setTitle("Error! ");

		ad.setIcon(android.R.drawable.btn_star_big_on);

		ad.setPositiveButton("Close", null);

		// Check UserID,UserName and UserEmail

		if(name.getText().length() == 0)

		{

			ad.setMessage("Please input [Your Full Names] ");

			ad.show();

			name.requestFocus();

			return false;

		}

		if(numberOfHours.getText().length() == 0)

		{

			ad.setMessage("Please input [Number of Hour Your booking] ");

			ad.show();

			numberOfHours.requestFocus();

			return false;

		}

		
		// Check Email 

		if(emailAddress.getText().length() == 0)

		{

			ad.setMessage("Sorry Please input [E-Mail Address]");

			ad.show();

			emailAddress.requestFocus();

			return false;

		}


		String url = "http://tutormi.co.za/tutorme/saveData.php";
		

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("bookedTutorsID", String.valueOf(memberID)));
		
		params.add(new BasicNameValuePair("name", fullNames));

		params.add(new BasicNameValuePair("emailAddress", emailAdd));

		params.add(new BasicNameValuePair("numberOfHoursBooked",String.valueOf(numberOfHours)));
		
		params.add(new BasicNameValuePair("subject",String.valueOf(subjectSpinner.getSelectedItem())));
		
		params.add(new BasicNameValuePair("totalAmount",String.valueOf(calculateTotal(Drate, hours))));
	
		params.add(new BasicNameValuePair("bookingDate",fullDate));
		
		params.add(new BasicNameValuePair("bookingTime",fullTime));
		
		
		
		
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

			Toast.makeText(MakePaymentActivity.this, "Sorry Booking is not Successfully-Contact TutorME Admin", Toast.LENGTH_SHORT).show();

		}

		else

		{
				

			
			
					dialog.setTitle("Please Confirm the following booking!");
					dialog.setMessage("Your booking is as follow :"+ "\n" 
					+ "\nBooked Tutor Names - [" + bookedTutorName.getText().toString() + "]"+ "\t" + "\t"
					+ "\nBooked Tutor Location - [" + "Soshanguve]" + "\t" + "\t"
					+ "\nBooked Tutor Subject- ["+ subjectSpinner.getSelectedItem().toString() + "]" + "\t" + "\t"
					+ "\nTotal Amount - R[" + totAmount + "]" );
			
			dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					String message = "Hi! " + bookedTutorName.getText().toString() + "\n" + "\nYou Have bookings Available :" + "\n" + 
									"\nDate And Time :"  + fullDate + " " + fullTime +
									"\nClient Names  :" + name.getText().toString() +
									"\nSubject booked :" + subjectSpinner.getSelectedItem().toString() +
									"\nLocation : Soshanguve" + 
									"\nAmount Paid in your Account is :R"+totAmount + "\n" +
									"\nPlease TutorME Admin Aucknowledges you to assists the client" + "\n" +
									"\nThank You By TutorME Admin";
					
			
					
					SendMail2 mail = new SendMail2();
					String subject = "Booking Confirmations";
					String from = USER_NAME;
					String pass = PASSWORD;
			   	 	String[] to = {RECIPIENT}; // list of recipient email addresses
			   	 
			   	 Toast.makeText(MakePaymentActivity.this, RECIPIENT, Toast.LENGTH_SHORT).show();

			   	 	
			   	 	mail.sendFromGMail(from, pass, to, subject, message);
			    
					
						
					Toast.makeText(MakePaymentActivity.this, "Booked Successfully Thank you.", Toast.LENGTH_SHORT).show();

					name.setText("");
					numberOfHours.setText("");
					emailAddress.setText("");
				
					
					Intent intent = new Intent(MakePaymentActivity.this,SearchTutorByNameActivity.class);
					
					startActivity(intent);
					
					
					
				}
			});
		 dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					Toast.makeText(MakePaymentActivity.this, "Sorry Try to book another Tutor", Toast.LENGTH_SHORT).show();
					
					Intent intent = new Intent(MakePaymentActivity.this,SearchTutorByNameActivity.class);
					startActivity(intent);
				}
			});

		}
		dialog.show();
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

		getMenuInflater().inflate(R.menu.payment_menu, menu);

		return true;

	}
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub


		int id = item.getItemId();

		if (id == R.id.action_back) {


			finish();

			return true;
		}
		if (id == R.id.action_clear) {

			numberOfHours.setText("");
			name.setText("");
			emailAddress.setText("");

			

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
					
					Toast.makeText(MakePaymentActivity.this, "Thank you-Enjoy your Tutor and Let us keep learning! And Sharing knowledge" + "\nBy TutorME ''Lets Keep learning and share Ideas''" + "\nBye! Bye!", Toast.LENGTH_LONG).show();
					
					System.exit(0);
					
				}
				
				
			});
	    	  
	    	  
	    	  dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
	  			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					Toast.makeText(MakePaymentActivity.this, "Thank you...but you only allowed to book Once per Tutor" + "\nThank you by ''TutorME''", Toast.LENGTH_LONG).show();
				}
				
				
			});

			
	    	  dialog.show();
			return true;
		}


		if (id == R.id.action_settings) {

			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	public double calculateTotal(double rRate, int hours)
	{
		totAmount = rRate * hours;
		
		
		return totAmount;
	}
	
	


}