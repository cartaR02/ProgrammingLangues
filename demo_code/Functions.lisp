(defun isEven (aNumber) "Checks if a given number is even" 
  (if 
   (= 0 (mod aNumber 2 ))
   T			;true branch
   NIL		;false branch
  )
)

(defun stringComp (&key (a "") (b "") (toConcat "" toConcat-supplied-p) ) "Compares two Strings and first one alphabetically" 
  (if 
    ;conditional
    (STRING> a b) 
    ;true branch
    (if 
      ;conditional
    	(not (null toConcat-supplied-p))
      ;true branch
    	(concatenate 'string b toConcat)
      ;false branch
    	b
    )
    ;false branch
    (if 
      ;conditional
    	(not (null toConcat-supplied-p))
      ;true branch
    	(concatenate 'string a toConcat)
      ;false branch
    	a
    )
  )
)


(defun realComp (a b &optional offset) "Compares two real numbers and returns the larger one" 
  (cond
  	((and (> a b) (not (null offset))) (+ a offset) )
  	((and (> a b) (null offset)) 	      a         )
  	((and (< a b) (not (null offset))) (+ b offset) )
  	((and (< a b) (null offset))          b         )
  )
)


(defvar *input* (read-char))
(cond 
  ((CHAR-EQUAL #\E *input*) 
    (isEven 3)
  )
  ((CHAR-EQUAL #\S *input*) 
    (stringComp :a "Hello" :b "World")
  )
  ((CHAR-EQUAL #\R *input*) 
    (realComp 2/3 3/2)
  )
  (T (print "Error, incorrect char input")) 
) 