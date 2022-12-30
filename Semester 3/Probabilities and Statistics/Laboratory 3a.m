# X - normal u, o
# X - student n
# X - Fischer m, n

a = input('alpha = ');
b = input('beta = ');

option = input('option = ', 's');
switch option
  case 'normal'
      n = input('n = ');
      o = input('o = ');

      #a) p1 = P(X <= 0) =
                          p1 = normcdf(0, n, o)
      #    p2 = P(x >= 0) = 1 - p1
                          p2 = 1 - p1

      #b) p3 = P(-1 <= x <= 1) =
                                 p3 = normcdf(1, n, o) - normcdf(-1, n, o)
      #   p4 = P(x <= -1 or X >= 1) = 1 - p3
                                 p4 = 1 - p3

      #c) xa = ? P(X < xa) = a E (0, 1)
                                 xa = norminv(a, n, o)

      #d) xb = ?
            xb = norminv(1-b, n, o)
  case 'Student'
      n = input('n = ');
       #a) p1 = P(X <= 0) =
                          p1 = tcdf(0, n)
      #    p2 = P(x >= 0) = 1 - p1
                          p2 = 1 - p1

      #b) p3 = P(-1 <= x <= 1) =
                                 p3 = tcdf(1, n) - tcdf(-1, n)
      #   p4 = P(x <= -1 or X >= 1) = 1 - p3
                                 p4 = 1 - p3

      #c) xa = ? P(X < xa) = a E (0, 1)
                                 xa = tinv(a, n)

      #d) xb = ?
            xb = tinv(1-b, n);

  case 'Fisher'
       m = input('m = ');
       n = input('n = ');
       #a) p1 = P(X <= 0) =
                          p1 = fcdf(0, m, n)
      #    p2 = P(x >= 0) = 1 - p1
                          p2 = 1 - p1

      #b) p3 = P(-1 <= x <= 1) =
                                 p3 = fcdf(1, m, n) - fcdf(-1, m, n)
      #   p4 = P(x <= -1 or X >= 1) = 1 - p3
                                 p4 = 1 - p3

      #c) xa = ? P(X < xa) = a E (0, 1)
                                 xa = finv(a, m, n)

      #d) xb = ?
            xb = tinv(1-b, n)
  otherwise
   fprintf('Invalid input');

  end

