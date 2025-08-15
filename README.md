# android_project

##### plugins

~~~
IdeaVim

adb_idea

Key Promoter X
~~~

##### ADB 命令查看 app 当前显示的 activity

~~~shell
# adb shell dumpsys activity top | grep -i "com.example.studyapplication"

# FirstActivity

$ adb shell dumpsys activity top | grep -i "com.example.studyapplication"
        Package index, name, class, description, bitmap flag: 6/com.example.studyapplication:com.example.studyapplication.FirstActivity, StudyApplication, 0+0
8/13/25 12:59 PM LauncherAppsCallbackImpl onPackageAdded triggered for packageName=com.example.studyapplication, user=UserHandle{0}
8/13/25 12:59 PM SessionCommitReceiver Removing unneeded PromiseIcon for package: com.example.studyapplication, install reason: 0, alreadyAddedPromiseIcon: false
8/13/25 1:01 PM ModelWriter removing items from db . Reason: [removed because the corresponding package or component is removed. mOp=2 removedPackages=[] removedComponents=[{com.example.studyapplication/com.example.studyapplication.MainActivity}]]
} targetComponent=ComponentInfo{com.example.studyapplication/com.example.studyapplication.FirstActivity} screen=3 cell(3,0) span(1,1) minSpan(1,1) rank=3 user=UserHandle{0} title=StudyApplication supportsMultiInstance=false nonResizeable=false) item #1: ItemInfo(id=-1 type=APP container=# com.android.launcher3.logger.LauncherAtom$ContainerInfo@19f79dd
} targetComponent=ComponentInfo{com.example.studyapplication/com.example.studyapplication.FirstActivity} screen=3 cell(3,0) span(1,1) minSpan(1,1) rank=3 user=UserHandle{0} title=StudyApplication supportsMultiInstance=false nonResizeable=false)
TASK 10218:com.example.studyapplication id=33 userId=0 displayId=0(type=INTERNAL)
  ACTIVITY com.example.studyapplication/.FirstActivity 13dc74b pid=4084 userId=0 uid=10218 displayId=0(type=INTERNAL)
       mInputChannel: 71f8d27 com.example.studyapplication/com.example.studyapplication.FirstActivity
          mChannel = 71f8d27 com.example.studyapplication/com.example.studyapplication.FirstActivity
          context: com.example.studyapplication.FirstActivity@97adc23


# DialogActivity

$ adb shell dumpsys activity top | grep -i "com.example.studyapplication"
        Package index, name, class, description, bitmap flag: 6/com.example.studyapplication:com.example.studyapplication.FirstActivity, StudyApplication, 0+0
8/13/25 12:59 PM LauncherAppsCallbackImpl onPackageAdded triggered for packageName=com.example.studyapplication, user=UserHandle{0}
8/13/25 12:59 PM SessionCommitReceiver Removing unneeded PromiseIcon for package: com.example.studyapplication, install reason: 0, alreadyAddedPromiseIcon: false
8/13/25 1:01 PM ModelWriter removing items from db . Reason: [removed because the corresponding package or component is removed. mOp=2 removedPackages=[] removedComponents=[{com.example.studyapplication/com.example.studyapplication.MainActivity}]]
} targetComponent=ComponentInfo{com.example.studyapplication/com.example.studyapplication.FirstActivity} screen=3 cell(3,0) span(1,1) minSpan(1,1) rank=3 user=UserHandle{0} title=StudyApplication supportsMultiInstance=false nonResizeable=false) item #1: ItemInfo(id=-1 type=APP container=# com.android.launcher3.logger.LauncherAtom$ContainerInfo@19f79dd
} targetComponent=ComponentInfo{com.example.studyapplication/com.example.studyapplication.FirstActivity} screen=3 cell(3,0) span(1,1) minSpan(1,1) rank=3 user=UserHandle{0} title=StudyApplication supportsMultiInstance=false nonResizeable=false)
TASK 10218:com.example.studyapplication id=33 userId=0 displayId=0(type=INTERNAL)
  ACTIVITY com.example.studyapplication/.DialogActivity 7291e62 pid=4084 userId=0 uid=10218 displayId=0(type=INTERNAL)
       mInputChannel: 87ebeae com.example.studyapplication/com.example.studyapplication.DialogActivity
          mChannel = 87ebeae com.example.studyapplication/com.example.studyapplication.DialogActivity
          context: com.example.studyapplication.DialogActivity@18ffae6

~~~

##### ADB 拉起一个 Activity

~~~shell
# adb shell am start -n com.example.studyapplication/.MainActivity

# FirstActivity
$ adb shell am start -n com.example.studyapplication/.FirstActivity
Starting: Intent { cmp=com.example.studyapplication/.FirstActivity }


# SecondActivity

$ adb shell am start -n com.example.studyapplication/.SecondActivity
Starting: Intent { cmp=com.example.studyapplication/.SecondActivity }

Exception occurred while executing 'start':
java.lang.SecurityException: Permission Denial: starting Intent { flg=0x10000000 xflg=0x4 cmp=com.example.studyapplication/.SecondActivity } from null (pid=4461, uid=2000) not exported from uid 10218
        at com.android.server.wm.ActivityTaskSupervisor.checkStartAnyActivityPermission(ActivityTaskSupervisor.java:1211)
        at com.android.server.wm.ActivityStarter.executeRequest(ActivityStarter.java:1231)
        at com.android.server.wm.ActivityStarter.execute(ActivityStarter.java:870)
        at com.android.server.wm.ActivityTaskManagerService.startActivityAsUser(ActivityTaskManagerService.java:1315)
        at com.android.server.wm.ActivityTaskManagerService.startActivityAsUser(ActivityTaskManagerService.java:1259)
        at com.android.server.am.ActivityManagerService.startActivityAsUserWithFeature(ActivityManagerService.java:3259)
        at com.android.server.am.ActivityManagerShellCommand.runStartActivity(ActivityManagerShellCommand.java:869)
        at com.android.server.am.ActivityManagerShellCommand.onCommand(ActivityManagerShellCommand.java:250)
        at com.android.modules.utils.BasicShellCommandHandler.exec(BasicShellCommandHandler.java:97)
        at android.os.ShellCommand.exec(ShellCommand.java:38)
        at com.android.server.am.ActivityManagerService.onShellCommand(ActivityManagerService.java:10418)
        at android.os.Binder.shellCommand(Binder.java:1151)
        at android.os.Binder.onTransact(Binder.java:953)
        at android.app.IActivityManager$Stub.onTransact(IActivityManager.java:5739)
        at com.android.server.am.ActivityManagerService.onTransact(ActivityManagerService.java:2735)
        at android.os.Binder.execTransactInternal(Binder.java:1426)
        at android.os.Binder.execTransact(Binder.java:1365)

~~~

##### MuMu模拟器

~~~shell
# https://mumu.163.com/download/

# 找到 MuMu 模拟器的 adb 端口
$ netstat -ano | findstr "LISTENING" | findstr "7555"
  TCP    0.0.0.0:7555           0.0.0.0:0              LISTENING       8100

# 用 Android Studio 自带的 adb 连接 MuMu
$ adb connect 127.0.0.1:7555
connected to 127.0.0.1:7555

# 在 Android Studio 里选择 MuMu
~~~