//
//  Grocery.swift
//  CPMD_Week2
//
//  Created by Heather Wolford on 8/7/16.
//  Copyright © 2016 Heather Wolford. All rights reserved.
//

import UIKit
import Firebase

class Grocery{
    
    //Member variables
    var item: NSString = ""
    var amount: NSNumber = 0

    //Class Constructor
    init(){
    }

    init(item: NSString, amount: NSNumber) {
        self.item = item
        self.amount = amount
    }
    
    init(snapshot: FIRDataSnapshot) {
        item = snapshot.value!["item"] as! NSString
        amount = snapshot.value!["amount"] as! NSNumber
    }
    
    func toAnyObject() -> AnyObject {
        return [
            "item": item,
            "amount": amount]
    }
}
