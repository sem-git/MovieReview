package ddwu.com.mobilesw.moviereview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobilesw.moviereview.databinding.ActivityIntroduceBinding

class IntroduceActivity : AppCompatActivity() {

    val introduceBinding by lazy {
        ActivityIntroduceBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(introduceBinding.root)

        introduceBinding.btnBack.setOnClickListener{
            finish()
        }
    }
}