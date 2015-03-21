###
# @author Oswaldo Ceballos Zavala
# @author Irving Caro Fierros
# @author Angel Medina Moreno
# @author Juan Ku Quintana
###

_ = require 'underscore'
zones = require './zones.json'

geojson = 
	type: 'MultiPolygon'
	coordinates: []

geojson.coordinates[0] = []

_.each zones, (zone)->
	poly = _.map zone.polygon, (latLng)->
		[latLng.lng, latLng.lat]

	geojson.coordinates[0].push poly

console.log JSON.stringify geojson, null, 4
