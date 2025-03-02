package com.pas.financetracker.data

import com.pas.financetracker.utils.AppConstants

data class ProgressState(val state: Int = AppConstants.DEFAULT_PROGRESS_STATE, val isShow: Boolean) {
}