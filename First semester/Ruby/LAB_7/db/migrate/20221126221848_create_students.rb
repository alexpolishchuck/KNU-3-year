class CreateStudents < ActiveRecord::Migration[7.0]
  def change
    create_table :students do |t|
      t.string :surname, index: {unique: true}
      t.string :institution
      t.boolean :enrolled

      t.timestamps
    end
  end
end
