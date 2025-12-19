package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.VacancyEditandDeletePage;
import utils.Excelutils;

import java.io.IOException;

public class VacancyEditDeleteSteps {

    WebDriver driver;
    VacancyEditandDeletePage vc;


    @Given("i navigate to vacancy module")
    public void i_navigate_to_vacancy_module(){

       driver= DriverManager.getDriver();
       vc=new VacancyEditandDeletePage(driver);

        vc.navigatevacancymodule();


    }

    @When("i verify the edit action")
    public void i_verify_the_edit_action() throws IOException {
        String path="D:\\selenium-intellij\\src\\test\\resources\\vacancycreation.xlsx";
        String SheetName="edit";

        String data[][]= Excelutils.getcelldatas(path,SheetName);
         for (int i=0;i<data.length;i++){

             String searchname=data[i][0];


             String noofvacancies=data[i][1];


             vc.searchitem(searchname);

             if (vc.isNoRecordFoundDisplayedFast()) {
                 System.out.println("No record found for: " + searchname);
                 continue;
             }

             vc.clickeditbutton();
             vc.editvacancy(noofvacancies);
             vc.clickupdatebutton();

             String message=vc.getvalidationmessage();
             boolean success=message.toLowerCase().contains("vacancy updated")||message.toLowerCase().contains("successfully");

             if (success){
                 System.out.println("Updated successfully : " + message);
             }
             else {

                 System.out.println("Updated failed : " + message);

             }
         }

    }
       @Then("i verify the delete action")
       public void i_verify_the_delete_action() throws IOException {

           String paths = "D:\\selenium-intellij\\src\\test\\resources\\vacancycreation.xlsx";
           String sheetName = "edit";

           String datas[][] = Excelutils.getcelldatas(paths, sheetName);
           for (int j = 0; j < datas.length; j++) {

               String searchid = datas[j][0];

               vc.searchdelete(searchid);
           if(vc.isNoRecordFoundDisplayedFast()){
                System.out.println("No record found for: " + searchid);
                continue;


           }
               vc.clickdelete();

               String message = vc.gettoastmessage();
               boolean success = message.toLowerCase().contains("deleted ")||message.toLowerCase().contains("successfully");
               if (success) {
                   System.out.println("Deleted successfully :  " + message);

               } else {
                   System.out.println("Deleted failed :  " + message);

               }

           }

           }

}
