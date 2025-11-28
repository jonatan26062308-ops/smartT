package or.school.android.navigationdrawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AddTripFragment extends Fragment {

    private EditText tripTitleEditText, destinationEditText, startDateEditText, endDateEditText, notesEditText;
    private Button saveTripButton;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        initializeViews(view);

        // Set up button click listener
        saveTripButton.setOnClickListener(v -> saveTrip());

        return view;
    }

    private void initializeViews(View view) {
        tripTitleEditText = view.findViewById(R.id.editTextTripTitle);
        destinationEditText = view.findViewById(R.id.editTextDestination);
        startDateEditText = view.findViewById(R.id.editTextStartDate);
        endDateEditText = view.findViewById(R.id.editTextEndDate);
        notesEditText = view.findViewById(R.id.editTextNotes);
        saveTripButton = view.findViewById(R.id.buttonSaveTrip);
    }

    private void saveTrip() {
        // Get input values
        String title = tripTitleEditText.getText().toString().trim();
        String destination = destinationEditText.getText().toString().trim();
        String startDate = startDateEditText.getText().toString().trim();
        String endDate = endDateEditText.getText().toString().trim();
        String notes = notesEditText.getText().toString().trim();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Basic validation
        if (title.isEmpty() || destination.isEmpty()) {
            Toast.makeText(getContext(), "Please fill at least title and destination", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create trip data
        Map<String, Object> trip = new HashMap<>();
        trip.put("title", title);
        trip.put("destination", destination);
        trip.put("startDate", startDate);
        trip.put("endDate", endDate);
        trip.put("notes", notes);
        trip.put("userId", userId);
        trip.put("createdAt", System.currentTimeMillis());

        // Save to Firestore
        db.collection("trips")
                .add(trip)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getContext(), "Trip saved successfully!", Toast.LENGTH_SHORT).show();
                    clearForm();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error saving trip: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void clearForm() {
        tripTitleEditText.setText("");
        destinationEditText.setText("");
        startDateEditText.setText("");
        endDateEditText.setText("");
        notesEditText.setText("");
    }
}