
//
//  LogInViewController.swift
//  CPMD_Week2
//
//  Created by Heather Wolford on 8/7/16.
//  Copyright © 2016 Heather Wolford. All rights reserved.
//

import UIKit
import Firebase

class LogInViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var LogInButton: UIButton!
    @IBOutlet weak var logOutButton: UIButton!
    @IBOutlet weak var createAccountButton: UIButton!
    @IBOutlet weak var thankYou: UILabel!
    @IBOutlet weak var goToList: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        loadUI()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func loadUI(){
        self.emailTextField.delegate = self
        self.passwordTextField.delegate = self
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(FormViewController.dismissKeyboard))
        view.addGestureRecognizer(tap)
        FIRAuth.auth()?.addAuthStateDidChangeListener { auth, user in
            if user != nil {
                // User is signed in.
                print("User is " + (user?.uid)!)
                self.emailTextField.hidden = true
                self.passwordTextField.hidden = true
                self.LogInButton.hidden = true
                self.createAccountButton.hidden = true
                self.thankYou.hidden = false
                self.logOutButton.hidden = false
                self.goToList.hidden = false
            } else {
                // No user is signed in.
                print("No user!")
                self.emailTextField.hidden = false
                self.passwordTextField.hidden = false
                self.LogInButton.hidden = false
                self.createAccountButton.hidden = false
                self.thankYou.hidden = true
                self.logOutButton.hidden = true
                self.goToList.hidden = true
            }
        }

    }
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        self.view.endEditing(true)
        return false
    }
    
    func dismissKeyboard() {
        view.endEditing(true)
    }
    @IBAction func backToLogIn(segue: UIStoryboardSegue){
        //Target for the unwind
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.destinationViewController is TableViewController{
            if emailTextField.text == "" || passwordTextField.text == "" {
                //alert
                let alert = UIAlertController(title: "Alert!", message: "You can not continue until all of the fields have text.", preferredStyle: UIAlertControllerStyle.Alert)
                let okButton = UIAlertAction(title: "Ok", style: UIAlertActionStyle.Default, handler: nil)
                alert.addAction(okButton)
                presentViewController(alert, animated: true, completion: nil)
            }else{
               //Attempt Login
            }
        }
    }
}
