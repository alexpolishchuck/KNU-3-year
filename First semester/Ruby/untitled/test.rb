class Student
  def initialize(surname_, place_, succeeded_)
    if !(surname_.is_a?String) || !(place_.is_a?String) || (succeeded_!=1 && succeeded_!=0)
      raise ArgumentError.new("Wrong input data!")
    end
    @surname = surname_
    @succeeded = succeeded_
    @place = place_
  end
  enddef start()
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
  enddef start()
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
    enddef start()
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
      enddef start()
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