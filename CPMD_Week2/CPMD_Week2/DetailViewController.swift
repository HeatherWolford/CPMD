//
//  DetailViewController.swift
//  CPMD_Week2
//
//  Created by Heather Wolford on 8/16/16.
//  Copyright © 2016 Heather Wolford. All rights reserved.
//

import UIKit
import Firebase

class DetailViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var itemTextField: UITextField!
    @IBOutlet weak var qtyTextField: UITextField!
    var item: String = ""
    var qty: String = ""
    var dataArray: [AnyObject] = []
    var currentIndexNumber = 0
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        itemTextField.text = item
        qtyTextField.text = qty
        checkDatabaseForData()
        loadUI()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    @IBAction func updateButton(sender: AnyObject) {
    }
    
    func loadUI(){
        self.itemTextField.delegate = self
        self.qtyTextField.delegate = self
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(FormViewController.dismissKeyboard))
        view.addGestureRecognizer(tap)
    }
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        self.view.endEditing(true)
        return false
    }
    
    func dismissKeyboard() {
        view.endEditing(true)
    }
    
    func checkDatabaseForData(){
        let user = FIRAuth.auth()?.currentUser
        let userID = user?.uid
        let rootRef = FIRDatabase.database().reference()
        let userRef = rootRef.child(userID!)
        userRef.observeEventType(.Value, withBlock: { snapshot in
            var newItems = [AnyObject]()
            for item in snapshot.children {
                let groceryItem = Grocery(snapshot: item as! FIRDataSnapshot)
                newItems.append(groceryItem.toAnyObject())
            }
            self.dataArray = newItems
            print("checkDatabaseForData - Before adding item, the size of dataArray is " + String(self.dataArray.count))
        })
    }


    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.destinationViewController is TableViewController{
            print("prepareForSegue")
            let user = FIRAuth.auth()?.currentUser
            let userID = user?.uid
            print("The value from itemTextField is " + itemTextField.text!)
            print("The value from qtyTextField is " + qtyTextField.text!)
            if (itemTextField.text == "" || qtyTextField.text == ""){
                let alert = UIAlertController(title: "Alert!", message: "Please enter both an item and quantity.", preferredStyle: UIAlertControllerStyle.Alert)
                let okButton = UIAlertAction(title: "Ok", style: UIAlertActionStyle.Cancel, handler: nil)
                alert.addAction(okButton)
                presentViewController(alert, animated: true, completion: nil)
            }else{
                let item = itemTextField.text!
                let amount = qtyTextField.text!
                let intAmount = Int(amount)
                let nsNumberAmount = NSNumber(integer:intAmount!)
                let grocery = Grocery(item: item, amount: nsNumberAmount)
                let rootRef = FIRDatabase.database().reference()
                let userRef = rootRef.child(userID!)
                dataArray.removeAtIndex(currentIndexNumber)
                dataArray.append(grocery.toAnyObject())
                userRef.setValue(dataArray)
            }
        }
    }
}
