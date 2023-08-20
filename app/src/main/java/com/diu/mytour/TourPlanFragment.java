package com.diu.mytour;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TourPlanFragment extends Fragment {

    RecyclerView planRecyclerView;
    PlanAdapter planAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Plan> planList;
    Query query;
    private SearchView srcvw;
    // private Button btnsearch;
    String spinnerData;
    private boolean status; // Member variable to store the status data

    public TourPlanFragment() {
        // Required empty public constructor
    }

    // Modified newInstance method to accept the status data
    public static TourPlanFragment newInstance(boolean status) {
        TourPlanFragment fragment = new TourPlanFragment();
        fragment.status = status; // Store the status data in the member variable
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_tour_plan, container, false);
        planRecyclerView = fragmentView.findViewById(R.id.planRV);
        planRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        planRecyclerView.setHasFixedSize(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Plans");
        query = databaseReference.orderByChild("planStatus").equalTo(status); // Use the status data in the query

        srcvw = fragmentView.findViewById(R.id.srcvw);

        srcvw.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch();
                return true;
            }
        });

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                planList = new ArrayList<>();
                for (DataSnapshot plansnap : dataSnapshot.getChildren()) {
                    Plan plan = plansnap.getValue(Plan.class);
                    planList.add(0, plan);
                }
                planAdapter = new PlanAdapter(getActivity(), planList);
                planRecyclerView.setAdapter(planAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error if needed
            }
        });
    }

    private void performSearch() {
        String searchQuery = srcvw.getQuery().toString().trim().toLowerCase();

        Query searchQueryRef = databaseReference.orderByChild("planCaption")
                .startAt(searchQuery)
                .endAt(searchQuery + "\uf8ff");

        searchQueryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                planList.clear();
                for (DataSnapshot plansnap : dataSnapshot.getChildren()) {
                    Plan plan = plansnap.getValue(Plan.class);
                    // Check if the searchQuery matches in either planCaption or spinnerData
                    if (plan.getPlanCaption().toLowerCase().contains(searchQuery) ||
                            plan.getSpinnerData().toLowerCase().contains(searchQuery)) {
                        planList.add(plan);
                    }
                }
                planAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}