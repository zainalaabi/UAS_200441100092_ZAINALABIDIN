package com.example.projectakhir.data

import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_EMAIL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.projectakhir.AddEditActivity
import com.example.projectakhir.AddEditActivity.Companion.EXTRA_ALAMAT
import com.example.projectakhir.AddEditActivity.Companion.EXTRA_ID
import com.example.projectakhir.AddEditActivity.Companion.EXTRA_KODEPOS
import com.example.projectakhir.AddEditActivity.Companion.EXTRA_KOTA
import com.example.projectakhir.AddEditActivity.Companion.EXTRA_NAMA
import com.example.projectakhir.AddEditActivity.Companion.EXTRA_STATUS
import com.example.projectakhir.AddEditActivity.Companion.EXTRA_TANGGAL
import com.example.projectakhir.AddEditActivity.Companion.EXTRA_NAMAITEM
import com.example.projectakhir.AddEditActivity.Companion.EXTRA_EMAIL
import com.example.projectakhir.Detail
import com.example.projectakhir.MainActivity
import com.example.projectakhir.R



enum class ActionType {
    ADD_EDIT,
    DETAIL
}
class InvoiceActivityAdapter(
    private val list: List<InvoiceActivityEntity>,
    private val context: Context,
    private val actionType: ActionType
) :
    RecyclerView.Adapter<InvoiceActivityAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kode: TextView = itemView.findViewById(R.id.tvId)
        val nama: TextView = itemView.findViewById(R.id.tvNama)
        val tanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        val status: TextView = itemView.findViewById(R.id.tvStatus)
        val alamat :TextView = itemView.findViewById(R.id.tvAlamat)
        val kota :TextView = itemView.findViewById(R.id.tvKota)
        val kodepos :TextView = itemView.findViewById(R.id.tvKodePos)
        val email :TextView = itemView.findViewById(R.id.tvEmail)
        val namaItem :TextView = itemView.findViewById(R.id.tvNamaBarang)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cardlist, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.nama.text = item.nama
        holder.tanggal.text = item.tanggal
        holder.alamat.text = item.alamat
        holder.kodepos.text = item.kodePos
        holder.email.text = item.email
        holder.namaItem.text = item.namaItem
        holder.kota.text = item.kota
        holder.kode.text = item.id.toString()

        val statusString = item.status.toString()
        holder.status.text = statusString

        val status = item.status // Ubah ini sesuai dengan sumber data status yang sesuai

        if (status) {
            holder.status.text = "Invoice Telah Divalidasi"
        } else {
            holder.status.text = "Invoice Belum Divalidasi"
        }


        holder.itemView.setOnClickListener {
            val intent = Intent(context, getActivityClass(actionType))
            intent.putExtra(EXTRA_ID, list[position].id)
            intent.putExtra(EXTRA_NAMA, list[position].nama)
            intent.putExtra(EXTRA_TANGGAL, list[position].tanggal)
            intent.putExtra(EXTRA_STATUS, list[position].status)
            intent.putExtra(EXTRA_ALAMAT,list[position].alamat)
            intent.putExtra(EXTRA_KOTA,list[position].kota)
            intent.putExtra(EXTRA_KODEPOS,list[position].kodePos)
            intent.putExtra(AddEditActivity.EXTRA_EMAIL,list[position].email)
            intent.putExtra(EXTRA_NAMAITEM,list[position].namaItem)
            context.startActivity(intent)
        }
    }

    private fun getActivityClass(actionType: ActionType): Class<out AppCompatActivity> {
        return when (actionType) {
            ActionType.ADD_EDIT -> AddEditActivity::class.java
            ActionType.DETAIL -> Detail::class.java
        }
    }
}


