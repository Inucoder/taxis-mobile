//
//  PointMarker.swift
//  TaxiTaxiiOS
//
//  Created by Angel Medina on 20/03/15.
//  Copyright (c) 2015 Angel Medina. All rights reserved.
//

import Foundation


class PointMarker: GMSMarker {

    let coordinate: CLLocationCoordinate2D

    init(locationCoordinate: CLLocationCoordinate2D) {
        self.coordinate = locationCoordinate
        super.init()
        position = locationCoordinate
        groundAnchor = CGPoint(x: 0.5, y: 1)
        appearAnimation = kGMSMarkerAnimationPop
    }
    
}