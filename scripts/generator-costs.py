# @author Oswaldo Ceballos Zavala
# @author Irving Caro Fierros
# @author Angel Medina Moreno
# @author Juan Ku Quintana

import sys
import json

costs = []

for line in sys.stdin:
	prices = line.split()
	zoneCosts = []

	for price in prices:
		price = int(price)
		zoneCosts.append(price)

	costs.append(zoneCosts)

print(json.dumps(costs))
