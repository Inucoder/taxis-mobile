###
# @author Oswaldo Ceballos Zavala
# @author Irving Caro Fierros
# @author Angel Medina Moreno
# @author Juan Ku Quintana
###

_ = require 'underscore'

module.exports = (zones, scaleLat, scaleLng)->
	_.each zones, (zone)->
		zone.polygon = _.map zone.polygon, (latLng)->
			lat: latLng.lat * scaleLat
			lng: latLng.lng * scaleLng
