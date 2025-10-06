package dev.moorhen.diahelp
import android.app.Activity
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast

fun Toast.showIncorrectToast(message: String, activity: Activity) {
    val layout = activity.layoutInflater.inflate (
        R.layout.incorrect_value_toast,
        activity.findViewById(R.id.toast_container)
    )

    // Set the text of the TextView of the message
    val textView = layout.findViewById<TextView>(R.id.toast_text)
    textView.text = message

    // Use the application extension function
    this.apply {
        setGravity(Gravity.BOTTOM, 0, 40)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
}