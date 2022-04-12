Feature: To test the post query functionality of the metadata API service

  Scenario: User is able to post query request to the metadata service

    Given user has the correct subject data
    When the user makes a post query request the metadata service
    Then the status code from metadata service is 200
    And the response from the service is a empty subject array

  Scenario: User is able to post query with subject and properties

    Given user has the correct subject and properties
    When the user makes a post query request the metadata service
    Then the status code from metadata service is 200
    And the response from the service is a empty subject array

  Scenario: User receives an error when he calls post query with in valid request body

    Given user has invalid request body for post query
    When the user makes a post query request the metadata service
    Then the user receives an error "Internal Server Error"




