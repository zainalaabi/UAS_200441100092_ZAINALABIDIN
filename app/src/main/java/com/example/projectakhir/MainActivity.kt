package com.example.projectakhir

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.projectakhir.data.InvoiceActivityRepository
import com.example.projectakhir.data.InvoiceActivityRoomDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates



class MainActivity : AppCompatActivity() {
    // deklarasi id untuk menentukan apakah data akan diupdate atau ditambahkan
    private var getId by Delegates.notNull<Int>()
    private lateinit var InvoiceActivityRepository: InvoiceActivityRepository


    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, HomeFragment()).commit()
                    return@OnNavigationItemSelectedListener true
                }R.id.nav_valid -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, ValidasiFragment()).commit()
                setActionBarProperties(R.color.blue, "CBOOK")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, ProfileFragment()).commit()
                    setActionBarProperties(R.color.colorAddDailyActivity, "CBOOK")
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, HomeFragment()).commit()
        InvoiceActivityRepository = InvoiceActivityRepository(application)

        getId = intent.getIntExtra(MainActivity.EXTRA_ID, 0)

    }

    private fun setActionBarProperties(colorId: Int, title: String) {
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(this, colorId))
        )
        actionBar?.title = title
    }

    // membuat tombol more (3 titik) pada action bar jika id != 0
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete, menu)
        return true
    }

    // membuat fungsi untuk menentukan action dari tombol delete pada action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                deleteDataFromDatabase()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }


    // fungsi untuk menghapus data dari database
    private fun deleteDataFromDatabase() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val database = InvoiceActivityRoomDatabase.getDatabase(applicationContext)
                val dao = database.InvoiceActivityDao()

                // Misalnya, jika Anda ingin menghapus semua data dari tabel invoiceActivity
                dao.deleteAll()
            }
            showToast("Aktivitas berhasil dihapus")
            navigateToMainActivity()
        }
    }
    // fungsi untuk menampilkan toast
    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    // fungsi untuk berpindah ke MainActivity
    private fun navigateToMainActivity() {
        val intent = Intent(this@MainActivity, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
    // fungsi untuk mengambil data dari intent
    companion object {
        const val EXTRA_ID = "extra_id"}
//        const val EXTRA_NAMAP = "extra_nama"
//        const val EXTRA_HARGA = "extra_harga"
//        const val EXTRA_TANGGAL = "extra_tanggal"
//        const val EXTRA_STATUS = "extra_status"
//        const val EXTRA_ALAMAT = "extra_alamat"
//        const val EXTRA_ALAMATP = "extra_alamatp"
//        const val EXTRA_KOTA = "extra_kota"
//        const val EXTRA_KODEPOS = "extra_kodepos"
//        const val EXTRA_NAMAITEM = "extra_namaitem"
//        const val EXTRA_JUMLAH = "extra_jumlah"}
    
}