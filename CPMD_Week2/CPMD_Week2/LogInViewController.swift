
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
    @IBOutlet weak var emailLabel: UILabel!
    @IBOutlet weak var goToList: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if (FIRAuth.auth()?.currentUser) != nil{
            print("viewDidLoad - currentUser is " + (FIRAuth.auth()?.currentUser?.email)!)
            self.setUpUserSignedIn()
        }else{
            //User not signed in
            self.setUpUserNotSignedIn()
        }
        setUpKeyboardForTap()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func setUpKeyboardForTap(){
        self.emailTextField.delegate = self
        self.passwordTextField.delegate = self
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
    
    @IBAction func backToLogIn(segue: UIStoryboardSegue){
        //Target for the unwind
    }
    
    @IBAction func createAccountAction(sender: AnyObject){
        if emailTextField.text == "" || passwordTextField.text == "" {
            //alert
            let alert = UIAlertController(title: "Alert!", message: "Please enter an email and password.", preferredStyle: UIAlertControllerStyle.Alert)
            let okButton = UIAlertAction(title: "Ok", style: UIAlertActionStyle.Cancel, handler: nil)
            alert.addAction(okButton)
            presentViewController(alert, animated: true, completion: nil)
        }else{
            //Attempt to create account
            print("Attempting to create account")
            FIRAuth.auth()?.createUserWithEmail(emailTextField.text!, password: passwordTextField.text!) { (user, error) in
                if error == nil{
                    //User was created
                    print("createAccountAction - The email is " + self.emailTextField.text! + " and the password is " + self.passwordTextField.text!)
                    self.setUpUserSignedIn()
                }else{
                    //User was not created
                    //alert
                    let alert = UIAlertController(title: "Alert!", message: error?.localizedDescription, preferredStyle: UIAlertControllerStyle.Alert)
                    let okButton = UIAlertAction(title: "Ok", style: UIAlertActionStyle.Cancel, handler: nil)
                    alert.addAction(okButton)
                    self.presentViewController(alert, animated: true, completion: nil)
                    self.setUpUserNotSignedIn()
                }
            }
        }
    }
    
    @IBAction func logInAction(sender: AnyObject) {
        if emailTextField.text == "" || passwordTextField.text == "" {
            //alert
            let alert = UIAlertController(title: "Alert!", message: "Please enter an email and password.", preferredStyle: UIAlertControllerStyle.Alert)
            let okButton = UIAlertAction(title: "Ok", style: UIAlertActionStyle.Cancel, handler: nil)
            alert.addAction(okButton)
            presentViewController(alert, animated: true, completion: nil)
        }else{
            //Attempt to sign in
            print("Attempting to sign into account")
            FIRAuth.auth()?.signInWithEmail(emailTextField.text!, password: passwordTextField.text!) { (user, error) in
                if error != nil{
                    //User was not signed in
                    //alert
                    let alert = UIAlertController(title: "Alert!", message: error?.localizedDescription, preferredStyle: UIAlertControllerStyle.Alert)
                    let okButton = UIAlertAction(title: "Ok", style: UIAlertActionStyle.Cancel, handler: nil)
                    alert.addAction(okButton)
                    self.presentViewController(alert, animated: true, completion: nil)
                    //Update UI
                    self.setUpUserNotSignedIn()
                }else{
                    self.setUpUserSignedIn()
                }
            }
        }
    }
    
    func setUpUserSignedIn(){
        //Update UI
        self.emailTextField.hidden = true
        self.passwordTextField.hidden = true
        self.LogInButton.hidden = true
        self.createAccountButton.hidden = true
        self.thankYou.hidden = false
        self.emailLabel.hidden = false
        let user = FIRAuth.auth()?.currentUser
        self.emailLabel.text = user?.email
        self.logOutButton.hidden = false
        self.goToList.hidden = false
    }
    
    func setUpUserNotSignedIn(){
        //Update UI
        self.emailTextField.hidden = false
        self.passwordTextField.hidden = false
        self.LogInButton.hidden = false
        self.createAccountButton.hidden = false
        self.thankYou.hidden = true
        self.emailLabel.hidden = true
        self.logOutButton.hidden = true
        self.goToList.hidden = true
    }
    
    @IBAction func logOutAction(sender: AnyObject) {
        try! FIRAuth.auth()?.signOut()
        self.setUpUserNotSignedIn()
    }
}
