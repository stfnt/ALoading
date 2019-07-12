package com.baimaisu.aloading.customize

import android.content.Context
import android.media.Image
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.baimaisu.aloading.R
import com.baimaisu.aloading.view.Factory
import com.baimaisu.aloading.view.ILoadingView
import com.baimaisu.aloading.view.IReloadView
import com.bumptech.glide.Glide.init

class MyLoadingView(context: Context) : LinearLayout(context), ILoadingView, IReloadView {

    var im :ImageView =ImageView(context)
    var tv : TextView = TextView(context)
    init {
        this.addOnAttachStateChangeListener(object : OnAttachStateChangeListener{
            override fun onViewDetachedFromWindow(v: View?) {
                if(v == this@MyLoadingView){
                    v.clearAnimation()
                }
            }

            override fun onViewAttachedToWindow(v: View?) {

            }

        })
    }

    fun switchToIm(){
        val size = 20.0f
        val dpSize = TypedValue.applyDimension(1,size,context.resources.displayMetrics).toInt()
        gravity = Gravity.CENTER_VERTICAL
        val ivwidth = if(width == 0 || width > dpSize) dpSize else width
        val ivHeight = if(height == 0 || height > dpSize) dpSize else height
        val layoutParams = LayoutParams(ivwidth,ivHeight)

        im.setImageResource(com.baimaisu.aloading.demo.R.drawable.myloading)
        this@MyLoadingView.removeAllViews()
        this@MyLoadingView.addView(im,layoutParams)
    }

    fun switchToTv(){
        gravity = Gravity.CENTER
        tv.apply {
            textSize = 22.0f
        }

        this.apply {
            im.clearAnimation()
            this@MyLoadingView.removeAllViews()
            addView(tv)
        }
    }

    var reload: Runnable? = null

    override fun showLoading() {
        visibility = View.VISIBLE

        switchToIm()
        startTranslateAnimation()

    }

    fun startTranslateAnimation(){

        this.addOnLayoutChangeListener(object : OnLayoutChangeListener{
            override fun onLayoutChange(
                v: View?,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                 val anim = TranslateAnimation(0.0f,(this@MyLoadingView.width / 1).toFloat(),0.0f,0.0f)
                 anim.duration  = 2000
                 anim.interpolator = LinearInterpolator()
                 anim.repeatCount = -1
                 im.startAnimation(anim)

                this@MyLoadingView.removeOnLayoutChangeListener(this)

            }

        })
    }


    override fun showLoadFailed() {

        switchToTv()
        tv.text = "loading fail"
        this.setOnClickListener {
            reload?.run()
        }
    }

    override fun showLoadSuccess() {

        this.clearAnimation()
        visibility = View.GONE
    }

    override fun showLoadEmptyData() {
        switchToTv()
        tv.text = "loading empty"
    }

    override fun getView(): View {
        return this
    }

    override fun setReloadTask(reload: Runnable) {
        this.reload = reload
    }


}

object MyLoadingViewFactory : Factory<MyLoadingView> {
    override fun create(context: Context): MyLoadingView = MyLoadingView(context)
}
