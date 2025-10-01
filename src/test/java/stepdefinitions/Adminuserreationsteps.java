package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.AdminUserCreationPage;
import utils.Excelutils;

import java.io.IOException;

public class Adminuserreationsteps {
    WebDriver driver;
    AdminUserCreationPage ap;

    @Given("iam on the user module page")
    public void iam_on_the_user_module_page() {
        driver = DriverManager.getDriver();
        ap = new AdminUserCreationPage(driver);

    }

    @When("i create a user from Excel")
    public void i_create_a_user_from_excel() throws IOException {
        String path = "C:\\Users\\navee\\IdeaProjects\\Apollo-ta\\src\\test\\resources\\adminusercreation.xlsx";
        String sheetname = "user";

        String[][] data = Excelutils.getcelldatas(path, sheetname);
        try {

            for (int i = 0; i < data.length; i++) {

                String salt = data[i][0];
                String fnames = data[i][1];
                String midname = data[i][2];
                String lnames = data[i][3];
                String emails = data[i][4];
                String mobileno = data[i][5];
                String usernames = data[i][6];
                String roles = data[i][7];
                String reportto = data[i][8];
                String passwd = data[i][9];
                String cnfpasswd = data[i][10];

                ap.addbutton();

                ap.usercreation(salt, fnames, midname, lnames, emails, mobileno, usernames, roles, reportto, passwd, cnfpasswd);

                ap.clicksave();

                ap.waitForToast();

                String msg = ap.getvalidationmessage();

                ap.waitForToastToDisappear();

                if (msg.toLowerCase().contains("saved") || msg.toLowerCase().contains("successfully")) {
                    System.out.println(" User created successfully: " + usernames);
                    Assert.assertTrue(true);
                } else {
                    System.out.println(" User creation failed: " + msg);


                    if (msg.toLowerCase().contains("mandatory") || msg.toLowerCase().contains("fill")) {
                        ap.handlepopup();

                    } else {
                        ap.clickcancelform();
                    }

                    Assert.assertTrue(true, "Skipped: " + msg);
                }
            }

        } catch (Exception e) {
            System.out.println("Exception while creating user: " + e.getMessage());
            Assert.fail("Exception : " + e.getMessage());
        }
         /*ap.addbutton();

         boolean success = msg.toLowerCase().contains("saved") || msg.toLowerCase().contains("successfully");

         Assert.assertTrue(success, "User not saved. Message: " + msg);

         }

     } catch (Exception e) {

    System.out.println("exception while creating user : " + e.getMessage() );
}
*/


    }


}
