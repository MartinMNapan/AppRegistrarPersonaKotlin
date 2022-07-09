package pe.idat.edu.appclasssemana4_v1.common

import android.app.Application

class MIApp : Application(){

    companion object{
        lateinit var instance: MIApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}