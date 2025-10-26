package dev.moorhen.diahelp.utils
import android.app.Activity
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import dev.moorhen.diahelp.R

fun Toast.showIncorrectToast(message: String, activity: Activity) {
    // 1️⃣ Inflate layout тоста без привязки к корневому view
    val layout = activity.layoutInflater.inflate(
        R.layout.incorrect_value_toast,
        null
    )

    // 2️⃣ Устанавливаем текст
    val textView = layout.findViewById<TextView>(R.id.toast_text)
    textView.text = message

    // 3️⃣ Применяем кастомный view к Toast
    this.apply {
        duration = Toast.LENGTH_LONG
        view = layout
        setGravity(Gravity.BOTTOM, 0, 250)
        show()
    }
}
