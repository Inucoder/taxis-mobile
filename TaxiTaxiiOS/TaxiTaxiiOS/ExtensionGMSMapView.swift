//
//  ExtensionGMSMapView.swift
//  TaxiTaxiiOS
//
//  Created by Angel Medina on 21/03/15.
//  Copyright (c) 2015 Angel Medina. All rights reserved.
//

import Foundation

extension GMSMapView {
    
    func displayZone(zone: Zone) -> Zone{
        var path = GMSMutablePath()
        for coor in zone.polygon {
            path.addCoordinate(CLLocationCoordinate2DMake(coor.lat!, coor.lng!))
        }
        var polygon = GMSPolygon(path: path)
        polygon.fillColor = UIColor(red:0.39, green:0.90, blue:0.75, alpha: 0.1);
        polygon.strokeColor = UIColor.blackColor()
        polygon.strokeWidth = 2
        polygon.map = self
        zone.mapPolygon = polygon
        return zone
    }
    
    func getZoneFor(coordinate: CLLocationCoordinate2D, zonesGMS: [Zone]) -> Zone? {
        for zone in zonesGMS {
            if GMSGeometryContainsLocation(coordinate, zone.mapPolygon.path, zone.mapPolygon.geodesic) {
                return zone
            }
        }
        return nil
    }
    
}