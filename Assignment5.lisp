(defun myList ()
    (list 4 (list 7 22) "art" (list "math" (list 8) 99) 100)
    )
;(4 (7 22) "art" ("math" (8) 99) 100)

(myList)

(defun leapYear (&optional (currentYearNum 1800) (leapYearList '()))

    (when (<= currentYearNum 2024) ; base case check
        (if (= (mod currentYearNum 4) 0) ; checkign for year to mod 0
            (if (mod currentYearNum 100) 
                ;(format t "~A is a leap year~%" currentYearNum) 
                (car  ; first condition of not 100 
                (if (not (zerop(mod currentyearnum 400))) 
                    (push currentyearnum leapYearList)) ; then if mod 400 
            ); when
        ) ; if mod
        (leapYear (+ currentyearnum 1) (cdr leapYearList))  ; calls again within when < 2024)
    leapYearList) ; when / return the list
); leapYear
;(leapYear)

