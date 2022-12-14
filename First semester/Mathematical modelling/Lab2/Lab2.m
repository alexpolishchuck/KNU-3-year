clear 
% Варіант 5
%f(a,t)=a_1+a_2 t+a_3 t^2+a_4 t^3+a_5sin⁡(2 * pi * b_1 t)+a_6cos⁡(2 * pi * b_1t) +
% + a_7sin⁡(2 * pi * b_2 t)+a_8cos⁡(2 * pi * b_2 t)
% коефіцієнти b_1, b_2 - результати, отримані в першій лабораторній роботі.
% b_1 = 5
% b_2 = 20
% задача - знайти коефіцієнти а1,a2,...,a8
b1 = 5;
b2 = 20;
Fs = 100;     %частота        
T = 1/Fs;   %період
L = 500;    % кількість вимірів

Y = dlmread('f5.txt', ' '); %зчитування вимірів
Y = Y(1:end-1);
t = (0:L-1)*T;  % моменти часу

N = 8; %кількість коефіцієнтів
% представимо функцію f(a,t) у вигляді матричного добутку A*a = b
A = zeros(L, N); % матриця моментів часу
b = zeros(L, 1); % матриця вимірів
for i=1:L
    A(i,1) = 1;
    A(i,2) = t(i);
    A(i,3) = t(i)^2;
    A(i,4) = t(i)^3;
    A(i,5) = sin(2 * pi * b1 * t(i));
    A(i,6) = cos(2 * pi * b1 * t(i));
    A(i,7) = sin(2 * pi * b2 * t(i));
    A(i,8) = cos(2 * pi * b2 * t(i));
    b(i,1) = Y(i);
end

a = lsqr(A, b); %least squares 
fprintf('Coefficients: [%s]\n', join(string(a), ',')); % коефіцієнти
f = a(1)*1 + a(2)*t + a(3)*t.^2 + a(4)*t.^3 + a(5) * sin(2 * pi * b1 * t) + a(6) * cos(2 * pi * b1 * t)+ a(7)*sin(2 * pi * b2 * t) + a(8)*cos(2 * pi * b2 * t);
F = (norm(f-Y)^2)/ 2; %похибка
figure()
subplot(1,2,1)
plot(t, Y) % початкова функція 
xlabel ("Моменти часу")
ylabel ("Виміри")
title("Початкова функція");
subplot(1,2,2)
plot(t,f) % модель
xlabel ("Моменти часу")
ylabel ("Виміри")
title("Модель. Похибка: " + F);

