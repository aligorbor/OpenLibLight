package ru.geekbrains.android2.openliblight.view.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_details.*
import ru.geekbrains.android2.openliblight.R
import ru.geekbrains.android2.openliblight.presenter.book.BookPresenter
import ru.geekbrains.android2.openliblight.presenter.book.PresenterBookContract
import ru.geekbrains.android2.openliblight.utils.setImageFromUri
import java.util.*

class BookFragment : Fragment(), ViewBookContract {
    private val presenter: PresenterBookContract<ViewBookContract> = BookPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        arguments?.let {
            val title = it.getString(BookActivity.BOOK_TITLE_EXTRA)
            val author = it.getString(BookActivity.BOOK_AUTHOR_EXTRA)
            val cover = it.getString(BookActivity.BOOK_COVER_EXTRA)
            val raiting = it.getInt(BookActivity.BOOK_RAITING_EXTRA, 0)
            iv_cover.setImageFromUri(cover ?: "")
            tv_title.text =
                String.format(Locale.getDefault(), getString(R.string.title), title ?: "")
            tv_authors.text =
                String.format(Locale.getDefault(), getString(R.string.authors), author ?: "")
            presenter.setRaiting(raiting)
        }
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

        @JvmStatic
        fun newInstance(
            title: String,
            author: String,
            cover: String,
            raiting: Int
        ) =
            BookFragment().apply {
                arguments = bundleOf(
                    BOOK_TITLE_EXTRA to title,
                    BOOK_AUTHOR_EXTRA to author,
                    BOOK_COVER_EXTRA to cover,
                    BOOK_RAITING_EXTRA to raiting
                )
            }


    }
}