/**
 * @providesModule ReactNativeAlternateIcons
 * @flow
 */
"use strict";

import { NativeModules, Platform } from "react-native";
var NativeReactNativeAlternateIcons = NativeModules.ReactNativeAlternateIcons;

var warning = require("fbjs/lib/warning");

const isIos = () => Platform.OS === "ios";

/**
 * High-level docs for the ReactNativeAlternateIcons API can be written here.
 */

var ReactNativeAlternateIcons = {
    setIconName(name) {
        NativeReactNativeAlternateIcons.setIconName(name);
    },
    reset() {
        isIos()
            ? NativeReactNativeAlternateIcons.reset()
            : warning("Not yet implemented for Android.");
    },
    getIconName(callback) {
        NativeReactNativeAlternateIcons.getIconName(result => {
            isIos()
                ? callback(result.iconName)
                : callback(result);
        });
    },
    supportDevice(callback) {
        isIos
            ? NativeReactNativeAlternateIcons.supportDevice(result => {
                callback(result.supported);
            })
            : callback(true);
    }
};

module.exports = ReactNativeAlternateIcons;
