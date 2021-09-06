package screen.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseauthexample.R
import kotlinx.android.synthetic.main.variants_recyclerview.view.*
import java.util.*
import kotlin.collections.HashMap

class ProfileAdapter(
    val test: HashMap<String, List<Int>>
) : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ProfileViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.custom_text_spinner, parent, false)
    )

    override fun getItemCount() = test.keys.size

    override fun onBindViewHolder(holder: ProfileAdapter.ProfileViewHolder, position: Int) {
        holder.bind(title = test.keys.toMutableList()[position])
    }

    inner class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val adapterVariants = AdapterVariants()

        private val tvTitle = itemView.findViewById<AppCompatTextView>(R.id.title)
        private val rvVariants = itemView.findViewById<RecyclerView>(R.id.recyclerVariants)

        init {
            rvVariants.adapter = adapterVariants

            itemView.setOnClickListener {

                if (selectedPosition == adapterPosition) {
                    rvVariants.visibility = View.GONE
                    selectedPosition = -1
                    lastSelectedPosition = -1
                } else {
                    lastSelectedPosition = selectedPosition
                    selectedPosition = adapterPosition

                    notifyItemChanged(lastSelectedPosition)
                    notifyItemChanged(selectedPosition)
                }
            }
        }

        fun bind(title: String) {


            tvTitle.text = title + ": " + test[title].orEmpty().last()

            adapterVariants.setVariants(test[title].orEmpty())

            rvVariants.visibility = if (selectedPosition == adapterPosition) View.VISIBLE else View.GONE
        }
    }
}