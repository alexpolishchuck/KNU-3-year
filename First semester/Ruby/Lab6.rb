class Student
  def initialize(surname_, place_, succeeded_)
    if !(surname_.is_a?String) || !(place_.is_a?String) || (succeeded_!=1 && succeeded_!=0)
      raise ArgumentError.new("Wrong input data!")
    end
    @surname = surname_
    @succeeded = succeeded_
    @place = place_
  end
  def getSurname()
    @surname
  end
  def getSucceded
    @succeeded
  end
  def getPlace
    @place
  end


end

def start()
  numberofStudents = rand(45..75)
  students = Array.new(numberofStudents)
  for i in 0...numberofStudents
    surname = (0...10).map { ('a'..'z').to_a[rand(26)] }.join
    place =  ["10","tech","college"].to_a[rand(3)]
    students[i] = Student.new(surname,place,rand(2))

  end

  numWantedTenthGrade =0
  numGotIntoTenthGrade=0
  wantedToCollege = Array.new
  gotIntoCollege = Array.new

  students.each do |student|
    place = student.getPlace()
    surname = student.getSurname()
    succeeded = student.getSucceded()
    if place == "10"
      numWantedTenthGrade +=1
      if succeeded == 1
        numGotIntoTenthGrade+=1
      end
    else wantedToCollege.push(student)
    if succeeded == 1
      gotIntoCollege.push(student)
    end
    end
  end
  puts "Number of students who wanted to get into tenth grade:#{numWantedTenthGrade}"
  puts "Number of students who got into tenth grade:#{numGotIntoTenthGrade}"
  rest = numGotIntoTenthGrade%25
  numberOfClassrooms = numGotIntoTenthGrade/25
  if rest <15 && numberOfClassrooms!=0
    temp = (numGotIntoTenthGrade - 15)/numberOfClassrooms
    if temp>=15
      numberOfClassrooms+=1
    end
  end
  puts "Required number of classrooms:#{numberOfClassrooms}"
  if numberOfClassrooms >0
    puts "Approximate number of students in each of them:#{numGotIntoTenthGrade/numberOfClassrooms}"
  end
  puts "Students who wanted to enrol at college:#{wantedToCollege.to_s}"
  puts "Students who enrolled at college:#{gotIntoCollege.to_s}"
end

start()