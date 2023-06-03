package com.example.projectakhir.data

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class InvoiceActivityRepository(application: Application) {
    private val mInvoiceActivtyDao: InvoiceActivityDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = InvoiceActivityRoomDatabase.getDatabase(application)
        mInvoiceActivtyDao = db.InvoiceActivityDao()

    }

    fun insert(InvoiceActivity: InvoiceActivityEntity) {
        executorService.execute { mInvoiceActivtyDao.insert(InvoiceActivity) }
    }

    fun update(InvoiceActivity: InvoiceActivityEntity) {
        executorService.execute { mInvoiceActivtyDao.update(InvoiceActivity) }
    }

    fun deleteAll(invoiceActivity: InvoiceActivityEntity) {
        executorService.execute { mInvoiceActivtyDao.deleteAll() }
    }

    fun deleteInvoiceActivityById(Id: Int) {
        mInvoiceActivtyDao.deleteById(Id)
    }
    fun getInvoiceActivityById(id: Int): LiveData<InvoiceActivityEntity> {
        return mInvoiceActivtyDao.getInvoiceActivityById(id)
    }

    fun getAllInvoiceActivity(): LiveData<List<InvoiceActivityEntity>> =
        mInvoiceActivtyDao.getAllInvoiceActivity()

    fun getAllValidInvoiceActivity(): LiveData<List<InvoiceActivityEntity>> =
        mInvoiceActivtyDao.getAllValidInvoiceActivity()
}
