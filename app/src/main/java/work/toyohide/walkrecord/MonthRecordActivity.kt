package work.toyohide.walkrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MonthRecordActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month_record)

        ///
        val tv_yearmonth:TextView = findViewById(R.id.tv_yearmonth)
        val yearmonth = intent.getStringExtra("yearmonth")
        tv_yearmonth.text = yearmonth

        ///
        val btn_back:Button = findViewById(R.id.btn_back)
        btn_back.setOnClickListener{
            this.finish()
        }

        ///
        var exYearmonth = yearmonth!!.split('-')

        ///
        val rv_list: RecyclerView = findViewById(R.id.rv_list)
        val adapter = WalkAdapter()
        rv_list.adapter = adapter
        rv_list.layoutManager = LinearLayoutManager(this)

        ///
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.readMonthRecords(exYearmonth[0], exYearmonth[1]).observe(this, {walkRecords -> adapter.setData(walkRecords) })

    }
}
