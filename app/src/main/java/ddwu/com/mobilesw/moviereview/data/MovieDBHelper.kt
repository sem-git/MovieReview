package ddwu.com.mobilesw.moviereview.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import ddwu.com.mobilesw.moviereview.R

class MovieDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    val TAG = "MovieDBHelper"

    companion object {
        const val DB_NAME = "movie_db"
        const val TABLE_NAME = "movie_table"
        const val COL_POSTER = "poster"
        const val COL_TITLE = "title";
        const val COL_YEAR = "year";
        const val COL_DIRECTOR = "director";
        const val COL_ACTORS = "actors";
        const val COL_GENRE = "genre";
        const val COL_LINE = "line";
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_POSTER INTEGER, $COL_TITLE TEXT, $COL_YEAR TEXT, $COL_DIRECTOR TEXT, $COL_ACTORS TEXT, $COL_GENRE TEXT, $COL_LINE TEXT)"
        Log.d(TAG, CREATE_TABLE)
        db?.execSQL(CREATE_TABLE)

        // 샘플 데이터
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.movie01}, '다만악에서구하소서', '2020', '홍원찬', '황정민, 이정재, 박정민', '범죄, 액션', '이 오빠 게이에요?')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.movie02}, '엘리멘탈',  '2023', '피터 손', '레아 루이스, 마무두 아티', '애니메이션', '네 빛이 일렁일 때가 좋아')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.movie03}, '극한직업', '2019', '이병헌', '류승룡, 이하늬, 진선규, 이동휘, 공명', '코미디', '지금까지 이런 맛은 없었다')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.movie04}, '기생충', '2019', '봉준호', '송강호, 이선균, 조여정, 최우식, 박소담', '드라마', '제시카 외동딸 일리노이 시카고')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.movie05}, '인턴',  '2015', '낸시 마이어스', '앤 해서웨이, 로버트 드 니로', '코미디', 'Experience never gets old')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}


