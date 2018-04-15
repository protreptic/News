package name.peterbukhal.example.news.presentation.articles.article.impl

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotterknife.bindView
import name.peterbukhal.example.news.R
import name.peterbukhal.example.news.activity.abs.AbsFragment
import name.peterbukhal.example.news.presentation.articles.article.ArticlePresenter
import name.peterbukhal.example.news.presentation.articles.article.ArticleView
import name.peterbukhal.example.news.presentation.articles.model.ArticleModel
import name.peterbukhal.example.news.support.glide.GlideApp

class ArticleFragment : AbsFragment(), ArticleView {

    companion object {

        const val FRAGMENT_TAG = "TAG_ArticleFragment"
        const val ARG_ARTICLE = "articleId"

        fun newInstance(articleId: String): Fragment = ArticleFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_ARTICLE, articleId)
            }
        }

    }

    override val targetLayout = R.layout.layout_article

    private val presenter: ArticlePresenter by lazy {
        ArticlePresenterImpl()
    }

    private val articleId: String get() {
        return arguments?.getString(ARG_ARTICLE) ?: ""
    }

    private val vImage: ImageView by bindView(R.id.vImage)
    private val vTitle: TextView by bindView(R.id.vTitle)
    private val vDescription: TextView by bindView(R.id.vDescription)
    private val vPublishedAt: TextView by bindView(R.id.vPublishedAt)
    private val vOpen: View by bindView(R.id.vOpen)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)
        presenter.displayArticle(articleId)
    }

    override fun showArticle(article: ArticleModel) {
        GlideApp.with(vImage)
                .load(article.imageUri)
                .centerCrop()
                .error(R.drawable.place_holder)
                .into(vImage)

        vTitle.text = article.title
        vPublishedAt.text = article.publishedAt
        vDescription.text = article.description
        vOpen.setOnClickListener {
            startActivity(Intent(ACTION_VIEW, Uri.parse(article.url)))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.detachView()
    }

}