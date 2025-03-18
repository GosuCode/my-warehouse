package util;

import org.springframework.stereotype.Component;

@Component
public class AppUitlity {
	
	
	public static boolean isEmptyNull(String inputString) {
		return inputString == null || inputString == "";
	}

}
