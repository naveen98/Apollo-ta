package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pageobjects.RecruiterRankingpage;
import utils.Excelutils;

import java.io.IOException;

public class RecruiterRankingSteps {

    WebDriver driver;
    RecruiterRankingpage rp;

    @Given("i navigate to recruiter ranking")
    public void i_navigate_to_recruiter_ranking(){
     driver= DriverManager.getDriver();
     rp=new RecruiterRankingpage(driver);


       rp.navigatetorecruitmentsourcing();

    }

    @Then("i create recruiter ranking from excel sheet")
    public void i_create_recruiter_ranking_from_excel_sheet() throws IOException {

        String path="C:\\Users\\navee\\IdeaProjects\\Apollo-ta\\src\\test\\resources\\recruiterranking.xlsx";
        String sheetname="ranking";

        String data[][]= Excelutils.getcelldatas(path,sheetname);

           for(String[] inputs:data) {
               String rank = inputs[0];
               String recruiter = inputs[1];
               String vacancyclosed = inputs[2];
               String sourcingeffort = inputs[3];
               String hiringeffort = inputs[4];


               rp.clickaddrankingbutton();
               rp.createrankingdata(rank, recruiter, vacancyclosed, sourcingeffort, hiringeffort);

               rp.clicksave();






           }


    }


}
