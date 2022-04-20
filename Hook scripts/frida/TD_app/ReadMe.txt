本来最开始我是用xposed去进行实现的，但是对于这个app来说frida写脚本要相比简单一点，所以又写了一份frida脚本。
目的：实现免登录和vip的破解
工具：MT管理器、jadx-gui、开发者工具（用于控件引用分析）
过程：
        （一）：我先用MT查看壳加固并去除签名校验，老样子签名去除没有成功，所以为了省区时间使用hook注入破解。
        （二）：然后我用开发者工具找到了登录点击事件的id的name，通过在jadx搜索name，找到了引用位置com.tadu.android.ui.view.a0.e
        （三）：然后发现isLogin方法，点进去逻辑很清楚就是判断userStatus是否为1，然后返回布尔值，所以为了确定登录是否在这里检验，hook了一下，
          结果真是这里。
        （四）：免登录实现后，根据这个app的特点，我直接在jadx上搜索了vip关键词，发现有很多地方都有这个关键词，思路不是很明确。然后我突然发现
          isLogin这个方法的类名就叫UserInfoModel，包着疑惑的态度，搜索了一下vip，发现没有这个关键词。
        （五）：然后我又用开发者工具对“开通Vip”这个按钮进行了分析，发现这个vip这个按钮的name竟然叫getMember，而这个getMember关键词在
          UserInfoModel里面有出现。所以又回去搜索getMember，找到了getisMember这个函数，然后试着hook了一下，结果成功破解。

结果：实现了免登录和vip破解。（apk在百度网盘上：链接：https://pan.baidu.com/s/1lc2pTguyU19_U-2Ln0owHg     提取码：f6l6  ）
（脚本在该目录下）