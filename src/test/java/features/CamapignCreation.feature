Feature: Campaign creation

  @smoke @regression
  Scenario: Verify Login with valid credentials and see TalentAcquisition
    Given I am on the TalentAcquisition login page
    When I login using valid credentials
    Then  I should see the TalentAcquisition page

  @smoke @regression
  Scenario: Verify the clicking Talent Acquisition
    Given I Navigate to App Selection page
    When I clicks on the AppSelection
    Then Apollo Acquisition app should be displayed


  @smoke
  Scenario: Verify counts for campaign section in campaign module
    Given I am on the campaign module page
    When I navigate to campaign module for count verification
    And I capture campaign count before creation
    Then I create campaigns and verify campaign count


  @Smoke
  Scenario: Verify Edit Action for campaign
    Given I navigate to campaign module
    When I verify edit action for campaign


  @Smoke
  Scenario: Verify adding recruiters to a specific campaign
    Given I have navigated to the Campaigns module
    When I click on a campaign and add recruiters
    Then I should see the recruiters successfully added to the campaign

  @Smoke
  Scenario: Verify Added recruiter in campaign
    Given I navigate to campaign Recruiter Table
    When I capture added recruiter in campaign
    Then I should see the added recruiter in campaign recruiter table


  @Smoke
  Scenario: verify Login with Recruiter
    Given I login with recruiter credentials
    When I verify recruiter dashboard page

  @Smoke
  Scenario: Verify the campaign table data from recruiter campaign module
    Given I Navigate To Campaign Module
    When I Captures the campaigns Table data
    Then I should click on logout

  @Smoke
  Scenario: Login with valid credentials and TalentAcquisition CMS label
    Given I am on the TalentAcquisition login page
    When I login using valid credentials
    Then I should see the TalentAcquisition page

  @Smoke
  Scenario: Verify the clicking Talent Acquisition
    Given I Navigate to App Selection page
    When I clicks on the AppSelection
    Then Apollo Acquisition app should be displayed

  @Smoke
  Scenario: Delete Campaign and check count after delete
    Given I Navigate to Campaign Module
    When i click on Delete Campaign
    Then i Verify the Deleted Campaign

  @Smoke
  Scenario:I Verify Campaign Recruiter copies URL and completes application
    Given I am on campaign recruiter page
    When I click recruiter copy url and open in new tab
    And I enter mobile number and otp
    Then I click submit application

    @regression
      Scenario:Verify the Applied candidate details From Application Module
      Given I Navigate to applications module
      When I verify the candidate application and capture the status

  @regression
  Scenario: Verify recruiters Application with invalid Data
    Given I am on campaign recruiter page for invaid data
    When I open recruiters application url in new tab
    Then I should see validation errors after submitting with invalid data

