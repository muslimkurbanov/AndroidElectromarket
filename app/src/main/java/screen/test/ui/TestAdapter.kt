package screen.test.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseauthexample.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_test_layout.view.*
import screen.learninfo.LearnInfoActivity

class TestAdapter(
    val fieldTestList: ArrayList<String>,
    val imageTestList: ArrayList<String>,
    val childList: ArrayList<String>,
    val listener: OnItemClickListener
) : RecyclerView.Adapter<TestAdapter.TestViewHolder>( ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdapter.TestViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_test_layout, parent, false)
        return TestViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return fieldTestList.size
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.titleTextView?.text = fieldTestList[position]
        holder.initialaze(listener)
        val path = imageTestList[position]
        Picasso.get()
            .load(path)
            .resize(250, 250)
            .centerCrop()
            .into(holder.image)

    }



    class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val act = LearnInfoActivity
        val testAct = LearnInfoActivity()
        var titleTextView: TextView? = null
        var image: ImageView? = null

        init {
            titleTextView = itemView.titleTest
            image = itemView.imageTest
        }

        fun initialaze(action: OnItemClickListener) {
            itemView.setOnClickListener {
                action.onItemClick(adapterPosition)
            }

        }

//        override fun onClick(v: View?) {
//            val position = adapterPosition
//            val intent = Intent(mContext, LearnInfoActivity::class.java)
//            intent.putExtra("childName", childList[position])
//            mContext.startActivity(intent)
////            if (position != RecyclerView.NO_POSITION) {
////                listener.onItemClick(position)
////            }
//        }
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}