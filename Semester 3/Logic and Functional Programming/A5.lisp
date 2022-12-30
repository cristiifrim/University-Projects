#|
    a)
 |#


#|          
                            [], if [] and n > 0
    ins(l1l2...ln, e, n) =  {e}, if [] and n = 0  
                            {e} U l1l2..ln, if n = 0
                            {l1} U ins(l2..ln, e, n - 1), otherwise

|#

 (defun ins (l e n)
    (cond
        ((and (null l) (> n 0)) nil)
        ((and (null l) (= n 0)) (cons e l))
        ((= n 0) (cons e l))
        (t (cons (car l) (ins (cdr l) e (- n 1))))
    )
 )

 (defun testIns ()
    (assert
        (and
            (equal (ins '(1 2 3) '4 '3) '(1 2 3 4))
            (equal (ins '(1 2 3) '4 '2) '(1 2 4 3))
            (equal (ins '(1 2 3) '4 '0) '(4 1 2 3))
        )
    )
 )

 #|
                            [], if [] and n >= 0
    repl(l1l2..ln, e, n) =  {e}, if n = 0
                            l1 U ins(l2...ln, e, n - 1) otherwise

|#

(defun repl (l e n)
    (cond
        ((and (null l) (> n 0)) nil)
        ((and (null l) (= n 0)) nil)
        ((= n 0) (cons e (cdr l)))
        (t (cons (car l) (repl (cdr l) e (- n 1))))
    )
)

(defun testRepl()
    (assert
        (and
            (equal (repl '(1 2 3) 5 2) '(1 2 5))
            (equal (repl '(1 2 3) 5 1) '(1 5 3))
            (equal (repl '(1 2 3) 5 0) '(5 2 3))
        )
    )
)
 
#|

                        0, if []
                        l1 + sumAll(l2...ln), if l1 is a number
    sumAll(l1l2..ln) =  sumAll(l1) + sumAll(l2..ln), if l1 is a list
                        sumAll(l2..ln), otherwise

|#

(defun sumAll (l)
    (cond
        ((null l) 0)
        ((numberp (car l)) (+ (car l) (sumAll (cdr l))))
        ((listp (car l)) (+ (sumAll (car l)) (sumAll (cdr l))))
        (t (sumAll (cdr l)))
    )
)

 (defun testSumAll ()
    (assert
        (and
            (equal (sumAll '(1 2 3)) '6)
            (equal (sumAll '(1 2 10)) '13)
            (equal (sumAll '(5 5 5)) '15)
            (equal (sumAll '(1 5 (2 4 6) (5 5 5))) '33)
        )
    )
 )

#|
                              {e}, if []
    appendElem(l1l2..ln, e) = 
                              {l1} U appendElem(l2...ln), otherwise

                        [], if []
    subsets(l1l2..ln) = l1 U subsets(l1) U subsets(l2..ln), if l1 is a list
                        subsets(l2..ln), otherwise
|#

(defun appendElem (l e)
    (cond
        ((null l) e)
        (t (cons (car l) (appendElem (cdr l) e)))
    )
)

(defun testAppendElem ()
    (assert
        (and
            (equal (appendElem '(1 5 3) '(4 5)) '(1 5 3 4 5))
            (equal (appendElem '(5 5 5 1) '(2)) '(5 5 5 1 2))
        )
    )
)

(defun subsets (l)
    (cond
        ((null l) nil)
        ((listp (car l)) (appendElem (list (car l)) (appendElem (subsets (car l)) (subsets (cdr l)))))
        (t (subsets (cdr l)))
    )
)

(defun testSubsets ()
    (assert
        (and
            (equal (subsets '((1 2 3) ((4 5) 6))) '((1 2 3) ((4 5) 6) (4 5)))
            (equal (subsets '((5 5 5 5) 2 (6 6 (5 2)))) '((5 5 5 5) (6 6 (5 2)) (5 2)))
        )
    )
)

#|
                                            true, n = 0 and m = 0
    equalSetLenghts(l1l2...ln, s1s2...sm) = false, n = 0 or m = 0
                                            equalSetLenghts(l2..ln, s2...sm), otherwise

                                        true, n = 0
    equalitySets(l1l2...ln, s1s2..sm) = false, findElem(l1, s1s2..sm) = false 
                                        equalitySets(l2...ln, s1s2...sm)

                            false, if []
    findElem(e, l1l2..ln) = true, if l1 = e
                            findElem(e, l2...ln), otherwise

                                         equalitySets(l1..ln, s1...sn), if equalSetLenths(l1..ln, s1...sn) is true
    testSetsEquality(l1...ln, s1...sn) = 
                                         false, otherwise
|# 



(defun findElem (e l)
    (cond
        ((null l) nil)
        ((= e (car l)) t)
        (t (findElem e (cdr l)))
    )
)

(defun testFindElem ()

    (assert
        (and
            (equal (findElem '5 '(1 2 3)) nil)
            (equal (findELem '5 '(5 3 2)) t)
        )
    )

)

(defun equalSetLenghts (l s)
    (cond
        ((and (null l) (null s)) t)
        ((or (null l) (null s)) nil)
        (t (equalSetLenghts (cdr l) (cdr s)))
    )
)

(defun testEqualSetLengths ()

    (assert
        (and
            (equal (equalSetLenghts '(1 2 3) '(1 2 5)) t)
            (equal (equalsetLenghts '(4 4) '(5 3 2)) nil)
        )
    )

)


(defun equalitySets (l s)
    (cond
        ((null l) t)
        ((not (findElem (car l) s)) nil)
        (t (equalitySets (cdr l) s))
    )
)

(defun testEqualitySets ()

    (assert
        (and
            (equal (equalitySets '(1 2 3) '(1 2 3)) t)
            (equal (equalitySets '(4 4 4) '(5 3 2)) nil)
        )
    )

)


(defun testSetsEquality (l s)

    (cond 
        ((equalSetLenghts l s) (equalitySets l s))
        (t nil)
    )
)


#|
    b) 11. Return the level (and coresponded list of nodes) with maximum number of nodes for a tree of type
(2). The level of the root element is 0.
|#



#|
                                counter, if []
findMaxLevel(l1l2l3, counter) = 
                                max(findMaxLevel(l2, counter + 1), findMaxLevel(l3, counter + 1)) otherwise
|#



(defun findMaxLevel(l counter)
  (cond
    ((null l) counter)
    (t (max(findMaxLevel (cadr l) (+ 1 counter)) (findMaxLevel (caddr l) (+ 1 counter))))
  )
)

(defun testFindMaxLevel()
    (assert
        (and
            (equal (findMaxLevel '(A (B) (C (D) (E))) '-1) '2)
            (equal (findMaxLevel '(A (B) (C (D (F)) (E))) '-1) '3)
        )
    )

)

#|
                                                [], if []
    getNodesFromLevel(l1l2l3, level, counter) = l1, if counter = level
                                                getNodesFromLevel(l2, level, counter + 1) U getNodesFromLevel(l3, level, counter + 1), otherwise
|#

(defun getNodesFromLevel(l level counter)
  (cond
    ((null l) nil)
    ((equal counter level) (list (car l)))
    (t (appendElem (getNodesFromLevel (cadr l) level (+ 1 counter)) (getNodesFromLevel (caddr l) level (+ 1 counter))))
  )
)

(defun testGetNodesFromLevel()

    (assert
        (and
            (equal (getNodesFromLevel '(A (B) (C (D) (E))) '1 '0) '(B C))
            (equal (getNodesFromLevel '(A (B) (C (D) (E))) '2 '0) '(D E))
        )
    )
)

#|
                            0, if []
numberOfElements(l1l2l3) = 
                            1 + numberOfElements(l2...ln)
 |#

(defun numberOfElements(l)
    (cond
        ((null l) 0)
        (t (+ 1 (numberOfElements(cdr l))))
    )
)

(defun testNumberOfElements()

    (assert
        (and
            (equal (numberOfElements '(1 2 3)) '3)
            (equal (numberOfElements '(1 2 3 5)) '4)
        )
    )
)

#|
                                                        {bestLvl} + getNodesFRomLevel(l bestLvl)
main(l1..ln, currLevel, maxLevel, bestVal, bestLvl) =   main(l1...ln, currLvl + 1, maxLevel, numberOfElements(getNodesFRomLevel(l1...ln, currLevel), currLevel))
                                                        main(l1....ln, currLvl + 1, maxLevel, bestVal, bestLevel)
 |#



(defun main(l currLevel maxLevel bestVal bestLvl)
  (cond
    ((> (numberOfElements (getNodesFromLevel l currLevel 0)) bestVal) (main l (+ currLevel 1) maxLevel (numberOfElements (getNodesFromLevel l currLevel 0)) currLevel))
    ((= currLevel maxLevel) (cons bestLvl (getNodesFromLevel l bestLvl 0)))
    (t (main l (+ currLevel 1) maxLevel bestVal bestLvl))
  )
)

(defun run(l)
    (main l 0 (findMaxLevel l -1) 0 0)
)

(defun testRun()
    (assert
        (and
            (equal (run '(A (B) (C (D) (E)))) '(1 B C))
            (equal (run '(A (B (D)) (C (E)))) '(1 B C))
        )
    )

)

(defun runTests()
    (progn
        (testIns)
        (testRepl)
        (testSumAll)
        (testSubsets)
        (testFindELem)
        (testequalsetlengths)
        (testequalitysets)
        (testfindmaxlevel)
        (testgetnodesfromlevel)
        (testNumberOfElements)
        (testRun)
    )

)




