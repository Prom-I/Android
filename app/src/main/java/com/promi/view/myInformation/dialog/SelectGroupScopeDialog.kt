package com.promi.view.myInformation.dialog

import com.promi.R
import com.promi.base.BaseBottomSheetDialog
import com.promi.databinding.DialogSelectGroupScopeBinding

class SelectGroupScopeDialog: BaseBottomSheetDialog<DialogSelectGroupScopeBinding> (R.layout.dialog_select_group_scope) {
    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.btnSelectScope.setOnClickListener {
            dismiss()
        }
    }
}