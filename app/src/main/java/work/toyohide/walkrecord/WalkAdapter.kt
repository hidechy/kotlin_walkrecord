package work.toyohide.walkrecord

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_walk_item.view.*

class WalkAdapter : RecyclerView.Adapter<WalkAdapter.ViewHolder>() {

    private var walkRecords = emptyList<WalkRecords>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    var onItemClick: ((WalkRecords) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalkAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_walk_item, parent, false))
    }

    override fun onBindViewHolder(holder: WalkAdapter.ViewHolder, position: Int) {
        val currentItem = walkRecords[position]
        holder.itemView.tv_date.text = currentItem.date
        holder.itemView.tv_step.text = currentItem.step
        holder.itemView.tv_distance.text = currentItem.distance

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(walkRecords[position])
        }

    }

    override fun getItemCount(): Int {
        return walkRecords.size
    }

    fun setData(walkRecords: List<WalkRecords>) {
        this.walkRecords = walkRecords
        notifyDataSetChanged()
    }

}
