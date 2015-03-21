###
# @author Oswaldo Ceballos Zavala
# @author Irving Caro Fierros
# @author Angel Medina Moreno
# @author Juan Ku Quintana
###

_ = require 'underscore'
data = require './polygons-geo.json'
translate = require './translate'
scale = require './scale'

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
translate areas, -0.0015, 0.03
scale areas, 1.0001, 1.0

console.log JSON.stringify areas, null, 4
