import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

df = pd.read_csv('execution-time-test.csv')
exec_time_ms = df['exec-time-ms']
n = df['n']

coefficients = np.polyfit(n, exec_time_ms, 1)
line_of_best_fit = np.poly1d(coefficients)

plt.scatter(n, exec_time_ms, c='blue')
plt.plot(n, line_of_best_fit(n), c='purple')
plt.xlabel('n')
plt.ylabel('Execution Time (ms)')
plt.title('')
plt.show()