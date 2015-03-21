//
//  ExtensionString.swift
//  TaxiTaxiiOS
//
//  Created by Angel Medina on 21/03/15.
//  Copyright (c) 2015 Angel Medina. All rights reserved.
//

import Foundation

extension String {
    var floatValue: Float {
        return (self as NSString).floatValue
    }
}