//
//  DashViewController.swift
//  TaxiTaxiiOS
//
//  Created by Angel Medina on 20/03/15.
//  Copyright (c) 2015 Angel Medina. All rights reserved.
//

import UIKit

class DashViewController: UIViewController {
    
    @IBOutlet weak var myCurrentLocation: UILabel!
    
    @IBOutlet weak var mapDashView: GMSMapView!
    
    var zonesGMS: [Zone]! = []
    
    var selectedZone: Zone?
    
    var currentZone: Zone?
    
    var pointMarkerTarget: PointMarker?
    
    let locationManager=CLLocationManager()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.barTintColor = UIColor(red:38,green:194,blue:129)
        
        self.locationManager.delegate = self
        self.mapDashView.delegate = self
        self.locationManager.requestWhenInUseAuthorization()
        for zone in ZoneService.instance.getZones(){
            zonesGMS.append(self.mapDashView.displayZone(zone));
        }
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    @IBAction func costTaxi(sender: AnyObject) {
        let alert = SCLAlertView()
        if selectedZone != nil && currentZone != nil {
            alert.showSuccess("Tarifa", subTitle: "De la \(selectedZone!.name!) a la \(currentZone!.name!)")
        }else{
            alert.showError("Selecciona tu destino", subTitle:"Aún no has seleccionado tu punto de destino.", closeButtonTitle:"Aceptar")
        }
    }
    
}

extension DashViewController: CLLocationManagerDelegate {
    
    func locationManager(manager: CLLocationManager!, didChangeAuthorizationStatus status: CLAuthorizationStatus) {
        if status == .AuthorizedWhenInUse {
            locationManager.startUpdatingLocation()
            mapDashView.myLocationEnabled = true
            mapDashView.settings.myLocationButton = true
        }
    }
    
    func locationManager(manager: CLLocationManager!, didUpdateLocations locations: [AnyObject]!) {
        if let location = locations.first as? CLLocation {
            mapDashView.camera = GMSCameraPosition(target: location.coordinate, zoom: 12, bearing: 0, viewingAngle: 0)
            locationManager.stopUpdatingLocation()
        }
    }
    
}

extension DashViewController: GMSMapViewDelegate{
    
    func mapView( mapView: GMSMapView!, didTapAtCoordinate coordinate: CLLocationCoordinate2D) -> Void {
        if pointMarkerTarget != nil{
            pointMarkerTarget?.map = nil
        }
        if selectedZone != nil {
            selectedZone?.mapPolygon.fillColor = UIColor(red:0.39, green:0.90, blue:0.75, alpha: 0.1);
        }
        pointMarkerTarget = PointMarker(locationCoordinate:  coordinate)
        pointMarkerTarget?.map = self.mapDashView
        selectedZone = mapDashView.getZoneFor(coordinate,zonesGMS: zonesGMS)
        if selectedZone != nil {
            selectedZone?.mapPolygon.fillColor = UIColor(red:0.39, green:0.90, blue:0.75, alpha: 0.5);
        }
    }
    
    func mapView(mapView: GMSMapView!, idleAtCameraPosition position: GMSCameraPosition!) {
        myCurrentLocationName(position.target)
    }
    
    func myCurrentLocationName(coordinate: CLLocationCoordinate2D) {
        let geocoder = GMSGeocoder()
        let newLine: String = "\n"

//        if currentZone != nil {
//            currentZone?.mapPolygon.fillColor = UIColor(red:0.60, green:0.90, blue:0.90, alpha: 0.1);
//        }
        
        geocoder.reverseGeocodeCoordinate(coordinate) { response , error in
            if let location = response?.firstResult() {
                let location_strs = location.lines as [String]
                self.myCurrentLocation.text = join(newLine, location_strs)
                UIView.animateWithDuration(0.25) {
                    self.view.layoutIfNeeded()
                }
            }
        }
        currentZone = mapDashView.getZoneFor(coordinate,zonesGMS: zonesGMS)
        if currentZone != nil {
            currentZone?.mapPolygon.fillColor = UIColor(red:0.60, green:0.60, blue:0.60, alpha: 0.5);
        }
    }
}

