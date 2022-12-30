
/*
                                            empty set [], if [] and N > 1
mathematical model: ins(l1l2...ln, e, N) =  [e], if [] and N = 1
                                            {e} U l1l2...ln, if N = 1
                                            l1 U ins(l2l3...ln, e, N - 1), otherwise
*/

%ins(L: list, E: int, N: int, R: list)
%flow (i, i, i, o)
%flow (i, i, i, i)
ins([], _, N, []):- N > 1.
ins([], E, N, [E]):- N =:= 1.
ins(L, E, N, R):- N =:= 1, R = [E | L].
ins([H|T], E, N, [H|R]):- N > 1, N1 is N-1, ins(T, E, N1, R).


/*

            a, if b = 0
gcd(a, b) = 
            gcd(b, a % b), otherwise


                         0, if empty list []
gcd_of_list(l1l2...ln) = 
                         gcd(l1, gcd_of_list(l2l3...ln)), otherwise

*/

%gcd(A:int, B: int, R: int)
%flow(i, i, o)
%flow(i, i, i)
gcd(A, 0, A):- !.
gcd(A, B, R):- B > A, gcd(B, A, R).
gcd(A, B, R):- AUX is A mod B, gcd(B, AUX, R).


%gcd_of_list(L: list, R: int)
%flow(i, o)
%flow(i, i)
gcd_of_list([], 0).
gcd_of_list([H|T], R):- gcd_of_list(T, R1), gcd(H, R1, R). 


%Tests

%same(L1: list, L2: list)
%flow(i, i)
same([], []).
same([H1|R1], [H2|R2]):-
    H1 = H2,
    same(R1, R2).



testGcd():- gcd(4, 12, R), R =:= 4,
            gcd(1, 102, R1), R1 =:= 1,
            gcd(2, 5, R2), R2 =:= 1,
            gcd(5, 0, R3), R3 =:= 5.

testGcdOfList():- gcd_of_list([2, 4, 20, 22], 2),
                  gcd_of_list([2, 5, 8, 10], 1), 
                  gcd_of_list([10, 50, 1200], 10).

testIns():- ins([], 5, 5, []),
            ins([1,2,3], 5, 2, R1), same(R1, [1,5,2,3]),
            ins([1,2, 3], 5, 1, R2), same(R2, [5,1,2,3]),
            ins([], 5, 1, R3), same(R3, [5]).

tests():- testGcd(), testGcdOfList(), testIns().
