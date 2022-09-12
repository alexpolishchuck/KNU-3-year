#Task1
def CalculateArea(arr)
  size = arr.length()
  res =0

  for i in 0..size-1
    j = (i <size-1)? i+1 : 0
    res += (arr[i][0] + arr[j][0])*(arr[i][1]- arr[j][1])

  end
  return res.abs()/2
end

vertices = [[514,19],[515,56],[506,107],[492,154],[386,186],[314,185],[283,234],
            [205,233],[134,220],[78,202],[68,179],[67,148],[82,118],[90,94],[92,59],[94,42],[107,29],
            [155,32],[199,60],[224,51],[279,31],
            [245,37],[306,32],[342,28],[365,22],[397,16],[443,26],[468,39],[488,32]]
puts "Task1 = #{CalculateArea(vertices)}"

#Task2
def Diap(p,t,r)
  p**r * (1 - p**(-t))
end

puts "Task2 = #{Diap(4, 16, 8).to_f}"

def from2to10(number)
  res =0
  coeff=1
  while number!=0
    a = number%10
    number/=10
    res += a*coeff
    coeff*=2
  end
  return res
end
puts "Task3 = #{from2to10(1111100011001)}"

def from10to2(number)
  res =""
  while number!=0
    remainder = number%2
    number /=2
    res << remainder.to_s
  end
  return res.reverse!()
end
puts "Task4 = #{from10to2(332)}"