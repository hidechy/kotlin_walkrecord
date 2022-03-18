package work.toyohide.walkrecord

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.util.*

class YmListAdapter(private val listClickInterface: ListClickInterface) : RecyclerView.Adapter<YmListAdapter.ViewHolder>() {

    private val ym: ArrayList<String> = arrayListOf()

    init {
        //-------------------------------------------------//
        val hiduke: LocalDate = LocalDate.now()
        val exHiduke = (hiduke).toString().split('-')

        for (i in 2020..exHiduke[0].toInt()) {
            val year = i

            var month: String
            when {
                year == 2020 -> {
                    for (j in 8..12) {
                        month = j.toString().padStart(2, '0')
                        ym.add("$year-$month")
                    }
                }
                year == exHiduke[0].toInt() -> {
                    for (j in 1..exHiduke[1].toInt()) {
                        month = j.toString().padStart(2, '0')
                        ym.add("$year-$month")
                    }
                }
                else -> {
                    for (j in 1..12) {
                        month = j.toString().padStart(2, '0')
                        ym.add("$year-$month")
                    }
                }
            }
        }
//        println(ym)
        //-------------------------------------------------//
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YmListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_yearmonth_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: YmListAdapter.ViewHolder, position: Int) {
        holder.itemYm.text = ym[position]

        holder.itemView.setOnClickListener {
            listClickInterface.onListClick(ym[position])
        }
    }

    override fun getItemCount(): Int {
        return ym.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemYm: TextView

        init {
            itemYm = itemView.findViewById(R.id.tv_ym)
        }
    }
}

interface ListClickInterface {
    fun onListClick(yearmonth: String)
}
