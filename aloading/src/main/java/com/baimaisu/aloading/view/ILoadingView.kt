package com.baimaisu.aloading.view

import android.content.Context
import android.view.View
import java.lang.reflect.ParameterizedType


interface ILoadingView {
    fun showLoading()
    fun showLoadFailed()
    fun showLoadSuccess()
    fun showLoadEmptyData()
    /**
     *  return the loadingView
     */
    fun getView(): View

}

interface Factory<T : ILoadingView>{
    fun  create(context: Context):T
}

val default_factory = object : Factory<LoadingView>{
    override fun create(context: Context): LoadingView {
        return LoadingView(context)
    }


}


abstract class SampleFactory<T : ILoadingView> : Factory<T>{
    override fun create(context: Context): T {
        val tClass:Class<T> = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        return tClass.getConstructor(Context::class.java).newInstance(context)
    }



}


