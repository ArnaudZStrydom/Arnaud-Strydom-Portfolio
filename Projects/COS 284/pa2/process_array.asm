; ==========================
; Group member 01: Name_Surname_student-nr
; Group member 02: Name_Surname_student-nr
; Group member 03: Name_Surname_student-nr
; ==========================
section .text
global processArray

processArray:
    push rbp
    mov rbp, rsp
    push rbx
    push rdi
    push rsi

    mov rdi, rdi           ; arr (pointer to float array)
    mov esi, esi           ; size (number of elements in the array)
    xor rax, rax           ; Clear rax, it will hold the index
    xorps xmm0, xmm0       ; Clear xmm0 for accumulating the result

    cmp esi, 1             ; Check if the array size is less than or equal to 1
    jle .return_zero

.process_loop:
    movss xmm1, [rdi + rax*4] ; Load float from array into xmm1
    cvtss2sd xmm1, xmm1       ; Convert the float to double precision

    movss xmm2, [rdi + rax*4 + 4] ; Load next float into xmm2
    cvtss2sd xmm2, xmm2           ; Convert the next float to double precision

    mulsd xmm1, xmm2          ; Multiply the two doubles
    addsd xmm0, xmm1          ; Accumulate the result

    inc rax                   ; Increment index
    cmp rax, rsi              ; Compare index with size - 1
    jge .finalize             ; If we've processed all elements, jump to finalize
    jmp .process_loop

.finalize:
    ; The result is already in xmm0, so we just return it

    pop rsi
    pop rdi
    pop rbx
    mov rsp, rbp
    pop rbp
    ret

.return_zero:
    xorps xmm0, xmm0         ; Return 0.0 if the array size is 0 or 1
    jmp .finalize