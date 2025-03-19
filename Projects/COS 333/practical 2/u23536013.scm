#lang racket
(define (cylinderArea r h)
  (let ((pi (/ 22 7)))
    (if (or(<= r 0)(<= h 0))
        0
        (+(* 2 pi r h)(* 2 pi r r)))))

(define (addPositiveEvenValues list)
  (cond ((null? list)0)
        ((and (even? (car list)) (> (car list) 0))
         (+ (car list) (addPositiveEvenValues(cdr list))))
        (else(addPositiveEvenValues (cdr list)))))

(define (getEveryThirdElement lst)
  (define (helper lst index)
    (cond
      ((null? lst) '()) 
      ((= index 3) 
       (cons (car lst) (helper (cdr lst) 1))) 
      (else
       (helper (cdr lst) (+ index 1)))))
  (helper lst 1))

;; Test cases for Task 1
(display "Task 1: cylinderArea\n")
(cylinderArea 3.2 2.1)  ; Should yield approximately 106.6057142857143
(cylinderArea 0 5)      ; Should yield 0
(cylinderArea 5 0)      ; Should yield 0
(cylinderArea -1 10)    ; Should yield 0

;; Test cases for Task 2
(display "\nTask 2: addPositiveEvenValues\n")
(addPositiveEvenValues '())         ; Should yield 0
(addPositiveEvenValues '(-8 0 7))   ; Should yield 0
(addPositiveEvenValues '(3 4 0 -8 6)) ; Should yield 10
(addPositiveEvenValues '(2 4 6 8))  ; Should yield 20

;; Test cases for Task 3
(display "\nTask 3: getEveryThirdElement\n")
(getEveryThirdElement '())          ; Should yield '()
(getEveryThirdElement '(a b))       ; Should yield '()
(getEveryThirdElement '(a b c d e f g)) ; Should yield '(c f)
(getEveryThirdElement '(1 2 3 4 5 6 7 8 9 10)) ; Should yield '(3 6 9)