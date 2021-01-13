# haptic-aid-for-visually-impaired
Mobile phone vibration feedback guide interaction for the visually impaired
One. Demo interface analysis
From top to bottom are the elements in the Demo
1. Demo name
2. Display the direction displayed in the last operation and the input direction
3. Display the direction expected from the user, that is, the path
4. Four buttons for inputting direction
two. Demo usage process
1. Enter Demo
2. Enter the direction with the enter button
3. The device prompts whether the correct direction is input through the intensity of the vibration and the length of the vibration time. If the correct direction is input, it will vibrate for 500ms with an intensity of 100 (the range of vibration intensity is 1-255); In the wrong direction, vibration is performed for 50 ms with an intensity of 200. At the same time, a toast pop-up prompt will prompt the user whether the input is correct. If it is correct, the pop-up content is SUCCESS, if it is wrong, the pop-up content is Failed.
4. Repeat 2-3
three. Demo principle
After the user enters the Demo, the default path is displayed as downward. When the user enters the direction through the keys, a random number from 1-4 will be generated, and the next path will be displayed according to the random number.

four. Current deficiencies
1. The interaction method is to click the button, and the ideal state should be the user's swipe on the screen.
2. Since the equipment used for the test is not equipped with a linear motor, the actual effect of the vibration frequency change has not been tested, so it is not known whether there will be any problems in this area.
3. The Toast pop-up window stays in the user interface for a fixed time and is presented in the form of a queue. Therefore, when the user clicks too quickly, the content of the pop-up window will not be synchronized with the user input.


一．	Demo界面解析
自上至下为Demo中的元素
1.	Demo名称
2.	显示上一次操作所显示的方向与输入的方向
3.	显示期望用户输入的方向，即路径
4.	用于输入方向的四个按钮
二．	Demo使用流程
1.	进入Demo
2.	通过输入按钮输入方向
3.	设备通过振动的强度与振动时间的长度提示是否输入了正确的方向，目前若输入了正确的方向，则会进行500ms的振动，强度为100（振动强度范围为1-255）；若输入了错误的方向，则进行50ms的振动，强度为200。同时也将会通过toast弹窗提示的方式提示用户是否输入正确，若正确，则弹窗内容为SUCCESS，若错误，则弹窗内容为Failed。
4.	重复2-3
三．	Demo原理
用户进入Demo后默认的路径显示为向下，当用户通过按键输入方向后，则会生成1-4的随机数，并根据该随机数显示下一个路径。

四．	目前存在的不足
1.	交互方式为点击按钮，理想状态应为用户对屏幕的划动。
2.	由于用于测试的设备未配备线性马达，因此并未对振动频率改变的实际效果进行测试因此尚不了解是否会有该方面的问题。
3.	Toast弹窗在用户界面驻留时间固定且以队列的形式呈现，因此当用户点击过快时弹窗内容将与用户输入不同步。
