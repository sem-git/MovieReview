package ddwu.com.mobilesw.moviereview

import android.content.ContentValues
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ddwu.com.mobilesw.moviereview.data.MovieDBHelper
import ddwu.com.mobilesw.moviereview.data.MovieDto
import ddwu.com.mobilesw.moviereview.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    val updateBinding by lazy {
        ActivityUpdateBinding.inflate(layoutInflater)
    }

    var calendar = Calendar.getInstance()
    var year = calendar.get(Calendar.YEAR)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(updateBinding.root)

        // 개봉연도 선택
        updateBinding.ivUpdateCal.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("연도 선택")

            val numberPicker = NumberPicker(this)
            numberPicker.minValue = 1900
            numberPicker.maxValue = 2100
            numberPicker.value = year

            builder.setView(numberPicker)

            builder.setPositiveButton("확인") { _, _ ->
                val selectedYear = numberPicker.value
                updateBinding.etUpdateYear.setText(selectedYear.toString())
            }

            builder.setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }

            builder.create().show()
        }

        // RecyclerView 에서 선택하여 전달한 dto를 확인하고 수정 (필드가 비어있으면 토스트 출력)
        val dto = intent.getSerializableExtra("dto") as MovieDto

        updateBinding.ivUpdatePoster.setImageResource(dto.poster)
        updateBinding.etUpdateTitle.setText(dto.title)
        updateBinding.etUpdateYear.setText(dto.year)
        updateBinding.etUpdateDirector.setText(dto.director)
        updateBinding.etUpdateActors.setText(dto.actors)
        updateBinding.etUpdateGenre.setText(dto.genre)
        updateBinding.etUpdateLine.setText(dto.line)

        updateBinding.btnUpdate.setOnClickListener{
            dto.title = updateBinding.etUpdateTitle.text.toString()
            dto.year = updateBinding.etUpdateYear.text.toString()
            dto.director = updateBinding.etUpdateDirector.text.toString()
            dto.actors = updateBinding.etUpdateActors.text.toString()
            dto.genre = updateBinding.etUpdateGenre.text.toString()
            dto.line = updateBinding.etUpdateLine.text.toString()

            if (dto.title.isBlank() || dto.year.isBlank() || dto.director.isBlank() ||
                dto.actors.isBlank() || dto.genre.isBlank() || dto.line.isBlank()) {
                Toast.makeText(this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (updateMovie(dto) > 0) {
                setResult(RESULT_OK)
            } else {
                setResult(RESULT_CANCELED)
            }
            finish()
        }

        updateBinding.btnCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    fun updateMovie(dto: MovieDto): Int {
        val helper = MovieDBHelper(this)
        val db = helper.writableDatabase

        val updateValue = ContentValues()

        updateValue.put(MovieDBHelper.COL_POSTER, dto.poster)
        updateValue.put(MovieDBHelper.COL_TITLE, dto.title)
        updateValue.put(MovieDBHelper.COL_YEAR, dto.year)
        updateValue.put(MovieDBHelper.COL_DIRECTOR, dto.director)
        updateValue.put(MovieDBHelper.COL_ACTORS, dto.actors)
        updateValue.put(MovieDBHelper.COL_GENRE, dto.genre)
        updateValue.put(MovieDBHelper.COL_LINE, dto.line)

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        val UpdateCount =  db.update(MovieDBHelper.TABLE_NAME,
            updateValue, whereClause, whereArgs)

        helper.close()

        return UpdateCount
    }
}