When(/^I create the following student:$/) do |table|
  attributes = table.rows.hash

  visit (new_student_path)
  fill_in("surname", with: "Diego")
  fill_in( "institution", with: "College")
  choose( "enrolled_yes")
  click_on ("submit")
end
Then("I get the following student :") do |surname, institution, enrolled|

end
