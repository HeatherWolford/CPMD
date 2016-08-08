//
//  TableViewController.swift
//  CPMD_Week2
//
//  Created by Heather Wolford on 8/6/16.
//  Copyright © 2016 Heather Wolford. All rights reserved.
//

import UIKit
import Firebase

class TableViewController: UITableViewController {

    var dataArray = [Grocery]()

    override func viewDidLoad() {
        super.viewDidLoad()
        checkDatabaseForData()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    @IBAction func backToList(segue: UIStoryboardSegue){
        //Target for the unwind
    }

    // MARK: - Table view data source

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        //Return the number of rows
        print("numberOfRowsInSection - the dataArray size is " + String(dataArray.count))
        return dataArray.count
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("reuseIdentifier") as! CustomCell
        var newArray: [Grocery] = [Grocery]()
        newArray = dataArray
        let current: Grocery = newArray[indexPath.row]
        // Configure the cell...
        cell.amountLabel.text = String(current.amount)
        cell.itemLabel.text = current.item as String
        return cell
    }

    // Override to support conditional editing of the table view.
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }

    // Override to support editing the table view.
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete {
            // Delete the row from the data source
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
            //Delete from the database
            //Update the dataArray
            //Update the tableview
        }
    }
    
    func checkDatabaseForData(){
        let user = FIRAuth.auth()?.currentUser
        let userID = user?.uid
        let rootRef = FIRDatabase.database().reference()
        let userRef = rootRef.child(userID!)
        userRef.observeEventType(.Value, withBlock: { snapshot in
            var newItems = [Grocery]()
            for item in snapshot.children {
                let groceryItem = Grocery(snapshot: item as! FIRDataSnapshot)
                newItems.append(groceryItem)
            }
            self.dataArray = newItems
            print("checkDatabaseForData - Before adding item, the size of dataArray is " + String(self.dataArray.count))
            self.tableView.reloadData()
        })
    }
}
