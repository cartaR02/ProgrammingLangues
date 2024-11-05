(let( (MyList  '("a" "b" ("c" "d"))) )
	 (print MyList)
)

(let ((a 2) (b 3))  
  (print (+ 1 b))  
  (print (+ 6 a))
)	

(let ((circleArea (lambda (r) (* 3.14 (* r r)))))   
 (funcall circleArea 10) ;returns 314.0
)

;(circleArea 10) ;error, circleArea not defined


(let* ((x 10) (y (+ x 10)))  
    (list x y)
)

(defun sumUserInput () 
	(let (
	  ;Could replace these read lines with function calls
	  (x (parse-integer (read-line))) 
	  (y (parse-integer (read-line))) 
	 )
	(print (+ 0 y))
	)
)


;Scoping 
(let ((a 2) (b 3))
	(let ((a (+ a b)))
		(+ a b)
	)
)

(let ((pi- 3.14)) 
	(let ((circleArea (lambda (r) (* pi- (* r r)))))  
		(let ((p 3.1416)) 
			(funcall circleArea 10)
		)
	)
)


(let* ((a "a") (add-a 
  (lambda (x) 
  	(if (and (numberp a) (numberp x)) 
  	(+ x a) 
  	(format t "add-a: x or a is not a number x:~S a:~S" x a) 
  	) 
  )
  ))
  (funcall add-a 5)
)

;Closures

; (defvar x 5)					; x is a free variable in 	
; (defun addx (a) (+ a x))      ; function addx
; (addx 3)     					; result: 8
; (let ((x 4)) (addx 3))		; result: 7
; (let ((x 4)) (+ x 3))			; result: 7

(let ((a 1))
	(let ((f (lambda (x) (+ a x))))    
		(let ((a 2))       
			(funcall f 2)
		)
	)
)

(defvar a 5)
(defun f (x) (+ a x))
(f 2)					 ;returns 7  
(let ((a 1))  
	(print (f 2)) 		 ;prints 3     
	(let ((a 2))            
		(print (f 2))	 ;prints 4
	)
)	 



(defvar x 1)
(defun f (y) 
	(let ((x (+ y 1)))
		(lambda (z) (+ x y z)))
)
(defvar z 
	(let ((x 3) (g (f 4)) (y 5)) 
		(funcall g 6)
	)
)


(defun f (g) 
	(let ((x 3)) 
		(funcall g 2)  ;replace 2 with x and the value of x
						;is bound to the value of x in the let block
	)
)
(defvar x 4)
(defun h (y) (+ x y))
(defvar z (f #'h))
z 		;prints 6


;Currying

(defun f (a b) (+ a b))
(defun f1 (a) (lambda (b) (f a b)))
(f 2 3)				;returns 5
(funcall (f1 2) 3)	;returns 5




(defun quadratic (a b c x) (+ (* a x x) (* b x) c))
(quadratic 1 2 3 4) ;27

(defun q3 (a b c) (lambda (x) (quadratic a b c x)))
(defun q2 (a b) (lambda (c) (q3 a b c)))
(defun q1 (a) (lambda (b) (q2 a b)))
(funcall(funcall(funcall(q1 1) 2) 3) 4) ;27


;Partial application


