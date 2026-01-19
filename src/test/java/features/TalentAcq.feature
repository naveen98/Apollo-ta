Feature: Talent Acquisition

  @smoke @regression
  Scenario: Verify Login with valid credentials and see TalentAcquisition
    Given I am on the TalentAcquisition login page
    When I login using valid credentials
    Then  I should see the TalentAcquisition page

  @smoke @regression
  Scenario: Verify the clicking Talent Acquisition
    Given I Navigate to App Selection page
    When I clicks on the AppSelection


   @smoke
   Scenario:Create campaigns and verify campaign count
     Given I am on the campaign module page
     When I navigate to campaign module for count verification
     And I capture campaign count before creation
     Then I create campaigns and verify campaign count

  @smokes
  Scenario: Verify Navigate to Job Module
    Given i navigate to job module page for creating job
    Then  i click create job


  @smoke
  Scenario: Verify Navigate to vacancy module for creating vacancy
    Given i navigate to vacancy module for vacancy creation
    Then i create a vacancy

  @smoke
  Scenario: Verify vacancy Edit and Delete actions
    Given i navigate to vacancy module for Edit and Delete actions
    When i verify the edit action
    Then i verify the delete action

  @smoke
  Scenario: Verify Creation of Team Mapping and Assigning the users
    Given i navigates to team mapping module for Mapping
    When i add user team mapping and sites to recruiters
    Then i add region hr team mapping and map sites to region hr

  @smoke
  Scenario: Verify Edit And Delete actions In Team Mapping
    Given  i Navigate team Mapping Module for Edit and Delete Actions
    When i click on edit action recruiters
    And i click on delete action recruiters
    Then i click on edit and delete action region hr

  @smoke
  Scenario:Verify Recruitment Scoring Under Scoring Module for Adding Scoring
    Given i navigate to sourcing module under recruitment scoring
    Then i click add scoring

  @smokes
  Scenario: Verify Recruitment scoring Edit and Delete actions
    Given i navigate to recruitment scoring for edit and delete actions
    When i click on edit action
    Then i click on delete action

  @smokes
  Scenario: Verify Creating Recruiter ranking
    Given i navigate to recruiter ranking
    Then i create recruiter ranking

  @smokes
  Scenario: Verify Creating Questionnaire Module
    Given i navigate to Questionnaire module for creating question
    Then i create Questionnaire

  @smoke
  Scenario: Verify the Campaign Creation Details
    Given iam navigate to campaign module for capturing campaign details
    When i verify the created campaign details
    Then i capture the data

  @smoke
  Scenario: Verify Applications Module for Capturing Applicant Details
    Given iam navigate to Application module
    When i verify the application details
    Then i capture the applicants data


  @smokes
  Scenario: Verify User Navigate to Venu Module For Creating Venue
    Given I navigate to Venue module for Venue Creation
    When I create new Venue configuration

    @smoke
    Scenario: Verify User Navigate To Venue Module And Capture the data
      Given Iam navigate to Venue module for capturing Venu details
      When I verify the created Venue details
      Then I capture the Venue data



