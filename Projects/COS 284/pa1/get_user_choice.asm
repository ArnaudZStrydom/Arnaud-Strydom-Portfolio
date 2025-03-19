; ==========================
; Group member 01: Arnaud_Strydom_u23536013 
; Group member 02: Zainab_abdulrasaq_u22566202
; Group member 03: Rudolph_Lamprecht_u20598425
; ==========================

section .bss
    user_choice resb 2  ; Reserve 2 bytes: 1 for the digit, 1 for the newline

section .data
    prompt db "Choice: ",0
    prompt_len equ $ - prompt
    newline db 10       ; Newline character

section .text
    global get_user_choice

extern greeting

get_user_choice:
    ; Call the greeting function to print the welcome message
    call greeting

            ; Print a newline after the user enters their choice
    mov rax, 1          ; syscall number for sys_write
    mov rdi, 1          ; file descriptor 1 is stdout
    mov rsi, newline    ; address of the newline character
    mov rdx, 1          ; number of bytes to write (1 byte for newline)
    syscall

    ; Print the prompt message
    mov rax, 1          ; syscall number for sys_write
    mov rdi, 1          ; file descriptor 1 is stdout
    mov rsi, prompt     ; address of the prompt string
    mov rdx, prompt_len ; length of the prompt string
    syscall

    ; Read the user input (2 bytes)
    mov rax, 0          ; syscall number for sys_read
    mov rdi, 0          ; file descriptor 0 is stdin
    mov rsi, user_choice ; address to store the user input
    mov rdx, 2          ; number of bytes to read (digit + newline)
    syscall

    ; Convert ASCII character to integer by subtracting 48
    movzx rax, byte [user_choice]  ; Load the first byte (the digit) into rax
    sub rax, 48                    ; Convert ASCII to integer (e.g., '1' -> 1)

    ; Now rax contains the numeric value of the user input
    ; Return value (rax) is the user's numeric choice
    ret