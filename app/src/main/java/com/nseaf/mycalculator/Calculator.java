package com.nseaf.mycalculator;

public class Calculator {
    String numberString = "0";
    String detailsString = "";
    long intNumber;
    double realNumber;
    boolean isIntNumber = true;
    boolean numHasRadixPoint = false;
    double memory = 0.0;
    boolean isMemory = true;

    //    flag to check if either e,pi or % was clicked
    boolean funcflag = false;

    final double e = 2.71828;
    final double pi = 3.141592;

    char operator;
    boolean operatorExists = false;
    double op1;
    boolean op1Exists = false;
    double op2;

    int positionOfDecimal;
    int positionOfLastDigit;

    //    int digitsAfterDecimalPoint;
//    String formatString;
    int zeroCount = 0;
    int zeroCountImmediateToPoint = 0;

    int clearCount = 0;

    public Calculator() {
    }

    //process decimal
    public void radixPointClicked() {
        if (numberString.length() < 11 && !numberString.contains(".")) {  // limit of 10 digits
            realNumber = Double.parseDouble(numberString + ".");
            numberString = String.valueOf(realNumber);
            detailsString = "Clicked: . ";
            numHasRadixPoint = true;
        } else
            detailsString = "The number is too long or already contains a decimal..";
    }

    //process number
    public void processNumber(int i) {
        //operatorExists has been clicked before this
        //op1!=0 happens when already an operation has been completed before and this is the consecutive operation
        //e.g.: (2+3)+4  ;(2+3) results in op1=5 , i.e. op1 is not 0, hence if +4 is clicked, it stores op2=i and processes further
        if (operatorExists && op1Exists && !numHasRadixPoint) {
            op2 = i;
            detailsString = "Clicked: " + i;
            numberString = String.valueOf(i);
        }
        //if there is no decimal clicked
        else if (!numHasRadixPoint) {
            if (numberString.length() < 12) {  // limit of 12 digits
                intNumber = intNumber * 10 + i;
                numberString = String.valueOf(intNumber);
                detailsString = "Clicked: " + i;
            } else
                detailsString = "The number is too long..";
        } else {
            //when the input already has a decimal point (so this code block is for numbers after the decimal point)
            if (numberString.length() < 11) {  // point can be used on position- 11th digit or lower
                realNumber = Double.parseDouble(numberString);
                positionOfDecimal = numberString.indexOf(".");
                positionOfLastDigit = numberString.length() - 1;
                isIntNumber = false;

                //if there is only 1 digit after decimal point which is 0
                if (positionOfDecimal == positionOfLastDigit - 1 && numberString.charAt(positionOfLastDigit) == '0') {
                    if (i == 0) {
                        //if there is nothing after decimal (and now the user enters its first 0 or 00 or 000)
                        //increase the count of 0 so that we can fill it in later
                        //e.g.: 12.0 (now i=0)
                        //e.g.: 12.00 which means realNumber = 12.0 with zero count=2 (now i=0)
                        zeroCountImmediateToPoint++;
                    } else if (zeroCountImmediateToPoint == 0 && i != 0) {
                        //case when a non zero is pressed immediately after decimal
                        ////e.g.: 12.0 with zero count = 0(now i=1)
                        realNumber = realNumber + (double) i / 10;
                    } else {
                        //case when a non zero is pressed after n 0s after the decimal
                        //e.g.: 12.0000 with zero count = 3 (now i=1)
                        realNumber = realNumber + i / (Math.pow(10, numberString.length() - positionOfDecimal + zeroCountImmediateToPoint - 1));
                        zeroCountImmediateToPoint = 0;
                    }
                } else if (i == 0) {
                    //if there is atleast a non zero digit after the decimal and then the user enters 0
                    //e.g.: 12.1 (now i=0)
//                    since double values dont have trailing 0s concept after decimal, we increment count of zero
//                    everytime a 0 is entered (not immediately after decimal though),
//                    so that when a non zero no. is entered later, we put these 0s before it
//                    e.g.: 12.12 ->(0 entered)->12.12(zeroCount=1)->(0 entered)->12.12(zeroCount=2)->(1 entered)->12.001 (else block for this else if, does this calculation to fit in the zeros)
                    zeroCount++;
                    for (int y = 0; y < zeroCount; y++) {
                        numberString = numberString + "0";
                    }
//                    realNumber = Double.parseDouble(numberString);
                } else {
                    //if there is more than 1 digit after the decimal and it is any digit other than 0

//                    12.53 giving error cuz 3/10^3 is giving 0.029999999 instead of 0.03, chatgpt suggests using BigDecimal
                    realNumber = realNumber + i / (Math.pow(10, numberString.length() - positionOfDecimal + zeroCount));
                    zeroCount = 0;
                }
                numberString = String.valueOf(realNumber);
                detailsString = "Clicked: " + i;
            } else
                detailsString = "The number is too long..";
            if (op1Exists) op2 = realNumber;
        }

    }

    //process operator
    public void processOperator(char op) {
        detailsString = "Clicked: " + op;
        //if +,-,÷ or * is clicked
        if (!operatorExists && op != '=') {
            op1Exists = true;
            operator = op;
            //since operator1 is already set, we can reset status of decimal-exists
            numHasRadixPoint = false;
            if (isIntNumber) {
                op1 = intNumber;
            } else op1 = realNumber;
            operatorExists = true;
        }// if = is clicked
        else if (op == '=') {
            //if e,%, or pi is clicked, we need the resulted value as op2
            if (funcflag) op2 = realNumber;

            if (operator == '÷') {
                realNumber = op1 / op2;
            } else if (operator == 'x') {
                realNumber = op1 * op2;
            } else if (operator == '-') {
                realNumber = op1 - op2;
            } else {
                //addition case (operator == '+')
                realNumber = op1 + op2;
            }

            //case if (5+3) +4  then (5+3)=8 will be op1
            intNumber = (int) realNumber;
            op2 = 0;
            operatorExists = false;

            numberString = String.valueOf(realNumber);
            detailsString = operator + " performed!";
        } else {
            detailsString = "Invalid operation!";
        }
    }

    public void clearClicked() {
        if (clearCount > 0) {
            //more than 1 click of clear (need to clear everything)
            numberString = "0";
            detailsString = "";
            intNumber = 0;
            realNumber = 0.0;
            isIntNumber = true;
            numHasRadixPoint = false;
            op1 = op2 = 0;
            operatorExists = false;
            op1Exists = false;
            int zeroCount = 0;
            int zeroCountImmediateToPoint = 0;
            funcflag = false;
            clearCount = 0;
        } else {
            //clearcount=0 i.e. one click of clear (need to clear the displayed no.)
            clearCount++;
            numberString = "0";
            //if op2 is cleared and equal to is pressed, we went to make the result 0 i.e. op1 *0=0
            if (op1Exists && operatorExists) op2 = 0;
        }

    }

    //process M+
    public void memPlusClicked() {
        //since funcflag(true if %,pi or e used) results in double value, we check if funcflag is not true
        if (isIntNumber && !funcflag) {
            memory += intNumber;
        } else {
            memory += realNumber;
        }
        detailsString = "Memory: " + memory;
    }

    //process M-
    public void memMinusClicked() {
        if (isIntNumber && !funcflag) {
            memory -= intNumber;
        } else {
            memory -= realNumber;
        }
        detailsString = "Memory: " + memory;
    }

    //process MR
    public void memReadClicked() {
        detailsString = "Memory: " + memory;
    }

    //process MC
    public void memClearClicked() {
        detailsString = "";
        memory = 0;
        detailsString = "Memory: " + memory;
    }

    //process e
    public void exponentialClicked() {
        realNumber = Math.pow(e, intNumber);
        numberString = String.valueOf(realNumber);
        detailsString = "Clicked: e^" + intNumber;
        funcflag = true;
    }

    //process pi
    public void piClicked() {
        realNumber = pi;
        numberString = String.valueOf(realNumber);
        detailsString = "Clicked: \uD835\uDF0B";
        funcflag = true;
    }

    //process %
    public void percentClicked() {
        if (isIntNumber) {
            realNumber = (double) intNumber / 100;
        } else {
            realNumber = (double) realNumber / 100;
        }
        //for case: 5*2% (we want real no. to be 2% and not 2 as the operand2)
        if (op1Exists) {
            op2 = op2 / 100;
            realNumber = op2;
        }
        numberString = String.valueOf(realNumber);
        detailsString = "Clicked: %";
        funcflag = true;
    }

}
