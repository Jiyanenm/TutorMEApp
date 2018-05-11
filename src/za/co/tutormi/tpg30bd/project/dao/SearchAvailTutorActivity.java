package za.co.tutormi.tpg30bd.project.dao;

import java.util.zip.Inflater;

import com.example.tutorme.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.System;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchAvailTutorActivity extends Activity {
	
	private Button btnSearchByNames;
	private Button btnSearchByLocation;
	private Button btnCancelSearching;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_avail_tutor);
		
		btnSearchByLocation = (Button) findViewById(R.id.btnSearchByLocation);
		btnSearchByNames = (Button) findViewById(R.id.btnSearchByNames);
		btnCancelSearching = (Button) findViewById(R.id.btnCancelSearch);
		
		
		
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		
		dialog.setTitle("Hi! Welcome to TutorME!....");
		dialog.setMessage("You Are Very Smart!" + "\n" + "\nFeel Free To Search Your Tutor By GPS-Location Or Names" + "\t"
		 + "\n" + "\nThank You!....");
		dialog.setIcon(android.R.drawable.btn_star_big_on);
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				dialog.dismiss();
				
			}
		});
		dialog.show();
		
		btnSearchByNames.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
	        	Intent i = new Intent(SearchAvailTutorActivity.this,SearchTutorByNameActivity.class);

	        	startActivity(i);
				
			}
		});
		
		btnSearchByLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
		      	
	        	Intent i = new Intent(SearchAvailTutorActivity.this,SearchTutorByLocationActivity.class);

	        	startActivity(i);
				
			}
		});
		
		btnCancelSearching.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				final AlertDialog.Builder dialog = new AlertDialog.Builder(SearchAvailTutorActivity.this);
				
				dialog.setTitle("Cancel Search");
				dialog.setMessage("Ok! Are your sure you want to cancel Searching?");
				dialog.setIcon(android.R.drawable.btn_star_big_on);
				dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						
						Toast.makeText(SearchAvailTutorActivity.this, "Ok Thank you-Bye Bye!", Toast.LENGTH_LONG).show();
						
						dialog.dismiss();
						
						finish();
					}
				});
				
				
				dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						
						Toast.makeText(SearchAvailTutorActivity.this, "Ok! Please-Search Tutor By your Location[Eg: Auckland park] or Names[Eg : 'a']", Toast.LENGTH_LONG).show();
						
						
						dialog.dismiss();
						
						
						
					}
					
				});
				dialog.show();
				
				
			}
		});
		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.search, menu);
		
		return true;
		
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		
		int id = item.getItemId();
		
        if (id == R.id.action_by_name) {
        	
        	

        	
            return true;
        }
        if (id == R.id.action_search_location) {
        	
  
        	
            return true;
        }
        if (id == R.id.action_back) {
        	
        
        	finish();
        	
            return true;
        }
        
        if (id == R.id.action_settings) {
        	
            return true;
        }
       
        return super.onOptionsItemSelected(item);
	}

}
