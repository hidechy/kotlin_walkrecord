package work.toyohide.walkrecord

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_walk_item.view.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class WalkAdapter : RecyclerView.Adapter<WalkAdapter.ViewHolder>() {

    private var walkRecords = emptyList<WalkRecords>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    var onItemClick: ((WalkRecords) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_walk_item, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = walkRecords[position]

        ///
        val exDate = (currentItem.date).split("/")
        val localDate = LocalDate.of(exDate[0].toInt(), exDate[1].toInt(), exDate[2].toInt())
        val formmater = DateTimeFormatter.ofPattern("yyyy/MM/dd(E)", Locale.JAPANESE)
        val format = localDate.format(formmater)
        holder.itemView.tv_date.text = format

        ///
        holder.itemView.tv_step.setText(currentItem.step + " step.")
        holder.itemView.tv_distance.setText(currentItem.distance + "m.")

        ///
        val _date = currentItem.date.replace("/", "-")
        val dt = LocalDateTime.parse(_date + "T00:00:00")
        val result = dt.dayOfWeek.toString()

        when {
            result == "SATURDAY" -> {
                holder.itemView.setBackgroundColor(Color.parseColor("#e6e6fa"))
                holder.itemView.alpha = 0.3f
            }
            result == "SUNDAY" -> {
                holder.itemView.setBackgroundColor(Color.parseColor("#ffe4e1"))
                holder.itemView.alpha = 0.3f
            }
            else -> {}
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(walkRecords[position])
        }

    }

    override fun getItemCount(): Int {
        return walkRecords.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(walkRecords: List<WalkRecords>) {
        this.walkRecords = walkRecords
        notifyDataSetChanged()
    }

}
