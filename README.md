[README.md](https://github.com/user-attachments/files/27353776/README.md)
# ToDo

## 简介

##### 功能与实现：

登陆注册：通过Room数据库可以本地储存用户（包括用户名与密码），注册完储存用户然后用于登录，SharedPreference实现记住密码方便每次登录。包括主页面可以退出登录与注销账户。

*下面关于任务的操作都是建立在Room数据库上*

添加任务：跳转编辑页使用add方法添加一项任务对象然后rv自动更新列表

左滑删除任务：自定义一个dialog配合delete方法和itemTouchHelper

右滑置顶任务：通过切换置顶状态与优先置顶排序实现，再次右滑可取消置顶状态

查看和更改任务：item点击事件跳转编辑页时直接传送原本数据再修改保存自动更新列表

改变完成状态：通过复选框点击判断已完成与代办修改文字样式

##### 功能展示
<img width="216" height="480" alt="029c6d42b6e78ef4d08d18b18e9f3c30" src="https://github.com/user-attachments/assets/f9df6154-c8c2-4989-bd7f-660676a69552" />
<img width="216" height="480" alt="e2bb9c44ae5c802c8e9dfa89fd1846e8" src="https://github.com/user-attachments/assets/d881e0d5-484b-4f69-8a62-37493df166e3" />
<img width="216" height="480" alt="abff17bbeb3cfa0bd52ef5e8b255dca7" src="https://github.com/user-attachments/assets/b77a10f6-14f2-42f7-8c85-85c2379b05a7" />
<img width="216" height="479" alt="23f47bffd6f4381af58cbc420afd1bdc" src="https://github.com/user-attachments/assets/d349bc27-0cea-43ad-b07c-e16c828d3091" />
<img width="216" height="480" alt="84f7033acb82ade5037beb8a04e991d5" src="https://github.com/user-attachments/assets/c406264c-0bd1-42ca-b523-25231b67e0a7" />
<img width="216" height="480" alt="23446e5572b33f706ba491b87ad3b002" src="https://github.com/user-attachments/assets/fe399b60-b910-458f-a4ae-9547891108a9" />

##### 技术亮点

使用了MVVM架构与自定义删除对话框，用Flow实现数据自动刷新，基础的功能都差不多了

##### 心得体会

一开始感觉MVVM架构好像很难理解，跟着课件一点点实现Room数据库，实现repository，再到viewModel，最后到activity。

好像有点理解了Room数据库，ViewModel和LiveData进行数据与UI的分离，联系了RecyclerView的Adapter和ItemTouchHelper侧滑交互，也看了一点协程（lifecycleScope、viewModelScope）的基本用法。第一次使用了约束布局里面的分割线哈哈

过程中也遇到了很多问题，Gradle版本不兼容、编辑任务后列表不刷新、suspend函数不能在主线程调用、侧滑取消后还消失等，但最终都一一解决了。因为不熟练写了四天感觉还是差点意思。

##### 待提升

控件布局在不同手机上的样子还不一样，显得很奇怪

重复性的代码比较多基本上要用到Dao的地方都会随时创建一个 也没有实现网络请求 刷新都是全局刷新
