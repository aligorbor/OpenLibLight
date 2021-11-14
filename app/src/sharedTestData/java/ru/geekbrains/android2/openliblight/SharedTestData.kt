package ru.geekbrains.android2.openliblight

import androidx.core.os.bundleOf
import ru.geekbrains.android2.openliblight.view.book.BookFragment

internal const val TEST_RAITING = 100
internal const val TEST_TOTAL_COUNT = "100"
internal const val TEST_TITLE = "Title"
internal const val TEST_TITLE_BLANK = "Title: %s"
internal const val TEST_AUTHOR = "Author"
internal const val TEST_AUTHOR_BLANK = "Authors: %s"

internal const val TEST_COVER = "https://covers.openlibrary.org/b/ID/10551974-M.jpg"

internal const val TEST_RAITING_ZERO = "Raiting: 0"
internal const val TEST_RAITING_PLUS_1 = "Raiting: 1"
internal const val TEST_RAITING_MINUS_1 = "Raiting: -1"
internal const val TEST_RAITING_HUNDRED = "Raiting: 100"

internal const val TEST_SEARCH_QUERY = "some theme"
internal const val TEST_DISPLAY_ERROR_UNSUCCESSFUL = "Response is null or unsuccessful"
internal const val TEST_DISPLAY_ERROR_NULL = "Search results or total count are null"

internal const val TEST_BUTTON_INCR_TEXT = "+"
internal const val TEST_BUTTON_DECR_TEXT = "-"

internal const val TEST_SEARCH_TEXT = "cat"
internal const val TEST_SEARCH_TEXT_FOR_RESULT = "dog"
internal const val TEST_SEARCH_RAITING = 49
internal const val TEST_SEARCH_TEXT_RESULT = "Number of results: 49"
internal const val TEST_SEARCH_TEXT_RESULT_RAITING = "Raiting: 49"
internal const val TEST_SEARCH_TEXT_RESULT_RAITING_INCR = "Raiting: 50"
internal const val TEST_SEARCH_TEXT_RESULT_RAITING_DECR = "Raiting: 48"

internal const val TEST_SEARCH_TEXT_FOR_ZERO = "qqqqqqq"
internal const val TEST_SEARCH_TEXT_ZERO = "Number of results: 0"

internal const val TEST_DELAY = 3000L
internal const val TEST_DELAY_DESCR = "wait for 3 seconds"
internal const val TEST_TIMEOUT = 5000L

internal val ARG_BUNDLE = bundleOf(
    BookFragment.BOOK_TITLE_EXTRA to "The Detective Dog",
    BookFragment.BOOK_AUTHOR_EXTRA to "Julia Donaldson",
    BookFragment.BOOK_COVER_EXTRA to "https://covers.openlibrary.org/b/ID/10551974-M.jpg",
    BookFragment.BOOK_RAITING_EXTRA to TEST_SEARCH_RAITING
)

internal const val TEST_RECYCLE_SCROLL_TO = "Junket"
internal const val TEST_RECYCLE_CLICK_AT = 3
internal const val TEST_RECYCLE_CLICK_ON = "The dog"
internal const val TAP_ON_ITEM_WITH_ID_DESCRIPTION = "Нажимаем на view с указанным id"

