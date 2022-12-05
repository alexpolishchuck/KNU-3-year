clear

main();

function res = taskFunction(x)
res = x^2;
end

function res = lagrange_method(initVals, n )

poly = [0];
for i = 1:n+1
    p1 = [1];
    
    div = 1;
    for j = 1:n+1
        if j == i
            continue
        end
        div = div * (initVals(1,i) - initVals(1,j));
        p1 = conv(p1,[1 -initVals(1,j)]);
    end
    
    p1 = conv(p1, [initVals(2,i)]);
    p1 = p1 /div;
   % poly = p1 + poly;
    poly = sum_poly_coeff(p1,poly);
end

res = poly;
end
function res = findDivDif(a, b, initVals)
if a == b 
    res = initVals(2,a);
    return;
end
res = 0;
for i = a:b
   div = 1;
   for j = a:b
       if j == i
           continue
       end
       div = div * (initVals(1,i) - initVals(1,j));
   end
   res = res + initVals(2,i)/div;
end
end
function res = newton_method(initVals, n )
poly  = [initVals(2,1)];
prev = initVals(2,1);
pTemp = [1];
for i = 2:n
    pTemp = conv(pTemp,[1 -initVals(1,i-1)]);
    f = (findDivDif(2,i,initVals) - prev)/(initVals(1,i) - initVals(1,1));
    poly = sum_poly_coeff(conv(pTemp, [f]), poly);
    prev = f;
end
res = poly;
end
function res = spline(initVals, n, h)
 A = zeros(n-2,n-2);
 B = zeros(1,n-2);
 for i = 2:n-1
     B(i-1) = 3 * ((initVals(2,i+1) - 2*initVals(2,i) + initVals(2,i-1))/h);
     if i-2 > 0
         A(i-1, i-2) = h;
     end
     A(i-1, i-1) = 4 * h;
     if i<n-1
     A(i-1, i) = h;
     end
 end
 c = solveTRIDIG(A,B);
 c = [0 c 0];
 res = 0;
 for i= 2:n
     a = initVals(2,i);
     d = (c(i) - c(i-1))/(3 * h);
     b = (a - initVals(2,i-1)/h + (2*c(i) + c(i-1))*h/3);
     conv1 = conv([1 -initVals(1,i)],[1 -initVals(1,i)]);
     conv2 = conv(conv1,[1 -initVals(1,i)]);
     elem1 = b * [1 -initVals(1,i)];
     elem2 = sum_poly_coeff(c(i)*conv1,d*conv2);
     poly = sum_poly_coeff([a],elem1);
     poly = sum_poly_coeff(elem2,poly); 
     %[d (-d*3*initVals(1,i) + c(i)) (d*3*initVals(1,i)^2 - 2*c(i)*initVals(1,i) + b) (d*initVals(1,i)^3 + c(i)*initVals(1,i) - b*initVals(1,i))];
     if res == 0
         res = poly;
     else
         res = [res;poly];
     end
     
 end
end
function res = solveTRIDIG(A,B)
n = length(A(1,:));
x = zeros(1,n);
for i = 2:n+1
    a =  A(i-1,i-1);
     A(i-1,i-1) = 1;
    B(i-1) = B(i-1)/a;
    if i <= n
        A(i-1,i) = A(i-1,i) / a;
        a1 = A(i, i-1);
        A(i, i-1) = 0;
        A(i,i) = A(i,i) - A(i-1,i) * a1;
    end
    end
x(n) = B(n);
for i = n-1:-1:1
    x(i) = (B(i) - x(i+1) * A(i,i+1))/A(i,i);
end
    
res = x;
end
function coeff_sum = sum_poly_coeff(x1, x2)
x1_order = length(x1);
x2_order = length(x2);
max_order = 0;
if x1_order > x2_order
     max_order = length(x1);
else
     max_order = length(x2);
end
new_x1 = [zeros(1,max_order-length(x1)) x1];
new_x2 = [zeros(1,max_order-length(x2)) x2];
coeff_sum = new_x1 + new_x2;
return
end
function res = calculateInitFunction(n, a, h)
A = zeros(2,n+1) ;
atemp = a;
for z=1:n+1
    
    A(1,z) = atemp;
    A(2,z) = taskFunction(atemp);
    atemp = atemp + h;
end
res = A;
end
function main()
a = -80;
b = 20;
n = 100;
h = (b - a)/n;
A = calculateInitFunction(n, a, h);
RealFunc = calculateInitFunction(b-a,a, 1);

p1 = lagrange_method(A, n);
p2 = newton_method(A, n);
p3 = spline(A, n + 1, h);

y =[];
j = 1;
cur = a + h;
for i = a:b
    if i>cur
        cur = cur + h;
        j = j + 1;
    end
   
    if length(y) == 0
        y = [polyval(p3(j,:), i)];
    else
    y = [y polyval(p3(j,:), i)];
    end
end
hold on
plot(RealFunc(1,:), RealFunc(2,:), "green");
plot(RealFunc(1,:), polyval(p1,RealFunc(1,:)), "red");
plot(RealFunc(1,:), polyval(p2,RealFunc(1,:)), "magenta");
plot(RealFunc(1,:), y, "blue");

%for i = 1: length(p3(:,1))
%    plot(RealFunc(1,:), polyval(p3(i,:),RealFunc(1,:)), "cyan");
%end
hold off


end