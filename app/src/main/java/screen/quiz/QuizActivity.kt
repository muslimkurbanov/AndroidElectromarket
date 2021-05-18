package screen.quiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseauthexample.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.activity_registration.*
import screen.fragment.TestFragment
import screen.test.ui.TestActivity
import kotlin.collections.ArrayList

class QuizActivity : AppCompatActivity() {

    companion object {
        const val TEST_NAME: String = "test_name"
        const val TEST_CHILD: String = "test_child"
    }

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private var firebaseQuestions = arrayListOf<String>()
    private var firebaseRightAnswers = arrayListOf<String>()
    private val model = ArrayList<ArrayList<String>>()

    private var testResults = arrayListOf<Int>()

    private var index = 0
    private var score = 0

    private val buttons by lazy {
        arrayListOf(
            button1,
            button2,
            button3,
            button4
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val testChild = intent.getStringExtra(TEST_CHILD)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        ref = database.getReference("users")
            .child(auth.currentUser.uid)
            .child("tests")
        getResultsFromFB()

        reference = database.getReference("users")
            .child(auth.currentUser.uid)
            .child("Tests")
            .child(testChild.toString())
        getTestFromFB()


        initListeners()
    }

    private fun getResultsFromFB() {
        val testName = intent.getStringExtra(TEST_NAME)

        val postListener = object : ValueEventListener {


            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.child(testName!!).children.forEach {
                    testResults.add(it.getValue(Int::class.java) as Int)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("err", "${error.details} - ${error.message}")
            }
        }
        this.ref.addListenerForSingleValueEvent(postListener)
    }

    private fun getTestFromFB() {

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataSnapshot.child("Questions").children.forEach {
                    firebaseQuestions.add(it.getValue(String::class.java) as String)
                }

                dataSnapshot.child("RightAnswers").children.forEach {
                    firebaseRightAnswers.add(it.getValue(String::class.java) as String)
                }

                dataSnapshot.child("Answers").children.forEach { dataSnapshot ->
                    dataSnapshot?.let {
                        model.add((it.value as ArrayList<String>))
                    }
                }
                textView.text = firebaseQuestions[index]
                applyButtons()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("err", "${databaseError.details} - ${databaseError.message}")
            }
        }

        reference.addListenerForSingleValueEvent(postListener)
    }

    private fun applyButtons() {

        model.getOrNull(index = index)?.forEachIndexed { index, s ->
            buttons[index].text = s
        }
    }

    private fun initListeners() {

        finishButton.setOnClickListener {
            val intentNice = Intent(this, TestActivity::class.java)
            startActivity(intentNice)

            val map: HashMap<String, Any> = hashMapOf(
                "Имя" to inputNameEditTextRegistrationAct.text.toString(),
                "Фамилия" to inputSernameEditTextRegistrationAct.text.toString(),
                "Номер телефона" to inputUserNumberRegistrationAct.text.toString()
            )
            reference.setValue(map)
            val intentToTestFrag = Intent(this, TestActivity::class.java)
            startActivity(intentToTestFrag)

        }

        buttons.forEach { button ->

            button.setOnClickListener {

                 when {
                    button.text == firebaseRightAnswers[index] -> {
                        index++
                        score++
                        textView.text = firebaseQuestions[index]
                        applyButtons()
                    }

                    index <= firebaseQuestions.count() -> {
                        index++
                        textView.text = firebaseQuestions[index]
                        applyButtons()
                    }

                    else -> return@setOnClickListener
                }
                if (index == firebaseQuestions.count() - 1) {
                    for (i in buttons) {
                        i.visibility = View.GONE
                        finishButton.visibility = View.VISIBLE
                        resultTextView.visibility = View.VISIBLE
                        resultTextView.text = "Ваш результат: $score"
                    }

                }
            }
        }
    }
}