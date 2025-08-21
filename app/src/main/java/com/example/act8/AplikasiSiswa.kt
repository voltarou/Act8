package com.example.act8

import android.app.Application
import com.example.act8.repositori.ContainerApp
import com.example.act8.repositori.ContainerDataApp

class AplikasiSiswa : Application() {
    lateinit var container: ContainerApp

    override fun onCreate() {
        super.onCreate()
        container = ContainerDataApp(this)
    }
}