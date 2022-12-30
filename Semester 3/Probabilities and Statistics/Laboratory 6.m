clear;
pkg load statistics

x = [7,7,4,5,9,9,4,12,8,1,8,7,3,13,2,1,17,7,12,5,6,2,1,13,14,10,2,4,9,11,3,5,12,6,10,7];
n = length(x);

sigma = 5;
alpha = 0.01;

#H0: miu = 9
#H1: miu < 9

# 1. a)

miu = 9;

[h, p, ci, ztest] = ztest(x, miu, sigma, 'alpha', alpha, 'tail', 'left')


RR = [-inf, norminv(alpha)]

if h == 1
  fprintf('the null hypothesis is rejected\n');
else
  fprintf('the null hypothesis is accepted\n');
endif

 fprintf('the rejection region = [%1.2f, %1.2f]\n', RR);
 fprintf('the value of the statistic = %1.2f\n', ztest);
 fprintf('the p-value = %1.2f\n', p);

 #1. b)
#H0: miu = 5.5
#H1: miu > 5.5

miu = 5.5;

[h, p, ci, test] = ttest(x, miu, 'alpha', alpha, 'tail', 'right')

RR = [tinv(1 - alpha / 2, n - 1), inf]

if h == 1
  fprintf('the null hypothesis is rejected\n');
else
  fprintf('the null hypothesis is accepted\n');
endif

 fprintf('the rejection region = [%1.2f, %1.2f]\n', RR);
 fprintf('the value of the statistic = %1.2f\n', test.tstat);
 fprintf('the p-value = %1.2f\n', p);


 #2.





