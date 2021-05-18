package screen.learninfo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseauthexample.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_learn_info.*
import screen.quiz.QuizActivity


class LearnInfoActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    var video: String = ""

    companion object {
        const val CHILD_NAME: String = "child_name"
        const val TEST_CHILD_NAME: String = "test_child_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_learn_info)
        super.onCreate(savedInstanceState)

        val a = intent.getStringExtra(CHILD_NAME)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        reference = database.getReference("users")
            .child(auth.currentUser.uid)
            .child("Tests")
            .child(a.toString())
        getVideoUrlFromFB()

        button1.setOnClickListener {
            val childName = intent.getStringExtra(TEST_CHILD_NAME)
            val testChild = intent.getStringExtra(CHILD_NAME)
            val intentNice = Intent(this, QuizActivity::class.java)
            intentNice.putExtra(QuizActivity.TEST_NAME, childName)
            intentNice.putExtra(QuizActivity.TEST_CHILD, testChild)
            startActivity(intentNice)
        }
    }

    private fun getVideoUrlFromFB(){

        val childList = ArrayList<String>()
        var videoUrlSting = String()

        val getData = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                print(error)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val videoUrl = dataSnapshot.child("TestsVideo").getValue(String::class.java)
                videoUrlSting = videoUrl.toString()

//                val videoView = findViewById<VideoView>(R.id.videoViewLearnInfoScreen)
//                val mediaController = MediaController(this@LearnInfoActivity)
//                mediaController.setAnchorView(videoViewLearnInfoScreen)
//                videoViewLearnInfoScreen.setMediaController(mediaController)
//
////        videoView.setVideoURI(Uri.parse(video))
//                videoViewLearnInfoScreen.setVideoURI(Uri.parse(videoUrlSting))
//                videoViewLearnInfoScreen.requestFocus()
//                videoViewLearnInfoScreen.start()
            }
        }
        reference.addValueEventListener(getData)

    }
}