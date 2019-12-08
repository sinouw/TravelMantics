package ke.com.yass.travelmantics;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {
	public static FirebaseDatabase mFirebaseDatabase;

	public static DatabaseReference mDatabaseRef;

	private static FirebaseUtil firebaseUtil;

	public static FirebaseAuth firebaseAuth;

	public static FirebaseAuth.AuthStateListener firebaseAuthListener;

	public static ArrayList<TravelDeal> mDeals;

	private static Activity caller;

	private static final int RC_SIGN_IN = 123;

	private FirebaseUtil() {
	}

	public static void openFbReference(String ref, final Activity callerActivity) {

		if (firebaseUtil == null) {
			firebaseUtil = new FirebaseUtil();

			mFirebaseDatabase = FirebaseDatabase.getInstance();

			firebaseAuth = FirebaseAuth.getInstance();

			caller = callerActivity;

			firebaseAuthListener = new FirebaseAuth.AuthStateListener() {

				@Override
				public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

					if (firebaseAuth.getCurrentUser() == null) {
						FirebaseUtil.signIn();
					}
				}
			};

		}
		mDeals = new ArrayList<TravelDeal>();

		mDatabaseRef = mFirebaseDatabase.getReference().child(ref);

	}

	public static void attachListener() {
		firebaseAuth.addAuthStateListener(firebaseAuthListener);
	}

	public static void detachListener() {
		firebaseAuth.removeAuthStateListener(firebaseAuthListener);
	}

	private static void signIn() {
		List<AuthUI.IdpConfig> providers = Arrays.asList(
				new AuthUI.IdpConfig.EmailBuilder().build(),
				new AuthUI.IdpConfig.GoogleBuilder().build());


		// Create and launch sign-in intent
		caller.startActivityForResult(
				AuthUI.getInstance()
						.createSignInIntentBuilder()
						.setAvailableProviders(providers)
						.build(),
				RC_SIGN_IN);
	}
}
