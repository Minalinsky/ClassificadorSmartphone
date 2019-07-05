package ClassificadorSmartphone;

import com.aafanasev.fonoapi.DeviceEntity;
import com.aafanasev.fonoapi.retrofit.FonoApiFactory;
import com.aafanasev.fonoapi.retrofit.FonoApiService;
import java.util.stream.*;
import java.io.BufferedWriter;
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
	
	private static final String token = "0cd1d2134743a100b50c2c69153c5757dca4a2a963ab7a64";
	private static FonoApiService fono = new FonoApiFactory().create();
	private static final String PHONES_DB = "files/phonesdb.tsv";
	
	private static int i = 0;
	
	public static void main(String[] args) {
		
		
			try {
				createPhonesDB();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		/*System.out.println("ALO");
		AdTitleComparator a = new AdTitleComparator();
		
		List<String> strArr = a.filterTitlesByBrand();
		System.out.println("imprimindo" + strArr.size());
		
		try {
			a.writeLinesIntoFile(strArr, a.OUTPUT_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		
		
		/*fono.getLatest(token, "os", 1000).enqueue(new Callback<List<DeviceEntity>>() {

		    @Override
		    public void onResponse(Call<List<DeviceEntity>> call, Response<List<DeviceEntity>> response) {
		        response.body().forEach(device -> {
		            System.out.println(device.getDeviceName());
		        });
		    }

		    @Override
		    public void onFailure(Call<List<DeviceEntity>> call, Throwable t) {}

		});*/
	}
	
	//consulta a API e cria um arquivo com os dados
	public static void createPhonesDB() throws InterruptedException, IOException {
		int brandsSize = brands.length;
		FileWriter fw = new FileWriter(PHONES_DB, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bw);

		while(i < brandsSize) {
			char ch = 'a';
			while(ch  <= 'z') {
				fono.getDevice(token, brands[i] + " " + ch, null, null).enqueue(new Callback<List<DeviceEntity>>() {
				    @Override
				    public void onResponse(Call<List<DeviceEntity>> call, Response<List<DeviceEntity>> response) {
				    	response.body().forEach(device -> {
				        	out.println(device.getDeviceName());
				            System.out.println(device.getDeviceName());
				        });
				    }
				    
				    @Override
				    public void onFailure(Call<List<DeviceEntity>> call, Throwable t) {
				    	}
				});
				ch++;
			}
			i++;
		}
		out.close();
	}
	

}
