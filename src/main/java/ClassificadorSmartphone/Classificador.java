package ClassificadorSmartphone;

import com.aafanasev.fonoapi.DeviceEntity;
import com.aafanasev.fonoapi.retrofit.FonoApiFactory;
import com.aafanasev.fonoapi.retrofit.FonoApiService;
import java.util.stream.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Classificador {
	private static FileManager fileManager = new FileManager();
	private static int i = 0;
	
	public static void main(String[] args) throws IOException {
		
		try {
			//caso arquivo não exista:
			fileManager.createPhonesDB();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			AdTitleComparator comp = new AdTitleComparator();

			//lendo arquivo de entrada
			List<String> adTitles = fileManager.readFileIntoLines(FileManager.INPUT_PATH);//comp.filterTitlesByBrand();

			//Lista com Marcas e modelos de celulares
			List<String> phones = fileManager.readFileIntoLines(FileManager.PHONES_DB);

			//Removendo Marcas
			phones = comp.removeFirstWord(phones);

			//filtrando lista para montar um array com todos os modelos de telefones
			List<String> search = new ArrayList<String>();
			for(String p : phones) {
				search.addAll(Arrays.asList(p.split(" ")));
			}
			//removendo letras únicas (ex: E, I, X)
			search = search.stream().filter(mod -> mod.length() >= 2).collect(Collectors.toList());
			
			/*adicionando as empresas à lista para comparação
			 * para também considerarmos seus nomes na pesquisa
			 */
			search.addAll(Arrays.asList(fileManager.getBrands()));
			
			//removendo itens repetidos (ex: palavra "Galaxy" -> Galaxy S6, Galaxy S7, Galaxy S8)
			search = comp.removeDuplicates(search);
			search = search.stream().sorted().collect(Collectors.toList()); //ordenando para binSearch
			
			//Pegando títulos que contém ao menos um dos modelos de celular ou então o nome de uma empresa de smartphone
			List<String> filteredList = comp.adsContainingTerms(adTitles, search);
			
			//pegando os ids dos anuncios selecionados
			List<String> filteredListIDs = comp.splitIDs(filteredList);
			List<String> finalResult = new ArrayList<String>();
			
			for(String ad : adTitles) {
				String s = comp.adIdEqualsAny(ad, filteredListIDs) ? ad + " SMARTPHONE" : ad + " NÃO-SMARTPHONE" ;
				finalResult.add(s);
			}
			
			System.out.println("FILTRADOS: " + filteredList.size());
			//escrevendo arquivo final
			fileManager.writeLinesIntoFile(finalResult, FileManager.OUTPUT_PATH);
		}
	}
	
	
	

}
