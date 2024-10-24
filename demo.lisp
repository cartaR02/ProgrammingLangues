(defun addList (inList)
    (if (null inList)
    0
    (+ (car inList) (addList (cdr inList)))
    )
)
(addList '(5 4 3 2))    

(defun square (n) (* n n))
(print (square 9))