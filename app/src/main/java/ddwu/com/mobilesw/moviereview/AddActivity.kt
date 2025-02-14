package ddwu.com.mobilesw.moviereview

import android.content.ContentValues
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ddwu.com.mobilesw.moviereview.data.MovieDBHelper
import ddwu.com.mobilesw.moviereview.data.MovieDto
import ddwu.com.mobilesw.moviereview.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    val addBinding by lazy {
        ActivityAddBinding.inflate(layoutInflater)
    }

    var calendar = Calendar.getInstance()
    var year = calendar.get(Calendar.YEAR)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(addBinding.root)

        // 개봉연도 선택
        addBinding.ivCal.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("연도 선택")

            val numberPicker = NumberPicker(this)
            numberPicker.minValue = 1900
            numberPicker.maxValue = 2100
            numberPicker.value = year

            builder.setView(numberPicker)

            builder.setPositiveButton("확인") { _, _ ->
                val selectedYear = numberPicker.value
                addBinding.etYear.setText(selectedYear.toString())
            }

            builder.setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }

            builder.create().show()
        }

        // et~ 에서 값을 읽어와 추가 (필드가 비어있으면 토스트 출력)
        addBinding.btnAdd.setOnClickListener{
            val title = addBinding.etTitle.text.toString()
            val year = addBinding.etYear.text.toString()
            val director = addBinding.etDirector.text.toString()
            val actors = addBinding.etActors.text.toString()
            val genre = addBinding.etGenre.text.toString()
            val line = addBinding.etLine.text.toString()

            if (title.isBlank() || year.isBlank() || director.isBlank() ||
                actors.isBlank() || genre.isBlank() || line.isBlank()) {
                Toast.makeText(this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val posterResourceId = R.mipmap.movie00
            if (addMovie(MovieDto(0, posterResourceId, title, year, director, actors, genre, line)) > 0) {
                setResult(RESULT_OK)
            } else {
                setResult(RESULT_CANCELED)
            }
            finish()
        }

        addBinding.btnCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    fun addMovie(newDto : MovieDto) : Long {
        val helper = MovieDBHelper(this)
        val db = helper.writableDatabase

        val newValue = ContentValues()

        newValue.put(MovieDBHelper.COL_POSTER, newDto.poster)
        newValue.put(MovieDBHelper.COL_TITLE, newDto.title)
        newValue.put(MovieDBHelper.COL_YEAR, newDto.year)
        newValue.put(MovieDBHelper.COL_DIRECTOR, newDto.director)
        newValue.put(MovieDBHelper.COL_ACTORS, newDto.actors)
        newValue.put(MovieDBHelper.COL_GENRE, newDto.genre)
        newValue.put(MovieDBHelper.COL_LINE, newDto.line)

        val newCount = db.insert(MovieDBHelper.TABLE_NAME, null, newValue)

        helper.close()

        return newCount
    }
}