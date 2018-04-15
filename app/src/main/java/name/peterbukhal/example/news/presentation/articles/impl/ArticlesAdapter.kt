package name.peterbukhal.example.news.presentation.articles.impl

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotterknife.bindView
import name.peterbukhal.example.news.R
import name.peterbukhal.example.news.presentation.articles.model.ArticleModel
import name.peterbukhal.example.news.support.glide.GlideApp

/**
 * Created by Peter Bukhal on 4/13/18.
 */
class ArticlesAdapter(private var data: List<ArticleModel> = listOf()) :
        RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    interface ArticlesListener {

        fun onArticleClicked(article: ArticleModel)
    }

    fun setData(newData: List<ArticleModel>) {
        data = newData

        notifyDataSetChanged()
    }

    var listener: ArticlesListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ArticleViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_articles_item, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        data[position].let { item -> holder.bindItem(item, position) }
    }

    inner class ArticleViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val vImage: ImageView by bindView(R.id.vImage)
        private val vViewed: TextView by bindView(R.id.vViewed)
        private val vTitle: TextView by bindView(R.id.vTitle)

        fun bindItem(article: ArticleModel, position: Int) {
            article.let {
                GlideApp.with(itemView)
                        .load(it.imageUri)
                        .centerCrop()
                        .error(R.drawable.place_holder)
                        .into(vImage)

                vViewed.visibility = if (it.viewed) View.VISIBLE else View.GONE
                vTitle.text = it.title

                itemView.setOnClickListener {
                    article.viewed = true

                    listener?.onArticleClicked(article)

                    notifyItemChanged(position)
                }
            }
        }

    }

}