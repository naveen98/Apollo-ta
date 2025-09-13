Feature: Talent Acquisition
  Scenario: Login with valid credentials and see CMS label
    Given I am on the CMS login page
    When I login using valid credentials
    Then I should see the CMS home page

  Scenario: User selects Talent Acquistion
    Given user is on the App Selection page
    When user clicks on the Talent_Acquistion
    Then Apollo TalentAcq app should be displayed

    Scenario: User navigate to Venu Module
      Given I am logged into Talent Acqusition
      When I Navigate to venu module from  Menubar
      Then venu module page should visible

  Scenario: Create Venu configuration from Excel
    Given I am on the Venu Module page
    When I create new Venu Configuration  from Excel test data