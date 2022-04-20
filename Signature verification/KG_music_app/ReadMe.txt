目的：实现签名对抗校验。
工具：雷电模拟器、MT管理器，ida pro、jadx-gui、ArmToAsm（因为ida没有安装keypatch）
过程：（一）：首先用MT管理器去除签名校验，发现没用（校验失败后会打开酷狗官方界面下载正版）。
            猜测签名校验可能存在于so文件中，以防万一用jadx进入java层进行关键字的搜索，发现jadx包
            名等做了混淆，还是得用MT查看smali，结果没有线索，基本确定目标在so中。

          （二）：但是lib目录下so文件实在太多，没办法一个一个关键字（signature、getsignature等）
            查询，还好在这个版本下，最后找到这些函数全部都在可导出函数列表下。

          （三）：根据关键字发现有两个可疑函数，right_signature_or_exit_v2 和 right_signature_or_exit
           进入right_signature_or_exit_v2这个函数里面，发现pthread_create((&thread_return + 3), &attr, sub_3A2F4, 0);
           进入线程sub_3A2F4下，发现代码混淆的很严重，经过仔细分析后，结果发现盗版提示的跳转在这里被实现，
           于是目标很明确nop掉BLX create_thread。

          （四）：然后再看第二个函数，分析后看到这个函数也是对比签名MD5，不对就进行盗版提示。本来是想直接将上面线程的MD5签名
            改到这个函数里面，但是后面重打包发现没有成功。后来经过指点，发现可以先将寄存器R4，R7压到栈顶，然后再返回1，最后出栈。
            整个函数就被改为了直接返回1值。

结果：签名校验已破解（成品在百度网盘上， 链接：https://pan.baidu.com/s/16nnoM-msJ4J6qjcq3c4aGQ   提取码：agbx ）
（破解文件在该目录下）
       