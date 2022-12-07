require './app/assets/modules/enum_institutions'
class CrudController < ApplicationController
  include Enum_institutions
  def new_student
  end
  def create_student
    @student = Student.new(student_params)
    @student.save
    redirect_to :action => "new_student"
  end
  def show_all
       @students = Student.all
    # redirect_to :action => "show"
     render 'show'
  end
  def show

  end
  def edit
    @student = Student.find(params[:id])
  end
  def update
    @student = Student.find(params[:id])
    if(@student.update(student_params))
      redirect_to :action => "show_all"
    else
      redirect_to :action => "edit"
    end
  end
  def delete_student
    begin
      @student = Student.find(params[:id])
    rescue => error
      else
    @student.destroy
    end
    redirect_to :action => "show_all"
  end
  helper_method :delete_student
  def delete_all_students
    Student.delete_all
    redirect_to :action => "show_all"
  end

  def show_statistics
    calculate_stats()
  end
  def show_students_who_wanted_to_college
    @students = Student.where(institution: Enum_institutions::COLLEGE)
    render 'show', :locals => {:students => @students}
  end
  def show_students_who_got_to_college
    @students = Student.where(institution: Enum_institutions::COLLEGE, enrolled: true)
    render 'show', :locals => {:students => @students}
  end
  private def student_params
    params.require(:student).permit(:surname,:institution, :enrolled, :id)
  end
  private def calculate_stats
    @students = Student.all
    @numWantedTenthGrade =0
    @numGotIntoTenthGrade=0
    @wantedToCollege = Array.new
    @gotIntoCollege = Array.new
    @students.each do |student|
      place = student.institution
      surname = student.surname()
      succeeded = student.enrolled()
      if place == Enum_institutions::TENTH_GR
        @numWantedTenthGrade +=1
        if succeeded == true
          @numGotIntoTenthGrade+=1
        end
      else @wantedToCollege.push(student)
      if succeeded == true
        @gotIntoCollege.push(student)
      end
      end
    end
    rest = @numGotIntoTenthGrade%25
    @numberOfClassrooms = @numGotIntoTenthGrade/25
    if rest <15 && @numberOfClassrooms!=0
      rest = (@numGotIntoTenthGrade - 15).to_f/@numberOfClassrooms
    end
    if rest>=15
      @numberOfClassrooms+=1
    end
  end
end
