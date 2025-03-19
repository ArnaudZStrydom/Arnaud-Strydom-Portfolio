; ==========================
; Group member 01: Arnaud_Strydom_u23536013 
; Group member 02: Zainab_abdulrasaq_u22566202
; Group member 03: Rudolph_Lamprecht_u20598425
; ==========================

section .data
fmt db "%c", 0  ; Format string for printing characters
plain_text_label db "The plaintext is: ", 0
plain_text_len equ $ - plain_text_label
; ==========================
; Your data goes here
xor_constant dd 0x73113777
; ==========================

section .text
global decrypt_and_print
extern printf

print_char:
    push rbp
    mov rbp, rsp
    and rsp, -16  ; Align stack
    mov rsi, rax
    mov rdi, fmt
    xor eax, eax
    call printf
    leave
    ret

decrypt_and_print:
    ; ==========================
    ; Your code goes here
    


    ; Preserve non-volatile registers
    push rbx
    push r12
    push r13
    push r14
    
    ; Store the four input numbers in registers
    mov r12d, edi  ; First number
    mov r13d, esi  ; Second number
    mov r14d, edx  ; Third number
    mov ebx, ecx   ; Fourth number

    mov rax, 1
    mov rdi, 1
    mov rsi, plain_text_label
    mov rdx, plain_text_len
    syscall

    ; Decrypt and print first number
    mov eax, r12d
    call decrypt_char

    ; Decrypt and print second number
    mov eax, r13d
    call decrypt_char

    ; Decrypt and print third number
    mov eax, r14d
    call decrypt_char

    ; Decrypt and print fourth number
    mov eax, ebx
    call decrypt_char

    
    ; Restore non-volatile registers
    pop r14
    pop r13
    pop r12
    pop rbx
    ; ==========================

    ret

decrypt_char:
    ; XOR with constant (this is its own inverse operation)
    xor eax, [xor_constant]
    
    ; Rotate right by 4 bits (to undo the left rotation)
    ror eax, 4
    
    ; Print decrypted character
    call print_char
    
    ret

