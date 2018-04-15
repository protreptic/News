package name.peterbukhal.example.news.presentation.articles.impl

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotterknife.bindView
import name.peterbukhal.example.news.R

/**
 * Created by Peter Bukhal on 4/13/18.
 */
class ArticlesRecentAdapter(private var data: List<String> = listOf()) :
        RecyclerView.Adapter<ArticlesRecentAdapter.ArticleViewHolder>() {

    interface ArticlesRecentListener {

        fun onArticleRecentClicked(recentQuery: String)

    }

    fun setData(newData: List<String>) {
        data = newData

        notifyDataSetChanged()
    }

    var listener: ArticlesRecentListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ArticleViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_articles_recent_item, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        data[position].let { item -> holder.bindItem(item, listener) }
    }

    class ArticleViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val vTitle: TextView by bindView(R.id.vTitle)

        fun bindItem(text: String, lis: ArticlesRecentListener?) {
            vTitle.text = text

            itemView.setOnClickListener {
                lis?.onArticleRecentClicked(text)
            }
        }

    }

}