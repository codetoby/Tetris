import matplotlib.pyplot as plt
import numpy as np

xPoints = np.array([0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75])

# yPoints1 = np.array([9, 9, 9, 9, 9, 9, 9])
# yPoints2 = np.array([6, 6, 6, 6, 6, 6, 6])
# yPoints3 = np.array([10, 10, 10, 8, 8, 8, 8])

yPoints1 = np.array([14, 14, 14, 26, 24, 24, 24])
yPoints2 = np.array([6, 6, 6, 6, 6, 6, 6])
yPoints3 = np.array([23, 23, 23, 11, 11, 11, 11])

plt.title("Heuristic Steps\n Input: N, V, L, P, X, I, W, F, Y, Z, U, T\nScore after the Game Ends")

plt.xlabel("Weight")
plt.ylabel("Score")

plt.xticks(xPoints)
plt.ylim([5, 27])

plt.plot(xPoints, yPoints1)
plt.plot(xPoints, yPoints2)
plt.plot(xPoints, yPoints3)
plt.legend(["Blue: Lines clearing", "Orange: Height Difference", "Green: Deadspaces"])
plt.grid()
plt.show()