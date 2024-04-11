// recursive fibonacci implementation
// finds fibonacci number 16 = 987
copy r1 16
push add r1 0
call 5
pop r5
halt

pop r31
pop r1
copy r2 1
copy r3 2
branch notequal r0 r1 3
push r0
push r31
return
branch notequal r1 r2 3
push r2
push r31
return
push r31
push r1
push sub r2 r1
call 5
pop r4
pop r1
push r4
push sub r3 r1
call 5
pop r5
pop r4
pop r31
push add r4 r5
push r31
return
