# frida

##### 查看 CPU 架构

~~~shell
adb shell getprop ro.product.cpu.abi
~~~

##### frida-server

~~~shell
adb push frida-server /data/local/tmp/

adb shell "chmod 755 /data/local/tmp/frida-server"

adb shell "ls -l /data/local/tmp/frida-server"

adb shell "su -c '/data/local/tmp/frida-server'"
~~~

##### frida

~~~shell
pip install frida==16.0.0 frida-tools==12.1.0

frida-ps -U
~~~
