package screen.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firebaseauthexample.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.custom_toolbar_test_act.*
import kotlinx.android.synthetic.main.fragment_test.*
import screen.learninfo.LearnInfoActivity
import screen.test.mvp.TestView
import screen.test.ui.TestAdapter

class TestFragment : Fragment(), TestView, TestAdapter.OnItemClickListener {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    var childs = arrayListOf<String>()
    var testChild = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAlertDialog()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("users")
            .child(auth.currentUser.uid)
            .child("Tests")
            .child("TestsInformation")

        loadDataFromFB()
    }
    // Загрузка названий тестов
    private fun loadDataFromFB() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val fieldList = ArrayList<String>()
                val imageList = ArrayList<String>()
                val childList = ArrayList<String>()
                val testResultChild = ArrayList<String>()

                for (data in dataSnapshot.child("TestsImage").children) {
                    val model = data.getValue(String::class.java)
                    imageList.add(model as String)
                }

                for (data in dataSnapshot.child("TestsName").children) {
                    val model = data.getValue(String::class.java)
                    fieldList.add(model as String)
                }

                for (data in dataSnapshot.child("TestChilds").children) {
                    val model = data.getValue(String::class.java)
                    childList.add(model as String)
                }

                for (data in dataSnapshot.child("TestsResultChild").children) {
                    val model = data.getValue(String::class.java)
                    testResultChild.add(model as String)
                }

                testChild = testResultChild
                childs = childList

                val testAdapter = TestAdapter(fieldList, imageList, childList, this@TestFragment)
                recyclerViewTestAct.adapter = testAdapter

                progressBar.visibility = View.INVISIBLE
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message

                Log.d("err", "${databaseError.details} - ${databaseError.message}")
            }
        }
        reference.addValueEventListener(postListener)
    }


     override fun onItemClick(position: Int) {
         var child = childs[position]
         var testChild = testChild[position]
         val intent = Intent(activity, LearnInfoActivity::class.java)
         intent.putExtra(LearnInfoActivity.CHILD_NAME, child)
         intent.putExtra(LearnInfoActivity.TEST_CHILD_NAME, testChild)
         print(child)
         recyclerViewTestAct.adapter?.notifyItemChanged(position)
         startActivity(intent)
     }


     private fun initAlertDialog() {
        logoutButton.visibility = View.INVISIBLE
         iconHelp.visibility = View.VISIBLE

        val alertDialog =
            AlertDialog.Builder(this.view!!.context).setTitle("Инструкция").setMessage(
                "После нажатия кнопки 'Приступить к тесту' вы перейдете к просмотру"
                        + "\n"
                        + "видеоролика с материалом по тесту."
                        + "\n"
                        + "После просмотра видеоролика"
                        + "\n"
                        + "нажмите на кнопку 'Начать тест' и приступайте к выполнению теста"
            )
                .setPositiveButton("OK") { _, _ ->

                }.create()

        iconHelp.setOnClickListener {

            alertDialog.show()

        }

    }


}






