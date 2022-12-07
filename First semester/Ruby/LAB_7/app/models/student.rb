class Student < ApplicationRecord
  validates :surname, :presence => true
  validates :institution, :presence => true
  validates :enrolled, inclusion: [true, false]
  validates :enrolled, exclusion: [nil]
end
