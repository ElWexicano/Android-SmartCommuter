package ie.smartcommuter.models;

import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This is the Database Helper class which
 * is used to create the Database.
 * @author Shane Bryan Doyle
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "smartDB.db";
	private static final String SQL_FILE_NAME = "smartDB.sql";
	private static final int DATABASE_VERSION = 1;
    private final Context myContext;

    public DatabaseHelper(Context context) {
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    	this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	
    	// TODO: Display Progress Bar when creating the database.
    	generateDBFromSQLFile(db);
    }

    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	generateDBFromSQLFile(db);
    }
    
    /**
     * This method is used to generate the Database
     * by executing SQL commands from the SQL file.
     * @param db
     */
    public void generateDBFromSQLFile(SQLiteDatabase db) {

		try {
			InputStream is = myContext.getResources().getAssets().open(SQL_FILE_NAME);
		
			String[] statements = FileHelper.parseSqlFile(is);
	
			for (String statement : statements) {
				db.execSQL(statement);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
}