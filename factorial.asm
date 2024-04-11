// Calculates 7! and places result in R2
copy R1 7
math add R1 R2
copy R6 -1
math add R2 R6 R3
branch less than R3 R0 3
math mult R3 R2
math add R6 R3
branch greater than R3 R0 -3
