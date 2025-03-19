; ==========================
; Group member 01: Zainab_Abdulrasaq_22566202
; Group member 02: Rudolph_Lamrechet_20598425
; Group member 03: Arnaud_Strydom_23536013
; ==========================
; convert_string_to_float.asm

section .text
    global convertStringToFloat

convertStringToFloat:
    push rbp
    mov rbp, rsp
    push rbx
    push r12
    push r13

    xorps xmm0, xmm0            ; Clear xmm0 to hold the final result (float)
    mov r12, rdi                ; r12 holds the input string pointer
    xor r13, r13                ; r13 will be used for sign (0 for positive, 1 for negative)
    xor rax, rax                ; Clear rax for use in integer conversion
    xor rbx, rbx                ; Clear rbx for use in fractional conversion
    xor rcx, rcx                ; Clear rcx to count the number of fractional digits

    ; Skip leading whitespace
.skip_whitespace:
    movzx edx, byte [r12]
    cmp dl, ' '
    jne .check_sign
    inc r12
    jmp .skip_whitespace

.check_sign:
    cmp byte [r12], '-'
    jne .process_integer
    mov r13, 1                  ; Set negative flag
    inc r12

.process_integer:
    movzx edx, byte [r12]
    sub dl, '0'
    jl .check_decimal
    cmp dl, 9
    jg .check_decimal

    imul rax, rax, 10
    add rax, rdx
    inc r12
    jmp .process_integer

.check_decimal:
    cmp byte [r12], '.'
    jne .finalize
    inc r12

.process_fraction:
    movzx edx, byte [r12]
    sub dl, '0'
    jl .finalize
    cmp dl, 9
    jg .finalize

    imul rbx, rbx, 10
    add rbx, rdx
    inc rcx                      ; Increment fractional digit counter
    inc r12
    jmp .process_fraction

.finalize:
    ; Convert integer part to float
    cvtsi2ss xmm0, rax

    ; Add fractional part if exists
    test rbx, rbx
    jz .apply_sign
    cvtsi2ss xmm1, rbx
    ; Normalize by dividing by 10^n
    mov rax, 1
    mov rdx, rcx
.normalize_fraction:
    imul rax, rax, 10
    dec rdx
    jnz .normalize_fraction
    cvtsi2ss xmm2, rax
    divss xmm1, xmm2
    addss xmm0, xmm1

.apply_sign:
    ; Apply sign if negative
    test r13, r13
    jz .done
    movss xmm1, [rel minus_one]
    mulss xmm0, xmm1

.done:
    pop r13
    pop r12
    pop rbx
    mov rsp, rbp
    pop rbp
    ret

section .data
    minus_one dd -1.0