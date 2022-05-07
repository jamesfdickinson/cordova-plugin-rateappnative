/********* RateAppNative.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import <StoreKit/StoreKit.h>

@interface RateAppNative : CDVPlugin {
  // Member variables go here.
}

- (void)rate:(CDVInvokedUrlCommand*)command;
@end

@implementation RateAppNative

- (void)rate:(CDVInvokedUrlCommand*)command
{
    
   if ([SKStoreReviewController class]) {
    
        [self.commandDelegate runInBackground:^{
            [SKStoreReviewController requestReview];
        }];
    }

    CDVPluginResult* pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK ];
  
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
