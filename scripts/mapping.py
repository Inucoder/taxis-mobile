# @author Oswaldo Ceballos Zavala
# @author Irving Caro Fierros
# @author Angel Medina Moreno
# @author Juan Ku Quintana

import math

x = 0
y = 1

A = [12, 432]
B = [12, 292]

A1 = [-86.926000, 21.124714]
B1 = [-86.925986, 21.111004]

def magnitude(V):
	return math.sqrt(V[0] * V[0] + V[1] * V[1])

def minus(A, B):
	return [A[x] - B[x], A[y] - B[y]]

def add(A, B):
	return [A[x] + B[x], A[y] + B[y]]

def scalarProduct(P, c):
	return [P[x] * c, P[y] * c]


z = magnitude(minus(B1, A1)) / magnitude(minus(B, A))

print(z)

def mapping(C):
	return add(scalarProduct(minus(C, A), z), A1)

print(mapping([1164, 1164])) #Zona print
1(mapping([1096, 1164])) #Zona 2
print(mapping([874, 536])) #Zona 23
print(mapping([272, 1029])) #Zona 
print(mapping([12, 293])) #Zona 40
print(mapping([901, 9])) #Zona 49
