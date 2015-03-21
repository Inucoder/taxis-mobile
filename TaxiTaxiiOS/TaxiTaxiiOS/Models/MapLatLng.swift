//
//  MapLatLng.swift
//  TaxiTaxiiOS
//
//  Created by Angel Medina on 20/03/15.
//  Copyright (c) 2015 Angel Medina. All rights reserved.
//

import Foundation

class MapLatLng: Mappable {
    
    var lat: Double?
    var lng: Double?
    
    required init?(_ map: Map) {
        mapping(map)
    }
    
    func mapping(map: Map) {
        lat <- map["lat"]
        lng <- map["lng"]
    }
}