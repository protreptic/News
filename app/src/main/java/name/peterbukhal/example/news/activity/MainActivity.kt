package name.peterbukhal.example.news.activity

import android.os.Bundle
import name.peterbukhal.example.news.activity.abs.AbsActivity
import name.peterbukhal.example.news.presentation.articles.impl.ArticlesFragment

class MainActivity : AbsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(android.R.id.content,
                            ArticlesFragment.newInstance(),
                            ArticlesFragment.FRAGMENT_TAG)
                    .commit()
        }
    }

}