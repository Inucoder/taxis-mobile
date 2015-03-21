//
//  Zone.swift
//  TaxiTaxiiOS
//
//  Created by Angel Medina on 20/03/15.
//  Copyright (c) 2015 Angel Medina. All rights reserved.
//

import Foundation

class Zone: Mappable{
    
    var id: Int!
    var name: String!
    var polygon: [MapLatLng]!
    var mapPolygon: GMSPolygon!;
    
    required init?(_ map: Map) {
        mapping(map)
    }
    
    init(){
        
    }
        
    func mapping(map: Map) {
        id <- map["id"]
        name <- map["name"]
        polygon <- map["polygon"]
    }
}

