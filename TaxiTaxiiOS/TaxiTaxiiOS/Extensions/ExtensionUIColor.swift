//
//  ExtensionUIColor.swift
//  TaxiTaxiiOS
//
//  Created by Angel Medina on 20/03/15.
//  Copyright (c) 2015 Angel Medina. All rights reserved.
//

import UIKit

extension UIColor {

    convenience init(red: Int, green: Int, blue: Int) {
        let CCLV = CGFloat(255);
        let red = CGFloat(red)/CCLV
        let green = CGFloat(green)/CCLV
        let blue = CGFloat(blue)/CCLV
        self.init(red: red, green: green, blue: blue, alpha: 1.0)
    }
    
}
