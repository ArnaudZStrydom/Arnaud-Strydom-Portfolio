; ==========================
; Group member 01: Name_Surname_student-nr
; Group member 02: Name_Surname_student-nr
; Group member 03: Name_Surname_student-nr
; ==========================

extern malloc
extern strncpy
section .text
    global createStudent

createStudent:
    ; Function to create a new Student struct
    ; Parameters:
    ;   rdi - id (32-bit integer)
    ;   rsi - name (pointer to string)
    ;   xmm0 - gpa (32-bit float)

    ; Preserve non-volatile registers
    push rbp
    mov rbp, rsp
    push rbx
    push r12
    push r13
    push r14

    ; Save parameters
    mov r12d, edi  ; id
    mov r13, rsi   ; name pointer
    movss [rsp - 4], xmm0  ; gpa (temporarily store on stack)

    ; Allocate memory for the struct (4 + 64 + 4 = 72 bytes)
    mov rdi, 72
    call malloc
    test rax, rax
    jz .allocation_failed

    ; rax now contains the pointer to the allocated memory
    mov rbx, rax  ; Save the pointer in rbx

    ; Set the id (32-bit integer)
    mov [rbx], r12d

    ; Copy the name (using strncpy for safety)
    lea rdi, [rbx + 4]  ; Destination: struct + 4 bytes (after id)
    mov rsi, r13        ; Source: name pointer
    mov rdx, 63         ; Max length (63 to leave room for null terminator)
    call strncpy
    mov byte [rbx + 67], 0  ; Ensure null termination

    ; Set the gpa (32-bit float)
    movss xmm0, [rsp - 4]  ; Retrieve gpa from stack
    movss [rbx + 68], xmm0

    ; Return the pointer to the struct
    mov rax, rbx

    ; Restore non-volatile registers and return
    pop r14
    pop r13
    pop r12
    pop rbx
    pop rbp
    ret

.allocation_failed:
    ; Handle allocation failure
    xor rax, rax  ; Return NULL
    pop r14
    pop r13
    pop r12
    pop rbx
    pop rbp
    ret