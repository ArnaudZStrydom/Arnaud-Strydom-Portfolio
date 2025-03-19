; ==========================
; Group member 01: Arnaud_Strydom_u23536013 
; Group member 02: Zainab_abdulrasaq_u22566202
; Group member 03: Rudolph_Lamprecht_u20598425
; ==========================

section .data
    ; ==========================
    ; Your data goes here
    ; ==========================
    ;define greeting message 
    greeting_msg db "Welcome agent. What do you want to do, Encrypt [1] or Decrypt [2]?"
    ;getting the length of greeting message
    greeting_len equ $ - greeting_msg

section .text
    global greeting

greeting:
    ; Do not modify anything above this line unless you know what you are doing
    ; ==========================
    ; Your code goes here
    ; ==========================
    ; Do not modify anything below this line unless you know what you are doing
    
    ;base pointer

    push rbp
    mov rbp, rsp

    ;printing greeting
    mov rax, 1
    mov rdi, 1
    mov rsi, greeting_msg
    mov rdx, greeting_len
    syscall

    ;restoring stack
    
    mov rsp, rbp
    pop rbp
    ret                         ; return from function
