# AndroidIPC


## 一、AIDL

### 1. 简介
`AIDL` 是 Android Interface Definition Language 的缩写，是 Android 定义的一种专门用于进程间通信的接口语言，它的语法也和 `java` 比较类似。

### 2. 特点
我们知道 Android 中还有一种轻量级的进程通信方式——`Messenger`。

`Messenger` 类似于一个进程间使用的 `Handler`，它适用于消息的传递，服务器只能以串行的方式一个一个地处理客户端的消息。无法处理大量的并发请求，也不能直接地调用服务器的方法，若想解决这种问题，则需要使用 AIDL。

关于 Messenger 的介绍可以戳： [https://blog.csdn.net/afei__/article/details/83386759](https://blog.csdn.net/afei__/article/details/83386759)

`AIDL` 可以让我们定义一系列的接口，供客户端和服务端共同调用，区别于 Java 的接口，它只支持定义方法，不支持静态常量。对象本质还是不能跨进程传输的，`Binder` 会把客户端和服务端传递的对象序列化重新转化生成一个新的对象进行传递。


### 3. 支持的数据类型
- 基本数据类型
- `String` 和 `CharSequence`
- `List` 和 `Map`
- `Parcelable`
- `AIDL` 接口本身 

### 4. 定向 Tag
定向 Tag 有三种，即：

- `in` : 表示输入参数，数据只能从客户端流入服务端
- `out` : 表示输出参数，数据只能从服务端流入客户端
- `inout` : 表示输入输出参数，即数据可以在客户端和服务端双向流通

`AIDL` 中除基本数据类型和 `String` 外，其余类型 **必须** 标注定向 Tag。

建议不要随意使用 `inout`，因为会加大系统的消耗和降低效率。
我们应该保证，对于输入参数就使用 `in`，输出参数就使用 `out`。

### 5. 实战
[代码示例](https://github.com/afei-cn/AndroidIPC/blob/master/app/src/main/java/com/afei/androidipc/aidl/AIDLActivity.java)

[博客讲解](https://blog.csdn.net/afei__/article/details/84594748)
