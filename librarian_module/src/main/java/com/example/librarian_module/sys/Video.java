package com.example.librarian_module.sys;

/**********************************************
 Assignment 1 - Librarian Module
 Course: BTP400 Object-Oriented Software Development II – Java – Winter 2022
 Last Name: Yeung
 First Name: Jackson
 ID: 110018207
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Date: March 4, 2022
 **********************************************/

/**
 * The class Video manages the videos in the library system. It inherits the class Item.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class Video extends Item {
    private int directorID;
    private int genreID;
    private int pubYear;

    /**
     * Create an empty video.
     */
    public Video() {
    }

    /**
     * Create a Video object with the specified parameters. Creating the object does not mean
     * it has been added to the library system.
     *
     * @param vid   Unique video ID.
     * @param tle   Name of the video.
     * @param lname Last name of the director.
     * @param fname First name of the director.
     * @param yr    Published year of the video.
     * @param cat   Genre of the video.
     */
    public Video(String vid, String tle, String lname, String fname, String yr, String cat) {
        super(vid, tle);
        directorID = Author.getAuthorID(lname, fname);
        genreID = BookGenre.getGenreID(cat);
        pubYear = Integer.parseInt(yr);
    }

    /**
     * Add the video with the specified parameters to the library system.
     *
     * @param vid   Unique video ID.
     * @param tle   Name of the video.
     * @param lname Last name of the director.
     * @param fname First name of the director.
     * @param yr    Published year of the video.
     * @param cat   Genre of the video.
     * @return An enum StatusCode representing the result of the add video operation.
     */
    public static StatusCode add(String vid, String tle, String lname, String fname, String yr, String cat) {
        int index = findItem(vid);
        if (index == -1) {
            items.add(new Video(vid, tle, lname, fname, yr, cat));
            return StatusCode.SUCCESS;
        } else {
            return StatusCode.DUPLICATE_ITEM_ID;
        }
    }
}
