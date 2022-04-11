Feature: To test the get properties request feature of the metadata API service

  Scenario Outline: User is able to retrieve the the properties from the server with valid subjects

    Given user has the correct <subject> values
    When the user makes a get properties request to the metadata service
    Then the status code from teh service is 200
    And he is able to receive correct <property> for the given subject

    Examples:
      | subject                                                                          | property    |
      | "919e8a1922aaa764b1d66407c6f62244e77081215f385b60a62091494861707079436f696e"     | "property1" |
      | "2048c7e09308f9138cef8f1a81733b72e601d016eea5eef759ff2933416d617a696e67436f696e" | "property2" |

Scenario: User gets an error message with incorrect subject value

  Given user has the incorrect subject values
  When the user makes a get properties request to the metadata service
  Then he receives a not found error message



