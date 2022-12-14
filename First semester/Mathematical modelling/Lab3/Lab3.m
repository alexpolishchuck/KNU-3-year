clear
%5 ???????
X = double(imread('x1.bmp')); %?????? ??????????
Y = double(imread('y5.bmp')); %??????? ??????????
x1 = MoorePenrosePseudoInversion(X, 1);
x2 = GrevillePseudoInversion(X);
V = rand(size(Y,1), size(X,1));
x1 = Y*x1;%???????? ?? ????????? ?????? ????
x2 = Y*x2;%?????????? ????????? ? ????????????? invXGrevil
figure;
subplot(4,1,1);
imshow(uint8(X));
title('INPUT');
subplot(4,1,2);
imshow(uint8(Y));
title('OUTPUT');
subplot(4,1,3);
imshow(uint8(x1*X)); % ???????????? ???????? ??????????
title('MoorePenrosePseudoInversion');
subplot(4,1,4);
imshow(uint8(x2*X));% ???????????? ???????? ??????????
title('GrevillePseudoInversion');

function n = norm(A)
    n = sum(sum(abs(A)));
end


function pInv = MoorePenrosePseudoInversion(A, delta) 
    E = eye(size(A,1)); % ???????? ???????
    invA0 = transpose(A); % ?????????????? 
    invA = transpose(A) * inv(A * transpose(A) + delta * E); % ???????????? ??????? ??????????? ??????????????? ???????
    eps = norm(invA-invA0); % ????????
    i = 1;
    while eps > 0.000001 % ???????????, ???? ?? ????????? ?????????? ?????????? 
    i = i + 1;
    invA0 = invA;
    delta = delta/2;
    invA = transpose(A) * inv(A * transpose(A) + delta * E);
    eps = norm(invA-invA0);
    end
pInv = invA;
end

function pInv = GrevillePseudoInversion(A)% ????? ??????? 
    
    a = A(1,:)';% ?????? ?????????????? ?????
    res = 0;
    if (a' == 0)
        res = a;
    else
        res = a/(a' * a);     %res - ??????? ?????????????? ???????
    end
    sz = size(A,1); %????? 
    currentMatrix = a'; % ???????, ???????? ? ???????????? ????????? ??????
    
    for i = 2:sz  % ?? ???? ??????
        a = A(i,:)'; % ????????? ?????????????? ?????
        Z = eye(size(res,1)) - res * currentMatrix; 
        R = res * res'; % ?????????, ???????? ??? ??????????? 
        if(a' * Z * a > 0)
           res = [(res  - (Z * a * a' * res)/(a' * Z * a)),(Z * a)/(a' * Z * a)];
        else
            res = [(res  - (R * a * a' * res)/(1 + a' * R * a)),(R * a)/(1 + a' * R * a)]; 
        end % ??????????? ??????? ??????? 
        currentMatrix = [currentMatrix;a']; % ????????? ?????
    end
    
    pInv = res; %?????????? ?????????
end
