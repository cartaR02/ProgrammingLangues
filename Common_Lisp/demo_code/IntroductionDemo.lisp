;(load "IntroductionDemo.lisp")
(list "horse" (list "cow" () (list "dog" () ()) ) (list "zebra" (list "yak" () ()) () ) )

;Cons Cells
(defvar *fooCell* (cons 2 4))
*fooCell*
(car *fooCell*)
(cdr *fooCell*)

;Lists in Common Lisp
(defvar *list* (list 1 2 3 4))
*list*
(car *list*)
(cdr *list*)
(car (cdr *list*))
(car (cdr (cdr (cdr *list*))))
(cons 0 *list*)

;Constructing a list from cons cells
(cons "abc" (cons 7 (cons (cons 9 (cons 10 () ) ) () ) ) )

;Showing the quote operator 
;(car (2 3 4)) ;Thows exception so commented out by default 
(car '(2 3 4))

;Basic Math
5
(+ 3 3)
(/ 1 9)
(/ (* (+ 2 2) (+ 5 3)))

;Sum of list (Hardcoded indices)
(+ (car *list*) (car (cdr  *list*)) (car (cdr (cdr  *list*))) (car (cdr (cdr (cdr  *list*)))) )

;Mention the print function
(print "Hello! You need me 😅")