package name.peterbukhal.example.news.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Peter Bukhal on 4/15/18.
 */
open class SearchQuery : RealmObject() {

    companion object {

        const val FIELD_QUERY = "query"
        const val FIELD_QUERIED_AT = "queriedAt"
        const val FIELD_ARTICLES = "articles"
    }

    @PrimaryKey
    var query: String = ""
    var queriedAt: Long = 0
    var articles: RealmList<Article> = RealmList()

}