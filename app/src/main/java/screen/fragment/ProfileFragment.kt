package screen.fragment

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.firebaseauthexample.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.custom_toolbar_test_act.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_test.*
import screen.login.ui.MainActivity
import screen.test.ui.TestAdapter

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private lateinit var newRef: DatabaseReference

    private var arr = arrayListOf<Int>(1,4,3)

    private var firKeys = arrayListOf<String>()
    private var test: HashMap<String, List<Int>> = HashMap()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        ref = database.getReference("users")
            .child(auth.currentUser.uid)


        newRef = database.getReference("users")
            .child(auth.currentUser.uid)
            .child("Tests")
            .child("TestsInformation")
        getUserScore()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initLogOut()
        logoutButton.visibility = View.VISIBLE
        iconHelp.visibility = View.INVISIBLE
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getUserScore() {

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataSnapshot.child("tests").children.forEach {
                    test[it.key.orEmpty()] = it.value as List<Int>
                }

                val testAdapter = ProfileAdapter(test)
                recyclerViewHome.adapter = testAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("err", "${databaseError.details} - ${databaseError.message}")
            }
        }

        ref.addListenerForSingleValueEvent(postListener)

    }
    private fun initLogOut() {

        logoutButton.setOnClickListener {

            val alertDialog =
                AlertDialog.Builder(this.view!!.context).setTitle("Инструкция").setMessage("Вы действительно хотите выйти из профиля?")
                    .setPositiveButton("Да") { _, _ ->

                        auth.signOut()
                        val intentToMainAct = Intent(context, MainActivity::class.java)
                        startActivity(intentToMainAct)

                    }.setNegativeButton("Нет") { _, _ ->

                    }.create()

                        logoutButton.setOnClickListener {

                            alertDialog.show()

                        }
                    }
        }
    }