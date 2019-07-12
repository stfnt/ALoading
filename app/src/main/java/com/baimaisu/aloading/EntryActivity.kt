package com.baimaisu.aloading

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.baimaisu.aloading.customize.MyLoadingView
import com.baimaisu.aloading.demo.R
import com.baimaisu.aloading.view.Aloading
import com.baimaisu.aloading.view.LoadingView
import kotlinx.android.synthetic.main.activity_entry.*

class EntryActivity : Activity() {

    var useCoustomize = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        main_activity.setOnClickListener {
            this.startActivity(Intent(this@EntryActivity,MainActivity::class.java))
        }

        recycleView_activity.setOnClickListener {
            this.startActivity(Intent(this@EntryActivity,RecyclerActivity::class.java))
        }

        switch_loading.setOnClickListener {
            useCoustomize = !useCoustomize
            if(useCoustomize){
                Aloading.Registry.setDefaultLoadingView(MyLoadingView::class.java)
            }else{
                Aloading.Registry.setDefaultLoadingView(LoadingView::class.java)
            }

        }
    }
}

