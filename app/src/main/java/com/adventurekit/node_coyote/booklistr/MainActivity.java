package com.adventurekit.node_coyote.booklistr;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    public static final String GOOGLE_BOOKS_REQUEST_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1";

    // The base url to build a query upon
    //public static final String GOOGLE_BOOKS_REQUEST_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    // TODO do I need an API key?
    // API KEY
    public static final String API_KEY = "AIzaSyCpdjPykoL3HtKyceMX5Jf8nd-q0f92iMs";

    // Variable to store the adapter
    public BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find a reference to the {@link ListView}
        ListView bookListView = (ListView) findViewById(R.id.book_list);

        //Create a new {@link ArrayAdapter} of Books
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        bookListView.setAdapter(mAdapter);

    }


//    public String getRequestUrl(String baseUrl) {
//
//        // Begin with Google Books basic url
//        baseUrl = GOOGLE_BOOKS_REQUEST_BASE_URL;
//
//        //TODO for each word entered in search, split between spaces
//
//        // Grab Search View
//        EditText searchInput = (EditText) findViewById(R.id.search_bar);
//        String searchQuery = searchInput.getText().toString();
//        String requestUrl = baseUrl + searchQuery + "&key=" + API_KEY;
//
//        return requestUrl;
//    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(MainActivity.this, GOOGLE_BOOKS_REQUEST_BASE_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> bookData) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mAdapter.clear();

        if (bookData != null && !bookData.isEmpty()){
            mAdapter.addAll(bookData);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}
