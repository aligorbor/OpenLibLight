package ru.geekbrains.android2.openliblight.view.book

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.android2.openliblight.R

class BookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportFragmentManager.beginTransaction()
            .add(
                R.id.detailsFragmentContainer,
                BookFragment.newInstance(
                    intent.getStringExtra(BOOK_TITLE_EXTRA) ?: "",
                    intent.getStringExtra(BOOK_AUTHOR_EXTRA) ?: "",
                    intent.getStringExtra(BOOK_COVER_EXTRA) ?: "",
                    intent.getIntExtra(BOOK_RAITING_EXTRA, 0)
                )
            )
            .commitAllowingStateLoss()
    }


    companion object {
        const val BOOK_TITLE_EXTRA = "BOOK_TITLE_EXTRA"
        const val BOOK_AUTHOR_EXTRA = "BOOK_AUTHOR_EXTRA"
        const val BOOK_COVER_EXTRA = "BOOK_COVER_EXTRA"
        const val BOOK_RAITING_EXTRA = "BOOK_RAITING_EXTRA"

        fun getIntent(
            context: Context,
            title: String,
            author: String,
            cover: String,
            raiting: Int
        ): Intent {
            return Intent(context, BookActivity::class.java).apply {
                putExtra(BOOK_TITLE_EXTRA, title)
                putExtra(BOOK_AUTHOR_EXTRA, author)
                putExtra(BOOK_COVER_EXTRA, cover)
                putExtra(BOOK_RAITING_EXTRA, raiting)
            }
        }
    }
}