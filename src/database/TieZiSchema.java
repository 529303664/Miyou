package database;

import database.DatabaseHelper.TableCreateInterface;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * 帖子数据表
 * @author Administrator
 *
 */
public  class TieZiSchema implements  BaseColumns,TableCreateInterface{
	private   String TEXT_TYPE = " TEXT";
	private   String VARCHAR_TYPE = " VARCHAR(255)";
	private   String COMMA_SEP = ",";
	public static final String TABLE_NAME=" mibo";
	public static final String COLUMN_CONTENT="content";
	public static final String COLUMN_FAVOR="favors";
	public static final String COLUMN__COMMENT_COUNT="comment_count";
	public static final String COLUMN_PARENT_ID="parentid";
	public static final String COLUMN_OPEN="open";
	public static final String COLUMN_FRCMOL="friendcomentonly";
	public static final String COLUMN_PIC_PATH="picpath";
	public static final String COLUMN_MESSAGE_TYPE="messagetype";
	
	private TieZiSchema(){}
	
	private static TieZiSchema mTieZiSchema = new TieZiSchema();
	
	public static TieZiSchema getIntance(){
		return mTieZiSchema;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String tablesql = "CREATE TABLE IF NOT EXISTS"
				+ TieZiSchema.TABLE_NAME + " ("
				+ TieZiSchema._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ TieZiSchema.COLUMN_CONTENT + TEXT_TYPE + COMMA_SEP
				+ TieZiSchema.COLUMN_FAVOR + VARCHAR_TYPE + COMMA_SEP
				+ TieZiSchema.COLUMN__COMMENT_COUNT+VARCHAR_TYPE+COMMA_SEP
				+ TieZiSchema.COLUMN_PARENT_ID+VARCHAR_TYPE+ COMMA_SEP
				+ TieZiSchema.COLUMN_OPEN+VARCHAR_TYPE+ COMMA_SEP
				+ TieZiSchema.COLUMN_FRCMOL+VARCHAR_TYPE+ COMMA_SEP
				+ TieZiSchema.COLUMN_PIC_PATH+VARCHAR_TYPE+ COMMA_SEP
				+ TieZiSchema.COLUMN_MESSAGE_TYPE+VARCHAR_TYPE+ COMMA_SEP
				+" )";
		db.execSQL(tablesql);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if(oldVersion<newVersion){
			String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
			db.execSQL(sql);
			this.onCreate(db);
		}
	}

}
