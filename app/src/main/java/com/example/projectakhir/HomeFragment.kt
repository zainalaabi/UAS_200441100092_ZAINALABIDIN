package com.example.projectakhir

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectakhir.data.ActionType
import com.example.projectakhir.data.InvoiceActivityAdapter
import com.example.projectakhir.data.InvoiceActivityRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var rvInvoiceActivity: RecyclerView
    private lateinit var tvEmptyActivity: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(toolbar)
        setActionBarProperties(R.color.blue1, "Home")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        rvInvoiceActivity = view.findViewById(R.id.rvHome)
        tvEmptyActivity = view.findViewById(R.id.tvEmptyActivity)

        // Setup RecyclerView
        setupRecyclerView()

        // Initialize the listener
        initListener(view)

        // Deklarasi repository
        val application = requireActivity().application
        val invoiceActivityRepository = InvoiceActivityRepository(application)

        // Fungsi untuk menampilkan data dari database ke RecyclerView
        invoiceActivityRepository.getAllInvoiceActivity().observe(viewLifecycleOwner) { invoiceActivity ->
            if (invoiceActivity.isEmpty()) {
                tvEmptyActivity.visibility = View.VISIBLE
            } else {
                tvEmptyActivity.visibility = View.GONE

                val adapter = InvoiceActivityAdapter(invoiceActivity, requireContext(), ActionType.ADD_EDIT).apply {
                    setHasStableIds(true)
                }
                rvInvoiceActivity.adapter = adapter
            }
        }

        return view
    }

    private fun setupRecyclerView() {
        // Setup RecyclerView adapter and layout manager
        val layoutManager = LinearLayoutManager(requireContext())
        rvInvoiceActivity.layoutManager = layoutManager
        rvInvoiceActivity.setHasFixedSize(true)
    }

    private fun initListener(view: View) {
        val btnTambah: FloatingActionButton = view.findViewById(R.id.btnTambah)
        btnTambah.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btnTambah) {
            val intent = Intent(requireContext(), AddEditActivity::class.java)
            startActivity(intent)
        }
    }
    private fun setActionBarProperties(colorId: Int, title: String) {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), colorId)))
        actionBar?.title = title
    }
}



