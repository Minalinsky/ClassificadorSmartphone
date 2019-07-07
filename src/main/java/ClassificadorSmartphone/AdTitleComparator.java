package ClassificadorSmartphone;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.*;

public class AdTitleComparator {
	private static final String[] brands = FileManager.getBrands();
	private static int i = 0;
	
	//Recebe uma lista de títulos e retorna outra lista com os titulos que possuem algum nome de alguma marca de brands[]
	public List<String> filterTitlesByBrand(List<String> titles){
		List<String> selectedList = new ArrayList<String>();
		int brandsSize = brands.length;
		
		while(i < brandsSize) {
		 	List<String> temp = titles.stream().filter(s -> s.toLowerCase().contains(brands[i].toLowerCase()))
							.collect(Collectors.toList());
		 	selectedList.addAll(temp);
		 	i++;
		}
		return selectedList;
	}
	
	//retorna uma lista com todas as linhas que contém a palavra do parâmetro "word"
	public List<String> filterTitlesByWord(List<String> list, String word){
		List<String> temp = list.stream()
				.filter(s -> s.toLowerCase().contains(word.toLowerCase()))
				.collect(Collectors.toList());
		return temp;
	}
	
	//compara se uma string contém uma substring ignorando maiúsculas e minúsculas
	public boolean containsIgnoreCase(String str, String subString) {
		String strLower = str.toLowerCase();
		String subStringLower = subString.toLowerCase();
        return strLower.contains(subStringLower);
    }
	
	
	//retorna true somente se o parâmetro "title" possui alguma palavra idêntica a outra em "items"
	//Passar "items" ordenados para busca binária
	public boolean strContainsItemFromList(String title, List<String> items) {	
		String[] titleWords = title.toLowerCase().split(" |\t|,|-|/");
		items.replaceAll(String::toLowerCase);
		i = 0;
		while(i < titleWords.length) {
			if(Collections.binarySearch(items, titleWords[i]) >= 0) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	//verifica se o título passado tem o mesmo ID que algum dos IDs passados como parâmetro
	public boolean adIdEqualsAny(String title, List<String> ids) {
		String[] titleWords = title.split("\t");
		
		i = 0;
		while(i < titleWords.length) {
			if(ids.stream().anyMatch(item -> titleWords[i].toLowerCase().equals(item.toLowerCase()))) {
				return true;
			}
			i++;
		}
		return false;
	}

	public List<String> removeDuplicates(List<String> list){
		List<String> auxList = new ArrayList<String>();
		for(String s : list) {
			if (!auxList.contains(s)) { 
                auxList.add(s); 
            } 
		}
		return auxList;
	}
	public List<String> removeTermFromList(List<String> original, String[] termsToRmv){
		for(String term : termsToRmv) {
			original.remove(term);
		}
		return original;
	}
	//recebe uma lista de anúncios e retorna todos os anúncios que contém alguma palavra de "terms"
 	public List<String> adsContainingTerms(List<String> adTitles, List<String> terms){
		List<String> containingModels = new ArrayList<String>();
		
		//Comparando se o título do anúncio contém algum modelo
		for(String ad : adTitles) {
			if(strContainsItemFromList(ad, terms)) {
				//se estiver, adicionar na lista que será retornada
				containingModels.add(ad);
			}
		}
		return containingModels;
	}
	
	public List<String> removeFirstWord(List<String> s) {
		List<String> auxList = new ArrayList<String>();
		for(int i = 0; i < s.size(); i++) {
			String[] splitStr = s.get(i).split(" ", 2);
			auxList.add(splitStr[1]);
		}
		return  auxList;
	}
	
	public List<String> removeFirstWordTab(List<String> s) {
		List<String> auxList = new ArrayList<String>();
		for(int i = 0; i < s.size(); i++) {
			String[] splitStr = s.get(i).split("\t", 2);
			auxList.add(splitStr[1]);
		}
		return  auxList;
	}

 	//Dado títulos de anúncio, retorna seus IDs
 	public List<String> splitIDs(List<String> titles){
 		List<String> aux = new ArrayList<String>();
 		List<String> splittedOnTabs = new ArrayList<String>();
 		for(int i = 0; i < titles.size(); i++) {
 			String[] splitStr = titles.get(i).split("\t", 2);
 			splittedOnTabs.add(splitStr[0]);
 		}
 		aux.addAll(splittedOnTabs);
 		return aux;
 	}
 	
 	
 	
 	
 	
 	
 	
 	
}
