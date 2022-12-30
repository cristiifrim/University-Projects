clf; hold on;

n = input("No of  x");
p = input("Prob of resasda");

x = 0 : n;

px = binopdf(x, n, p);

plot(x, px, '+');

xx = 0:0.1:n;

cx = binocdf(xx, n, p);
plot(xx, cx, '-');

p1 = binopdf(0, 3, 0.5);
fprintf('P(x = 0) = %1.4f', p1);

N = 10;
C = rand(3, N);

D = C < 0.5;

E = sum(D);

hist(E);

