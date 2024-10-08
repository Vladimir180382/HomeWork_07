package ru.otus.homework.customview.pieChart

import android.content.Intent
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
import ru.otus.customview.R.id.button
import ru.otus.customview.R.id.pie_chart_view
import ru.otus.homework.customview.lineChart.SecondActivity
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private val payloadData: List<PayloadData> by readPayloadData()
    private val pieChartItemList: List<PieChartItem> by lazy {
        mapPayloadDataToPieChartItem(payloadData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pieChartView = findViewById<CustomViewPieChart>(pie_chart_view).apply {
            setOnSectionClickListener { section ->
                val totalAmount = pieChartItemList.sumOf { it.amount }
                val sectionAmount = section.pieChartItems.sumOf { it.amount }.toFloat()
                setCenterText("${(sectionAmount / totalAmount * 100).roundToInt()}%")
            }
        }

        findViewById<Spinner>(R.id.custom_view_variant).apply {
            adapter = createSpinnerAdapter()
            onItemSelectedListener = createSpinnerClickListener(pieChartView)
        }
        if (savedInstanceState == null) {
            pieChartView.setPieChartItems(pieChartItemList)
        }
        val button = findViewById<Button>(button)
        button.setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    private fun mapPayloadDataToPieChartItem(
        payloadData: List<PayloadData>
    ): List<PieChartItem> {
        return payloadData.map {
            PieChartItem(
                name = it.name,
                category = it.category,
                amount = it.amount
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

    private fun createSpinnerClickListener(view: CustomViewPieChart): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?,
                selectedItemPosition: Int,
                selectedId: Long
            ) {
                when (selectedItemPosition) {
                    0 -> view.visibility = ViewGroup.VISIBLE
                    else -> view.visibility = ViewGroup.GONE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }
    }
}