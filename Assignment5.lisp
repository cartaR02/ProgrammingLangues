; Carter Struck 
; Assignment 5 
; Common Lisp

(defun myList ()
    (list 4 (list 7 22) "art" (list "math" (list 8) 99) 100)
)
;(4 (7 22) "art" ("math" (8) 99) 100)

(myList)


(defun leapYear (&optional (currentYearNum 1800) (leapYearList '()))

    (if (> currentYearNum 2024) ;condition
       leapYearList                        ; if
        (let ((newList                 ;else ; creating let to make newList surrounding if
             (if (and (= (mod currentYearNum 4) 0) ; checking for mod 4
                (or (zerop (mod currentYearNum 400))
                    (not (zerop (mod currentYearNum 100))))
                    ); ands mod 4 with (mod 400 or not mod 100)
                (append leapYearList (list currentYearNum)) ; add to list
                leapYearList; else block for if 
                ); if and
             ); new list
            ) ; or keep unchagned
        (leapYear (+ currentYearNum 1) newList) ; calls function again with new list
        ) ; let
    ) ; if currentYear 2024
) ; defun leapYear


; helper function that looks at a spot (number or letter)
; and checks it against a list
(defun contains (spot list)
    (cond 
        ((null list) nil); null list returns nil base case
        ((equal spot (car list)) t) ;return true if equal to spot in list
        (t (contains spot (cdr list))))); defun contains

(defun union- (list1 list2); idea to take arbitary index i of list 1 and compare to list 2
    (cond
        ((null list1) list2) ; emtpy list 1 return list 2
        ((contains (car list1) list2); if true then call its self with next part
        (union- (cdr list1) list2)) ; if false then skip iterating and add it to a new list
        (t (cons (car list1) (union- (cdr list1) list2))) ;add the item to result
    );cond

); defun union


; check if the list is non then check if the count is 0
; 0 count means emmpty list > 0 count is a completely iterated list
(defun avg (aList &optional(sum 0) (count 0))
    (if (null aList)
        (if (= count 0) ; count 0 then return 0 as base
            NIL ;if
            (/ sum count)) ; else 
        (avg (cdr aList) (+ sum (car aList)) (+ count 1)) ; adds the current part in list and goes into next part of list count++
    ) ; else for null aList
); defun avg


; isType returns type of function
(defun isType (dataType)
    (lambda (uData)
    (typep uData dataType)) ; checks type from value against type using lambda

); defun isType


(defun taxCalculator (limit rate values)
(if (null values)
nil
    (cons(if (< (car values) limit) ; cons list adding value if correct or changing if wrong then iterating
        (car values)
        (* rate (car values)))
        (taxCalculator limit rate (cdr values))
    )
)
    
    
); defun taxCalculator


(defun clean (aFunc aList)
 (cond
 ((null aList) nil)

 ((listp (car aList)); check list on slot in list
    (cons (clean aFunc (car aList)) (clean aFunc (cdr aList))) ; go down each path to get to next one
 )

 ((funcall aFunc (car aList)) ; if true in function then add actual value and then call next one
    (cons (car aList) (clean aFunc (cdr aList)))
 )

 (t (clean aFunc (cdr aList))) ; if not valid then just call self again with next one in list
 ); cond
); defun clean


(defmacro threeWayBranch (x y z)
`(if ,(car x) ;check if true first branch
    (progn ,@(cdr x)) ;eval if true other wise go to next
    (if ,(car y)
        (progn ,@(cdr y))
        (if ,(car z)
            (progn ,@(cdr z))
            nil
        )
    )
)
); defmacro threeway branch