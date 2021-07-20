
# SwingUI
A slight Java Swing library support form with material design, construct same with Android UI Framework writen in `Kotlin`

## Supported:

### 1. Screen:
Like `Activity` in Android, enable display a frame with default close action, support movable in status bar.
Using by implement Screen class:
```kotlin
class MainScreen : Screen(900, 600) {
	override fun onCreateView() {
		container.layout = BorderLayout()
		container.add(JPanel().apply {
			background = Color.black
		})
	}
}
```

### 2. Widget:
Like `View` in Android, support some common widget with re-defined UI.
Currently, support 2 widgets:

 - `org.cuongnv.swingui.widget.TextView`
 - `org.cuongnv.swingui.widget.EditText`

### 3. Material UI:
Support flat button style and flat scrollbar UI with 3 build-in UIs: `FlatButtonUI`, `FlatCircleButtonUI` and `FlatScrollBarUI`.


### 4. Animation:
Like with `ValueAnimator` in Android, `SwingUI` support play animation on property of Swing `Component`.
```kotlin
val animator = ValueAnimator.ofFloat(0f, 1f)
	.setDuration(250L)
	.setInterpolator(SinInterpolator())
	.setUpdateListener { value: Float ->
		// Redraw with new animated value [value]
	}
	 
animator.start()
```
Currently, supported 2 `Interpolator`: `LinearInterpolator` and `SinInterpolator`.

## Future Update:
`SwingUI` in development phase, too much thing must update, if you have any ideas, please contribute with me.

## License
```
Copyright 2021 Cuong V. Nguyen (github.com/cuongnv126).

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
