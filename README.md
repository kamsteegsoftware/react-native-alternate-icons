# react-native-alternate-icons

React Native Alternate Icons

![Demo](http://kamsteegsoftware.nl/react-native-alternate-icons/demo.gif)

## Requirements

* React Native 0.44+ (only tested on 0.44 and 0.53.3)

## Installation

```bash
npm install react-native-alternate-icons@latest --save
```

## Link the library to your React Native project

You should be able to automatically link the library using:

```bash
react-native link react-native-alternate-icons
```

If this fails for iOS you can try the manual linking approach in the [React Native documentation.](https://facebook.github.io/react-native/docs/linking-libraries-ios.html)

## iOS Preparation

Add your icons into your Xcode Project

![Icons in your Xcode Project](http://kamsteegsoftware.nl/react-native-alternate-icons/icons-project.png)

Add the following code to your info.plist, filling in primaryIconName and alternateIconName. More alternate icons can be added.

```xml
<key>supportsAlternateIcons</key>
<true/>
<key>CFBundleIcons</key>
<dict>
  <key>CFBundlePrimaryIcon</key>
  <dict>
    <key>CFBundleIconFiles</key>
    <array>
      <string>primaryIconName</string>
    </array>
    <key>UIPrerenderedIcon</key>
    <false/>
  </dict>
  <key>CFBundleAlternateIcons</key>
  <dict>
    <key>alternateIconName</key>
    <dict>
      <key>CFBundleIconFiles</key>
      <array>
        <string>alternateIconName</string>
      </array>
      <key>UIPrerenderedIcon</key>
      <false/>
    </dict>
  </dict>
</dict>
```

## Android Preparation

### Technical Considerations

* Android does not have a first-class way of supporting this functionality, so
  changing the icon takes about 10 seconds to take effect, and will remove any
  home screen shortcuts for the app. During the 10 seconds that the icon is
  changing, tapping the icon displays a message of 'App is not installed' to the
  user.
* We are unsure whether
  [Android adaptive icons](https://developer.android.com/guide/practices/ui_guidelines/icon_design_adaptive)
  will work or not. Some experimentation has been done, but no icon changes were
  realized.

### Android Icon Locations

Android icons are stored in your `android/app/src/main/res` directory, with a folder
for each dpi setting. The filenames of the icons must contain only lowercase
a-z, 0-9, or underscore. It is recommended to follow the naming
scheme `ic_launcher_[lowercase_icon_name]` to avoid conflicts

### AndroidManifest

Comment out or delete any existing `<category android:name="android.intent.category.LAUNCHER" />` lines. Take note of the android:name of the activity it is attached to, as this will be your targetActivity below.

Add activity-aliases to your project's AndroidManifest.xml file that map from an icon
name to an icon file. You can copy paste the following example then make the following changes:

* `android:enabled` must be `"true"` for exactly one activity-alias, which will be the default icon for the app.
* `android:name` **must** be of the format `.MainActivity_[ICON_NAME_HERE]`
* `android:icon` is the filename of the icon
* `android:targetActivity` is the fully qualified name of whatever xml item previously had `<category android:name="android.intent.category.LAUNCHER" />` attached to it.

      <activity-alias android:label="@string/app_name"
          android:icon="@mipmap/ic_launcher_default"
          android:name=".MainActivity_default"
          android:enabled="true"
          android:targetActivity="com.yourPackageName.yourTargetActivityName">
          <intent-filter>
              <action android:name="android.intent.action.MAIN" />
              <category android:name="android.intent.category.DEFAULT" />
              <category android:name="android.intent.category.LAUNCHER" />
          </intent-filter>
      </activity-alias>

### MainApplication.java

If the targetActivity from above is not `MainActivity` you will need to pass in a string with the short name of that activity to the ReactNativeAlternateIcons constructor in MainApplication.java, e.g.:

            new ReactNativeAlternateIcons("SplashActivity"),

## Using in your React Native applications

```javascript
import Icons from "react-native-alternate-icons";

/** Change the icons of your application */
Icons.setIconName(iconName);
/** get Current Icon Name */
Icons.getIconName(callback(result));
/** Reset the icon of your application to the default (iOS only at the moment)*/
Icons.reset();
/** Check if your device does support alternate icons, android always returns true */
Icons.supportDevice(callback(result));
```
