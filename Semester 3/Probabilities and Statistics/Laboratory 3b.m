#2)
  option = input('option = ', 's')

  switch option
    # a)
    case 'normal'
      p = input('p = ');
      for n = 1:3:100
        k = 0:n
        prob = binopdf(k, n, p);
        plot(prob);
        pause(0.2);
        xlim([0, 100]);
        ylim([0, 0.15]);
      endfor
    # b)
    case 'poisson'
      n = input('n = ');
      p = input('p = ');

      l = n * p;
      k = 0:n;
      p1 = poisspdf(k, l);
      p2 = binopdf(k, n, p);
      plot(k, p1, k, p2);
      pause(0.2);

  endswitch



