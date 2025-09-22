Feature: Talent Acquisition
  Scenario: Login with valid credentials and see CMS label
    Given I am on the CMS login page
    When I login using valid credentials
    Then I should see the CMS home page

  Scenario: User selects App Selection
    Given user is on the App Selection page
    When user clicks on the appselection
    Then Apollo TalentAcq app should be displayed

  Scenario:Navigate to Users Module
    Given I am logged into admin
    When i navigate to menu bar
    Then user module should be visible

    Scenario:creating user form
      Given iam on the user module page
      When i create a user from Excel

