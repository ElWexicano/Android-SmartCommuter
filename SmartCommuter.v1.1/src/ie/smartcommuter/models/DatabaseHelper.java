package ie.smartcommuter.models;

import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This is the Database Helper class which is used to create the Database.
 * 
 * @author Shane Doyle
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private final Context mContext;

	public DatabaseHelper(Context context) {
		super(context, Constants.Database.NAME, null,
				Constants.Database.VERSION);
		this.mContext = context;
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		generateDBFromSQLFile(db);
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, int oldVersion,
			int newVersion) {
		generateDBFromSQLFile(db);
	}

	/**
	 * This method is used to generate the Database by executing SQL commands
	 * from the SQL file.
	 * 
	 * @param db
	 */
	public void generateDBFromSQLFile(SQLiteDatabase db) {
		try {
			InputStream is = mContext.getResources().getAssets()
					.open(Constants.Database.SQL_FILE_NAME);

			String[] statements = FileHelper.parseSqlFile(is);

			for (String statement : statements) {
				db.execSQL(statement);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}