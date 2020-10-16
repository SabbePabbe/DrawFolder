package com.example.gestureunlock.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.gestureunlock.R
import kotlinx.coroutines.NonCancellable.cancel

class CreateFileFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.dialog_create_file)
                //use .setView with a custom layout file to set the contents of the dialog to include a field for name and something about access
                .setPositiveButton(R.string.create,
                    DialogInterface.OnClickListener { dialog, id ->
                        // TODO insert code for creating a file and navigating to file fragment
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        // TODO i guess just return to home fragment?
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
