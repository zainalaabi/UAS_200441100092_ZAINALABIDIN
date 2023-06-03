package com.example.projectakhir.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class InvoiceActivityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "nama")
    var nama: String? = null,

    @ColumnInfo(name = "alamat")
    var alamat: String? = null,

    @ColumnInfo(name = "email")
    var email: String? = null,

    @ColumnInfo(name = "tanggal")
    var tanggal: String? = null,

    @ColumnInfo(name = "kota")
    var kota: String? = null,

    @ColumnInfo(name = "kodePos")
    var kodePos: String? = null,

    @ColumnInfo(name = "namaItem")
    var namaItem: String? = null,

    @ColumnInfo(name = "status")
    var status : Boolean = false
)
