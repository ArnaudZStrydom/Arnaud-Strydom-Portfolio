section .data
    temp_float dd 0.0           ; Define a temporary memory location for storing the float

section .text
    global calculateMatrixDotProduct

calculateMatrixDotProduct:
    ; Preserve callee-saved registers and align stack
    push rbp
    mov rbp, rsp
    push rbx
    push r12
    push r13
    push r14
    push r15
    sub rsp, 8      ; Align stack to 16 bytes

    ; Store parameters
    mov r12, rdi    ; matrix1
    mov r13, rsi    ; matrix2
    mov r14, rdx    ; rows
    mov r15, rcx    ; cols

    ; Initialize the dot product result to 0.0
    xorps xmm0, xmm0   ; xmm0 = 0.0 (accumulator for dot product)

    ; Check if rows or cols are zero
    test r14, r14      ; Check if rows == 0
    jz .return
    test r15, r15      ; Check if cols == 0
    jz .return

    ; Perform dot product
    xor r8, r8         ; Row counter
.outer_loop:
    xor r9, r9         ; Column counter
    mov rsi, [r12 + r8 * 8]   ; Pointer to current row of matrix1
    mov rdi, [r13 + r8 * 8]   ; Pointer to current row of matrix2

.inner_loop:
    movss xmm1, [rsi + r9 * 4]  ; Load matrix1[row][col] into xmm1
    movss xmm2, [rdi + r9 * 4]  ; Load matrix2[row][col] into xmm2
    mulss xmm1, xmm2            ; Multiply xmm1 and xmm2
    addss xmm0, xmm1            ; Accumulate result in xmm0
    inc r9
    cmp r9, r15
    jl .inner_loop

    inc r8
    cmp r8, r14
    jl .outer_loop

.return:
    ; Store result to temporary memory
    movss [temp_float], xmm0
    
    ; Move the result from temporary memory to rax
    mov rax, [temp_float]

    ; Restore stack and registers
    add rsp, 8
    pop r15
    pop r14
    pop r13
    pop r12
    pop rbx
    pop rbp
    ret

