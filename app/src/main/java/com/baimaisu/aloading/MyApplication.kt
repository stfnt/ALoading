package com.baimaisu.aloading

import android.app.Application
import android.widget.TextView
import com.baimaisu.aloading.customize.MyLoadingView
import com.baimaisu.aloading.customize.MyLoadingViewFactory
import com.baimaisu.aloading.view.*
import com.baimaisu.aloading.view.Aloading.Registry.Companion.register

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Aloading.Registry.apply {
            register(MyLoadingView::class.java,MyLoadingViewFactory)
        }



    }


}