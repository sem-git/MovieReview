package ddwu.com.mobilesw.moviereview.data

import android.annotation.SuppressLint
import android.content.Context
import android.provider.BaseColumns

class MovieDao (val context: Context){

    // 삭제
    fun deleteMovie(dto: MovieDto) : Int {
        val helper = MovieDBHelper(context)
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        return db.delete(MovieDBHelper.TABLE_NAME, whereClause, whereArgs)
    }

    // 검색
    @SuppressLint("Range")
    fun searchMovies(query: String): ArrayList<MovieDto> {
        val helper = MovieDBHelper(context)
        val db = helper.readableDatabase

        val cursor = db.query(
            MovieDBHelper.TABLE_NAME,
            null,
            "${MovieDBHelper.COL_TITLE} LIKE ? OR " +
                    "${MovieDBHelper.COL_DIRECTOR} LIKE ? OR " +
                    "${MovieDBHelper.COL_ACTORS} LIKE ?",
            arrayOf("%$query%", "%$query%", "%$query%"),
            null, null, null
        )

        val movies = arrayListOf<MovieDto>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndex(BaseColumns._ID))
                val poster = getInt(getColumnIndex(MovieDBHelper.COL_POSTER))
                val title = getString(getColumnIndex(MovieDBHelper.COL_TITLE))
                val year = getString(getColumnIndex(MovieDBHelper.COL_YEAR))
                val director = getString(getColumnIndex(MovieDBHelper.COL_DIRECTOR))
                val actors = getString(getColumnIndex(MovieDBHelper.COL_ACTORS))
                val genre = getString(getColumnIndex(MovieDBHelper.COL_GENRE))
                val line = getString(getColumnIndex(MovieDBHelper.COL_LINE))
                val dto = MovieDto(id, poster, title, year, director, actors, genre, line)
                movies.add(dto)
            }
        }
        return movies
    }

    // 영화 정보 가져오기
    @SuppressLint("Range")
    fun getAllMovies() : ArrayList<MovieDto> {
        val helper = MovieDBHelper(context)
        val db = helper.readableDatabase
        val cursor = db.query(MovieDBHelper.TABLE_NAME, null, null, null, null, null, null)

        val movies = arrayListOf<MovieDto>()
        with (cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndex(BaseColumns._ID))
                val poster = getInt(getColumnIndex(MovieDBHelper.COL_POSTER))
                val title = getString(getColumnIndex(MovieDBHelper.COL_TITLE))
                val year = getString(getColumnIndex(MovieDBHelper.COL_YEAR))
                val director = getString(getColumnIndex(MovieDBHelper.COL_DIRECTOR))
                val actors = getString(getColumnIndex(MovieDBHelper.COL_ACTORS))
                val genre = getString(getColumnIndex(MovieDBHelper.COL_GENRE))
                val line = getString(getColumnIndex(MovieDBHelper.COL_LINE))
                val dto = MovieDto(id, poster, title, year, director, actors, genre, line)
                movies.add(dto)
            }
        }
        return movies
    }
}