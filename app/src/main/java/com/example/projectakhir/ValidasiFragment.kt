package com.example.projectakhir

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectakhir.data.ActionType
import com.example.projectakhir.data.InvoiceActivityAdapter
import com.example.projectakhir.data.InvoiceActivityRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ValidasiFragment : Fragment(){


    private lateinit var rvInvoiceActivity: RecyclerView
    private lateinit var tvEmptyActivity: TextView

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(toolbar)
            setActionBarProperties(R.color.blue1, "Detail Invoice")
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_validasi, container, false)


        rvInvoiceActivity = view.findViewById(R.id.rvValid)
        tvEmptyActivity = view.findViewById(R.id.tvEmptyActivity)

        setupRecyclerView()

        // deklarasi repository
        val application = requireActivity().application
        val invoiceActivityRepository = InvoiceActivityRepository(application)

        // fungsi untuk menampilkan data dari database ke recyclerview
        invoiceActivityRepository.getAllValidInvoiceActivity().observe(viewLifecycleOwner) { invoiceActivity ->
            if (invoiceActivity.isEmpty()) {
                tvEmptyActivity.visibility = View.VISIBLE
            } else {
                tvEmptyActivity.visibility = View.GONE

                val adapter = InvoiceActivityAdapter(invoiceActivity, requireContext(),
                    ActionType.DETAIL).apply {
                    setHasStableIds(true)
                }
                rvInvoiceActivity.adapter = adapter
            }
        }

        return view
    }

    private fun setupRecyclerView() {
        rvInvoiceActivity.layoutManager = LinearLayoutManager(requireContext())
        rvInvoiceActivity.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }
    private fun setActionBarProperties(colorId: Int, title: String) {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), colorId)))
        actionBar?.title = title
    }

}