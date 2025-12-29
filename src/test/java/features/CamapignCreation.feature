Feature: Campaign creation

  @smoke @regression
  Scenario: Login with valid credentials and see CMS label
    Given I am on the CMS login page
    When I login using valid credentials
    Then I should see the CMS home page

  @smoke @regression
  Scenario: User selects Talent Acquisition
    Given user is on the App Selection page
    When user clicks on the AppSelection
    Then Apollo TalentAcq app should be displayed


  @smoke
  Scenario: Create campaigns from excel and verify campaign count
    Given I am on the campaign module page
    When I navigate to campaign module for count verification
    And I capture campaign count before creation
    Then I create campaigns from excel and verify campaign count


  @smoke
  Scenario: Verify edit action for campaign
    Given I navigate to campaign module
    When I verify edit action for campaign


  @smoke
  Scenario: click on campaign and add recruiters
    Given  navigate to campaign module
    When I click on campaign and add recruiters


  @smoke
  Scenario: Verify Added recruiter in campaign
    Given I navigate to campaign Recruiter Table
    When I  capture added recruiter in campaign


  @smoke
    Scenario:Login with Recruiter
      Given I login with recruiter credentials
      When I verify recruiter dashboard page


    @smoke
      Scenario:Navigate To Campaign Module
      Given I Navigate To Campaign Module
      When I Captures the campaigns Table data
      Then I should click on logout


  @smoke
  Scenario: Login with valid credentials and see CMS label
    Given I am on the CMS login page
    When I login using valid credentials
    Then I should see the CMS home page

  @smoke
  Scenario: User selects Talent Acquisition
    Given user is on the App Selection page
    When user clicks on the AppSelection
    Then Apollo TalentAcq app should be displayed

    @smoke
      Scenario:Delete Campaign and check count after delete
      Given I Navigate to Campaign Module
      When i click on Delete Campaign
      Then i Verify the Deleted Campaign










































  @smokess
  Scenario: Recruiter copies URL and completes application using quick apply
    Given I am on campaign recruiter page
    When I click recruiter copy url and open in new tab
    And I enter mobile number and otp
    And I complete application using quick apply

