package name.peterbukhal.example.news.presentation.articles.impl

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotterknife.bindView
import name.peterbukhal.example.news.R
import name.peterbukhal.example.news.activity.abs.AbsFragment
import name.peterbukhal.example.news.presentation.articles.ArticlesPresenter
import name.peterbukhal.example.news.presentation.articles.ArticlesRouter
import name.peterbukhal.example.news.presentation.articles.ArticlesView
import name.peterbukhal.example.news.presentation.articles.article.impl.ArticleFragment
import name.peterbukhal.example.news.presentation.articles.model.ArticleModel
import name.peterbukhal.example.news.support.dismissKeyboard
import java.util.concurrent.TimeUnit

class ArticlesFragment : AbsFragment(), ArticlesView, ArticlesRouter {

    companion object {

        const val FRAGMENT_TAG = "TAG_ArticlesFragment"

        fun newInstance(): Fragment = ArticlesFragment().apply {
            arguments = Bundle.EMPTY
        }

    }

    override val targetLayout = R.layout.layout_articles

    private val vContent: ViewGroup by bindView(R.id.content)
    private val vLoading: View by bindView(R.id.loading)

    private val vError: TextView by bindView(R.id.error)
    private val vEmpty: TextView by bindView(R.id.empty)

    private val vClear: ImageButton by bindView(R.id.clear)
    private val vSearch: EditText by bindView(R.id.vSearch)
    private val vArticles: RecyclerView by bindView(R.id.vArticles)

    private val presenter: ArticlesPresenter by lazy {
        ArticlesPresenterImpl(router = this)
    }

    private val articlesAdapter = ArticlesAdapter()
    private val disposables = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vArticles.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articlesAdapter.apply {
                articlesListener = object : ArticlesListener {

                    override fun onArticleClicked(article: ArticleModel) {
                        adapter.notifyDataSetChanged()

                        presenter.displayArticle(article)
                    }
                }
            }
        }

        disposables.addAll(
            RxView
                .clicks(vClear)
                .subscribe({
                    vClear.visibility = View.GONE
                    vSearch.setText("")
                }),
            RxTextView
                .afterTextChangeEvents(vSearch)
                .skip(1)
                .debounce(800, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .map { pattern -> pattern.editable().toString() }
                .doOnNext { pattern ->
                    when (pattern.isEmpty()) {
                        true  -> {
                            presenter.displayArticles()

                            vClear.visibility = View.GONE
                        }
                        else -> {
                            presenter.displayArticles(pattern)

                            vClear.visibility = View.VISIBLE
                        }
                    }
                }
                .subscribe())

        presenter.attachView(this)
    }

    override fun showArticles(articles: List<ArticleModel>) {
        articlesAdapter.setData(articles)

        vContent.visibility = View.VISIBLE
        vLoading.visibility = View.GONE
        vError.visibility = View.GONE
        vEmpty.visibility = View.GONE
    }

    override fun toArticle(article: ArticleModel) {
        fragmentManager!!
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_in_right, R.anim.slide_out_left)
                .add(android.R.id.content,
                        ArticleFragment.newInstance(article.id),
                        ArticleFragment.FRAGMENT_TAG)
                .addToBackStack(null)
                .commit()

        dismissKeyboard(activity)
    }

    override fun showLoading() {
        vContent.visibility = View.GONE
        vLoading.visibility = View.VISIBLE
        vError.visibility = View.GONE
        vEmpty.visibility = View.GONE
    }

    override fun showEmpty() {
        vContent.visibility = View.GONE
        vLoading.visibility = View.GONE
        vError.visibility = View.GONE
        vEmpty.visibility = View.VISIBLE
    }

    override fun showError() {
        vContent.visibility = View.GONE
        vLoading.visibility = View.GONE
        vError.visibility = View.VISIBLE
        vEmpty.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()

        disposables.dispose()
        presenter.detachView()
    }

}