package it.unibo.gamevault.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import it.unibo.gamevault.R
import com.bumptech.glide.Glide
import it.unibo.gamevault.Application
import it.unibo.gamevault.ui.viewModel.SharedViewModel
import it.unibo.gamevault.ui.viewModel.SharedViewModelFactory

class AddFavoriteDialog : BottomSheetDialogFragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var favoritePoster: ImageView

    private val sharedViewModel: SharedViewModel by activityViewModels {
        val repository = (requireActivity().application as Application).repository
        SharedViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_favorite_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progressBar)
        favoritePoster = view.findViewById(R.id.favoritePoster)

        //Close the dialog on Close click
        view.findViewById<Button>(R.id.btnClose).setOnClickListener{
            dismiss()
        }

        //Made the search, call the viewModel
        view.findViewById<SearchView>(R.id.searchBar).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    sharedViewModel.searchGame(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        //Observe gameResult LiveData
        sharedViewModel.gameResult.observe(viewLifecycleOwner) { game ->
            if (game != null) {
                Toast.makeText(requireContext(), "Favorite add", Toast.LENGTH_SHORT).show()
                Glide.with(this@AddFavoriteDialog)
                    .load(game.imgLink)
                    .placeholder(R.drawable.poster_placeholder)
                    .into(favoritePoster)

                sharedViewModel.clearGameResult()
            } else { //Game not found
                sharedViewModel.error.observe(viewLifecycleOwner) { error ->
                    Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //Observer ProgressBar - Show/Hidden ProgressBar
        sharedViewModel.isLoading.observe(this) { isLoading -> progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE }
    }
}