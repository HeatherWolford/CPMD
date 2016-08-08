//
//  CreateAccountViewController.swift
//  CPMD_Week2
//
//  Created by Heather Wolford on 8/7/16.
//  Copyright © 2016 Heather Wolford. All rights reserved.
//

import UIKit
import Firebase

class CreateAccountViewController: UIViewController, UITextFieldDelegate {
    
    
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!

    override func viewDidLoad() {
        super.viewDidLoad()
        loadUI()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func loadUI(){
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
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.destinationViewController is LogInViewController{
            if emailTextField.text == "" || passwordTextField.text == "" {
                //alert
                let alert = UIAlertController(title: "Alert!", message: "You can not continue until all of the fields have text.", preferredStyle: UIAlertControllerStyle.Alert)
                let okButton = UIAlertAction(title: "Ok", style: UIAlertActionStyle.Default, handler: nil)
                alert.addAction(okButton)
                presentViewController(alert, animated: true, completion: nil)
            }else{
                //Attempt to create account
                print("Attempting to create account")
                FIRAuth.auth()?.signInWithEmail(emailTextField.text!, password: passwordTextField.text!) { (user, error) in
                    print("The email is " + self.emailTextField.text! + " and the password is " + self.passwordTextField.text!)
                }
            }
        }
    }
}
