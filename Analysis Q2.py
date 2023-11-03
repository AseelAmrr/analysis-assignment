import matplotlib.pyplot as plt

import time
import timeit
def merge_sort(arr):
    if len(arr) > 1:
        mid = len(arr) // 2
        left_half = arr[:mid]
        right_half = arr[mid:]

        merge_sort(left_half)
        merge_sort(right_half)

        i = 0
        j = 0
        k = 0

        while i < len(left_half) and j < len(right_half):
            if left_half[i] < right_half[j]:
                arr[k] = left_half[i]
                i += 1
            else:
                arr[k] = right_half[j]
                j += 1
            k += 1

        while i < len(left_half):
            arr[k] = left_half[i]
            i += 1
            k += 1

        while j < len(right_half):
            arr[k] = right_half[j]
            j += 1
            k += 1

def binary_search(arr, target, index):
    left, right = 0, len(arr) - 1
    while left <= right:
        mid = left + (right - left) // 2
        if arr[mid] == target and mid != index:
            return True
        elif arr[mid] < target:
            left = mid + 1
        else:
            right = mid - 1
    return False

def find_pairs_with_sum(arr, target):
    merge_sort(arr)  # Sort the array using Merge Sort
    pairs = []

    for i in range(len(arr)):
        x = arr[i]
        y = target - x

        if x <= y and binary_search(arr, y, i):
            pairs.append((x, y))

    return pairs





# Example usage:
S = [1,2,3,4,5,6,7,8,9]
H = 10
pairs = find_pairs_with_sum(S, H)
print("Pairs with sum", H, "are:", pairs)



def measure_time(n, target):
    arr = list(range(1, n + 1))
    start_time = time.time()
    find_pairs_with_sum(arr, target)
    end_time = time.time()
    return end_time - start_time

n_values = [10, 100, 1000, 10000, 100000, 1000000]
target = 10000

execution_times = []
for n in n_values:
    execution_time = measure_time(n, target)
    execution_times.append(execution_time)

plt.plot(n_values, execution_times, marker='o')
plt.xlabel('n')
plt.ylabel('Execution Time (s)')
plt.title('Algorithm Scalability')
plt.show()