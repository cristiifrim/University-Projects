/*
Given a numerical linear list consisting of integers, add a value after each element from
position N to N.
Se da o lista formata din numere intregi. Se cere sa se adauge din M in M o valoare e data.
[1 2 3 4 5 6 7 8] 3 11 -> [1 2 3 11 4 5 6 11 7 8]
[1 2 3 4 5] 3 11 -> [1 2 3 11 4 5]
[1 2 3] 5 11 -> [1 2 3]
[1 2 3 4 5 6 7 8 9 10] 2 11 -> [1 2 11 3 4 11 5 6 11 7 8 11 9 10 11]
*/


/*

                                [], if [] and N > 0
addToList(l1l2...ln, originalN, N, E) =    l1 U addToList(l2...ln, originalN, N-1, E), if L != [] and N > 0
                                E u addToList(l2...ln, originalN, originalN, E), if N = 0
            
*/

% addToList(L: list, ON: number, N: number, E: number, R: list)
% (i, i, i, i, o), (i, i, i, i, i)

addToList([], _, 0, E, [E]):- !.
addToList([], _, N, _, []):- N > 0, !.
addToList([H | T], ON, N, E, [H | R]):-
    N > 0, !,
    N1 is N-1,
    addToList(T, ON, N1, E, R).

addToList(L, ON, 0, E, [E | R]):-
    addToList(L, ON, ON, E, R).

test_addToList():-
    addToList([1, 2, 3, 4, 5, 6, 7, 8], 3, 3, 11, [1, 2, 3, 11, 4, 5, 6, 11, 7, 8]),
    addToList([1, 2, 3, 4, 5], 3, 3, 11, [1, 2, 3, 11, 4, 5]),
    addToList([1, 2, 3], 5, 5, 11, [1, 2, 3]),
    addToList([1, 2, 3, 4, 5, 6, 7, 8, 9, 10], 2, 2, 11, [1, 2, 11, 3, 4, 11, 5, 6, 11, 7, 8, 11, 9, 10, 11]).


