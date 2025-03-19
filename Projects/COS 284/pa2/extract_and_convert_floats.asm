section .data
    format db "Enter values separated by whitespace and enclosed in pipes (|):", 0
    input_buffer resb 1024   ; Reserve space for user input (1024 bytes)

section .bss
    float_array resb 1024    ; Reserve space for up to 256 floats (256 * 4 bytes = 1024 bytes)

section .text
global extractAndConvertFloats
extern convertStringToFloat
extern printf, scanf

extractAndConvertFloats:
    ; Function prologue
    push rbp
    mov rdi, rsi                ; rdi = num_floats (int*)
    mov rsi, rdi                ; rsi = num_floats
    mov r12, rdi                ; r12 = input string (char*)
    xor r14, r14                ; r14 = float count
    xor r15, r15                ; r15 = index in input string

    ; Print prompt message
    mov rdi, format             ; rdi = address of format string
    call printf

    ; Read user input
    lea rdi, [input_buffer]    ; rdi = address of input buffer
    mov rsi, rdi               ; rsi = buffer for scanf
    mov rdx, 1024              ; rdx = maximum number of bytes to read
    call scanf                 ; scanf("%s", input_buffer)

    ; Count number of floats
.count_floats:
    movzx eax, byte [r12 + r15]
    test al, al
    jz .allocate_memory
    cmp al, '|'
    je .skip_char
    cmp al, ' '
    je .skip_char
    inc r14                     ; Increment float count
.skip_number:
    inc r15
    movzx eax, byte [r12 + r15]
    test al, al
    jz .allocate_memory
    cmp al, ' '
    jne .skip_number
.skip_char:
    inc r15
    jmp .count_floats

.allocate_memory:
    ; Store float count in the provided pointer
    mov [rdi], r14d

    ; Initialize registers for conversion
    mov rbx, float_array       ; rbx = start of float array
    xor r14, r14                ; Reset float index
    xor r15, r15                ; Reset string index

.convert_floats:
    movzx eax, byte [r12 + r15]
    test al, al
    jz .done
    cmp al, '|'
    je .next_char
    cmp al, ' '
    je .next_char

    lea rdi, [r12 + r15]
    call convertStringToFloat
    movss [rbx + r14*4], xmm0
    inc r14

.skip:
    inc r15
    movzx eax, byte [r12 + r15]
    test al, al
    jz .done
    cmp al, ' '
    jne .skip

.next_char:
    inc r15
    jmp .convert_floats

.done:
    ; Function epilogue
    pop rbp
    ret

