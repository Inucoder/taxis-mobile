# @author Oswaldo Ceballos Zavala
# @author Irving Caro Fierros
# @author Angel Medina Moreno
# @author Juan Ku Quintana

from vectors import *

A = [12, 432]
B = [12, 292]

A1 = [-86.926000, 21.124714]
B1 = [-86.925986, 21.111004]

z = magnitude(minus(B1, A1)) / magnitude(minus(B, A))

#print(z)

def mapping(C):
	return add(scalarProduct(minus(C, A), z), A1)
