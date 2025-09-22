Feature: Talent Acquisition

  @smoke @regression
  Scenario: Login with valid credentials and see CMS label
    Given I am on the CMS login page
    When I login using valid credentials
    Then I should see the CMS home page

  @smoke @regression
  Scenario: User selects Talent Acquistion
    Given user is on the App Selection page
    When user clicks on the appselection
    Then Apollo TalentAcq app should be displayed

  @regression
  Scenario: User navigate to Venu Module
    Given I am logged into Talent Acqusition
    When I Navigate to venu module from  Menubar
    Then venu module page should visible

  @regression
  Scenario: Create Venu configuration from Excel
    Given I am on the Venu Module page
    When I create new Venu Configuration  from Excel test data

    @smoke
    Scenario: navigate to campaign module
      Given i am on the campagn module page
      When i navigate to campaign module
      Then i create new campaign from excel sheet


