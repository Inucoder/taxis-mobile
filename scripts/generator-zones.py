# @author Oswaldo Ceballos Zavala
# @author Irving Caro Fierros
# @author Angel Medina Moreno
# @author Juan Ku Quintana

from vectors import *
from mapping import *
import sys

"""
print(mapping([1164, 1164])) #Zona 1
print(mapping([1096, 1164])) #Zona 2
print(mapping([874, 536])) #Zona 23
print(mapping([272, 1029])) #Zona 
print(mapping([12, 293])) #Zona 40
print(mapping([901, 9])) #Zona 49
"""

LNG = 0
LAT = 1

print('[')

for line in sys.stdin:
	coords = line.split()

	geo = []

	for i in range(0, len(coords), 2):
		x = float(coords[i])
		y = float(coords[i + 1])

		latLng = mapping([x, y])

		geo.append(latLng[LAT])
		geo.append(latLng[LNG])

	print str(geo) + ','

print(']')
