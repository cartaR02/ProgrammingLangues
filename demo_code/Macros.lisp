(defmacro when- (condition &rest body) 
 	`(if ,condition (progn ,@body))
)

(defmacro test-exp (form)
  `(format t "~&~S => ~S~%" ',form ,form))


(test-exp (+ 3 5))

(when- (= 3 3) (print "Hello World"))


(when- (= 3 3)
	(let ((a 3) (b 4))
		(+ a b)
		(- a b)
		(/ a b)
	)
)

(macroexpand `(test-exp (+ 2 3)))


(macroexpand ` (when- (= 3 3)
	(let ((a 3) (b 4))
		(+ a b)
		(- a b)
		(/ a b)
	)
)
)
