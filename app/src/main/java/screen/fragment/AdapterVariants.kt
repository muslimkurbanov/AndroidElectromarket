package screen.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseauthexample.R
class AdapterVariants: RecyclerView.Adapter<AdapterVariants.ViewHolder>() {

    private val variants = arrayListOf<Int>()

    fun setVariants(variants: List<Int>) {

        this.variants.run {
            clear()
            addAll(variants)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.variants_recyclerview, parent, false)
    )

    override fun getItemCount() =  variants.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(variants[position].toString())

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val variantsTitle = itemView.findViewById<AppCompatTextView>(R.id.title)

        fun bind(title: String) {
            variantsTitle.text = title
        }
    }
}