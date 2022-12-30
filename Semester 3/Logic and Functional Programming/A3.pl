/*
-------------------------------------------------------
                        -1, if n = 0 or []

getMaximum(l1l2...ln) = l1, if l1 > getMaximum(l2...ln) and l1 is number
                        
                        getMaximum(l2...ln), otherwise

-------------------------------------------------------

                                [], if n = 0 or []

replaceElem(l1l2...ln, E, N) =  N U replaceElem(l2...ln, E, N), if l1 == E and l1 is number 
    
                                l1 U replaceElem(l2...ln, E, N), otherwise

------------------------------------------------------- 

                                 [], if n = 0 or []

replaceMaximum(l1l2...ln, max) = l1 U replaceMaximum(l2...ln, max), if l1 is number

                                 replaceElem(l1, max, getMaximum(l1)) U replaceMaximum(l2...ln, max), otherwise

-------------------------------------------------------

replaceMaxOfListInSublists(l1l2...ln) = replaceMaximum(l1l2...ln, getMaximum(l1l2...ln)) 

-------------------------------------------------------
*/

% getMaximum(L: list, R: integer)
% (i, o) - deterministic
% (i, i) - deterministic

getMaximum([], MX, MX).

getMaximum([H | T], MX, R):- 
	number(H), 
    H > MX, !,
    getMaximum(T, H, R1),
    R is R1.

getMaximum([_ | T], MX, R):-
	getMaximum(T, MX, R).

% getMinimum(L: list, R: integer)
% (i, o) - deterministic
% (i, i) - deterministic

getMinimum([], MX, MX).

getMinimum([H | T], MX, R):- 
	number(H), 
    H < MX, !,
    getMinimum(T, H, R1),
    R is R1.

getMinimum([_ | T], MX, R):-
	getMinimum(T, MX, R).

testGetMaximum():-
    getMaximum([], -1, -1),
    getMaximum([1, 2, 3, 4, 90], -1, 90),
    getMaximum([90, 5, 28, 40], -1, 90),
    getMaximum([500, 10, 25, 650, 89, 10], -1, 650),
    getMaximum([123, 50, [1, 2, 3], [999, 10000, 200], 32], -1, 123),
    true.

% -------------------------------------------------------

% replaceElem(L: list, E: integer, N: integer, R: list)
% (i, o) - deterministic
% (i, i) - deterministic

replaceElem([], _, _, []).
replaceElem([H | T], E, N, [N | R]):- 
    number(H),
    replaceElem(T, E, N, R),
    H =:= E,
    !.

replaceElem([H | T], E, N, [H | R]):-
    replaceElem(T, E, N, R).

testReplaceElem():-
    replaceElem([], 1, 2, []),
    replaceElem([1, 2, 3, 4, 5, 5, 5], 5, 1, [1, 2, 3, 4, 1, 1, 1]),
    replaceElem([1, 2, [3, 4, 5], 5, 5], 5, 1, [1, 2, [3, 4, 5], 1, 1]),
    replaceElem([[1, 2, 3], [5, 5, 5]], 5, 2, [[1, 2, 3], [5, 5, 5]]),
    true.
    
% -------------------------------------------------------

% replaceMaximum(L: list, M: integer)
% (i, o) - deterministic
% (i, i) - deterministic

replaceMaximum([], _, []).
replaceMaximum([H | T], M, [R2 | R]):-
    is_list(H), !,
    getMaximum(H, -1, R1),
    replaceElem(H, M, R1, R2),
    replaceMaximum(T, M, R).

replaceMaximum([H | T], M, [H | R]):-
    replaceMaximum(T, M, R).

testReplaceMaximum():- 
    replaceMaximum([], 1, []),
    replaceMaximum([1, 2, 3, 9, 8], 9, [1, 2, 3, 9, 8]),
    replaceMaximum([1, [1, 2, 3], [5, 5, 9], [8, 9, 5]], 5, [1, [1, 2, 3], [9, 9, 9], [8, 9, 9]]),
    true.

% -------------------------------------------------------

replaceOddEven([], _, _, []).

replaceOddEven([H | T], MX, MN, [MX | R]):-
    number(H),
    replaceOddEven(T, MX, MN, R),
    mod(H, 2) =:= 0,
    !.

replaceOddEven([H | T], MX, MN, [MN | R]):-
    number(H),
    replaceOddEven(T, MX, MN, R),
    mod(H, 2) =:= 1,
    !.

replaceWrapper([], _, _, []).
replaceWrapper([H | T], MX, MN, [R1 | R]):-
    is_list(H), !,
    replaceOddEven(H, MX, MN, R1),
    replaceWrapper(T, MX, MN, R).

replaceWrapper([H | T], MX, MN, [H | R]):-
    replaceWrapper(T, MX, MN, R).
    
replaceMinMax([], []).
replaceMinMax(L, R):-
    getMaximum(L, -1, MX),
    getMinimum(L, 1000000, MN),
    replaceWrapper(L, MX, MN, R).
    

% -------------------------------------------------------

replaceMaxOfListInSublists(L, R):-
    getMaximum(L, -1, R1),
    replaceMaximum(L, R1, R).

testReplaceMaxOfListInSublists():-
    replaceMaxOfListInSublists([], []),
    replaceMaxOfListInSublists([1, [1, 2, 3], [5, 5, 9], [8, 9, 5], 5], [1, [1, 2, 3], [9, 9, 9], [8, 9, 9], 5]),
    replaceMaxOfListInSublists([1, [1, 2, 3], [5, 5, 9], [8, 9, 5], 5, 9, 10], [1, [1, 2, 3], [5, 5, 9], [8, 9, 5], 5, 9, 10]),
    replaceMaxOfListInSublists([1, [1, 10, 17], [5, 15, 10], [11, 9, 10], 5, 10], [1, [1, 17, 17], [5, 15, 15], [11, 9, 11], 5, 10]),
    true.




