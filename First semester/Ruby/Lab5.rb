#Task1



def methodOfRectangles(a,b,n)
  if !(n.is_a?Integer) || n<0 || !(a.is_a?Numeric) || a<0 || !(b.is_a?Numeric) || b<a
    return -1;
  end

  step = (a + b).to_f / n
  x = a + step
  res =0
  while x<=b
    res += step*(x**3 * (2.7)**(2*x))
    x+= step
  end
  return res
end
def methodOfTrapezoids(a,b,n)
  if !(n.is_a?Integer) || n<0 || !(a.is_a?Numeric) || a<0 || !(b.is_a?Numeric) || b<a
    return -1;
  end

  step = (a + b).to_f / n
  if a==0
    a+=0.00000000000001
  end
  prevF = Math.tan(a)**(5.0/2)*(5.0/a + Math::PI/4).to_f
  x = a + step
  res =0
  while x<=b
    newF = Math.tan(x)**(5.0/2)*(5.0/x + Math::PI/4)
    res += step*(newF.to_f+prevF)/2
    prevF = newF
    x+= step
  end
  return res
end

puts "Task1.1 #{methodOfRectangles(0,0.8,1000)}"
puts "Task1.2 #{methodOfTrapezoids(0,Math::PI/8,1000)}"

