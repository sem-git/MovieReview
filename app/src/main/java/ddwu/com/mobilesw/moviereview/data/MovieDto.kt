package ddwu.com.mobilesw.moviereview.data

import java.io.Serializable

data class MovieDto(
    val id: Int,
    val poster: Int,
    var title: String,
    var year: String,
    var director: String,
    var actors: String,
    var genre: String,
    var line: String,
) : Serializable