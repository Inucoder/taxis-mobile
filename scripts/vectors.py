# @author Oswaldo Ceballos Zavala
# @author Irving Caro Fierros
# @author Angel Medina Moreno
# @author Juan Ku Quintana

import math

x = 0
y = 1

def magnitude(V):
	return math.sqrt(V[0] * V[0] + V[1] * V[1])

def minus(A, B):
	return [A[x] - B[x], A[y] - B[y]]

def add(A, B):
	return [A[x] + B[x], A[y] + B[y]]

def scalarProduct(P, c):
	return [P[x] * c, P[y] * c]

