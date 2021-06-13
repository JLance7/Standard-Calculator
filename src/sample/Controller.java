package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.math.BigDecimal;

public class Controller {

    @FXML
    private Label result;

    private double num1 = 0;
    private double num2 = 0;
    private String operator = "";
    private boolean start = true;
    private boolean afterEquals = false;
    private boolean operatorJustPressed = false;

    private boolean decimalUsed = false;

    @FXML
    public void processNumbers(ActionEvent e){
        if (operatorJustPressed){       //after first number and operator is pressed clear screen
            result.setText("");
            operatorJustPressed = false;
        }
        //get value of text on button
        String value = ((Button)e.getSource()).getText();
        if (start){             //at beginning remove 0
            result.setText("");
            start = false;
        }
        if (afterEquals){        //if number is entered right after using equals clear screen
            result.setText("");
            afterEquals = false;
        }
        //add numbers clicked to label
        result.setText(result.getText() + value);
    }

    @FXML
    public void processOperators(ActionEvent e){       //+,-,/,x, & = operators
        //get value of operator on button
        String value = ((Button)e.getSource()).getText();
        decimalUsed = false;

        if (afterEquals){
            operator = value;
            num1 = Double.parseDouble(result.getText());
            afterEquals = false;
            operatorJustPressed = true;
        }

        if (!value.equals("=")){                    //if button is +,-,x, or /
            if (!operator.isEmpty())                //if operator is not empty exit out of method (prevent hitting operator twice)
                return;

            operator = value;
            num1 = Double.parseDouble(result.getText());
            operatorJustPressed = true;
        } else{                   //if operator is "="
            if (operator.isEmpty())        //if no other operator was used besides = exit method
                return;
            num2 = Double.parseDouble(result.getText());
            double output = calculate();
            result.setText(String.valueOf(output));
            operator = "";
            afterEquals = true;
        }
    }

    public double calculate(){
        switch (operator){
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "x":
                return num1 * num2;
            case "/":
                if (num2 == 0){
                    return 0;
                }
                return num1 / num2;
            default:
                return 0;
        }
    }

    @FXML
    public void processMoreOperators(ActionEvent e){       //.,%,1/x,x², & ²√x
        String value = ((Button)e.getSource()).getText();
        switch (value){
            case ".":
                if (decimalUsed){
                    return;
                }
                result.setText(result.getText() + ".");
                decimalUsed = true;
                break;
            case "%":
                double decimal = Double.parseDouble(result.getText());
                BigDecimal percent = new BigDecimal(decimal);
                result.setText(String.valueOf(percent.scaleByPowerOfTen(-2)));
                operatorJustPressed = true;
                break;
            case "1/x":
                double number = Double.parseDouble(result.getText());
                double answer = 1/number;
                result.setText(String.valueOf(answer));
                operatorJustPressed = true;
                break;
            case "x²":
                double num = Double.parseDouble(result.getText());
                double answer2 = num*num;
                result.setText(String.valueOf(answer2));
                operatorJustPressed = true;
                break;
            case "²√x":
                double numba = Double.parseDouble(result.getText());
                double answer3 = Math.sqrt(numba);
                result.setText(String.valueOf(answer3));
                operatorJustPressed = true;
                break;
            default:
                break;
        }


    }

    @FXML
    public void clearOperators(ActionEvent e){   //C, CE, & ⌫
        String value = ((Button)e.getSource()).getText();

        switch (value){
            case "C":
                num1 = 0;
                num2 = 0;
                result.setText("");
                break;
            case "CE":
                result.setText("");
                break;
            case "⌫":
                String text = result.getText();
                String newString = text.substring(0, text.length() - 1);
                result.setText(newString);
                break;
            default:
                break;
        }
    }
}
