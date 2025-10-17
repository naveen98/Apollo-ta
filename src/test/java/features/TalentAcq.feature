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

    @regression
    Scenario: navigate to campaign module
      Given i am on the campaign module page
      When i navigate to campaign module
      Then i create new campaign from excel sheet

      @Smoke
      Scenario: navigate to job module
        Given i navigate to job module page
        When i create job from excel sheet

      @Smoke
      Scenario:navigate to vacancy module
        Given i navigated to vacancy module
        Then i create a vacancy from excel sheet


        @Smoke
        Scenario:navigate to vacancy module module verify edit and delete actions
          Given i navigate to vacancy module
          When i verify the edit action
          Then i verify the delete action


      @Smoke
      Scenario: Creation of Team Mapping
        Given i navigate to team mapping module
        Then i add user team mapping

        @Smoke
        Scenario: Under sourcing module recruitment scoring
          Given i navigate to sourcing module under recruitment scoring
          Then i add scoring from excel sheet

          @Smoke
          Scenario: verifying the edit and delete actions
            Given i navigate to recruitment scourcing
            When i click on edit action
            Then i click on delete action


            @Smoke
            Scenario: creating recruiter ranking
              Given i navigate to recruiter ranking
              Then i create recruiter ranking from excel sheet

        @smoke
        Scenario: creating Questionnaire Module
          Given i navigate to Questionnaire module
          Then i create Questionnaire from excel sheet








