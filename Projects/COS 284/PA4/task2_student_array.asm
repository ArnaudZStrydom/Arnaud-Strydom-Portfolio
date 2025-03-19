; ==========================
; Group member 01: Name_Surname_student-nr
; Group member 02: Name_Surname_student-nr
; Group member 03: Name_Surname_student-nr
; ==========================

section .text
    extern malloc
    extern strncpy

    global addStudent

addStudent:
    ; Inputs:
    ;   rdi - pointer to the array of Student structs
    ;   esi - maximum size of the array
    ;   rdx - name of the new student (string)
    ;   xmm0 - GPA of the new student (float)

    push rbp
    mov rbp, rsp
    sub rsp, 16          ; Allocate 16 bytes of stack space for alignment and GPA storage
    push rbx
    push r12
    push r13
    push r14
    push r15

    ; Save inputs
    mov r12, rdi         ; r12 = pointer to the student array
    mov r13d, esi        ; r13 = max size of the array
    mov r14, rdx         ; r14 = pointer to the student name
    movss [rsp], xmm0    ; Save GPA on the stack (aligned to 16 bytes)

    ; Initialize variables
    xor r15d, r15d       ; r15d = highest ID (starts at 0)
    xor ecx, ecx         ; ecx = student count

    ; Loop through the array to find the highest ID and empty slot
.find_highest_id:
    cmp ecx, r13d        ; If we've checked all slots, break
    jge .add_new_student

    mov eax, [r12]       ; eax = current student's id
    test eax, eax        ; If id is 0, we've found an empty slot
    jz .add_new_student

    cmp eax, r15d
    cmovg r15d, eax      ; Update highest ID if current student's id is greater

    inc ecx              ; Increment student count
    add r12, 72          ; Move to next student struct (72 bytes per struct)
    jmp .find_highest_id

.add_new_student:
    ; Check if array is full
    cmp ecx, r13d
    jge .array_full

    ; Calculate new student's ID
    inc r15d             ; New ID is highest ID + 1

    ; Add new student to the array
    mov [r12], r15d      ; Set the student's ID

    ; Copy the name (up to 63 characters) and null-terminate
    lea rdi, [r12 + 4]   ; Destination: name field (offset 4 bytes after id)
    mov rsi, r14         ; Source: input name
    mov rdx, 63          ; Copy up to 63 characters
    call strncpy
    mov byte [r12 + 67], 0  ; Null-terminate the string (at position 67)

    ; Set GPA (float value, offset 68 bytes into the struct)
    movss xmm0, [rsp]    ; Load GPA back from the stack into xmm0
    movss [r12 + 68], xmm0  ; Store the GPA in the struct (68-byte offset)

    ; Return success
    mov eax, 1
    jmp .end

.array_full:
    ; Array is full, return failure (0)
    xor eax, eax

.end:
    add rsp, 16          ; Restore the stack space
    pop r15
    pop r14
    pop r13
    pop r12
    pop rbx
    pop rbp
    ret