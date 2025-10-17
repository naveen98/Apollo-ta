package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pageobjects.QuestionnairePages;
import utils.Excelutils;

import java.io.IOException;

public class QuestionnaireSteps {

    WebDriver driver;
    QuestionnairePages qp;

    @Given("i navigate to Questionnaire module")
    public void i_navigate_to_Questionnaire_module(){
        driver= DriverManager.getDriver();
        qp=new QuestionnairePages(driver);
        qp.navigatetoQuestionaries();



    }
    @Then ("i create Questionnaire from excel sheet")
    public void i_create_Questionnaire_from_excel_sheet() throws IOException {

       String path="C:\\Users\\navee\\IdeaProjects\\Apollo-ta\\src\\test\\resources\\questionaries.xlsx";
       String sheetname="question";

       String data[][]= Excelutils.getcelldatas(path,sheetname);

       for (int i=0;i<data.length;i++){

           String questiontype=data[i][0];
           String behaviour=data[i][1];
           String competency=data[i][2];
           String question=data[i][3];
           String answer=data[i][4];
           String imagefile = data[i][5].replaceAll("^\"|\"$", "");
           String audiofile = data[i][6].replaceAll("^\"|\"$", "");

           qp.addquestion();

           qp.createquestion(questiontype,behaviour,competency,question,answer);
           qp.addfiles(imagefile,audiofile);

           qp.savebutton();



       }

    }

}
