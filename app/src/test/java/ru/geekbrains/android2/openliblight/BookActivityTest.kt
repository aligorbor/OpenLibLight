package ru.geekbrains.android2.openliblight

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import ru.geekbrains.android2.openliblight.view.book.BookActivity

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class BookActivityTest {
    private lateinit var scenario: ActivityScenario<BookActivity>
    private lateinit var context: Context

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(BookActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activityTextView_Title_NotNull() {
        scenario.onActivity {
            val tv = it.findViewById<TextView>(R.id.tv_title)
            TestCase.assertNotNull(tv)
        }
    }

    @Test
    fun activityTextView_Authors_NotNull() {
        scenario.onActivity {
            val tv = it.findViewById<TextView>(R.id.tv_authors)
            TestCase.assertNotNull(tv)
        }
    }

    @Test
    fun activityTextView_Raiting_NotNull() {
        scenario.onActivity {
            val tv = it.findViewById<TextView>(R.id.tv_raiting)
            TestCase.assertNotNull(tv)
        }
    }

    @Test
    fun activityImageView_Cover_NotNull() {
        scenario.onActivity {
            val iv = it.findViewById<ImageView>(R.id.iv_cover)
            TestCase.assertNotNull(iv)
        }
    }

    @Test
    fun activityTextView_Title_HasText() {
        scenario.onActivity {
            val tv = it.findViewById<TextView>(R.id.tv_title)
            TestCase.assertEquals("Title: ", tv.text)
        }
    }

    @Test
    fun activityTextView_Authors_HasText() {
        scenario.onActivity {
            val tv = it.findViewById<TextView>(R.id.tv_authors)
            TestCase.assertEquals("Authors: ", tv.text)
        }
    }

    @Test
    fun activityTextView_Raitng_HasText() {
        scenario.onActivity {
            val tv = it.findViewById<TextView>(R.id.tv_raiting)
            TestCase.assertEquals("Raiting: 0", tv.text)
        }
    }

    @Test
    fun activityTextView_Title_IsVisible() {
        scenario.onActivity {
            val tv = it.findViewById<TextView>(R.id.tv_title)
            TestCase.assertEquals(View.VISIBLE, tv.visibility)
        }
    }

    @Test
    fun activityTextView_Authors_IsVisible() {
        scenario.onActivity {
            val tv = it.findViewById<TextView>(R.id.tv_authors)
            TestCase.assertEquals(View.VISIBLE, tv.visibility)
        }
    }

    @Test
    fun activityTextView_Raitng_IsVisible() {
        scenario.onActivity {
            val tv = it.findViewById<TextView>(R.id.tv_raiting)
            TestCase.assertEquals(View.VISIBLE, tv.visibility)
        }
    }

    @Test
    fun activityButtons_AreVisible() {
        scenario.onActivity {
            val btnDecr = it.findViewById<Button>(R.id.btn_decr)
            TestCase.assertEquals(View.VISIBLE, btnDecr.visibility)

            val btnIncr = it.findViewById<Button>(R.id.btn_incr)
            TestCase.assertEquals(View.VISIBLE, btnIncr.visibility)
        }
    }

    @Test
    fun activityButtonIncr_IsWorking() {
        scenario.onActivity {
            val btnIncr = it.findViewById<Button>(R.id.btn_incr)
            val tv = it.findViewById<TextView>(R.id.tv_raiting)
            btnIncr.performClick()
            TestCase.assertEquals("Raiting: 1", tv.text)
        }
    }

    @Test
    fun activityButtonDecr_IsWorking() {
        scenario.onActivity {
            val btnDecr = it.findViewById<Button>(R.id.btn_decr)
            val tv = it.findViewById<TextView>(R.id.tv_raiting)
            btnDecr.performClick()
            TestCase.assertEquals("Raiting: -1", tv.text)
        }
    }

    @Test
    fun activityCreateIntent_NotNull() {
        val intent = BookActivity.getIntent(
            context = context,
            title = "Title",
            author = "Author",
            cover = "https://covers.openlibrary.org/b/ID/123-M.jpg",
            raiting = 0
        )
        TestCase.assertNotNull(intent)
    }

    @Test
    fun activityCreateIntent_HasExtras() {
        val intent = BookActivity.getIntent( context = context,
            title = "Title",
            author = "Author",
            cover = "https://covers.openlibrary.org/b/ID/123-M.jpg",
            raiting = 0)
        val bundle = intent.extras
        TestCase.assertNotNull(bundle)
    }

    @Test
    fun activityCreateIntent_HasExtrasValues() {
        val title = "Title"
        val author = "Author"
        val cover = "https://covers.openlibrary.org/b/ID/123-M.jpg"
        val raiting = 100
        val intent = BookActivity.getIntent(context, title,author,cover,raiting)
        val bundle = intent.extras
        TestCase.assertEquals(title, bundle?.getString(BookActivity.BOOK_TITLE_EXTRA, ""))
        TestCase.assertEquals(author, bundle?.getString(BookActivity.BOOK_AUTHOR_EXTRA, ""))
        TestCase.assertEquals(cover, bundle?.getString(BookActivity.BOOK_COVER_EXTRA, ""))
        TestCase.assertEquals(raiting, bundle?.getInt(BookActivity.BOOK_RAITING_EXTRA, 0))
    }

    @After
    fun close() {
        scenario.close()
    }
}