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


;(defun union- (testingList '())

;);defun union
(defparameter *myList* (list 5 4 3 2 1))

(defparameter *sorted_list* (sort (copy-list *myList*) #'<))

; check if the list is non then check if the count is 0
; 0 count means emmpty list > 0 count is a completely iterated list
(defun avg (aList &optional(sum 0) (count 0))
    (if (null aList)
        (if (= count 0) ; count 0 then return 0 as base
            NIL ;if
            (/ sum count)) ; else 
        (avg (cdr aList) (+ sum (car aList)) (+ count 1)) ; adds the current part in list and goes into next part of list count++
    ) ; else for null aList
)