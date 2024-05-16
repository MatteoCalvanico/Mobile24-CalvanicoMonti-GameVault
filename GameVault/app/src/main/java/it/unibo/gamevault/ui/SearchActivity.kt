package it.unibo.gamevault.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import android.util.Log;
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.unibo.gamevault.R
import it.unibo.gamevault.ui.adapter.SearchGamesAdapter
import it.unibo.gamevault.ui.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchActivity : AppCompatActivity() {

    private lateinit var searchBar: SearchView
    private lateinit var searchOptions: RadioGroup
    private lateinit var recyclerView: RecyclerView

    private lateinit var searchAdapter: SearchGamesAdapter
    private var searchResults: List<Game> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchBar = findViewById(R.id.searchBar)
        searchOptions = findViewById(R.id.searchOptions)
        recyclerView = findViewById(R.id.recycler_view)

        searchAdapter = SearchGamesAdapter(searchResults)
        recyclerView.adapter = searchAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let{

                    val queryType = searchOptions.checkedRadioButtonId //Save the type of query
                    CoroutineScope(Dispatchers.IO).launch {

                        val apiResult: List<Game> = emptyList() //TODO: spostare l'inizializzazione con la chiamata API

                        //We do a different query based on the radioButton checked
                        when(queryType){ //TODO: fare la chiamata API
                            R.id.optionSeries -> Log.d("SEARCH_BY_SERIES","Search series")
                            R.id.optionGame -> Log.d("SEARCH_BY_GAME","Search game")
                            R.id.optionGame -> Log.d("SEARCH_BY_MAP","Search map")
                        }

                        withContext(Dispatchers.Main){
                            searchResults = apiResult
                            searchAdapter.updateSearchResults(searchResults)
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })
    }
}