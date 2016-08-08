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

    var dataArray: [Grocery] = [Grocery]()

    override func viewDidLoad() {
        super.viewDidLoad()
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
        return dataArray.count
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("reuseIdentifier") as! CustomCell
        //let current: Grocery = dataArray[indexPath.row]
        // Configure the cell...
        //cell.amountLabel.text = String(current.amount)
        //cell.itemLabel.text = current.item
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
}
