package com.promi.view.promise

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.databinding.FragmentAllPromiseBinding
import com.promi.view.promise.adapter.PromiseRecyclerViewAdapter
import com.promi.viewmodel.group.GroupViewModel

class AllPromiseFragment : Fragment() {


    private var _binding: FragmentAllPromiseBinding? = null
    private val binding get() = _binding!!

    private lateinit var promiseRecyclerview : RecyclerView
    private lateinit var promiseRecyclerviewAdapter : PromiseRecyclerViewAdapter

    private lateinit var groupViewModel: GroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAllPromiseBinding.inflate(inflater, container, false)

        groupViewModel = ViewModelProvider(requireActivity())[GroupViewModel::class.java]

        //init recyclerview
        promiseRecyclerview = binding.recyclerviewPromise
        setRecyclerViewAdapter()

        //약속 생성 버튼 클릭시
        binding.btnCreateGroup.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_promise_to_navigation_create_group)
        }

        // promiseRecyclerviewAdapter.setList(groupViewModel.promises)
        // 현재 약속 목록 observe
        groupViewModel.promises.observe(viewLifecycleOwner) { promise ->
            promiseRecyclerviewAdapter.updateData(promise)
        }


        return binding.root
    }

    // init Group RecyclerView
    private fun setRecyclerViewAdapter(){
        promiseRecyclerview.layoutManager = LinearLayoutManager(context)
        promiseRecyclerviewAdapter = PromiseRecyclerViewAdapter()
        promiseRecyclerview.adapter = promiseRecyclerviewAdapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}