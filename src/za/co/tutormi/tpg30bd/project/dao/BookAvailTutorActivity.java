package za.co.tutormi.tpg30bd.project.dao;

import com.example.tutorme.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

@SuppressLint("NewApi") public class BookAvailTutorActivity extends Activity {

private ListView listAvailTutors;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_avail_tutor);
		
		listAvailTutors = (ListView) findViewById(R.id.listViewAvailTutors);
		
		Intent i = new Intent(BookAvailTutorActivity.this,MakePaymentActivity.class);
		startActivity(i);
	}
}
