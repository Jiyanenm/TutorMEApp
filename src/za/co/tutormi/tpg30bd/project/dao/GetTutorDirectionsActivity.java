package za.co.tutormi.tpg30bd.project.dao;

import com.example.tutorme.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

@SuppressLint("NewApi")
public class GetTutorDirectionsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_tutor_directions);
		
		
		 getActionBar().setHomeButtonEnabled(true);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
		 
		 setTitle("Welcome Tutor");
		 getActionBar().setIcon(R.drawable.tutorme);
	}
}
