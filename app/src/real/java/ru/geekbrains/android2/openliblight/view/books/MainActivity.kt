package ru.geekbrains.android2.openliblight.view.books

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrains.android2.openliblight.R
import ru.geekbrains.android2.openliblight.model.Work
import ru.geekbrains.android2.openliblight.repository.OpenLibApi
import ru.geekbrains.android2.openliblight.repository.OpenLibRepository
import ru.geekbrains.android2.openliblight.repository.RepositoryContract
import ru.geekbrains.android2.openliblight.view.book.BookActivity
import java.util.*

class MainActivity : AppCompatActivity(), ViewBooksContract, BooksAdapter.Delegate {
    private val adapter = BooksAdapter(this)

    //    private val presenter: PresenterBooksContract<ViewBooksContract> =
//        BooksPresenter(createRepository())
    private val viewModel: BooksViewModel by lazy {
        ViewModelProvider(this).get(BooksViewModel::class.java)
    }
    private var totalCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUI()
        viewModel.subscribeToLiveData().observe(this) {
            onStateChange(it)
        }
    }

    private fun onStateChange(screenState: ScreenState) {
        when (screenState) {
            is ScreenState.Working -> {
                displayLoading(false)
                val searchResponse = screenState.searchResponse
                displaySearchResults(
                    searchResponse.works ?: listOf(),
                    searchResponse.workCount ?: "0"
                )
            }
            is ScreenState.Loading -> {
                displayLoading(true)
            }
            is ScreenState.Error -> {
                displayLoading(false)
                displayError(screenState.error.message ?: getString(R.string.undefined_error))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //      presenter.onAttach(this)
    }

    override fun onStop() {
        super.onStop()
        //     presenter.onDetach()
    }

    private fun setUI() {
        setQueryListener()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    private fun setQueryListener() {
        searchEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchQuery()
            }
            false
        })
        search_fab.setOnClickListener {
            searchQuery()
        }
    }

    private fun searchQuery(): Boolean {
        val query = searchEditText.text.toString()
        if (query.isNotBlank()) {
            //   presenter.searchOpenLib(query)
            viewModel.searchOpenLib(query)
            return true
        } else {
            Toast.makeText(
                this@MainActivity,
                getString(R.string.enter_search_word),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
    }


    private fun createRepository(): RepositoryContract {
        return OpenLibRepository(createRetrofit().create(OpenLibApi::class.java))
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }


    override fun displaySearchResults(searchResults: List<Work>, totalCount: String) {
        adapter.updateResults(searchResults)
        resultsCountTextView.text =
            String.format(Locale.getDefault(), getString(R.string.results_count), totalCount)
        this.totalCount = totalCount.toInt()
        resultsCountTextView.visibility = View.VISIBLE
    }

    override fun displayError() {
        Toast.makeText(this, getString(R.string.undefined_error), Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun displayLoading(show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onBookPicked(book: Work) {
        startActivity(
            BookActivity.getIntent(
                this,
                book.title,
                book.authors[0].name,
                "https://covers.openlibrary.org/b/ID/${book.coverId}-M.jpg",
                totalCount
            )
        )
    }

    companion object {
        const val BASE_URL = "https://openlibrary.org"
    }


}