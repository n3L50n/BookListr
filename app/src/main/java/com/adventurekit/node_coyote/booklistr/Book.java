package com.adventurekit.node_coyote.booklistr;

/**
 * Created by node_coyote on 4/2/17.
 */

public class Book {

    // Title of a book
    public final String mTitle;

    // Author of a book
    public final String mAuthor;


    /**
     * Constructs a new {@link Book}
     * @param title  the title of a book from the Google Books api
     * @param author  the author of a book from the Google Books api
     */
    public Book(String title, String author){
        mTitle = title;
        mAuthor = author;
    }

    
    public String getTitle(){
        return mTitle;
    }

    public String getAuthor(){
        return mAuthor;
    }
}
