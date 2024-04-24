copy R1 1
copy R2 200 // first array address
copy R5 220
load R2 R3
math add R3 R4
math add R1 R2
branch less than R2 R5 -4