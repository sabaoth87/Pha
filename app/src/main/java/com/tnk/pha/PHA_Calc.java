package com.tnk.pha;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.content.Context;

public class PHA_Calc extends Activity implements OnClickListener{

	private static String ACTIVITYNAME = "PHA_Calc";

	public static TextView CalcDisplay;
	public static TextView OperatorDisplay;
	public StringBuilder number1Builder;
	public StringBuilder number2Builder;
	public StringBuilder numberBuilder;
	public StringBuilder displayBuilder;
	public String displayEqn;
	public boolean EqlsHit;
	public boolean ClcDivHit;
	public boolean ClcMultHit;
	public boolean ClcPlusHit;
	public boolean ClcMinusHit;
	public boolean number1Set = false;
	public boolean number2Set = false;
	public String calcAnswer;
	public double answer = 0;
	public double number1 = -1;
	public double number2 = -1;
	public boolean mathDone = false;
	public int entryNumber;

	public int numbersAvail;
	public String[] mathStrings;
	public Double[] doubleString;
	public String[] workers;
	public String worker;
	public String worker1;
	public String worker2;
	public Double doubleWorker;
	public Double doubleWorker1;
	public String PHA_calc1;

	public ExpressionBuilder mathBuilder;
	public Calculable mathCalcable;
	public double mathAnswer;

	public double[] PHA_ClcNumbers;
	public String[] PHA_MathNumbers;

	public double varX;
	public double varY;
	public double answerMem0 = 0.0;
	public double answerMem1 = 0.0;
	public double answerMem2 = 0.0;
	public double answerMem3 = 0.0;
	public double answerMem4 = 0.0;
	public boolean valMem0 = false;
	public boolean valMem1 = false;
	public boolean valMem2 = false;
	public boolean valMem3 = false;
	public boolean valMem4 = false;
	public Toast setXToast;
	public Toast setYToast;
	public Toast clearToast;
	public Toast setXFailToast;
	public Toast setYFailToast;
	private int durationShort = Toast.LENGTH_SHORT;
	private int durationLong = Toast.LENGTH_LONG;
	private String setYText = "Set Y to ";
	private String setXText = "Set X to ";
	private String setXFailText = "Nothing to Assign to X";
	private String setYFailText = "Nothing to Assign to Y";
	private String setXConfirmText = "X set to";
	private String setYConfirmText = "Y set to";
	private String clearText = "Cleared";
	private Context context;

	private double[] calcMems;
	private boolean calcMemsInitiated = false;
	private int memLocale = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pha_calc);
		Log.v(ACTIVITYNAME, "onCreate");
		/*
		 * Initiate the TextView to show the calculations
		 */
		CalcDisplay = (TextView) findViewById(R.id.phaClcDisplay1);
		OperatorDisplay = (TextView) findViewById(R.id.PHA_ClcOperatorsDisplay);
		/*
		 * Initiate the StringBuilder to handle the button inputs
		 */
		displayBuilder = new StringBuilder();
		number1Builder = new StringBuilder();
		number2Builder = new StringBuilder();
		numberBuilder = new StringBuilder();
		calcBttnStart();
		context = getApplicationContext();
		setXFailToast = Toast.makeText(context, setXFailText, durationShort);
		setYFailToast = Toast.makeText(context, setYFailText, durationShort);
		if (!calcMemsInitiated){
			memInitiate();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pha__calc, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		//setXToast = Toast.makeText(context, setXText, durationShort);
		//setYToast = Toast.makeText(context, setYText, durationShort);
		clearToast = Toast.makeText(context, clearText, durationLong);
		switch (v.getId()){
		case R.id.phaClcZero:
			displayBuilder.append(0);
			numberBuilder.append(0);
			break;
		case R.id.phaClcOne:
			displayBuilder.append(1);
			numberBuilder.append(1);
			break;
		case R.id.phaClcTwo:
			displayBuilder.append(2);
			numberBuilder.append(2);
			break;
		case R.id.phaClcThree:
			displayBuilder.append(3);
			numberBuilder.append(3);
			break;
		case R.id.phaClcFour:
			displayBuilder.append(4);
			numberBuilder.append(4);
			break;
		case R.id.phaClcFive:
			displayBuilder.append(5);
			numberBuilder.append(5);
			break;
		case R.id.phaClcSix:
			displayBuilder.append(6);
			numberBuilder.append(6);
			break;
		case R.id.phaClcSeven:
			displayBuilder.append(7);
			numberBuilder.append(7);
			break;
		case R.id.phaClcEight:
			displayBuilder.append(8);
			numberBuilder.append(8);
			break;
		case R.id.phaClcNine:
			displayBuilder.append(9);
			numberBuilder.append(9);
			break;
		case R.id.phaClcDP:
			displayBuilder.append(".");
			numberBuilder.append(".");
			break;


		case R.id.PHA_ScnClcAcos:
			displayBuilder.append("acos(");
			numberBuilder.append("acos(");
			break;
		case R.id.PHA_ScnClcAsin:
			displayBuilder.append("asin(");
			numberBuilder.append("asin(");
			break;
		case R.id.PHA_ScnClcAtan:
			displayBuilder.append("atan(");
			numberBuilder.append("atan(");
			break;
		case R.id.PHA_ScnClcSin:
			displayBuilder.append("sin(");
			numberBuilder.append("sin(");
			break;
		case R.id.PHA_ScnClcCos:
			displayBuilder.append("cos(");
			numberBuilder.append("cos(");
			break;
		case R.id.PHA_ScnClcTan:
			displayBuilder.append("tan(");
			numberBuilder.append("tan(");
			break;
		case R.id.PHA_ScnClcE:
			numberBuilder.append("exp(");
			displayBuilder.append("exp(");
			break;
		case R.id.PHA_ScnClcInverse:
			numberBuilder.append("(1/(");
			displayBuilder.append("1/(");
			break;
		case R.id.PHA_ScnClcLog:
			numberBuilder.append("log(");
			displayBuilder.append("log(");
			break;
		case R.id.PHA_ScnClcOpenBracket:
			numberBuilder.append("(");
			displayBuilder.append("(");
			break;
		case R.id.PHA_ScnClcCloseBracket:
			numberBuilder.append(")");
			displayBuilder.append(")");
			break;
		case R.id.PHA_ScnClcSqrt:
			numberBuilder.append("sqrt(");
			displayBuilder.append("sqrt(");
			break;
		case R.id.phaClcClearLast:
			if (displayBuilder.length() > 0 ) {
				displayBuilder.deleteCharAt((displayBuilder.length()-1));	
				numberBuilder.deleteCharAt((numberBuilder.length()-1));
			}
			else {
				break;
			}
			break;
		case R.id.phaClcClearAll:
			displayBuilder.delete(0, displayBuilder.length());	
			numberBuilder.delete(0, numberBuilder.length());
			clearToast.show();
			break;
		case R.id.PHA_ScnClcPercent:
			numberBuilder.append("%");
			displayBuilder.append("%");
			break;
		case R.id.PHA_ScnClcPi:
			numberBuilder.append("PI");
			displayBuilder.append("PI");
			break;
		case R.id.phaClcANS:
			numberBuilder.append("("+answerMem0+")");
			displayBuilder.append("("+answerMem0+")");
			memLocale++;
			break;
		case R.id.PHA_ScnClcX:
			displayBuilder.append("("+varX+")");
			numberBuilder.append("("+varX+")");
			break;
		case R.id.PHA_ScnClcY:
			displayBuilder.append("("+varY+")");
			numberBuilder.append("("+varY+")");
			break;
		case R.id.PHA_ScnClcSetX:
			//possibly try sending it to the string math jar to ensure its a viable variable
			if (displayBuilder.length()>0){
				varX = Double.valueOf(numberBuilder.toString());
				displayBuilder.delete(0, displayBuilder.length());	
				numberBuilder.delete(0, numberBuilder.length());
				setXToast = Toast.makeText(context, (setXText + varX), durationShort);
				setXToast.show();
				break;
			}
			else {
				setXFailToast.show();
				break;
			}
		case R.id.PHA_ScnClcSetY:
			if (displayBuilder.length()>0){
				varY = Double.valueOf(numberBuilder.toString());
				displayBuilder.delete(0, displayBuilder.length());	
				numberBuilder.delete(0, numberBuilder.length());
				setYToast = Toast.makeText(context, (setYText + varY), durationShort);
				setYToast.show();
				break;
			}
			else {
				setYFailToast.show();
				break;
			}

		case R.id.phaClcDiv:
			displayBuilder.append("/");
			numberBuilder.append("/");
			ClcDivHit =true;
			Log.v(ACTIVITYNAME, "hit Div");
			OperatorDisplay.setText("/");
			break;
		case R.id.phaClcPlus:
			displayBuilder.append("+");
			numberBuilder.append("+");
			ClcPlusHit =true;
			Log.v(ACTIVITYNAME, "hit Plus");
			OperatorDisplay.setText("+");
			break;
		case R.id.phaClcMult:
			displayBuilder.append("*");
			numberBuilder.append("*");
			ClcMultHit =true;
			Log.v(ACTIVITYNAME, "hit Mult");
			OperatorDisplay.setText("*");
			break;
		case R.id.phaClcMinus:
			displayBuilder.append("-");
			numberBuilder.append("-");	
			ClcMinusHit =true;
			Log.v(ACTIVITYNAME, "hit Minus");
			OperatorDisplay.setText("-");
			break;


		case R.id.phaClcEqls:
			displayBuilder.append("=");
			try {
				PHA_DoMathSon(numberBuilder);
				Log.v(ACTIVITYNAME, "did math");
			} catch (UnknownFunctionException e) {
				Log.v(ACTIVITYNAME, "Caught Unknown Function");
				e.printStackTrace();
			} catch (UnparsableExpressionException e) {
				Log.v(ACTIVITYNAME, "Caught Unknown Expression");
				e.printStackTrace();
			}
			break;

		case R.id.phaClcDltBtn:
			if (displayBuilder.length()>0){
				if (number2Builder.length() >0){
					number2Builder.delete(0, number2Builder.length());
					Log.v(ACTIVITYNAME, "number 2 deleted");
					break;
				}
				number1Builder.delete(0, number1Builder.length());
				Log.v(ACTIVITYNAME, "number 1 deleted");

			}
			break;

			// more butons go here later
		}
		displayUpdate();
	}

	public void calcBttnStart(){
		/*
		 * Right Side, Normal Function Buttons
		 */
		((Button) findViewById(R.id.phaClcZero)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcOne)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcTwo)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcThree)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcFour)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcFive)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcSix)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcSeven)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcEight)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcNine)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcMinus)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcPlus)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcMult)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcDiv)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcDP)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcEqls)).setOnClickListener(this);

		/*
		 * Left Side, Advanced Math Function Buttons
		 */		
		((Button) findViewById(R.id.PHA_ScnClcAcos)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcAsin)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcAtan)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcCos)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcTan)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcSin)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcInverse)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcSquare)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcSqrt)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcClearAll)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcE)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcPercent)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcClearLast)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcLog)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcOpenBracket)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcCloseBracket)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcPi)).setOnClickListener(this);
		((Button) findViewById(R.id.phaClcANS)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcRandom)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcY)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcX)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcSetY)).setOnClickListener(this);
		((Button) findViewById(R.id.PHA_ScnClcSetX)).setOnClickListener(this);

		((ImageView) findViewById(R.id.phaClcDltBtn)).setOnClickListener(this);		

	}	

	public void displayUpdate(){

		if (mathDone == false){
			displayEqn = displayBuilder.toString();
			CalcDisplay.setText(displayEqn);
		}
		if (mathDone == true){
			displayBuilder.delete(0, displayBuilder.length());	
			numberBuilder.delete(0, numberBuilder.length());
			displayEqn = Double.toString(mathAnswer);
			CalcDisplay.setText(displayEqn);
			mathDone=false;}
	}

	public void calcMem(double answerVal){
		for (int i =0; i>5; i++){
			calcMems[i]=answerVal;
		}
		//		if (valMem0){
		//			if(valMem1){
		//				if(valMem2){
		//					if(valMem3){
		//						if(valMem4){
		//							answerMem4 = answer;
		//							valMem4 = true;
		//						}
		//						answerMem3= answer;
		//						valMem3 = true;
		//					}
		//					answerMem2 = answer;
		//					valMem2 = true;
		//				}
		//				answerMem1 = answer;
		//				valMem1 = true;
		//			}
		//			answerMem0 = answer;
		//			valMem0 = true;
		//		}

	}

	public void PHA_DoMathSon(StringBuilder numbers) throws UnknownFunctionException, UnparsableExpressionException{
		Log.v(ACTIVITYNAME, "doing math");
		PHA_calc1 = numbers.toString();
		Log.v(ACTIVITYNAME, PHA_calc1);
		Calculable calc = new ExpressionBuilder(PHA_calc1)
		.withVariable("x", varX)
		.withVariable("y", varY)
		.build();
		mathAnswer=calc.calculate();
		mathDone =true;
		Log.v(ACTIVITYNAME, Double.toString(mathAnswer));
		answerMem0 = mathAnswer;
	}

	public void memInitiate(){
		for (int i =0; i>5;i++){
			calcMems[i]=0.0;
		}
		calcMemsInitiated = true;
	}

}