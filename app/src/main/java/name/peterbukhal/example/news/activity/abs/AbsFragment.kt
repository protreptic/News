package name.peterbukhal.example.news.activity.abs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import name.peterbukhal.example.news.support.ViewBinder

abstract class AbsFragment : Fragment() {

    abstract val targetLayout: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(targetLayout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ViewBinder.setup(this, view)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        ViewBinder.tearDown(this)
    }

}