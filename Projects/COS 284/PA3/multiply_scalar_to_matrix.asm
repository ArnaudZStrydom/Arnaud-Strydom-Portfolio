; ==========================
; Group member 01: Name_Surname_student-nr
; Group member 02: Name_Surname_student-nr
; Group member 03: Name_Surname_student-nr
; ==========================

section .text
global multiplyScalarToMatrix

multiplyScalarToMatrix:
    push rbx
    push rsi
    push rdi
    push rcx

    xor rbx, rbx  ; row counter

.row_loop:
    cmp rbx, rsi
    je .end

    mov rax, [rdi + rbx * 8]

    xor rcx, rcx  ; column counter
.col_loop:
    cmp rcx, rdx
    je .next_row

    movss xmm1, [rax + rcx * 4]
    mulss xmm1, xmm0
    movss [rax + rcx * 4], xmm1

    inc rcx
    jmp .col_loop

.next_row:
    inc rbx
    jmp .row_loop

.end:
    pop rcx
    pop rdi
    pop rsi
    pop rbx
    ret
