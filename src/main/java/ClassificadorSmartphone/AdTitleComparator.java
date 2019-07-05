package ClassificadorSmartphone;

import java.io.BufferedReader;
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
import java.util.List;
import java.util.stream.*;

public class AdTitleComparator {
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

	private List<String> fileLines = new ArrayList<String>();
	public static final String OUTPUT_PATH = "files/output.tsv";
	public static final String INPUT_PATH = "files/data_estag_ds.tsv";
	private int i = 0;
	
	public AdTitleComparator() {
		try {//prepara fileLines
			readFileIntoLines();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//lê o arquivo e coloca cada linha no Array fileLines
	public void readFileIntoLines() throws IOException {
		BufferedReader buffRead = new BufferedReader(new InputStreamReader(new FileInputStream(INPUT_PATH), "UTF-8"));
		String line;
		line = buffRead.readLine();
		
		while(line != null) {
			fileLines.add(line);
			line = buffRead.readLine();
		}
		buffRead.close();
	}
	
	//retorna o ArrayList com os titulos que possuem algum nome de alguma marca
	public ArrayList<String> filterTitlesByBrand(){
		ArrayList<String> selectedList = new ArrayList<String>();
		int brandsSize = brands.length;
		
		while(i < brandsSize) {
		 	List<String> temp = fileLines.stream().filter(s -> s.toLowerCase().contains(brands[i].toLowerCase()))
							.collect(Collectors.toList());
		 	selectedList.addAll(temp);
		 	i++;
		}
		return selectedList;
	}
	
	//retorna uma lista com todas as linhas que contém a palavra
	public List<String> filterTitlesByWord(List<String> list, String word){
		List<String> temp = list.stream()
				.filter(s -> s.toLowerCase().contains(word.toLowerCase()))
				.collect(Collectors.toList());
		return temp;
	}
	
	public static boolean containsIgnoreCase(String str, String subString) {
		String strLower = str.toLowerCase();
		String subStringLower = subString.toLowerCase();
		
		System.out.println("Comparando <<" + subStringLower + ">> dentro de " + strLower);
        return strLower.contains(subStringLower);
    }
	
	public void writeLinesIntoFile(List<String> lines,String path) throws IOException {
		BufferedWriter buffWrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true), StandardCharsets.UTF_8));
		for(String s : lines) {
			buffWrite.append(s + "\n");
		}
		buffWrite.close(); 
	}
	
	public List<String> getFileLines() {
		return fileLines;
	}
	
}
