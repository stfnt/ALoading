package com.baimaisu.aloading.view

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import com.baimaisu.aloading.R
import kotlinx.android.synthetic.main.aloading_loading_view.view.*
import java.lang.RuntimeException

class LoadingView : LinearLayout,ILoadingView,IReloadView{


    var reload:Runnable? = null
    constructor(context: Context?) : super(context)

    init {
        gravity = Gravity.CENTER_HORIZONTAL
        orientation = LinearLayout.VERTICAL
        LayoutInflater.from(context).inflate(R.layout.aloading_loading_view,this)

    }



    override fun showLoading() {
        this.visibility = View.VISIBLE
        val loading = context.resources.getString(R.string.loading)
        loadingview_iv.setImageResource(R.drawable.icon_loading)
        loadingview_tv.text = loading
        startAnimation()
        disableReloadClick()
    }

    override fun showLoadFailed() {
        val loadingFail = context.resources.getString(R.string.load_failed)
        loadingview_iv.setImageResource(R.drawable.icon_failed)
        loadingview_tv.text = loadingFail
        enableReloadClick()
        stopAnimation()
    }

    override fun showLoadSuccess() {
        this.visibility = View.GONE
        disableReloadClick()
        stopAnimation()
    }

    override fun showLoadEmptyData() {
        loadingview_tv.visibility = View.GONE
        loadingview_iv.setImageResource(R.drawable.icon_empty)
        disableReloadClick()
        stopAnimation()
    }


    override fun getView(): View {
        return this
    }

    override fun setReloadTask(reload: Runnable) {
        this.reload = reload
    }


    fun enableReloadClick(){
        this.setOnClickListener {
            reload?.run()
        }
    }

    fun disableReloadClick(){
        this.setOnClickListener {

        }
    }



    private fun startAnimation(){
        val anim = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.duration  = 3000
        anim.interpolator = LinearInterpolator()
        anim.repeatCount = -1
        loadingview_iv.startAnimation(anim)

    }

    private fun stopAnimation(){
        loadingview_iv.clearAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimation()
    }



}