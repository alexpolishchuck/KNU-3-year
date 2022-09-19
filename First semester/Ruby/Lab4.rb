#Task0
a = Array.new(14)
b = Array.new(14)
size = 14
for i in 0..size - 1
  a[i] = rand(10)
  b[i] = rand(10)
end
a.sort!
b.sort!
c = Array.new()
i = 0
j = 0
while i < size && j < size
  while i < size && a[i] < b[j]
    i += 1
  end
  while j < size && a[i] > b[j]
    j += 1
  end
  if i < size && j < size && a[i] == b[j]
    c.push(a[i])
    while i < size && a[i] == b[j]
      i += 1
    end
    while j < size && b[j] == a[i - 1]
      j += 1
    end
  end

end
puts "Task0"
print"a= "
a.each { |a| print" #{a}," }
print"\nb= "
b.each { |a| print" #{a}," }
print"\nc= "
c.each { |a| print" #{a}," }


#Task1
ARRSIZE = 8
x = Array.new(ARRSIZE)
y = Array.new(ARRSIZE)
for i in 0..ARRSIZE - 1
  x[i] = rand(10)
  y[i] = rand(10)
end

#Task1

puts"\nTask1"
print"x=  "
x.each { |a| print "#{a}, " }
print"\ny=  "
y.each { |a| print "#{a}, " }
print"\n"
def multiplyVectors(x1,y1)

  res = 0
  for i in 0..ARRSIZE - 1
    res += x1[i] * y1[i]

  end
  print"#{res}"
end
multiplyVectors(x,y)

K = 9

#Task2

puts "\nTask2"
def solveEquations(n)
  if(n<3 || n>9)
    return
  end
  matrix = Array.new(n){Array.new(n,K.to_f)}
  for i in 0..n-1
    matrix[i][i] = 2.0
  end

matrix.each { |x| puts x.join(" ") }

  b = Array.new(n)
  res = Array.new(n)
  for i in 0..n-1
    b[i]=i.to_f + 1
  end

  for i in 0..n-1
    leading = matrix[i][i]
    b[i]/= leading
    for j in i..n-1
      matrix[i][j]/=leading
    end
    for j in i+1..n-1
      leading = matrix[j][i]
      for k in i..n-1
        matrix[j][k]-= leading*matrix[i][k]
      end
      b[j]-=b[i]* leading
    end
  end

  i = n-1
  while i >= 0
    x = b[i].to_f
    j = n-1
    while j > i
      x -= matrix[i][j] * res[j]
      j-=1
    end
    x/=matrix[i][i]
    res[i]=x.to_f
    i-=1
  end

  return res
end

print solveEquations(3)