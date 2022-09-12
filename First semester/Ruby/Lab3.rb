# frozen_string_literal: true

# Task1.1

def logicalOpearators(a, b, c, x, y, z)
  puts !(a || b) && (a && !b)
  puts (z != y ? 1 : 0) <= (y >= 6 ? 1 : 0) && a || b && c && x >= 1.5 # ???(z != y) <= (y >=6)
  puts ((8 - (x * 2)) <= z) && (x**2 != y**2) || (z >= 15)
  puts x.positive? && y.negative? || z >= (x * y + (y / x * -1)) + (z / 3) * -1
  puts !(a || b && !(c || (!a || b)))
  puts x**2 + y**2 >= 1 && x >= 0 && y >= 0
  puts (a && (c && b != b || a) || c) && b
end
logicalOpearators(true, false, false, 22, 30, 50)

# Task1.2
def logicalRes(n, p)
  (Math.exp(n * Math.log(2).to_f) < n**2) || !(p || !p)
end

puts "Task1.2 = #{logicalRes(1, false)}"

# Task2
def equation(x)


  case x
  when -Float::INFINITY..-4
    puts(1.0 / (1.0 + x / (1.0 + x / (1.0 + x))))
  when -4..0
    puts((x - 2.0).abs / (x**2 * Math.cos(x))**(1.0 / 7))
  when 0..12
    puts(1.0 / ((Math.tan(x.to_f + 1.0 / Math.exp(x)) / Math.sin(x)**2)**(1.0 * 7.0 / 2)))
  else
    puts(1.0 / (1.0 + x / (1.0 + x / (1.0 + x))))
  end

  if x > -4 && x <= 0
    puts((x - 2.0).abs / (x**2 * Math.cos(x))**(1.0 / 7))
  elsif x.positive? && x <= 12
    puts(1.0 / ((Math.tan(x.to_f + 1.0 / Math.exp(x)) / Math.sin(x)**2)**(1.0 * 7.0 / 2)))
  else
    puts(1.0 / (1.0 + x / (1.0 + x / (1.0 + x))))
  end
end
equation(1000)
#Task3
def sum(n,x)
  res =1
  counter =1.0
  for i in 1..8
    counter *= 1.0/3
    res += counter
  end
  puts "Task3.1 = #{res}"
  res =1
  counter = 1
  for i in 1..n
    counter*=x.to_f/i.to_f
    res += counter

  end
  puts "Task3.2 = #{res}"
end
sum(5,4)
#Task4
def factorial(n)
  n > 1 ? n * factorial(n - 1) : 1
end

def calculateseries(x)
  e = 0.00001
  res =0
  counter = 0
  n=2
  loop do
    counter= (factorial(n-1).to_f/factorial(n+1).to_f )**(n*(n+1))
    res +=counter
    break if counter < e
    n+=1
  end
  puts "Task4.1 = #{res}"

  n=0
  res=0
  loop do
    counter= ((-1)**n).to_f * (x**(2*n)).to_f/(factorial(2*n))
    res +=counter
    break if counter >0 && counter < e
    n+=1
  end
  puts "Task4.2 = #{res}"

  n=1
  res=0
  loop do
    counter= factorial(4*n - 1).to_f/(factorial(4*n - 1).to_f * 3**(2*n) * factorial(2*n).to_f)
    res +=counter
    break if counter >0 && counter < e
    n+=1
  end
  puts "Task4.3 = #{res}"
end
calculateseries(5)

#Task5

def Yfunction(x,c,n)
  if !(n.is_a?Numeric) || !(c.is_a?Numeric) || !(x.is_a?Numeric) || (n<0)||(c<0)
    return "Error"
  end
  c = c.to_f
  n = n.to_f
  x = x.to_f
  (((c**(3.0/2) + 27*x**(3.0/5))/(Math.sqrt(c) + 3 * x**(1.0/5)) + 3*(32*x**2)**(1.0/10))*x**(-2))**c +
    (1 + x**(1.0/c) + x**2)/((n-x) + 7.0/3)
end

def Zfunction(x,c,n)
  if !(n.is_a?Numeric) || !(c.is_a?Numeric)|| !(x.is_a?Numeric)
    return "Error"
  end

  p = 3.141
  (1 - Math.cos(4*x))/(Math.cos(2*x)**(-2) - 1) +
    (1+ Math.cos(4*x))/(Math.sin(2*x)**(-2) -1) + Math.tan(2*p/9 -x)**(c/n)


end

N = 7
C = 10 #random
PI = 3.141
steps = (N+C).to_i
counter = 0
puts"Task5.1"
for i in 0..steps
  print"#{Yfunction(1.0 + i.to_f*((N.to_f - 1)/steps.to_f),C,N)}  "
end

steps = ((3/2)*N+C).to_i
puts"\nTask5.2"
for i in 0..steps
  print"#{Zfunction(PI/N + i.to_f*((PI - PI/N)/steps.to_f),C,N)}  "
end
puts"\nTask5.3"
steps = 2*N
for i in 0..steps

  x = 2 + i*(C-2)/steps

  if x>2 && x<N
    print"y = #{Yfunction(x,C,N)}  "
  elsif x>N && x<2*N
    print"z = #{Zfunction(x,C,N)}  "
  else
    print"y+z = #{Yfunction(x,C,N).to_f + Zfunction(x,C,N).to_f}  "
  end
end