package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Button btnExpandable;

    private final String[] items = {"Apple", "Banana", "Grape", "Orange", "Pineapple"};
    private final Integer[] imgIds = {R.drawable.apple, R.drawable.banana, R.drawable.grape, R.drawable.orange, R.drawable.pineapple};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        initializeViews();

        // Set up button to navigate to the Expandable activity
        setupButtonClickListener();

        // Set up custom adapter for the ListView
        setupListView();
    }

    /**
     * Initializes views.
     */
    private void initializeViews() {
        listView = findViewById(R.id.simpleListView);
        btnExpandable = findViewById(R.id.btn_exp);
    }

    /**
     * Sets up the click listener for the button to navigate to the Expandable activity.
     */
    private void setupButtonClickListener() {
        btnExpandable.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Expandable.class);
            startActivity(intent);
        });
    }

    /**
     * Sets up the ListView with a custom adapter and item click listener.
     */
    private void setupListView() {
        CustomAdapter adapter = new CustomAdapter();
        listView.setAdapter(adapter);

        // Handle item clicks
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = items[position];
            Toast.makeText(MainActivity.this, "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Custom adapter for the ListView to display images and text.
     */
    private class CustomAdapter extends ArrayAdapter<String> {

        public CustomAdapter() {
            super(MainActivity.this, R.layout.list_item, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                // Inflate custom list item layout
                convertView = getLayoutInflater().inflate(R.layout.list_item, parent, false);

                // Initialize ViewHolder
                holder = new ViewHolder();
                holder.textView = convertView.findViewById(R.id.textview);
                holder.imageView = convertView.findViewById(R.id.imageView);

                // Store ViewHolder in tag for reuse
                convertView.setTag(holder);
            } else {
                // Reuse existing ViewHolder
                holder = (ViewHolder) convertView.getTag();
            }

            // Set text and image for the current item
            holder.textView.setText(items[position]);
            holder.imageView.setImageResource(imgIds[position]);

            return convertView;
        }

        /**
         * ViewHolder pattern to optimize ListView performance by avoiding redundant view lookups.
         */
        private class ViewHolder {
            TextView textView;
            ImageView imageView;
        }
    }
}
