package com.adventurekit.node_coyote.booklistr;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by node_coyote on 4/2/17.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String mUrl;

    public BookLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    public List<Book> loadInBackground() {

        if (mUrl == null){
            return null;
        }

        List<Book> books = QueryUtility.fetchBookData(mUrl);

        return books;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }
}
