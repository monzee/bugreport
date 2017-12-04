package em.zed.bugreport

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import kotlinx.coroutines.experimental.CoroutineExceptionHandler
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch


/*
 * This file is a part of the bugreport project.
 */

class CoroExnHandler : AppCompatActivity() {
    lateinit var tv: TextView

    val handler = CoroutineExceptionHandler { _, throwable ->
        launch(UI) {
            tv.text = throwable.message
        }
    }

    private fun workaround(message: String?) {
        launch(UI) {
            tv.text = message
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hello)
        tv = findViewById(R.id.hello)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hello, menu)
        return true
    }

    @SuppressLint("SetTextI18n")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        launch(UI + handler) {
            repeat(3) {
                tv.text = "Crashing in ${3 - it}..."
                delay(1_000)
            }
            error("bang.")
        }
        return true
    }
}
