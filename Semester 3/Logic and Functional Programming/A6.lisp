

#|
                      l, if l is a number and l1 % 2 == 0
findSum(l1l2...ln) = -l, if l is a number and l1 % 2 == 1
                      0, if l is an atom
                      findSum(l1) + findSum(l2) + .... + findSum(ln), if l is a list
|#


(defun findSum (l)
    (cond
        (
            (and
                (numberp l)
                (equal 0 (mod l 2))
            )
            l
        )
        (
            (and
                (numberp l)
                (equal 1 (mod l 2))
            )
            (- l)
        )
        (
            (atom l)
            0
        )
        (
            t
            (apply '+ (mapcar #'findSum l))
        )
    )

)

(defun testFindSum ()
    (assert
        (and
            (equal (findSum '(1 5 9)) '-15)
            (equal (findSum '(2 4 6)) '12)
            (equal (findSum '(1 2 3 4 5 6 7 8 9 10)) (- 30 25))
            (equal (findSum '(1 2 3 (4 5) a b c (6 7 (8 9)) 10)) (- 30 25))
        )
    )
)

(defun findProduct (l)
    (cond
        (
            (and
                (numberp l)
                (equal 0 (mod l 2))
            )
            1
        )
        (
            (and
                (numberp l)
                (equal 1 (mod l 2))
            )
            -1
        )
        (
            (atom l)
            0
        )
        (
            t
            (apply '* (mapcar #'findProduct l))
        )
    )
)

(defun main (l)
    (cons (findSum l) (findProduct l))
)

(defun testMain ()
    (assert
        (and
            (equal (main '(1 2 3 4)) '(2 . 1))
            (equal (main '(1 2 3 5)) '(-7 . -1))
            (equal (main '(2 2 a b 2 2 )) '(8 . 0))
            (equal (main '(1 2 a a a b c (5 10) 3 4)) '(7 . 0))
        )
    )
)



