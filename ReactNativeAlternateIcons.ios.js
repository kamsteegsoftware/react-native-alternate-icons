/**
 * @providesModule ReactNativeAlternateIcons
 * @flow
 */
'use strict';

var NativeReactNativeAlternateIcons = require('NativeModules').ReactNativeAlternateIcons;

/**
 * High-level docs for the ReactNativeAlternateIcons iOS API can be written here.
 */

var ReactNativeAlternateIcons = {
  setIconName: function( name ) {
    NativeReactNativeAlternateIcons.setIconName( name );
  },
  reset: function() {
    NativeReactNativeAlternateIcons.reset();
  }
};

module.exports = ReactNativeAlternateIcons;
