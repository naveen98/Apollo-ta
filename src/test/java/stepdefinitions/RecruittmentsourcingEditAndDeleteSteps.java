package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pageobjects.RecruittmentsourcingEditAndDeletePage;
import utils.Excelutils;
import utils.Webdriverwaitutils;

import java.io.IOException;

public class RecruittmentsourcingEditAndDeleteSteps {

   WebDriver driver;

    RecruittmentsourcingEditAndDeletePage ed;


    @Given("i navigate to recruitment sourcing")
    public void i_navigate_to_recruitment_sourcing(){

        driver= DriverManager.getDriver();
        ed=new RecruittmentsourcingEditAndDeletePage(driver);
        //ed.navigatetorecruitmentsourcing();


    }

    @When("i click on edit action")
    public void i_click_on_edit_action() throws IOException {

         String path="D:\\selenium-intellij\\src\\test\\resources\\recruitmentsourcing.xlsx";
         String sheetname="edit";

         String data[][]= Excelutils.getcelldatas(path,sheetname);

         for(int i=0;i<data.length;i++){

             String sourcetype = data[i][0];
             String singlesoucingeffort = data[i][1];
             String singlehiringeffort = data[i][2];
             String multisourcineffort = data[i][3];
             String multihiringeffort = data[i][4];
             String partsourcingeffort = data[i][5];
             String parthiringeffort = data[i][6];
             String searchname=data[i][7];

             ed.searchitem(searchname);
             if(ed.isNoRecordFoundDisplayed()) {
                 System.out.println("No record found for: " + searchname + ". Skipping...");
                 continue;
             }

             ed.clickedit();
             ed.updatesourcingconfiguration(sourcetype,singlesoucingeffort,singlehiringeffort,multisourcineffort,multihiringeffort,partsourcingeffort,parthiringeffort);

             ed.clickupdate();

             String message=ed.getvalidationmessage();
             boolean success=message.toLowerCase().contains("updated")||message.toLowerCase().contains("successful");

             if(success){
                 System.out.println("Updated successfully :" + message);
             }else {

                 System.out.println("Updated Failed :" + message);

             }

         }

    }

    @Then("i click on delete action")
    public void i_click_on_delete_action() throws IOException {
        String path = "C:\\Users\\navee\\IdeaProjects\\Apollo-ta\\src\\test\\resources\\recruitmentsourcing.xlsx";
        String sheetname = "edit";

        String data[][] = Excelutils.getcelldatas(path, sheetname);
        for (int i = 0; i < data.length; i++) {
            String searchname = data[i][7];

            System.out.println("delete: " + searchname);
            ed.searchitem(searchname);
            if (ed.isNoRecordFoundDisplayed()) {
                System.out.println("No record found for: " + ". Skipping...");
                continue;
            }
            ed.clickdelete();
            ed.clickdeleteok();

            String message = ed.getdeletevalidationmessage();
            boolean success = message.toLowerCase().contains("deleted") || message.toLowerCase().contains("successfull");

            if (success) {
                System.out.println("Deleted successfully :" + message);
            } else {

                System.out.println("Deleted Failed :" + message);

            }

        }
    }

}
