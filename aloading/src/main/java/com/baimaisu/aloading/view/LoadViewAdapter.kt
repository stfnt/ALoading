package com.baimaisu.aloading.view
import android.view.View
import java.lang.RuntimeException

class LoadViewAdapter(private var loadingView: ILoadingView,private var root: View) : ILoadingView,IRootView,IReloadView {

    enum class LoadingState{
        UNSTART,LOADING,LOADFAILED,LOADSUCCESS,LOADEMPTYDATA
    }

    private var state:LoadingState = LoadingState.UNSTART

    /**
     *  always showLoading as if the obj may be reused when the state is LOADING,but the loadingView has detach from window
     */
    override fun showLoading() {
        state = LoadingState.LOADING
        loadingView.showLoading()
    }

    override fun showLoadFailed() {
        if(state == LoadingState.LOADFAILED){
            return
        }
        state = LoadingState.LOADFAILED
        loadingView.showLoadFailed()
    }

    override fun showLoadSuccess() {
        if(state == LoadingState.LOADSUCCESS){
            return
        }
        state = LoadingState.LOADSUCCESS
        loadingView.showLoadSuccess()
    }

    override fun showLoadEmptyData() {
        if(state == LoadingState.LOADEMPTYDATA){
            return
        }
        state = LoadingState.LOADEMPTYDATA
        loadingView.showLoadEmptyData()
    }

    override fun getView(): View {
        return loadingView.getView()
    }

    override fun rootView(): View {
        return root
    }

    override fun setReloadTask(reload: Runnable) {
        if(IReloadView::class.java.isAssignableFrom(loadingView.javaClass)){
            (loadingView  as IReloadView).setReloadTask(reload)
        }
    }


}