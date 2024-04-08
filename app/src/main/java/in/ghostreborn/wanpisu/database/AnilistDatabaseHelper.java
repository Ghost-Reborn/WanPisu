package in.ghostreborn.wanpisu.database;

import static in.ghostreborn.wanpisu.constants.WanPisuConstants.COLUMN_ALL_ANIME_ID;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.COLUMN_ANIME_ANILIST_ID;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.COLUMN_ANIME_AVAILABLE_EPISODES;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.COLUMN_ANIME_IMAGE_URL;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.COLUMN_ANIME_MAL_ID;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.COLUMN_ANIME_NAME;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.TABLE_NAME;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;

public class AnilistDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wanpisu.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" +
            COLUMN_ANIME_ANILIST_ID + " TEXT," +
            COLUMN_ALL_ANIME_ID + " TEXT," +
            COLUMN_ANIME_MAL_ID + " TEXT," +
            COLUMN_ANIME_NAME + " TEXT," +
            COLUMN_ANIME_IMAGE_URL + " TEXT," +
            COLUMN_ANIME_AVAILABLE_EPISODES + " TEXT" +
            ")";

    public AnilistDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
