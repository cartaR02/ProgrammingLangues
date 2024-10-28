;break
;backtrace

(defun square-list (L)
  (if 
  	;condition
  	(null L) 
  	;true branch (base case)
	()
  	;false branch (recursive case)
    (cons (* (car L) (car L)) (square-list(cdr L)))
  )
)

;
;(3 * (2 * (1)))

;not tail recursive
(defun factorial (n)
  (if 
  	(= n 0)
    1
    (* n (factorial(- n 1)))
  )
)

;(3*1)*2 
(factorialTail 3)
;tail recursive
(defun factorialTail (n &optional (result 1))
   (if 
   	(= n 1)
    result
    (factorialTail (- n 1) (* n result))
   )
)

(defun addList (inlist)
  (if 
  	(null inlist)
    0
    (+ (car inlist) (addList (cdr inlist)))
  )
)
;;(5 4 3 2 1)
;(+ 5 (addlist '(4 3 2 1) ))
;(+ 4 (addlist '(3 2 1)   ))
;(+ 3 (addlist '(2 1)     ))
;(+ 2 (addlist '(1)       ))
;(+ 1 (addlist ()         ))
;0

;(+ 1 0)
;(+ 2 1)
;(+ 3 3)
;(+ 4 6)
;(+ 5 10)
;15




(defun addListTail (inlist &optional (result 0))
  (if 
  	(null inlist)
    result   
    (addListTail (cdr inlist) (+ (car inlist) result)) 
  )
)

;(5 4 3 2 1)
;(addListTail '() 15)

(defun myListLength (inlist)
  (if (null inlist)
    0
    (+ 1 (myListLength (cdr inlist)))
  )
)

(defun listLengthTail (inlist &optional (result 0))
  (if 
  	(null inlist)
    result
    (listLengthTail (cdr inlist) (+ 1 result)) 
  )
)

(defun print-squares-cond (low high)
    (cond 
    	;if branch
    	((> low high) NIL)
    	;else branch
    	(T (print (* low low)) (print-squares-cond (+ 1 low) high ))
	)
)

(defun print-squares-if (low high)
	;if statement with no else
    (if (> low high) (RETURN-FROM print-squares-if (break)))
    ;statements to execute
    (print (* low low))
    (print-squares-if (+ 1 low) high)
)


(defun power (a b &optional (c 1))
  (if (= b 0)
    c
    (power a (- b 1) (* c a))
  )
)

(defun makeList (n)
  (if (= 0 n)
    ()
    (cons n (makeList (- n 1)))
  )
)

(defun makeListTail (n &optional aList)
  (if (= 0 n)
    aList
    (makeListTail (- n 1) (append aList (list n) ) )
  )
)
