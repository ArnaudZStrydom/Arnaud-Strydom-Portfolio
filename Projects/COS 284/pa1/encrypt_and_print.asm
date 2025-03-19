; ==========================
; Group member 01: Arnaud_Strydom_u23536013 
; Group member 02: Zainab_abdulrasaq_u22566202
; Group member 03: Rudolph_Lamprecht_u20598425
; ==========================

section .data
    fmt db "%u ", 0  ; Format string for printing unsigned integers
    prompt_encrypt db "Enter plaintext to encrypt: ", 0
    cipher_text_label db "The cipher text is: ", 0
    fmt_string db "%s", 0
    fmt_char db "%c", 0
    input_buffer times 5 db 0  ; Buffer for 4 characters + null terminator

; ==========================
; Your data goes here
xor_constant dd 0x73113777
; ==========================

section .text
global encrypt_and_print
extern printf, scanf, getchar

print_uint_32:
    push rbp
    mov rbp, rsp
    and rsp, -16  ; Align stack
    mov rsi, rax
    mov rdi, fmt
    xor eax, eax
    call printf
    leave
    ret

encrypt_and_print:
    ; ==========================
    ; Your code goes here
    
    ; Preserve non-volatile registers
    push rbx
    push r12
    push r13
    
    ; Print prompt
    mov rdi, fmt_string
    mov rsi, prompt_encrypt
    xor eax, eax
    call printf
    
    ; Read input
    mov rdi, fmt_string
    mov rsi, input_buffer
    xor eax, eax
    call scanf

    ; Clear input buffer
.clear_buffer:
    call getchar
    cmp al, 10  ; Check for newline
    jne .clear_buffer

    ; Print cipher text label
    mov rdi, fmt_string
    mov rsi, cipher_text_label
    xor eax, eax
    call printf

    ; Encrypt and print terminal input
    mov rbx, input_buffer

.encrypt_loop:
    ; Load character
    movzx eax, byte [rbx]
    
    ; Check for end of string or newline character
    test al, al
    jz .end
    cmp al, 10  ; Check if the character is a newline
    je .end
    
    ; Rotate left by 4 bits
    rol eax, 4
    
    ; XOR with constant
    xor eax, [xor_constant]
    
    ; Print encrypted character
    call print_uint_32
    
    ; Move to next character
    inc rbx
    jmp .encrypt_loop

.end:
    
    ; Restore non-volatile registers
    pop r13
    pop r12
    pop rbx
    ; ==========================

    ret