//
//  ZoneService.swift
//  TaxiTaxiiOS
//
//  Created by Angel Medina on 21/03/15.
//  Copyright (c) 2015 Angel Medina. All rights reserved.
//

import Foundation

class ZoneService {
    
    class var instance : ZoneService {
        struct Static {
            static let instance : ZoneService = ZoneService()
        }
        return Static.instance
    }
    
    func getZones() -> [Zone] {
        let filePath = NSBundle.mainBundle().pathForResource(CONFIG.FILE.ZONES,ofType:CONFIG.FILE.TYPE)
        var readError:NSError?
        let data = NSData(contentsOfFile:filePath!, options:NSDataReadingOptions.DataReadingUncached, error:&readError)
        var json = JSON(data: data!)
        var zones: Array<Zone> = Array<Zone>()
        for (key: String, subJson: JSON) in json {
            var zoneAux: Zone = Mapper<Zone>().map(string: subJson.rawString()! )!
            zones.append(zoneAux)
        }
        return zones
    }
    
    func getCosts() -> JSON {
        let filePath = NSBundle.mainBundle().pathForResource(CONFIG.FILE.COSTS,ofType:CONFIG.FILE.TYPE)
        var readError:NSError?
        let data = NSData(contentsOfFile:filePath!, options:NSDataReadingOptions.DataReadingUncached, error:&readError)
        var json = JSON(data: data!)
//        var zones: Array<Array> = Array<Array>()
//        for (key: String, subJson: JSON) in json {
//            var zoneAux: Zone = Mapper<Zone>().map(string: subJson.rawString()! )!
//            zones.append(zoneAux)
//            println(key)
//            println(subJson.rawString())
//        }
        return json
    }
    
}