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