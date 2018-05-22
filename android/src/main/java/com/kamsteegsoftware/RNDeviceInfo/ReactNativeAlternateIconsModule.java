package com.kamsteegsoftware.ReactNativeAlternateIcons;


import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class ReactNativeAlternateIconsModule extends ReactContextBaseJavaModule {

  ReactApplicationContext reactContext;
  private String targetActivityName;

  private static final String MAIN_ACTIVITY_NAME = "MainActivity";
  private static final String TAG = "ReactNativeAlternateIconsModule";

  public ReactNativeAlternateIconsModule(ReactApplicationContext reactContext, String targetActivityName) {
    super(reactContext);

    this.reactContext = reactContext;
    this.targetActivityName = targetActivityName;
  }

  @Override
  public String getName() {
    return "ReactNativeAlternateIcons";
  }

  public String getComponentNamePrefix() {
    return getCurrentPackageName() + "." + MAIN_ACTIVITY_NAME + "_";
  }

  private String getFullComponentName(String iconName) {
    return getComponentNamePrefix() + iconName;
  }

  private String getIconName() {
    String aliasName = getCurrentAliasName();
    if (aliasName == null) {
      return null;
    }
    int componentNamePrefixLength = getComponentNamePrefix().length();
    return aliasName.substring(componentNamePrefixLength);
  }


  @ReactMethod
  public void getIconName(Callback callback) {
      try {
          callback.invoke(getIconName());
      } catch(Exception error) {
          Log.e(TAG, "Something went wrong while getting the iconName. Caught Error:");
          error.printStackTrace();
      }
  }

  @ReactMethod
  public void setIconName(String desiredIconName) {
    String currentComponentName = getCurrentAliasName();
    String potentialNewComponentName = getFullComponentName(desiredIconName);
    boolean shouldActivateNewComponent = shouldActivateNewComponent(potentialNewComponentName, currentComponentName);
    if (!shouldActivateNewComponent) {
      return;
    }

    try {
        enableOrDisableComponent(potentialNewComponentName, true);
    } catch(Exception error) {
      Log.i(TAG, "Could not set enabled activity-alias. Most likely the icon name does not have an associated alias. Caught Error:");
      error.printStackTrace();
      return;
    }
    try {
        boolean shouldDisableOldComponent = currentComponentName != null && !currentComponentName.isEmpty();
        if (!shouldDisableOldComponent) {
            return;
        }
        enableOrDisableComponent(currentComponentName, false);
    } catch(Exception error) {
        Log.w(TAG, "Could not disable active activity-alias Caught Error:");
        error.printStackTrace();
        return;
    }
  }

  private void enableOrDisableComponent(String componentName, boolean enabled) {
      reactContext
              .getPackageManager()
              .setComponentEnabledSetting(
                      new ComponentName(
                              getCurrentPackageName(),
                              componentName
                      ),
                      enabled ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED ,
                      PackageManager.DONT_KILL_APP
              );
  }

  private Boolean shouldActivateNewComponent(String potentialNewComponentName, String currentComponentName) {
    try {
      return potentialNewComponentName != null
        && !potentialNewComponentName.isEmpty()
        && (
            currentComponentName == null ||
            !potentialNewComponentName.equals(currentComponentName)
        );
    } catch (Exception e) {
        //returning true because if things are in a weird state, let's try to change them. This could be incorrect.
        return true;
    }
  }

  public String getCurrentPackageName() {
    return reactContext.getCurrentActivity().getPackageName();
  }

  public String getCurrentAliasName() {
    String expectedTargetActivity = getCurrentPackageName() + "." + targetActivityName;

    PackageManager pManager = reactContext.getPackageManager();
    String packageName = getCurrentPackageName();
    try {
      ActivityInfo[] list = pManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).activities;
      // list only has enabled activity aliases in it.
      for (ActivityInfo activityInfo : list) {
        String name = activityInfo.name;
        String targetActivity = activityInfo.targetActivity;
        if (targetActivity != null && targetActivity.equals(expectedTargetActivity)) {
          return name;
        }
      }
    }
    catch (PackageManager.NameNotFoundException e) {
        Log.w(TAG, "PackageManager name not found when getting alias name. Caught Error:");
        e.printStackTrace();
    }
    Log.w(TAG, "no current alias name selected");
    return "";
  }
}
