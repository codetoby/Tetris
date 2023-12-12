import matplotlib.pyplot as plt
import numpy as np

# Original data points
xPoints = np.array([0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75])
# yPoints1 = np.array([24, 24, 24, 22, 22, 22, 22])
# yPoints2 = np.array([16, 16, 16, 16, 16, 16, 16])
# yPoints3 = np.array([19, 19, 19, 33, 22, 22, 22])

yPoints1 = np.array([60, 60, 60, 60, 60, 60, 60])
yPoints2 = np.array([16, 16, 16, 16, 16, 16, 16])
yPoints3 = np.array([16, 16, 16, 16, 16, 16, 16])


jitter_strength =  0.01
xPoints_jittered1 = xPoints + np.random.normal(0, jitter_strength, xPoints.size)
xPoints_jittered2 = xPoints + np.random.normal(0, jitter_strength, xPoints.size)
xPoints_jittered3 = xPoints + np.random.normal(0, jitter_strength, xPoints.size)

# Plotting
plt.figure(figsize=(8, 7))
plt.title("Heuristic Steps\n Input: N, V, L, P, X, I, W, F, Y, Z, U, T\nScore after Game Ends\n60 is a upper bound")
plt.xlabel("Weight")
plt.ylabel("Score")
plt.xticks(xPoints)
plt.ylim([15, 61])

# Scatter plots with alpha blending
plt.scatter(xPoints_jittered1, yPoints1, alpha=0.7)
plt.scatter(xPoints_jittered2, yPoints2, alpha=0.7)
plt.scatter(xPoints_jittered3, yPoints3, alpha=0.7)

plt.legend(["Blue: Lines clearing", "Orange: Height Difference", "Green: Deadspaces"])
plt.grid()

plt.show()
