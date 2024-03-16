package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayout: LinearLayout
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayout = findViewById<LinearLayout>(R.id.rootLayout)
        val button = findViewById<Button>(R.id.removeButton)
        textView = findViewById<TextView>(R.id.titleTextView)

        button.setOnClickListener {
            linearLayout.removeView(textView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val removedTextView = linearLayout.childCount == 1
        outState.putBoolean(KEY, removedTextView)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val removeTextView = savedInstanceState.getBoolean(KEY)
        if (removeTextView) {
            linearLayout.removeView(textView)
        }
    }

    companion object {
        private const val KEY = "key"
    }
}