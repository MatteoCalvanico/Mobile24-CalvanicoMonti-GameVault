package it.unibo.gamevault.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.SearchView
import it.unibo.gamevault.R
import it.unibo.gamevault.ui.adapter.SearchGamesAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var searchBar: SearchView
    private lateinit var searchOptions: RadioGroup
    //private lateinit var searchAdapter: SearchGamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchBar = findViewById(R.id.searchBar)
        searchOptions = findViewById(R.id.searchOptions)

        //TODO: adapter e sue chiamate

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let{
                    CoroutineScope(Dispatchers.IO).launch {
                        //TODO: fare la chiamata API
                        /*
                        * Es:
                        * val searchResult = 'chiamata API con metodo che ritorna una lista'
                        *
                        * withContext(Dispatchers.Main) {
                        *    searchAdapter.updateSearchResults(searchResults) 'Metodo per aggiornare i dati del recycler'
                        * }
                        * */
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })
    }
}