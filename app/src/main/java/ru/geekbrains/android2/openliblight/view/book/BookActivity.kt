package ru.geekbrains.android2.openliblight.view.book

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*
import ru.geekbrains.android2.openliblight.R
import ru.geekbrains.android2.openliblight.presenter.book.BookPresenter
import ru.geekbrains.android2.openliblight.presenter.book.PresenterBookContract
import ru.geekbrains.android2.openliblight.utils.setImageFromUri
import java.util.*

class BookActivity : AppCompatActivity(), ViewBookContract {

    private val presenter: PresenterBookContract<ViewBookContract> = BookPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setUI()
    }

    override fun onStart() {
        super.onStart()
        presenter.onAttach(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onDetach()
    }

    private fun setUI() {
        val title = intent.getStringExtra(BOOK_TITLE_EXTRA)
        val author = intent.getStringExtra(BOOK_AUTHOR_EXTRA)
        val cover = intent.getStringExtra(BOOK_COVER_EXTRA)
        val raiting = intent.getIntExtra(BOOK_RAITING_EXTRA, 0)
        iv_cover.setImageFromUri(cover ?: "")
        tv_title.text = String.format(Locale.getDefault(), getString(R.string.title), title ?: "")
        tv_authors.text = String.format(Locale.getDefault(), getString(R.string.authors), author ?: "")
        presenter.setRaiting(raiting)
        setRaitingText(presenter.getRaiting())
        btn_decr.setOnClickListener { presenter.onDecrement() }
        btn_incr.setOnClickListener { presenter.onIncrement() }
    }

    override fun setRaiting(raiting: Int) {
        setRaitingText(raiting)
    }

    private fun setRaitingText(raiting: Int) {
        tv_raiting.text =
            String.format(Locale.getDefault(), getString(R.string.raiting_count), raiting)
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