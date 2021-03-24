package tech.medina.drivertracking.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import tech.medina.drivertracking.databinding.LayoutDialogTwoOptionsBinding

class DialogWithTwoOptions(private val title: String? = null,
                           private val message: String? = null,
                           private val leftButtonText: String? = null,
                           private val rightButtonText: String? = null,
                           private val leftButtonFunction: (() -> Unit)? = null,
                           private val rightButtonFunction: (() -> Unit)? = null,
                             ): DialogFragment() {

    private lateinit var binding: LayoutDialogTwoOptionsBinding
    private var dismissedByUser: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutDialogTwoOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title?.let {
            binding.dialogTitle.visibility = View.VISIBLE
            binding.dialogTitle.text = it
        }
        message?.let {
            binding.dialogDescription.text = it
        }
        leftButtonText?.let {
            binding.dialogLeftButton.setText(it)
        }
        rightButtonText?.let {
            binding.dialogRightButton.setText(it)
        }
        //Right button is considered a deny or dismiss action button
        binding.dialogRightButton.setOnClickListener {
            rightButtonFunction?.invoke()
            dismiss()
            dismissedByUser = true
        }
        //Left button is considered a confirm or positive action button
        binding.dialogLeftButton.setOnClickListener {
            leftButtonFunction?.invoke()
            dismiss()
            dismissedByUser = true
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (!dismissedByUser) rightButtonFunction?.invoke()
    }

}