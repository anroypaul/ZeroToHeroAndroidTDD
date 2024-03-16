package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var state: State = State.Initial
    private lateinit var button: Button
    private lateinit var textView: TextView
    private lateinit var layout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.removeButton)
        layout = findViewById(R.id.rootLayout)
        textView = findViewById(R.id.titleTextView)

        button.setOnClickListener {
            state = State.Removed
            state.apply(layout, textView, button)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        state = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(KEY, State::class.java) as State
        } else {
            savedInstanceState.getSerializable(KEY) as State
        }
        state.apply(layout, textView,button)
    }

    companion object {
        private const val KEY = "KEY"
    }
}

interface State : Serializable {

    fun apply(linearLayout: LinearLayout, textView: TextView, button: Button)

    object Initial : State {
        override fun apply(linearLayout: LinearLayout, textView: TextView, button: Button) = Unit
    }

    object Removed : State {
        override fun apply(linearLayout: LinearLayout, textView: TextView, button: Button) {
            linearLayout.removeView(textView)
            button.isEnabled = false
        }
    }
}