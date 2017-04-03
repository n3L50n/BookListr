package com.adventurekit.node_coyote.booklistr;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    // The base url to build a query upon
    public static final String GOOGLE_BOOKS_REQUEST_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    public static final String API_KEY = "AIzaSyCpdjPykoL3HtKyceMX5Jf8nd-q0f92iMs";

    // Variable to store the adapter
    public BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());
    }
    /**
     *
     * @param baseUrl
     * @return
     */
    public String getRequestUrl(String baseUrl){

//        // Begin with Google Books basic url
        baseUrl = GOOGLE_BOOKS_REQUEST_BASE_URL;

        //TODO for each word entered in search, split between spaces
        
//        // Grab Search View
        EditText searchInput = (EditText) findViewById(R.id.search_bar);
        String searchQuery = searchInput.getText().toString();
        String requestUrl = baseUrl + searchQuery + "&key=" + API_KEY;

        return requestUrl;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
