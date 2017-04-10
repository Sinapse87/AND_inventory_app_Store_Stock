package com.example.android.storestocks;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.android.storestocks.data.InventoryDbHelper;
import com.example.android.storestocks.data.StockItem;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getCanonicalName();
    InventoryDbHelper dbHelper;
    StockCursorAdapter adapter;
    int lastVisibleItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new InventoryDbHelper(this);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Details.class);
                startActivity(intent);
            }
        });


        /**
         * To list the items from the stock during the scrolling section update
         */

        final ListView listView = (ListView) findViewById(R.id.list_view);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        Cursor cursor = dbHelper.readStock();

        adapter = new StockCursorAdapter(this, cursor);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == 0) return;
                final int currentFirstVisibleItem = view.getFirstVisiblePosition();
                if (currentFirstVisibleItem > lastVisibleItem) {
                    fab.show();
                } else if (currentFirstVisibleItem < lastVisibleItem) {
                    fab.hide();
                }
                lastVisibleItem = currentFirstVisibleItem;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    /**
     * Click/button behavior
     */

    @Override
    protected void onResume() {
        super.onResume();
        adapter.swapCursor(dbHelper.readStock());
    }

    public void clickOnViewItem(long id) {
        Intent intent = new Intent(this, Details.class);
        intent.putExtra("itemId", id);
        startActivity(intent);
    }

    public void clickOnSale(long id, int quantity) {
        dbHelper.sellOneItem(id, quantity);
        adapter.swapCursor(dbHelper.readStock());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_dummy_data:
                // add dummy data for testing
                addDummyData();
                adapter.swapCursor(dbHelper.readStock());
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Add data for demo purposes
     */
    private void addDummyData() {
        StockItem boots = new StockItem(
                "Boots",
                "RM 100",
                45,
                "Best Market",
                "+603 2222 3111",
                "bestmarket@gmail.com",
                "android.resource://com.example.android.storestocks/drawable/boots");
        dbHelper.insertItem(boots);

        StockItem jacket = new StockItem(
                "Jacket",
                "RM 250",
                24,
                "Best Market",
                "+603 2222 3111",
                "bestmarket@gmail.com",
                "android.resource://com.example.android.storestocks/drawable/jacket");
        dbHelper.insertItem(jacket);

        StockItem jeans = new StockItem(
                "Jeans",
                "RM 120",
                74,
                "Best Market",
                "+603 2222 3111",
                "bestmarket@gmail.com",
                "android.resource://com.example.android.storestocks/drawable/jeans");
        dbHelper.insertItem(jeans);

        StockItem ladiesSandal = new StockItem(
                "Ladies Sandal",
                "RM 40",
                44,
                "Best Market",
                "+603 2222 3111",
                "bestmarket@gmail.com",
                "android.resource://com.example.android.storestocks/drawable/ladiesandal");
        dbHelper.insertItem(ladiesSandal);

        StockItem longSleeves = new StockItem(
                "Long Sleeves",
                "RM 240",
                34,
                "Best Market",
                "+603 2222 3111",
                "bestmarket@gmail.com",
                "android.resource://com.example.android.storestocks/drawable/longsleeves");
        dbHelper.insertItem(longSleeves);

        StockItem sandals = new StockItem(
                "Sandals",
                "RM 30",
                26,
                "Best Market",
                "+603 2222 3111",
                "bestmarket@gmail.com",
                "android.resource://com.example.android.storestocks/drawable/sandals");
        dbHelper.insertItem(sandals);

        StockItem shinepad = new StockItem(
                "Shinepad",
                "RM 45",
                54,
                "Best Market",
                "+603 2222 3111",
                "bestmarket@gmail.com",
                "android.resource://com.example.android.storestocks/drawable/shinepad");
        dbHelper.insertItem(shinepad);

        StockItem sleeveless = new StockItem(
                "Sleeveless",
                "RM 120",
                12,
                "Best Market",
                "+603 2222 3111",
                "bestmarket@gmail.com",
                "android.resource://com.example.android.storestocks/drawable/sleeveless");
        dbHelper.insertItem(sleeveless);

        StockItem sunglasses = new StockItem(
                "Sun Glasses",
                "RM 400",
                62,
                "Best Market",
                "+603 2222 3111",
                "bestmarket@gmail.com",
                "android.resource://com.example.android.storestocks/drawable/sunglasses");
        dbHelper.insertItem(sunglasses);

        StockItem tshirt = new StockItem(
                "T-Shirt",
                "RM 60",
                22,
                "Best Market",
                "+603 2222 3111",
                "bestmarket@gmail.com",
                "android.resource://com.example.android.storestocks/drawable/tshirt");
        dbHelper.insertItem(tshirt);
    }
}
