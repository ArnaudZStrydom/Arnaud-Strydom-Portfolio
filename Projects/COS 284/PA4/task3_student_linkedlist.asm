; ==========================
; Group member 01: Arnaud_Strydom_23536013
; Group member 02: Name_Surname_student-nr
; Group member 03: Name_Surname_student-nr
; ==========================

extern malloc
extern strncpy

section .text
    global addStudentToList

addStudentToList:
    ; Function to add a new StudentNode to a linked list
    ; Inputs:
    ;   rdi - pointer to the pointer of the head of the linked list (StudentNode**)
    ;   rsi - name of the new student (string)
    ;   xmm0 - GPA of the new student (float)

    push rbp
    mov rbp, rsp
    push rbx
    push r12
    push r13
    push r14
    push r15

    ; Validate head pointer
    test rdi, rdi
    jz .mem_error  ; If head pointer is null, error

    mov r12, [rdi]  ; Save head pointer
    mov r13, rsi    ; Save name pointer
    movss [rsp], xmm0  ; Save GPA on stack

    ; Allocate memory for new StudentNode (8 bytes for Student*, 8 bytes for next*)
    mov rdi, 16
    call malloc
    test rax, rax
    jz .mem_error
    mov r14, rax    ; r14 holds new StudentNode

    ; Allocate memory for new Student (72 bytes: 4 for id, 64 for name, 4 for GPA)
    mov rdi, 72
    call malloc
    test rax, rax
    jz .mem_error
    mov [r14], rax  ; Store Student pointer in StudentNode

    ; Find highest id and last node
    mov r15d, 0     ; Highest id
    mov rbx, r12    ; Current node
.find_highest_id:
    test rbx, rbx
    jz .set_new_student  ; If we've reached the end, set up new student

    mov rax, [rbx]   ; Load current StudentNode pointer
    mov eax, [rax]   ; Load current student's id
    cmp eax, r15d
    cmovg r15d, eax  ; Update highest id if current id is greater

    mov rax, [rbx + 8]  ; Load next pointer
    test rax, rax
    jz .set_new_student  ; If next is null, we've found the last node

    mov rbx, rax    ; Move to next node
    jmp .find_highest_id

.set_new_student:
    ; Set new student's id
    inc r15d
    mov rax, [r14]  ; Load Student pointer
    mov [rax], r15d  ; Set id

    ; Copy name (up to 63 characters)
    mov rdi, rax
    add rdi, 4      ; Destination: name field of new student
    mov rsi, r13    ; Source: input name
    mov rdx, 63     ; Max length
    call strncpy
    mov rax, [r14]
    mov byte [rax + 67], 0  ; Null-terminate the name

    ; Set GPA (float value, offset 68 bytes into the struct)
    mov rax, [r14]  ; Load the pointer to the new Student struct
    movss xmm0, [rsp]  ; Load GPA back from the stack into xmm0
    movss [rax + 68], xmm0  ; Store the GPA in the struct (68-byte offset)

    ; Link new node to list
    mov qword [r14 + 8], 0  ; Set next to null
    test rbx, rbx
    jz .set_head    ; If list was empty, set as head
    mov [rbx + 8], r14  ; Otherwise, link to end of list
    jmp .end

.set_head:
    mov [rdi], r14  ; Update the head pointer

.end:
    mov rax, r14    ; Return pointer to new node
    pop r15
    pop r14
    pop r13
    pop r12
    pop rbx
    pop rbp
    ret

.mem_error:
    xor rax, rax    ; Return null on memory error
    pop r15
    pop r14
    pop r13
    pop r12
    pop rbx
    pop rbp
    ret

