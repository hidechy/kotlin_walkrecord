package work.toyohide.walkrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MonthRecordActivity : AppCompatActivity() {
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

    }
}
