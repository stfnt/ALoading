package com.baimaisu.aloading.view

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

class Aloading(private var loadingViewClass: Class<out ILoadingView>) {
    companion object {
        private val loadingFactorys: MutableMap<Class<out ILoadingView>, Factory<out ILoadingView>> = mutableMapOf()

        private val viewGroupHandlers:MutableList<ViewGroupHandler> = mutableListOf()

        init {
            loadingFactorys[LoadingView::class.java] = default_factory
            viewGroupHandlers.add(ConstraintLayoutHandler)
        }

        private var defaultLoadingViewClass: Class<out ILoadingView> = LoadingView::class.java

        fun default(): Aloading {
            return Aloading(defaultLoadingViewClass)
        }

        fun with(loadingViewClass: Class<out ILoadingView>): Aloading {
            return Aloading(loadingViewClass)
        }

    }

    class Registry {
        private constructor()
        companion object {
            fun setDefaultLoadingView(viewClass: Class<out ILoadingView>) {
                defaultLoadingViewClass = viewClass
            }

            fun <T : ILoadingView> register(viewClass: Class<T>, factory: Factory<T>) {
                loadingFactorys[viewClass] = factory
            }


            fun appendViewGroupHandler(handler:ViewGroupHandler){
                viewGroupHandlers.add(handler)
            }
        }
    }


    fun wrap(act: Activity): LoadViewAdapter {
        return ViewWrapper(loadingFactorys[loadingViewClass]!!.create(act)).wrap(act)
    }

    fun wrap(view: View): LoadViewAdapter {
        return ViewWrapper(loadingFactorys[loadingViewClass]!!.create(view.context)).wrap(view)
    }




    private inner class ViewWrapper(private var loadingView: ILoadingView) {



        fun wrap(act: Activity): LoadViewAdapter {
            return wrap(act.findViewById<View>(android.R.id.content))
        }


        private fun wrapDefaultByFrameLayout(view: View): LoadViewAdapter {
            val wrapper = FrameLayout(view.context)
            val lp = view.layoutParams
            if (lp != null) {
                wrapper.layoutParams = lp
            }
            if (view.parent != null) {
                val parent = view.parent as ViewGroup
                val index = parent.indexOfChild(view)
                parent.removeView(view)
                parent.addView(wrapper, index)
            }
            val newLp =
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            wrapper.addView(view, newLp)
            wrapper.addView(
                loadingView.getView(),
                FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            )
            loadingView.getView().bringToFront()
            return LoadViewAdapter(loadingView, wrapper)
        }

        /**
         * if(view not has parent){
         *     return wrapDefaultByFrameLayout();
         * }else if(constraintLayoutClass.isAssignableFrom(view.getParent().getClass()){
         *     return wrapConstraintLayout()
         * }else if(***.class.isAssignableFrom(view.getParent().getClass()){
         *     return wrap***Layout();
         * }else{
         *     return wrapDefaultByFrameLayout()
         * }
         */
        fun wrap(view: View): LoadViewAdapter {
            if (FrameLayout::class.java.isAssignableFrom(view.javaClass)) {
                (view as FrameLayout).addView(
                    loadingView.getView(),
                    FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                )
                loadingView.getView().bringToFront()
                return LoadViewAdapter(loadingView, view)
            }

            if(view.parent != null){
                viewGroupHandlers.forEach {
                    if(it.couldHandle(view)){
                        return LoadViewAdapter(loadingView,it.handle(view,view.parent as ViewGroup))
                    }
                }
            }

            return wrapDefaultByFrameLayout(view)
        }




    }


}






