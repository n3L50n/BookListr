package com.adventurekit.node_coyote.booklistr;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    public static final String GOOGLE_BOOKS_REQUEST_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    // Variable to store the adapter
    private BookAdapter mAdapter;

    private String mTotal;
    private TextView mEmptyStateTextView;
    private ListView mBookListView;
    private boolean mHasBeenClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHasBeenClicked = false;
        //Find a reference to the {@link ListView}
        mBookListView = (ListView) findViewById(R.id.book_list);

        //Create a new {@link ArrayAdapter} of Books and set it to the adapter
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        mBookListView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(0, null, MainActivity.this);
            mEmptyStateTextView.setText(R.string.empty_view_text);
        } else {
            mBookListView.setEmptyView(mEmptyStateTextView);
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        // Set a click listener on the search button
        ImageButton searchIcon = (ImageButton) findViewById(R.id.search_button);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaderManager loaderManager = getLoaderManager();

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    loaderManager.restartLoader(1, null, MainActivity.this);
                    loaderManager.initLoader(1, null, MainActivity.this);
                    mEmptyStateTextView.setText(R.string.empty_view_text_nothing);
                } else {
                    loaderManager.initLoader(1, null, MainActivity.this);
                    mBookListView.setEmptyView(mEmptyStateTextView);
                    View loadingIndicator = findViewById(R.id.loading_indicator);
                    loadingIndicator.setVisibility(View.GONE);
                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                }
                Log.v("Search query is ", mTotal);
                mHasBeenClicked = true;
            }
        });

    }


    public String getRequestUrl(String baseUrl) {

        // Begin with Google Books basic url
        baseUrl = GOOGLE_BOOKS_REQUEST_BASE_URL;

        Log.v("TAG", "Search query before loop is " + baseUrl);

        // Grab Search View
        EditText searchInput = (EditText) findViewById(R.id.search_bar);
        String searchQuery = searchInput.getText().toString();
        if (searchQuery.contains("\\s")) {
            for (int i = 0; i < searchQuery.length(); i++) {
                searchQuery.replaceAll("\\s","+");
            }
        }

        Log.v("TAG", "Search query after loop is " + baseUrl);
        mTotal = GOOGLE_BOOKS_REQUEST_BASE_URL + searchQuery + "&maxResults=10";
        Log.v("TAG", "Search total  is " + mTotal);

        return mTotal;
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        Log.v("TAG", "onCreateLoader total is " + mTotal);
        mAdapter.clear();
        getRequestUrl(mTotal);
        return new BookLoader(MainActivity.this, mTotal);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> bookData) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Clear adapter of previous Book data
        mAdapter.clear();

        getRequestUrl(mTotal);

        if (bookData != null && !bookData.isEmpty()) {
            mAdapter.addAll(bookData);
        }
        if (bookData == null && mHasBeenClicked){
                mEmptyStateTextView.setText(R.string.empty_view_text_no_results);
            }
        Log.v("TAG", "onLoadFinished is " + mTotal);

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

        mAdapter.clear();
        Log.v("TAG", "onLoadReset is " + mTotal);

    }
}
