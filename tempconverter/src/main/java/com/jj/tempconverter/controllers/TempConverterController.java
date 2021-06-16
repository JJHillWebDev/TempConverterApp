package com.jj.tempconverter.controllers;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.springframework.stereotype.Component;


/**
    The TempConverterController class controls the TempConverter application.
    The controller allows the user to input a temperature and a unit type, a
    target unit type and the student's numeric response for each worksheet
    question. The student's response must match an authoritative answer after
    both the student's response and authoritative answer are rounded to the 
    ones place. The application indicates that the response(s) are correct,
    incorrect or invalid.

    @author Jeremy Hill
    @version 16.0.1
 */
public class TempConverterController 
{
    @FXML
    private TextField inputTempTextField;

    @FXML
    private TextField studentAnswerTextField;

    @FXML
    private RadioButton inputKelvin;

    @FXML
    private ToggleGroup inputRadioGroup;

    @FXML
    private RadioButton inputCelsius;

    @FXML
    private RadioButton inputFahrenheit;

    @FXML
    private RadioButton inputRankine;

    @FXML
    private RadioButton answerKelvin;

    @FXML
    private ToggleGroup answerRadioGroup;

    @FXML
    private RadioButton answerCelsius;

    @FXML
    private RadioButton answerFahrenheit;

    @FXML
    private RadioButton answerRankine;

    @FXML
    private ListView<String> questionsListView;

    @FXML
    private ListView<String> answersListView;

    @FXML
    private ListView<String> gradesListView;

    @FXML
    private Button addQuestionButton;

    @FXML
    private Button calculateGradesButton;

    @FXML
    private Button clearAllButton;

    //holds the input temperatures from the worksheet
    private List<String> inputTempList = new ArrayList<String>();

    //holds the input unit types from the worksheet
    private List<String> inputUnitList = new ArrayList<String>();

    //holds the student's answers from the worksheet
    private List<String> answerTempList = new ArrayList<String>();

    //holds the unit types for the student's answers
    private List<String> answerUnitList = new ArrayList<String>();

    //difference between Kelvin and Celsius
    private final double KCDELTA = 273.15;

    //difference between Rankine and Fahrenheit
    private final double RFDELTA = 459.67;

    private final double NINEFIVEFRACTION = 1.8;                // 9/5

    private final double FIVENINEFRACTION = 0.55555555555;      // 5/9


    /**
     * The addQuestionButtonListener method adds a new question to the
     * questionsListView and the student's answer to the answersListView. The
     * method also determines the unit type from the inputRadioGroup. 
     * @param event ActionEvent for "Add Question" Button
     */
    @FXML
    void addQuestionButtonListener(ActionEvent event)
    {
        //holds the question's input temperature
        String inputTemperature = inputTempTextField.getText();
        inputTempList.add(inputTemperature);

        //holds the questions's answer to the question
        String answerTemperature = studentAnswerTextField.getText();
        answerTempList.add(answerTemperature);

        //determine input temperature's unit type
        String inputUnit = "";
        if (inputKelvin.isSelected())
        {
            inputUnit = "Kelvin";
            inputUnitList.add(inputUnit);
        }
        else if (inputCelsius.isSelected())
        {
            inputUnit = "Celsius";
            inputUnitList.add(inputUnit);
        }
        else if (inputFahrenheit.isSelected())
        {
            inputUnit = "Fahrenheit";
            inputUnitList.add(inputUnit);
        }
        else if (inputRankine.isSelected())
        {
            inputUnit = "Rankine";
            inputUnitList.add(inputUnit);
        }
        else
        {
            inputUnitList.add(null);
        }

        //determine questions's answer unit type
        String answerUnit = "";
        if (answerKelvin.isSelected())
        {
            answerUnit = "Kelvin";
            answerUnitList.add(answerUnit);
        }
        else if (answerCelsius.isSelected())
        {
            answerUnit = "Celsius";
            answerUnitList.add(answerUnit);
        }
        else if (answerFahrenheit.isSelected())
        {
            answerUnit = "Fahrenheit";
            answerUnitList.add(answerUnit);
        }
        else if (answerRankine.isSelected())
        {
            answerUnit = "Rankine";
            answerUnitList.add(answerUnit);
        }
        else
        {
            answerUnitList.add(null);
        }

        //add the new question string to the questionsListView
        questionsListView.getItems().addAll(inputTemperature + " " + inputUnit
                + " to " + answerUnit);

        //add the student's answer to the answersListView
        answersListView.getItems().addAll(answerTemperature + " "
                + answerUnit);

        //clear grades ListView, textFileds and RadioGroups
        inputTempTextField.clear();
        studentAnswerTextField.clear();
        gradesListView.getItems().clear();
        inputRadioGroup.selectToggle(null);
        answerRadioGroup.selectToggle(null);
    }


    /**
     * The calculateGradesButtonListener method determines the input type for
     * the worksheet question and then calls the appropriate converter method.
     * @param event ActionEvent for "Calculate Grades" Button
     */
    @FXML
    void calculateGradesButtonListener(ActionEvent event)
    {
        //loop through the questons in the inputTempList
        for (int i = 0; i < inputTempList.size(); i++)
        {
            String response;

            //determine input unit type and call appropriate converter method
            if (inputUnitList.get(i) == ("Kelvin"))
            {
                response = kelvinConverter(inputTempList.get(i),
                        				   answerTempList.get(i),
                        				   answerUnitList.get(i));
            }
            else if (inputUnitList.get(i) == ("Celsius"))
            {
                response = celsiusConverter(inputTempList.get(i),
                        					answerTempList.get(i),
                        					answerUnitList.get(i));
            }
            else if (inputUnitList.get(i) == ("Fahrenheit"))
            {
                response = fahrenheitConverter(inputTempList.get(i),
                        					   answerTempList.get(i),
                        					   answerUnitList.get(i));
            }
            else if (inputUnitList.get(i) == "Rankine")
            {
                response = rankineConverter(inputTempList.get(i),
                        					answerTempList.get(i),
                        					answerUnitList.get(i));
            }
            else
            {
                response = "Invalid: (Input Unit Type)";
            }

            gradesListView.getItems().addAll(response);
        }
    }


    /**
     * The clearAllButtonListener method clears all ArrayLists, ListViews
     * and RadioGroups in the application.
     * @param event ActionEvent for "Clear All" Button
     */
    @FXML
    void clearAllButtonListener(ActionEvent event)
    {
        questionsListView.getItems().clear();
        answersListView.getItems().clear();
        gradesListView.getItems().clear();
        inputRadioGroup.selectToggle(null);
        answerRadioGroup.selectToggle(null);
        inputTempList.clear();
        inputUnitList.clear();
        answerTempList.clear();
        answerUnitList.clear();
    }


    /**
     * The kelvinConverter method determines if anything is invalid or
     * incorrect. If everything is valid the method converts the 
     * kelvin temperature to the new unit type and calls the testAnswers
     * method to determine if the student's answer is correct.
     * @param inputTemperature Temperature from worksheet question.
     * @param studentAnswer Student's answer to the worksheet question.
     * @param newUnitType Unit type for the question's answer.
     */
    private String kelvinConverter(String inputTemperature,
                                   String studentAnswer,
                                   String newUnitType)
    {
        Double inputNum = doubleConverter(inputTemperature);
        Double answerNum = doubleConverter(studentAnswer);

        if (inputNum == null)
        {
            return "Invalid: (Input Temperature)";
        }
        else if (answerNum == null)
        {
            return "Incorrect";
        }
        else if (newUnitType.equals("Celsius"))
        {
            Double conversion = inputNum - KCDELTA;
            return testAnswers(answerNum, conversion);
        }
        else if (newUnitType.equals("Fahrenheit"))
        {
            Double conversion = (NINEFIVEFRACTION * (inputNum - KCDELTA)) + 32;
            return testAnswers(answerNum, conversion);
        }
        else if (newUnitType.equals("Rankine"))
        {
            Double conversion = inputNum * NINEFIVEFRACTION;
            return testAnswers(answerNum, conversion);
        }
        else
        {
            return "Invalid: (Target Unit Type)";
        }
    }


    /**
     * The celsiusConverter method determines if anything is invalid or
     * incorrect. If everything is valid the method converts the 
     * celsius[] temperature to the new unit type and calls the testAnswers
     * method to determine if the student's answer is correct.
     * @param inputTemperature Temperature from worksheet question.
     * @param studentAnswer Student's answer to the worksheet question.
     * @param newUnitType Unit type for the question's answer.
     */
    private String celsiusConverter(String inputTemperature,
                                    String studentAnswer,
                                    String newUnitType)
    {
        Double inputNum = doubleConverter(inputTemperature);
        Double answerNum = doubleConverter(studentAnswer);

        if (inputNum == null)
        {
            return "Invalid: (Input Temperature)";
        }
        else if (answerNum == null)
        {
            return "Incorrect";
        }
        else if (newUnitType.equals("Kelvin"))
        {
            Double conversion = inputNum + KCDELTA;
            return testAnswers(answerNum, conversion);
        }
        else if (newUnitType.equals("Fahrenheit"))
        {
            Double conversion = (NINEFIVEFRACTION * inputNum) + 32;
            return testAnswers(answerNum, conversion);
        }
        else if (newUnitType.equals("Rankine"))
        {
            Double conversion = (inputNum + KCDELTA) * NINEFIVEFRACTION;
            return testAnswers(answerNum, conversion);
        }
        else
        {
            return "Invalid: (Target Unit Type)";
        }
    }


    /**
     * The fahrenheitConverter method determines if anything is invalid or
     * incorrect. If everything is valid the method converts the 
     * fahrenheit temperature to the new unit type and calls the testAnswers
     * method to determine if the student's answer is correct. 
     * @param inputTemperature Temperature from worksheet question.
     * @param studentAnswer Student's answer to the worksheet question.
     * @param newUnitType Unit type for the question's answer.
     */
    private String fahrenheitConverter(String inputTemperature,
                                       String studentAnswer,
                                       String newUnitType)
    {
        Double inputNum = doubleConverter(inputTemperature);
        Double answerNum = doubleConverter(studentAnswer);

        if (inputNum == null)
        {
            return "Invalid: (Input Temperature)";
        }
        else if (answerNum == null)
        {
            return "Incorrect";
        }
        else if (newUnitType.equals("Kelvin"))
        {
            Double conversion = (FIVENINEFRACTION * (inputNum - 32)) + KCDELTA;
            return testAnswers(answerNum, conversion);
        }
        else if (newUnitType.equals("Celsius"))
        {
            Double conversion = FIVENINEFRACTION * (inputNum - 32);
            return testAnswers(answerNum, conversion);
        }
        else if (newUnitType.equals("Rankine"))
        {
            Double conversion = inputNum + RFDELTA;
            return testAnswers(answerNum, conversion);
        }
        else
        {
            return "Invalid: (Target Unit Type)";
        }
    }


    /**
     * The rankineConverter method determines if anything is invalid or
     * incorrect. If everything is valid the method converts the 
     * rankine temperature to the new unit type and calls the testAnswers
     * method to determine if the student's answer is correct.
     * @param inputTemperature Temperature from worksheet question.
     * @param studentAnswer Student's answer to the worksheet question.
     * @param newUnitType Unit type for the question's answer.
     */
    private String rankineConverter(String inputTemperature,
                                    String studentAnswer,
                                    String newUnitType)
    {
        Double inputNum = doubleConverter(inputTemperature);
        Double answerNum = doubleConverter(studentAnswer);

        if (inputNum == null)
        {
            return "Invalid: (Input Temperature)";
        }
        else if (answerNum == null)
        {
            return "Incorrect";
        }
        else if (newUnitType.equals("Kelvin"))
        {
            Double conversion = inputNum / NINEFIVEFRACTION;
            return testAnswers(answerNum, conversion);
        }
        else if (newUnitType.equals("Celsius"))
        {
            Double conversion = (inputNum - 491.67) / NINEFIVEFRACTION;
            return testAnswers(answerNum, conversion);
        }
        else if (newUnitType.equals("Fahrenheit"))
        {
            Double conversion = inputNum - RFDELTA;
            return testAnswers(answerNum, conversion);
        }
        else
        {
            return "Invalid: (Target Unit Type)";
        }
    }


    /**
     * The doubleConverter method converters Strings to Double Objects and
     * returns them. It will return null if an NumberFormatException occurs.
     * @param temp Temperature being converted from Double object to double
     */
    private Double doubleConverter(String temp)
    {
        try
        {
            return Double.parseDouble(temp);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }


    /**
     * The testAnswers method converts and rounds the studentAnswer and 
     * correctAnswer Double objects to the one's place and determines
     * if the answer is correct or incorrect.
     * @param studentAnswer Student's response to the question.
     * @param correctAnswer Correct response to the question.
     */
    private String testAnswers(Double studentAnswer, Double correctAnswer)
    {
        //round the answers to the one's place
        int studentRounded = (int) Math.round(studentAnswer.doubleValue());
        int correctRounded = (int) Math.round(correctAnswer.doubleValue());

        //determine is the student's response is correct or incorrect.
        if (studentRounded == correctRounded)
        {
            return "Correct";
        }
        else
        {
            return "Incorrect";
        }
    }
}