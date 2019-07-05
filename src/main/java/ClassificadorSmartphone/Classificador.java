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
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Classificador {
	private static final String[] brands = {
			"Acer",
			"Aermoo",
			"AGM",
			"Akai",
			"Alcatel",
			"AllCall",
			"Allview",
			"Amazon",
			"Amigoo",
			"Apple",
			"Archos",
			"Argos",
			"Ark",
			"Assistant",
			"Asus",
			"Avvio",
			"BeeX",
			"BenQ",
			"Black Fox",
			"BlackBerry",
			"Blackview",
			"Blaupunkt",
			"BLU",
			"Bluboo",
			"BQ",
			"BQ Mobile",
			"Bravis",
			"Cagabi",
			"Casper",
			"CAT",
			"Celkon", 
			"Centric",
			"Cherry Mobile",
			"Colorovo",
			"Comio",
			"ConCorde",
			"Conquest",
			"Coolpad",
			"Crosscall",
			"Cubot",
			"Daj",
			"DEXP",
			"Digma",
			"Doogee",
			"Doopro",
			"Doov",
			"Echo",
			"Ecoo ",
			"Elephone",
			"Energizer",
			"Essential",
			"Evolveo",
			"Explay",
			"FairPhone",
			"FinePower",
			"Fly",
			"Flycat",
			"Freetel",
			"Fujitsu",
			"Geecoo",
			"Geotel",
			"Gigabyte",
			"Gigaset",
			"Ginzzu",
			"Gionee",
			"Gome",
			"Google",
			"Gooweel",
			"Gretel",
			"Haier",
			"Happymobile",
			"Haus",
			"Helio",
			"Highscreen",
			"Hisense",
			"HomTom",
			"Hotwav",
			"HTC",
			"Huawei",
			"Hyve",
			"Icemobile",
			"iLA",
			"iMan",
			"Impression",
			"iNew",
			"Infinix",
			"InFocus",
			"InnJoo",
			"iNO Mobile",
			"Inoi",
			"Intex",
			"Ioutdoor",
			"IQM",
			"Irbis",
			"Itel",
			"Ivoomi",
			"Jeasung",
			"Jesy",
			"JiaYu",
			"Jinga",
			"Just5",
			"JVC",
			"Karbonn",
			"Kazam",
			"Keneksi",
			"Kenxinda",
			"KingZone",
			"Kodak",
			"Kogan",
			"Konka",
			"Koobee",
			"Koolnee",
			"Kult",
			"Kyocera",
			"Land Rover",
			"Landvo",
			"Lanix",
			"Lava",
			"Leagoo",
			"LeEco",
			"Lenovo",
			"LeRee",
			"LeTV",
			"LG",
			"Lumigon",
			"Lyf",
			"M-Horse",
			"Maxvi",
			"Maxwest",
			"Maze",
			"Meiigoo",
			"Meitu",
			"Meizu ",
			"Micromax",
			"Microsoft",
			"Mlais",
			"Mobiistar",
			"Motorola",
			"MPIE",
			"Multilaser",
			"myPhone",
			"MyWigo",
			"Neffos",
			"Nexian",
			"Nextbit",
			"Noa",
			"Nobby",
			"Nokia",
			"Nomi",
			"Nomu",
			"NUU Mobile",
			"OKWU",
			"OnePlus",
			"Onkyo",
			"Oppo",
			"Oukitel",
			"Palm",
			"Panasonic",
			"Phicomm",
			"Philips",
			"Phonemax",
			"Pixelphone",
			"Pixus",
			"Plum",
			"Pluzz",
			"Polaroid",
			"Poptel",
			"Posh",
			"PPTV",
			"Prestigio",
			"Qiku",
			"QMobile",
			"Razer",
			"Red",
			"RugGear",
			"Runbo",
			"Samsung",
			"Senseit",
			"Sharp",
			"Sigma",
			"Siswoo",
			"Smartisan",
			"Snopow",
			"Sonim",
			"Sony",
			"Spice",
			"Starmobile",
			"Sugar",
			"SuperD",
			"Swipe",
			"Symphony",
			"TCL",
			"Tecno",
			"teXet",
			"THL",
			"Timmy",
			"Uhans",
			"Uhappy",
			"Ukozi",
			"Ulefone",
			"UMi",
			"UMiDIGI",
			"Vargo",
			"Vernee",
			"Vertex",
			"Vertu",
			"Verykool", 
			"Vestel",
			"Videocon", 
			"Vivo",
			"Vkworld",
			"Vodafone",
			"Walton",
			"Weimei",
			"Wieppo",
			"Wigor",
			"Wiko ",
			"Wileyfox", 
			"Wolder",
			"Xgody",
			"Xiaolajiao", 
			"Xiaomi",
			"Xolo",
			"Xtouch",
			"Yandex",
			"Yezz",
			"Yota", 
			"YU",
			"Zen",
			"Zoji",
			"Zopo",
			"ZTE",
			"ZUK"};
	
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

			//Lista de modelos Sem a Marca
			List<String> phonesWithoutBrand = comp.removeFirstWord(phones);

			//criando lista com os nomes dos modelos de celulares separados para comparacao
			List<String> search = new ArrayList<String>();

			//filtrando Lista para montar um array com todos os modelos de telefones
			for(String p : phonesWithoutBrand) {
				search.addAll(Arrays.asList(p.split(" ")));
			}
			//removendo letras únicas (ex: E, I, X)
			search = search.stream().filter(mod -> mod.length() >= 2).collect(Collectors.toList());
			
			/*adicionando as empresas à lista para comparação
			 * para também considerarmos seus nomes na pesquisa
			 */
			search.addAll(Arrays.asList(brands));
			
			//removendo itens repetidos (ex: remoção da palavra "Galaxy" -> Galaxy S6, Galaxy S7, Galaxy S8)
			search = comp.removeDuplicates(search); 

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
