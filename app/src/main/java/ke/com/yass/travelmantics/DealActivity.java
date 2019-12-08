package ke.com.yass.travelmantics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DealActivity extends AppCompatActivity {

	private FirebaseDatabase mFirebaseDatabase;

	private DatabaseReference mDatabaseRef;

	EditText title, price, description;

	TravelDeal deal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert);

		// instance of firebase
		FirebaseUtil.openFbReference("traveldeals", this);

		mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;

		mDatabaseRef = FirebaseUtil.mDatabaseRef;

		title = findViewById(R.id.edit_title_text);

		price = findViewById(R.id.edit_price_text);

		description = findViewById(R.id.edit_description_text);

		Intent intent = getIntent();

		TravelDeal deal = (TravelDeal) intent.getSerializableExtra("Deal");

		if (deal == null) {
			deal = new TravelDeal();
		}

		this.deal = deal;

		title.setText(deal.getTitle());
		description.setText(deal.getDescription());
		price.setText(deal.getPrice());

	}

	// menu


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()){

			case R.id.item_save_menu:
				saveDeal();
				Toast.makeText(this, "Deal saved", Toast.LENGTH_LONG).show();
				cleanFields();
				backToList();
				return true;
			case R.id.item_delete_menu:
				deleteDeal();
				Toast.makeText(this, "Deal deleted", Toast.LENGTH_LONG).show();
				backToList();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void cleanFields() {
		title.setText("");
		price.setText("");
		description.setText("");
		title.requestFocus();
	}

	private void saveDeal() {
		deal.setTitle(title.getText().toString());

		deal.setPrice(price.getText().toString());

		deal.setDescription(description.getText().toString());

		//String title, String price, String description, String imageUrl

		//TravelDeal deal = new TravelDeal(dealTitle, dealPrice, dealDescription, "");
		if (deal.getId() == null) {
			mDatabaseRef.push().setValue(deal);
		} else {
			mDatabaseRef.child(deal.getId()).setValue(deal);
		}

	}

	private void deleteDeal() {
		if (deal == null) {
			Toast.makeText(this, "Please save before deleting", Toast.LENGTH_SHORT).show();
			return;

		} else {
			mDatabaseRef.child(deal.getId()).removeValue();
		}
	}

	private void backToList(){
		Intent intent = new Intent(this, ListActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.save_menu, menu);

		return true;
	}
}
