package com.baimaisu.aloading.view

import android.content.Context
import android.view.View
import android.view.ViewGroup


interface ViewGroupHandler{
    fun couldHandle(view:View):Boolean
    fun handle(view:View,root:ViewGroup):View
}
abstract class BaseViewGroupHandler : ViewGroupHandler{

    abstract fun getClassName():String

    var viewGroupClass:Class<ViewGroup>? = null
    var LayoutParamsClass: Class<ViewGroup.LayoutParams>? = null

    var hasClass:Boolean = false

    companion object {
        val tag:String = "\$LayoutParams"
    }

    init {
        try{
            viewGroupClass = this.javaClass.classLoader.loadClass(getClassName()) as Class<ViewGroup>
            LayoutParamsClass = this.javaClass.classLoader.loadClass(getClassName() + tag) as Class<ViewGroup.LayoutParams>
            hasClass = true
        }catch (e: java.lang.Exception){

        }
    }

    override fun couldHandle(view: View): Boolean {
        return hasClass && viewGroupClass!!.isAssignableFrom(view.javaClass)
    }

    override fun handle(view: View,root:ViewGroup): View {
        root.removeView(view)
        val wrapper = viewGroupClass!!.getConstructor(Context::class.java).newInstance(view.context)
        root.addView(wrapper, view.layoutParams)
        val lp = LayoutParamsClass!!.getConstructor(Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            .newInstance(view.layoutParams.width, view.layoutParams.height)
        wrapper.addView(view, lp)
        return wrapper
    }

}

object ConstraintLayoutHandler : BaseViewGroupHandler() {
    override fun getClassName(): String = "android.support.constraint.ConstraintLayout"
}