package work.toyohide.walkrecord

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.Dialog
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class MonthRecordActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel

    private lateinit var yearmonth: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.supportActionBar?.hide()

        setContentView(R.layout.activity_month_record)

        ///
        val tv_yearmonth: TextView = findViewById(R.id.tv_yearmonth)
        yearmonth = intent.getStringExtra("yearmonth").toString()
        tv_yearmonth.text = yearmonth

        ///
        val btn_back: ImageButton = findViewById(R.id.btn_back)
        btn_back.setOnClickListener {
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
        viewModel.readMonthRecords(exYearmonth[0], exYearmonth[1]).observe(this, { walkRecords -> adapter.setData(walkRecords) })

        ///
        adapter.onItemClick = { walkRecords -> showActionDialog(walkRecords) }

        ///
        val btn_month_input:ImageButton = findViewById(R.id.btn_month_input)
        btn_month_input.setOnClickListener {
            showAddRecordDialog()
        }

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
            var exYearmonth = yearmonth!!.split('-')

            DatePickerDialog(
        this, datePicker,
        exYearmonth[0].toInt(),
        exYearmonth[1].toInt() - 1,
        1
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

    private fun showActionDialog(walkRecords: WalkRecords) {
        val builder = AlertDialog.Builder(this)

        builder.setPositiveButton("Delete") { _, _ ->
//            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
            viewModel.deleteData(walkRecords)
        }

        builder.setNegativeButton("Update") { _, _ ->
//            Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show()
            showUpdateDialog(walkRecords)
        }

        builder.setNeutralButton("Cancel") { _, _ ->
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        }

        builder.setTitle("Select Action")
        builder.create().show()
    }

    private fun showUpdateDialog(walkRecords: WalkRecords) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_update_record)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        val tv_date: TextView = dialog.findViewById(R.id.tv_date)
        val et_step: EditText = dialog.findViewById(R.id.et_step)
        val et_distance: EditText = dialog.findViewById(R.id.et_distance)

        tv_date.text = walkRecords.date
        et_step.setText(walkRecords.step)
        et_distance.setText(walkRecords.distance)

        val btn_update_cancel: ImageButton = dialog.findViewById(R.id.btn_update_cancel)
        btn_update_cancel.setOnClickListener {
            dialog.dismiss()
        }

        val btn_update_execute: ImageButton = dialog.findViewById(R.id.btn_update_execute)
        btn_update_execute.setOnClickListener {

            if (inputCheck(et_step.text.toString(), et_distance.text.toString())) {
                val tv_date: TextView = dialog.findViewById(R.id.tv_date)
                var exDate = (tv_date.text).split("/")

                val record = WalkRecords(
                    walkRecords.id, tv_date.text.toString(), exDate[0], exDate[1], exDate[2], et_step.text.toString(), et_distance
                        .text.toString()
                )

                viewModel.updateData(record)

                Toast.makeText(this, "data updated", Toast.LENGTH_LONG).show()

                dialog.dismiss()

            } else {
                Toast.makeText(this, "please enter data", Toast.LENGTH_LONG).show()
            }

            dialog.dismiss()
        }

        dialog.show()
        dialog.window!!.attributes = layoutParams
    }

    private fun inputCheck(step: String, distance: String): Boolean {
        return !(TextUtils.isEmpty(step) && TextUtils.isEmpty(distance))
    }

}
