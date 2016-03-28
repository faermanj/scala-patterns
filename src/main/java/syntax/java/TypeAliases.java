package syntax.java;

import java.util.HashMap;
import java.util.Map;

public class TypeAliases {
	
	Map<String,Map<Integer,String>> dics = new HashMap<>();
	
	public String lookup(String palavra, Integer versao) {
		if(dics.containsKey(palavra)){
			Map<Integer,String> dict = dics.get(palavra);
			if (dict.containsKey(versao)){
				return dict.get(versao);
			}
		}
		throw new IllegalArgumentException();
	}
	
	
}
