> 关注公众号学习更多知识
>
>![](https://img-blog.csdnimg.cn/img_convert/6dd2df09156ca4cbfc44ad68c9baa2e4.png)



## 页面导航动画是啥
`Compose`的页面导航动画就相当于`Activity`中的页面切换动画，例如打开`Activity`时候进入的动画，关闭`Activity`时候的退出动画。

## 页面导航实现的现状
官方正式版的导航中并没有提供导航的动画，但是可能官方也发现了这个问题，因此官方目前正在开发独立于主框架的依赖项目（com.google.accompanist:accompanist-navigation-animation）方便开发者使用导航。

依赖目前最新版本是：`com.google.accompanist:accompanist-navigation-animation:0.21.1-beta`，本文使用`0.21.1-beta`版本进行演示，0.16.0的版本和当前版本差异较大，所以不再演示。

> 需要重点说明的是，导航动画的api目前都是实验性质的api，不过我认为不久的将来这些都会转正，只不过是个别的api可能会有大调整，这不可避免。


## Compose中页面导航动画种类
Compose的导航动画提供了两个基础接口`EnterTransition`和`ExitTransition`用于提供进入页面导航动画和退出页面导航动画。并且提供了多个现成的实现效果供开发者方便使用，平时开发使用现成的实现基本就可以满足大部分需求。
### 动画基础类
1. `进入动画：` `EnterTransition`

2. `退出动画：` `ExitTransition`

### 滑动进入退出类型
1. `滑动进入动画：`基础的进入动画是`slideIn`，并且派生出`slideInHorizontally`和`slideInVertically`

2. `滑动退出动画：`基础的退出动画是`slideOut`，并且派生出`slideOutHorizontally`和`slideOutVertically`

### 淡入淡出类型
1. `淡入动画：` `fadeIn`，无派生

2. `淡出动画：` `fadeOut`，无派生
### 膨胀收缩类型
1. `膨胀进入动画：` `expandHorizontally`和`expandVertically`

2. `收缩退出动画：` `shrinkHorizontally`和`shrinkVertically`

### 放大和缩放类型
1. `缩放进入动画：` `scaleIn`

2. `缩放推出的桑：` `scaleOut`

## 使用导航动画发方式
1. 添加依赖


```groovy
implementation "com.google.accompanist:accompanist-navigation-animation:0.21.1-beta"
```


2. 导航类代码
   `enterTransition`和`exitTransition`分别可以设置进入动画和退出动画。

> composable中也是可以为单独的页面设置导航动画的

```kt
AnimatedNavHost(navController = controller,
        startDestination = home,
        enterTransition = {
            enterAnim(flag.value)
        },
        exitTransition = {
            exitAnim(flag.value)
        }) {

        composable(home) {
            Home(controller, flag)
        }
        composable(main) {
            Main(controller, flag)
        }
    }
```
3. NavHostController选择

```kt
val controller = rememberAnimatedNavController()
```
4. 编写具体进入退出的动画

后续章节放代码



## 几中动画的实现和效果
后续几个效果只选用我代码实现中的几种实现举例，具体使用大同小异。
### 滑动进入和滑动退出
#### 代码
`进入动画`
```kt
slideInHorizontally(animationSpec = tween(1000),//动画时长1s initialOffsetX = {
                -it//初始位置在负一屏的位置，也就是说初始位置我们看不到，动画动起来的时候会从负一屏滑动到屏幕位置
            })
```

`退出动画`
```kt
 slideOutHorizontally(animationSpec = tween(1000), targetOffsetX = {
                it
            })
```
#### 效果
![滑动进入和退出动画](https://files.mdnice.com/user/15648/99a0c1fb-1587-4ed4-b993-b9c242deeaea.gif)

### 淡入和退出

#### 代码

`进入动画`
```
fadeIn(animationSpec = tween(1000), initialAlpha = 0f)
```

`退出动画`
```kt
fadeOut(animationSpec = tween(1000), targetAlpha = 0f)
```


#### 效果
![淡入进入和淡出动画效果](https://files.mdnice.com/user/15648/cc17a49f-1e0c-47cd-8d82-2ed88aea5805.gif)



### 膨胀进入和收缩退出
#### 代码
`膨胀动画`

```kt
expandIn(animationSpec = tween(1000), expandFrom = Alignment.TopStart){
                IntSize(0,0)
            }
```
`收缩动画`
```kt
shrinkOut(animationSpec = tween(1000), shrinkTowards = Alignment.BottomEnd) {//缩小80%
                it*4/5
            }
```


#### 效果
![膨胀进入和收缩退出动画](https://files.mdnice.com/user/15648/889a3d75-0629-4727-8a8b-265c5a7ae2b7.gif)


### 放大进入和缩小退出


#### 代码
`进入动画`
```kt
scaleIn(animationSpec = tween(1000), initialScale = 0f//初始缩放大小,
transformOrigin = TransformOrigin(0f,0f)//设置动画缩放的基准点)
```

`退出动画`

```kt
scaleOut(animationSpec = tween(1000), targetScale = 0f, transformOrigin = TransformOrigin(1f,1f))
```

#### 效果

![放大进入和缩放退出动画](https://files.mdnice.com/user/15648/9810e255-b70f-4b4e-8557-332afc16dbb6.gif)
