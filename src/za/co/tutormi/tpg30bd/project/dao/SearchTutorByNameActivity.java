package za.co.tutormi.tpg30bd.project.dao;


import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import javax.security.auth.Subject;

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

import android.os.Bundle;
import android.os.IInterface;

import android.os.StrictMode;
import android.provider.Settings.System;

import android.annotation.SuppressLint;

import android.app.Activity;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.util.Log;

import android.view.MenuItem;
import android.view.View;

import android.view.Menu;

import android.widget.AdapterView;
import android.widget.Toast;

import android.widget.AdapterView.OnItemClickListener;

import android.widget.Button;

import android.widget.EditText;

import android.widget.ListView;

import android.widget.SimpleAdapter;

public class SearchTutorByNameActivity extends Activity {

	
	@SuppressLint("NewApi")

	@Override

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_search_tutor_by_name);

		// Permission StrictMode

		if (android.os.Build.VERSION.SDK_INT > 9) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

			StrictMode.setThreadPolicy(policy);

		}

		final Button btn1 = (Button) findViewById(R.id.btnSearch);

		// Perform action on click

		btn1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				SearchData();

			}

		});

	}

	public void SearchData()

	{

		// listView1

		final ListView lisView1 = (ListView)findViewById(R.id.listView1);  



		// editText1

		final EditText inputText = (EditText)findViewById(R.id.editSearch);

		/**

		 * [{"CustomerID":"C001","Name":"Win Weerachai","Email":"win.weerachai@thaicreate.com" ,"CountryCode":"TH","Budget":"1000000","Used":"600000"},

		 * {"CustomerID":"C002","Name":"John Smith","Email":"john.smith@thaicreate.com" ,"CountryCode":"EN","Budget":"2000000","Used":"800000"},

		 * {"CustomerID":"C003","Name":"Jame Born","Email":"jame.born@thaicreate.com" ,"CountryCode":"US","Budget":"3000000","Used":"600000"},

		 * {"CustomerID":"C004","Name":"Chalee Angel","Email":"chalee.angel@thaicreate.com" ,"CountryCode":"US","Budget":"4000000","Used":"100000"}]

		 */

		String url = "http://tutormi.co.za/tutorme/getJSON.php";

		// Paste Parameters

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("txtKeyword", inputText.getText().toString()));
		

		try {

			JSONArray data = new JSONArray(getJSONUrl(url,params));

			final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();

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

			
			
			SimpleAdapter sAdap;

			sAdap = new SimpleAdapter(SearchTutorByNameActivity.this, MyArrList, R.layout.activity_column_search,

					new String[] {"MemberID", "firstname", "subject"}, new int[] {R.id.ColMemberID, R.id.ColfirstName, R.id.Colsubject});     

			lisView1.setAdapter(sAdap);

			final AlertDialog.Builder viewDetail = new AlertDialog.Builder(this);
			final AlertDialog.Builder bookingDialog = new AlertDialog.Builder(this);

			// OnClick Item
			
			lisView1.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> myAdapter, View myView,

						int position, long mylng) {

					final String strMemberID = MyArrList.get(position).get("MemberID")

							.toString();

					final String firstName = MyArrList.get(position).get("firstname")

							.toString();

					final String secondName = MyArrList.get(position).get("secondName")

							.toString();

					final String lastName = MyArrList.get(position).get("lastName")

							.toString();

					final String gender = MyArrList.get(position).get("gender")

							.toString();

					final String subject = MyArrList.get(position).get("subject")

							.toString();

					final String tell = MyArrList.get(position).get("tell")

							.toString();

					final String email = MyArrList.get(position).get("email")

							.toString();

					final String rate = MyArrList.get(position).get("rate")

							.toString();

					final String username = MyArrList.get(position).get("username")

							.toString();

					final String password = MyArrList.get(position).get("password")

							.toString();

					viewDetail.setIcon(android.R.drawable.btn_star_big_on);

					viewDetail.setTitle("Tutor Details");

					viewDetail.setMessage("MemberID : " + strMemberID + "\n"

					+ "First Name: " + firstName + "\n"

					+ "Second Name : " + secondName + "\n"

					+ "Surname Name : " + lastName + "\n"

					+ "Subject : " + subject + "\n"

					+ "Rate Per Hour : " + rate);
		
					viewDetail.setPositiveButton("OK",new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog,int which) {

							// TODO Auto-generated method stub

							bookingDialog.setTitle("Book Selected Tutor");
							bookingDialog.setMessage("Do you want to book the Selected Tutor?");
							bookingDialog.setIcon(android.R.drawable.btn_star_big_on);
							bookingDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub

									Intent intent = new Intent(SearchTutorByNameActivity.this,MakePaymentActivity.class);

									intent.putExtra("ID", Integer.parseInt(strMemberID));
									intent.putExtra("FIRSTNAME", firstName);
									intent.putExtra("SECONDNAME", secondName);
									intent.putExtra("LASTNAME", lastName);
									intent.putExtra("RATE", Double.parseDouble(rate));
									intent.putExtra("SUBJECT", subject);
									intent.putExtra("EMAIL", email);
									
									startActivity(intent);
					
								}
							});
							bookingDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub

									dialog.dismiss();

									Toast.makeText(SearchTutorByNameActivity.this, "Oopz! OK Try Another Tutor.....", Toast.LENGTH_LONG).show();

								}
							});
							bookingDialog.show();
						}
						
						
					});
					
					viewDetail.show();
				}

			});
			

		} catch (JSONException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

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

				Log.e("Log", "Failed to download file..");

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
		getMenuInflater().inflate(R.menu.search, menu);

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

			final EditText inputText = (EditText)findViewById(R.id.editSearch);

			inputText.setText(null);

			return true;
		}


		if (id == R.id.action_settings) {

			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		
	}


}