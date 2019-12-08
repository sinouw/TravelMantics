package ke.com.yass.travelmantics;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.DealViewHolder> {

	ArrayList<TravelDeal> deals;

	private FirebaseDatabase mFirebaseDatabase;

	private DatabaseReference mDatabaseRef;

	private ChildEventListener mChildEventListener;

	public DealAdapter() {

		mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;

		mDatabaseRef = FirebaseUtil.mDatabaseRef;

		deals = FirebaseUtil.mDeals;

		mChildEventListener =  new ChildEventListener() {

			@Override
			public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

				TravelDeal td =  dataSnapshot.getValue(TravelDeal.class);

				Log.i("DealsAdapter", td.getTitle());

				td.setId(dataSnapshot.getKey());

				deals.add(td);

				notifyItemInserted(deals.size() - 1);

			}

			@Override
			public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

			}

			@Override
			public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

			}

			@Override
			public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		};

		mDatabaseRef.addChildEventListener(mChildEventListener);
	}

	@NonNull
	@Override
	public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		Context context = parent.getContext();

		View itemView = LayoutInflater.from(context).inflate(R.layout.deal_row, parent, false);

		return  new DealViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {
		TravelDeal deal = deals.get(position);

		holder.bind(deal);
	}

	@Override
	public int getItemCount() {
		return deals.size();
	}

	public class DealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

		TextView textTitle, textDescription, textPrice;

		public DealViewHolder(@NonNull View itemView) {
			super(itemView);

			textTitle = itemView.findViewById(R.id.text_deal_title);

			textDescription = itemView.findViewById(R.id.text_deal_description);

			textPrice = itemView.findViewById(R.id.text_deal_price);

			itemView.setOnClickListener(this);


		}

		public void bind(TravelDeal deal) {
			textTitle.setText(deal.getTitle());

			textPrice.setText("TND "+ deal.getPrice());

			textDescription.setText(deal.getDescription());
		}

		@Override
		public void onClick(View v) {
			int position = getAdapterPosition();

			TravelDeal selectedTravelDeal = deals.get(position);

			Intent intent = new Intent(v.getContext(), DealActivity.class);

			intent.putExtra("Deal", selectedTravelDeal);

			v.getContext().startActivity(intent);

		}
	}
}
