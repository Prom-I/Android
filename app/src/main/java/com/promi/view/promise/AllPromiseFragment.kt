package com.promi.view.promise

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.databinding.FragmentAllPromiseBinding
import com.promi.util.VerticalSpaceItemDecoration
import com.promi.view.promise.adapter.PromiseItemClickListener
import com.promi.view.promise.adapter.PromiseRecyclerViewAdapter
import com.promi.viewmodel.group.GroupViewModel

class AllPromiseFragment : Fragment(),PromiseItemClickListener {

    // 약속 타입 정의
    private val PROGRESS = 0 // 진행중인 약속
    private val DONE = 1 // 끝난 약속

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
            findNavController().navigate(R.id.action_navigation_all_promises_and_groups_to_selectGroupFragment)
        }

        // promiseRecyclerviewAdapter.setList(groupViewModel.promises)
        // 현재 약속 목록 observe
        groupViewModel.promises.observe(viewLifecycleOwner) { promise ->
            promiseRecyclerviewAdapter.updateData(promise)
        }


        return binding.root
    }

    // Promise Item Click Event Delegate
    override fun onPromiseItemClicked(positio: Int,type : Int) {
        if (type == PROGRESS) {
            findNavController().navigate(R.id.action_navigation_all_promises_and_groups_to_viewPromiseTimeFragment)
        } else if (type == DONE){
            findNavController().navigate(R.id.action_navigation_all_promises_and_groups_to_promiseDetailFragment)
        }

    }

    // init Group RecyclerView
    private fun setRecyclerViewAdapter(){
        promiseRecyclerview.layoutManager = LinearLayoutManager(context)
        promiseRecyclerviewAdapter = PromiseRecyclerViewAdapter(this)
        promiseRecyclerview.adapter = promiseRecyclerviewAdapter
        var verticalSpaceItemDecoration = VerticalSpaceItemDecoration(dipToPx(16f,requireContext()))
        verticalSpaceItemDecoration.setOption(true)
        promiseRecyclerview.addItemDecoration(verticalSpaceItemDecoration)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun dipToPx(dipValue: Float, context: Context): Int{
        return (dipValue * context.resources.displayMetrics.density).toInt()
    }

}