; ==========================
; Group member 01: Name_Surname_student-nr
; Group member 02: Name_Surname_student-nr
; Group member 03: Name_Surname_student-nr
; ==========================

extern allocateMatrix
section .text
    global addMatrices

addMatrices:
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

    ; Call allocateMatrix(rows, cols)
    mov rdi, r14    ; rows
    mov rsi, r15    ; cols
    call allocateMatrix
    mov rbx, rax    ; Store result matrix pointer in rbx

    ; Check if allocation was successful
    test rbx, rbx
    jz .return

    ; Perform matrix addition
    xor r8, r8      ; Row counter
.add_outer_loop:
    xor r9, r9      ; Column counter
    mov rsi, [r12 + r8 * 8]  ; Pointer to current row of matrix1
    mov rdi, [r13 + r8 * 8]  ; Pointer to current row of matrix2
    mov r10, [rbx + r8 * 8]  ; Pointer to current row of result matrix
.add_inner_loop:
    movss xmm0, [rsi + r9 * 4]
    addss xmm0, [rdi + r9 * 4]
    movss [r10 + r9 * 4], xmm0
    inc r9
    cmp r9, r15
    jl .add_inner_loop
    inc r8
    cmp r8, r14
    jl .add_outer_loop

.return:
    ; Return pointer to result matrix
    mov rax, rbx

    ; Restore stack and registers
    add rsp, 8
    pop r15
    pop r14
    pop r13
    pop r12
    pop rbx
    pop rbp
    ret