(defun map- (f L)
  (if (null L) (RETURN-FROM map- (break)))
  (cons (funcall f (car L)) (map- f (cdr L)))
)

;tail recursive
(defun map-tail (f L &optional L2)
  (if (null L) (RETURN-FROM map-tail (reverse L2)))
  (map-tail f (cdr L) (cons (funcall f (car L)) L2 ))
)

(defun square (x) (* x x))
(defun square-list (L) (map-tail #'square L))

(square-list '(3 4 5)) ;prints '(9 16 25)


;example problem
(defun isString (x)
	(typep x 'String)
)

(defun myExample (f L &optional L2)
	(if (null L) (RETURN-FROM myExample (reverse L2)))
	(if (funcall f (car L))
		(myExample f (cdr L) (cons (concatenate 'String "OR" (car L)) L2))
		(myExample f (cdr L) L2)
	)
)