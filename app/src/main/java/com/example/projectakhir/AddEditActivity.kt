package com.example.projectakhir

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.projectakhir.data.InvoiceActivityEntity
import com.example.projectakhir.data.InvoiceActivityRepository
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates
import androidx.lifecycle.lifecycleScope
import java.util.*


class AddEditActivity : AppCompatActivity() {


    private lateinit var InvoiceActivityRepository: InvoiceActivityRepository

    // deklarasi id untuk menentukan apakah data akan diupdate atau ditambahkan
    private var getId by Delegates.notNull<Int>()
    private lateinit var edtNama: TextInputLayout
    private lateinit var edtAlamat: TextInputLayout
    private lateinit var edtEmail: TextInputLayout
    private lateinit var edtKota: TextInputLayout
    private lateinit var edtKodePos: TextInputLayout
    private lateinit var edtTanggal: TextInputLayout
    private lateinit var edtNamaItem: TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // Inisialisasi repository di dalam onCreate()
        InvoiceActivityRepository = InvoiceActivityRepository(application)
        getId = intent.getIntExtra(EXTRA_ID, 0)
        initializeViews()
        val saveButton = findViewById<Button>(R.id.btnSimpan)

        if (getId != 0) {
            setActionBarProperties(R.color.blue1, "Edit Invoice")
            fillActivityData()
        } else {
            setActionBarProperties(R.color.blue2, "Invoice Baru")
        }

        saveButton.setOnClickListener {
            val nama = edtNama.editText?.text.toString().trim()
            val alamat = edtAlamat.editText?.text.toString().trim()
            val kota = edtKota.editText?.text.toString().trim()
            val kode = edtKodePos.editText?.text.toString().trim()
            val tanggal = edtTanggal.editText?.text.toString().trim()
            val email = edtEmail.editText?.text.toString().trim()
            val namaitem = edtNamaItem.editText?.text.toString().trim()

            if (nama.isNullOrEmpty()) {
                showError("Nama tidak boleh kosong")
            } else if (alamat.isNullOrEmpty()) {
                showError("Alamat tidak boleh kosong")
            } else {
                if (getId != 0) {
                    UpdateDataToDatabase(getId, nama, alamat, kota, kode, tanggal, email, namaitem)
                } else {
                    insertDataToDatabase(nama, alamat, kota, kode, tanggal, email, namaitem)
                }
            }
        }

    }
    private fun setActionBarProperties(colorId: Int, title: String) {
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(this, colorId))
        )
        actionBar?.title = title
    }
    private fun initializeViews() {
        edtNama= findViewById(R.id.tfNama)
        edtAlamat= findViewById(R.id.tfAlamat)
        edtEmail= findViewById(R.id.tfEmail)
        edtKota= findViewById(R.id.tfKota)
        edtKodePos= findViewById(R.id.tfKode)
        edtTanggal= findViewById(R.id.tfTanggal)
        edtNamaItem= findViewById(R.id.tfNamaItem)
        edtTanggal.editText?.let { showDatePickerDialog(this, it) }


    }
    // fungsi untuk memasukkan data ke dalam view berdasarkan data yang dikirimkan dari adapter
    private fun fillActivityData() {
        edtNama.editText?.setText(intent.getStringExtra(EXTRA_NAMA))
        edtAlamat.editText?.setText(intent.getStringExtra(EXTRA_ALAMAT))
        edtEmail.editText?.setText(intent.getStringExtra(EXTRA_EMAIL))
        edtKota.editText?.setText(intent.getStringExtra(EXTRA_KOTA))
        edtKodePos.editText?.setText(intent.getStringExtra(EXTRA_KODEPOS))
        edtTanggal.editText?.setText(intent.getStringExtra(EXTRA_TANGGAL))
        edtNamaItem.editText?.setText(intent.getStringExtra(EXTRA_NAMAITEM))

    }
    // fungsi untuk menghapus data dari database
    private fun deleteDataFromDatabase(id: Int) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                InvoiceActivityRepository.deleteInvoiceActivityById(id)
            }
            showToast("Aktivitas berhasil dihapus")
            navigateToMainActivity()
        }
    }
    private fun insertDataToDatabase(

        namape: String,
        alamatpe: String,
        kota: String,
        kode: String,
        tanggal: String,
        email:String,
        namaitem: String ) {
        val InvoiceActivity = InvoiceActivityEntity(
            nama = namape, alamat = alamatpe, email = email,
            tanggal = tanggal, kota = kota, kodePos = kode, namaItem = namaitem )
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                InvoiceActivityRepository.insert(InvoiceActivity)
            }
            showToast("Invoice Berhasil Dimasukkan")
            navigateToMainActivity()
        }
    }

    private fun UpdateDataToDatabase(

        id: Int,
        nama: String,
        alamat: String,
        kota: String,
        kode: String,
        tanggal: String,
        email:String,
        namaitem :String,
        status: Boolean = false) {
        val InvoiceActivity = InvoiceActivityEntity(id,nama, alamat, email, tanggal,  kota, kode, namaitem, status)
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                InvoiceActivityRepository.update(InvoiceActivity)
            }
            showToast("Invoice Telah Berhasil Diubah")
            navigateToMainActivity()
        }
    }
    // fungsi untuk berpindah ke MainActivity
    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    // membuat tombol kembali ke activity sebelumnya
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // membuat tombol more (3 titik) pada action bar jika id != 0
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (getId != 0) menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }
    // membuat fungsi untuk menentukan action dari tombol more (3 titik) pada action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_valid -> {
                val nama = edtNama.editText?.text.toString().trim()
                val alamat = edtAlamat.editText?.text.toString().trim()
                val kota = edtKota.editText?.text.toString().trim()
                val kode = edtKodePos.editText?.text.toString().trim()
                val tanggal = edtTanggal.editText?.text.toString().trim()
                val email = edtEmail.editText?.text.toString().trim()
                val namaitem = edtNamaItem.editText?.text.toString().trim()

                UpdateDataToDatabase(getId,nama, alamat, kota, kode, tanggal, email, namaitem, true)
            }
            R.id.action_delete -> {
                deleteDataFromDatabase(getId)
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    fun showDatePickerDialog(context: Context, editText: EditText) {
        editText.isFocusableInTouchMode = false
        editText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, monthOfYear, dayOfMonth ->
                    // format tanggal sesuai kebutuhan Anda
                    val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                    editText.setText(selectedDate)
                }, year, month, day
            )
            datePickerDialog.show()
        }

    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_TANGGAL = "extra_tanggal"
        const val EXTRA_STATUS = "extra_status"
        const val EXTRA_ALAMAT = "extra_alamat"
        const val EXTRA_KOTA = "extra_kota"
        const val EXTRA_KODEPOS = "extra_kodepos"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_NAMAITEM = "extra_namaitem" }


}
