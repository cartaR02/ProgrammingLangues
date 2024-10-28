;is even
(print "Enter a number")
(if 
 (= 0 (mod (parse-integer (read-line)) 2 ))
 (print "is even")	;true branch
 (print "is odd")		;false branch
)

;larger string
(print "Enter two strings to be compared")
(if 
  ;conditional
  (STRING> (read-line) (read-line)) 
  ;true branch
  (print "Second is lexicographically larger")
  ;false branch
  (print "First is lexicographically larger")
)

(print "Enter two numbers to be compared")
(if 
  (> (parse-integer (read-line)) (parse-integer (read-line)))				;conditional
  (print "First is larger") 	;true branch
  (print "Second is larger")	;false branch
)


(defvar *input* (read-char))
(cond 
  ((CHAR-EQUAL #\M *input*) (print #\M))
  ((CHAR-EQUAL #\S *input*) (print #\S))
  ((CHAR-EQUAL #\I *input*) (print #\I))
  (T (print "Error, incorrect char input")) 
) 
