//
//  FormViewController.swift
//  CPMD_Week2
//
//  Created by Heather Wolford on 8/6/16.
//  Copyright © 2016 Heather Wolford. All rights reserved.
//

import UIKit

class FormViewController: UIViewController, UITextFieldDelegate {
    
    var itemName: String = ""
    var qty: Int = 0

    @IBOutlet weak var itemTextField: UITextField!
    @IBOutlet weak var qtyTextField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        loadUI()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
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

    // MARK: - Navigation
    //Collect the input from the text fields
    //Save the input from the text fields
    //Navigate back to list and make sure it has been updated with info
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        print("prepareForSegue");
    }
}
