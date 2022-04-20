这是liangwu公司的某一款大众级游戏的内购hook脚本，主要用Xposed实现Java层的注入（frida写着太麻烦了）。
工具：MT管理器、jadx-gui、fiddler
           实现过程：
	（一）：用MT去查看壳和去签名，发现没有壳加固，但是去签名没有成功（进去后会闪退），所以为了省区去签名校验的过程就用到了hook。
	（二）：先去注册一个游戏账号，然后故意支付失败，发现有“支付失败:user_cancel”“校验失败”之类的弹窗出现，所以就用jadx-gui去进行搜索，
	             发现了onActivityResult 和 onPostExecute这两个关键方法，然后再细看方法内部，一个是订单支付后的检验（判断成功与否），一个是订单的校验。
	（三）：用fiddler对支付订单进行抓包分析，找到了onPostExecute的参数JSON格式
	（四）：通过hook requestOrder，找到了订单相应的信息，然后把它们放进onPostExecute进行参数构成，内购便破解成功。
结果：内购破解成功。（apk在百度网盘上，链接：https://pan.baidu.com/s/17gkhoUsYw4KDCP6HBg9DJA    提取码：f5gx ）
（脚本在该目录下）
	