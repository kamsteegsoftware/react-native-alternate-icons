#import "ReactNativeAlternateIcons.h"
#import <React/RCTLog.h>
#import <React/RCTClipboard.h>
#import <UIKit/UIKit.h>

@implementation ReactNativeAlternateIcons

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(setIconName:(NSString *)name){
    NSLog(@"Setting the following icon: %@", name);
    [[UIApplication sharedApplication] setAlternateIconName:name completionHandler:^(NSError * _Nullable error) {
        if( error != nil ){
            NSLog(@"Error: %@", error.description );
        }
    }];
}

RCT_EXPORT_METHOD(reset){
    [[UIApplication sharedApplication] setAlternateIconName:nil completionHandler:^(NSError * _Nullable error) {
        if( error != nil ){
            NSLog(@"Error: %@", error.description );
        }
    }];
}

RCT_EXPORT_METHOD(getIconName:(RCTResponseSenderBlock) callback ){
    //resolve( [[UIApplication sharedApplication] alternateIconName] );]
    NSString *name = [[UIApplication sharedApplication] alternateIconName];
    if( name == nil ){
        name = @"default";
    }
    
    NSDictionary *results = @{
                              @"iconName":name
                              };
    
    callback(@[results]);
}

RCT_EXPORT_METHOD(supportDevice:(RCTResponseSenderBlock) callback){
    NSDictionary *results;
    
    if( [[UIApplication sharedApplication] supportsAlternateIcons ] ){
        results = @{
            @"supported":YES
        };
    }else{
        results = @{
            @"supported":NO
        };
    }
    
    callback(@[results]);
}

@end
