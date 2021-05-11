package screen.test.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.firebaseauthexample.R
import kotlinx.android.synthetic.main.activity_test.*
import screen.fragment.ProfileFragment
import screen.fragment.TestFragment

class TestActivity : AppCompatActivity() {

    private val testFragment = TestFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        replaceFragment(testFragment)

        bottomNavigationTestAct.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.test_list_bar -> replaceFragment(testFragment)
                R.id.profile_bar -> replaceFragment(profileFragment)
            }
            true
        }
    }

    override fun onBackPressed() {

    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

}