package it.unibo.gamevault.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import it.unibo.gamevault.Application
import it.unibo.gamevault.R
import it.unibo.gamevault.data.local.entity.GameLocalModel
import it.unibo.gamevault.data.local.entity.GamesVaultLocalModel
import it.unibo.gamevault.ui.model.Game
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

//Need to obtain the datePicker date without problem
interface OnDateSetListener {
    fun onDateSet(date: String)
}

class AddGameDialog : BottomSheetDialogFragment() {

    private var startDatePicked: String? = null
    private var endDatePicked: String? = null
    private var rating: Float? = null

    private var onDateSetListener: OnDateSetListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_game_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = (requireActivity().application as Application).repository
        val game = arguments?.getParcelable<Game>("game")

        //Close the dialog on cancel click
        view.findViewById<Button>(R.id.btnCancel).setOnClickListener{
            dismiss()
        }

        //Click to choose start date
        view.findViewById<Button>(R.id.btnStartDate).setOnClickListener {
            setOnDateSetListener(object : OnDateSetListener {
                override fun onDateSet(date: String) {
                    startDatePicked = date
                    view.findViewById<TextView>(R.id.dialogDateFieldS)?.text = startDatePicked
                }
            })
            showDatePickerDialog()
        }

        //Click to choose end date
        view.findViewById<Button>(R.id.btnEndDate).setOnClickListener {
            setOnDateSetListener(object : OnDateSetListener {
                override fun onDateSet(date: String) {
                    endDatePicked = date
                    view.findViewById<TextView>(R.id.dialogDateFieldE)?.text = endDatePicked
                }
            })
            showDatePickerDialog()
        }

        //Click on save
        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            rating = view.findViewById<RatingBar>(R.id.yourRating).rating
            val gameToSave = GameLocalModel(
                slug = game?.slug ?: "",
                imgLink = game?.backgroundImage ?: "",
                gameName = game?.name ?: "",
                yourRating = rating?.toDouble(),
                startDate = startDatePicked,
                endDate = endDatePicked
            )

            lifecycleScope.launch {

                //Save VaultGame info
                repository.insertGame(gameToSave)

                //Real add to Vault
                repository.insertGamesVault(GamesVaultLocalModel(gameToSave.slug))

                dismiss()
            }
        }
    }

    /**
     * Show a datePicker and update the listener for the value choose
     */
    private fun showDatePickerDialog() {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, selectedDay)

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val datePicked = dateFormat.format(selectedCalendar.time)
            onDateSetListener?.onDateSet(datePicked) //Update the listener


        }, year, month, day)

        datePickerDialog.show()
    }

    /**
     * Create listener for the datePicker
     */
    private fun setOnDateSetListener(listener: OnDateSetListener) {
        this.onDateSetListener = listener
    }
}