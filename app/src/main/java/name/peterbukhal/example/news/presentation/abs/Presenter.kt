package name.peterbukhal.example.news.presentation.abs

interface Presenter<in T: View> {

    fun attachView(view: T)
    fun detachView()

}