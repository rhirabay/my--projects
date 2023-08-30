Feature: sample feature
  Background:
    * url 'http://localhost:8080'

  Scenario: sample scenario
    Given path '/sample'
    When method get
    Then status 200
