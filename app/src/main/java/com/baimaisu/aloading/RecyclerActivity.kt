package com.baimaisu.aloading

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.baimaisu.aloading.demo.R
import com.baimaisu.aloading.view.Aloading
import com.baimaisu.aloading.view.LoadViewAdapter
import com.baimaisu.aloading.view.LoadingView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_recycler.*

class RecyclerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        recycleView.apply {
            val datas:MutableList<String > = mutableListOf()
            (0 until 100).forEach {
                datas.add(Util.randomImage)
            }
            adapter = MyAdapter(datas)
            layoutManager = LinearLayoutManager(this.context)

        }
    }


    class MyAdapter(var datas:MutableList<String>) : RecyclerView.Adapter<MyViewHolder>() {
        override fun getItemCount(): Int {
            return datas.size
        }

        override fun onBindViewHolder(myViewHolder: MyViewHolder, posistion: Int) {
            myViewHolder.loadUrl(datas[posistion])
        }

        override fun onCreateViewHolder(viewgroup: ViewGroup, position: Int): MyViewHolder {
            Log.d("axx","onCreateViewHolder: $position")
            val view = LayoutInflater.from(viewgroup.context).inflate(R.layout.item_recycleview,viewgroup,false)
            return MyViewHolder(Aloading.default().wrap(view))
        }

    }
    class MyViewHolder(var viewWrapper: LoadViewAdapter) : RecyclerView.ViewHolder(viewWrapper.rootView()){
        private  var imageView =viewWrapper.rootView().findViewById<ImageView>(R.id.item_iv)


        fun loadUrl(url:String){
            viewWrapper.showLoading()
            Glide.with(imageView).load(url).listener(object :RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewWrapper.showLoadFailed()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewWrapper.showLoadSuccess()
                    return false
                }

            }).into(imageView)
        }
    }
}