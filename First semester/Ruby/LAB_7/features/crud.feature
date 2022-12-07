Feature: Crud

  Scenario: Create a new student
    When I create the following student:
    |Surname| |YAY|
    |institution| |college|
    |enrolled   | |true   |
    Then I get the following student:
      |Surname| |Diego|
      |institution| |College|
      |enrolled   | |true   |