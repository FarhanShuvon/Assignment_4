package com.example.myapplication;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Expandable extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private List<String> groupData;
    private HashMap<String, List<String>> childData;
    private int lastExpandedGroup = -1; // Tracks the last expanded group for auto-collapse

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);

        // Apply edge-to-edge insets for proper layout adjustment
        applyEdgeToEdgeInsets();

        // Initialize views and data
        initializeViews();
        initializeData();

        // Set up the expandable list view
        setupExpandableListView();
    }

    /**
     * Applies edge-to-edge insets for modern layouts.
     */
    private void applyEdgeToEdgeInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Initializes views in the layout.
     */
    private void initializeViews() {
        expandableListView = findViewById(R.id.expandableListView);
    }

    /**
     * Populates the group and child data for the expandable list view.
     */
    private void initializeData() {
        // Group titles (headers) and child items
        String[] headers = getResources().getStringArray(R.array.fruit_groups);
        String[][] children = {
                getResources().getStringArray(R.array.citrus_fruits),
                getResources().getStringArray(R.array.berry_fruits),
                getResources().getStringArray(R.array.tropical_fruits)
        };

        // Initialize group and child data
        groupData = new ArrayList<>();
        childData = new HashMap<>();

        for (int i = 0; i < headers.length; i++) {
            groupData.add(headers[i]); // Add group title
            List<String> childList = new ArrayList<>();
            for (String child : children[i]) {
                childList.add(child); // Add child items to the corresponding group
            }
            childData.put(headers[i], childList);
        }
    }

    /**
     * Sets up the expandable list view with adapter and listeners.
     */
    private void setupExpandableListView() {
        // Create and set the adapter
        ExpandableListAdapter adapter = new com.example.myapplication.adapter.ExpandableListAdapter(this, groupData, childData);
        expandableListView.setAdapter(adapter);

        // Set listeners for group and child interactions
        setupListeners();
    }

    /**
     * Sets up listeners for group and child click events in the expandable list view.
     */
    private void setupListeners() {
        // Group click listener
        expandableListView.setOnGroupClickListener((parent, view, groupPosition, id) -> {
            String groupText = groupData.get(groupPosition);
            showToast("Group: " + groupText);
            return false;
        });

        // Group collapse listener
        expandableListView.setOnGroupCollapseListener(groupPosition -> {
            String groupText = groupData.get(groupPosition);
            showToast(groupText + " collapsed");
        });

        // Child click listener
        expandableListView.setOnChildClickListener((parent, view, groupPosition, childPosition, id) -> {
            String childText = childData.get(groupData.get(groupPosition)).get(childPosition);
            showToast("Selected: " + childText);
            return false;
        });

        // Group expand listener with auto-collapse for other groups
        expandableListView.setOnGroupExpandListener(groupPosition -> {
            if (lastExpandedGroup != -1 && lastExpandedGroup != groupPosition) {
                expandableListView.collapseGroup(lastExpandedGroup); // Collapse previously expanded group
            }
            lastExpandedGroup = groupPosition;
        });
    }

    /**
     * Displays a toast message.
     *
     * @param message The message to display.
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
