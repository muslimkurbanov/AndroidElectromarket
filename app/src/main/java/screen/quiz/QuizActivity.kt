package screen.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseauthexample.R
import kotlinx.android.synthetic.main.activity_learn_info.*

class QuizActivity: AppCompatActivity() {

    companion object {
        const val TEST_NAME: String = "test_name"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val testName = intent.getStringExtra(TEST_NAME)

    }

}