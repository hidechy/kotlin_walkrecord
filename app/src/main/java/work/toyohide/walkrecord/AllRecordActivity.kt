package work.toyohide.walkrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AllRecordActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.supportActionBar?.hide()

        setContentView(R.layout.activity_all_record)

        ///
        val btn_back: Button = findViewById(R.id.btn_back)
        btn_back.setOnClickListener{
            this.finish()
        }

        ///
        val rv_list:RecyclerView = findViewById(R.id.rv_list)
        val adapter = WalkAdapter()
        rv_list.adapter = adapter
        rv_list.layoutManager = LinearLayoutManager(this)

        ///
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.readAllRecords.observe(this, {walkRecords -> adapter.setData(walkRecords) })

        ///
        val btn_alldelete:Button = findViewById(R.id.btn_alldelete)
        btn_alldelete.setOnClickListener{
            viewModel.deleteAllData()
        }

    }
}
