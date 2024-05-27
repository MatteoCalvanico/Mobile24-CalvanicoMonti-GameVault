package it.unibo.gamevault.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import it.unibo.gamevault.R
import it.unibo.gamevault.data.sources.repositories.GameRepositoryImpl
import it.unibo.gamevault.data.sources.GamesApi
import it.unibo.gamevault.ui.adapter.SearchGamesAdapter
import it.unibo.gamevault.ui.mapper.GameMapperImpl
import it.unibo.gamevault.ui.model.Game
import it.unibo.gamevault.ui.viewModel.SearchViewModel
import it.unibo.gamevault.ui.viewModel.SearchViewModelFactory

class SearchActivity : AppCompatActivity() {

    private lateinit var searchBar: SearchView
    private lateinit var searchOptions: RadioGroup
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var searchAdapter: SearchGamesAdapter
    private var searchResults: List<Game> = emptyList()

    private val viewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(GameRepositoryImpl(GamesApi.rootService, GameMapperImpl()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchBar = findViewById(R.id.searchBar)
        searchOptions = findViewById(R.id.searchOptions)
        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progressBar)

        searchAdapter = SearchGamesAdapter(searchResults)
        recyclerView.adapter = searchAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Observer API response - Show the games
        viewModel.searchResults.observe(this) { games -> games?.let { searchAdapter.updateSearchResults(it) } }

        //Observer error - Show SnackBar
        viewModel.error.observe(this) { error ->
            when(error){
                1 -> Snackbar.make(findViewById(android.R.id.content), "Game series or game not found. Try the complete name", Snackbar.LENGTH_LONG).show()
                0 -> Snackbar.make(findViewById(android.R.id.content), "API ERROR", Snackbar.LENGTH_LONG).show()
               -1 -> Snackbar.make(findViewById(android.R.id.content), "Ops something is went wrong...", Snackbar.LENGTH_LONG).show()
                else -> Snackbar.make(findViewById(android.R.id.content), "Ok now we have a problem", Snackbar.LENGTH_LONG).show()
            }
        }

        //Observer ProgressBar - Show/Hidden ProgressBar
        viewModel.isLoading.observe(this) { isLoading -> progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE }

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let{
                    val formattedQuery = query.trim().lowercase().replace(" ", "-") //The API need the game_slug in this-form

                    val queryType = searchOptions.checkedRadioButtonId //Save the type of query
                    viewModel.searchGames(formattedQuery,queryType)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })
    }
}