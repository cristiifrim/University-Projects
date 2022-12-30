%=====================================
%                       e U l1l2...ln 
% insert(e, l1l2...ln) = 
%                       l1 U insert(e, l2...ln)
%=====================================
%                             l1, if k = 1
% arrangements(l1l2..ln, k) = arrangements(l2...ln, k), if k >= 1
%                             l1 U arrangements(l2...ln, k-1), if k > 1
%=====================================


%insert(E: number, L: list, R: list)
% (i, i, o), (i, i, i)
insert(E, L, [E | L]).
insert(E, [H | T], [H | R]):-
    insert(E, T, R).


test_insert():-
    findall(RL, insert(2, [3, 4, 5], RL), [[2, 3, 4, 5], [3, 2, 4, 5], [3, 4, 2, 5], [3, 4, 5, 2]]),
    findall(RL, insert(10, [1, 2], RL), [[10, 1, 2], [1, 10, 2], [1, 2, 10]]).

%arrangements(L: list, K: number, R: list)
% (i, i, o), (i, i, i)
arrangements([E | _], 1, [E]).
arrangements([_ | T], K, R):-
    arrangements(T, K, R).
arrangements([H | T], K, R1):-
    K > 1,
    K1 is K-1,
    arrangements(T, K1, R),
    insert(H, R, R1).

test_arrangements():-
    findall(RL, arrangements([2, 3, 4], 2, RL), [[3, 4], [4, 3], [2, 3], [3, 2], [2, 4], [4, 2]]),
    findall(RL, arrangements([1, 3, 5], 2, RL), [[3, 5], [5, 3], [1, 3], [3, 1], [1, 5], [5, 1]]).

% allsols(L: list, K: number, R: result)
% (i, i, o), (i, i, i)
allsols(L, K, R):-
    findall(RL, arrangements(L, K, RL), R).