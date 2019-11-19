import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.hanthink.util.Tools;


public class TestIsNumber {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x = Tools.getInt("-125");
		System.out.println(x);
		/*boolean flag = true; 
	   Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]*");
	   Matcher isNum = pattern.matcher("");
//	   double d = Tools.getDouble("253555.3.",0.0);
	   if( !isNum.matches() ){
		   flag = false; 
	   } 
	   

//		boolean flag = StringUtils.isNumeric(arg0);
		System.out.println(flag);*/
	}

}
