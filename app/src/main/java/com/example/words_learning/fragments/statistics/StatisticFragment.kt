package com.example.words_learning.fragments.statistics

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.example.words_learning.R
import com.example.words_learning.Router
import kotlinx.android.synthetic.main.fragment_statistic.view.*
import kotlinx.android.synthetic.main.main_application.view.*


class StatisticFragment : Fragment() {

    private lateinit var router : Router

    val name = "Statistics"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        router = Router(requireActivity(), R.id.fragment_container)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var layout = inflater.inflate(R.layout.fragment_statistic, container, false)

        layout = createButtons(layout)

        val text1 = layout.textView1
        text1.setText("Столько слов вы выучили за последний месяц: ")

        val text2 = layout.textView3
        text2.setText("Столько слов вы выучили за последние две недели: ")

        val text3 = layout.textView4
        text3.setText("Столько слов вы выучили за последнюю неделю: ")

        val listView = activity!!.findViewById<TextView>(R.id.textView2)
        listView.setText(name)

        return layout

    }

    private fun createButtons(layout: View) : View{


        //listView.adapter = model.createAdapter()
        //listView.onItemClickListener = this

        //findViewById<View>(R.id.add).setOnClickListener(this)
        //findViewById<View>(R.id.delete).setOnClickListener(this)

//        layout.add.setOnClickListener(addNewValue())

        return layout
    }
/*
    private fun addNewValue() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(resources.getString(R.string.enter_text))

        val l = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = l.inflate(R.layout.edit, null)
        val text = v.findViewById<View>(R.id.edit_text) as EditText
        alertDialog.setView(v)
        text.setText("Hello, World!")

        alertDialog.setPositiveButton(R.string.ok) { dialog, which ->
            val value = text.text.toString()
            model.addValue(value)
        }

        alertDialog.show()
    }
*/
    override fun onResume() {
        super.onResume()
        val listView = activity!!.findViewById<TextView>(R.id.textView2)
        listView.setText(name)
    }

}

