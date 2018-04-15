package name.peterbukhal.example.news.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Article : RealmObject() {

    companion object {

        const val FIELD_ID = "id"
        const val FIELD_TITLE = "title"
        const val FIELD_DESCRIPTION = "description"
        const val FIELD_PUBLISHED_AT = "publishedAt"

    }

    @PrimaryKey
    var id: String = ""
    var urlToImage: String = ""
    var publishedAt: String = ""
    var title: String = ""
    var description: String = ""
    var url: String = ""

    var viewed: Boolean = false

}