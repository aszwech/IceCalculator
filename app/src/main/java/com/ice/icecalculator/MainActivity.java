package com.ice.icecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;

import static java.lang.String.*;


public class MainActivity extends AppCompatActivity {

        private EditText e1, e2;
        private int count = 0;
        private String expression = "";
        private int resultEqual = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.icecalendar);

            e1 = findViewById(R.id.editText1);
            if (e1.getText().toString().isEmpty()) {
                e1.setBackgroundResource(android.R.color.transparent);
            }
            e2 = findViewById(R.id.editText2);

            e2.setText(expression);
        }

        private void clearAfterFail() {
            if (resultEqual == -1) {
                e1.setText("");
                e2.setText("");
                count = 0;
                expression = "";
            }
            resultEqual = 0;
        }

        public void onClick(View v) {
            String text;
            switch (v.getId()) {
                case R.id.num0:
                    clearAfterFail();
                    e2.append("0");
                    break;
                case R.id.num1:
                    clearAfterFail();
                    e2.append("1");
                    break;
                case R.id.num2:
                    clearAfterFail();
                    e2.append("2");
                    break;
                case R.id.num3:
                    clearAfterFail();
                    e2.append("3");
                    break;
                case R.id.num4:
                    clearAfterFail();
                    e2.append("4");
                    break;
                case R.id.num5:
                    clearAfterFail();
                    e2.append("5");
                    break;
                case R.id.num6:
                    clearAfterFail();
                    e2.append("6");
                    break;
                case R.id.num7:
                    clearAfterFail();
                    e2.append("7");
                    break;
                case R.id.num8:
                    clearAfterFail();
                    e2.append("8");
                    break;
                case R.id.num9:
                    clearAfterFail();
                    e2.append("9");
                    break;
                case R.id.dot:
                    clearAfterFail();
                    if (count == 0 && e2.length() != 0 && !e2.getText().toString().contains(".")) {
                        e2.append(".");
                        count++;
                    }
                    if (e2.length() == 0) {
                        e2.append("0.");
                        count++;
                    }
                    break;
                case R.id.clear:
                    e1.setText("");
                    e2.setText("");
                    count = 0;
                    expression = "";
                    break;
                case R.id.backSpace:
                    text = e2.getText().toString();
                    if (text.length() > 0) {
                        if (text.endsWith(".")) {
                            count = 0;
                        }
                        String newText = text.substring(0, text.length() - 1);
                        if (text.endsWith(")")) {
                            char[] a = text.toCharArray();
                            int pos = a.length - 2;
                            int counter = 1;
                            for (int i = a.length - 2; i >= 0; i--) {
                                if (a[i] == ')') {
                                    counter++;
                                } else if (a[i] == '(') {
                                    counter--;
                                }
                                else if (a[i] == '.') {
                                    count = 0;
                                }
                                if (counter == 0) {
                                    pos = i;
                                    break;
                                }
                            }
                            newText = text.substring(0, pos);
                        }
                        if (newText.equals("-") || newText.endsWith("sqrt")) {
                            newText = "";
                        }
                        else if (newText.endsWith("^"))
                            newText = newText.substring(0, newText.length() - 1);

                        e2.setText(newText);
                    }
                    break;

                case R.id.plus:
                    clearAfterFail();
                    operationClicked("+");
                    break;

                case R.id.minus:
                    clearAfterFail();
                    operationClicked("-");
                    break;

                case R.id.divide:
                    clearAfterFail();
                    operationClicked("/");
                    break;

                case R.id.multiply:
                    clearAfterFail();
                    operationClicked("*");
                    break;

                case R.id.sqrt:
                    clearAfterFail();
                    if (e2.length() != 0) {
                        text = "sqrt(" + e2.getText().toString() + ")";
                        e2.setText(text);
                    }
                    break;

                case R.id.square:
                    clearAfterFail();
                    if (e2.length() != 0) {
                        text = "(" + e2.getText().toString() + ")^2";
                        e2.setText(text);
                    }
                    break;

                case R.id.inverse:
                    clearAfterFail();
                    if (e2.length() != 0) {
                        text = 1+ "/" + e2.getText().toString();
                        e2.setText(text);
                    }
                    break;

                case R.id.posneg:
                    clearAfterFail();
                    if (e2.length() != 0) {
                        String s = e2.getText().toString();
                        char arr[] = s.toCharArray();
                        if (arr[0] == '-')
                            e2.setText(s.substring(1, s.length()));
                        else {
                            s = "-" + s;
                            e2.setText(s);
                        }
                    }
                    break;

                case R.id.equal:
                    if (e2.length() != 0) {
                        text = e2.getText().toString();
                        expression = e1.getText().toString() + text;
                    }
                    e1.setText("");
                    if (expression.length() == 0) {
                        expression = "0";
                    }
                    try {
                        Double result = new ExtendedDoubleEvaluator().evaluate(expression);
                        String resultStr = format(Locale.US, "%.2f", result) + "";
                        e2.setText(resultStr);
                        resultEqual = 1;
                    } catch (Exception e) {
                        e2.setText(R.string.invalid_expresion);
                        e1.setText("");
                        expression = "";
                        resultEqual = -1;
                    }
                    break;
                case R.id.openBracket:
                    clearAfterFail();
                    e2.append("(");
                    break;

                case R.id.closeBracket:
                    clearAfterFail();
                    e2.append(")");
                    break;
            }
        }

        private void operationClicked(String op) {
            if (e2.length() != 0) {
                String text = e1.getText() + e2.getText().toString() + op;
                e1.setText(text);
                e2.setText("");
                count = 0;
            } else {
                String text = e1.getText().toString();
                if (text.length() > 0) {
                    String newText = text.substring(0, text.length() - 1) + op;
                    e1.setText(newText);
                }
            }
        }
    }

