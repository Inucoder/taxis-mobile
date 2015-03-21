###
# @author Oswaldo Ceballos Zavala
# @author Irving Caro Fierros
# @author Angel Medina Moreno
# @author Juan Ku Quintana
###

_ = require 'underscore'
data = require './polygons-geo.json'
translate = require './translate'

#console.log data

areas = []

_.each data, (areaData, idx)->
	area =
		id: idx
		name: "Zona #{idx + 1}"
		polygon: []

	for k in _.range 0, areaData.length, 2
		area.polygon.push 
			lat: areaData[k]
			lng: areaData[k + 1]

	area.polygon.push _.first area.polygon

	areas.push area

#transform
translate areas, -0.001, 0.03

console.log JSON.stringify areas, null, 4
