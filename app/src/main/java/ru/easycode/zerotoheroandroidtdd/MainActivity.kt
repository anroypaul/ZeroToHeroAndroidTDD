package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text
import java.io.Serializable

class MainActivity : AppCompatActivity() {


    private var state: State = State.Initial
    private lateinit var linearLayout: LinearLayout
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayout = findViewById<LinearLayout>(R.id.rootLayout)
        val button = findViewById<Button>(R.id.removeButton)
        textView = findViewById<TextView>(R.id.titleTextView)

        button.setOnClickListener {
            state = State.Remove
            state.apply(linearLayout, textView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(KEY, State::class.java) as State
        } else {
            savedInstanceState.getSerializable(KEY) as State
        }
        state.apply(linearLayout, textView)
    }

    companion object {
        private const val KEY = "key"
    }
}

interface State : Serializable {

    fun apply(linearLayout: LinearLayout, textView: TextView)

    object Initial : State {
        override fun apply(linearLayout: LinearLayout, textView: TextView) = Unit
    }

    object Remove : State {
        override fun apply(linearLayout: LinearLayout, textView: TextView) {
            linearLayout.removeView(textView)
        }
    }
}