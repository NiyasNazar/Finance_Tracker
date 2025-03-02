package com.pas.financetracker.ui.expense_manager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.acube.itms.utils.date_picker.DatePickerDialogFragment

import com.pas.financetracker.data.model.ExpenseEntity
import com.pas.financetracker.data.model.category.Categorymaster
import com.pas.financetracker.databinding.BottomSheetAddExpenseBinding
import com.pas.financetracker.ui.base.BaseBottomSheet
import com.pas.financetracker.utils.AppUtils
import com.pas.financetracker.utils.AppUtils.singleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AddExpenseBottomSheet() :
    BaseBottomSheet<ExpenseManagerViewModel, BottomSheetAddExpenseBinding>(),
    DatePickerDialogFragment.DatePickerListener {

    private var _callBack: CallBack? = null

    private var _categoryDataSet: ArrayList<Categorymaster> = arrayListOf()
    private var _selectedCategory: Categorymaster? = null


    var _amount: Double? = null
    var _date: String? = null
    var _notes: String? = null
    var _category: String? = null
    var isEdit: Boolean = false


    constructor(

        _popupCallBack: CallBack?
    ) : this() {
        this._callBack = _popupCallBack

    }


    companion object {
        private const val REQUEST_CODE_EXPENSE_DATE = 1

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        onClicks()
        initView()
    }

    private fun onClicks() {

        binding?.btnCancel?.singleClickListener {
            dismiss()
        }
        binding?.btnSave?.singleClickListener {
            _amount = binding?.etAmount?.text.toString().toDoubleOrNull()
            _notes = binding?.etNotes?.text.toString()
            _category = binding?.etCategory?.text.toString()

            if (isValidData()) {
                val expense = ExpenseEntity(
                    category = _selectedCategory?.categoryName,
                    amount = _amount,
                    date = _date,
                    notes = _notes,
                    receiptImage = null
                )
                viewModel.addExpense(expense)
                dismiss()
            }
        }
        binding?.etCategory?.singleClickListener {
            binding?.spinCategory?.show()
        }

        binding?.etDate?.singleClickListener {
            val datePicker = DatePickerDialogFragment(REQUEST_CODE_EXPENSE_DATE, this)
            datePicker.show(childFragmentManager, "datePicker")
        }




        binding?.spinCategory?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?, view: View, pos: Int, l: Long
                ) {

                    _selectedCategory = _categoryDataSet[pos]
                    binding?.etCategory?.setText(_categoryDataSet[pos].categoryName)
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }


    }


    private fun observeData() {
        viewModel.categories.observe(this) {
            _categoryDataSet.clear()
            _categoryDataSet.addAll(it)
            setUpSpinner(_categoryDataSet)
        }
        if (isEdit) {
            /* expirydate = _selectedData?.expirydate
            manufacturingdate = _selectedData?.manufacturingdate
            batchnumber = _selectedData?.batchnumber
            quantity = _selectedData?.quantity.toString()
            uom = UOMResp(UOMCode = _selectedData?.uomcode, UOMID = _selectedData?.uomid)
            _selectedPackingType = ValueListResp(
                DisplayText = _selectedData?.packingType,
                Value = _selectedData?.packingTypeId
            )
            _selectedProduct = ProductMaster(
                ProductName = _selectedData?.productName,
                ProductID = _selectedData?.productID
            )
            binding?.etQuantity?.setText(quantity)
            binding?.etPackingType?.setText(_selectedPackingType?.DisplayText)
            binding?.etProduct?.setText(_selectedProduct?.ProductName)
            binding?.etUom?.setText(uom?.UOMCode)
            binding?.etManufacturingDate?.setText(manufacturingdate)
            binding?.etExpiryDate?.setText(expirydate)
            binding?.etBatchNumber?.setText(batchnumber)*/

        }
    }


    private fun initView() {

        viewModel.fetchCategories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _callBack = null
    }

    override fun getViewModelType() = ExpenseManagerViewModel::class.java

    override fun getBottomSheetBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = BottomSheetAddExpenseBinding.inflate(inflater)

    interface CallBack {
        fun onExpenseSaved(

        )
    }

    private fun setUpSpinner(dataSet: ArrayList<Categorymaster>) {
        val arrayAdapter: ArrayAdapter<Categorymaster> = ArrayAdapter<Categorymaster>(
            requireContext(), android.R.layout.simple_spinner_item, dataSet
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.spinCategory?.adapter = arrayAdapter
    }


    private fun isValidData(): Boolean {
        var isValid = true
        if (_selectedCategory == null) {
            isValid = false
            showToast("Category Required")
        } else if (_amount==null) {
            isValid = false
            showToast("Amount Required")

        } else if (_date.isNullOrEmpty()) {
            isValid = false
            showToast("Date Required")

        }
        return isValid
    }

    override fun onDateSelected(requestCode: Int, year: Int, month: Int, dayOfMonth: Int) {
        val (isoDate, formattedDate) = AppUtils.formatDate(year, month, dayOfMonth)

        when (requestCode) {

            REQUEST_CODE_EXPENSE_DATE -> {
                binding?.etDate?.setText(formattedDate)
                _date = isoDate
            }


        }
    }
}

