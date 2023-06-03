package com.example.projectakhir

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.example.projectakhir.data.ActionType
import com.example.projectakhir.data.InvoiceActivityAdapter
import com.example.projectakhir.data.InvoiceActivityEntity
import com.example.projectakhir.data.InvoiceActivityRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.properties.Delegates


class Detail : AppCompatActivity(){
    private lateinit var dInvoiceActivityRepository: InvoiceActivityRepository
    private var getId by Delegates.notNull<Int>()
    private lateinit var tvId: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvnama: TextView
    private lateinit var tvAlamat: TextView
    private lateinit var tvKota: TextView
    private lateinit var tvKodePos: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvNamaItem: TextView
    private lateinit var tvTanggal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setActionBarProperties(R.color.blue1, "Detail Invoice")

        dInvoiceActivityRepository = InvoiceActivityRepository(application)

        getId = intent.getIntExtra(MainActivity.EXTRA_ID, 0)
        initializeViews()
        if (getId != 0) {
            fillActivityData()
        } else {

        }

    }

    // fungsi untuk menampilkan judul dan warna pada action bar
    private fun setActionBarProperties(colorId: Int, title: String) {
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(this, colorId))
        )
        actionBar?.title = title
    }

    // membuat tombol more (3 titik) pada action bar jika id != 0
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return true
    }
    // membuat fungsi untuk menentukan action dari tombol more (3 titik) pada action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Nama: " + intent.getStringExtra(EXTRA_NAMA) + "\n" +
                            "Email: " + intent.getStringExtra(EXTRA_EMAIL) + "\n" +
                            "Alamat: " + intent.getStringExtra(EXTRA_ALAMAT) + "\n" +
                            "Kota: " + intent.getStringExtra(EXTRA_KOTA) + "\n" +
                            "Kode Pos: " + intent.getStringExtra(EXTRA_KODEPOS) + "\n" +
                            "Tanggal: " + intent.getStringExtra(EXTRA_TANGGAL) + "\n" +
                            "Item: " + intent.getStringExtra(EXTRA_NAMAITEM)
                    )

                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, "Bagikan melalui")
                startActivity(shareIntent)
            }

            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
    // membuat tombol kembali ke activity sebelumnya
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun fillActivityData() {

        tvId.text = getId.toString()
        tvnama.text = intent.getStringExtra(EXTRA_NAMA)
        tvTanggal.text = intent.getStringExtra(EXTRA_TANGGAL)
        tvAlamat.text = intent.getStringExtra(EXTRA_ALAMAT)
        tvKodePos.text = intent.getStringExtra(EXTRA_KODEPOS)
        tvKota.text = intent.getStringExtra(EXTRA_KOTA)
        tvEmail.text = intent.getStringExtra(EXTRA_EMAIL)
        tvNamaItem.text = intent.getStringExtra(EXTRA_NAMAITEM)

        val status = intent.getBooleanExtra(EXTRA_STATUS, true)
        if (status) {
            tvStatus.text = "Invoice Telah Divalidasi"
        } else {
            tvStatus.text = "Invoice Telah Divalidasi"
        }

    }

    // fungsi untuk mengindentifikasi id view yang digunakan
    private fun initializeViews() {

        tvId = findViewById(R.id.tvId)
        tvTanggal = findViewById(R.id.tvTanggal)
        tvKodePos = findViewById(R.id.tvKodePos)
        tvStatus = findViewById(R.id.tvStatus1)
        tvnama = findViewById(R.id.tvNama)
        tvAlamat = findViewById(R.id.tvAlamat)
        tvKota = findViewById(R.id.tvKota)
        tvEmail = findViewById(R.id.tvEmail)
        tvNamaItem = findViewById(R.id.tvNamaItem)


    }

    // fungsi untuk mengambil data dari intent
    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_TANGGAL = "extra_tanggal"
        const val EXTRA_STATUS = "extra_status"
        const val EXTRA_ALAMAT = "extra_alamat"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_KOTA = "extra_kota"
        const val EXTRA_KODEPOS = "extra_kodepos"
        const val EXTRA_NAMAITEM = "extra_namaitem"
    }
}
