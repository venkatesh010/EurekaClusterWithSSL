Feature: Calculate
  Scenario: Add
    Given the input
      | input |
      | 2+2                 |
      | 4+3                 |
      | 2+6 |
    When Calculator is run
    Then the output should be
      | output |
      | 4                 |
      | 7                 |
      | 8 |

#  Scenario: Subtract
#    Given the input "9-4"
#    When Calculator is run
#    Then the output should be "5"