Feature: Talent Acquisition

  @smoke @regression
  Scenario: Login with valid credentials and see CMS label
    Given I am on the CMS login page
    When I login using valid credentials
    Then I should see the CMS home page

  @smoke @regression
  Scenario: User selects Talent Acquisition
    Given user is on the App Selection page
    When user clicks on the appselection
    Then Apollo TalentAcq app should be displayed

   @smoke
  Scenario: Navigate to campaign module
    Given i am on the campaign module page
    When i navigate to campaign module
    Then i create new campaign from excel sheet

  @smokes
  Scenario: Navigate to job module
    Given i navigate to job module page
    When i create job from excel sheet

  @smoke
  Scenario: Navigate to vacancy module
    Given i navigated to vacancy module
    Then i create a vacancy from excel sheet

  @smoke
  Scenario: Verify vacancy edit and delete actions
    Given i navigate to vacancy module
    When i verify the edit action
    Then i verify the delete action

  @smoke
  Scenario: Creation of Team Mapping
    Given i navigates to team mapping module
    When i add user team mapping and sites to recruiters
    Then i add region hr team mapping and map sites to region hr

  @smoke
  Scenario: Verify edit delete actions in team mapping
    Given Navigate team mapping module
    When i click on edit action recruiters
    And i click on delete action recruiters
    Then i click on edit and delete action region hr

  @smoke
  Scenario: Recruitment scoring under sourcing module
    Given i navigate to sourcing module under recruitment scoring
    Then i add scoring from excel sheet

  @smoke
  Scenario: Verify recruitment souring edit and delete actions
    Given i navigate to recruitment sourcing
    When i click on edit action
    Then i click on delete action

  @smokes
  Scenario: Creating recruiter ranking
    Given i navigate to recruiter ranking
    Then i create recruiter ranking from excel sheet

  @smokes
  Scenario: Creating Questionnaire Module
    Given i navigate to Questionnaire module
    Then i create Questionnaire from excel sheet

  @smoke
  Scenario: Verify the campaign creation details
    Given iam navigate to campaign module
    When i verify the created campaign details
    Then i capture the data into excel

  @smoke
  Scenario: Verify Applications module
    Given iam navigate to Application module
    When i verify the application details
    Then i capture the applicants data into excel


  @smoke
  Scenario: User navigate to Venu Module
    Given I navigate to Venu module from Menubar
    When I create new Venu configuration from Excel test data

    @smoke
    Scenario: User navigate Venue Module And Capture the data into excel
      Given Iam navigate to Venu module from Menubar
      When I verify the created Venu details
        Then I capture the Venu data into excel file



