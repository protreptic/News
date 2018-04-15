package name.peterbukhal.example.news.support

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun dismissKeyboard(activity: Activity?) {
    activity?.let {
        it.currentFocus?.let { view ->
            (activity
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}