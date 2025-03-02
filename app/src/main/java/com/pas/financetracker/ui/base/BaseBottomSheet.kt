package com.pas.financetracker.ui.base

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseBottomSheet<VM : ViewModel,B : ViewBinding> : BottomSheetDialogFragment() {

    protected var TAG: String = javaClass.simpleName
    protected var binding: B? = null
    protected lateinit var viewModel: VM
    protected var isFirstTime: Boolean = true
    protected var isSharedViewModel: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetStyle)
        viewModel = if (isSharedViewModel) {
            ViewModelProvider(requireActivity())[getViewModelType()]
        } else {
            ViewModelProvider(this)[getViewModelType()]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getBottomSheetBinding(inflater, container)
        return binding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet: FrameLayout? = d.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            ) as FrameLayout?
            BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
            if (bottomSheet != null) {
                val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
                behavior.isDraggable = false
            }
        }
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    fun setIsSharedViewModel() {
        isSharedViewModel = true
    }
    abstract fun getViewModelType(): Class<VM>
    open fun hideKeyboard(view: View) {
        val inputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    open fun showToast(value: String?) {
        Toast.makeText(requireContext(), value, Toast.LENGTH_LONG).show()
    }

    abstract fun getBottomSheetBinding(inflater: LayoutInflater, container: ViewGroup?): B
}