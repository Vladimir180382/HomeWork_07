package ru.otus.homework.customview.lineChart

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.otus.customview.R
import ru.otus.homework.customview.pieChart.PayloadData

class SecondActivity : AppCompatActivity() {

    private val payloadData: List<PayloadData> by readPayloadData()
    private val lineChartItemList: List<LineChartItem> by lazy {
        mapPayloadDataToLineChartItem(payloadData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val lineChartView = findViewById<CustomViewLineChart>(R.id.line_chart_view)
        findViewById<Spinner>(R.id.custom_view_variant).apply {
            adapter =
                createSpinnerAdapter()
            onItemSelectedListener =
                createSpinnerClickListener(lineChartView)
        }
        if (savedInstanceState == null) {
            lineChartView.setLineChartItems(lineChartItemList)
        }
        val buttonExit = findViewById<Button>(R.id.button_exit)
        buttonExit.setOnClickListener {
            finish()
        }
    }

    private fun mapPayloadDataToLineChartItem(
        payloadData: List<PayloadData>
    ): List<LineChartItem> {
        return payloadData.map {
            LineChartItem(
                category = it.category,
                amount = it.amount,
                time = it.time
            )
        }
    }

    private fun readPayloadData(): Lazy<List<PayloadData>> {
        return lazy {
            val fileData = resources.openRawResource(R.raw.payload).reader().readText()
            Json.decodeFromString(fileData)
        }
    }

    private fun createSpinnerAdapter(): ArrayAdapter<CharSequence> {
        return ArrayAdapter.createFromResource(
            this,
            R.array.custom_view_variants,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    private fun createSpinnerClickListener(
        lineChartView: CustomViewLineChart
    ): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?,
                selectedItemPosition: Int,
                selectedId: Long
            ) {
                if (selectedItemPosition == 0) {
                    lineChartView.visibility = ViewGroup.VISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }
}