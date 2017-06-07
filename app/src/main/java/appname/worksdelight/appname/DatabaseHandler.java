package appname.worksdelight.appname;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Arabemoji";

    // Contacts table name
    private static final String TABLE_IMAGE1 = "images";

    // Contacts Table Columns names
    private static final String KEY_ID1 = "id";
    private static final String IMAGE1 = "image";

    private static final String TABLE_IMAGE2 = "images";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE1 = "CREATE TABLE " + TABLE_IMAGE1 + "("
                + KEY_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + IMAGE1 + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE1);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE1);

        // Create tables again
        onCreate(db);
    }


    // Adding new contact
    public void addImages1(ImagesModal imgModal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(IMAGE1, imgModal.getImage());
        // Inserting Row
        db.insert(TABLE_IMAGE1, null, values);
        db.close(); // Closing database connection

    }

    public boolean hasObject(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_IMAGE1 + " WHERE " + IMAGE1 + " =?";

        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString, new String[]{id});

        boolean hasObject = false;
        if (cursor.moveToFirst()) {
            hasObject = true;

            //region if you had multiple records to check for, use this region.

            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            //here, count is records found
            Log.d(TAG, String.format("%d records found", count));

            //endregion

        }

        cursor.close();          // Dont forget to close your cursor
        db.close();              //AND your Database!
        return hasObject;
    }


    // Getting All images
    public List<ImagesModal> getAllContacts() {
        List<ImagesModal> contactList = new ArrayList<ImagesModal>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_IMAGE1;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ImagesModal contact = new ImagesModal();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.setImage(Integer.parseInt(cursor.getString(1)));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_IMAGE1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);


        // return count
        return cursor.getCount();
    }


    // Deleting single contact
    public void deleteContact(ImagesModal contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGE1, IMAGE1 + " = ?",
                new String[]{String.valueOf(contact.getImage())});
        db.close();
    }
      /*  public void updatecontact(Contact c, int postion) {

	        SQLiteDatabase db = this.getWritableDatabase();
	        ContentValues values = new ContentValues();
	        
	        values.put(Ques_status, c.getQues_status());
	        // update Row
	        db.update(TABLE_CONTACTS,values,"KEY_ID = '"+postion+"'",null);
	        db.close(); // Closing database connection
	       

	    }*/
	   /* public void updateImage(ImagesModal imgModal, int postion) {
	        SQLiteDatabase db = this.getWritableDatabase();
	 
	        ContentValues values = new ContentValues();
	       
	        values.put(Ques_status, imgModal.getQues_status());
	        db.update(TABLE_CONTACTS, values, KEY_ID + " = '"+postion+"'",null);
	                
	        db.close();
	        // updating row
	        
	    }*/


	/*public void updateContact(String question, int postion) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(QUES_NAME, question);
		db.update(TABLE_CONTACTS, values, KEY_ID + " = '"+postion+"'",null);

		db.close();

	}*/


    public void removeAllContact() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_IMAGE1);
        db.close();
        // updating row

    }


}
