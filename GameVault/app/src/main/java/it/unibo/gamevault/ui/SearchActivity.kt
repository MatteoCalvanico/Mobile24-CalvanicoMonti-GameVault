package it.unibo.gamevault.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        enableEdgeToEdge()

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
                1 -> Toast.makeText(this@SearchActivity, "Game series or game not found. Try the complete name", Toast.LENGTH_LONG).show()
                0 -> Toast.makeText(this@SearchActivity, "API ERROR", Toast.LENGTH_SHORT).show()
               -1 -> Toast.makeText(this@SearchActivity, "Ops something is went wrong...", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this@SearchActivity, "Ok now we have a problem", Toast.LENGTH_SHORT).show()
            }
        }

        //Observer ProgressBar - Show/Hidden ProgressBar
        viewModel.isLoading.observe(this) { isLoading -> progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE }

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let{
                    val queryType = searchOptions.checkedRadioButtonId //Save the type of query
                    if (queryType == R.id.optionMap){
                        val intent = Intent(this@SearchActivity, MapActivity::class.java).apply { putExtra("store_name", query) }
                        startActivity(intent)
                    }else{
                        val formattedQuery = query.trim().lowercase().replace(" ", "-") //The API need the game_slug in this-form
                        viewModel.searchGames(formattedQuery,queryType)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })
    }
}