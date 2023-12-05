import matplotlib.pyplot as plt
import numpy as np

xPoints = np.array([0.25, 0.5, 0.75, 1])

yPoints1 = np.array([12, 12, 12, 12])
yPoints2 = np.array([11, 11, 11, 11])
yPoints3 = np.array([9, 10, 10, 10])

plt.title("Heuristic Steps\n Input: N, V, L, P, X, I, W, F, Y, Z, U, T")

plt.xlabel("Weight")
plt.ylabel("Score")

plt.xticks(xPoints)
plt.ylim([0, 12.5])

plt.scatter(xPoints, yPoints1)
plt.scatter(xPoints, yPoints2)
plt.scatter(xPoints, yPoints3)
plt.legend(["Blue: Lines clearing", "Orange: Height Difference", "Green: Deadspaces"])
plt.grid()
plt.show()