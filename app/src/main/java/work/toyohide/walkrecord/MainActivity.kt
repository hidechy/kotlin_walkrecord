package work.toyohide.walkrecord

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), ListClickInterface {

    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.supportActionBar?.hide()

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        ///
        val rv_list: RecyclerView = findViewById(R.id.rv_list)
        val adapter = YmListAdapter(this, this)

        rv_list.layoutManager = LinearLayoutManager(this)
        rv_list.adapter = adapter

        ///
        val tv_main: TextView = findViewById(R.id.tv_main)
        tv_main.text = "Walking Record"

        ///
        val fab_add: FloatingActionButton = findViewById(R.id.fab_add)
        fab_add.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#e6e6fa"))
        fab_add.setOnClickListener {
            showAddRecordDialog()
        }

        ///
        val btn_allrecord:ImageButton = findViewById(R.id.btn_allrecord)
        btn_allrecord.setOnClickListener{
            val intent = Intent(this@MainActivity, AllRecordActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onListClick(yearmonth: String) {
        val intent = Intent(this@MainActivity, MonthRecordActivity::class.java)
        intent.putExtra("yearmonth", yearmonth)
        startActivity(intent)
    }

    private fun showAddRecordDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_add_new_record)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        val et_step: EditText = dialog.findViewById(R.id.et_step)
        val et_distance: EditText = dialog.findViewById(R.id.et_distance)

        ///
        val tv_date: TextView = dialog.findViewById(R.id.tv_date)
        val myCalendar = Calendar.getInstance()

        ///
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy/MM/dd"
            val sdf = SimpleDateFormat(myFormat, Locale.JAPAN)
            tv_date.text = sdf.format(myCalendar.time)
        }

        ///
        val btn_datepicker: ImageButton = dialog.findViewById(R.id.btn_datepicker)
        btn_datepicker.setOnClickListener {
            DatePickerDialog(
                this, datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        ///
        val btn_cancel: ImageButton = dialog.findViewById(R.id.btn_cancel)
        btn_cancel.setOnClickListener {
            dialog.dismiss()
        }

        ///
        val btn_add: ImageButton = dialog.findViewById(R.id.btn_add)
        btn_add.setOnClickListener {
            if (inputCheck(et_step.text.toString(), et_distance.text.toString())) {
//                Toast.makeText(this, "${et_step.text.toString()} / ${et_distance.text.toString()}", Toast.LENGTH_LONG).show()

                val tv_date: TextView = dialog.findViewById(R.id.tv_date)
                if (TextUtils.isEmpty(tv_date.text)) {
                    Toast.makeText(this, "date required!!", Toast.LENGTH_LONG).show()
                } else {
                    var exDate = (tv_date.text).split("/")

                    val walkRecords = WalkRecords(
                        0, tv_date.text.toString(), exDate[0], exDate[1], exDate[2], et_step.text.toString(), et_distance
                            .text.toString()
                    )

                    viewModel.addRecord(walkRecords)

                    Toast.makeText(this, "data added", Toast.LENGTH_LONG).show()

                    dialog.dismiss()
                }
            } else {
                Toast.makeText(this, "step or distance required!!", Toast.LENGTH_LONG).show()
            }
        }

        dialog.show()
        dialog.window!!.attributes = layoutParams

    }

    private fun inputCheck(step: String, distance: String): Boolean {
        return !(TextUtils.isEmpty(step) && TextUtils.isEmpty(distance))
    }

}
