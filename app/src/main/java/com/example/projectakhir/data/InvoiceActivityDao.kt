package com.example.projectakhir.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface InvoiceActivityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(InvoiceActivity: InvoiceActivityEntity)

    @Update
    fun update(InvoiceActivity: InvoiceActivityEntity)

    @Query("DELETE FROM InvoiceActivityEntity")
    fun deleteAll()

    @Query("DELETE FROM InvoiceActivityEntity WHERE id = :InvoiceActivityId")
    fun deleteById(InvoiceActivityId: Int)

    @Query("SELECT * FROM InvoiceActivityEntity WHERE id = :InvoiceActivityId")
    fun getInvoiceActivityById(InvoiceActivityId: Int): LiveData<InvoiceActivityEntity>

    @Query("SELECT * from InvoiceActivityEntity WHERE status = 0 ORDER BY nama ASC")
    fun getAllInvoiceActivity(): LiveData<List<InvoiceActivityEntity>>

    @Query("SELECT * from InvoiceActivityEntity WHERE status = 1 ORDER BY nama ASC")
    fun getAllValidInvoiceActivity(): LiveData<List<InvoiceActivityEntity>>
}