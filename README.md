# ALoading

### 参考：https://github.com/luckybilly/Gloading


#### 1.一行代码为你的view增加可以显示加载中，加载完成，加载失败等视图
#### 2.支持自定义加载视图，多个加载视图风格可以同时运行

### 使用：


``` kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Aloading.Registry.apply {
            /**
             *  注册自定义加载视图
             */
            register(MyLoadingView::class.java,MyLoadingViewFactory)
        }
    }
}
```


``` kotlin
/**
    *  装饰view
    */
    loadView = Aloading.default().wrap(this)
    
    loadView.showLoading() // 显示加载中
    loadView.showLoadFailed() // 显示加载失败
    loadView.showLoadSuccess() // 显示加载成功

```


### 演示：
![Aaron Swartz](https://s2.ax1x.com/2019/07/14/ZIe14H.png)

![Aaron Swartz](https://s2.ax1x.com/2019/07/14/ZIefVU.png)

![Aaron Swartz](https://i.loli.net/2019/07/14/5d2b2cfe44a4d52007.gif)







