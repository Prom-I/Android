package com.promi.view.promise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.promi.databinding.FragmentPromiseDetailBinding
import com.promi.view.promise.adapter.PromiseParticipantsRecyclerViewAdapter

class PromiseDetailFragment : Fragment() {

    private lateinit var binding : FragmentPromiseDetailBinding

    private lateinit var participantsRecyclerViewAdapter: PromiseParticipantsRecyclerViewAdapter

    private val participants = listOf("이자민","이자민","이자민","이자민","이자민","이자민","이자민","이자민")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPromiseDetailBinding.inflate(layoutInflater)

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        participantsRecyclerViewAdapter = PromiseParticipantsRecyclerViewAdapter(participants)
        binding.recyclerviewPromiseMember.apply {
            adapter = participantsRecyclerViewAdapter
            layoutManager = GridLayoutManager(context, 5) // 한 줄에 5개의 아이템이 보이도록 설정
        }
    }
}