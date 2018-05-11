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

import com.example.tutorme.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemSelectedListener {

	private ListView listview;
	private ArrayList<HashMap<String, String>> MyArrList;
	private MyArrayAdapter myArrayAdapter;
	int postion = 0;
	String MemberID;
	
	@SuppressLint("NewApi")

	@Override

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// Permission StrictMode

		if (android.os.Build.VERSION.SDK_INT > 9) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

			StrictMode.setThreadPolicy(policy);

		}

		
		listview = (ListView) findViewById(R.id.listView1);
		
		listview.setOnItemSelectedListener(this);
	
		registerForContextMenu(listview);
		
		ShowData();
		

	}
	
	public void ShowData()

	{

		// listView1

		final ListView lisView1 = (ListView)findViewById(R.id.listView1);  

		// keySearch

		EditText strKeySearch = (EditText)findViewById(R.id.txtKeySearch);

		// Disbled Keyboard auto focus

		InputMethodManager imm = (InputMethodManager)getSystemService(

				Context.INPUT_METHOD_SERVICE);

		/**

		 * [{"MemberID":"1","Username":"weerachai","Password":"weerachai@1","Name":"Weerachai Nukitram","Tel":"0819876107","Email":"weerachai@thaicreate.com"},

		 * {"MemberID":"2","Username":"adisorn","Password":"adisorn@2","Name":"Adisorn Bunsong","Tel":"021978032","Email":"adisorn@thaicreate.com"},

		 * {"MemberID":"3","Username":"surachai","Password":"surachai@3","Name":"Surachai Sirisart","Tel":"0876543210","Email":"surachai@thaicreate.com"}]

		 */

		String url = "http://tutormi.co.za/tutorme/showAllData.php";

		// Paste Parameters
		
		Intent intent = getIntent();
		
		 MemberID = intent.getStringExtra("MemberID");
	
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		
		params.add(new BasicNameValuePair("txtKeyword", MemberID));
		
		
		try {

			JSONArray data = new JSONArray(getJSONUrl(url,params));

			
			MyArrList = new ArrayList<HashMap<String, String>>();

			HashMap<String, String> map;

			for(int i = 0; i < data.length(); i++){

				JSONObject c = data.getJSONObject(i);

				map = new HashMap<String, String>();

				map.put("MemberID", c.getString("MemberID"));

				map.put("firstname", c.getString("firstname"));

				map.put("secondName", c.getString("secondName"));

				map.put("lastName", c.getString("lastName"));

				map.put("gender", c.getString("gender"));

				map.put("subject", c.getString("subject"));
				
				map.put("tell", c.getString("tell"));
				
				map.put("email", c.getString("email"));

				map.put("rate", c.getString("rate"));

				map.put("username", c.getString("username"));

				map.put("password", c.getString("password"));

				MyArrList.add(map);

			}

			listview.setAdapter(new MyArrayAdapter(this));
		} catch (JSONException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

	public class MyArrayAdapter extends BaseAdapter

	{

		private Context context;

		public MyArrayAdapter(Context c)

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

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {

				convertView = inflater.inflate(R.layout.activity_column,null);

			}
			// ColMemberID

			TextView txtMemberID = (TextView) convertView.findViewById(R.id.ColMemberID);
			

			txtMemberID.setPadding(10, 0, 0, 0);

			txtMemberID.setText("Your Member ID : " + MyArrList.get(position).get("MemberID"));
			
			
			
			// R.id.ColName

			TextView txtFirstName = (TextView) convertView.findViewById(R.id.ColFirstName);

			txtFirstName.setPadding(5, 0, 0, 0);

			txtFirstName.setText("First Name : " + MyArrList.get(position).get("firstname"));

			// R.id.ColTel
			
			TextView txtSecondName = (TextView) convertView.findViewById(R.id.ColSecondName);

			txtSecondName.setPadding(5, 0, 0, 0);

			txtSecondName.setText("Second Name : " + MyArrList.get(position).get("secondName"));
		
			TextView txtLastName = (TextView) convertView.findViewById(R.id.ColLasName);

			txtLastName.setPadding(5, 0, 0, 0);

			txtLastName.setText("Last Name : " + MyArrList.get(position).get("lastName"));
			
			TextView txtSubject = (TextView) convertView.findViewById(R.id.ColSubject);

			txtSubject.setPadding(5, 0, 0, 0);

			txtSubject.setText("Tutor Subjects : " + MyArrList.get(position).get("subject"));
			
			TextView txtGender = (TextView) convertView.findViewById(R.id.ColGender);

			txtGender.setPadding(5, 0, 0, 0);

			txtGender.setText("Gender : " +MyArrList.get(position).get("gender"));
			
			TextView txtTell = (TextView) convertView.findViewById(R.id.ColTel);

			txtTell.setPadding(5, 0, 0, 0);

			txtTell.setText("Tell/Cell : " + MyArrList.get(position).get("tell"));
			
			TextView txtEmail = (TextView) convertView.findViewById(R.id.ColEmail);

			txtEmail.setPadding(5, 0, 0, 0);

			txtEmail.setText("E-Mail : " + MyArrList.get(position).get("email"));
			
			TextView txtRate = (TextView) convertView.findViewById(R.id.ColRate);

			txtRate.setPadding(5, 0, 0, 0);

			txtRate.setText("Rate Per Hour : " + MyArrList.get(position).get("rate"));
			
			TextView txtUsername = (TextView) convertView.findViewById(R.id.ColUsername);

			txtUsername.setPadding(5, 0, 0, 0);

			txtUsername.setText("Username : " +MyArrList.get(position).get("username"));
			
			TextView txtPassword = (TextView) convertView.findViewById(R.id.ColPassword);

			txtPassword.setPadding(5, 0, 0, 0);

			txtPassword.setText("Password : ************");
			
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
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuInflater menuInflater = new MenuInflater(this);
		menuInflater.inflate(R.menu.activity_main, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo itemInformation = null;

		int id = item.getItemId();
		
		
		final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
		final AlertDialog.Builder dialog1 = new AlertDialog.Builder(MainActivity.this);

		
		
		if (id == R.id.action_edit) {
			
			Intent intent = new Intent(MainActivity.this,UpdateTutorProfileActivity.class);
			intent.putExtra("MemberID", MemberID);
			startActivity(intent);
		
			
			return true;
		}
		if(id == R.id.action_view)
		{
			
		}
		
		
		if (id == R.id.action_delete) {
		
			dialog.setTitle("Delete?");

			dialog.setMessage("Are you sure you want to Delete your profile, as a Tutor?");

			dialog.setPositiveButton("Yes", new AlertDialog.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					

					// Request to Delete data.

					String url = "http://tutormi.co.za/tutorme/deleteData.php";

					List<NameValuePair> params = new ArrayList<NameValuePair>();

					params.add(new BasicNameValuePair("MemberID", MyArrList.get(postion).get("MemberID")));
				
					
					String resultServer  = getJSONUrl(url,params);

					/** Get result delete data from Server (Return the JSON Code)

					 * StatusID = ? [0=Failed,1=Complete]

					 * Error    = ? [On case error return custom error message]

					 *

					 * Eg Login Failed = {"StatusID":"0","Error":"Cannot delete data!"}

					 * Eg Login Complete = {"StatusID":"1","Error":""}

					 */

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
						Toast.makeText(MainActivity.this, "Data Not Deleted Successfully!", Toast.LENGTH_SHORT).show();

					}

					else

					{

						Toast.makeText(MainActivity.this, "Profile Deleted Successfully Bye!" + "\nYou Can Register Again", Toast.LENGTH_SHORT).show();

						
						//ShowData(); // reload data again
						
						finish();
					}
					
				}
				
			});
			
			dialog.setNegativeButton("No", new AlertDialog.OnClickListener()
			{

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					
					Toast.makeText(MainActivity.this, "Thank you for Remaining as Tutor...." +"\nKeep Sharing Information", Toast.LENGTH_SHORT).show();
				}
				
			});
			dialog.show();
			return true;
		}

		
		
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		int id = item.getItemId();

		if(id == R.id.action_managebookings)
		{
			
			Intent intent = new Intent(MainActivity.this,ManageBookingsActivity.class);
			intent.putExtra("MemberID", MemberID);
			startActivity(intent);
		}
		if(id == R.id.action_help)
		{
			Intent intent = new Intent(MainActivity.this,ForgotPasswordAdminActivity.class);
			
			startActivity(intent);
		}
		if(id == R.id.action_settings)
		{
			
		}
		return super.onOptionsItemSelected(item);
	}
	

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
		
		ShowData();
		
		ArrayList<HashMap<String, String>> MyArrList;
		
		listview = (ListView) findViewById(R.id.listView1);
		
		myArrayAdapter = new MyArrayAdapter(this);
		
		//listview.setAdapter(new MyArrayAdapter(this));

		
		//registerForContextMenu(listview);
		
		
		
	}

}