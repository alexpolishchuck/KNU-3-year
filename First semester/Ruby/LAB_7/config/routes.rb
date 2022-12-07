Rails.application.routes.draw do
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # Defines the root path route ("/")
  # root "articles#index"
  root "main#index", as: "home"
  get "/students/new" =>"crud#new_student", as: "new_student"
  post "/students/new" =>"crud#create_student"
  get "/students/show/show_all", to: "crud#show_all", as: "show_students"
  get "/students/edit/:id", to: "crud#edit", as: "edit_student"
  patch "/students/edit/:id", to: "crud#update"
  get "/students/:id/delete", to: "crud#delete_student", as: "delete_student"
  get "/students/delete_all", to: "crud#delete_all_students", as:"delete_all_students"
  get "/students/show/statistics", to: "crud#show_statistics", as: "show_statistics"
  get "/students/show/wanted_to_college", to:"crud#show_students_who_wanted_to_college", as:"show_wanted_to_college"
  get "/students/show/got_to_college", to:"crud#show_students_who_got_to_college", as:"show_got_to_college"
  get "/students/show", to:"crud#show", as:"show"
end
