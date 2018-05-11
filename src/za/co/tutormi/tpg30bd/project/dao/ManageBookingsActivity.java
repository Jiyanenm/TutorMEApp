package za.co.tutormi.tpg30bd.project.dao;


import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.util.ArrayList;

import java.util.HashMap;

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

import org.json.JSONArray;

import org.json.JSONException;

import org.json.JSONObject;

import za.co.tutormi.tpg30bd.project.dao.services.SendMail2;

import com.example.tutorme.R;

import android.os.Bundle;

import android.os.StrictMode;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import android.content.Intent;

import android.util.Log;

import android.view.ContextMenu;

import android.view.ContextMenu.ContextMenuInfo;

import android.view.LayoutInflater;

import android.view.MenuItem;

import android.view.View;

import android.view.Menu;

import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;

import android.widget.AdapterView;

import android.widget.BaseAdapter;

import android.widget.Button;

import android.widget.EditText;

import android.widget.ListView;

import android.widget.TextView;

import android.widget.Toast;

public class ManageBookingsActivity extends Activity {

	ArrayList<HashMap<String, String>> MyArrList;
	private ListView list;
	int position = 0;
	String names = "";
	String totAmount = "";
	String NumnerOfHours = "";
	String subject = "";
	
	private static String USER_NAME = "sibanyonibj@tutormi.co.za";  // AfriHost user name (just the part before "@gmail.com")
	private static String PASSWORD = "JohnBBesabakhe.12@#"; // AfriHost password
	String email = "";
	
	
	String[] Cmd = {"View Booking Info","Confirm Booking","Delete Booking"};

	@SuppressLint("NewApi")

	@Override

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_manage_bookings);

		// Permission StrictMode

		if (android.os.Build.VERSION.SDK_INT > 9) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

			StrictMode.setThreadPolicy(policy);

		}
		
		ShowData();

	}
	
	

	public void ShowData()

	{

		// listView1

		final ListView lisView1 = (ListView)findViewById(R.id.listView1);  

		/**

		 * [{"MemberID":"1","Username":"weerachai","Password":"weerachai@1","Name":"Weerachai Nukitram","Tel":"0819876107","Email":"weerachai@thaicreate.com"},

		 * {"MemberID":"2","Username":"adisorn","Password":"adisorn@2","Name":"Adisorn Bunsong","Tel":"021978032","Email":"adisorn@thaicreate.com"},

		 * {"MemberID":"3","Username":"surachai","Password":"surachai@3","Name":"Surachai Sirisart","Tel":"0876543210","Email":"surachai@thaicreate.com"}]

		 */

		String url = "http://tutormi.co.za/tutorme/showAllDataBooking.php";

		Intent intent = getIntent();
		
		String MemberBookedID = intent.getStringExtra("MemberID");
		
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("bookedTutorsID", MemberBookedID));

		try {

			JSONArray data = new JSONArray(getJSONUrl(url,params));

			MyArrList = new ArrayList<HashMap<String, String>>();

			HashMap<String, String> map;

			for(int i = 0; i < data.length(); i++){

				JSONObject c = data.getJSONObject(i);

				map = new HashMap<String, String>();

				map.put("bookedTutorsID", c.getString("bookedTutorsID"));

				map.put("name", c.getString("name"));

				map.put("emailAddress", c.getString("emailAddress"));

				map.put("numberOfHoursBooked", c.getString("numberOfHoursBooked"));

				map.put("subjects", c.getString("subjects"));

				map.put("totalAmount", c.getString("totalAmount"));

				MyArrList.add(map);

			}
			
			if(MyArrList.size() == 0)
			{
				final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				
				dialog.setTitle("No Bookings");
				dialog.setMessage("Sorry Tutor-You have no bookings as yet please Try again next Time");
				dialog.setIcon(android.R.drawable.btn_star_big_on);
				dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						finish();
					}
				});
				dialog.show();
			}
			
			
			lisView1.setAdapter(new ImageAdapter(this));

			
			registerForContextMenu(lisView1);
			
	

		} catch (JSONException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
		


	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {



		menu.setHeaderIcon(android.R.drawable.btn_star_big_on);

		menu.setHeaderTitle("Manage Your Bookings");

		String[] menuItems = Cmd;

		for (int i = 0; i<menuItems.length; i++) {

			menu.add(Menu.NONE, i, i, menuItems[i]);

		}

	}

	@Override

	public boolean onContextItemSelected(MenuItem item) {
		
		
		
		

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

		int menuItemIndex = item.getItemId();

		String[] menuItems = Cmd;

		String CmdName = menuItems[menuItemIndex];

		// Check Event Command

		if ("View Booking Info".equals(CmdName)) {
			

			names = MyArrList.get(info.position).get("name").toString();

		    email = MyArrList.get(info.position).get("emailAddress").toString();

			 NumnerOfHours = MyArrList.get(info.position).get("numberOfHoursBooked").toString();

			 subject = MyArrList.get(info.position).get("subjects").toString();

		    totAmount = MyArrList.get(info.position).get("totalAmount").toString();
			
			ShowData();
			
			Toast.makeText(ManageBookingsActivity.this,"Client Full Names :" + names + "\nE-Mail :" + email + "\nNumber Of Hours booked :" + NumnerOfHours + "\nSubject Booked :" + subject + "\nTotal Amount Paid : R"+totAmount, Toast.LENGTH_LONG).show();

		
		} else if ("Confirm Booking".equals(CmdName)) {

			
			final AlertDialog.Builder dialog1 = new AlertDialog.Builder(ManageBookingsActivity.this);
			
			dialog1.setTitle("Confirm Booking-Email Will be Sent");
			dialog1.setIcon(android.R.drawable.btn_star_big_on);
			dialog1.setMessage("Are you sure you want to Confirm ['" + names + "'] bookings " + "Which they can pay you ['R" + totAmount + " ']for Tutoring Them?");
			dialog1.setPositiveButton("Yes",  new AlertDialog.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
	
					SendMail2 mail = new SendMail2();

					String from = USER_NAME;
					String pass = PASSWORD;
			   	 	String[] to = {email}; // list of recipient email addresses
			   	 	String subjects = "Booking Confirmation From Tutor";
			   	 	String results = "";
			   	 	results = "HI!... " + names +
			   	 			"\nYour Tutor Booking has been confirmed" +
			   	 			"\nSubject you have booked is : " + subject +
			   	 			"\nNumber of Hours / Session you booked is : " + NumnerOfHours +
			   	 			"\nAnd The Total Amount you have paid is R"+totAmount+
			   	 			"\nTutorME Admin Wish Best of Luck in your Studies" +
			   	 			"\nAny Information or Queries please Contact TutorME Admin Below At:"+
			   	 			"\nE-mail Address : nmjiyane1@gmail.com / nmjiyane@yahoo.com" +
			   	 			"\nCell Number : 073 0781590 / 0765961601" +
			   	 			"\nPhysical Address : 676 Kwamhlanga 'AA' 1022" +
			   	 			"\n" + "\nThank You";
			   	 	
			   	 	mail.sendFromGMail(from, pass, to, subjects, results);
			    
			   	 	Toast.makeText(ManageBookingsActivity.this,"E-mail Successfully sent to Client-Thank you", Toast.LENGTH_LONG).show();

					
					
				}
			});
			dialog1.setNegativeButton("NO", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					Toast.makeText(ManageBookingsActivity.this, "Ok! Tutor Please Delete your bookings,If you cant Tutor or Help [ '" + names + "' ]" + "\nNB-NO Amount of R" + totAmount + "Will be Paid To Your Account" + "\nThank You", Toast.LENGTH_LONG).show();
					
				}
			});
			
			dialog1.show();

		} else if ("Delete Booking".equals(CmdName)) {

			
			/**

			 * Call the mthod

			 */
			final AlertDialog.Builder dialog = new AlertDialog.Builder(ManageBookingsActivity.this);
			final AlertDialog.Builder dialog1 = new AlertDialog.Builder(ManageBookingsActivity.this);
			
			dialog.setTitle("Delete Booking");
			dialog.setIcon(android.R.drawable.btn_star_big_on);
			dialog.setMessage("Are you sure you want to Delete ['" + names + "'] bookings " + "Which they can pay you ['R" + totAmount + " ']for Tutoring Them?");
			dialog.setPositiveButton("Yes",  new AlertDialog.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
					String url = "http://tutormi.co.za/tutorme/deleteBookingData.php";

					List<NameValuePair> params = new ArrayList<NameValuePair>();

					params.add(new BasicNameValuePair("subjects", MyArrList.get(position).get("subjects")));
					//params.add(new BasicNameValuePair("bookingID", MyArrList.get(position).get("bookingID")));
					
					
					String resultServer  = getJSONUrl(url,params);
					
					String strStatusID = "0";

					String strError = "Unknow Status";

					try {

						JSONObject c = new JSONObject(resultServer);

						strStatusID = c.getString("StatusID");

						strError = c.getString("Error");

					} catch (JSONException e) {

						// TODO Auto-generated catch block

						e.printStackTrace();

					}



					// Prepare Delete

					if(strStatusID.equals("0"))

					{

						// Dialog

						dialog1.setTitle("Error! ");

						dialog1.setIcon(android.R.drawable.btn_star_big_on);

						dialog1.setPositiveButton("Close", null);

						dialog1.setMessage(strError);

						dialog1.show();

					}

					else

					{

						Toast.makeText(ManageBookingsActivity.this, "Delete Booking successfully.", Toast.LENGTH_SHORT).show();

						ShowData(); // reload data again

					}
					
					
				}
				
			});
			dialog.setNegativeButton("NO", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					Toast.makeText(ManageBookingsActivity.this, "Thank you Tutor!...Then Confirm the Bookings", Toast.LENGTH_LONG).show();
					
				}
			});

			dialog.show();

		}       

		return true;

	}

	public class ImageAdapter extends BaseAdapter

	{

		private Context context;

		public ImageAdapter(Context c)

		{

			// TODO Auto-generated method stub

			context = c;

		}

		public int getCount() {

			// TODO Auto-generated method stub

			return MyArrList.size();

		}

		public Object getItem(int position) {

			// TODO Auto-generated method stub

			return position;

		}

		public long getItemId(int position) {

			// TODO Auto-generated method stub

			return position;

		}

		public View getView(final int position, View convertView, ViewGroup parent) {

			// TODO Auto-generated method stub

			LayoutInflater inflater = (LayoutInflater) context

					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {

				convertView = inflater.inflate(R.layout.manage_bookings_column, null);

			}

			// ColMemberID

			TextView txtFullNames = (TextView) convertView.findViewById(R.id.ColNames);

			txtFullNames.setPadding(10, 0, 0, 0);

			txtFullNames.setText(MyArrList.get(position).get("name") +".");



			// R.id.ColName

			TextView txtNumHourseBooked = (TextView) convertView.findViewById(R.id.ColNumberOfHours);

			txtNumHourseBooked.setPadding(5, 0, 0, 0);

			txtNumHourseBooked.setText(MyArrList.get(position).get("numberOfHoursBooked"));

			// R.id.ColTel

			TextView txtSubject = (TextView) convertView.findViewById(R.id.ColSubject);

			txtSubject.setPadding(5, 0, 0, 0);

			txtSubject.setText(MyArrList.get(position).get("subjects"));

			return convertView;

		}

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

	@Override

	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.manage_booking, menu);

		return true;

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
	
		int id = item.getItemId();

        
        if (id == R.id.action_exit) {
        	
			System.exit(0);
        	
            return true;
        }
      if (id == R.id.action_help) {
    	  

    	  Intent intent = new Intent(ManageBookingsActivity.this,ForgotPasswordAdminActivity.class);
    	  
			startActivity(intent);
			
            return true;
        }
        if (id == R.id.action_settings) {
        	
            return true;
        }
       
        return super.onOptionsItemSelected(item);
	}



}