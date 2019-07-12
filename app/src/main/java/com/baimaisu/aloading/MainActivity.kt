package com.baimaisu.aloading

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.baimaisu.aloading.demo.R
import com.baimaisu.aloading.view.Aloading
import com.baimaisu.aloading.view.LoadViewAdapter
import com.baimaisu.aloading.view.LoadingView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(){

    lateinit var loadView:LoadViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadView = Aloading.default().wrap(this)



        loadView.setReloadTask(Runnable {
            loadData(Util.randomImage)
        })
        loadData(Util.errorImage)

    }


    fun loadData(url:String){
        loadView.showLoading()
        Glide.with(this).load(url).listener(object : RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                loadView.showLoadFailed()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                loadView.showLoadSuccess()
                return false
            }

        }).into(main_iv)
    }


}
