package com.adventurekit.node_coyote.booklistr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by node_coyote on 4/2/17.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    /**
     *  Constructor for a new BookAdapter item. Use to populate the UI
     * @param context Given context
     * @param books A list of book items to fill a list view
     */
    public BookAdapter(Context context, ArrayList<Book> books){
        super(context, 0, books);
    }


    /**
     *
     * @param position The item being selected either by user or programmatically
     * @param listItem
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View listItem, ViewGroup parent){

        // If there are no list items, add list items
        if (listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item_book, parent, false);
        }

        // Get a Book in the list
        final Book currentBook = getItem(position);

        // find and set Title of book
        String currentTitle = currentBook.getTitle();
        TextView titleTextView = (TextView) listItem.findViewById(R.id.book_title);
        if (currentTitle != null) {
            titleTextView.setText(currentTitle);
        }

        // find and set Author of book
        String currentAuthor = currentBook.getAuthor();
        TextView authorTextView = (TextView) listItem.findViewById(R.id.book_author);
        if (currentAuthor != null){
            authorTextView.setText(currentAuthor);
        }

        return listItem;
    }
}
