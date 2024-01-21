package com.promi.view.myInformation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentEditProfileBinding
import com.promi.view.myInformation.adapter.IncludedGroupAdapter
import com.promi.view.myInformation.dialog.SelectGroupScopeDialog

class EditProfileFragment : BaseFragment<FragmentEditProfileBinding> (R.layout.fragment_edit_profile) {
    private lateinit var includedGroupAdapter: IncludedGroupAdapter

    override fun initAfterBinding() {
        super.initAfterBinding()

        // EditText change Event
        binding.etEditName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var nameSize = 0
                // if User input any text on EditText
                if(p0.toString().isNotEmpty()){
                    nameSize = p0.toString().length
                }
                binding.tvNameCounter.text = "${nameSize}/10" // Change name counter
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        includedGroupAdapter = IncludedGroupAdapter()

        binding.listGroupIncluded.adapter = includedGroupAdapter

        includedGroupAdapter.setItemClickListener(object : IncludedGroupAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                SelectGroupScopeDialog().show(parentFragmentManager, "SelectGroupScopeDialog")
            }
        })
    }
}