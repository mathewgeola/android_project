function callThreadDetect () {
  Java.perform(function () {

    const obj = Java.use("com.example.studyapplication.MainActivity");

    const result = obj.threadDetect();
    console.log("result: ", result);

    Java.scheduleOnMainThread(function () {
      const result = obj.threadDetect();
      console.log("result: ", result);
    });

  });
}

setImmediate(function () {});