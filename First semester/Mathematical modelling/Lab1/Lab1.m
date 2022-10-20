clear

fs = 100;
T= 1/fs;
L =500;
Y = dlmread('f5.txt',' ');
Y =Y(1:end-1);
t = (0:L-1)*T;

plot(t,Y)
axis padded
figure()

Yf = fft(Y);
plot(Yf)
axis padded
figure()

Sp = abs(Yf)*2/L;
tf = (0:L-1)*fs/L;
plot(tf,Sp)
axis padded
figure()

Sp = Sp(1:L/2);
tf = tf(1:L/2);

plot(tf,Sp)
axis padded

findLocalMaxs(L,tf,Sp);
function findLocalMaxs(L,tf,Sp)
    y=[];
    for i=1:(L/2 -2)
        a = tf(i);
        b = tf(i+1); 
        c = tf(i+2);
        if Sp(i+1)>Sp(i) && Sp(i+1) >Sp(i+2)
            y = [y,b];
        end
        end
        fprintf('Local maximums: [%s]\n', join(string(y), ','));
end