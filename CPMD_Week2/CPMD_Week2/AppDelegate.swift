//
//  AppDelegate.swift
//  CPMD_Week2
//
//  Created by Heather Wolford on 8/6/16.
//  Copyright © 2016 Heather Wolford. All rights reserved.
//

import UIKit
import Firebase

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    private var reachability: Reachability!


    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
        // Override point for customization after application launch.
        // Use Firebase library to configure APIs
        FIRApp.configure()
        do{
            NSNotificationCenter.defaultCenter().addObserver(self, selector:#selector(AppDelegate.checkForReachability(_:)), name: ReachabilityChangedNotification, object: nil);
            try self.reachability = Reachability.reachabilityForInternetConnection();
            try self.reachability.startNotifier();
        }catch{
            print(error)
        }
        return true
    }
    
    func checkForReachability(notification:NSNotification){
        let networkReachability = notification.object as! Reachability;
        let remoteHostStatus = networkReachability.currentReachabilityStatus
        print(remoteHostStatus)
        
        if String(remoteHostStatus) == "No Network Connection"{
            print("Not Reachable")
            let alert = UIAlertController(title: "No network detected!", message: "The device is not connected to a network. Please check your device settings.", preferredStyle: UIAlertControllerStyle.Alert)
            let okButton = UIAlertAction(title: "Ok", style: UIAlertActionStyle.Cancel, handler: nil)
            alert.addAction(okButton)
            self.window?.rootViewController?.presentViewController(alert, animated: true, completion: nil)
        }else{
            print("Reachable")
            let alert = UIAlertController(title: "Network connection found!", message: "The device is now connected to a network.", preferredStyle: UIAlertControllerStyle.Alert)
            let okButton = UIAlertAction(title: "Ok", style: UIAlertActionStyle.Cancel, handler: nil)
            alert.addAction(okButton)
            self.window?.rootViewController?.presentViewController(alert, animated: true, completion: nil)
        }
    }

    func applicationWillResignActive(application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(application: UIApplication) {
        // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }
}

