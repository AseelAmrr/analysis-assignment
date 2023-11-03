import matplotlib.pyplot as plt

import time
import timeit

def naive(x, y):
    result = 1
    for _ in range(y):
        result = result * x
    return result

def divide_conquer(x, y):
    if y == 0:
        return 1
    if y % 2 == 0:
        temp = divide_conquer(x, y // 2)
        return temp * temp
    else:
        temp = divide_conquer(x, (y - 1) // 2)
        return base * temp * temp



exponents = list(range(1, 106))  # You can adjust the range accordingly
results_iterative = []
results_divide_conquer = []

for exponent in exponents:
    base = 2  # Choose any base
    time_iterative = timeit.timeit(lambda: naive(base, exponent), number=1000)
    results_iterative.append(time_iterative)

    time_divide_conquer = timeit.timeit(lambda: divide_conquer(base, exponent), number=1000)
    results_divide_conquer.append(time_divide_conquer)

# Plotting the results using a plotting library (e.g., Matplotlib)


plt.plot(exponents, results_iterative, label='Iterative')
plt.plot(exponents, results_divide_conquer, label='Divide & Conquer')
plt.xlabel('Exponent')
plt.ylabel('Time (seconds)')
plt.legend()
plt.show()
print(results_iterative)
print(results_divide_conquer)

