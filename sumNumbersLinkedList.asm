copy R1 711 // pointer to the head of the list

load R1 R2 // get value
math add R2 R3 // add value
load R1 1 // load next address
branch notequal R0 R1 -4