package screen.fragment

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.firebaseauthexample.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.custom_toolbar_test_act.*
import screen.login.ui.MainActivity

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()


        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initLogOut()
        logoutButton.visibility = View.VISIBLE
        iconHelp.visibility = View.INVISIBLE
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initLogOut() {
//        iconHelp.setOnClickListener {
//            auth.signOut()
//            val intentToMainAct = Intent(context, MainActivity::class.java)
//            startActivity(intentToMainAct)
//        }

        logoutButton.setOnClickListener {
            auth.signOut()
            val intentToMainAct = Intent(context, MainActivity::class.java)
            startActivity(intentToMainAct)
        }
    }
}